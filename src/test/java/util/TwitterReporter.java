package util;

import com.google.common.base.Throwables;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.openqa.selenium.WebDriver;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 */
@RunListener.ThreadSafe
public class TwitterReporter extends RunListener {


    public TwitterReporter() {
        System.out.println("Listener created");
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        System.out.println("Test failure " + failure.toString());
        try {
            Twitter twitter = TwitterProvider.get();
            WebDriver driver = DriverRegistry.getDriver();
            String link = null;
            if (driver != null) {
                link = Screenshoter.create().saveScreenshot(failure.getDescription().getClassName() + "_failure_screenshot", driver);
            }

            if (link.equals(Screenshoter.NULL_OBJ)) {
                Status status = twitter.updateStatus("@PapaMinos " + failure.getDescription().getClassName());
            } else {
                Status status = twitter.updateStatus("@PapaMinos " + failure.getDescription().getClassName());
//                status = twitter.updateStatus("@PapaMinos Screenshot" + link);
                StatusUpdate statusUpdate = new StatusUpdate("@PapaMinos failure screenshot");
                statusUpdate.setMedia("screenshot.png", Screenshoter.create().getScreenshotAsStream(driver));
                twitter.updateStatus(statusUpdate);
            }

        } catch (TwitterException e) {
            System.out.println("Listener error " + Throwables.getStackTraceAsString(e));

        }
    }
}
