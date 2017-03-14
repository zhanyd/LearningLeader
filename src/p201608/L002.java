package p201608;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/2/22 0022.
 */
public class L002 {

    public static void main(String[] args) throws Exception{
        ExecutorService executorService= Executors.newFixedThreadPool(5);
        ServerSocket serverSocket = new ServerSocket(80);
        while(true){
            executorService.execute(new RunServer(serverSocket));
        }
    }

    static class RunServer implements Runnable{
        ServerSocket serverSocket;
        public RunServer(ServerSocket serverSocket){
            this.serverSocket = serverSocket;
        }
        @Override
        public void run(){
            try{
                System.out.println(Thread.currentThread().getName() + "is accept()......");
                System.out.println("before serverSocket.accept()");
                Socket socket = serverSocket.accept();
                System.out.println(Thread.currentThread().getName() + "is processing......");
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
                            doResponseGet(socket);
                        }
                    }
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private static void doResponseGet(Socket socket) throws IOException{
        OutputStream out = socket.getOutputStream();
            String msg = "I can't find bao zang...\r\n";
            String respone = "HTTP/1.1 200 OK\r\n";
            respone += "Server: zhanyd Server/0.1\r\n";
            respone += "Content-Length: " + (msg.length()) + "\r\n";
            respone += "\r\n";
            respone += msg;
            out.write(respone.getBytes());
            out.flush();
            //socket.close();
            System.out.println("return I can't find bao zang...");
    }
}
