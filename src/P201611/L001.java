package P201611;

import java.nio.ByteBuffer;
import java.nio.*;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
public class L001 {

    private static ByteBuffer readBuffer100;
    private static ByteBuffer readBuffer200;
    private static ByteBuffer readBuffer300;
    private static TreeSet<ByteBuffer> DirectByteBufferPool = new TreeSet<ByteBuffer>();

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executorService= Executors.newFixedThreadPool(3);
        initPool();
        for(int i = 0; i <= 5; i++){
            Future<ByteBuffer> future = executorService.submit(new ByteBufferPool(DirectByteBufferPool,i*100));

            System.out.println("return byteBuffer : " + future.get());
        }
    }

    public static void initPool(){

        readBuffer100 = ByteBuffer.allocateDirect(100);
        readBuffer200 = ByteBuffer.allocateDirect(200);
        readBuffer300 = ByteBuffer.allocateDirect(300);

        DirectByteBufferPool.add(readBuffer200);
        DirectByteBufferPool.add(readBuffer100);
        DirectByteBufferPool.add(readBuffer300);
    }



    static class ByteBufferPool implements Callable {
        TreeSet<ByteBuffer> DirectByteBufferPool;
        int size;
        public ByteBufferPool(TreeSet<ByteBuffer> DirectByteBufferPool,int size){
            this.DirectByteBufferPool = DirectByteBufferPool;
            this.size = size;
        }

        @Override
        public ByteBuffer call() throws Exception {
            System.out.println(Thread.currentThread().getName());
            Iterator iterator = DirectByteBufferPool.iterator();
            ByteBuffer byteBuffer = null;
            while (iterator.hasNext()){
                byteBuffer = (ByteBuffer)iterator.next();
                if(byteBuffer.capacity() >= size){
                    byteBuffer.clear();
                    return byteBuffer;
                }
            }

            return byteBuffer;
        }
    }



}
