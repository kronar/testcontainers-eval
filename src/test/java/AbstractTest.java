import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import util.MyRule;

/**
 * Created by nikita on 24.05.16.
 */
public abstract class AbstractTest {


    private BrowserWebDriverContainer chrome = new BrowserWebDriverContainer()
            .withDesiredCapabilities(DesiredCapabilities.chrome());
    private MyRule twiReporing = new MyRule(chrome.getWebDriver());

    @Rule
    public TestRule ruleChain = RuleChain.outerRule(chrome).around(twiReporing);

    protected RemoteWebDriver webDriver;

    @Before
    public void prepareBrowser() {
        webDriver = chrome.getWebDriver();
    }

}
