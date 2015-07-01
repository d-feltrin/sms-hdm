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
	public Connection con = DatebaseConnection.connection();

	protected StocklistMapper() {

	}

	public static StocklistMapper stocklistMapper() {
		if (stocklistMapper == null) {
			stocklistMapper = new StocklistMapper();
		}
		return stocklistMapper;
	}

	public void insertStocklist(Stocklist newStocklist) {
		Connection con = DatebaseConnection.connection();
		try {
			Statement state = con.createStatement();

			Timestamp t = DateHelperClass.getCurrentTime();

			String sqlquery = "INSERT INTO `db_sms`.`Stocklist` (`Name`, `Creationdate`, `Modifier`, `LastModified`) VALUES ( '"
					+ newStocklist.getName() + "', '" + t + "', '" + newStocklist.getLastModified() + "', '" + t + "');";

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
	}

	public ArrayList<Stocklist> loadAllStocklistsIncludingRelations() {
		Connection con = DatebaseConnection.connection();
		ArrayList<Stocklist> resultList = new ArrayList<>();

		try {
			Statement state = con.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM Stocklist");

			while (result.next()) {
				Stocklist sl = new Stocklist();
				sl.setId(result.getInt("Id"));
				sl.setName(result.getString("Name"));
				sl.setCreationDate(result.getTimestamp("Creationdate"));
				sl.setLastModified(result.getTimestamp("LastModified"));
				sl.setModifier(result.getInt("Modifier"));

				boolean hasComponentGroups = false;

				try {
					Statement s2 = con.createStatement();
					ResultSet result2 = s2
							.executeQuery("SELECT * FROM `StocklistComponentgroup`  WHERE Stocklistid = '"
									+ sl.getId() + "'");

					while (result2.next()) {
						hasComponentGroups = true;
						continue;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				if (hasComponentGroups)
					sl = FillComponentGroupRelations(sl);
				
				

				boolean hasComponents = false;

				try {
					Statement s3 = con.createStatement();
					ResultSet result3 = s3
							.executeQuery("SELECT * FROM `StocklistComponent`  WHERE Stocklistid = '"
									+ sl.getId() + "'");

					while (result3.next()) {
						hasComponents = true;
						continue;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				if (hasComponents)
					sl = FillComponentRelations(sl);

				resultList.add(sl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	private Stocklist FillComponentRelations(Stocklist sl) {
		// TODO Auto-generated method stub
		return sl;
	}

	private Stocklist FillComponentGroupRelations(Stocklist sl) {
		// TODO Auto-generated method stub
		return sl;
	}

}
