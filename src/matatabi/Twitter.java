package matatabi;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

/**
 *
 * @author Be4rJP
 */
public class Twitter {
    
    public static void Tweet(String mes){
        try{
            User user = Matatabi.twitter.verifyCredentials();
            Status status = Matatabi.twitter.updateStatus(mes);
        } catch (TwitterException ex) {
            System.out.println("!!!ツイートに失敗しました!!!");
        }
    }
}
