package de.hdm.sms.client.gui;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String getName(String name) throws IllegalArgumentException;
	String search(String name) throws IllegalArgumentException;
	String getName() throws IllegalArgumentException;
	String createUser(String name, String email, String keyword) throws IllegalArgumentException;
	String createElement(String name, String description, String material) throws IllegalArgumentException;
	String createModule(String name, String component1, String component2, String component3, Boolean endproduct) throws IllegalArgumentException;
	String getTypeOfComponent(String name) throws IllegalArgumentException;
	String delete(String name) throws IllegalArgumentException;

}