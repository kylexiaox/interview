package com.Interview.service;

import java.util.Vector;

import com.Interview.bean.Message;
/**
 * singleton object cache for messages
 * @author xiangxiao
 * @date Oct 3, 2014
 * @version 1.7
 */
public class MessageCache {
	
	private final int _cacheNumber = 200;
	
	private static MessageCache instance = new MessageCache();
	private Vector<Message> cacheMessages = new Vector<Message>(_cacheNumber);
	public Vector<Message> getMessagesCached() {
		return this.cacheMessages;
	}
	private MessageCache() {
	}
	public static MessageCache getInstance() {
		return instance;
	}
}
