package lt.pavilonis.cmm.api.rest.rolegroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RequestMapping("/rest")
@RestController
public class RoleGroupController {

   @Autowired
   private NamedParameterJdbcTemplate jdbcSalto;

   @GetMapping("roles")
   public List<String> loadRoles() {
      return jdbcSalto.queryForList("" +
                  "SELECT DISTINCT u.dummy4 AS userRole " +
                  "FROM tb_Users u " +
                  "  JOIN tb_Cards c ON c.Cardcode = u.Cardcode " +
                  "WHERE u.Cardcode IS NOT NULL" +
                  "  AND u.dummy4 IS NOT NULL " +
                  "  AND u.dummy4 <> '' " +
                  "ORDER BY u.dummy4",
            Collections.emptyMap(),
            String.class
      );
   }

   @GetMapping("groups")
   public List<String> loadGroups() {
      return jdbcSalto.queryForList("" +
                  "SELECT DISTINCT u.dummy3 AS userGroup " +
                  "FROM tb_Users u " +
                  "  JOIN tb_Cards c ON c.Cardcode = u.Cardcode " +
                  "WHERE u.Cardcode IS NOT NULL" +
                  "  AND u.dummy3 IS NOT NULL " +
                  "  AND u.dummy3 <> ''" +
                  "ORDER BY u.dummy3",
            Collections.emptyMap(),
            String.class
      );
   }
}
