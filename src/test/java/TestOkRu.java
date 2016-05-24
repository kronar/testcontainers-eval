import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;

/**
 * Created by nikita on 22.05.16.
 */
public class TestOkRu {

    private static final Logger LOGGER = Logger.getLogger(TestOkRu.class.getName());
    private static final By FIELD_EMAIL = By.id("field_email");
    @Rule
    public BrowserWebDriverContainer chrome = new BrowserWebDriverContainer()
            .withDesiredCapabilities(DesiredCapabilities.chrome());

    @Test
    public void test() {
        LOGGER.info("Starting");
        RemoteWebDriver webDriver = chrome.getWebDriver();
        webDriver.get("https://ok.ru");

        Assert.assertTrue(webDriver.findElements(FIELD_EMAIL).size() > 0);
        Screenshoter.create().takeScreenshot(webDriver);
    }
}
