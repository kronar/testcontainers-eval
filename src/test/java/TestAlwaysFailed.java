import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import util.Screenshoter;

/**
 * Created by nikita on 24.05.16.
 */
public class TestAlwaysFailed {
    private static final Logger LOGGER = Logger.getLogger(TestAlwaysFailed.class.getName());

    @Rule
    public BrowserWebDriverContainer chrome = new BrowserWebDriverContainer()
            .withDesiredCapabilities(DesiredCapabilities.chrome());

    @Test
    public void test() {
        LOGGER.info("Starting");
        RemoteWebDriver webDriver = chrome.getWebDriver();
        webDriver.get("https://google.com");

        Assert.assertEquals(webDriver.getTitle(), "Одноклассники");
        Screenshoter.create().takeScreenshot(webDriver);
    }
}
