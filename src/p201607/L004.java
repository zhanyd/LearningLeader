package p201607;

import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Administrator on 2017/1/23 0023.
 */
public class L004 {

    public static void main(String[] args) throws InterruptedException{
        SynchronousQueue<Object> queue = new SynchronousQueue<>();


        List<Thread> threadListProduct = IntStream.range(0,5).mapToObj(t->{ return new Thread(() -> {

            while (true){
                if(!queue.offer(new Object())){
                    System.out.println(Thread.currentThread().getName() + "offer Failure");
                }else{
                    System.out.println(Thread.currentThread().getName() + "offer success");
                }
                try{
                    Thread.sleep(1000);
                }catch(Exception e){
                    e.printStackTrace();
                }

            }

        });}).collect(Collectors.toList());

        threadListProduct.forEach(t->t.start());

        List<Thread> threadListCustomer = IntStream.range(0,5).mapToObj(t->{ return new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " take begin");

            Object item = null;

            try {
                while (true) {
                    if((item = queue.poll()) != null) {
                        System.out.println(Thread.currentThread().getName() + " take " + item.toString() + " item");
                    }else{
                        System.out.println(Thread.currentThread().getName() + "take end");
                    }

                    try{
                        Thread.sleep(1000);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e){e.printStackTrace();}

        });}).collect(Collectors.toList());

        threadListCustomer.forEach(t->t.start());





    }
}
