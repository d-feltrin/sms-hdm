package de.hdm.sms.server.db;

import com.google.appengine.api.rdbms.AppEngineDriver;
import java.sql.*;

public class DatebaseConnection {

	private static Connection con = null;

	public static Connection connection() {

		/**
		 * Falls die DB-Connection noch nicht besteht, führe nachfolgende
		 * Befehle aus.
		 */
		if (con == null) {

			try {
				DriverManager.registerDriver(new AppEngineDriver());

				boolean UseLocalConnection = true;

				if (UseLocalConnection) {
					con = DriverManager.getConnection("jdbc:mysql://85.214.149.12:3306/db_sms", "smshdm", "hdmitpj");
				} else {
					con = DriverManager.getConnection("jdbc:google:rdbms://stuecklistenmanagementsystem:hdmitpjsms/db_sms", "root", "");
				}
			}

			catch (SQLException e1) {
				con = null;

				e1.printStackTrace();
			}
		}

		return con;
	}

}