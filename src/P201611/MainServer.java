package P201611;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/19 0019.
 */
public class MainServer {
    public static void main(String[] args) throws IOException {
        MyNIORector reacot=new MyNIORector(9000);
        reacot.start();
    }
}
