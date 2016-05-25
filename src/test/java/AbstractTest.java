import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.remote.RemoteWebDriver;
import util.MyRule;

/**
 * Created by nikita on 24.05.16.
 */
public abstract class AbstractTest {

    @Rule
    public MyRule containerWithReporting = new MyRule();

    protected RemoteWebDriver webDriver;

    @Before
    public void prepareBrowser() {
        webDriver = containerWithReporting.getWebDriver();
    }

}
