package com.Interview.bean;
/**
 * @author xiangxiao
 * @since Oct 2, 2014
 * @version 1.7
 */
import java.sql.Timestamp;

public class Message {
	private long messageId;
	private long replyMessageId;
	private User user;
	private String MessageContent;
	private Timestamp time;
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
	public long getReplyMessageId() {
		return replyMessageId;
	}
	public void setReplyMessageId(long replyMessageId) {
		this.replyMessageId = replyMessageId;
	}
}
