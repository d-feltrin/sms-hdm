package de.hdm.sms.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.hdm.sms.server.db.DatebaseConnection;
import de.hdm.sms.shared.bo.Component;
import de.hdm.sms.shared.bo.ComponentGroup;

public class ComponentGroupMapper {

	private static ComponentGroupMapper componentGroupMapper = null;
	public Connection con = DatebaseConnection.connection();

	protected ComponentGroupMapper() {

	}

	public static ComponentGroupMapper componentGroupMapper() {
		if (componentGroupMapper == null) {
			componentGroupMapper = new ComponentGroupMapper();
		}
		return componentGroupMapper;
	}

	public void insertComponentGroup(ComponentGroup cg) {
		Connection con = DatebaseConnection.connection();
		try {
			Statement state = con.createStatement();
			String sqlquery = "INSERT INTO Componentgroup (Name, Modifier, Creationdate) VALUES ("
					+ "'"
					+ cg.getName()
					+ "','"
					+ cg.getModifier()
					+ "', '"
					+ DateHelperClass.getCurrentTime() + "');";

			state.executeUpdate(sqlquery);

			List<Component> listOfComponents = cg.getComponentList();
			List<Integer> listOfComponentsAmount = cg.getAmountListOfComponent();
				for (int i = 0; i < listOfComponents.size(); i++) {
					
				
				String sqlqueryComponent = "INSERT INTO `db_sms`.`ComponenGroupRelations` (`ComponentGroupID`, `ComponentGroupID2`, `ComponentId`, `Tag`, `Amount`) "
						+ "VALUES ('"
						+ cg.getId()
						+ "', '', '"
						+ listOfComponents.get(i).getId()
						+ "', 'C', '"
						+ listOfComponentsAmount.get(i).toString()
						+ "');";

			}

				List<ComponentGroup> listOfComponentGroups = cg.getComponentgroupList();
				List<Integer> listOfComponentGroupsAmount = cg.getAmountListOfComponentGroup();
				for (int i = 0; i < listOfComponentGroups.size(); i++) {
				String sqlqueryComponent = "INSERT INTO `db_sms`.`ComponenGroupRelations` (`ComponentGroupID`, `ComponentGroupID2`, `ComponentId`, `Tag`, `Amount`) "
						+ "VALUES ('"
						+ cg.getId()
						+ "', '"
						+ listOfComponentGroups.get(i).getId()
						+ "', '', 'G', '"
						+ listOfComponentGroupsAmount.get(i).toString() + "');";

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void updateComponentGroupById(ComponentGroup cg) {

		Connection con = DatebaseConnection.connection();

		try {

			Statement state = con.createStatement();

			state.executeUpdate("UPDATE `Componentgroup` SET `Name`= '"
					+ cg.getName() + "' " + "WHERE `Id` = '" + cg.getId()
					+ "';");

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public ArrayList<ComponentGroup> loadAllComponentGroups() {
		Connection con = DatebaseConnection.connection();
		ArrayList<ComponentGroup> resultList = new ArrayList<>();

		try {
			Statement state = con.createStatement();
			ResultSet result = state
					.executeQuery("SELECT * FROM Componentgroup");

			while (result.next()) {
				ComponentGroup cg = new ComponentGroup(result.getString("Name"));
				cg.setId(result.getInt("Id"));

				resultList.add(cg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	public ComponentGroup getOneComponentGroupIdByName(
			String selectedComponentGroup) {

		Connection con = DatebaseConnection.connection();
		ComponentGroup cg = new ComponentGroup("");

		try {
			Statement state = con.createStatement();
			ResultSet rs = state
					.executeQuery("SELECT * FROM Componentgroup WHERE name='"
							+ selectedComponentGroup + "';");

			while (rs.next()) {

				cg = new ComponentGroup(rs.getString("Name"));
				cg.setId(rs.getInt("Id"));

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return cg;

	}

	public void deleteComponentGroupById(int deleteComponentGroupId) {

		Connection con = DatebaseConnection.connection();

		try {

			Statement state = con.createStatement();

			state.executeUpdate("DELETE FROM Componentgroup WHERE Id='"
					+ deleteComponentGroupId + "';");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
