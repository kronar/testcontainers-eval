import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import twitter4j.Twitter;

/**
 * Created by nikita on 24.05.16.
 */
public class TwitterReporter extends RunListener {

    @Override
    public void testFailure(Failure failure) throws Exception {
        Twitter twitter = TwitterProvider.get();
        twitter.updateStatus(failure.getDescription().getClassName());
    }
}
