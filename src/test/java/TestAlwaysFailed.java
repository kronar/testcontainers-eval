import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import util.Screenshoter;

/**
 * Created by nikita on 24.05.16.
 */
public class TestAlwaysFailed extends AbstractTest {
    private static final Logger LOGGER = Logger.getLogger(TestAlwaysFailed.class.getName());

    @Test
    public void test() {
        LOGGER.info("Starting");
        webDriver.get("https://google.com");

        Screenshoter.create().takeScreenshot(webDriver);
        Assert.assertEquals(webDriver.getTitle(), "Одноклассники");
    }
}
