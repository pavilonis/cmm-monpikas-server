package lt.pavilonis.monpikas.server.repositories;

import lt.pavilonis.monpikas.server.domain.Meal;
import lt.pavilonis.monpikas.server.domain.PupilLocalData;
import lt.pavilonis.monpikas.server.domain.PupilType;
import lt.pavilonis.monpikas.server.utils.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class PupilDataResultSetExtractor implements ResultSetExtractor<Map<String, PupilLocalData>> {

   private static final Logger LOG = LoggerFactory.getLogger(PupilDataResultSetExtractor.class.getSimpleName());
   private final int ANY = 100500;
   private final MealMapper MEAL_MAPPER = new MealMapper();
   private int counter;

   @Override
   public Map<String, PupilLocalData> extractData(ResultSet rs) throws SQLException, DataAccessException {
      Map<String, PupilLocalData> result = new HashMap<>();
      counter = 0;
      LocalDateTime opStart = LocalDateTime.now();
      while (rs.next()) {
         counter++;
         String cardCode = rs.getString("p.cardCode");
         PupilLocalData pupilData = result.get(cardCode);
         if (pupilData == null) {
            pupilData = new PupilLocalData(
                  cardCode,
                  PupilType.valueOf(rs.getString("p.type")),
                  rs.getString("p.comment"),
                  new HashSet<>()
            );
            result.put(cardCode, pupilData);
         }

         Long mealId = (Long) rs.getObject("m.id");
         if (mealId != null) {
            Meal meal = MEAL_MAPPER.mapRow(rs, ANY);
            pupilData.getMeals().add(meal);
         }
      }
      LOG.info("RS extraction finished [duration={}]", DateTimeUtils.duration(opStart));
      System.out.println("counter = " + counter);
      return result;
   }
}
