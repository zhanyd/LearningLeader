package p201612NoBlock;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Administrator on 2017/3/30 0030.
 */
public class NIOAcceptor extends Thread{
    private final ServerSocketChannel serverSocketChannel;
    private final MyNIORector[] reactors;
    private final Selector selector;

    public NIOAcceptor(int bindPort,MyNIORector[] reactors) throws IOException{
        this.reactors = reactors;
        serverSocketChannel = ServerSocketChannel.open();
        //serverSocketChannel.configureBlocking(true);
        serverSocketChannel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress(bindPort);
        serverSocketChannel.socket().bind(address);
        System.out.println(Thread.currentThread().getName() + " started at " + address);
        
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void run(){
        while (true){
            try{
            	selector.select();
            	Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            	for(SelectionKey key : selectionKeySet){
            		if(key.isAcceptable()){
            			SocketChannel socketChannel = serverSocketChannel.accept();
            			System.out.println("Connection Accepted " + socketChannel.getRemoteAddress());
            			int nextReator = ThreadLocalRandom.current().nextInt(0,reactors.length);
            			reactors[nextReator].registerNewClient(socketChannel);
                        selectionKeySet.remove(key);
            		}
            	}
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
