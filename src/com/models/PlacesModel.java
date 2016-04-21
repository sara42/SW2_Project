package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class PlacesModel {
	
	private static String name;
	private Integer id;
	private Double lat;
	private Double lon;
	private String description;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	
	/*public static String getLastPosition (Integer id)
	{
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = " Select * from users where  `id`= ?  ";
			String name=null;

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,id );
			stmt.executeUpdate();
			Double lon=0.0,lat=0.0;
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				lon= rs.getDouble(5);
				lat=rs.getDouble(6);
			}
			String Sql = " Select * from places where  `long`= ? and `lat`=? ";
			PreparedStatement stm;
			stm = conn.prepareStatement(Sql);
			stm.setDouble(1, lon);
			stm.setDouble(2, lat);
			ResultSet ls=stm.executeQuery();
			while (ls.next())
			name=ls.getString(2);
			
		}catch(SQLException e){
			e.printStackTrace();
		}
				
		
		return name;
	}*/
	
	
	public static String getLastPosition (int follower_id,int user_id ) throws SQLException
	{
		//Double  lon=0.0, lat=0.0;
		
			boolean flag=Followers.Is_follower(follower_id, user_id);
			if (flag) ////lw l2ahom 5las kda kda followed 
			{
			UserModel user=UserModel.select_user(user_id);
			Connection conn = DBConnection.getActiveConnection();
			String Sql = "Select * from places where  `lat`= ? and `long`=? ";
			PreparedStatement st;
			st = conn.prepareStatement(Sql);
			st.setDouble(1, user.getLat());
			st.setDouble(2, user.getLon());
			ResultSet ls = st.executeQuery();
			if (ls.next())    name=ls.getString("name");
			return name;
			}
			return null;
	}

	public static PlacesModel AddPlace(String name,String Description,double lat,double Long) throws SQLException
	{
		Connection conn = DBConnection.getActiveConnection();
		String sql="INSERT INTO places ( `name`, `description`, `lat`, `long`) VALUES (?,?,?,?)";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1,name);
		stmt.setString(2,Description);
		stmt.setDouble(3, lat);
		stmt.setDouble(4, Long);
		stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();
		if (rs.next()) {
			PlacesModel place=new PlacesModel();
			place.id=rs.getInt(1);
			place.name=name;
			place.description=Description;
			place.lat=lat;
			place.lon=Long;
			return place;
		} 
		return null;
		}
	}

