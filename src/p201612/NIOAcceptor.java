package p201612;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Administrator on 2017/3/30 0030.
 */
public class NIOAcceptor extends Thread{
    private final ServerSocketChannel serverSocketChannel;
    private final MyNIORector[] reactors;

    public NIOAcceptor(int bindPort,MyNIORector[] reactors) throws IOException{
        this.reactors = reactors;
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(true);
        //serverSocketChannel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress(bindPort);
        serverSocketChannel.socket().bind(address);
        System.out.println(Thread.currentThread().getName() + " started at " + address);
    }

    public void run(){
        while (true){
            try{
                SocketChannel socketChannel = serverSocketChannel.accept();
                System.out.println("Connection Accepted " + socketChannel.getRemoteAddress());
                int nextReator = ThreadLocalRandom.current().nextInt(0,reactors.length);
                reactors[nextReator].registerNewClient(socketChannel);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
