/**
 * 
 */
package com.Interview.dao;


import com.Interview.bean.User;

/**
 * @author xiangxiao
 * @date Oct 2, 2014
 * @version 1.7
 */
public class UserDao {
	/**
	 * login for interviewee and admin
	 * @param userId  
	 * @param password
	 * @return
	 */
	public User login (long userId, String password){
		
		return null;
	}
	/**
	 * add new user to dataBase
	 * @param nickName
	 * @return
	 */
	public User register (String nickName){
		
		return null;
	}
	/**
	 * check permission
	 * @param userId
	 * @param token
	 * @return
	 */
	public boolean getAuth(long userId,String token){
		
		return false;
	}

}
