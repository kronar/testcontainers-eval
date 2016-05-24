import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;

/**
 * Created by nikita on 24.05.16.
 */
public abstract class AbstractTest {
    @Rule
    public BrowserWebDriverContainer chrome = new BrowserWebDriverContainer()
            .withDesiredCapabilities(DesiredCapabilities.chrome());
    protected RemoteWebDriver webDriver;

    @Before
    public void prepareBrowser() {
        webDriver = chrome.getWebDriver();
    }
}
