package de.hdm.sms.server;

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
	@Override
	public User getUserByEmail(String eMailAdress) {
		User u = new User();
		u = uMapper.getUserByEmail(eMailAdress);
		return u;
	}
}