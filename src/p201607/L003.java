package p201607;

import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Administrator on 2017/1/23 0023.
 */
public class L003 {
    public static void main(String[] args) throws InterruptedException{
        SynchronousQueue<Object> queue = new SynchronousQueue<>();


        List<Thread> threadListProduct = IntStream.range(0,5).mapToObj(t->{ return new Thread(() -> {
            try {
                //放数据的时候阻塞等待
                queue.put(new Object());
            }catch(InterruptedException e){e.printStackTrace();}
            System.out.println(Thread.currentThread().getName() + "offer success");
        });}).collect(Collectors.toList());

        threadListProduct.forEach(t->t.start());

        List<Thread> threadListCustomer = IntStream.range(0,5).mapToObj(t->{ return new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " take begin");

            Object item = null;

            try {
                while ((item = queue.poll()) != null) {
                    System.out.println(Thread.currentThread().getName() + " take " + item.toString() + " item");
                }
            }
            catch (Exception e){e.printStackTrace();}
            System.out.println(Thread.currentThread().getName() + "take end");
        });}).collect(Collectors.toList());

        threadListCustomer.forEach(t->t.start());





    }
}
