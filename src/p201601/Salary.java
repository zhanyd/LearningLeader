package p201601;

import java.util.Comparator;

/**
 * Created by Administrator on 2016/12/13 0013.
 */
public class Salary {

    public Salary(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    private int salary;


    /*@Override
    public int compareTo(Object o){
        //return this.salary - ((Salary)o).getSalary();
        return ((Salary)o).getSalary() - this.salary;
    }*/

    public static class SalaryCompare implements Comparator<Salary> {

        @Override
        public int compare(Salary o1, Salary o2) {
            return o1.getSalary() - o2.getSalary();
        }

    }

    public String toString(){
        return this.name + ":" + this.salary;
    }


}
