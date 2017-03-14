package p201607;

import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Administrator on 2017/1/22 0022.
 */
public class L002 {

    public static void main(String[] args) throws InterruptedException{
        SynchronousQueue<Object> queue = new SynchronousQueue<>();

        List<Thread> threadListCustomer = IntStream.range(0,5).mapToObj(t->{ return new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " take begin");

            Object item = null;

            try {
                //取数据的时候阻塞等待
                if ((item = queue.take()) != null) {
                    System.out.println(Thread.currentThread().getName() + " take " + item.toString() + " item");
                }
            }
            catch (Exception e){e.printStackTrace();}
            System.out.println(Thread.currentThread().getName() + "take end");
        });}).collect(Collectors.toList());

        threadListCustomer.forEach(t->t.start());



        List<Thread> threadListProduct = IntStream.range(0,5).mapToObj(t->{ return new Thread(() -> {

            while (!queue.offer(new Object())){
                System.out.println(Thread.currentThread().getName() + "offer Failure");
            }
            System.out.println(Thread.currentThread().getName() + "offer success");
        });}).collect(Collectors.toList());

        threadListProduct.forEach(t->t.start());

    }


//    public static class SQThread extends Thread {
//        private SynchronousQueue<Object> queue;
//        int mode;
//
//        SQThread(SynchronousQueue queue, int mode) {
//            this.queue = queue;
//            this.mode = mode;
//
//        }
//
//        public void run() {
//            Object item = null;
//            try {
//                System.out.println(Thread.currentThread().getId());
//                if (mode == 1) {
//                    while ((item = queue.poll()) != null) {
//                        System.out.println(item.toString() + " item");
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            System.out.println("take end");
//        }
//    }

}
