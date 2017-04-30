package lt.pavilonis.cmm.canteen.domain;

import lt.pavilonis.cmm.common.Identifiable;
import lt.pavilonis.cmm.common.converter.ADecimalFormat;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.Objects;

public final class Eating implements Identifiable<Long> {

   private static final DecimalFormat NUMBER_FORMAT = new ADecimalFormat();

   private Long id;

   @NotBlank
   private String name;

   @NotNull
   private EatingType type;

   @NotNull
   private BigDecimal price;

   @NotNull
   private LocalTime startTime;

   @NotNull
   private LocalTime endTime;

   public Eating() {
   }

   public Eating(Long id, String name, EatingType type, BigDecimal price, LocalTime startTime, LocalTime endTime) {
      this.id = id;
      this.name = name;
      this.type = type;
      this.price = price;
      this.startTime = startTime;
      this.endTime = endTime;
   }

   @Override
   public Long getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public EatingType getType() {
      return type;
   }

   public BigDecimal getPrice() {
      return price;
   }

   public LocalTime getStartTime() {
      return startTime;
   }

   public LocalTime getEndTime() {
      return endTime;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public void setEndTime(LocalTime endTime) {
      this.endTime = endTime;
   }

   public void setStartTime(LocalTime startTime) {
      this.startTime = startTime;
   }

   public void setPrice(BigDecimal price) {
      this.price = price;
   }

   public void setType(EatingType type) {
      this.type = type;
   }

   public void setName(String name) {
      this.name = name;
   }

   @Override
   public String toString() {
      return name + " - " + NUMBER_FORMAT.format(price);
   }

   @Override
   public int hashCode() {
      return Math.toIntExact(id);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof Eating) {
         Eating eating = (Eating) obj;
         return Objects.equals(eating.hashCode(), this.hashCode());
      }
      return false;
   }
}