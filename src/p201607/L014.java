package p201607;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Administrator on 2017/1/29 0029.
 */
public class L014  extends RecursiveTask<Integer> {


    private static Map<String,String> fileMap = new HashMap<String,String>();
    private static File[] files;
    private static final String keyWords = "String";
    int sum = 0;

    private int start;
    private int end;

    public L014(int start,int end){
        this.start = start;
        this.end = end;
    }


    @Override
    protected Integer compute(){
        int allSum = 0;

        if(end - start <= 2){
            for(int i = start; i <= end; i++){
                allSum = readFile(files[i],1);
            }
        }else{
            int middle = (start + end) / 2;
            L014 leftTask = new L014(start,middle);
            L014 rightTask = new L014(middle + 1,end);
            invokeAll(leftTask,rightTask);
            try{
                allSum = leftTask.get() + rightTask.get();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return allSum;
    }


    public int readFile(File fileCurrent,int currentDeepth) {
        int deeepth = currentDeepth;
        if (deeepth > 4) {
            System.out.println("deeepth is 4 return");
            return 0;
        }
        File[] files = fileCurrent.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    readFile(file, deeepth + 1);
                } else {
                    //获取扩展名
                    String extendName = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
                    if ("java".equals(extendName)) {
                        sum += countKeyWords(file.getAbsolutePath());
                        System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath() + " sum = " + sum);
                    }
                }
            }
        }
        return sum;
    }


    /**
     * 查找关键字出现次数
     * @param filePath
     * @throws Exception
     */
    public int countKeyWords(String filePath){
        int sum = 0;
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(keyWords)) {
                    sum++;
                    //查找当前行剩下的关键字
                    line = line.replaceFirst(keyWords, "");
                    while (line.contains(keyWords)) {
                        line = line.replaceFirst(keyWords, "");
                        sum++;
                    }
                }
            }
            if (sum > 0) {
                fileMap.put(filePath, String.valueOf(sum));
            }
            return sum;
        }catch (Exception e){
            e.printStackTrace();
            return sum;
        }
    }

    public static void main(String[] args) throws Exception{

        files = new File("F:\\IdeaProjects").listFiles();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        L014 task = new L014(0,files.length - 1);
        Future<Integer> allSum = forkJoinPool.submit(task);

        System.out.println(keyWords + " 总共出现 " + allSum.get() + "次");

        fileMap.entrySet().stream()
                .sorted((m1,m2)->Integer.compare(Integer.parseInt(m2.getValue()),Integer.parseInt(m1.getValue())))
                .forEach(m->System.out.println(m.getValue() + " 次出现在 " + m.getKey()));

    }
}
