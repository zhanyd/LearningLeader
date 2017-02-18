package p201604;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class L007 {

	public L007() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		//Arrays.stream(new int[]{1,2,2,3}).map(s->2*s+1).forEach(System.out::println);
		
		Stream.of("a1","a2","A3","A7").map(s->s.substring(1)).mapToInt(Integer::parseInt)
		.max().ifPresent(System.out::println);
		
		List<Person> persons = new ArrayList<Person>();
		for(int i = 0; i < 10; i++){
			persons.add(new Person(i,"name" + i));
		}
		
		persons.stream().reduce((p1, p2)->p1.age > p2.age?p1:p2)
			.ifPresent(p->System.out.println(p.name + " " + p.age));
		
		Person result = persons.stream().reduce(new Person(0,""), (p1,p2)->{
			p1.age += p2.age;
			p1.name += p2.name;
			return p1;
		});
		System.out.println(result.name + " " + result.age);
		
		Integer ageSum = persons.stream().reduce(0,(sum,p)->sum += p.age,(sum1,sum2)->sum1+sum2);
		System.out.println(ageSum);
		
		
		Integer ageSum2 = persons.parallelStream().reduce(0,(sum,p)->sum += p.age,(sum1,sum2)->sum1+sum2);
		System.out.println(ageSum2);
		
		Integer ageSum3 = persons.parallelStream().reduce(0,(sum,p)->{
			System.out.format("sum=%s,person=%s[%s]\n", sum,p,Thread.currentThread().getName());
			return sum += p.age;
		},
		(sum1,sum2)->{
			System.out.format("sum1=%s,sum2=%s[%s]\n", sum1,sum2,Thread.currentThread().getName());
			return sum1+sum2;
		});
		System.out.println(ageSum3);
		
		
		persons.stream()
		.filter(p->p.age > 2)
		.skip(3)
		.sorted((p1,p2)->Integer.compare(p2.age, p1.age))
		
		.forEachOrdered(p->System.out.println(p.name + " " + p.age));
		
		
		long count = Stream.of("d1","a2","b1","b2","c").filter(s->{
			System.out.println("filter:" + s);
			return true;
		}).count();
		System.out.println(count);
		
		System.out.println("---------------");
		
		boolean match = Stream.of("d1","a2","b1","b2","c")
			.map(s->{
				System.out.println("map:" + s);
				return s.toUpperCase();
			})
			.anyMatch(s->{
				System.out.println("anyMatch:" + s);
				return s.startsWith("B");
			});
		/*	.noneMatch(s->{
				System.out.println("noneMatch:" + s);
				return s.startsWith("B");
			});*/
		
		System.out.println(match);
		System.out.println("---------------");
		
		Supplier<Stream<String>> streamSupplier = ()->Stream.of("d1","a2","b1","b2","c").filter(s->s.startsWith("a"));
		
		System.out.println(streamSupplier.get().noneMatch(s->true));
		System.out.println(streamSupplier.get().anyMatch(s->true));
		
		System.out.println("---------------");
		
		List<Person> filtered = persons.stream()
				.filter(p->p.name.startsWith("n"))
				.collect(Collectors.toList());
		System.out.println(filtered);
		
	        persons
			.stream()
			.collect(Collectors.groupingBy(p->p.age))
			.forEach((age,p)->System.out.format("age %s:%s\n", age,p));
	        
	        Double averageAge = persons
	        		.stream()
	        		.collect(Collectors.averagingDouble(p->p.age));
	        System.out.println(averageAge);	
	        System.out.println("---------------");
	        
	        List<String> list2 = Arrays.asList("adf","bac","abc","hrd","gtd","biu");
	        String result2 = list2.parallelStream().collect(StringBuilder::new,
	        		(res,item)->{
	        			res.append("").append(item);
						System.out.println("parallelStream");
	        		},
	        		(res1,res2)->{
	        			System.out.format(Thread.currentThread().getName() + " res1=%s,res2=%s \r\n",res1,res2);
	        			res1.append(res2);
	        		}).toString();
	        System.out.println(result2);
		System.out.println("---------------");
	        String result3 = list2.stream().collect(StringBuilder::new,
	        		(res,item)->{
	        			res.append("").append(item);
						System.out.println("stream");
	        		},
	        		(res1,res2)->{
	        			System.out.format(Thread.currentThread().getName() + " res1=%s,res2=%s \r\n",res1,res2);
	        			res1.append(res2);
	        		}).toString();
	        System.out.println(result3);




	       	
	}
	
	
	static class Person{
		int age;
		String name;
		
		public Person(int age,String name){
			this.age = age;
			this.name = name;
		}
		
		public String toString(){
			return name;
		}
		
	}

}
