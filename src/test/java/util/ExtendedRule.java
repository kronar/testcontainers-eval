package util;

import com.google.common.base.Throwables;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;
import org.junit.runner.Description;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 */
public class ExtendedRule extends BrowserWebDriverContainer {
    private static final Logger LOGGER = Logger.getLogger(ExtendedRule.class.getName());
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("ddMMyyyy-HHmmssSS");

    @Override
    protected void failed(Throwable e, Description description) {
        RemoteWebDriver webDriver = super.getWebDriver();

        try {
            Twitter twitter = TwitterProvider.get();
            String travisJobNumber = Optional.ofNullable(System.getenv("TRAVIS_JOB_NUMBER")).orElse(SIMPLE_DATE_FORMAT.format(new Date()));
            String link = null;
            if (webDriver != null) {
                link = Screenshoter.create().saveScreenshot(description.getTestClass().getName() + "_failure_screenshot", webDriver);
                LOGGER.info("Failure Screenshot link " + link);
            } else {
                LOGGER.severe("Null webdriver");
                return;
            }

            if (Screenshoter.NULL_OBJ.equals(link)) {
                LOGGER.severe("Null object screenshot");
//                Status status = twitter.updateStatus("@PapaMinos " + description.getTestClass().getName());
            } else {
//                Status status = twitter.updateStatus("@PapaMinos " + description.getTestClass().getName());
//                status = twitter.updateStatus("@PapaMinos Screenshot" + link);

                String tweet = "@PapaMinos failure screenshot at " + travisJobNumber;
                LOGGER.info(tweet);
                StatusUpdate statusUpdate = new StatusUpdate(tweet);
                statusUpdate.setMedia("screenshot" + travisJobNumber + ".png", Screenshoter.create().getScreenshotAsStream(webDriver));
                twitter.updateStatus(statusUpdate);
            }

        } catch (TwitterException twe) {
            System.out.println("Listener error " + Throwables.getStackTraceAsString(twe));

        } finally {
            super.failed(e, description);
        }

    }
}
