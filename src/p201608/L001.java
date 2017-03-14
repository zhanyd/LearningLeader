package p201608;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2017/2/22 0022.
 */
public class L001 {

    public static void main(String[] args) throws Exception{
        try{
            ServerSocket serverSocket = new ServerSocket(80);
            while(true){
                System.out.println("before serverSocket.accept()");
                Socket socket = serverSocket.accept();
                System.out.println("Request: " + socket.toString() + " connected");
                LineNumberReader in = new LineNumberReader(new InputStreamReader(socket.getInputStream()));
                String lineInput;
                String requestPage = null;
                while((lineInput = in.readLine()) != null){
                    System.out.println(lineInput);
                    if(in.getLineNumber() == 1){
                        requestPage = lineInput.substring(lineInput.indexOf('/') + 1,lineInput.lastIndexOf(' '));
                        System.out.println("request page :" + requestPage);
                    }else{
                        if(lineInput.isEmpty()){
                            System.out.println("header finished");
                            doResponseGet(requestPage,socket);
                        }
                    }
                }


            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private static void doResponseGet(String requestPage, Socket socket) throws IOException{
        final String WEB_ROOT = "c:";
        File theFile = new File(WEB_ROOT,requestPage);
        OutputStream out = socket.getOutputStream();
        if(theFile.exists()){
            //从服务器根目录下找到用户请求的文件并发送回浏览器
            InputStream fileIn = new FileInputStream(theFile);
            byte[] buf = new byte[fileIn.available()];
            fileIn.read(buf);
            fileIn.close();
            /*out.write(buf);
            out.flush();
            socket.close();*/

            String msg2 = new String(buf, "GB2312");
            String respone = "HTTP/1.1 200 OK\r\n";
            respone += "Server: zhanyd Server/0.1\r\n";
            //respone += "Content-Length: " + (msg2.length() - 8) + "\r\n"
            // .000000000000;
            respone += "\r\n";
            respone += msg2;
            out.write(respone.getBytes());
            out.flush();
            socket.close();

            System.out.println("return Q2016.txt");
        }else{
            String msg = "I can't find bao zang...\r\n";
            String respone = "HTTP/1.1 200 OK\r\n";
            respone += "Server: zhanyd Server/0.1\r\n";
            respone += "Content-Length: " + (msg.length()) + "\r\n";
            //respone += "Content-Length: " + msg.length() + "\r\n";
            respone += "\r\n";
            respone += msg;
            out.write(respone.getBytes());
            out.flush();
            //socket.close();
            System.out.println("return I can't find bao zang...");
        }

    }
}
