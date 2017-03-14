package p201602;

/**
 * Created by Administrator on 2016/12/11 0011.
 */
public class L201602 {

    public static void main(String[] args) throws Exception{
        String s = "中国";
        byte[] utf8 = s.getBytes("utf-8");
        byte[] utf16 = s.getBytes("utf-16");
        byte[] gbk =  s.getBytes("gbk");
        byte[] iso = s.getBytes("iso-8859-1");
        System.out.println("utf-8 : " + utf8.length);
        System.out.println("utf-16 : " + utf16.length);
        System.out.println("gbk : " + gbk.length);
        System.out.println("iso-8859-1 : " + iso.length);


        String etrans = new String(utf8,"gbk");
        System.out.println("error trans : " + etrans);

        String rtrans = new String(utf8,"utf-8");
        System.out.println("right trans : " + rtrans);


        String egbk = new String(gbk,"utf-8");
        System.out.println("error egbk : " + egbk);

        String rgbk = new String(gbk,"gbk");
        System.out.println("right rgbk : " + rgbk);



    }
}
