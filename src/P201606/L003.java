package P201606;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
public class L003 {
    private static boolean stopRequested;
    public static void main(String[] args) throws InterruptedException{
        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while(!stopRequested){
                    i++;
                    //System.out.println(i);
                }
            }
        });

        backgroundThread.start();

        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;
    }
}
