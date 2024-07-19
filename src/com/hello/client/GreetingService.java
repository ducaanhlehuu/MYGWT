package com.hello.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.hello.shared.model.User;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	
	List<User> getContact();
	
	boolean deleteContact(User user);
	
	boolean checkExist(User user);
	
	User updateContact(User user);
	
	List<User> findContact(String property, String keyword);
}
