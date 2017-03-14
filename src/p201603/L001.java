package p201603;

import p201601.Salary;

import java.util.*;
import java.util.LinkedList;

/**
 * Created by Administrator on 2016/12/24 0024.
 */
public class L001 {
    public static void main(String[] args){
        List<String> alist = new ArrayList<String>();

        List<String> llist = new LinkedList<String>();

        TreeSet sd;

        HashSet dd;

        Hashtable ddd;

        Queue gg;

        HashMap hashMap = new HashMap();
        hashMap.put("1","1");
        hashMap.put("2","2");
        System.out.println(hashMap.get("1").hashCode());
        System.out.println(hashMap.get("2").hashCode());
        //printTreeSet();
        printTreeSetSalary();

    }

    public static void printTreeSet(){
        Set treeSet = new TreeSet();
        treeSet.add("ddd");
        treeSet.add("qqq");
        treeSet.add("ddd");
        treeSet.add("aaa");
        treeSet.add("sss");

        Iterator it  = treeSet.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }


    public static void printTreeSetSalary(){

        Salary salary1 = new Salary("zhangsang",32);
        Salary salary2 = new Salary("zhangsan23",35);
        Salary salary3 = new Salary("zhangsan4g",5);
        Salary salary4 = new Salary("zhangsang8",88);
        Salary salary5 = new Salary("zhangsang",31);


        Set treeSet = new TreeSet(new Salary.SalaryCompare());
        treeSet.add(salary1);
        treeSet.add(salary2);
        treeSet.add(salary3);
        treeSet.add(salary4);
        treeSet.add(salary5);

        Iterator it  = treeSet.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }
}
