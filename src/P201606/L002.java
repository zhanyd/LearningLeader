package P201606;

/**
 * Created by Administrator on 2017/1/15 0015.
 */
public class L002 {

    public static void main(String[] args) {
        TxtThread2 tt = new TxtThread2();
        new Thread(tt).start();
      /*  new Thread(tt).start();
        new Thread(tt).start();
        new Thread(tt).start();*/
    }
}

class TxtThread2 implements Runnable {

    String str = new String();
    int num = 10;
    public void run(){
        synchronized (this) {
            // int num = 10;
            while (num > 0) {
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.getMessage();
                }
                System.out.println(Thread.currentThread().getName()
                        + "this is " + num--);

                if (num < 5) {
                    try {
                        this.wait();
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        }
    }
}
