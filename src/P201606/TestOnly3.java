package P201606;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestOnly3 {
    //static Object lock=new Object();
    static ArrayList<String> datas=new  ArrayList<String>();
    static ReadWriteLock lock = new ReentrantReadWriteLock();
    static Condition condition = lock.writeLock().newCondition();


    public static void main(String[] args) throws InterruptedException
    {
        List<Thread> threads= IntStream.range(1, 10)
                .mapToObj(i->{if(i%2==0) {return new MThread3("consumer "+i);}
                else return new NThread3("producer "+i);})
                .filter(t->{t.start();return true;})
                .collect(Collectors.toList());
        threads.forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {

            }
        });

    }
}

class MThread3 extends Thread {

    public MThread3(String string) {
        this.setName(string);
    }

    public void run() {

        while (true) {
            TestOnly3.lock.writeLock().lock();
            //if (TestOnly3.lock.writeLock().tryLock()) {
                try {
                    while (TestOnly3.datas.isEmpty()) {
                        System.out.println(Thread.currentThread().getName() + " into wait ,because empty ");
                        try {

                            //TestOnly3.lock.wait();
                            TestOnly3.condition.await();
                        } catch (InterruptedException e) {
                            break;

                        }
                        System.out.println(Thread.currentThread().getName() + " wait finished ");


                    }
                    if (TestOnly3.datas.isEmpty()) {

                        System.out.println("impossible empty !! " + Thread.currentThread().getName());
                        System.exit(-1);
                    }

                    TestOnly3.datas.forEach(s -> System.out.println("consumer " + s));
                    TestOnly3.datas.clear();
                    //TestOnly3.lock.notifyAll();
                    TestOnly3.condition.signalAll();

                } finally {
                    TestOnly3.lock.writeLock().unlock();
                }
            }
        //}
    }
}

class NThread3 extends Thread
{
    public NThread3(String string) {
        this.setName(string);
    }

    public void run() {

        while (true) {
            TestOnly3.lock.writeLock().lock();
            //if (TestOnly3.lock.writeLock().tryLock()) {
                try {
                    while (TestOnly3.datas.size() > 1) {
                        System.out.println(Thread.currentThread().getName() + " into wait,because full ");
                        try {
                            //TestOnly3.lock.wait();
                            TestOnly3.condition.await();
                        } catch (InterruptedException e) {
                            break;

                        }
                        System.out.println(Thread.currentThread().getName() + " wait finished ");
                    }
                    if (TestOnly3.datas.size() > 1) {

                        System.out.println("impossible full !! " + Thread.currentThread().getName());
                        System.exit(-1);
                    }

                    IntStream.range(0, 1).forEach(i -> TestOnly3.datas.add(i + " data"));
                    TestOnly3.datas.forEach(s -> System.out.println("producer " + s));
                    //TestOnly3.lock.notifyAll();
                    TestOnly3.condition.signalAll();
                } finally {
                    TestOnly3.lock.writeLock().unlock();
                }
            }
        //}

    }
}



