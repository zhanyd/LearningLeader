package p201607;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/1/24 0024.
 */
public class L005 {

    public static void main(String[] args){
//        ExecutorService executorService= Executors.newFixedThreadPool(5);
        ExecutorService executorService= Executors.newCachedThreadPool();
//        ExecutorService executorService= Executors.newSingleThreadExecutor();

        for(int i = 0;i < 5; i++){
            executorService.execute(new TestRunnable());
            System.out.println("******* a" + i + "*********");
        }
        executorService.shutdown();
    }

    static class TestRunnable implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "线程被调用了。");
        }
    }


}
