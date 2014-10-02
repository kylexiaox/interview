package com.Interview.service;

import java.util.Vector;

import com.Interview.bean.Message;
/**
 * singleton cache for conversation
 * @author xiangxiao
 * @date Oct 3, 2014
 * @version 1.7
 */
public class ConversationCache {
	
	
	private static ConversationCache instance = new ConversationCache();
	private Vector<Message> cacheMessages = new Vector<Message>();
	public Vector<Message> getMessagesCached() {
		return this.cacheMessages;
	}
	private ConversationCache() {
		
	}
	public static ConversationCache getInstance() {
		return instance;
	}
}
