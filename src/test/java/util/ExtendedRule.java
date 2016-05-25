package util;

import com.google.common.base.Throwables;
import java.util.Map;
import java.util.logging.Logger;
import org.junit.runner.Description;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 */
public class ExtendedRule extends BrowserWebDriverContainer {
    private static final Logger LOGGER = Logger.getLogger(ExtendedRule.class.getName());

    @Override
    protected void failed(Throwable e, Description description) {
        RemoteWebDriver webDriver = super.getWebDriver();
        Map<String, String> getenv = System.getenv();
        for (String s : getenv.keySet()) {
//            if (s.startsWith("TRAVIS")) {
            LOGGER.info("TRAVIS ENV VAR " + s + "=" + getenv.get(s));
//            }
        }

        try {
            Twitter twitter = TwitterProvider.get();
            String link = null;
            if (webDriver != null) {
                link = Screenshoter.create().saveScreenshot(description.getTestClass().getName() + "_failure_screenshot", webDriver);
            }

            if (Screenshoter.NULL_OBJ.equals(link)) {
                Status status = twitter.updateStatus("@PapaMinos " + description.getTestClass().getName());
            } else {
                Status status = twitter.updateStatus("@PapaMinos " + description.getTestClass().getName());
//                status = twitter.updateStatus("@PapaMinos Screenshot" + link);

                StatusUpdate statusUpdate = new StatusUpdate("@PapaMinos failure screenshot at " + System.getenv("TRAVIS_BUILD_NUMBER"));
                statusUpdate.setMedia("screenshot.png", Screenshoter.create().getScreenshotAsStream(webDriver));
                twitter.updateStatus(statusUpdate);
            }

        } catch (TwitterException twe) {
            System.out.println("Listener error " + Throwables.getStackTraceAsString(twe));

        } finally {
            super.failed(e, description);
        }

    }
}
