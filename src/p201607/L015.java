package p201607;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/2/18 0018.
 */
public class L015 {

    public static void main(String[] args){
        String[] str = {"a","b","c"};
        System.out.println(str);
        System.out.println(str.toString());
        System.out.println(String.valueOf(str));
        Arrays.stream(str).forEach(f->System.out.print(" " + f));
    }
}
