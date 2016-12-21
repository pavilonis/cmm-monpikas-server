package lt.pavilonis.cmm.ui.menu;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import lt.pavilonis.cmm.Application;
import lt.pavilonis.cmm.ui.user.UserListControlPanel;
import lt.pavilonis.cmm.ui.user.UserTable;
import org.vaadin.viritin.layouts.MVerticalLayout;

@SpringComponent
@UIScope
public class MenuItemUsers extends MenuItem {

   private UserTable userTable;
   private UserListControlPanel controls;

   public MenuItemUsers() {
      setCaption("Users");
      setIcon(FontAwesome.USERS);
   }

   @Override
   public void collectLayoutElements(MVerticalLayout layout) {

      layout.add(controls = controls(), userTable = userTable())
            .expand(userTable);
   }

   private UserListControlPanel controls() {
      return controls == null
            ? Application.context.getBean(UserListControlPanel.class)
            : controls;
   }

   private UserTable userTable() {
      return userTable == null
            ? Application.context.getBean(UserTable.class)
            : userTable;
   }
}
