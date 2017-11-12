package lt.pavilonis.cmm.api.rest.scanlog;

import lt.pavilonis.cmm.api.rest.key.Key;
import lt.pavilonis.cmm.api.rest.key.KeyRepository;
import lt.pavilonis.cmm.api.rest.user.User;
import lt.pavilonis.cmm.api.rest.user.UserRepository;
import lt.pavilonis.util.QueryUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ScanLogRepository {

   private final Logger LOG = LoggerFactory.getLogger(ScanLogRepository.class.getSimpleName());
   private static final String FROM_WHERE_BLOCK = "" +
         "FROM mm_ScanLog sl " +
         "  JOIN mm_Scanner sc ON sc.id = sl.scanner_id " +
         "  JOIN tb_Cards c ON c.Cardcode IS NOT NULL AND c.ROMCode = sl.cardCode " +
         "  JOIN tb_Users u ON u.Cardcode = c.Cardcode " +
         "WHERE sl.dateTime >= :periodStart " +
         "  AND (:periodEnd IS NULL OR sl.dateTime <= :periodEnd) " +
         "  AND (:scannerId IS NULL OR sc.id = :scannerId) " +
         "  AND (:role IS NULL OR u.dummy4 = :role) " +
         "  AND (:text IS NULL OR sl.cardCode LIKE :text OR u.FirstName LIKE :text OR u.LastName LIKE :text)";

   @Autowired
   private NamedParameterJdbcTemplate jdbcSalto;

   @Autowired
   private KeyRepository keyRepository;

   @Autowired
   private UserRepository userRepository;

   public ScanLog saveCheckedAndLoad(long scannerId, String cardCode) {

      Long scanLogId = saveChecked(scannerId, cardCode, null);

      return scanLogId == null
            ? null
            : loadById(scannerId, scanLogId);
   }

   private ScanLog loadById(long scannerId, long scanLogId) {
      return jdbcSalto.queryForObject(
            "SELECT cardCode, dateTime FROM mm_ScanLog WHERE id = :id",
            Collections.singletonMap("id", scanLogId),
            (rs, i) -> {
               String loadedCardCode = rs.getString(1);
               User user = userRepository.load(loadedCardCode, true);
               List<Key> keys = keyRepository.loadActive(scannerId, loadedCardCode, null);
               return new ScanLog(rs.getTimestamp(2).toLocalDateTime(), user, keys);
            }
      );
   }

   public Long saveChecked(long scannerId, String cardCode, String location) {

      if (!userRepository.exists(cardCode)) {
         LOG.warn("Skipping scan log: user not found [scannerId={}, cardCode={}, location={}]",
               scannerId, cardCode, location);
         return null;
      }

      Map<String, Object> args = new HashMap<>();
      args.put("cardCode", cardCode);
      args.put("scannerId", scannerId);
      args.put("location", location);

      KeyHolder holder = new GeneratedKeyHolder();

      jdbcSalto.update("" +
                  "INSERT INTO mm_ScanLog (cardCode, scanner_id, location) " +
                  "VALUES (:cardCode, :scannerId, :location)",
            new MapSqlParameterSource(args),
            holder
      );
      LOG.info("ScanLog saved");
      return holder.getKey().longValue();
   }

   public int loadBriefSize(ScanLogBriefFilter filter) {
      return jdbcSalto.queryForObject("SELECT COUNT(*) " + FROM_WHERE_BLOCK, commonArgs(filter), Integer.class);
   }

   public List<ScanLogBrief> loadBrief(ScanLogBriefFilter filter, long offset, long limit) {
      Map<String, Object> args = commonArgs(filter);
      args.put("argOffset", offset);
      args.put("argLimit", limit);

      List<ScanLogBrief> result = jdbcSalto.query("" +
                  "SELECT " +
                  "  sl.dateTime AS dateTime, " +
                  "  sl.cardCode AS cardCode," +
                  "  sl.location AS location, " +
                  "  sc.name AS scannerName, " +
                  "  CONCAT(u.FirstName, ' ', u.LastName) AS userName, " +
                  "  u.dummy3 AS userGroup, " +
                  "  u.dummy4 AS userRole " +
                  FROM_WHERE_BLOCK +
                  "ORDER BY sl.dateTime DESC " +
                  "OFFSET :argOffset ROWS FETCH NEXT :argLimit ROWS ONLY",
            args,
            new ScanLogBriefMapper()
      );

      return result;
   }

   private Map<String, Object> commonArgs(ScanLogBriefFilter filter) {
      Map<String, Object> args = new HashMap<>();
      args.put("periodStart", filter.getPeriodStart());
      args.put("periodEnd", filter.getPeriodEnd());
      args.put("scannerId", filter.getScannerId());
      args.put("role", StringUtils.stripToNull(filter.getRole()));
      args.put("text", QueryUtils.likeArg(filter.getText()));
      return args;
   }
}
