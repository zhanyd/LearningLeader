package p201801;

public class L201702 {

	public static void main(String[] args) {
		byte ba = 127;
		int bb = ba << 2;
		System.out.println(bb);
		
		int a = -1024;
		System.out.println(Integer.toBinaryString(a));
		System.out.println(Integer.toBinaryString(a >> 1));
		System.out.println(Integer.toBinaryString(a >>> 1));
		System.out.println(a >> 1);
		System.out.println(a >>> 1);

	}

}
