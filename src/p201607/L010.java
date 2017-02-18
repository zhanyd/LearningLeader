package p201607;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/27 0027.
 */
public class L010 {

    public static void readAllFile(String filePath){
        File f = null;
        f = new File(filePath);

        File[] files = f.listFiles();
        List<File> list = new ArrayList<File>();

        if(files != null && files.length > 0){
            for(File file : files){
                if(file.isDirectory()){
                    readAllFile(file.getAbsolutePath());
                }else{
                    list.add(file);
                }
            }
        }


        for(File file:list){
            System.out.println(file.getAbsolutePath());
        }

    }


    public static void main(String[] args){
        readAllFile("G:\\java学习");
    }
}


