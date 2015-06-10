package de.hdm.sms.client;

import java.util.logging.Logger;

import com.google.gwt.core.shared.GWT;

import de.hdm.sms.shared.AServiceAsync;
import de.hdm.sms.shared.LoginService;
import de.hdm.sms.shared.LoginServiceAsync;
import de.hdm.sms.shared.AService;

/**
 * Klasse mit Eigenschaften und Diensten, die für alle Client-seitigen Klassen
 * relevant sind. Hier werden alle RPC-Klassen als Singletons verwaltet.
 * 
 * @author Thies
 * @author Dimitriu
 *
 */

public class ClientsideSettings {

	private static AServiceAsync smsAdministration = null;

	private static LoginServiceAsync loginService = null;

	private static final Logger log = Logger.getLogger("LOGGER_NAME");

	/**
	 * Gibt den Logger zurï¿½ck
	 * 
	 * @return eindeutige Instanz des Typs {@link Logger}
	 */
	public static Logger getLogger() {
		return log;
	}

	/**
	 * Gibt den LoginService zurï¿½ck.
	 * 
	 * @return eindeutige Instanz vom Typ {@link LoginServiceAsync}
	 */
	public static LoginServiceAsync getLoginService() {
		if (loginService == null) {
			loginService = GWT.create(LoginService.class);
		}
		return loginService;
	}

	/**
	 * Gibt die RaumplanerAdministraion zurï¿½ck und erstellt diese bei Bedarf.
	 * 
	 * @return eindeutige Instanz vom Typ {@link AServiceAsync}
	 */
	public static AServiceAsync getAService() {
		if (smsAdministration == null) {
			smsAdministration = GWT
					.create(AService.class);
		}
		return smsAdministration;
	}
}
