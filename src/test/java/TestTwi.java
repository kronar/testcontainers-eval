import java.util.Date;
import org.junit.Test;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * Created by nikita on 24.05.16.
 */
public class TestTwi {

    @Test
    public void test() throws TwitterException {
        Status status = TwitterProvider.get().updateStatus("@PapaMinos "+new Date().toString());
        System.out.println(status);
    }
}
