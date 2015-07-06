package de.hdm.sms.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;

import de.hdm.sms.server.db.DatebaseConnection;
import de.hdm.sms.shared.bo.User;

public class UserMapper {
	private static UserMapper userMapper = null;
	protected UserMapper() {

	}

	public static UserMapper userMapper() {
		if (userMapper == null) {
			userMapper = new UserMapper();
		}
		return userMapper;
	}

	public void insertUser(User u) {
		Connection con = DatebaseConnection.connection();
		try {
			Statement state = con.createStatement();
			String sqlquery = "INSERT INTO User (Firstname, Lastname, EmailAdress) VALUES ("
					+ "'"
					+ u.getFirstName()
					+ "','"
					+ u.getLastName()
					+ "', '"
					+ u.geteMailAdress() + "');";
			state.executeUpdate(sqlquery);

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DatebaseConnection.release(con);
		}
	}

	public ArrayList<User> loadAllUsers() {
		Connection con = DatebaseConnection.connection();
		ArrayList<User> resultList = new ArrayList<>();

		try {
			Statement state = con.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM User");

			while (result.next()) {
				User u = new User();
				u.setId(result.getInt("Id"));
				u.setFirstName(result.getString("Firstname"));
				u.setLastName(result.getString("Lastname"));
				u.seteMailAdress(result.getString("EmailAdress"));

				resultList.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			DatebaseConnection.release(con);
		}
		return resultList;
	}

	public User getOneUserIdByEmailAdress(String selectedUser) {
		
		Connection con = DatebaseConnection.connection();

		User u = new User();

		try {
			Statement state = con.createStatement();
			ResultSet rs = state
					.executeQuery("SELECT * FROM User WHERE EmailAdress='"
							+ selectedUser + "';");

			while (rs.next()) {

				u.setId(rs.getInt("Id"));
				u.setFirstName(rs.getString("Firstname"));
				u.setLastName(rs.getString("Lastname"));
				u.seteMailAdress(rs.getString("EmailAdress"));
			}
			rs.close();
			state.close();

		} catch (SQLException e) {
			e.printStackTrace();

		}
		finally{
			DatebaseConnection.release(con);
		}

		return u;

	}

	public void deleteUserById(int deleteUserId) {

		Connection con = DatebaseConnection.connection();

		try {

			Statement state = con.createStatement();

			state.executeUpdate("UPDATE Component SET Modifier = '" + -1
					+ "' WHERE Modifier = '" + deleteUserId + "';");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			Statement state = con.createStatement();

			state.executeUpdate("UPDATE Componentgroup SET Modifier = '" + -1
					+ "' WHERE Modifier = '" + deleteUserId + "';");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			Statement state = con.createStatement();

			state.executeUpdate("UPDATE Product SET Modifier = '" + -1
					+ "' WHERE Modifier = '" + deleteUserId + "';");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			Statement state = con.createStatement();

			state.executeUpdate("UPDATE Stocklist SET Modifier = '" + -1
					+ "' WHERE Modifier = '" + deleteUserId + "';");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			Statement state = con.createStatement();

			state.executeUpdate("DELETE FROM User WHERE Id='" + deleteUserId
					+ "';");

		} catch (Exception e) {
			e.printStackTrace();
		}
		

			DatebaseConnection.release(con);
		

	}

	public void updateUserById(User u) {

		Connection con = DatebaseConnection.connection();

		try {

			Statement state = con.createStatement();

			state.executeUpdate("UPDATE `User` SET `Firstname`= '"
					+ u.getFirstName() + "', `Lastname`= '" + u.getLastName()
					+ "', " + "`EmailAdress`= '" + u.geteMailAdress() + "' "
					+ "WHERE `Id` = '" + u.getId() + "';");

		} catch (Exception e) {
			e.printStackTrace();

		}

		DatebaseConnection.release(con);
	}

	public User getOneUserById(int tempUserId) {

		Connection con = DatebaseConnection.connection();

		User u = new User();

		try {
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery("SELECT * FROM User WHERE Id='"
					+ tempUserId + "';");

			while (rs.next()) {

				u.setId(rs.getInt("Id"));
				u.setFirstName(rs.getString("Firstname"));
				u.setLastName(rs.getString("Lastname"));
				u.seteMailAdress(rs.getString("EmailAdress"));

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		DatebaseConnection.release(con);
		return u;

	}
}
