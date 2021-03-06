package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.mysql.jdbc.Statement;

public class Followers {
 int id1,id2;

public int getId1() {
	return id1;
}

public void setId1(int id1) {
	this.id1 = id1;
}

public int getId2() {
	return id2;
}

public void setId2(int id2) {
	this.id2 = id2;
}
 public static Vector<UserModel> Get_followers(int _id) throws SQLException
 {
		Connection conn = DBConnection.getActiveConnection();
		String sql = "Select * from users,follower where `ID_2` = id and `ID_1`=?;"; 
				PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, _id); 
		ResultSet rs = stmt.executeQuery(); 
		Vector<UserModel> v =new Vector<>();
		while (rs.next()) {
			UserModel user = new UserModel();
		    user.setEmail(rs.getString("email"));
		    user.setName(rs.getString("name"));
		    v.addElement(user);
		
		}
		return v;

 }
 public static boolean UNfollow(int _id1,int _id2) 
 {
	 try{
		Connection conn = DBConnection.getActiveConnection();
		String sql = "delete from follower WHERE `ID_1`=? and `ID_2`=? "; 
				PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, _id1);
		stmt.setInt(2, _id2);
		stmt.executeUpdate();
		return true;
	 }catch(SQLException e){
			e.printStackTrace();
		}
		return false;

 }
 



public static boolean followUser(Integer userid,Integer followed)
{
	try{
		Connection conn = DBConnection.getActiveConnection();
		String sql = " Select * from follower where  `ID_1`= ? and `ID_2` = ? ";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, userid);
		stmt.setInt(2, followed);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) ////lw l2ahom 5las kda kda followed 
			return false ;
		else 
		{
			String Sql = "Insert into follower (`ID_1`,`ID_2`) VALUES  (?,?)";
			PreparedStatement stm;
			stm = conn.prepareStatement(Sql);
			stm.setInt(1, userid);
			stm.setInt(2, followed);
			stm.executeUpdate();
			return true;
		}
		
	}catch(SQLException e){
		e.printStackTrace();
	}
	return false;
	
}

public static boolean Is_follower(int id1,int id2) throws SQLException
{
	Connection conn = DBConnection.getActiveConnection();
	String sql = "Select * from follower where  `ID_1`= ? and `ID_2` = ?"; 
	PreparedStatement stmt;
	stmt = conn.prepareStatement(sql);
	stmt.setInt(1, id1);
	stmt.setInt(2, id2);
	ResultSet rs = stmt.executeQuery();
	if(rs.next()) return true;
	return false;
}

}