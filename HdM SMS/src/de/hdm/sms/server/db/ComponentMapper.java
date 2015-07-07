package de.hdm.sms.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import de.hdm.sms.server.db.DatebaseConnection;
import de.hdm.sms.shared.bo.Component;
import de.hdm.sms.shared.bo.User;

public class ComponentMapper {
	private static ComponentMapper componentMapper = null;

	protected ComponentMapper() {

	}

	public static ComponentMapper componentMapper() {
		if (componentMapper == null) {
			componentMapper = new ComponentMapper();
		}
		return componentMapper;
	}

	public void insertComponent(Component c) {
		Connection con = DatebaseConnection.connection("insertComponent");
		// Date now = new Date();
		try {
			Statement state = con.createStatement();
			String sqlquery = "INSERT INTO Component (Name, Description, Materialdescription, Modifier, Creationdate, LastModified) VALUES ("
					+ "'"
					+ c.getName()
					+ "','"
					+ c.getDescription()
					+ "', '"
					+ c.getMaterialDescription()
					+ "', '"
					+ c.getModifier()

					+ "', '"
					+ DateHelperClass.getCurrentTime()
					+ "', '"
					+ DateHelperClass.getCurrentTime() + "');";

			state.executeUpdate(sqlquery);

		} catch (Exception e) {
			e.printStackTrace();
		}
		DatebaseConnection.release(con);
	}

	public ArrayList<Component> loadAllComponents() {
		Connection con = DatebaseConnection.connection("loadAllComponents");
		ArrayList<Component> resultList = new ArrayList<>();

		try {
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery("SELECT * FROM Component");

			while (rs.next()) {
				Component c = new Component();
				c.setId(rs.getInt("Id"));
				c.setName(rs.getString("Name"));
				c.setDescription(rs.getString("Description"));
				c.setMaterialDescription(rs.getString("Materialdescription"));
				c.setModifier(rs.getInt("Modifier"));
				c.setCreationdate(rs.getTimestamp("Creationdate"));
				c.setLastModified(rs.getTimestamp("LastModified"));

				resultList.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DatebaseConnection.release(con);
		return resultList;
	}

	public Component getOneComponentIdById(int selectedComponent) {

		Connection con = DatebaseConnection.connection("getOneComponentIdById");

		Component c = new Component();

		try {
			Statement state = con.createStatement();
			ResultSet rs = state
					.executeQuery("SELECT * FROM Component WHERE Id='"
							+ selectedComponent + "';");

			while (rs.next()) {

				c.setId(rs.getInt("Id"));
				c.setName(rs.getString("Name"));
				c.setDescription(rs.getString("Description"));
				c.setMaterialDescription(rs.getString("Materialdescription"));
				c.setModifier(rs.getInt("Modifier"));
				c.setCreationdate(rs.getTimestamp("Creationdate"));
				c.setLastModified(rs.getTimestamp("LastModified"));

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		DatebaseConnection.release(con);
		return c;

	}

	public void deleteComponentById(int deleteComponentId) {

		Connection con = DatebaseConnection.connection("deleteComponentById");

		try {

			Statement state = con.createStatement();

			state.executeUpdate("DELETE FROM Component WHERE Id='"
					+ deleteComponentId + "';");

		} catch (Exception e) {
			e.printStackTrace();
		}
		DatebaseConnection.release(con);

	}

	public void updateComponentById(Component c) {

		Connection con = DatebaseConnection.connection("updateComponentById");

		try {

			Statement state = con.createStatement();

			state.executeUpdate("UPDATE Component SET LastModified= '"
					+ DateHelperClass.getCurrentTime() + "', Name= '"
					+ c.getName() + "', Materialdescription= '"
					+ c.getMaterialDescription() + "', " + "Description= '"
					+ c.getDescription() + "', " + "Modifier= '"
					+ c.getModifier() + "' " + "WHERE Id = '" + c.getId()
					+ "';");

		} catch (Exception e) {
			e.printStackTrace();

		}
		DatebaseConnection.release(con);
	}

	public User getLastMofierOfComponentById(Component c) {
		Connection con = DatebaseConnection.connection("getLastMofierOfComponentById");

		User u = new User();

		try {

			Statement state = con.createStatement();
			ResultSet rs = state
					.executeQuery("SELECT * From Component INNER JOIN User ON Component.Modifier = User.Id AND User.Id='"
							+ c.getModifier() + "';");

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

	public String checkRelationsOfComponent(int tempId) {
		Connection con = DatebaseConnection.connection("checkRelationsOfComponent");

		String relation = null;

		try {

			Statement state = con.createStatement();
			ResultSet rs = state
					.executeQuery("SELECT * From ComponenGroupRelations INNER JOIN Component ON Component.Id = ComponenGroupRelations.ComponentId INNER JOIN Componentgroup ON ComponenGroupRelations.ComponentGroupID = Componentgroup.Id WHERE ComponenGroupRelations.ComponentId =  '"
							+ tempId + "';");

			while (rs.next()) {
				relation = "Componentgroup "
						+ rs.getString("Componentgroup.Name");
				break;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {

			Statement state2 = con.createStatement();
			if (relation == null) {
				ResultSet rs2 = state2
						.executeQuery("SELECT * FROM StocklistComponent INNER JOIN Component ON StocklistComponent.Componentid = Component.Id INNER JOIN Stocklist ON StocklistComponent.Stocklistid = Stocklist.Id WHERE Component.Id = '"+tempId+"';");

				while (rs2.next()) {
					relation = "Stückliste "
							+ rs2.getString("Stocklist.Name");
					break;

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		DatebaseConnection.release(con);
		return relation;
	}
}
