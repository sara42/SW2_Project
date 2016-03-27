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
	
	public static String getLastPosition (Integer id)
	{
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = " Select * from users where  'id'= ?  ";
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
			String Sql = " Select * from places where  'lon'= ?,'lat'=?  ";
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
	}
	
	
}
