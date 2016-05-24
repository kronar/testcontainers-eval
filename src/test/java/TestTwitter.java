import org.junit.Test;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by nikita on 24.05.16.
 */
public class TestTwitter {


    @Test
    public void test() throws TwitterException {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(Credentials.TwitterCredentials.OAUTH_CONSUMER_KEY)
                .setOAuthConsumerSecret(Credentials.TwitterCredentials.OAUTH_CONSUMER_SECRET)
                .setOAuthAccessToken(Credentials.TwitterCredentials.OAUTH_ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(Credentials.TwitterCredentials.OAUTH_ACCESS_TOKEN_SECRET);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        Status test = twitter.updateStatus("Test");


    }
}
