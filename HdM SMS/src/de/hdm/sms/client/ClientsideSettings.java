package de.hdm.sms.client;

import java.util.logging.Logger;

import com.google.gwt.core.shared.GWT;

import de.hdm.sms.shared.AServiceAsync;
import de.hdm.sms.shared.LoginService;
import de.hdm.sms.shared.LoginServiceAsync;
import de.hdm.sms.shared.St�cklistenAdministration;

/**
 * Klasse mit Eigenschaften und Diensten, die f�r alle Client-seitigen Klassen
 * relevant sind. Hier werden alle RPC-Klassen als Singletons verwaltet.
 * 
 * @author Thies
 * @author Dimitriu
 *
 */

public class ClientsideSettings {

	 private static AServiceAsync st�cklistenVerwaltung = null;
	 
	 private static final Logger log = Logger.getLogger("LOGGER_NAME");
	 
	 private static LoginServiceAsync loginService = null;

	 /**
		 * Gibt den Logger zur�ck
		 * 
		 * @return eindeutige Instanz des Typs {@link Logger}
		 */

	 
	 public static Logger getLogger() {
		    return log;
		  }
	 
	 /**
		 * Gibt den LoginService zur�ck.
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
		 * Gibt die St�cklistenVerwaltung zur�ck und erstellt diese bei Bedarf.
		 * 
		 * @return eindeutige Instanz vom Typ {@link AServiceAsync}
		 */

	  public static AServiceAsync getSt�cklistenVerwaltung() {
		    if (st�cklistenVerwaltung == null) { 
		      st�cklistenVerwaltung = GWT.create(St�cklistenAdministration.class);
		    }

		    return st�cklistenVerwaltung;
		  }
}