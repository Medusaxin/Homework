package biz;

import bean.Users;
import dao.UserDao;
import utils.DaoFactory;;

public class UserService {
	public boolean validateUser(Users user){
		//去DB中查找指定用户名和口令的用户
//		UserDAO userdao = new UserDAOImpl();
		UserDao userdao = (UserDao) DaoFactory.newInstance("UserDao");
		return userdao.findUser(user);		
	}

}
