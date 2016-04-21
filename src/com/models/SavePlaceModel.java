package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class SavePlaceModel {
int UserID;
int PlaceID;

public int getUser_ID() {
	return UserID;
}
public void setUser_ID(int user_ID) {
	UserID = user_ID;
}
public int getPlace_ID() {
	return PlaceID;
}
public void setPlace_ID(int place_ID) {
	PlaceID = place_ID;
} 

public static SavePlaceModel Save_place(int uID,int pID) throws SQLException
{
	Connection conn=DBConnection.getActiveConnection();
	String sql="INSERT INTO saveplace ( `User_ID`, `Place_ID`) VALUES (?,?)";
	PreparedStatement stmt;
	stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	stmt.setInt(1,uID);
	stmt.setInt(2, pID);
	stmt.executeUpdate();
	ResultSet rs = stmt.getGeneratedKeys();
	SavePlaceModel place=new SavePlaceModel();
		place.UserID=uID;
		place.PlaceID=pID;
		return place;
	
	
}


}
