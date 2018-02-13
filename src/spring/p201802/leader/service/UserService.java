package spring.p201802.leader.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import spring.p201802.leader.bean.User;
import spring.p201802.leader.bean.UserSession;


public class UserService {

	
	File file;
	Map<String,UserSession> sessionMap = new HashMap<>();
	
	public UserService(String path){
		this.file = new File(path);
	}
	
	public boolean createUser(User user)
	{
		List<User> userList = getAllUser();
		userList.add(user);
		putUserList(userList);
		return true;
	}
	public boolean deleteUser(long userId)
	{
		List<User> userList = getAllUser();
		userList = userList.stream().filter(s->s.getUserId() != userId).collect(Collectors.toList());
		putUserList(userList);
		return true;
	}
	public boolean disableUser(long userId)
	{
		List<User> userList = getAllUser();
		userList.stream().filter(s->s.getUserId() == userId).forEach(s->s.setEnabled(false));
		putUserList(userList);
		return true;
	}
	public List<User> queryUsers(String userNamePrex,boolean onlyValidUser)
	{
		List<User> userList = getAllUser();
		userList = userList.stream().filter(s->s.getUserName().startsWith(userNamePrex) && s.isEnabled() == true).collect(Collectors.toList());
		return userList;
	}
	/**
	 * 如果密码不对，返回的UserSession对象里sessionId为空，客户端可以依次判断，参照UserSession.isValid方法
	 * @param userName
	 * @param md5EncodedPassword
	 * @return
	 */
	public UserSession login(String userName,String md5EncodedPassword)
	{
		UserSession userSession;
		List<User> userList = getAllUser();
		Optional<User> user = userList.stream()
				.filter(s->s.getUserName().equals(userName) && s.getPassword().equals(md5EncodedPassword) && s.isEnabled() == true)
				.findAny();
		if(user.isPresent()){
			String sessionId = UUID.randomUUID().toString();
			userSession = new UserSession(sessionId,user.get().getUserId(),user.get().getUserName(),new Date().getTime(),(short)1000);
			sessionMap.put(sessionId, userSession);
		}else{
			userSession = new UserSession();
		}
		return userSession;
	}
	
	
	public List<User> getAllUser(){
		if(!file.exists()){
			try {
				file.createNewFile();
				return new ArrayList<User>();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		List<User> userList = new ArrayList<User>();
		try(ObjectInputStream objInputStream = new ObjectInputStream(new FileInputStream(file))){
			while(true){
				Object object = objInputStream.readObject();
				if(object == null){
					break;
				}
				userList.add((User)object);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return userList;
	}
	
	public void putUserList(List<User> userList){
		try(ObjectOutputStream objOutputStream = new ObjectOutputStream(new FileOutputStream(file))){
			userList.stream().forEach(u->{
				try {
					objOutputStream.writeObject(u);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			//结束符
			objOutputStream.writeObject(null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
