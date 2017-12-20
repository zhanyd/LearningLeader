package p201601;

import java.util.Comparator;

/**
 * Created by Administrator on 2016/12/13 0013.
 */
public class Salary {

	private String name;
	private int baseSalary;
	private int bonus;
	
	
	public Salary(String name,int baseSalary,int bonus){
		this.name = name;
		this.baseSalary = baseSalary;
		this.bonus = bonus;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(int baseSalary) {
		this.baseSalary = baseSalary;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}




    /*@Override
    public int compareTo(Object o){
        //return this.salary - ((Salary)o).getSalary();
        return ((Salary)o).getSalary() - this.salary;
    }*/

   /* public static class SalaryCompare implements Comparator<Salary> {

        @Override
        public int compare(Salary o1, Salary o2) {
            return o1.getSalary() - o2.getSalary();
        }

    }*/

    public String toString(){
		return "name = " + name + " （baseSalary*13+bonus）=" + (baseSalary*13 + bonus);
	}


}
