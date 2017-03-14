package p201607;

import com.sun.xml.internal.bind.v2.model.annotation.RuntimeAnnotationReader;

import java.util.concurrent.*;

/**
 * Created by Administrator on 2017/1/24 0024.
 */
public class L007 {

    public static void main(String[] args){

        BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<Runnable>(20);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(3,5,50, TimeUnit.SECONDS,bqueue);

        Runnable t1 = new MyThread();
        Runnable t2 = new MyThread();
        Runnable t3 = new MyThread();
        Runnable t4 = new MyThread();
        Runnable t5 = new MyThread();
        Runnable t6 = new MyThread();
        Runnable t7 = new MyThread();

        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);
        pool.execute(t5);
        pool.execute(t6);
        pool.execute(t7);

        pool.shutdown();

    }

    static class MyThread implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
