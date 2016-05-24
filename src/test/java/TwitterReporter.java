import com.google.common.base.Throwables;
import java.util.logging.Logger;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 */
public class TwitterReporter extends RunListener {

    private static final Logger LOGGER = Logger.getLogger(TwitterProvider.class.getName());

    public TwitterReporter() {
        System.out.println("Listener created");
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        System.out.println("Test failure "+failure.toString());
        try {
            Twitter twitter = TwitterProvider.get();
            Status status = twitter.updateStatus("@PapaMinos " + failure.getDescription().getClassName());
            System.out.println("Status "+status);

        } catch (TwitterException e) {
            System.out.println("Listener error "+Throwables.getStackTraceAsString(e));

        }
    }
}
