package dao;

import java.util.ArrayList;

import bean.Users;

public interface UserDao {
	public ArrayList<Users> findUsers();
	public boolean findUser(Users user);
	public int insertUser(Users user);
	public int updateUser(Users user);
	public int deleteUser(Users user);

}
