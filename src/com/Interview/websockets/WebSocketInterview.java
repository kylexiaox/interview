/**
 * @author Xiang Xiao
 * @since Sep 28, 2014
 * @version 1.7
 */
package com.Interview.websockets;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.tomcat.jni.Directory;

import com.Interview.bean.Message;
import com.Interview.bean.User;
import com.Interview.bean.UserType;
import com.Interview.service.InterviewService;
import com.Interview.service.UserService;
import com.Interview.util.JsonObjectMapper;
import com.apple.laf.ClientPropertyApplicator.Property;
import com.google.gson.JsonObject;
import com.sun.research.ws.wadl.Request;
import com.sun.xml.internal.fastinfoset.sax.Properties;

@ServerEndpoint("/websocket")
public class WebSocketInterview {
	private static CopyOnWriteArraySet<WebSocketInterview> connectionSet = new CopyOnWriteArraySet<>();
	public static CopyOnWriteArrayList<User> adminList = new CopyOnWriteArrayList<>();
	private static CopyOnWriteArraySet<WebSocketInterview> adminSet= new CopyOnWriteArraySet<WebSocketInterview>();
	private Session session;
	private static User user;
	private AtomicInteger failureCount = new AtomicInteger(0);
	InterviewService is = new InterviewService();
	UserService us = new UserService();

	private static final Logger logger = Logger
			.getLogger(WebSocketInterview.class);

	/**
	 * Construct generate connectionId as userId
	 */
	public WebSocketInterview() {
		user = new User();
		String prefix = System.getProperty("catalina.base");
		String path = prefix + "/logs/OnlineInterview/log4j.properties";
		PropertyConfigurator.configure(path);
		System.out.println(path);
		logger.info("init");
		System.out.println("init");
	}

	@OnMessage
	public void onMessage(String message) throws IOException,
			InterruptedException, JSONException {
		Message messageObject = new Message();
		JSONObject messageJson = new JSONObject(message);
		try {
			messageObject.setMessageContent(messageJson
					.getString("messageContent"));
			messageObject.setReplyMessageId(messageJson
					.getLong("replyMessageId"));
			messageObject.setUser(user);
			messageObject = is.sendMessage(messageObject);
			message = new JSONObject(
					JsonObjectMapper.messageMapper(messageObject)).toString();
			MultiCast(message, connectionSet);
			logger.info("message with Id : " + messageObject.getMessageId()
					+ " have been sent");
		} catch (Exception e) {
			logger.info("message with Id : " + messageObject.getMessageId()
					+ " failed to send");
			MultiCast(message, this);
			e.printStackTrace();
		}
	}

	/**
	 * start websocket session,and check auth
	 * 
	 * @param session
	 * @throws
	 */
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("started");
		this.session = session;
		try {
			long userId = Long.parseLong(session.getRequestParameterMap()
					.get("userId").get(0).toString());
			String token = session.getRequestParameterMap().get("token").get(0)
					.toString();
			user = us.checkAuth(userId, token);
			if (user == null)
				throw new Exception("invalid user");
			connectionSet.add(this);
			if(user.getUserType()==UserType.interviewee||user.getUserType()==UserType.interviewer){
				adminList.add(user);
				adminSet.add(this);
				MultiCast(JsonObjectMapper.userMapper(user).remove("token").toString(),connectionSet);
			}
			session.getBasicRemote().sendText(
					"welcome! \nyour userId is " + user.getUserId()
							+ "\nyour nick name is " + user.getNickName());
			logger.info("user " + user.getUserId() + " have joined!");
		} catch (Exception e) {
			logger.info(" wrong token!");
			System.out.println("connection failure");
			e.printStackTrace();
		}
	}

	@OnClose
	public void onClose(Session session) {
		connectionSet.remove(this);
		if(user!=null)
			logger.info("user " + user.getUserId() + " have left!");
		else {
			logger.info("user failed to login");
		}
	}

	/**
	 * send Message to users
	 * 
	 * @param message
	 * @param interviewWSset
	 */
	private static void MultiCast(String message,
			Set<WebSocketInterview> webSocketInterview) {
		for (WebSocketInterview interview : webSocketInterview) {
			synchronized (interview) {
				try {
					interview.session.getBasicRemote().sendText(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					interview.failureCount.incrementAndGet();
					if (interview.failureCount.intValue() > 3) {
						connectionSet.remove(interview);
						logger.info("user " + user.getUserId()
								+ " have failed in connection!");
					} else {
						// repeat
						try {
							interview.session.getBasicRemote()
									.sendText(message);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		}
	}

	/**
	 * send Message to a user
	 * 
	 * @param message
	 * @param interviewWSset
	 */
	private static void MultiCast(String message, WebSocketInterview interview) {
		synchronized (interview) {
			try {
				interview.session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				interview.failureCount.incrementAndGet();
				if (interview.failureCount.intValue() > 3) {
					connectionSet.remove(interview);
					try {
						interview.session.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						logger.info("user " + user.getUserId()
								+ " have failed in connection!");
						e1.printStackTrace();
					}
				} else {
					// repeat
					try {
						interview.session.getBasicRemote().sendText(message);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}
}