package p201604;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static p201604.L003.Person.easternStyle;
import static p201604.L003.Person.westernStyle;

/**
 * Created by Administrator on 2016/12/31 0031.
 */
public class L003 {

    public static void main(String[] args){
        List<Person> list = new ArrayList<Person>();
        list.add(new Person(23,"zhangsang","温州"));
        list.add(new Person(25,"lisi","杭州"));
        list.add(new Person(27,"wangwu","上海"));

        for(Person person : list){
            System.out.println(person.printCustom(westernStyle));

            System.out.println(person.printCustom(easternStyle));
        }
    }



    static class Person{
        int age;
        String name;
        String address;

        public Person(int age,String name,String address){
            this.age = age;
            this.name = name;
            this.address = address;
        }

        public String printCustom(Function<Person,String> f){
            return f.apply(this);
        }


        static Function<Person,String> westernStyle = p->{
            return p.name + " " + p.age + " " + p.address;
        };

        static  Function<Person,String> easternStyle = p->{
            return p.address + " " + p.name + " " + p.age;
        };
    }
}
