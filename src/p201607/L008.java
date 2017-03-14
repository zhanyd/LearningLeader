package p201607;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Created by Administrator on 2017/1/25 0025.
 */
public class L008 {
    public static void main(String[] args) throws Exception{
        ExecutorService executor = Executors.newFixedThreadPool(5);

        List<Future<Long>> futures = Files.list(new File("D:/Downloads").toPath())
                .filter(s->!s.toFile().isDirectory())
                .map(s->new Callable<Long>(){
                        @Override
                        public Long call() throws Exception{
                            System.out.println(s);
                            return Files.size(s);
                        }
                    })
                .map(c->executor.submit((Callable<Long>)c))
                .collect(Collectors.toList());

       /* List<Long> fileSizeList = futures.stream()
                .map(f->{
                    try{
                        return f.get();
                    }catch(Exception e){
                        e.printStackTrace();
                        return -1;
                    }
                })
                .mapToLong(val->(Long)val)
                .collect(Collectors.toList());*/


        Supplier<LongStream> streamSupplier = ()->futures.stream()
                .map(f->{
                    try{
                        return f.get();
                    }catch(Exception e){
                        e.printStackTrace();
                        return -1;
                    }
                })
                .mapToLong(val->(Long)val);

        streamSupplier.get().forEach(System.out::println);
        System.out.println("total:" + streamSupplier.get().sum());
    }
}
