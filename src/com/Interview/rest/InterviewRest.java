package com.Interview.rest;
/**
 * @author      Xiang Xiao
 * @version     1.6                              
 * @since       2012-08-05         
 */
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.Interview.bean.Message;
import com.Interview.bean.User;
import com.Interview.service.UserService;
import com.google.gson.JsonArray;

@Path("/rest")
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
		try {
			//userBean = userService.login(user.getString("username").toString(), user.getString("password").toString());
			JSONObject jo = new JSONObject();
//			jo.put("token", userBean.getToken());
//			jo.put("role", userBean.getRole());
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
		User user = null;
		try {
			user = userService.register(nickName);
			response = Response.ok("success").build();
			
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
	@POST
	@Path("/retriveChatList/{index}/{type}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getUnsignedStudentListByProject(@PathParam("index") String index,@PathParam("type") String type){
	//	com.service.UserService userService = new com.service.UserService();
		Response response = null;
		List<Message> messages;
//		try {
//			//userBeans = userService.getUnsginedStudentByProject(accountId, projectId);
//			JSONArray jArray = new JSONArray();
//			for(int i = 0; i<messages.size();i++){
//				JSONObject jObject = new JSONObject();
//				//jObject.put("studentId", userBeans.get(i).getAccountId());
//				jObject.put("name", messages.get(i).getUserName());
//				jArray.put(jObject);
//				}
//			response = Response.ok(jArray.toString()).build();
//		}
//		catch (Exception e){
//			if (e.getMessage().equals("permision denied"))
//				response = Response.status(Response.Status.FORBIDDEN).build();
//			else
//				response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//		}
		return response;
	}

}
