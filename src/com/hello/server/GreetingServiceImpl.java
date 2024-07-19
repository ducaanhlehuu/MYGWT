package com.hello.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.hello.client.GreetingService;
import com.hello.shared.FieldVerifier;
import com.hello.shared.model.User;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {
	

	public static List<User> listUser = new ArrayList<>();
	private ObjectifyUserService observice;
	private static final int PAGE_SIZE = 10;
	
	public GreetingServiceImpl() {
		observice = new ObjectifyUserService();

	}
	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException("Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo + ".<br><br>It looks like you are using:<br>"
				+ userAgent;
	}
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
	public User getUserByUsername(String username) {
        Objectify ofy = ObjectifyService.ofy();
        return ofy.load().type(User.class).filter("username", username).first().now();
    }
	
	@Override
	public List<User> getContact(){
		
		List<User> users = observice.getAllUsers();

		return users;
		
	}

	@Override
	public boolean deleteContact(User user) {
		
		User userInDB = observice.getUserByUsername(user.getUsername());
	
		if(userInDB != null) {
			observice.deleteUser(user);
			return true;
		}
		
		return false;
	}

	@Override
	public User updateContact(User userDAO) {
		
		User userInDB = observice.getUserByUsername(userDAO.getUsername());
		if(userInDB!=null) {
			System.out.println("update");
			userInDB.setAddress(userDAO.getAddress());
			userInDB.setPhoneNumber(userDAO.getPhoneNumber());
			userInDB.setName(userDAO.getName());
			return observice.updateUser(userInDB);
		}
		else {
			return observice.addUser(userDAO);
		}
		
	}

	@Override
	public boolean checkExist(User userDAO) {
		User userInDB = observice.getUserByUsername(userDAO.getUsername());
		return userInDB!=null;
	}
	@Override
	public List<User> findContact(String property, String keyword) {
		 System.out.println(property + "--" + keyword);
		 if(property.equals("all")) {
			 return observice.searchUser(keyword);
		 }
		 return observice.findUsers(property, keyword);
	}
	
}
