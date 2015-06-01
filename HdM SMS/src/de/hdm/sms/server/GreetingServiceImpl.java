package de.hdm.sms.server;

import de.hdm.sms.client.GreetingService;
import de.hdm.sms.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	
	private String username = new String();
	
	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}


		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		
		username= input;

		return "Hallo " + input + ",<br><br>Ihr Login war erfolgreich!<br><br>";
	}	
	
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	@Override
	public String getName(String name) throws IllegalArgumentException {
		
		name = escapeHtml(this.username);

		return name + ", <br>";
	}

	@Override
	public String createUsergreetServer(String name, String email,
			String keyword) throws IllegalArgumentException {
		
		name = escapeHtml(name);
		email = escapeHtml(email);
		keyword = escapeHtml(keyword);

		return "Name:" + name + "<br> Email:" + email + "<br>Passwort:" + keyword + "<br><br>";

	}
	
}