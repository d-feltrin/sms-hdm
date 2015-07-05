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
			String sqlquery = "INSERT INTO Componentgroup (Name, Modifier, Creationdate, LastModified) VALUES ("
					+ "'"
					+ cg.getComponentGroupName()
					+ "','"
					+ cg.getModifier()
					+ "', '"
					+ DateHelperClass.getCurrentTime()
					+ "', '"
					+ DateHelperClass.getCurrentTime() + "');";

			state.executeUpdate(sqlquery);

			// Get ID from last line
			try {
				ResultSet result = state
						.executeQuery("SELECT `Id` FROM `Componentgroup` ORDER BY `Id` DESC LIMIT 1;");

				while (result.next()) {
					cg.setId(result.getInt("Id"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			List<Component> listOfComponents = cg.getComponentList();
			List<Integer> listOfComponentsAmount = cg
					.getAmountListOfComponent();
			for (int i = 0; i < listOfComponents.size(); i++) {

				String sqlqueryComponent = "INSERT INTO `db_sms`.`ComponenGroupRelations` (`ComponentGroupID`, `ComponentId`, `Tag`, `Amount`) "
						+ "VALUES ('"
						+ cg.getId()
						+ "', '"
						+ listOfComponents.get(i).getId()
						+ "', 'C', '"
						+ listOfComponentsAmount.get(i).toString() + "');";
				if (listOfComponentsAmount.get(i) > 0) // dont add Elements with
														// Amount under 1 (0 or
														// minus)
					state.executeUpdate(sqlqueryComponent);

			}

			List<ComponentGroup> listOfComponentGroups = cg
					.getComponentgroupList();
			List<Integer> listOfComponentGroupsAmount = cg
					.getAmountListOfComponentGroup();
			for (int i = 0; i < listOfComponentGroups.size(); i++) {
				String sqlqueryComponent = "INSERT INTO `db_sms`.`ComponenGroupRelations` (`ComponentGroupID`, `ComponentGroupID2`,  `Tag`, `Amount`) "
						+ "VALUES ('"
						+ cg.getId()
						+ "', '"
						+ listOfComponentGroups.get(i).getId()
						+ "', 'G', '"
						+ listOfComponentGroupsAmount.get(i).toString() + "');";
				if (listOfComponentGroupsAmount.get(i) > 0)// dont add Elements
															// with Amount under
															// 1 (0 or minus)
					state.executeUpdate(sqlqueryComponent);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void updateComponentGroupById(ComponentGroup cg) {

		Connection con = DatebaseConnection.connection();
		try {

			Statement state = con.createStatement();

			state.execute("UPDATE `db_sms`.`Componentgroup` SET `Name` = '"
					+ cg.getComponentGroupName() + "', `Modifier`='"
					+ cg.getModifier() + "',LastModified='"
					+ DateHelperClass.getCurrentTime()
					+ "' WHERE `Componentgroup`.`Id` = " + cg.getId() + " ;");
			state.close();

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
				ComponentGroup cg = new ComponentGroup();
				cg.setId(result.getInt("Id"));
				cg.setComponentGroupName(result.getString("Name"));
				cg.setCreationDate(result.getTimestamp("Creationdate"));
				cg.setModificationDate(result.getTimestamp("LastModified"));
				cg.setModifier(result.getInt("Modifier"));

				resultList.add(cg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public ArrayList<ComponentGroup> loadAllComponentGroupsIncludingRelations() {
		Connection con = DatebaseConnection.connection();
		ArrayList<ComponentGroup> resultList = new ArrayList<>();

		try {
			Statement state = con.createStatement();
			ResultSet result = state
					.executeQuery("SELECT * FROM Componentgroup");

			while (result.next()) {
				ComponentGroup cg = new ComponentGroup();
				cg.setId(result.getInt("Id"));
				cg.setComponentGroupName(result.getString("Name"));
				cg.setCreationDate(result.getTimestamp("Creationdate"));
				cg.setModificationDate(result.getTimestamp("LastModified"));
				cg.setModifier(result.getInt("Modifier"));

				boolean hasRelations = false;

				try {
					Statement s2 = con.createStatement();
					ResultSet result2 = s2
							.executeQuery("SELECT * FROM ComponenGroupRelations WHERE ComponentGroupID = '"
									+ cg.getId() + "'");

					while (result2.next()) {
						hasRelations = true;
						break;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				if (hasRelations)
					cg = FillRelations(cg);

				resultList.add(cg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	private ComponentGroup FillRelations(ComponentGroup cgToEdit) {
		Connection con = DatebaseConnection.connection();

		// get all sub Groups
		try {
			Statement state = con.createStatement();
			ResultSet result = state
					.executeQuery("SELECT * From ComponenGroupRelations JOIN Componentgroup ON ComponenGroupRelations.ComponentGroupID2 = Componentgroup.Id WHERE ComponenGroupRelations.Tag='G' AND ComponenGroupRelations.ComponentGroupID = '"
							+ cgToEdit.getId() + "'");

			while (result.next()) {
				ComponentGroup cg = new ComponentGroup();
				cg.setId(result
						.getInt("ComponenGroupRelations.ComponentGroupID2"));
				cg.setComponentGroupName(result
						.getString("Componentgroup.Name"));
				cg.setCreationDate(result
						.getTimestamp("Componentgroup.Creationdate"));
				cg.setModificationDate(result
						.getTimestamp("Componentgroup.LastModified"));
				cg.setModifier(result.getInt("Componentgroup.Modifier"));

				boolean hasRelations = false;

				try {
					Statement s2 = con.createStatement();
					ResultSet result2 = s2
							.executeQuery("SELECT * FROM ComponenGroupRelations WHERE ComponentGroupID = '"
									+ cg.getId() + "'");

					while (result2.next()) {
						hasRelations = true;
						continue;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				if (hasRelations)
					cg = FillRelations(cg);

				cgToEdit.addComponentGroup(cg, result.getInt("Amount"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// get all sub Components
		try {
			Statement state = con.createStatement();
			ResultSet result = state
					.executeQuery("SELECT * From ComponenGroupRelations JOIN Component ON ComponenGroupRelations.ComponentId = Component.Id WHERE ComponenGroupRelations.Tag='C' AND ComponenGroupRelations.ComponentGroupID =  '"
							+ cgToEdit.getId() + "'");

			while (result.next()) {
				Component c = new Component();
				c.setId(result.getInt("Component.Id"));
				c.setName(result.getString("Component.Name"));
				c.setDescription(result.getString("Component.Description"));
				c.setMaterialDescription(result
						.getString("Component.Materialdescription"));
				c.setModifier(result.getInt("Component.Modifier"));
				c.setCreationdate(result.getTimestamp("Component.Creationdate"));
				c.setLastModified(result.getTimestamp("Component.LastModified"));

				cgToEdit.addComponent(c, result.getInt("Amount"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cgToEdit;
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

	public void deleteComponentGroupById(ComponentGroup cg) {

		Connection con = DatebaseConnection.connection();

		try {

			Statement state = con.createStatement();

			state.executeUpdate("DELETE FROM Componentgroup WHERE Id='"
					+ cg.getId() + "';");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void insertCGElement(ComponentGroup cg, int ElementID,
			char ElementTag, int Amount) {
		Connection con = DatebaseConnection.connection();
		try {

			Statement statex = con.createStatement();

			statex.execute("UPDATE `db_sms`.`Componentgroup` SET `Name` = '"
					+ cg.getComponentGroupName() + "', `Modifier`='"
					+ cg.getModifier() + "',LastModified='"
					+ DateHelperClass.getCurrentTime()
					+ "' WHERE `Componentgroup`.`Id` = " + cg.getId() + " ;");
			statex.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

		try {

			Statement state = con.createStatement();

			if (ElementTag == 'C')
				state.executeUpdate("INSERT INTO `db_sms`.`ComponenGroupRelations` (`Id`, `ComponentGroupID`, `ComponentGroupID2`, `ComponentId`, `Tag`, `Amount`) VALUES (NULL, '"
						+ cg.getId()
						+ "', NULL, "
						+ ElementID
						+ ", 'C', '"
						+ Amount + "'); ");

			if (ElementTag == 'G')

				state.executeUpdate("INSERT INTO `db_sms`.`ComponenGroupRelations` (`Id`, `ComponentGroupID`, `ComponentGroupID2`, `ComponentId`, `Tag`, `Amount`) VALUES (NULL, '"
						+ cg.getId()
						+ "',  "
						+ ElementID
						+ ",NULL, 'G', '"
						+ Amount + "'); ");

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public void updateCGElementAmount(ComponentGroup cg, int ElementID,
			char ElementTag, int NewAmount) {
		Connection con = DatebaseConnection.connection();
		try {

			Statement statex = con.createStatement();

			statex.execute("UPDATE `db_sms`.`Componentgroup` SET `Name` = '"
					+ cg.getComponentGroupName() + "', `Modifier`='"
					+ cg.getModifier() + "',LastModified='"
					+ DateHelperClass.getCurrentTime()
					+ "' WHERE `Componentgroup`.`Id` = " + cg.getId() + " ;");
			statex.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

		try {

			Statement state = con.createStatement();

			if (ElementTag == 'C')

				state.executeUpdate("UPDATE `db_sms`.`ComponenGroupRelations` SET `Amount` = '"
						+ NewAmount
						+ "' WHERE `ComponenGroupRelations`.`ComponentGroupID` = "
						+ cg.getId()
						+ " AND `ComponenGroupRelations`.`ComponentId` = "
						+ ElementID + "; ");
			if (ElementTag == 'G')

				state.executeUpdate("UPDATE `db_sms`.`ComponenGroupRelations` SET `Amount` = '"
						+ NewAmount
						+ "' WHERE `ComponenGroupRelations`.`ComponentGroupID` = "
						+ cg.getId()
						+ " AND `ComponenGroupRelations`.`ComponentGroupID2` = "
						+ ElementID + "; ");

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void deleteCGElement(ComponentGroup cg, int ElementID,
			char ElementTag) {
		Connection con = DatebaseConnection.connection();

		try {

			Statement state = con.createStatement();

			if (ElementTag == 'C')
				state.executeUpdate("DELETE FROM `db_sms`.`ComponenGroupRelations` WHERE `ComponenGroupRelations`.`ComponentGroupID` = "
						+ cg.getId()
						+ " AND `ComponenGroupRelations`.`ComponentId` = "
						+ ElementID + ";");
			if (ElementTag == 'G')
				state.executeUpdate("DELETE FROM `db_sms`.`ComponenGroupRelations` WHERE `ComponenGroupRelations`.`ComponentGroupID` = "
						+ cg.getId()
						+ " AND `ComponenGroupRelations`.`ComponentGroupID2` = "
						+ ElementID + ";");

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public String checkRelationsOfComponentGroup(int tempId) {
		String relation = null;

		try {

			Statement state = con.createStatement();
			ResultSet rs = state
					.executeQuery("SELECT * From ComponenGroupRelations INNER JOIN Componentgroup ON Componentgroup.Id = ComponenGroupRelations.ComponentGroupID WHERE ComponenGroupRelations.ComponentGroupID2 =  '"
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
						.executeQuery("SELECT * FROM StocklistComponentgroup INNER JOIN Componentgroup ON StocklistComponentgroup.StocklistComponentgroupid INNER JOIN Stocklist ON StocklistComponentgroup.Stocklistid = Stocklist.Id WHERE StocklistComponentgroup.StocklistComponentgroupid = '"+tempId+"';");

				while (rs2.next()) {
					relation = "Stückliste " + rs2.getString("Stocklist.Name");
					break;

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return relation;
	}
}
