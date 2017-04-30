package lt.pavilonis.cmm.canteen.repository;


import lt.pavilonis.cmm.canteen.domain.EatingEvent;
import lt.pavilonis.cmm.canteen.domain.EatingType;
import lt.pavilonis.cmm.canteen.domain.PupilType;
import lt.pavilonis.cmm.canteen.ui.event.EatingEventFilter;
import lt.pavilonis.cmm.common.EntityRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class EatingEventRepository implements EntityRepository<EatingEvent, Long, EatingEventFilter> {

   private final RowMapper<EatingEvent> MAPPER = (rs, i) -> new EatingEvent(
         rs.getLong("id"),
         rs.getString("cardCode"),
         rs.getString("name"),
         rs.getString("grade"),
         rs.getTimestamp("date"),
         rs.getBigDecimal("price"),
         EatingType.valueOf(rs.getString("eatingType")),
         PupilType.valueOf(rs.getString("pupilType"))
   );

   @Autowired
   private JdbcTemplate jdbc;

   @Autowired
   private NamedParameterJdbcTemplate namedJdbc;

   public int numOfEatingEvents(String cardCode, Date periodStart,
                                Date periodEnd, EatingType eatingType) {
      return jdbc.queryForObject("" +
                  "SELECT count(*) " +
                  "FROM EatingEvent " +
                  "WHERE `date` BETWEEN ? AND ? " +
                  "  AND cardCode = ? " +
                  "  AND eatingType = ?",
            Integer.class,
            periodStart, periodEnd, cardCode, eatingType.name()
      );
   }

   @Override
   public List<EatingEvent> load(EatingEventFilter filter) {
      Map<String, Object> params = new HashMap<>();

      params.put("periodStart", filter.getPeriodStart() == null
            ? null
            : filter.getPeriodStart().atTime(LocalTime.MIN));

      params.put("periodEnd", filter.getPeriodEnd() == null
            ? null
            : filter.getPeriodEnd().atTime(LocalTime.MAX));

      params.put("text", StringUtils.isBlank(filter.getText())
            ? null
            : "%" + filter.getText() + "%");

      params.put("type", filter.getType() == null ? null : filter.getType().name());

      return namedJdbc.query("" +
                  "SELECT * " +
                  "FROM EatingEvent " +
                  "WHERE " +
                  "  (:periodStart IS NULL OR :periodStart <= date) " +
                  "  AND (:periodEnd IS NULL OR :periodEnd >= date) " +
                  "  AND (:text IS NULL OR name LIKE :text)" +
                  "  AND (:type IS NULL OR pupilType = :type)",
            params,
            MAPPER
      );
   }

   @Override
   public EatingEvent saveOrUpdate(EatingEvent entity) {
      Map<String, Object> args = new HashMap<>();
      args.put("cardCode", entity.getCardCode());
      args.put("name", entity.getName());
      args.put("price", entity.getPrice());
      args.put("eatingType", entity.getEatingType().name());
      args.put("grade", entity.getGrade());
      args.put("pupilType", entity.getPupilType().name());
      args.put("date", entity.getDate());

      KeyHolder keyHolder = new GeneratedKeyHolder();

      namedJdbc.update(
            "INSERT INTO EatingEvent (cardCode, `name`, price, eatingType, grade, pupilType, date) " +
                  "VALUES (:cardCode, :name, :price, :eatingType, :grade, :pupilType, :date)",
            new MapSqlParameterSource(args),
            keyHolder
      );

      return find(keyHolder.getKey().longValue())
            .orElseThrow(() -> new RuntimeException("could not save " + EatingEvent.class.getSimpleName()));
   }

   @Override
   public Optional<EatingEvent> find(Long id) {
      EatingEvent result = jdbc.queryForObject("SELECT * FROM EatingEvent WHERE id = ?", MAPPER, id);
      return Optional.of(result);
   }

   @Override
   public void delete(Long id) {
      jdbc.update("DELETE FROM EatingEvent WHERE id = ?", id);
   }

   @Override
   public Class<EatingEvent> entityClass() {
      return EatingEvent.class;
   }
}