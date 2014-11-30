package lt.pavilonis.monpikas.server.views.mealevents;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

import static com.vaadin.ui.Button.ClickListener;

public class MealEventListFilterPanel extends HorizontalLayout {

   private TextField text = new TextField();
   private CheckBox hadDinnerToday = new CheckBox("Šiandien pietavo");
   private Button filterButton = new Button("Filtruoti", FontAwesome.FILTER);
   private Button cancelFilterButton = new Button("Valyti", FontAwesome.REFRESH);

   public MealEventListFilterPanel() {
      addComponents(text, hadDinnerToday, filterButton, cancelFilterButton);
      setSpacing(true);
      setMargin(true);
      setComponentAlignment(hadDinnerToday, Alignment.MIDDLE_CENTER);
      setComponentAlignment(filterButton, Alignment.MIDDLE_RIGHT);
      text.focus();
   }

   public void addFilterButtonListener(ClickListener listener) {
      filterButton.addClickListener(listener);
   }

   public MealEventFilter getFilter() {
      return new MealEventFilter(text.getValue(), hadDinnerToday.getValue());
   }

   public void addCancelFilterButtonListener(ClickListener listener) {
      cancelFilterButton.addClickListener(listener);
   }

   public void cleanFields() {
      text.setValue("");
      hadDinnerToday.setValue(false);
   }
}