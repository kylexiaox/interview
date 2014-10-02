package com.Interview.service;

import java.util.List;
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
	 * get cached Message list
	 * @return
	 */
	public List<Message> getCurrentMessages() {
		return MessageCache.getInstance().getMessagesCached();
	}
	
	public List<Message> getCurrentConversation() {
		return null;
	}
	
	public void sendMessage(Message message){
		
	}
	
	//我在纠结是自己写缓存还是用memcache..
	public void refreshCache(Message message){
		if(message.getReplyMessageId()!=-1){
			
		}
			
	}

}
