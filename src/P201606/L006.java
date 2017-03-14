package P201606;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
public class L006 {
    private static volatile boolean stopRequested;
    public static void main(String[] args) throws InterruptedException{
        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {

                while(true) {
                    System.out.println("before wait...");
                    //if (stopRequested) {
                    while (stopRequested) {
                        try {
                            System.out.println(Thread.currentThread().getName() + " waitting...");
                            this.wait();
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }

                    System.out.println("after wait...");
                }
            }
        });

        Thread backgroundThread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                stopRequested = true;
                System.out.println("stopRequested = true");
            }
        });

        backgroundThread.start();

        backgroundThread2.start();

    }
}
