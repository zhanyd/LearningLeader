package p201605;

import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class L001 {

  /*  volatile int x=0;
    volatile int y=0;
    volatile int a=0;
    volatile int b=0;*/


    int x=0;
    int y=0;
    int a=0;
    int b=0;

    public static void doTest(int i){
        L001 l001 = new L001();
        Thread one = new Thread(()->{l001.a = 1;l001.x=l001.b;/*System.out.println("thread one " + i);*/});
        Thread other = new Thread(()->{l001.b = 1;l001.y=l001.a;/*System.out.println("thread other " + i);*/});
        one.start();
        other.start();
        try{
            one.join();
            other.join();
            //Thread.sleep(100);
        }catch(InterruptedException e){}
        System.out.println(/*Thread.currentThread().getName() + */" run case : " + i + "(x=" + l001.x + ",y=" + l001.y + ",a=" + l001.a + ",b=" + l001.b + ")");

    }
    public static void main(String[] args){
        IntStream.range(0,100).forEach(L001::doTest);

    }
}
