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

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;


 
@ServerEndpoint("/websocket")
public class WebSocketInterview{
	private static final AtomicInteger connectionId = new AtomicInteger(0);
	private static CopyOnWriteArraySet<WebSocketInterview> connectionSet = new CopyOnWriteArraySet<>();
	private static CopyOnWriteArraySet<WebSocketInterview> adminSet = new CopyOnWriteArraySet<>();
	private Session session;
	private AtomicInteger failureCount = new AtomicInteger(0);
	//private static final Logger logger = Logger.getLogger(WebSocketInterview.class);
	/**
	 * Construct generate connectionId as userId
	 */
	public WebSocketInterview() {
		connectionId.incrementAndGet();
		System.out.println("init");
	}
 
  @OnMessage
  public void onMessage(String message, Session session)
    throws IOException, InterruptedException {
	// call service
	  message = connectionId+" ："+message;
	  MultiCast(message, connectionSet);
  }
   
  @OnOpen
	public void onOpen(Session session) {
		System.out.println("started");
		this.session = session;
		connectionSet.add(this);
	}

	@OnClose
	public void onClose(Session session) {
		connectionSet.remove(this);
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
							interview.session.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						// repeat
						try {
							interview.session.getBasicRemote().sendText(
									message);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		}
	}
}