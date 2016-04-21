package com.models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Comment implements Actions {
private int checkin_id;
private int Nofcoments;
private int userid;
private int checkinUser_id;

public int getCheckinUser_id() {
	return checkinUser_id;
}
public void setCheckinUser_id(int checkinUser_id) {
	this.checkinUser_id = checkinUser_id;
}
public int getCheckin_id() {
	return checkin_id;
}
public void setCheckin_id(int checkin_id) {
	this.checkin_id = checkin_id;
}
public int getNofcoments() {
	return Nofcoments;
}
public void setNofcoments(int nofcoments) {
	Nofcoments = nofcoments;
}
public int getUserid() {
	return userid;
}
public void setUserid(int userid) {
	this.userid = userid;
}

public static boolean makeComment (int check_id , int user_id, int checkinUser_id)
{
	int n=0;
	try{
		Connection conn = DBConnection.getActiveConnection();
		String sql = " Select * from `comment` where `user_id`= ? and `checkin_id`= ? and `checkinUser_id`=? ";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, user_id);
		stmt.setInt(2, check_id);
		stmt.setInt(3, checkinUser_id);
		ResultSet rs = stmt.executeQuery();
		
		if (rs.next()) 
			{
			String Sql1 ="Select `N.ofcoments` from `comment` where `user_id` =? and `checkin_id`= ? and `checkinUser_id`=?";
			PreparedStatement stm;
			stm = conn.prepareStatement(Sql1);
			stm.setInt(1, user_id);
			stm.setInt(2, check_id);
			stm.setInt(3, checkinUser_id);
			ResultSet rss = stm.executeQuery();
			while (rss.next())
			{
				 n=rss.getInt("N.ofcoments");
			}
			n++;
			String Sql = "Update `comment` set `N.ofcoments`=? where `user_id`=? and `checkin_id`= ? and `checkinUser_id`=?";
			PreparedStatement st;
			st = conn.prepareStatement(Sql);
			st.setInt(1, n);
			st.setInt(2, user_id);
			st.setInt(3, check_id);
			st.setInt(4, checkinUser_id);
			st.executeUpdate();
			return true;
			}
		else 
		{
		
			String Sql = "Insert into `comment` (`checkin_id`,`N.ofcoments`,`user_id`,`checkinUser_id`) VALUES (?,?,?,?)";
			PreparedStatement st;
			st = conn.prepareStatement(Sql);
			st.setInt(1,check_id );
			st.setInt(2, 1);
			st.setInt(3, user_id);
			st.setInt(4, checkinUser_id);
			st.executeUpdate();
			notification.update("Comment",user_id ,check_id, checkinUser_id);
			return true;
			
		}
		
	}catch(SQLException e){
		e.printStackTrace();
	}
	return false;
	
}
}


