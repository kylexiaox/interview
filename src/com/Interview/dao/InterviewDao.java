/**
 * 
 */
package com.Interview.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.Driver;

import com.Interview.bean.Message;
import com.Interview.bean.User;
import com.Interview.locator.getSourceLocator;

/**
 * @author xiangxiao
 * @since Oct 3, 2014
 * @version 1.7
 */
public class InterviewDao {

	public void insertMessage(Message message) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getSourceLocator.ds.getConnection();
			String sql = "insert into MESSAGE (REPLY_MESSAGE_ID,"
					+ "USER_ID,USER_NICK_NAME,MESSAGE_CONTENT,TIME_STAMP) "
					+ "values (?,?,?,?,?);";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, message.getReplyMessageId());
			pstmt.setLong(2, message.getUser().getUserId());
			pstmt.setString(3, message.getUser().getNickName());
			pstmt.setString(4, message.getMessageContent());
			pstmt.setTimestamp(5, message.getTime());
			pstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw new Exception("internal error!");
		} finally {
			if (conn != null && pstmt != null) {
				conn.close();
				pstmt.close();
			}
		}
	}

	/**
	 * 
	 * @param index
	 *            current messageId,-1 means the latest message
	 * @param number
	 *            Indicate the number of messages which should been fetched
	 * @return
	 * @throws Exception 
	 */
	public List<Message> getMessageList(long index, int number) throws Exception {
		List<Message> messages = new ArrayList<Message>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = getSourceLocator.ds.getConnection();
			String sql ="SELECT * FROM  message  ORDER BY message.time_stamp DESC LIMIT ?";
			pstmt =conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Message message = new Message();
				User user = new User();
				message.setMessageContent(rs.getString("message_content"));
				message.setMessageId(rs.getLong("message_id"));
				message.setReplyMessageId(rs.getLong("reply_message_id"));
				message.setTime(rs.getTimestamp("time_stamp"));
				user.setNickName(rs.getString("user_nick_name"));
				user.setUserId(rs.getLong("user_id"));
				message.setUser(user);
				messages.add(message);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			if (conn != null && pstmt != null) {
				conn.close();
				pstmt.close();
			}
		}
		return messages;
	}
}
