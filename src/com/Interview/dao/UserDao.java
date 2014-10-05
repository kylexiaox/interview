/**
 * 
 */
package com.Interview.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import org.eclipse.jdt.internal.compiler.ast.FalseLiteral;

import com.Interview.bean.User;
import com.Interview.bean.UserType;
import com.Interview.locator.getSourceLocator;
import com.sun.crypto.provider.RSACipher;

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
	 * @throws Exception 
	 */
	public User register (String nickName) throws Exception{
		Connection conn = getSourceLocator.ds.getConnection();	
		User user = new User();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "insert into users (nick_name, token, user_type) values (?,?,?) returning user_id";
			pstmt = conn.prepareStatement(sql);
			String token = getRandomString(10);
			pstmt.setString(1, nickName);
			pstmt.setString(2, token);
			pstmt.setString(3, "visitor");
			rs = pstmt.executeQuery();
			rs.next();
			user.setUserId(rs.getLong("user_id"));
			user.setNickName(nickName);
			user.setUserType(UserType.visitor);
			user.setToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("internal error!");
		} finally {
			if (conn != null && pstmt != null) {
				conn.close();
				pstmt.close();
			}
		}
		return user;
	}
	/**
	 * check permission
	 * @param userId
	 * @param token
	 * @return
	 * @throws Exception 
	 */
	public User getAuth(long userId,String token) throws Exception{
		Connection conn = getSourceLocator.ds.getConnection();	
		PreparedStatement pstmt = null;
		User user = null;
		ResultSet rs = null;
		try {
			String sql = "select * from users where user_id = ? and token = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, userId);
			pstmt.setString(2, token);
			rs = pstmt.executeQuery();
			if(rs.next()){
				user = new User();
				user.setNickName(rs.getString("nick_name"));
				user.setToken(rs.getString("token"));
				user.setUserId(rs.getLong("user_id"));
				user.setUserType(UserType.valueOf(rs.getString("user_type")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("internal error!");
		} finally {
			if (conn != null && pstmt != null) {
				conn.close();
				pstmt.close();
			}
		}
		return user;
	}
	/**
	 * method to get token 
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {  
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 }     
}
