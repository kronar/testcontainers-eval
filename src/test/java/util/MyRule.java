package util;

import com.google.common.base.Throwables;
import org.junit.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Created by nikita on 25.05.16.
 */
public class MyRule extends TestWatcher {


    private BrowserWebDriverContainer container;
    private RemoteWebDriver webDriver;

    public BrowserWebDriverContainer getContainer() {
        return container;
    }

    public RemoteWebDriver getWebDriver() {
        return webDriver;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        container = new BrowserWebDriverContainer().withDesiredCapabilities(DesiredCapabilities.chrome());
        webDriver = container.getWebDriver();
        return super.apply(base, description);
    }

    @Override
    protected void succeeded(Description description) {
        super.succeeded(description);
    }

    @Override
    protected void failed(Throwable e, Description description) {
        try {
            Twitter twitter = TwitterProvider.get();
            String link = null;
            if (webDriver != null) {
                link = Screenshoter.create().saveScreenshot(description.getTestClass().getName() + "_failure_screenshot", webDriver);
            }

            if (link.equals(Screenshoter.NULL_OBJ)) {
                Status status = twitter.updateStatus("@PapaMinos " + description.getTestClass().getName());
            } else {
                Status status = twitter.updateStatus("@PapaMinos " + description.getTestClass().getName());
//                status = twitter.updateStatus("@PapaMinos Screenshot" + link);
                StatusUpdate statusUpdate = new StatusUpdate("@PapaMinos failure screenshot");
                statusUpdate.setMedia("screenshot.png", Screenshoter.create().getScreenshotAsStream(webDriver));
                twitter.updateStatus(statusUpdate);
            }

        } catch (TwitterException twe) {
            System.out.println("Listener error " + Throwables.getStackTraceAsString(twe));

        }
    }

    @Override
    protected void skipped(AssumptionViolatedException e, Description description) {
        super.skipped(e, description);
    }

    @Override
    protected void skipped(org.junit.internal.AssumptionViolatedException e, Description description) {
        super.skipped(e, description);
    }

    @Override
    protected void starting(Description description) {
        super.starting(description);
    }

    @Override
    protected void finished(Description description) {

    }
}
