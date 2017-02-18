package p201607;

import java.util.concurrent.SynchronousQueue;

/**
 * Created by Administrator on 2017/1/22 0022.
 */
public class L001 {
    public static void main(String[] args){
        SynchronousQueue<String> queue=new SynchronousQueue();
        if(queue.offer("S1"))
        {
            System.out.println("scucess");
        }else
        {
            System.out.println("faield");
        }
    }
}
