package biz;

import bean.Users;
import dao.UserDao;
import utils.DaoFactory;;

public class UserService {
	public boolean validateUser(Users user){
		//ȥDB�в���ָ���û����Ϳ�����û�
//		UserDAO userdao = new UserDAOImpl();
		UserDao userdao = (UserDao) DaoFactory.newInstance("UserDao");
		return userdao.findUser(user);		
	}

}
