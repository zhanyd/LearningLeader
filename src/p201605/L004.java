package p201605;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class L004 {

    public static void main(String[] args) throws InterruptedException{
        AbstractMyCounter normalCounter = new NormalCounter();
        AbstractMyCounter volatileCounter = new VolatileCounter();
        AbstractMyCounter synchronizedCounter = new SynchronizedCounter();
        AbstractMyCounter atomicLongCounter = new AtomicLongCounter();
        AbstractMyCounter longAdderCounter = new LongAdderCounter();

        System.out.println("normalCounter value = " + normalCounter.getCount(normalCounter));
        System.out.println("volatileCounter value = " + volatileCounter.getCount(volatileCounter));
        System.out.println("synchronizedCounter value = " + synchronizedCounter.getCount(synchronizedCounter));
        System.out.println("atomicLongCounter value = " + atomicLongCounter.getCount(atomicLongCounter));
        System.out.println("longAdderCounter value = " + longAdderCounter.getCount(longAdderCounter));

    }




}
