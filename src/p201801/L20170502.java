package p201801;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class L20170502 {

	public static void main(String[] args) {
		
		ByteStore byteStore = new ByteStore();
		Random random = new Random();
		for(int i = 0; i < 1000; i++){
			byteStore.putMyItem(i, new MyItem((byte)random.nextInt(127),(byte)random.nextInt(127),(byte)random.nextInt(127)));
		}
		
		/*List<Byte> byteList = Arrays.asList((byte)92,(byte)113,(byte)78,(byte)13,(byte)54,(byte)30,(byte)67,(byte)44,(byte)89,(byte)17,(byte)56,(byte)91,(byte)85,(byte)12,(byte)53);
		byteList.toArray(byteStore.storeByteArry);
		
		for(int i = 0; i < byteStore.storeByteArry.length; i++){
			System.out.print(byteStore.storeByteArry[i] + " ");
		}
		
		System.out.println();*/
		
		quick(byteStore.storeByteArry,0,byteStore.storeByteArry.length - 3);
		
		for(int i = 0; i < 1000; i++){
			System.out.println(byteStore.storeByteArry[i*3] + " " + byteStore.storeByteArry[i*3+1] + " " + byteStore.storeByteArry[i*3+2]);
		}
		
		/*for(int i = 0; i < byteStore.storeByteArry.length; i++){
			System.out.print(byteStore.storeByteArry[i] + " ");
		}*/
		
		
		

	}
	
	
	//快速排序
    public static void quick(Byte[] storeByteArry,int low,int high){
        if(low < high){
            int middle = getMiddle(storeByteArry,low,high);
            quick(storeByteArry,low,middle-3);
            quick(storeByteArry,middle+3,high);
        }
    }
    
    public static int getMiddle(Byte[] storeByteArry,int low,int high){
        byte tmp1 = storeByteArry[low];
        byte tmp2 = storeByteArry[low + 1];
        byte tmp3 = storeByteArry[low + 2];
        while(low < high){
            while(low < high && storeByteArry[high + 2] >= tmp3){
                high = high - 3;
            }
            storeByteArry[low] = storeByteArry[high];
            storeByteArry[low + 1] = storeByteArry[high + 1];
            storeByteArry[low + 2] = storeByteArry[high + 2];

            while(low < high && storeByteArry[low + 2] < tmp3){
                low = low + 3;
            }
            storeByteArry[high] = storeByteArry[low];
            storeByteArry[high + 1] = storeByteArry[low + 1];
            storeByteArry[high + 2] = storeByteArry[low + 2];
        }
        storeByteArry[low] = tmp1;
        storeByteArry[low + 1] = tmp2;
        storeByteArry[low + 2] = tmp3;
        return low;
    }


}
