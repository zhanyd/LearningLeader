package p201604;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2017/1/1 0001.
 */
public class L004 {
    public static void main(String[] args){
        List list = Stream.of(1,2,3,4,5,6,7,8,9,10)
                .filter(x->{
                    return x%2 == 0;
                })
                .map(x->{
                    return x*x;
                })
                .collect(Collectors.toList());

        System.out.println(list);

        Supplier ss;
    }
}
