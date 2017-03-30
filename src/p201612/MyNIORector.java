package p201612;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;

public class MyNIORector extends Thread{
	final Selector selector;
	final ExecutorService executor;
	public MyNIORector(ExecutorService executorService) throws IOException{
		selector = Selector.open();
		this.executor = executorService;
	}
	
	public void registerNewClient(SocketChannel socketChannel){
		System.out.println(" registered by actor " + this.getName());
		//new TelnetIOHandler(selector,socketChannel);
	}

}
