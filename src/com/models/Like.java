package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Like implements Actions {
	private int checkin_id;
	private int user_id;
	private int No;
	private int checkinUser_ID;
	public int getCheckinUser_ID() {
		return checkinUser_ID;
	}
	public void setCheckinUser_ID(int checkinUser_ID) {
		this.checkinUser_ID = checkinUser_ID;
	}
	public int getCheckin_id() {
		return checkin_id;
	}
	public void setCheckin_id(int checkin_id) {
		this.checkin_id = checkin_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getNo() {
		return No;
	}
	public void setNo(int no) {
		No = no;
	}
  
	public static int getNoOfLikes (int user_id ) throws SQLException
	{
		int n=0;
		Connection conn = DBConnection.getActiveConnection();
		String Sql1 ="Select COUNT(*)  from likes where `checkin_id` =?";
		PreparedStatement stm;
		stm = conn.prepareStatement(Sql1);
		stm.setInt(1, user_id);
		ResultSet rss = stm.getGeneratedKeys();
		while (rss.next())
		{
			 n=rss.getInt("COUNT(*)");
		}
		return n;
	}
	
	public static boolean makeLike(int check_id , int user_id,int checkinUser_id )
	{
		
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from `like` where `user_id` = ? and `checkin_id` = ? and `checkinUser_id` =?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,user_id);
			stmt.setInt(2,check_id);
			stmt.setInt(3, checkinUser_id);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) 
				{
				String sql1 = "delete  from `like` where  `user_id`=?  and `checkin_id`= ? and `checkinUser_id`=? ";
				PreparedStatement st;
				st = conn.prepareStatement(sql1);
				st.setInt(1, user_id);
				st.setInt(2, check_id);
				st.setInt(3, checkinUser_id);
				st.executeUpdate();
				  return false ;
				}
			else 
			{
				
				String Sql = " Insert into `like` (`user_id` ,`checkin_id` , `checkinUser_id`) VALUES  (?,?,?)";
				PreparedStatement st;
				st = conn.prepareStatement(Sql);
				st.setInt(1, user_id);
				st.setInt(2,check_id );
				st.setInt(3, checkinUser_id);
				st.executeUpdate();
				notification.update("Like",user_id ,check_id, checkinUser_id);
				return true;
				
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
		
	}
	}
