package de.hdm.sms.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;





import de.hdm.sms.server.db.DatebaseConnection;
import de.hdm.sms.shared.bo.Component;

public class ComponentMapper {
	private static ComponentMapper componentMapper = null;
	public Connection con = DatebaseConnection.connection();

	protected ComponentMapper() {

	}

	public static ComponentMapper componentMapper() {
		if (componentMapper == null) {
			componentMapper = new ComponentMapper();
		}
		return componentMapper;
	}

	public void insertComponent(Component c) {
		Connection con = DatebaseConnection.connection();
		try {
			Statement state = con.createStatement();
			String sqlquery = "INSERT INTO Component (Name, Description, Materialdescription) VALUES ("
					+ "'"
					+ c.getName()
					+ "','"
					+ c.getDescription()
					+ "', '"
					+ c.getMaterialDescription() + "');";
			state.executeUpdate(sqlquery);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Component> loadAllComponents() {
		Connection con = DatebaseConnection.connection();
		ArrayList<Component> resultList = new ArrayList<>();

		try {
			Statement state = con.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM Component");

			while (result.next()) {
				Component c = new Component();
				c.setId(result.getInt("Id"));
				c.setName(result.getString("Name"));
				c.setDescription(result.getString("Description"));
				c.setMaterialDescription(result
						.getString("Materialdescription"));

				resultList.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	public Component getOneComponentIdByName(String selectedComponent) {

		Connection con = DatebaseConnection.connection();

		Component c = new Component();

		try {
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery("SELECT * FROM Component WHERE name='"
					+ selectedComponent + "';");

			while (rs.next()) {

				c.setId(rs.getInt("Id"));
				c.setName(rs.getString("Name"));
				c.setDescription(rs.getString("Description"));
				c.setMaterialDescription(rs.getString("Materialdescription"));

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return c;

	}

	public void deleteComponentById(int deleteComponentId) {

		Connection con = DatebaseConnection.connection();

		try {

			Statement state = con.createStatement();

			state.executeUpdate("DELETE FROM Component WHERE Id='" + deleteComponentId + "';");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateComponentById(Component c) {

		Connection con = DatebaseConnection.connection();

		try {

			Statement state = con.createStatement();

			state.executeUpdate("UPDATE `Component` SET `Name`= '" + c.getName()
					+ "', `Materialdescription`= '" + c.getMaterialDescription()
					+ "', " + "`Description`= '" + c.getDescription() + "' "
					+ "WHERE `Id` = '" + c.getId() + "';");

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
