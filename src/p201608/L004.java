package p201608;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2017/3/4 0004.
 */
public class L004 {
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

    private static void doResponseGet(String requestPage, Socket socket) throws Exception {

        final String WEB_ROOT = "F:\\IdeaProjects\\LearningLeader\\src\\p201608\\";

        OutputStream out = socket.getOutputStream();
        String[] paramURL = requestPage.split("\\?");
        String fileName = paramURL[0];
        String[] paramArray = paramURL[1].split("&");
        String name = paramArray[0].substring( paramArray[0].indexOf("=") + 1);
        String password = paramArray[1].substring( paramArray[1].indexOf("=") + 1);

        //通过Nashorn在Java中调用javascript
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
        nashorn.eval(new FileReader(WEB_ROOT + fileName));

        Invocable invocable = (Invocable) nashorn;
        Object result = invocable.invokeFunction("getInfo", name, password);
        String msg = new String(String.valueOf(result));
        System.out.println(msg);
        System.out.println(result.getClass());

        String response = "HTTP/1.1 200 OK\r\n";
        response += "Server: zhanyd Server/0.1\r\n";
        response += "Content-Length: " + (msg.length()) + "\r\n";
        response += "\r\n";
        response += msg;
        out.write(response.getBytes("utf-8"));
        out.flush();
        //socket.close();
        System.out.println("return " + msg);
        }


}
