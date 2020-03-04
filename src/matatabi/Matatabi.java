
package matatabi;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import static matatabi.Twitter.Tweet;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

/**
 *
 * @author Be4rJP
 * @author iNMLOID
 */

public class Matatabi {
    
    public static int PLAYER_COUNT = 0;
    public static int PERIOD = 1800000; // 1800000[ms]
    public static twitter4j.Twitter twitter = new TwitterFactory().getInstance();
    public static boolean IS_ONLINE = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        
        System.out.println("Starting Matatabi_Bot...");
        //時刻の取得
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd H:mm");
        String str = sdf.format(timestamp);
        //起動時にツイートする
        Tweet("Matatabi_Botが起動しました (" + str + ")");
        
        //PERIODの間隔でオンラインプレイヤー数をチェック
        Timer timer = new Timer(); 
        TimerTask task = new TimerTask() {
            
            public void run() {
                
                try{
                    //時刻の取得
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd H:mm");
                    String time = sdf.format(timestamp);
                
                    MineStat ms = new MineStat("play.minecraft.jp", 25565);
                    if(Integer.valueOf(ms.getCurrentPlayers()) > PLAYER_COUNT || Integer.valueOf(ms.getCurrentPlayers()) >= 4) //前にチェックした時より増えていればツイートする
                        Tweet("現在のプレイヤー数は " + ms.getCurrentPlayers() + " 人です [" + String.valueOf(ms.getLatency()) + "ms]\n(" + time + ")");
                    PLAYER_COUNT = Integer.valueOf(ms.getCurrentPlayers());
                
                    if(!IS_ONLINE){
                        IS_ONLINE = ms.isServerUp();
                        if(IS_ONLINE)
                            Tweet("JPMCPVPがオンラインになりました");
                    }
                    IS_ONLINE = ms.isServerUp();
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000, PERIOD);

        System.out.println("Matatabi_Botが起動しました");
        System.out.println("終了するには[stop]コマンドを使用してください");
        
        Scanner scan = new Scanner(System.in);
        
        if(scan.next().equals("stop"))
            timer.cancel();
 
    }
    
}
