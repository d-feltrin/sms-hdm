package de.hdm.sms.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void getName(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void createUsergreetServer(String name, String email, String keyword, AsyncCallback<String> callback)
			throws IllegalArgumentException;

}
