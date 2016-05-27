import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import util.Screenshoter;

/**
 * 
 */
public class TestOkRu extends AbstractTest {

    private static final Logger LOGGER = Logger.getLogger(TestOkRu.class.getName());
    private static final By FIELD_EMAIL = By.id("field_email");


    @Test
    public void test() {
        LOGGER.info("Starting");

        webDriver.get("https://ok.ru");

        Screenshoter.create().takeScreenshot(webDriver);
        Assert.assertTrue(webDriver.findElements(FIELD_EMAIL).size() > 0);
    }
}
