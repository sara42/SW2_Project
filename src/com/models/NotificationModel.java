package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.mysql.jdbc.Statement;

public class NotificationModel {
	int NID;
	String type;
	int UID;
	int TypeID;
	int notifiedUID;
	String uname;
	
	
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public int getNID() {
		return NID;
	}
	public void setNID(int nID) {
		NID = nID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getUID() {
		return UID;
	}
	public void setUID(int uID) {
		UID = uID;
	}
	public int getTypeID() {
		return TypeID;
	}
	public void setTypeID(int typeID) {
		TypeID = typeID;
	}
	
	public int getNotifiedUID() {
		return notifiedUID;
	}
	public void setNotifiedUID(int notifiedUID) {
		this.notifiedUID = notifiedUID;
	}
	
	public static boolean update(String type,int uID,int typeID,int notifiedID) throws SQLException
	{
		Connection conn = DBConnection.getActiveConnection();
		String sql="INSERT INTO notifications ( `Type`, `UID`, `TypeID`,`NotifiedUID`) VALUES (?,?,?,?)";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1,type);
		stmt.setInt(2, uID);
		stmt.setInt(3, typeID);
		stmt.setInt(4, notifiedID);
		stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();
		if (rs.next()) {
          return true;			
		}
		return false;
		}  
	
	public static Vector<NotificationModel> getNotification(int id) throws SQLException
	{
		Connection conn = DBConnection.getActiveConnection();
		String sql = "Select * from notifications where `notifiedUID` =?";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id); 
		ResultSet rs = stmt.executeQuery();
		Vector<NotificationModel> v =new Vector<>();
		while (rs.next()) {
			String name;
			NotificationModel n =new NotificationModel();
			n.setType(rs.getString("Type"));
			n.setTypeID(rs.getInt("TypeID"));
			n.setUID(rs.getInt("UID"));
			name=UserModel.get_name(n.getUID());
			n.setUname(name);
			v.addElement(n);
		} 
		return v;
	}
	
		
	

}
