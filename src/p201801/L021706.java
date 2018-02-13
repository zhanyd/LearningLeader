package p201801;

import java.util.Arrays;
import java.util.stream.IntStream;

public class L021706 {

	public static void main(String[] args){
		int[] data = new int[8192];
		IntStream.range(0, 8192).forEach(i->data[i] = i);
		
		Arrays.parallelSort(data);
	}
}
