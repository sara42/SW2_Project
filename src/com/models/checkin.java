package com.models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class checkin {
	private static String place_name;
	private Integer user_id;
	private Integer No;
	private Integer Id;
	Actions action;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public static String getPlace_name() {
		return place_name;
	}

	public static void setPlace_name(String place_name) {
		checkin.place_name = place_name;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getNo() {
		return No;
	}

	public void setNo(Integer no) {
		No = no;
	}

	public static boolean Check_IN(String name, int id) {
		int n = 0;
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select name from places where name= ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				String Sql1 = "Select No from check_in where  place_name = ?";
				PreparedStatement stm;
				stm = conn.prepareStatement(Sql1);
				stm.setString(1, name);
				ResultSet rss = stm.executeQuery();
				if (rss.next()) {
					n = rss.getInt("No");
				}
				n++;
				String sql1 = "Insert into check_in (place_name,No,user_id) VALUES  (?,?,?)";
				PreparedStatement st;
				st = conn.prepareStatement(sql1);
				st.setString(1, name);
				st.setInt(2, n);
				st.setInt(3, id);
				st.executeUpdate();
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
