package com.Interview.service;

import java.util.Vector;

import com.Interview.bean.Message;

public class MessageCache {
	
	private static MessageCache instance = new MessageCache();
	private Vector<Message> cacheMessages = new Vector<Message>();
	public Vector<Message> getMessagesCached() {
		return this.cacheMessages;
	}
	private MessageCache() {
		
	}
	public static MessageCache getInstance() {
		return instance;
	}
}
