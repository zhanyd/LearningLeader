package p201605;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public abstract class AbstractMyCounter {

    private int times = 1000_000;

    public abstract void incr();

    public abstract long getCurValue();

    public void doCounte(AbstractMyCounter counter){
        for(int i=0; i<times; i++){
            counter.incr();
        }
    }


    public long getCount(AbstractMyCounter counter) throws InterruptedException{

        long beginTime = System.currentTimeMillis();
        Thread one = new Thread(()->doCounte(counter));
        Thread two = new Thread(()->doCounte(counter));

        one.start();
        two.start();

        one.join();
        two.join();

        System.out.print("cost :" + (System.currentTimeMillis() - beginTime) + "  ");
        return getCurValue();
    }


}
