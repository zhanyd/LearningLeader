package p201605;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
public class L007 implements Runnable{
    private int ticket = 5;  //5张票

    public void run() {
        for (int i=0; i<=20; i++) {
            if (this.ticket > 0) {
                System.out.println(Thread.currentThread().getName()+ "正在卖票"+this.ticket--);
            }
        }
    }



    public static void main(String [] args) {
        L007 my = new L007();
        L007 my2 = new L007();
        L007 my3 = new L007();
       /* new Thread(my, "1号窗口").start();
        new Thread(my2, "2号窗口").start();
        new Thread(my3, "3号窗口").start();*/

        new Thread(my, "1号窗口").start();
        new Thread(my, "2号窗口").start();
        new Thread(my, "3号窗口").start();
    }

}
