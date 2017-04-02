package p201612NoBlock;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class TelnetIOHandler extends IOHandler{

	public TelnetIOHandler(Selector selector,SocketChannel socketChannel) throws IOException{
		super(selector,socketChannel);
	}
	
	private int lastMessagePos;
	
	@Override
	public void onConnected() throws IOException{
		System.out.println(Thread.currentThread().getName() +  " connected from " + this.socketChannel.getRemoteAddress());
		this.writeData(("Welecome Leader.us Power Man Java Courde ... \r\n" +
				"1: find keyword in files \r\n" +
				"2: quit \r\n" +
				"Telenet>").getBytes());
	}
	
	@Override
	public void doHandler() throws Exception{
		System.out.println(Thread.currentThread().getName() + " read....");
		socketChannel.read(readBuffer);
		int readEndPos = readBuffer.position();
		String readedLine = null;
		
		for(int i = lastMessagePos; i < readEndPos; i++){
			if(readBuffer.get(i) == 13){//a line finished
				byte[] lineBytes = new byte[i - lastMessagePos];
				readBuffer.position(lastMessagePos);
				readBuffer.get(lineBytes);
				lastMessagePos = i;
				readedLine = new String(lineBytes);
				System.out.println("received line, length:" + readedLine.length() + " value:" + readedLine);
				break;
			}
		}
		
		if(readedLine != null){
			//取消读事件关注，因为要应答数据
			selectionKey.interestOps(selectionKey.interestOps() &~ SelectionKey.OP_READ);
			//处理指令
			processCommand(readedLine);
		}
		
		if(readBuffer.position() > readBuffer.capacity() / 2){// 清理前面读过的废弃空间
			System.out.println(" rewind read byte buffer,get more space " + readBuffer.position());
			readBuffer.limit(readEndPos);
            readBuffer.position(lastMessagePos + 2);
            readBuffer.compact();
            lastMessagePos = 0;
		}
	}
	
	private void processCommand(String readedLine) throws Exception{
		byte[] data = null;
		if(readedLine.startsWith("1")){
			String[] strCommand = readedLine.split(" ");
			if(strCommand.length != 3){
				data = "输入参数有误".getBytes("GBK");
			}else {
				File[] files = new File(strCommand[1]).listFiles();
				ForkJoinPool forkJoinPool = new ForkJoinPool();
				L014 task = new L014(0,files.length - 1,strCommand[2]);
				Future<Integer> allSum = forkJoinPool.submit(task);


				System.out.println("strCommand : ");
				Arrays.stream(strCommand).forEach(f->System.out.print(" " + f));
				System.out.println(" strCommand[2] : " + strCommand[2]);
				while (!allSum.isDone()){
					//Thread.sleep(100);
					System.out.println(" waiting forkJoinPool...");
				}
				if(allSum.isDone()){

					System.out.println(strCommand[2] + " 总共出现 " + allSum.get() + "次");
					data = (strCommand[2] + " 总共出现 " + allSum.get() + "次 \r\n Telnet>").getBytes("GBK");
				}else{
					data = ("forkJoinPool还没处理完完 \r\n Telnet>").getBytes("GBK");
				}
			}
			this.writeData(data);
		}else if(readedLine.equals("2")){
			data = new byte[100];
			ByteBuffer tempBuf = ByteBuffer.wrap(data);
			tempBuf.put("Bye My Power boy ..".getBytes());
			this.writeData(data);
			this.socketChannel.close();
		}else{
			data = new byte[100];
			ByteBuffer tempBuf = ByteBuffer.wrap(data);
			for(int i = 0;i < tempBuf.capacity() - 10; i++){
				tempBuf.put((byte)('a' + i % 25));
			}

			tempBuf.put("\r\nTelnet>".getBytes());
			this.writeData(data);
		}
	}
}
