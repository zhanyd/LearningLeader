package p201604;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2017/1/1 0001.
 */
public class L006 {

    @Test
    public void example1(){
        List<String> myList = Arrays.asList("a1","a2","b1","c1","c2","c5");
        myList.stream().filter(s->s.startsWith("c"))
        .map(String::toUpperCase)
                .sorted().forEach(System.out::println);
    }


    @Test
    public void example2(){
        Stream.of("a1","a2","a3").findFirst().ifPresent(System.out::println);
    }

    @Test
    public void example3(){
        IntStream.range(1,4).forEach(System.out::println);
    }

    @Test
    public void example4(){
       Stream<Integer> lsit =Stream.of(Arrays.asList(1),
                Arrays.asList(2,3),
                Arrays.asList(4,5,6)
        ).flatMap(p->p.stream());

       System.out.println(lsit);
    }


    @Test
    public void example5(){
        List<String> strList = new  ArrayList<String>();
        strList.add("a");
        strList.add("b");
        strList.add("n");
        strList.add("g");
        strList.add("f");

        String[] strArray = strList.stream()
                .map(String::toUpperCase)
                .toArray(String[]::new);

        Arrays.stream(strArray).forEach(System.out::print);


        //System.out.println(Collectors.counting());
    }


}
