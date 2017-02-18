package p201605;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class NormalCounter extends AbstractMyCounter{

    private long value = 0;

    public void incr(){
        value++;
    }

    public long getCurValue(){
        return value;
    }
}
