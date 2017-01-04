package lt.pavilonis.cmm.ui.key;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import lt.pavilonis.cmm.common.AbstractViewController;

@SpringComponent
@UIScope
public class KeyListController extends AbstractViewController {
   @Override
   protected FontAwesome getMenuIcon() {
      return FontAwesome.KEY;
   }

   @Override
   protected Class<? extends Component> getHeaderAreaClass() {
      return KeyListFilterPanel.class;
   }

   @Override
   protected Class<? extends Component> getMainAreaClass() {
      return KeyTable.class;
   }
}
