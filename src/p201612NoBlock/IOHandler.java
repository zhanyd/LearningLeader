package p201612NoBlock;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class IOHandler implements Runnable{
	protected final SelectionKey selectionKey;
	protected final SocketChannel socketChannel;
	private volatile ByteBuffer writeBuffer;
	private volatile LinkedList<ByteBuffer> writeQueue = new LinkedList<ByteBuffer>();
	private AtomicBoolean writingFlag = new AtomicBoolean(false);
	protected volatile ByteBuffer readBuffer;
	
	public IOHandler(final Selector selector,SocketChannel socketChannel) throws IOException{
		socketChannel.configureBlocking(false);
		this.socketChannel = socketChannel;
		selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
		readBuffer = ByteBuffer.allocate(100);
		//绑定会话
		selectionKey.attach(this);
		this.onConnected();
	}
	
	public abstract void onConnected() throws IOException;
	
	public abstract void doHandler() throws Exception;
	
	public void writeData(byte[] data) throws IOException{
		while(!writingFlag.compareAndSet(false, true)){
			// wait until release
		}
		try{
			ByteBuffer theWriteBuf = writeBuffer;
			if(theWriteBuf == null && writeQueue.isEmpty()){
				writeToChannel(ByteBuffer.wrap(data));
			}else{
				writeQueue.add(ByteBuffer.wrap(data));
				writeToChannel(theWriteBuf);
			}
		}finally{
			writingFlag.lazySet(false);
		}
	}
	
	public void run(){
		try{
			if(selectionKey.isReadable()){
				doHandler();
			}else if(selectionKey.isWritable()){
				doWriteData();
			}
		}catch(Exception e){
			e.printStackTrace();
			selectionKey.cancel();
			try{
				socketChannel.close();
			}catch(IOException e1){
				e1.printStackTrace();
			}
		}
	}
	
	private void doWriteData() throws IOException {
		try{
			while(!writingFlag.compareAndSet(false, true)){
				//wait until release
			}
			ByteBuffer theWriteBuf = writeBuffer;
			writeToChannel(theWriteBuf);
			
		}finally{
			//release
			writingFlag.lazySet(false);
		}
	}
	
	
	private void writeToChannel(ByteBuffer curBuffer) throws IOException{
		int writed = socketChannel.write(curBuffer);
		System.out.println(Thread.currentThread().getName() + " writed " + writed);
		if(curBuffer.hasRemaining()){
			System.out.println("writed " + writed + " not writed finished,remains " + curBuffer.remaining());
			selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_WRITE);
			if(curBuffer != this.writeBuffer){
				writeBuffer = curBuffer;
			}
		}else{
			System.out.println(Thread.currentThread().getName() + " block write finished ");
			writeBuffer = null;
			if(writeQueue.isEmpty()){
				System.out.println(Thread.currentThread().getName() +".... write finished,on more data ");
				selectionKey.interestOps((selectionKey.interestOps() &~ SelectionKey.OP_WRITE) | SelectionKey.OP_READ);
			}else{
				ByteBuffer buf = writeQueue.removeFirst();
				buf.flip();
				writeToChannel(buf);
			}
		}
	}
	

}
