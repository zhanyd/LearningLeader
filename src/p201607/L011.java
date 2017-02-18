package p201607;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Created by Administrator on 2017/1/27 0027.
 */
public class L011 {

    private static int allSum = 0;
    private static Map<String,String> fileMap = new HashMap<String,String>();
    private static final String keyWords = "String";

    public static void readFile(String path,int currentDeepth) throws Exception{
        int deeepth = currentDeepth;
        File[] files = new File(path).listFiles();
        List<File> fileList = new ArrayList<>();

        if(deeepth > 4){
            System.out.println("deeepth is 4 return");
            return;
        }

        if(files != null && files.length > 0){
            for(File file : files){
                if(file.isDirectory()){
                    //System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
                    //System.out.println("file.getPath() = " + file.getPath());
                    readFile(file.getAbsolutePath(),deeepth + 1);
                }else{
                    //System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
                    //System.out.println("file.getPath() = " + file.getPath());
                    String extendName = file.getName().substring(file.getName().lastIndexOf(".") + 1,file.getName().length());
                    //System.out.println(extendName);
                    if("java".equals(extendName)){
                        System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
                        countKeyWords(file.getAbsolutePath());

                    }
                }
            }
        }
    }

    /**
     * 查找关键字出现次数
     * @param filePath
     * @throws Exception
     */
    public static void countKeyWords(String filePath) throws Exception{
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        int sum = 0;
        while ((line = bufferedReader.readLine()) != null){
            if(line.contains(keyWords)){
                sum++;

                line = line.replaceFirst(keyWords,"");
                while (line.contains(keyWords)){
                    line = line.replaceFirst(keyWords,"");
                    sum++;
                }
            }
        }

        if(sum > 0){
            fileMap.put(filePath,String.valueOf(sum));
            allSum += sum;
        }
    }

    public static void main(String[] args) throws Exception{
        readFile("F:\\IdeaProjects",1);
        System.out.println(keyWords + " 总共出现 " + allSum + "次");

        List<Map.Entry<String,String>> mapList = new ArrayList<Map.Entry<String,String>>(fileMap.entrySet());
        Collections.sort(mapList, new Comparator<Map.Entry<String,String>>() {
            @Override
            public int compare(Map.Entry<String,String> o1, Map.Entry<String,String> o2) {
                return Integer.compare(Integer.parseInt(o2.getValue()),Integer.parseInt(o1.getValue()));
            }
        });
        for(Map.Entry<String,String> entry : mapList){
            System.out.println(entry.getValue() + " 次出现在 " + entry.getKey());
        }
    }
}
