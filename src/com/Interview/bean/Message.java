package com.Interview.bean;

import java.sql.Timestamp;

public class Message {
	private long messageId;
	private User user;
	private long replyMessageId = -1;
	private String MessageContent;
	private Timestamp time;
	
	public long getReplyMessageId() {
		return replyMessageId;
	}
	public void setReplyMessageId(long replyMessageId) {
		this.replyMessageId = replyMessageId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public long getMessageId() {
		return messageId;
	}
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	public String getMessageContent() {
		return MessageContent;
	}
	public void setMessageContent(String messageContent) {
		MessageContent = messageContent;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
}
