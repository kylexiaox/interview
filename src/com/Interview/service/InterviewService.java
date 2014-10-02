package com.Interview.service;

import java.util.Vector;

import com.Interview.bean.Message;
import com.Interview.bean.UserType;
/**
 * Web Logic
 * @author kyle_xiao
 *
 */
public class InterviewService {
	/**
	 *  identify the user Type 
	 * @param userName
	 * @param passwd
	 * @return
	 */
	public UserType login(String userName,String passwd) {
		UserType type;
		type = UserType.admin;
		//Call Dao
		return type;
	}
	/**
	 * get cached Message list
	 * @return
	 */
	public Vector<Message> getCurrentMessages() {
		return MessageCache.getInstance().getMessagesCached();
	}
	

}
