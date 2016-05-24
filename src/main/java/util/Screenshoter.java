package util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 */
public class Screenshoter {

    private static final Logger LOGGER = Logger.getLogger(Screenshoter.class.getName());
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("ddMMyyyy_HHmmssSS");
    private static final String PNG = ".png";
    public static final String NULL_OBJ = "failedToSaveScreenshot";
    private final String token;


    private Screenshoter(String token) {
        this.token = token;
    }

    public static Screenshoter create() {
        return new Screenshoter(Credentials.YAD_TOKEN);
    }


    public String saveScreenshot(String name, InputStream bytes) {
        try {
            String s = YaDiskImageUploadHelper.uploadFile(name, bytes, token);
            LOGGER.info("Screenshot saved " + s);
            return s;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }


    public String saveScreenshot(String name, WebDriver driver) {
        try {
            InputStream screenshotAsStream = getScreenshotAsStream(driver);
            String s = YaDiskImageUploadHelper.uploadFile(name, screenshotAsStream, token);
            LOGGER.info("Screenshot saved " + s);
            return s;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    public InputStream getScreenshotAsStream(WebDriver webDriver){
        TakesScreenshot sc = (TakesScreenshot) webDriver;
        byte[] screenshotAs = sc.getScreenshotAs(OutputType.BYTES);
        ByteArrayInputStream bytes = new ByteArrayInputStream(screenshotAs);
        return bytes;
    }

    public void takeScreenshot(WebDriver webDriver) {
        TakesScreenshot sc = (TakesScreenshot) webDriver;
        byte[] screenshotAs = sc.getScreenshotAs(OutputType.BYTES);
        try (ByteArrayInputStream bytes = new ByteArrayInputStream(screenshotAs)) {
            saveScreenshot("screen_"+FORMAT.format(new Date()) + PNG, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
