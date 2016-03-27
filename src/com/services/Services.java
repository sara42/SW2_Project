package com.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.models.DBConnection;
import com.models.Followers;
import com.models.PlacesModel;
import com.models.UserModel;

@Path("/")
public class Services {

	/*
	 * @GET
	 * 
	 * @Path("/signup")
	 * 
	 * @Produces(MediaType.TEXT_HTML) public Response signUp(){ return
	 * Response.ok(new Viewable("/Signup.jsp")).build(); }
	 */

	@POST
	@Path("/signup")
	@Produces(MediaType.TEXT_PLAIN)
	public String signUp(@FormParam("name") String name,
			@FormParam("email") String email, @FormParam("pass") String pass) {
		UserModel user = UserModel.addNewUser(name, email, pass);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		return json.toJSONString();
	}

	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@FormParam("email") String email,
			@FormParam("pass") String pass) {
		UserModel user = UserModel.login(email, pass);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		return json.toJSONString();
	}
	
	@POST
	@Path("/updatePosition")
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePosition(@FormParam("id") String id,
			@FormParam("lat") String lat, @FormParam("long") String lon) {
		Boolean status = UserModel.updateUserPosition(Integer.parseInt(id), Double.parseDouble(lat), Double.parseDouble(lon));
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}

	
	
	@POST
	@Path("/GetFollowers")
	@Produces(MediaType.TEXT_PLAIN)
	public String Get_followers(@FormParam("id") String id) throws NumberFormatException, SQLException {
		Vector<UserModel> arr=new Vector<>();
		arr=Followers.Get_followers(Integer.parseInt(id));
		JSONArray json = new JSONArray();
		for(int i=0;i<arr.size();i++)
		{
			JSONObject Json= new JSONObject();
			Json.put("name", (arr.get(i).getName()));
			Json.put("email", (arr.get(i).getEmail()));
			json.add(Json);
		}
		return json.toJSONString();
	}

	
	
	
	
	/////////////////////////////////////////////////////////////////
	
	@POST
	@Path("/Unfollow")
	@Produces(MediaType.TEXT_PLAIN)
	public String UNFollow(@FormParam("ID_1") String id1,
			@FormParam("ID_2") String id2) throws SQLException { 
		
		//Followers followers=new Followers();
		boolean status=Followers.UNfollow(Integer.parseInt(id1),Integer.parseInt(id2));
		//boolean status =  UserModel.unfollowUser(Integer.parseInt(id), Integer.parseInt(followedID));
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}
	
	
	/////////////////////////////////////////////////
	
	
	@POST
	@Path("/followUser")
	@Produces(MediaType.TEXT_PLAIN)
	public String followuser(@FormParam("id") String id,
			@FormParam("followed") String followedID) {
		Boolean status =  Followers.followUser(Integer.parseInt(id), Integer.parseInt(followedID));
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	} 
	
	
	@POST
	@Path("/getLastPosition")
	@Produces(MediaType.TEXT_PLAIN)
	public String lastPosition(@FormParam("id") String id) {
		String status = PlacesModel.getLastPosition(Integer.parseInt(id));
		JSONObject json = new JSONObject();
		json.put("status", status );
		return json.toJSONString();
	}
	
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String getJson() {
		return "Hello after editing";
		// Connection URL:
		// mysql://$OPENSHIFT_MYSQL_DB_HOST:$OPENSHIFT_MYSQL_DB_PORT/
	}
}
