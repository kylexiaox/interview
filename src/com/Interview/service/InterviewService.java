package com.Interview.service;

import java.util.List;
import java.util.Vector;

import com.Interview.bean.Message;
import com.Interview.bean.UserType;
import com.Interview.dao.InterviewDao;
/**
 * Web Logic
 * @author kyle_xiao
 *
 */
public class InterviewService {
	
	private InterviewDao interviewDao = new InterviewDao();

	/**
	 * get cached Message list
	 * @return
	 * @throws Exception 
	 */
	public List<Message> getCurrentMessages(long currentIndex) throws Exception {
		
		return interviewDao.getMessageList(currentIndex, 20);
	}
	
	
	public void sendMessage(Message message) throws Exception{
		interviewDao.insertMessage(message);
	}
	
	//我在纠结是自己写缓存还是用memcache..
	public void refreshCache(Message message){
		if(message.getReplyMessageId()!=-1){
			
		}
			
	}

}
