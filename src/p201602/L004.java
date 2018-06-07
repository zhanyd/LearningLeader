package p201602;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Administrator on 2016/12/17 0017.
 */
public class L004 {

    public static final String ALLCHAR = "qwertyuiopasdfghjklzxcvbnm";

    private static List<Map<String, long[]>> salaryList = new ArrayList<>();

    public static void main(String[] args) throws Exception{

        long beginTime = System.currentTimeMillis();
        List<Salary> list = initSalary();
        long endInitSalaryTime = System.currentTimeMillis();
        System.out.println("initSalary cost : " + (endInitSalaryTime - beginTime));
        long stratWriteTime = System.currentTimeMillis();
        writeToFileWriter(list);
        //writeToFileFileChannel(list);
        //writeToFileFileMappedByteBuffer(list);
        //writeToMappedByteBufferF();
        //writeToMappedByteBufferChar();
        //writeToBufferedWriter("F://salaryWriterChannel.txt");
        long endWriteTime = System.currentTimeMillis();
        System.out.println("write cost : " + (endWriteTime - stratWriteTime));
        readFromFileToMap("f:\\salaryWriterChannel.txt");
        // readFromFileToMap("F://salaryWriterChannel.txt");
        //readFromMappedByteBuffer();
        //readFromBufferedReaderStream("F://salaryWriterChannel.txt");
       //System.out.println("-------------");
        //readFromBufferedReaderStreamThread("F://salaryWriterChannel.txt");
        //readFromBufferedReaderStreamByForkJoin("F://salaryWriterChannel.txt");
        System.out.println("read cost : " + (System.currentTimeMillis() - endWriteTime));
        System.out.println("all cost : " + (System.currentTimeMillis() - beginTime));
    }


    /**
     * 读文件到map
     * @param filename
     * @throws Exception
     */
    public static void readFromFileToMap(String filename) throws Exception{
        FileReader reader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(reader);
        HashMap<String,Salary> salaryMap = new HashMap<String,Salary>();
        Salary salary,mapSalary;
        String tem;
        String subName;
        String[] temArry;
        while((tem = bufferedReader.readLine()) != null){
            if(!tem.contains(",")){
                break;
            }
            salary = new Salary();
            //拆分字符串
            temArry = tem.split(",");
            if(temArry.length < 1){
                break;
            }
            salary.setName(temArry[0]);
            salary.setBaseSalary(Integer.parseInt(temArry[1]));
            salary.setBonus(Integer.parseInt(temArry[2]));
            //取前2个字符
            subName = salary.getName().substring(0,2);
            //已存在则累加baseSalary和bonus
            if(salaryMap.containsKey(subName)){
                mapSalary = salaryMap.get(subName);
                mapSalary.setBaseSalary(mapSalary.getBaseSalary() + salary.getBaseSalary());
                mapSalary.setBonus(mapSalary.getBonus() + salary.getBonus());
                mapSalary.setCount(mapSalary.getCount() + 1 );
            }
            //不存在的直接加入
            else{
                salaryMap.put(subName,salary);
            }
        }

        List<HashMap.Entry<String,Salary>> list = new ArrayList<HashMap.Entry<String,Salary>>(salaryMap.entrySet());
        Collections.sort(list, new Comparator<HashMap.Entry<String,Salary>>() {
            @Override
            public int compare(HashMap.Entry<String,Salary> o1,HashMap.Entry<String,Salary> o2) {
                return (o2.getValue().getBaseSalary() +  o2.getValue().getBonus()) >
                        (o1.getValue().getBaseSalary() +  o1.getValue().getBonus()) ? 1 : -1;
            }
        });

       /* salaryMap.forEach((k,v)->{
            System.out.println("map : " + k + "," + v.getBaseSalary() + "," + v.getBonus());
        });*/

        for(int i = 0; i < 10; i++){
            System.out.println(list.get(i).getKey() + "," + list.get(i).getValue().getBaseSalary()
                    + "," + list.get(i).getValue().getBonus() + "," + list.get(i).getValue().getCount() + "人");
        }

    }

    public static void readFromFileToList(String filename) throws Exception{
        FileReader reader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<Salary> salaryList = new ArrayList<Salary>();
        Salary salary;
        String tem = "";
        String[] temArry;

        while((tem = bufferedReader.readLine()) != null){
            salary = new Salary();
            temArry = tem.split(",");
            salary.setName(temArry[0]);
            salary.setBaseSalary(Integer.parseInt(temArry[1]));
            salary.setBonus(Integer.parseInt(temArry[2]));
            salaryList.add(salary);
        }


        Collections.sort(salaryList, new Comparator<Salary>() {
            @Override
            public int compare(Salary o1, Salary o2) {
                return o2.getName().compareTo(o1.getName());
            }
        });

        /*Collections.sort(salaryList, new Comparator<Salary>() {
            @Override
            public int compare(Salary o1, Salary o2) {
                return o2.getBaseSalary() > o1.getBaseSalary() ? 1 : -1;
            }
        });*/

        for(Salary salaryTem : salaryList){
            System.out.println(salaryTem.getName() + "," + salaryTem.getBaseSalary() + "," + salaryTem.getBonus());
        }
    }


    /**
     * 写文件
     * @param salaryList
     * @throws Exception
     */
    public static void writeToFileWriter( List<Salary> salaryList) throws Exception{

        FileOutputStream fileOutputStream = new FileOutputStream("f:\\salaryWriterChannel.txt");
        OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream,"utf-8");

        for(Salary salary : salaryList){
            writer.write(salary.getName() + "," + salary.getBaseSalary() + "," + salary.getBonus() + "\n");
        }

        writer.close();
        fileOutputStream.close();
        System.out.println("write " + salaryList.size() + " done");
    }


    /**
     * ByteBuffer写入数据
     * @param salaryList
     * @throws IOException
     */
    public static void writeToFileFileChannel(List<Salary> salaryList) throws IOException{

        FileOutputStream fout = new FileOutputStream("F://salaryWriterChannel.txt");
        FileChannel fc2 = fout.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        String writeStr;
        for(Salary salary : salaryList){
            buffer.clear();
            writeStr = salary.getName() + "," + salary.getBaseSalary() + "," + salary.getBonus() + "\n";
            buffer.put(writeStr.getBytes());
            buffer.flip();
            fc2.write(buffer);
        }

        fc2.close();
        fout.close();
        System.out.println("fileChannel write " + salaryList.size() + " done");
    }



    /**
     * MappedByteBuffer写入字符串
     * @throws IOException
     */
    public static void writeToFileFileMappedByteBuffer(List<Salary> salaryList) throws IOException{

        RandomAccessFile fout = new RandomAccessFile("F://salaryWriterChannel.txt","rw");

        FileChannel fc2 = fout.getChannel();
        System.out.println("fc2.position() = " + fc2.position());
        MappedByteBuffer mbf = fc2.map(FileChannel.MapMode.READ_WRITE, fc2.position(), 10000000*13);

        String writeStr;
        for(Salary salary : salaryList){
            writeStr = salary.getName() + "," + salary.getBaseSalary() + "," + salary.getBonus() + "\n";
			 /*System.out.println("writeStr.length() = " + writeStr.length());
			 System.out.println("writeStr.getBytes().length = " + writeStr.getBytes().length);*/
            mbf.put(writeStr.getBytes());
            //mbf.put(writeStr.getBytes(Charset.forName("utf-8")));
        }

        fc2.close();
        fout.close();
        System.out.println("FileMappedByteBuffer write " + salaryList.size() + " done");
    }


    /**
     * MappedByteBuffer写入byte数据
     * @throws IOException
     */
    public static void writeToMappedByteBufferF() throws IOException{
        Random random = new Random();
        int count = 10_000_000;
        byte[] name = new byte[5];
        RandomAccessFile fout = new RandomAccessFile("F://salaryWriterChannel.txt","rw");

        FileChannel fc2 = fout.getChannel();
        System.out.println("fc2.position() = " + fc2.position());
        MappedByteBuffer mbf = fc2.map(FileChannel.MapMode.READ_WRITE, fc2.position(), count*15);

        String writeStr;
        for(int i = 0; i < count; i++){
            mbf.putInt(random.nextInt(1000000));
            mbf.putInt(random.nextInt(1000000));
            for(int j = 0; j < 5;j++){
                name[j] = (byte)(random.nextInt(26) + 97);
            }
            mbf.put(name);
            //mbf.put((byte)0x0A);
            mbf.putChar('\n');
        }

        fc2.close();
        fout.close();
        System.out.println("FileMappedByteBuffer write " + count + " done");
    }


    /**
     * MappedByteBuffer写入字符串
     * @throws IOException
     */
    public static void writeToMappedByteBufferChar() throws IOException{
        Random random = new Random();
        int count = 10_000_000;
        char[] name = new char[5];
        RandomAccessFile fout = new RandomAccessFile("F://salaryWriterChannel.txt","rw");

        FileChannel fc2 = fout.getChannel();
        System.out.println("fc2.position() = " + fc2.position());
        MappedByteBuffer mbf = fc2.map(FileChannel.MapMode.READ_WRITE, fc2.position(), count*30);

        String writeStr;
        for(int i = 0; i < count; i++){
            for(int j = 0; j < 5;j++){
                name[j] = (char)(random.nextInt(26) + 97);
            }

            writeStr = random.nextInt(1000000) + "," + random.nextInt(1000000) + "," + String.valueOf(name) + '\n';
            mbf.put(writeStr.getBytes());
        }

        fc2.close();
        fout.close();
        System.out.println("FileMappedByteBuffer write " + count + " done");
    }


    /**
     * BufferedWriter方式写入数据
     * @param filename
     * @throws Exception
     */
    public static void writeToBufferedWriter(String filename) throws Exception{
        FileWriter reader = new FileWriter(filename);
        BufferedWriter bufferedWrite = new BufferedWriter(reader);

        Random random = new Random();
        int count = 10_000_000;
        //int count = 100;
        char[] name = new char[5];

        String writeStr;
        for(int i = 0; i < count; i++){
            for(int j = 0; j < 5;j++){
                name[j] = (char)(random.nextInt(26) + 97);
            }

            writeStr = random.nextInt(1000000) + "," + random.nextInt(1000000) + "," + String.valueOf(name) + '\n';
            bufferedWrite.write(writeStr);
//            bufferedWrite.flush();
        }

        bufferedWrite.flush();
        bufferedWrite.close();
        System.out.println("writeToBufferedWriter write " + count + " done");

    }


    /**
     * 读取MappedByteBuffer
     * @throws Exception
     */
    public static void readFromMappedByteBuffer() throws Exception{
        byte[] name = new byte[5];
        RandomAccessFile fout = new RandomAccessFile("F://salaryWriterChannel.txt","r");
        FileChannel fch = fout.getChannel();
        MappedByteBuffer buffer = fch.map(FileChannel.MapMode.READ_ONLY, 0, fout.length());
        //while(buffer.position() < buffer.limit()){
        while(buffer.position() < 1000){
    		/*System.out.println(buffer.get());
    		System.out.println(buffer.getInt());
    		buffer.get(name);
    		System.out.println(new String(name));*/

            System.out.print(buffer.getInt() + " ");
            System.out.print(buffer.getInt() + " ");
            buffer.get(name);
            System.out.print(new String(name));
            //换不了行？
            //System.out.print(buffer.get());
            System.out.println(buffer.getChar());
        }
    }


    /**
     * 读取BufferedReader
     * @param filename
     * @throws Exception
     */
    public static void readFromBufferedReaderStream(String filename) throws Exception{
        FileReader reader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(reader);
        bufferedReader.lines()
                .map(s->s.split(","))
                .filter(s->Integer.parseInt(s[0]) > 10000)
                .collect(Collectors.groupingBy(s->s[2].substring(0,2),
                        Collector.of(()->new long[2],
                                (a,sa)->{
                                    a[0] += Integer.parseInt(sa[0]) + Integer.parseInt(sa[1]);
                                    a[1] += 1L;
                                },
                                (a,b)->{
                                    a[0] += b[0];
                                    a[1] += b[1];
                                    return a;
                                }
                            )
                        )
                )
                .entrySet().stream()
                .sorted((a,b)->Long.compare(b.getValue()[0],a.getValue()[0]))
                .limit(10)
                .forEach(s->System.out.printf("%s,%s,%s\n",s.getKey(),s.getValue()[0],s.getValue()[1]));

    }


    /**
     * 多线程读取文件
     * @param filename
     * @throws Exception
     */
    public static void readFromBufferedReaderStreamThread(String filename) throws Exception{
        List<String> allLinesList = Files.readAllLines(Paths.get(filename));
        int size = allLinesList.size();
        int average = size/8;

        List<Thread> threadList = IntStream.range(0,8)
                .mapToObj(i->{
                    if(i==7){
                        return  new Thread(()->{
                            getSalaryList(allLinesList,i*average,size);
                        });
                    }else{
                        return  new Thread(()->{
                            getSalaryList(allLinesList,i*average,i*average + average);
                        });
                    }
                })
                .collect(Collectors.toList());

        threadList.forEach(i-> {
                    i.start();
                }
            );
        threadList.forEach(i->{

            try{
                i.join();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });

        //合并分段读取的文件
        salaryList.stream()
                .reduce((map1,map2)->{
                    map2.entrySet().stream()
                            .forEach(map->{
                                map1.merge(map.getKey(),map.getValue(),(k,v)->{
                                    k[0] += v[0];
                                    k[1] += v[1];
                                    return k;
                                });
                            });
                    return map1;
                })
                .ifPresent(s->s.entrySet().stream()
                        .sorted((a,b)->Long.compare(b.getValue()[0],a.getValue()[0]))
                                .limit(10)
                                .forEach(f->System.out.printf("%s,%s,%s\n",f.getKey(),f.getValue()[0],f.getValue()[1]))
                );




//        bufferedReader.lines()
//                .map(s->s.split(","))
//                .filter(s->Integer.parseInt(s[0]) > 10000)
//                .collect(Collectors.groupingBy(s->s[2].substring(0,2),
//                        Collector.of(()->new long[2],
//                                (a,sa)->{
//                                    a[0] += Integer.parseInt(sa[0]) + Integer.parseInt(sa[1]);
//                                    a[1] += 1L;
//                                },
//                                (a,b)->{
//                                    a[0] += b[0];
//                                    a[1] += b[1];
//                                    return a;
//                                }
//                        )
//                        )
//                )
//                .entrySet().stream()
//                .sorted((a,b)->Long.compare(b.getValue()[0],a.getValue()[0]))
//                .limit(10)
//                .forEach(s->System.out.printf("%s,%s,%s\n",s.getKey(),s.getValue()[0],s.getValue()[1]));

    }

    /**
     * 分段读取文件
     * @param list
     * @param start
     * @param end
     */
    public static void getSalaryList(List<String> list,int start,int end){
        salaryList.add(
                list.stream()
                .skip(start)
                .limit(end - start)
                .map(s->s.split(","))
                .filter(s->Integer.parseInt(s[0]) > 10000)
                .collect(Collectors.groupingBy(s->s[2].substring(0,2),
                        Collector.of(()->new long[2],
                                (a,sa)->{
                                    a[0] += Integer.parseInt(sa[0]) + Integer.parseInt(sa[1]);
                                    a[1] += 1L;
                                },
                                (a,b)->{
                                    a[0] += b[0];
                                    a[1] += b[1];
                                    return a;
                                }
                        )
                        )
                )
        );
    }


    public static void readFromBufferedReaderStreamByForkJoin(String filename) throws Exception {
        List<String> allLinesList = Files.readAllLines(Paths.get(filename));
        int size = allLinesList.size();
        ForkJoinProduces task = new ForkJoinProduces(allLinesList,0,size);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(task);
        task.join();

        //合并分段读取的文件
        salaryList.stream()
                .reduce((map1,map2)->{
                    map2.entrySet().stream()
                            .forEach(map->{
                                map1.merge(map.getKey(),map.getValue(),(k,v)->{
                                    k[0] += v[0];
                                    k[1] += v[1];
                                    return k;
                                });
                            });
                    return map1;
                })
                .ifPresent(s->s.entrySet().stream()
                        .sorted((a,b)->Long.compare(b.getValue()[0],a.getValue()[0]))
                        .limit(10)
                        .forEach(f->System.out.printf("%s,%s,%s\n",f.getKey(),f.getValue()[0],f.getValue()[1]))
                );

    }




    public static class ForkJoinProduces extends RecursiveAction {
        List<String> list;
        int start;
        int end;
        public ForkJoinProduces( List<String> list,int start,int end){
            this.list = list;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if(end - start <= 1000_000){
                getSalaryList(list,start,end);
            }else{
                    int middle = (start + end) / 2;
                    ForkJoinProduces leftTask = new ForkJoinProduces(list,start,middle);
                    ForkJoinProduces rightTask = new ForkJoinProduces(list,middle + 1,end);
                    invokeAll(leftTask,rightTask);
                }
            }
    }

 /*   public static void writeToFileStream( List<Salary> salaryList) throws Exception{

        FileOutputStream fileOutputStream = new FileOutputStream("f:\\salaryStream.txt");

        byte[] buffer = new byte[1024];
        for(Salary salary : salaryList){
            fileOutputStream.write("name=" + salary.getName() + " baseSalary=" + salary.getBaseSalary() + " bonus=" + salary.getBonus())

        }
        System.out.println("write " + salaryList.size() + "done");
    }*/


    public static List<Salary> initSalary(){
        List<Salary> salaryList = new ArrayList<Salary>();
        Salary salary;
        Random random = new Random();
        StringBuffer name = new StringBuffer();
        for(int i=0; i<10000000; i++){
            salary = new Salary();
            salary.setBonus(random.nextInt(1000000));
            salary.setBaseSalary(random.nextInt(1000000));
            for(int j = 0; j < 5;j++){
                name.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
            }
            salary.setName(name.toString());
            salaryList.add(salary);
            name.setLength(0);
        }

        return salaryList;
    }





    public static class Salary {

        private String name;

        private long baseSalary;

        private long bonus;

        private int count;

        public long getBaseSalary() {
            return baseSalary;
        }

        public void setBaseSalary(long baseSalary) {
            this.baseSalary = baseSalary;
        }

        public long getBonus() {
            return bonus;
        }

        public void setBonus(long bonus) {
            this.bonus = bonus;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
