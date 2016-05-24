import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 */
public class Screenshoter {

    private final String token;

    private Screenshoter(String token) {
        this.token = token;
    }

    public static Screenshoter create() {
        return new Screenshoter(Credentials.YAD_TOKEN);
    }


    public void saveScreenshot(String name, byte[] bytes) {
        try (ByteArrayInputStream is = new ByteArrayInputStream(bytes)) {
            saveScreenhsot(name, is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveScreenhsot(String name, InputStream bytes) {
        try {
            YaDiskImageUploadHelper.uploadFile(name, bytes, token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
