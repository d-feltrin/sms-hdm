package de.hdm.sms.server.db;
import java.sql.Timestamp;
import java.util.Date;

public class DateHelperClass {
	public static Timestamp getCurrentTime() {
		Date currentTime = new Date(System.currentTimeMillis());
		return new Timestamp(currentTime.getTime());
	}

}
