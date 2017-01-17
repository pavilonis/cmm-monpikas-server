package lt.pavilonis.cmm.converter;

import com.vaadin.data.util.converter.Converter;

import java.util.Locale;
import java.util.Optional;

public class OptionalBooleanCellConverter implements Converter<String, Optional<?>> {
   @Override
   public Optional<?> convertToModel(String value, Class<? extends Optional<?>> targetType, Locale locale) throws ConversionException {
      return null;
   }

   @Override
   public String convertToPresentation(Optional<?> value, Class<? extends String> targetType, Locale locale) throws ConversionException {
      return value.isPresent() ? "✔" : null;
   }

   @Override
   public Class<Optional<?>> getModelType() {
      return null;
   }

   @Override
   public Class<String> getPresentationType() {
      return String.class;
   }
}