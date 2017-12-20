package p201801;

import java.util.Random;

public class L201703 {

	public static void main(String[] args) {
		
		byte[][] cloumnByte = new byte[1024][1024];
		Random random = new Random();
		for(int i = 0; i < cloumnByte.length; i++){
			for(int j = 0; j < cloumnByte[i].length; j++){
				cloumnByte[i][j] = (byte) random.nextInt(100);
			}
		}
		
		countByRow(cloumnByte);
		countByColumn(cloumnByte);

	}
	
	public static void countByRow(byte[][] cloumnByte){
		long startTime = System.currentTimeMillis();
		int sum = 0;
		for(int i = 0; i < cloumnByte.length; i++){
			for(int j = 0; j < cloumnByte[i].length; j++){
				sum += cloumnByte[i][j];
			}
		}
		
		System.out.println("用时:" + (System.currentTimeMillis() - startTime));
		System.out.println("sum = " + sum);
	}
	
	
	public static void countByColumn(byte[][] cloumnByte){
		long startTime = System.currentTimeMillis();
		int sum = 0;
		for(int i = 0; i < cloumnByte.length; i++){
			for(int j = 0; j < cloumnByte[i].length; j++){
				sum += cloumnByte[j][i];
			}
		}
		
		System.out.println("用时:" + (System.currentTimeMillis() - startTime));
		System.out.println("sum = " + sum);
	}

}
