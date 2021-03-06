package lt.pavilonis.cmm.school.user.form;

import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Upload;
import lt.pavilonis.cmm.common.util.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Hex;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.function.BiConsumer;

public class UserFormViewImageUploader implements Upload.Receiver, Upload.SucceededListener {

   private final Logger logger = LoggerFactory.getLogger(getClass());
   private final BiConsumer<Resource, String> imageResourceConsumer;
   private ByteArrayOutputStream baos;
   private byte[] scaledImageBytes;

   public UserFormViewImageUploader(BiConsumer<Resource, String> imageResourceConsumer) {
      this.imageResourceConsumer = imageResourceConsumer;
   }

   @Override
   public OutputStream receiveUpload(String filename, String mimeType) {
      return baos = new ByteArrayOutputStream();
   }

   @Override
   public void uploadSucceeded(Upload.SucceededEvent event) {
      byte[] bytes = baos.toByteArray();
      scaledImageBytes = ImageUtils.scale(bytes, 500, 500);

      var imageResource = new StreamResource(() -> new ByteArrayInputStream(scaledImageBytes), "img.png");
      String base16ImageString = new String(Hex.encode(bytes));

      imageResourceConsumer.accept(imageResource, base16ImageString);
      close(baos);
   }

   private void close(Closeable closeable) {
      if (closeable != null) {
         try {
            closeable.close();
         } catch (final IOException e) {
            logger.error("Could not close stream", e);
         }
      }
   }

   public byte[] getScaledImageBytes() {
      return scaledImageBytes;
   }
}
