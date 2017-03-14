package P201610;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/3/12 0012.
 */
public class L001 {

    public static void main(String[] args) throws Exception{
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress(9000);
        serverSocketChannel.socket().bind(address);
        System.out.println("started at " + address);

        SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while(true){
            int selectNum = selector.select();
            System.out.println("Selected Number is :" + selectNum);
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()){
                SelectionKey selectionKey = iter.next();

                if((selectionKey.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT){
                    System.out.println("selectionKey.readyOps() = " + selectionKey.readyOps());
                    ServerSocketChannel serverChannel = (ServerSocketChannel)selectionKey.channel();
                    SocketChannel socketChannel = serverChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,selectionKey.OP_READ);
                    socketChannel.write(ByteBuffer.wrap("welcome leader.us power man java course..\r\n".getBytes()));
                    iter.remove();
                }
                else if((selectionKey.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ){
                    System.out.println("selectionKey.readyOps() = " + selectionKey.readyOps());
                    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(100);
                    buffer.put("\r\n Follow you : ".getBytes());
                    socketChannel.read(buffer);
                    buffer.put("\r\n".getBytes());
                    buffer.flip();
                    socketChannel.write(buffer);
                    iter.remove();
                }
            }
        }


    }
}
