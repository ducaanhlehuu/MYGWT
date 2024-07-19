package com.hello.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.hello.shared.model.User;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
	
	
	void getContact(AsyncCallback<List<User>> callback);
	
	void deleteContact(User user, AsyncCallback<Boolean> callback);
	
	void updateContact(User user, AsyncCallback<User> callback);
	
	void checkExist(User user, AsyncCallback<Boolean> callback);
	
	void findContact(String property, String keyword,AsyncCallback<List<User>> callback);
}
