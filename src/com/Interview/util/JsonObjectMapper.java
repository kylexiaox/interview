/**
 * 
 */
package com.Interview.util;

import java.util.HashMap;
import java.util.Map;

import com.Interview.bean.Message;
import com.Interview.bean.User;

/**
 * @author xiangxiao
 * @since Oct 5, 2014
 * @version 1.7
 */
public class JsonObjectMapper {

	public static Map<String, Object> userMapper(User user) {
		Map<String,Object> userMap = new HashMap<String, Object>();
		userMap.put("nickName", user.getNickName());
		userMap.put("token", user.getToken());
		userMap.put("userId", user.getUserId());
		userMap.put("type", user.getUserType());		
		return userMap;
	}
	
	public static Map<String, Object> messageMapper(Message message) {
		Map<String,Object> messageMap = new HashMap<String, Object>();
		messageMap.put("messageId", message.getMessageId());
		messageMap.put("messageContent", message.getMessageContent());
		messageMap.put("userId", message.getUser().getUserId());
		messageMap.put("nickName", message.getUser().getNickName());
		messageMap.put("replyMessageId", message.getReplyMessageId());
		messageMap.put("timeStamp", message.getTime());
		return messageMap;
	}
}
