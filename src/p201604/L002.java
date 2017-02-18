package p201604;

import java.util.stream.Stream;

/**
 * Created by Administrator on 2016/12/31 0031.
 */
public class L002 {
    public static void main(String[] args){
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return true;
                })
                .forEach(s -> System.out.println("forEach: " + s));
    }
}
