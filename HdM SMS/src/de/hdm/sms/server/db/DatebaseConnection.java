package de.hdm.sms.server.db;

import com.google.appengine.api.rdbms.AppEngineDriver;
import com.google.gwt.user.client.Timer;

import java.sql.*;
import java.util.ArrayList;

public class DatebaseConnection {

	private static boolean FirstTime = true;
	private static ArrayList<Connection> AvailableConnections = null;
	private static int amountAvailable = 0;
	private static int MaxAmount = 30;
	private static boolean UseLocalConnection = false;

	public static Connection connection(String source) {

		if (FirstTime) {
			FirstTime = false;
			// First Call - create 10 DB Connections
			try {
				DriverManager.registerDriver(new AppEngineDriver());

				AvailableConnections = new ArrayList<Connection>();
				for (int i = 0; i < MaxAmount; i++) {
					try {
						System.out.println("Created: " + (i+1));
						Connection con = null;
						con = getNewConnection();
						AvailableConnections.add(con);
						amountAvailable++;
					} catch (Exception ex) {

						ex.printStackTrace();
					}

				}
				System.out.println("initialized all connections: - Connections left" + amountAvailable);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return getConnection(1,source);
	}

	private static Connection getConnection(int tryAmount, String source) {
		if (tryAmount > 5) {
			System.out.println("More than " + 5 + " tries to get DB.");
			return null;
		}

		if (amountAvailable > 0) {
			Connection conToReturn = AvailableConnections.get(amountAvailable - 1);
			try {
				if (conToReturn.isClosed()) {
					conToReturn = getNewConnection();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			AvailableConnections.remove(conToReturn);
			amountAvailable--;
			System.out.println("Connections left " + amountAvailable + ". Returned one DB-Con to: " + source);
			return conToReturn;
		} else {
			Timer timeoutTimer = new Timer() {
				public void run() {
					// Wait until next connection is free
				}
			};

			timeoutTimer.schedule(3 * 1000); // wait 5 seconds if any connection is available in 5 sec
			return getConnection(tryAmount + 1,source);
		}
	}

	public static void release(Connection con) {
		try{
		AvailableConnections.add(con);
		amountAvailable++;
		System.out.println("Connections left " + amountAvailable + " connection released");
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static Connection getNewConnection() {
		Connection con = null;
		try {
			if (UseLocalConnection) {
				con = DriverManager.getConnection("jdbc:mysql://85.214.149.12:3306/db_sms", "smshdm", "hdmitpj");

			} else {
				con = DriverManager.getConnection("jdbc:google:rdbms://stuecklistenmanagementsystem:hdmitpjsms/db_sms", "root", "");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

}