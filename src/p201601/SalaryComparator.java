package p201601;

import java.util.Comparator;

public class SalaryComparator implements Comparator<Salary>{

	@Override
	public int compare(Salary o1,Salary o2){
		if(o1.getBaseSalary()*13 + o1.getBonus() < o2.getBaseSalary()*13 + o2.getBonus()){
			return 1;
		}
		if(o1.getBaseSalary()*13 + o1.getBonus() == o2.getBaseSalary()*13 + o2.getBonus()){
			return 0;
		}
		else{
			return -1;
		}
	}
}
