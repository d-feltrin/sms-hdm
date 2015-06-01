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
		
		username = name;

		return "Name:" + name + "<br> Email:" + email + "<br>Passwort:" + keyword + "<br><br>";

	}
	
}


/*
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.sms.server.db.ComponentMapper;
import de.hdm.sms.server.db.UserMapper;
import de.hdm.sms.shared.AService;
import de.hdm.sms.shared.bo.*;

@SuppressWarnings("serial")
public class AServiceImpl extends RemoteServiceServlet implements AService {
	private ComponentMapper cMapper = null;
	private UserMapper uMapper = null;

	public void init() throws IllegalArgumentException {
		this.cMapper = ComponentMapper.componentMapper();
		this.uMapper = UserMapper.userMapper();
	}

	public void insertComponent(Component c) {
		init();
		cMapper.insertComponent(c);
	}

	@Override
	public ArrayList<Component> loadAllComponents() {
		ArrayList<Component> ComponentList = cMapper.loadAllComponents();
		return ComponentList;
	}

	@Override
	public Component getOneComponentIdByName(String selectedComponent) {
		Component c = new Component();
		c = cMapper.getOneComponentIdByName(selectedComponent);
		return c;
	}

	@Override
	public void deleteComponentById(int deleteComponentId) {
		cMapper.deleteComponentById(deleteComponentId);
		
	}

	@Override
	public void updateComponentById(Component c) {
		cMapper.updateComponentById(c);
		
	}

	@Override
	public void insertUser(User u) {
		uMapper.insertUser(u);
		
	}

	@Override
	public ArrayList<User> loadAllUsers() {
		
			ArrayList<User> UserList = uMapper.loadAllUsers();
			return UserList;
		
	}

	@Override
	public User getOneUserIdByName(String selectedUser) {
		User u = new User();
		u = uMapper.getOneComponentIdByName(selectedUser);
		return u;
	}

	@Override
	public void deleteUserById(int deleteUserId) {
		uMapper.deleteUserById(deleteUserId);
		
	}

	@Override
	public void updateUserById(User u) {
		uMapper.updateUserById(u);
	}
}
*/