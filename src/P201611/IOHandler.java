package P201611;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by Administrator on 2017/3/19 0019.
 */
public class IOHandler implements Runnable{
    private final SelectionKey selectionKey;
    private final SocketChannel socketChannel;
    private ByteBuffer writeBuffer;
    private ByteBuffer readBuffer;
    private int lastMessagePos;

    public IOHandler(final Selector selector, SocketChannel socketChannel) throws IOException{
        socketChannel.configureBlocking(false);
        this.socketChannel = socketChannel;
        selectionKey = socketChannel.register(selector,0);
        selectionKey.interestOps(SelectionKey.OP_READ);
        writeBuffer = ByteBuffer.allocateDirect(100);
        readBuffer = ByteBuffer.allocateDirect(20);
        //绑定会话
        selectionKey.attach(this);
        writeBuffer.put("welcome leader.us power man java course ... \r\n telnet>".getBytes());
        writeBuffer.flip();
        doWriteData();
    }

    public void run(){
        try{
            if(selectionKey.isReadable()){
                doReadData();
            }else if(selectionKey.isWritable()){
                doWriteData();
            }
        }catch(Exception e){
            e.printStackTrace();
            selectionKey.cancel();
            try{
                socketChannel.close();
            }catch (IOException e1){
                e1.printStackTrace();
            }
        }
    }

    private void doWriteData() throws  IOException{
        writeToChannel();
    }

    private void writeToChannel() throws IOException{
        int writed = socketChannel.write(writeBuffer);
        System.out.println("writed " + writed);
        if(writeBuffer.hasRemaining()){
            System.out.println("writed " + writed + "not write finished so bind to session ,remins "
                + writeBuffer.remaining());
            selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_WRITE);
        }else{
            System.out.println(" block write finished ");
            writeBuffer.clear();
            selectionKey.interestOps(selectionKey.interestOps() &~ SelectionKey.OP_WRITE);
        }
    }

    private void doReadData() throws IOException{
        socketChannel.read(readBuffer);
        int readEndPos = readBuffer.position();
        byte[] lineBytes;
        String readedLine = null;
        for(int i = lastMessagePos; i < readEndPos; i++){
            if(readBuffer.get(i) == 13){
                lineBytes = new byte[i - lastMessagePos];
                readBuffer.position(lastMessagePos);
                readBuffer.get(lineBytes);
                lastMessagePos = i;
                readedLine = new String(lineBytes);
                System.out.println("received line,length :" + readedLine.length() + " value :" + readedLine);
                break;
            }
        }

        if(readedLine != null){
            //不能取消读事件，取消了就没办法继续读了  added by zhan
            //取消读事件关注，因为要应答数据
            //selectionKey.interestOps(selectionKey.interestOps() &~SelectionKey.OP_READ);
            //处理指令
            processCommand(readedLine);
        }


        if(readBuffer.position() > readBuffer.capacity()/2){
            //清理前面度过的废弃空间
            System.out.println("rewind read byte buffer,get more space " + readBuffer.position());
            //limit 不应该设置成readBuffer.position()，应为在for的if语句里position只是到上一个报文的结束，结束符后的报文会丢失。。。
            //readBuffer.limit(readBuffer.position());
            readBuffer.limit(readEndPos);
            readBuffer.position(lastMessagePos + 2);
            readBuffer.compact();
            lastMessagePos = 0;
        }
    }

    private void processCommand(String readLine) throws IOException{
        if(readLine.startsWith("dir")){
            readLine = "cmd /c " + readLine;
            String reslut = LocalCmandUtil.callCmdAndgetResult(readLine);
            writeBuffer.put(reslut.getBytes("GBK"));
            writeBuffer.put("\r\nTelnet>".getBytes());
        }else{
            for(int i =0; i < writeBuffer.capacity() - 10; i++){
                writeBuffer.put((byte)('a' + i % 25));
            }
            writeBuffer.put("\r\nTelnet>".getBytes());
        }
        writeBuffer.flip();
        writeToChannel();
    }


}
