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

	public static Connection connection() {

		if (FirstTime) {
			FirstTime = false;
			// First Call - create 10 DB Connections
			try {
				DriverManager.registerDriver(new AppEngineDriver());

				AvailableConnections = new ArrayList<Connection>();
				for (int i = 0; i < MaxAmount; i++) {
					try {
						System.out.println("Created: " + i);
						Connection con = null;

						boolean UseLocalConnection = false;

						if (UseLocalConnection) {
							con = DriverManager.getConnection("jdbc:mysql://85.214.149.12:3306/db_sms", "smshdm", "hdmitpj");
						} else {
							con = DriverManager.getConnection("jdbc:google:rdbms://stuecklistenmanagementsystem:hdmitpjsms/db_sms", "root", "");
						}
						AvailableConnections.add(con);
						amountAvailable++;
					} catch (SQLException ex) {

						ex.printStackTrace();
					}

				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return getConnection(1);
	}

	private static Connection getConnection(int tryAmount) {
		System.out.println(amountAvailable);
		if (tryAmount > 5) {
			System.out.println("More than " + 5 + " tries to get DB.");
			return null;
		}

		if (amountAvailable > 0) {
			Connection conToReturn = AvailableConnections.get(amountAvailable - 1);
			AvailableConnections.remove(conToReturn);
			amountAvailable--;
			return conToReturn;
		} else {
			Timer timeoutTimer = new Timer() {
				public void run() {
					// Wait until next connection is free
				}
			};

			timeoutTimer.schedule(3 * 1000); // wait 5 seconds
			return getConnection(tryAmount + 1);
		}
	}

	public static void release(Connection con) {
		System.out.println(amountAvailable);
		AvailableConnections.add(con);
		amountAvailable++;
	}

}