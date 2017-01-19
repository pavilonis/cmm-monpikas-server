package lt.pavilonis.cmm.canteen.views.setting;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.InlineDateField;
import lt.pavilonis.cmm.canteen.domain.MealType;
import lt.pavilonis.cmm.canteen.views.component.EnumComboBox;
import lt.pavilonis.cmm.converter.LocalTimeToDateConverter;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MGridLayout;

import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;

public class MealFormView extends MGridLayout {

   private static final Converter<Date, LocalTime> TIME_CONVERTER = new LocalTimeToDateConverter();
   private final InlineDateField startTime = dateField("Periodo pradžia: ");
   private final InlineDateField endTime = dateField("Periodo pabaiga: ");

   private final MTextField price = new MTextField("Kaina").withNullRepresentation("");
   private final MTextField name = new MTextField("Pavadinimas").withNullRepresentation("");
   private final ComboBox type = new EnumComboBox<>(MealType.class);

   public MealFormView() {
      super(2, 3);
      add(name, type, startTime, endTime, price);
      setMargin(false);
   }

   private InlineDateField dateField(String caption) {
      InlineDateField dateField = new InlineDateField();
      dateField.setResolution(Resolution.MINUTE);
      dateField.setLocale(new Locale("lt", "LT"));
      dateField.addStyleName("time-only");
      dateField.setConverter(TIME_CONVERTER);
      dateField.setCaption(caption);
      return dateField;
   }
}
