package P201611;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2017/3/19 0019.
 */
public class LocalCmandUtil {

    public static String callCmdAndgetResult(String cmd){
        StringBuffer result = new StringBuffer();
        try{
            ProcessBuilder pb = new ProcessBuilder(cmd.split("\\s"));// 创建进程管理实例
            Process process = pb.start();//启动线程
            InputStream is = process.getInputStream();//获得输入流
            InputStreamReader isr = new InputStreamReader(is,"GBK");
            BufferedReader br = new BufferedReader(isr);
            String line;
            while((line = br.readLine()) != null){
                result.append(line);
            }
            is.close();
            isr.close();
            br.close();
            process.waitFor();
        }catch (Exception e){
            result.append(e.toString());
        }
        return result.toString();
    }
}
