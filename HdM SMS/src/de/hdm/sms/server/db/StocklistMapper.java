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
}
