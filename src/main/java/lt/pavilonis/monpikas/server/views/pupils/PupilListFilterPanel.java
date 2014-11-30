package lt.pavilonis.monpikas.server.views.pupils;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

import static com.vaadin.ui.Button.ClickListener;

public class PupilListFilterPanel extends HorizontalLayout {

   private TextField text = new TextField();
   private CheckBox breakfastPermission = new CheckBox("Pusryčiai");
   private CheckBox dinnerPermission = new CheckBox("Pietus");
   private Button filterButton = new Button("Filtruoti", FontAwesome.FILTER);
   private Button cancelFilterButton = new Button("Valyti", FontAwesome.REFRESH);

   public PupilListFilterPanel() {
      addComponents(text, breakfastPermission, dinnerPermission, filterButton, cancelFilterButton);
      setSpacing(true);
      setMargin(true);
      setComponentAlignment(dinnerPermission, Alignment.MIDDLE_CENTER);
      setComponentAlignment(breakfastPermission, Alignment.MIDDLE_CENTER);
      setComponentAlignment(filterButton, Alignment.MIDDLE_RIGHT);
      text.focus();
   }

   public void addFilterButtonListener(ClickListener listener) {
      filterButton.addClickListener(listener);
   }

   public PupilFilter getFilter() {
      return new PupilFilter(text.getValue(), breakfastPermission.getValue(), dinnerPermission.getValue());
   }

   public void addCancelFilterButtonListener(ClickListener listener) {
      cancelFilterButton.addClickListener(listener);
   }

   public void cleanFields() {
      text.setValue("");
      dinnerPermission.setValue(false);
      breakfastPermission.setValue(false);
   }
}
