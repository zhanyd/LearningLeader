package P201606;

/**
 * Created by Administrator on 2017/1/14 0014.
 */
public class L001 {

    public static void main(String[] args) throws InterruptedException{
        TestThread testThread1 = new TestThread();
        TestThread testThread2 = new TestThread();
        TestThread testThread3 = new TestThread();

        TestRunable testRunable1 = new TestRunable();
        TestRunable testRunable2 = new TestRunable();
        TestRunable testRunable3 = new TestRunable();
        Thread runable1 = new Thread(testRunable1);
        Thread runable2 = new Thread(testRunable2);
        Thread runable3 = new Thread(testRunable3);

        testThread1.start();
        testThread2.start();
        testThread3.start();

        runable1.start();
        runable2.start();
        runable3.start();

      /*  testThread1.join();
        testThread2.join();
        testThread3.join();*/

    }

    static class TestThread extends Thread {

        public void run() {
            for(int i = 0; i < 30; i++){
                try {
                    //Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " TestThread running" + i);
            }

        }
    }

    static class TestRunable implements Runnable{

        public void run(){
            for(int i = 0; i < 30; i++){
                try {
                    //Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " TestRunable running" + i);
            }

        }
    }


}
