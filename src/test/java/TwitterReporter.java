import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import twitter4j.Twitter;

/**
 */
public class TwitterReporter extends RunListener {

    @Override
    public void testFailure(Failure failure) throws Exception {
        Twitter twitter = TwitterProvider.get();
        twitter.updateStatus("@papaminos "+failure.getDescription().getClassName());
    }
}
