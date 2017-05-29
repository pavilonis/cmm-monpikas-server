package lt.pavilonis.cmm.api.rest.user;

import lt.pavilonis.cmm.common.util.SimpleRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper extends SimpleRowMapper<User> {

   @Override
   public User mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new User(
            rs.getString("cardCode"),
            rs.getString("firstName"),
            rs.getString("lastName"),
            rs.getString("userGroup"),
            rs.getString("userRole"),
            rs.getString("photo"),
            rs.getString("birthDate")
      );
   }

}