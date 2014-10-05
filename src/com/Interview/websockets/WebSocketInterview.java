/**
 * @author xiangxiao
 * @since Sep 28, 2014
 * @version 1.7
 */
package com.Interview.websockets;

import java.io.IOException;
import java.util.Set;
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
import org.apache.log4j.Logger;

import com.Interview.bean.Message;
import com.Interview.bean.User;
import com.Interview.service.InterviewService;
import com.Interview.service.UserService;
import com.Interview.util.JsonObjectMapper;
import com.google.gson.JsonObject;

@ServerEndpoint("/websocket")
public class WebSocketInterview {
	private static CopyOnWriteArraySet<WebSocketInterview> connectionSet = new CopyOnWriteArraySet<>();
	private static CopyOnWriteArraySet<WebSocketInterview> adminSet = new CopyOnWriteArraySet<>();
	private Session session;
	User user;
	private AtomicInteger failureCount = new AtomicInteger(0);
	InterviewService is = new InterviewService();
	UserService us = new UserService();

	// private static final Logger logger =
	// Logger.getLogger(WebSocketInterview.class);
	/**
	 * Construct generate connectionId as userId
	 */
	public WebSocketInterview() {
		user = new User();
		System.out.println("init");
	}

	@OnMessage
	public void onMessage(String message) throws IOException,
			InterruptedException, JSONException {
		Message messageObject = new Message();
		JSONObject messageJson = new JSONObject(message);
		try {
			messageObject.setMessageContent(messageJson.getString("messageContent"));
			messageObject.setReplyMessageId(messageJson.getLong("replyMessageId"));   
			messageObject.setUser(user);
			messageObject =is.sendMessage(messageObject);
			message = new JSONObject(JsonObjectMapper.messageMapper(messageObject)).toString();
			MultiCast(message, connectionSet);
		} catch (Exception e) {
			message = "fail to send this message";
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
			session.getBasicRemote().sendText("welcome! \nyour userId is "
			+user.getUserId()+"\nyour nick name is "+user.getNickName());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("connection failure");
			try {
				session.close(new CloseReason(CloseCodes.TLS_HANDSHAKE_FAILURE,
						"invalid user"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@OnClose
	public void onClose(Session session) {
		connectionSet.remove(this);
		try {
			session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE,
					"session closed"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
						try {
							interview.session.close(new CloseReason(
									CloseCodes.TRY_AGAIN_LATER,
									"connection failure"));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
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
	 * send Message to users
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