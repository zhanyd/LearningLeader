package p201605;

/**
 * Created by Administrator on 2017/1/13 0013.
 */
public class L006 {

    public static void main(String[] args){
        String str = "a,b,c,,";
        String[] ary = str.split(","); //预期大于3，结果是3
        System.out.println(ary.length);
    }
}
