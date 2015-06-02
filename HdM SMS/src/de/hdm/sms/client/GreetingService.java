package de.hdm.sms.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	String createUsergreetServer(String name, String email, String keyword) throws IllegalArgumentException;
	String createElement(String name, String description, String material) throws IllegalArgumentException;
	String getName(String name) throws IllegalArgumentException;
}
