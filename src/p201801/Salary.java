package p201801;

public class Salary implements Comparable<Salary>{

	private String name;
	private int baseSalary;
	private int bonus;
	
	public Salary(String name,int baseSalary,int bonus){
		this.name = name;
		this.baseSalary = baseSalary;
		this.bonus = bonus;
	}
	
	public String toString(){
		return "name = " + name + " （baseSalary*13+bonus）=" + (baseSalary*13 + bonus);
	}
	
	@Override
	public int compareTo(Salary o){
		
		if(this.baseSalary*13 + this.bonus < o.baseSalary*13 + o.bonus){
			return 1;
		}
		if(this.baseSalary*13 + this.bonus == o.baseSalary*13 + o.bonus){
			return 0;
		}
		else{
			return -1;
		}

	}
}
