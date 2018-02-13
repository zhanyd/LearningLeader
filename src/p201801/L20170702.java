package p201801;

public class L20170702 {

	private static int[] storeByteArry = new int[100];
	
	public static void main(String[] args) {
		MyItem myItem = new MyItem((byte)2,(byte)4,(byte)5);
		putMyItem(0,myItem);
		
		MyItem myItem2 = getMyItem(0);
		System.out.println(myItem2.getType() + " " + myItem2.getColor() + " " + myItem2.getPrice());

	}
	
	
	public static void putMyItem(int index,MyItem item){
		/*System.out.println(Integer.toBinaryString(item.getType()));
		System.out.println(Integer.toBinaryString(item.getColor() << 8));
		System.out.println(Integer.toBinaryString(item.getPrice() << 16));
		System.out.println(item.getType());
		System.out.println(item.getColor() << 8);
		System.out.println(item.getPrice() << 16);
		int a = (item.getType()) + (item.getColor() << 8) + (item.getPrice() << 16);
		System.out.println(a);*/
		storeByteArry[index] = (item.getType()) + (item.getColor() << 8) + (item.getPrice() << 16);
		System.out.println(Integer.toBinaryString(storeByteArry[index]));
	}
	
	
	public static MyItem getMyItem(int index){
		MyItem myItem = new MyItem();
		myItem.setType((byte)(storeByteArry[index] & 0xff));
		myItem.setColor((byte)(storeByteArry[index] >> 8 & 0xff));
		myItem.setPrice((byte)(storeByteArry[index] >> 16 & 0xff));
		
		return myItem;
	}
}
