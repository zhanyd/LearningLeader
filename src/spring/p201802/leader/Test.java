package spring.p201802.leader;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.ho.yaml.Yaml;

import spring.p201802.leader.bean.User;
import spring.p201802.leader.bean.UserSession;
import spring.p201802.leader.service.UserService;

public class Test {

	public static void main(String[] args) throws FileNotFoundException {
		
		HashMap yamMap =Yaml.loadType(Thread.currentThread().getContextClassLoader().getResourceAsStream("common.yaml"),HashMap.class);
		UserService userService = new UserService(String.valueOf(yamMap.get("path")));
		
		User user1 = new User(1,"li","12345",true,new Date());
		userService.createUser(user1);
		user1 = new User(2,"zhan","123",true,new Date());
		userService.createUser(user1);
		user1 = new User(3,"wang","1234",true,new Date());
		userService.createUser(user1);
		user1 = new User(4,"hu","1234",true,new Date());
		userService.createUser(user1);
		List<User> userList = userService.getAllUser();
		System.out.println("所有:");
		userList.stream().forEach(System.out::println);

		System.out.println("删除后:");
		userService.deleteUser(4);
		userList = userService.getAllUser();
		userList.stream().forEach(System.out::println);
		
		System.out.println("查找:");
		userList = userService.queryUsers("zhan", true);
		userList.stream().forEach(System.out::println);
		
		System.out.println("登录:");
		UserSession userSession = userService.login("zhan", "123");
		System.out.println(userSession.isValid());
	}

}
