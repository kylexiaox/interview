package com.Interview.rest;
/**
 * @author      Xiang Xiao
 * @version     1.7                             
 * @since       Sep 29 2014        
 */
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import com.Interview.bean.Message;
import com.Interview.bean.User;
import com.Interview.service.InterviewService;
import com.Interview.service.UserService;
import com.Interview.util.JsonObjectMapper;

@Path("")
public class InterviewRest {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	/**
	 * User login. for interviewer and interviewee
	 *
	 * @param user 		 a user object with "username" and "password" encapsulated in a JSON object
	 * @return           response
	 * @throws Exception
	 */
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response userLogin(JSONObject user){
		Response response = null;
		//User userBean;
		UserService userService = new UserService();
		try {
			User userBean = userService.login(user.getLong("userId"), user.getString("password"));
			JSONObject jo = new JSONObject();
			jo.put("token", userBean.getToken());
			jo.put("role", userBean.getUserId());
			jo.put("type", userBean.getUserType());
			response = Response.ok(jo).build();
		}
		catch (Exception e){
			if (e.getMessage().equals("invalid"))
				response = Response.status(Response.Status.UNAUTHORIZED).build();
			else
				response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	/**
	 * new user in
	 * @param user
	 * @return
	 */
	@POST
	@Path("/register/{nickName}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response userRegister(@PathParam("nickName")String nickName){
		UserService userService = new UserService();
		Response response = null;
		try {
			User user = userService.register(nickName);
			JSONObject jo = new JSONObject(JsonObjectMapper.userMapper(user));
			response = Response.ok(jo).build();	
		}
		catch (Exception e){
			if (e.getMessage().equals("person Exist"))
				response = Response.status(Response.Status.FORBIDDEN).build();
			else
				response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	/**
	 * List Chat with 10 more recods
	 *
	 * @param index   current MessageId
	 * @param type    visit Type : 1 for all,2 for conversation
	 * @param token   token of admin
	 * @return              response
	 * @throws Exception
	 */
	@GET
	@Path("/retriveChatList/{index}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response retriveChatList (@PathParam("index") long index){
		InterviewService interviewService = new InterviewService();
		Response response = null;
		List<Message> messages;
		try {
			messages = interviewService.getMessages(index, 20);	
			JSONArray jArray = new JSONArray();
			for(int i = 0; i<messages.size();i++){
				JSONObject jObject = new JSONObject(JsonObjectMapper.messageMapper(messages.get(i)));			
				jArray.put(jObject);
				}
			response = Response.ok(jArray.toString()).build();
		}
		catch (Exception e){
			if (e.getMessage().equals("wrong tpye!"))
				response = Response.status(Response.Status.FORBIDDEN).build();
			else
				response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}
	
	/**
	 * List latest messages
	 * @return response
	 * @throws Exception
	 */
	@GET
	@Path("/retriveLatest")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response retriveLatestChatList (){
		InterviewService interviewService = new InterviewService();
		Response response = null;
		List<Message> messages;
		try {
			messages = interviewService.getCurrentMessages(30);	
			JSONArray jArray = new JSONArray();
			for(int i = 0; i<messages.size();i++){
				JSONObject jObject = new JSONObject(JsonObjectMapper.messageMapper(messages.get(i)));
				jArray.put(jObject);
				}
			response = Response.ok(jArray.toString()).build();
		}
		catch (Exception e){
			if (e.getMessage().equals("wrong tpye!"))
				response = Response.status(Response.Status.FORBIDDEN).build();
			else
				response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	// test
		@GET
		@Produces(MediaType.TEXT_HTML)
		public String sayHelloHtml() {
			return "<html> " + "<title>" + "Hello Jersey" + "</title>"
					+ "<body><h1>" + "Hello World in REST" + "</body></h1>" + "</html> ";
		}

}
