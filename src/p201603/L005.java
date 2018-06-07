package p201603;

import java.util.ArrayList;
import java.util.*;

/**
 * Created by Administrator on 2016/12/25 0025.
 */
public class L005 {

    public static void main(String[] args){
        ArrayList<String> alist = new ArrayList<String>();
        alist.add("a");
        alist.add("b");
        alist.add("f");
        alist.add("c");
        alist.add("g");

        String[] strArry = alist.toArray(new String[alist.size()]);
        for (String str:strArry) {
            System.out.println(str);
        }

        List<String> cList = Arrays.asList(strArry);
        for(String str2 : cList){
            System.out.println(str2);
        }



    }
}
