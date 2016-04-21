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
import com.models.Like;
import com.models.NotificationModel;
import com.models.PlacesModel;
import com.models.SavePlaceModel;
import com.models.UserModel;

@Path("/")
public class NotificationServices {
	@POST
	@Path("/GetNotifications")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@FormParam("NotifiedID") String ID) throws NumberFormatException, SQLException {
		Vector<NotificationModel> notification=NotificationModel.getNotification(Integer.parseInt(ID));
		JSONArray json = new JSONArray();
		for(int i=0;i<notification.size();i++)
		{
			JSONObject Json= new JSONObject();
			Json.put("Type ", (notification.get(i).getType()));
			Json.put("Type ID ", (notification.get(i).getTypeID()));
			Json.put("User ID ", (notification.get(i).getUID()));
			Json.put("User Nmae ", (notification.get(i).getUname()));
			json.add(Json);
			
		}
		return json.toJSONString();
	}
/////////////////////////////////////////////////////////
	@POST
	@Path("/Like")
	@Produces(MediaType.TEXT_PLAIN)
	public String like(@FormParam("checkin_Id") String Cid,@FormParam("user_id") String Uid,@FormParam("chUser_id") String ChUid) {
		Boolean status =Like.makeLike(Integer.parseInt(Cid),Integer.parseInt(Uid),Integer.parseInt(ChUid));
		JSONObject json = new JSONObject();
		json.put("Like ", status);
		return json.toJSONString();
	}
	/////////////////////////////////////////////////////////////////
	@POST
	@Path("/Comment")
	@Produces(MediaType.TEXT_PLAIN)
	public String comment(@FormParam("checkin_Id") String Cid,@FormParam("user_id") String Uid,@FormParam("chUser_id") String ChUid) {
		Boolean status =com.models.Comment.makeComment (Integer.parseInt(Cid),Integer.parseInt(Uid),Integer.parseInt(ChUid));
		JSONObject json = new JSONObject();
		json.put("Comment ", status);
		return json.toJSONString();
	}

}
