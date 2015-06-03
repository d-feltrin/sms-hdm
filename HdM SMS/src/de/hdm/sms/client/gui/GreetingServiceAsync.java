package de.hdm.sms.client.gui;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void getName(String name, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void search(String name, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void getName(AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void createUser(String name, String email, String keyword, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void createElement(String name, String description, String material, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void createModule(String name, String component1, String component2, String component3, 
			Boolean endproduct, AsyncCallback<String> callback)
					throws IllegalArgumentException;
	void getTypeOfComponent(String name, AsyncCallback<String> callback)
					throws IllegalArgumentException;

}
