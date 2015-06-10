package de.hdm.sms.client;

import java.util.logging.Logger;

import com.google.gwt.core.shared.GWT;

import de.hdm.sms.shared.AServiceAsync;
import de.hdm.sms.shared.LoginService;
import de.hdm.sms.shared.LoginServiceAsync;
import de.hdm.sms.shared.AService;

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
	 
	 private static LoginServiceAsync loginService = null;
	 
	 private static final Logger log = Logger.getLogger("LOGGER_NAME");
	 
	 

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

	  public static AServiceAsync getAService() {
		    if (st�cklistenVerwaltung == null) { 
		      st�cklistenVerwaltung = GWT.create(AService.class);
		    }

		    return st�cklistenVerwaltung;
		  }
}
