package lt.pavilonis.cmm;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.AbstractBeanContainer.BeanIdResolver;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.layouts.MVerticalLayout;

import java.util.Collections;

@SpringUI
@Theme("valo")
public class VaadinUI extends UI {

   @Autowired
   private UserRestRepository repo;

   @Autowired
   private UserTable table;

   @Autowired
   private UserEditPopup editForm;

   @Autowired
   private ControlPanel controlPanel;

   @Override
   protected void init(VaadinRequest request) {
      addStyles();

      setContent(new MVerticalLayout(controlPanel, table).withFullWidth().expand(table));
   }

   private void addStyles() {
      Page.Styles styles = Page.getCurrent().getStyles();
      styles.add(
            ".valo.v-app, .valo.v-app-loading { " +
                  "  font-family: sans-serif; " +
                  "  font-weight: 500 " +
                  "} " +

                  ".valo .v-margin-top { " +
                  "  padding-top: 20px " +
                  "} " +

                  ".valo .v-margin-right { " +
                  "  padding-right: 20px " +
                  "} " +

                  ".valo .v-margin-bottom { " +
                  "  padding-bottom: 20px " +
                  "} " +

                  ".valo .v-margin-left { " +
                  "  padding-left: 20px " +
                  "} " +
                  ".redicon .v-icon { " +
                  "     color: red; " +
                  "} " +

                  ".time-only .v-inline-datefield-calendarpanel-header," +
                  ".time-only .v-inline-datefield-calendarpanel-body {" +
                  "  display: none;" +
                  "}"
      );
   }

   void updateContainer(String text) {

      BeanContainer<String, UserRepresentation> container = new BeanContainer<>(UserRepresentation.class);
      container.setBeanIdResolver((BeanIdResolver<String, UserRepresentation>) UserRepresentation::getCardCode);
      try {
         container.addAll(
               StringUtils.isEmpty(text)
                     ? repo.loadAll()
                     : Collections.singletonList(repo.load(text))
         );
      } catch (Exception e) {
         Notification.show("Could not load data: " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
      }

      table.setContainerDataSource(container);
   }
}
