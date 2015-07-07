package de.hdm.sms.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import de.hdm.sms.server.db.DatebaseConnection;
import de.hdm.sms.shared.bo.Component;
import de.hdm.sms.shared.bo.ComponentGroup;
import de.hdm.sms.shared.bo.Stocklist;

public class StocklistMapper {

	private static StocklistMapper stocklistMapper = null;

	protected StocklistMapper() {

	}

	public static StocklistMapper stocklistMapper() {
		if (stocklistMapper == null) {
			stocklistMapper = new StocklistMapper();
		}
		return stocklistMapper;
	}

	public void insertStocklist(Stocklist newStocklist) {
		Connection con = DatebaseConnection.connection("insertStocklist");
		try {
			Statement state = con.createStatement();

			Timestamp t = DateHelperClass.getCurrentTime();

			String sqlquery = "INSERT INTO `db_sms`.`Stocklist` (`Name`, `Creationdate`, `Modifier`, `LastModified`) VALUES ( '"
					+ newStocklist.getName() + "', '" + t + "', '" + newStocklist.getModifier() + "', '" + t + "');";

			state.executeUpdate(sqlquery);

			// Get ID from last line
			try {
				ResultSet result = state.executeQuery("SELECT `Id` FROM `Stocklist` ORDER BY `Id` DESC LIMIT 1;");

				while (result.next()) {
					newStocklist.setId(result.getInt("Id"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			List<Component> listOfComponents = newStocklist.getComponentList();
			List<Integer> listOfComponentsAmount = newStocklist.getAmountListOfComponent();
			for (int i = 0; i < listOfComponents.size(); i++) {

				String sqlqueryComponent = "INSERT INTO `db_sms`.`StocklistComponent` (`Componentid`, `Stocklistid`, `Amount`) VALUES ('"
						+ listOfComponents.get(i).getId()
						+ "', '"
						+ newStocklist.getId()
						+ "', '"
						+ listOfComponentsAmount.get(i).toString() + "');";

				if (listOfComponentsAmount.get(i) > 0) // dont add Elements with
														// Amount under 1 (0 or
														// minus)
					state.executeUpdate(sqlqueryComponent);

			}

			List<ComponentGroup> listOfComponentGroups = newStocklist.getComponentGroupList();
			List<Integer> listOfComponentGroupsAmount = newStocklist.getAmountListOfComponentGroup();
			for (int i = 0; i < listOfComponentGroups.size(); i++) {
				String sqlqueryComponent = "INSERT INTO `db_sms`.`StocklistComponentgroup` (`StocklistComponentgroupid`, `Stocklistid`, `Amount`) VALUES('"
						+ listOfComponentGroups.get(i).getId()
						+ "', '"
						+ newStocklist.getId()
						+ "', '"
						+ listOfComponentGroupsAmount.get(i).toString() + "'); ";

				if (listOfComponentGroupsAmount.get(i) > 0)// dont add Elements
															// with Amount under
															// 1 (0 or minus)
					state.executeUpdate(sqlqueryComponent);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		DatebaseConnection.release(con);
	}

	public ArrayList<Stocklist> loadAllStocklistsIncludingRelations() {
		Connection con = DatebaseConnection.connection("loadAllStocklistsIncludingRelations");
		ArrayList<Stocklist> resultList = new ArrayList<>();

		try {
			Statement StateMentStocklist = con.createStatement();
			ResultSet ResultStocklist = StateMentStocklist.executeQuery("SELECT * FROM Stocklist");

			while (ResultStocklist.next()) {
				Stocklist sl = new Stocklist();
				sl.setId(ResultStocklist.getInt("Id"));
				sl.setName(ResultStocklist.getString("Name"));
				sl.setCreationDate(ResultStocklist.getTimestamp("Creationdate"));
				sl.setLastModified(ResultStocklist.getTimestamp("LastModified"));
				sl.setModifier(ResultStocklist.getInt("Modifier"));

				// Load all ComponentGroups
				Statement StatementStocklistComponentGroup = con.createStatement();
				ResultSet ResultStocklistComponentGroup = StatementStocklistComponentGroup
						.executeQuery("SELECT * From StocklistComponentgroup JOIN Componentgroup ON StocklistComponentgroup.StocklistComponentgroupid = Componentgroup.Id WHERE StocklistComponentgroup.Stocklistid =  '"
								+ sl.getId() + "'");
				while (ResultStocklistComponentGroup.next()) {
					ComponentGroup cg = new ComponentGroup();
					cg.setId(ResultStocklistComponentGroup.getInt("Id"));
					cg.setComponentGroupName(ResultStocklistComponentGroup.getString("Name"));
					cg.setCreationDate(ResultStocklistComponentGroup.getTimestamp("Creationdate"));
					cg.setModificationDate(ResultStocklistComponentGroup.getTimestamp("LastModified"));
					cg.setModifier(ResultStocklistComponentGroup.getInt("Modifier"));

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
						cg = FillComponentGroupRelations(cg);
					sl.addComponentGroup(cg, ResultStocklistComponentGroup.getInt("Amount"));

				}

				// get all sub Components
				try {
					Statement StatementStocklistComponents = con.createStatement();
					ResultSet ResultsStocklistComponent = StatementStocklistComponents
							.executeQuery("SELECT * From StocklistComponent JOIN Component ON StocklistComponent.Componentid = Component.Id WHERE StocklistComponent.Stocklistid =  '"
									+ sl.getId() + "'");

					while (ResultsStocklistComponent.next()) {
						Component c = new Component();
						c.setId(ResultsStocklistComponent.getInt("Component.Id"));
						c.setName(ResultsStocklistComponent.getString("Component.Name"));
						c.setDescription(ResultsStocklistComponent.getString("Component.Description"));
						c.setMaterialDescription(ResultsStocklistComponent.getString("Component.Materialdescription"));
						c.setModifier(ResultsStocklistComponent.getInt("Component.Modifier"));
						c.setCreationdate(ResultsStocklistComponent.getTimestamp("Component.Creationdate"));
						c.setLastModified(ResultsStocklistComponent.getTimestamp("Component.LastModified"));

						sl.addComponent(c, ResultsStocklistComponent.getInt("Amount"));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				resultList.add(sl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		DatebaseConnection.release(con);
		return resultList;
	}

	private ComponentGroup FillComponentGroupRelations(ComponentGroup cgToEdit) {
		Connection con = DatebaseConnection.connection("FillComponentGroupRelations");

		// get all sub Groups
		try {
			Statement state = con.createStatement();
			ResultSet result = state
					.executeQuery("SELECT * From ComponenGroupRelations JOIN Componentgroup ON ComponenGroupRelations.ComponentGroupID2 = Componentgroup.Id WHERE ComponenGroupRelations.Tag='G' AND ComponenGroupRelations.ComponentGroupID = '"
							+ cgToEdit.getId() + "'");

			while (result.next()) {
				ComponentGroup cg = new ComponentGroup();
				cg.setId(result.getInt("ComponenGroupRelations.ComponentGroupID2"));
				cg.setComponentGroupName(result.getString("Componentgroup.Name"));
				cg.setCreationDate(result.getTimestamp("Componentgroup.Creationdate"));
				cg.setModificationDate(result.getTimestamp("Componentgroup.LastModified"));
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
					cg = FillComponentGroupRelations(cg);

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
				c.setMaterialDescription(result.getString("Component.Materialdescription"));
				c.setModifier(result.getInt("Component.Modifier"));
				c.setCreationdate(result.getTimestamp("Component.Creationdate"));
				c.setLastModified(result.getTimestamp("Component.LastModified"));

				cgToEdit.addComponent(c, result.getInt("Amount"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		DatebaseConnection.release(con);
		return cgToEdit;
	}

	public void updateStockList(Stocklist newStocklist) {
		Connection con = DatebaseConnection.connection("updateStockList");
		try {

			Statement state = con.createStatement();

			state.execute("UPDATE `db_sms`.`Stocklist` SET `Name` = '" + newStocklist.getName() + "', `Modifier` = '"
					+ newStocklist.getModifier() + "',LastModified='" + DateHelperClass.getCurrentTime()
					+ "' WHERE `Stocklist`.`Id` = " + newStocklist.getId() + ";");

			state.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

		DatebaseConnection.release(con);
	}

	public void updateAmountOfStocklistComponentGrouElement(Stocklist newStocklist, ComponentGroup original,
			Integer amount) {
		Connection con = DatebaseConnection.connection("updateAmountOfStocklistComponentGrouElement");
		try {

			Statement state = con.createStatement();

			state.execute("UPDATE `db_sms`.`StocklistComponentgroup` SET `Amount` = '" + amount
					+ "' WHERE `StocklistComponentgroup`.`StocklistComponentgroupid` = " + original.getId()
					+ " AND `StocklistComponentgroup`.`Stocklistid` = " + newStocklist.getId() + ";");

			state.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

		DatebaseConnection.release(con);
	}

	public void updateAmountOfStocklistComponentElement(Stocklist newStocklist, Component originalComponent,
			Integer amount) {
		Connection con = DatebaseConnection.connection("updateAmountOfStocklistComponentElement");

		try {

			Statement state = con.createStatement();

			state.execute("UPDATE `db_sms`.`StocklistComponent` SET `Amount` = '" + amount
					+ "' WHERE `StocklistComponent`.`Componentid` = " + originalComponent.getId()
					+ " AND `StocklistComponent`.`Stocklistid` = " + newStocklist.getId() + ";");
			

			state.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

		DatebaseConnection.release(con);
	}

	public void insertComponentGroupToSocklist(Stocklist newStocklist,
			ComponentGroup newStockListComponentGroupToInsert, Integer amount) {
		Connection con = DatebaseConnection.connection("insertComponentGroupToSocklist");
		try {

			Statement state = con.createStatement();
			state.execute("INSERT INTO `db_sms`.`StocklistComponentgroup` (`StocklistComponentgroupid`, `Stocklistid`, `Amount`) VALUES ('"
					+ newStockListComponentGroupToInsert.getId()
					+ "', '"
					+ newStocklist.getId()
					+ "', '"
					+ amount + "');");
			

			state.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

		DatebaseConnection.release(con);
	}

	public void insertComponentToStocklist(Stocklist newStocklist, Component newStockListComponentToInsert,
			Integer amount) {
		Connection con = DatebaseConnection.connection("insertComponentToStocklist");
		try {

			Statement state = con.createStatement();
			state.execute("INSERT INTO `db_sms`.`StocklistComponent` (`Componentid`, `Stocklistid`, `Amount`) VALUES ('"
					+newStockListComponentToInsert.getId()
					+"', '"
					+newStocklist.getId()
					+"', '"
					+amount 
					+"'); ");
			

			state.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

		DatebaseConnection.release(con);
	}

	public void deleteStocklistComponentElement(Stocklist newStocklist, Component originalComponent,
			Integer integer) {
		Connection con = DatebaseConnection.connection("deleteStocklistComponentElement");
		try {

			Statement state = con.createStatement();
	
			state.execute("DELETE FROM `db_sms`.`StocklistComponent` WHERE `StocklistComponent`.`Componentid` = "
					+originalComponent.getId()
					+" AND `StocklistComponent`.`Stocklistid` = '"
					+ newStocklist.getId()
					+"'");
			

			state.close();

		} catch (Exception e) {
			e.printStackTrace();

		}
		DatebaseConnection.release(con);
	}

	public void deleteStockListComponentGroupElement(Stocklist newStocklist, ComponentGroup originalComponentGroup,
			Integer integer) {
		Connection con = DatebaseConnection.connection("deleteStockListComponentGroupElement");
		try {

			Statement state = con.createStatement();
	
			state.execute("DELETE FROM `db_sms`.`StocklistComponentgroup` WHERE `StocklistComponentgroup`.`StocklistComponentgroupid` = "
					+originalComponentGroup.getId()
					+" AND `StocklistComponentgroup`.`Stocklistid` = '"
					+ newStocklist.getId()
					+"'");
			

			state.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

		DatebaseConnection.release(con);
	}

	public void deleteStructureListByID(Stocklist originalStocklistToEdit) {
		Connection con = DatebaseConnection.connection("deleteStructureListByID");
		try {

			Statement state = con.createStatement();
	
			state.execute("DELETE FROM `db_sms`.`Stocklist` WHERE `Stocklist`.`Id` = "+originalStocklistToEdit.getId());
			

			state.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

		DatebaseConnection.release(con);
	
		
	}
}