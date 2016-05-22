import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;

/**
 * Created by nikita on 22.05.16.
 */
public class TestGoogle {

    private static final Logger LOGGER = Logger.getLogger(TestGoogle.class.getName());
    @Rule
    public BrowserWebDriverContainer chrome = new BrowserWebDriverContainer()
            .withDesiredCapabilities(DesiredCapabilities.chrome());

    @Test
    public void test() {
        LOGGER.info("Starting");
        RemoteWebDriver webDriver = chrome.getWebDriver();
        webDriver.get("https://ok.ru");

        Assert.assertTrue(webDriver.findElements(By.id("field_email")).size() > 0);

        TakesScreenshot sc = (TakesScreenshot) webDriver;
        byte[] screenshotAs = sc.getScreenshotAs(OutputType.BYTES);
        LOGGER.info("Screenshot taken" + screenshotAs.length);
    }
}
