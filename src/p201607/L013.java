package p201607;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Administrator on 2017/1/28 0028.
 */
public class L013 {
    public static void main(String[] args) throws IOException {
        System.out.println(processFile((BufferedReader br)->br.readLine()));

        System.out.println(processFile((BufferedReader br)->br.readLine() + br.readLine() + br.readLine()));
    }


    /*public static String processFile() throws IOException {
        try (BufferedReader br = new BufferedReader((new FileReader("F:\\IdeaProjects\\LearningLeader\\src\\p201604\\L007.java")))) {
            return br.readLine();
        }
    }*/


    public static String processFile(BufferReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader((new FileReader("F:\\IdeaProjects\\LearningLeader\\src\\p201604\\L007.java")))) {
            return p.process(br);
        }
    }

    @FunctionalInterface
    public interface BufferReaderProcessor{
        String process(BufferedReader b) throws IOException;
    }
}
