package de.hdm.sms.shared;


import java.util.List;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.hdm.sms.shared.bo.*;

/** Synchrone Schnittstelle, die der Verwaltung dient und alle notwenidgen Methoden für die StücklistenAdministration {@link StücklistenAdministrationImpl} bereitstellt. ( FEHLT NOCH)

 * @author Dimitriu
  */

@RemoteServiceRelativePath("stücklistenAdministration")

public interface StücklistenAdministration extends RemoteService{

	public User getUserByEmail(String user);
	public List<User>getAllUser();
}
