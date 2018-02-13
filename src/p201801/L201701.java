package p201801;

import java.math.BigDecimal;
import java.util.Comparator;

public class L201701 {

	public static void main(String[] args) {
		char hex[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
		
		byte b = (byte)0xf1;
		System.out.println("(b>>4&0x0f) = " + (b>>4&0x0f));
		System.out.println("(b&0x0f) = " + (b&0x0f));
		System.out.println("b=0x" + hex[(b>>4&0x0f)] + hex[b&0x0f]);
		
		byte a = (byte)0xf0;
		System.out.println(a);
		int b2 = 0xff&a;
		System.out.println(b2);
		
		int x = (int)1023.99999999999999999999;
		System.out.println(x);
		
		BigDecimal bg = new BigDecimal(0.1);
		System.out.println(bg);
		BigDecimal bg2 = new BigDecimal("0.1");
		System.out.println(bg2);
		
		BigDecimal bg3 = new BigDecimal("0.10");
		System.out.println(bg2.compareTo(bg3));
		System.out.println(bg2.equals(bg3));
		
		BigDecimal bg4 = new BigDecimal("0.1");
		System.out.println(bg2.compareTo(bg4));
		System.out.println(bg2.equals(bg4));
		
		int[] ar = new int[]{};
		System.out.println(ar.getClass().getName());
		Object[] br = new Object[]{};
		System.out.println(br.getClass().getName());
		
	
	}

}
