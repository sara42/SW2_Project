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
import com.models.NotificationModel;
import com.models.PlacesModel;
import com.models.SavePlaceModel;
import com.models.UserModel;
import com.models.checkin;

@Path("/")
public class PlaceServices {

	
	@POST
	@Path("/AddPlace")
	@Produces(MediaType.TEXT_PLAIN)
	public String AddNewPlace(@FormParam("name") String name,
			@FormParam("description") String desc,@FormParam("lat") String lat,
			@FormParam("long") String Long) throws NumberFormatException, SQLException
	{
	PlacesModel place=PlacesModel.AddPlace(name, desc,Double.parseDouble(lat), Double.parseDouble(Long));
	JSONObject json = new JSONObject();
	json.put("id", place.getId());
	json.put("name", place.getName());
	json.put("description",place.getDescription());
	json.put("lat", place.getLat());
	json.put("long", place.getLon());
	return json.toJSONString();
	
	}
	///////////////////////////////////////////////////////////////////
	
	@POST
	@Path("/SavePlace")
	@Produces(MediaType.TEXT_PLAIN)
	public String SavePlace(@FormParam("User_ID") String UID,
			@FormParam("Place_ID") String PID ) throws NumberFormatException, SQLException
	{
		SavePlaceModel Splace=SavePlaceModel.Save_place(Integer.parseInt(UID), Integer.parseInt(PID));
		JSONObject Json = new JSONObject();
	    Json.put("uid",Splace.getUser_ID());
	    Json.put("pid",Splace.getPlace_ID());
	     return Json.toJSONString();
	}
	
	/////////////////////////////////////////////////////////////////////////////////
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
	////////////////////////////////////////////////////////////////////////
	@POST
	@Path("/getLastPosition")
	@Produces(MediaType.TEXT_PLAIN)
	public String lastPosition(@FormParam("id1") String id1,@FormParam("id2") String id2) throws NumberFormatException, SQLException {
		String status = PlacesModel.getLastPosition(Integer.parseInt(id1),Integer.parseInt(id2));
		JSONObject json = new JSONObject();
		json.put("status", status );
		return json.toJSONString();
	}
	/////////////////////////////////////////////////////////////////////////
	@POST
	@Path("/Check_IN")
	@Produces(MediaType.TEXT_PLAIN)
	public String chech_in(@FormParam("place_name") String name,@FormParam("user_id") String id) {
		Boolean status = checkin.Check_IN(name,Integer.parseInt(id));
		JSONObject json = new JSONObject();
		json.put("Check_IN ", status);
		return json.toJSONString();
	}
}
