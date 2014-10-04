package com.Interview.service;
/**
 * @author      Xiang Xiao
 * @version     1.6                              
 * @since       2012-08-05         
 */
import java.util.List;

import com.Interview.bean.User;
import com.Interview.dao.InterviewDao;
import com.Interview.dao.UserDao;
import com.google.gson.JsonObject;

public class UserService{

	/**
	 * User login.for admin and interviewee
	 *
	 * @param accountId  the id of the current user
	 * @param password   the password of the current user
	 * @return           a user object
	 * @throws Exception
	 */
	public User login(long userId, String password)
			throws Exception {
		//JsonObject json = new JsonObject();
		UserDao userDao = new UserDao();
		User user = new User();
		try{
			user = userDao.login(userId, password);
		}
		catch (Exception e){
			if(e.getMessage().equals("invalid"))
				throw e;
			else
				throw new Exception("internal error!");
		}
		return user;
	}

	/**
	 * Checks the role of the current user.
	 *
	 * @param accountId  the id of the current user
	 * @param token      the token corresponding to the current user
	 * @return           role of the user
	 * @throws Exception
	 */
	public boolean checkAuth(long userId, String token) throws Exception {
		boolean success = false;
		UserDao userDao = new UserDao();
		try{
			success = userDao.getAuth(userId, token);
		}
		catch (Exception e){
			e.printStackTrace();
			if(e.getMessage().equals("invalid"))
				throw e;
			else	
				throw new Exception("internal error!");
		}
		return success;
	}


	/**
	 * Registration - adds a new user.
	 *
	 * @param accountId  the id of the new user
	 * @param password   the password of the new user
	 * @param userName   the name of the new user
	 * @param role       the role of the new user
	 * @throws Exception
	 */
	public User register(String nickName) throws Exception {
		UserDao userDao = new UserDao();
		User user=null;
		try {
			user = userDao.register(nickName);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("fail to add new user!");
		}
		return user;
	}
	
	
	
}
