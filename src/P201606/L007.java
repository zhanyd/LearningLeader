package P201606;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
public class L007 {
    public static void main(String[] args) throws InterruptedException{
        Thread r=new ThreadDemo("直接调用run执行");
        r.run();
        Thread t1=new ThreadDemo("线程一");
        t1.start();
        Thread t2=new ThreadDemo("线程二");
        t2.start();
        t1.join();
        t2.join();
        for(int i=0;i<10;i++){
            System.out.println("主线程执行------>"+i);
        }
    }

    public static class ThreadDemo extends Thread{
        private String ThreadName;
       public ThreadDemo(String s){
            this.ThreadName=s;
        }
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(this.getThreadName()+"执行------>"+i);
            }
        }
    public String getThreadName() {
        return ThreadName;
    }
}
}
