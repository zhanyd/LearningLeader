package p201604;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2017/1/1 0001.
 */
public class L005 {

    public static void main(String[] args){
        Stream<Long> natural = Stream.generate(new NaturalSupplier());
        natural.map(p->{
            return p;
        }).limit(100).forEach(System.out::println);
    }

    static class NaturalSupplier implements Supplier<Long>{
        long value = 0;
        public Long get(){
            return value++;
        }
    }
}
