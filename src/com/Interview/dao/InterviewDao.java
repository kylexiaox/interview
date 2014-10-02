/**
 * 
 */
package com.Interview.dao;

import java.util.List;

import com.Interview.bean.Message;

/**
 * @author xiangxiao
 * @since Oct 3, 2014
 * @version 1.7
 */
public class InterviewDao {
  
	public void insertMessage(Message message) {
		
	}
	/**
	 * 
	 * @param index  current messageId,-1 means the latest message
	 * @param number Indicate the number of messages which should been fetched
	 * @return
	 */
	public List<Message> getMessageList(long index,int number) {
		return null;
	}
}
