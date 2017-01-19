package lt.pavilonis.cmm.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lt.pavilonis.cmm.common.Identifiable;
import org.apache.commons.lang3.NotImplementedException;

import java.time.LocalDateTime;

public class KeyRepresentation implements Identifiable<String> {
   private final int keyNumber;
   private final LocalDateTime dateTime;
   private final UserRepresentation user;
   private final ScannerRepresentation scanner;
   private final KeyAction keyAction;

   public KeyRepresentation(@JsonProperty("keyNumber") int keyNumber,
//                            @JsonSerialize(using = IsoLocalDateTimeSerializer.class)
                            @JsonProperty("dateTime") LocalDateTime dateTime,
                            @JsonProperty("user") UserRepresentation user,
                            @JsonProperty("scanner") ScannerRepresentation scanner,
                            @JsonProperty("keyAction") KeyAction keyAction) {

      this.keyNumber = keyNumber;
      this.dateTime = dateTime;
      this.user = user;
      this.scanner = scanner;
      this.keyAction = keyAction;
   }

   public int getKeyNumber() {
      return keyNumber;
   }

   public LocalDateTime getDateTime() {
      return dateTime;
   }

   public UserRepresentation getUser() {
      return user;
   }

   public ScannerRepresentation getScanner() {
      return scanner;
   }

   public KeyAction getKeyAction() {
      return keyAction;
   }

   @Override
   public String getId() {
      throw new NotImplementedException("not needed");
   }
}
