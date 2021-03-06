package lt.pavilonis.cmm.school.key.ui;

import com.vaadin.data.ValueProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import lt.pavilonis.cmm.api.rest.key.Key;
import lt.pavilonis.cmm.api.rest.key.KeyAction;
import lt.pavilonis.cmm.api.rest.scanner.Scanner;
import lt.pavilonis.cmm.common.ListGrid;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@SpringComponent
@UIScope
class KeyListGrid extends ListGrid<Key> {

   private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd  hh:mm");

   public KeyListGrid() {
      super(Key.class);
   }

   @Override
   protected List<String> columnOrder() {
      return List.of("scanner", "keyNumber", "dateTime", "user.name", "user.role", "user.group", "keyAction");
   }

   @Override
   protected Map<String, ValueProvider<Key, ?>> getCustomColumns() {
      return Map.of(
            "user.name", key -> key.getUser().getName(),
            "user.role", key -> key.getUser().getOrganizationRole(),
            "user.group", key -> key.getUser().getOrganizationGroup(),
            "dateTime", key -> DATE_TIME_FORMAT.format(key.getDateTime()),
            "keyAction", key -> messages.get(KeyAction.class, key.getKeyAction().name()),
            "scanner", key -> messages.get(Scanner.class, key.getScanner().getName())
      );
   }
}
