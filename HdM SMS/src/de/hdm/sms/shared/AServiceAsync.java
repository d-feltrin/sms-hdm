package de.hdm.sms.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.sms.shared.bo.Component;
import de.hdm.sms.shared.bo.User;

public interface AServiceAsync {

	void insertComponent(Component c, AsyncCallback<Void> callback);

	void loadAllComponents(AsyncCallback<ArrayList<Component>> asyncCallback);

	void getOneComponentIdByName(String selectedComponent,
			AsyncCallback<Component> asyncCallback);

	void deleteComponentById(int deleteComponentId,
			AsyncCallback<Void> asyncCallback);

	void updateComponentById(Component c, AsyncCallback<Void> asyncCallback);

	void insertUser(User u, AsyncCallback<Void> asyncCallback);

	void loadAllUsers(AsyncCallback<ArrayList<User>> asyncCallback);

	void getOneUserIdByName(String selectedUser,
			AsyncCallback<User> asyncCallback);

	void deleteUserById(int deleteUserId, AsyncCallback<Void> asyncCallback);

	void updateUserById(User u, AsyncCallback<Void> asyncCallback);

	void getUserByEmail(String eMailAdress, AsyncCallback<User> callback);

}
