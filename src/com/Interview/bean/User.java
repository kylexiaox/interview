/**
 * 
 */
package com.Interview.bean;

/**
 * @author xiangxiao
 * @since Oct 2, 2014
 * @version 1.7
 */
public class User {

	private long userId;
	private String nickName;
	private String token;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	
}
