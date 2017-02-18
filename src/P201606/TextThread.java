package P201606;

/**
 * Created by Administrator on 2017/1/14 0014.
 */
public class TextThread {
    public static void main(String[] args) {
        TxtThread tt = new TxtThread();
        new Thread(tt).start();
        new Thread(tt).start();
        new Thread(tt).start();
        new Thread(tt).start();
    }
}

class TxtThread implements Runnable {

    String str = new String();
    int num = 10;
    public void run() {
        //synchronized (new TxtThread()) {
        synchronized (this) {
        //synchronized (TxtThread.class) {
           // int num = 10;
            while (num > 0) {

                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.getMessage();
                }
                System.out.println(Thread.currentThread().getName()
                        + "this is " + num--);
            }
        }
    }
}
