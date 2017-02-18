package p201602;

/**
 * Created by Administrator on 2016/12/11 0011.
 */
public class Test {

    private final static char[] HEX = "0123456789abcdef".toCharArray();
    public static void main(String[] args) throws Exception {
        String str = "中";
        String[] encoding = { "Unicode", "UnicodeBig", "UnicodeLittle", "UnicodeBigUnmarked",
                "UnicodeLittleUnmarked", "UTF-16", "UTF-16BE", "UTF-16LE","UTF-8","gbk" };
        for (int i = 0; i < encoding.length; i++) {
            System.out
                    .printf("%-22s %s%n", encoding[i], bytes2HexString(str.getBytes(encoding[i])));
        }
    }
    public static String bytes2HexString(byte[] bys) {
        char[] chs = new char[bys.length * 2 + bys.length - 1];
        for (int i = 0, offset = 0; i < bys.length; i++) {
            if (i > 0) {
                chs[offset++] = ' ';
            }
            chs[offset++] = HEX[bys[i] >> 4 & 0xf];
            chs[offset++] = HEX[bys[i] & 0xf];
        }
        return new String(chs);
    }
}
