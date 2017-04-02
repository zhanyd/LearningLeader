package p201607;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2017/1/24 0024.
 */
public class L006 {
    public static void main(String[] args){
//        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
        List<Future<String>> resultList = new ArrayList<Future<String>>();

        for(int i = 0; i < 10; i++){
            Future<String> future = executorService.submit(new TaskWithResult(i));
            resultList.add(future);
        }

        for(Future<String> future : resultList){
            try{
                //while (!future.isDone());
                System.out.println(future.get());
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                executorService.shutdown();
            }
        }

    }


    static class TaskWithResult implements Callable<String>{
        private int id;

        public TaskWithResult(int id){
            this.id = id;
        }

        public String call() throws Exception{
            System.out.println("call() 方法被调用。。。" + Thread.currentThread().getName());
            return "call() 方法的返回结果 ：" + id + " " + Thread.currentThread().getName();
        }
    }
}
