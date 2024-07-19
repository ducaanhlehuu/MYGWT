package com.hello.server;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.appengine.api.search.*;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import com.hello.shared.model.User;


@Service
public class ObjectifyUserService {
	private static Index index;
    static {
        Closeable closeable =  ObjectifyService.begin();
        IndexSpec indexSpec = IndexSpec.newBuilder().setName("UserIndex").build();
        index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
    }

    private Objectify ofy = ObjectifyService.ofy();

    public User addUser(User user) {
        ofy.save().entity(user).now();
        try {
        	indexUser(user);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return user;
    }

    public User updateUser(User user) {
        ObjectifyService.ofy().save().entity(user).now();
        try {
        	System.out.println("Pre" + user.getId());
        	indexUser(user);
        	 System.out.println("After" + user.getId());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return user;
    }

    public void deleteUser(User user) {
        ofy.delete().type(User.class).id(user.getId()).now();
        deleteIndex(user.getUsername());
    }

    public User getUserById(Long userId) {
        return ofy.load().type(User.class).id(userId).now();
    }

    public List<User> getAllUsers() {
    	List<User> query = ObjectifyService.ofy().load().type(User.class).list();
        return convertFromLoad(query);
    }
    
    public User getUserByUsername(String username) {
        return ofy.load().type(User.class).filter("username", username).first().now();
    }
    
    
    
    public List<User> findUsers(String property, String keyword) {
        List<User> query = ObjectifyService.ofy().load().type(User.class).filter(property + " >=", keyword)
                .filter(property + " <=", keyword + "\uFFFD").list();
        return convertFromLoad(query);
    }
    
    public List<User> findUsers(String keyword) {
    	List<User> users = new ArrayList<>();
    	users.addAll(findUsers("name",keyword));
    	users.addAll(findUsers("phoneNumber",keyword));
    	users.addAll(findUsers("address",keyword));
    	users.addAll(findUsers("username",keyword));
    	
        return users;
    }
    
    
    private List<User> convertFromLoad(List<User> listInDatabase){
    	List<User> listInServer = new ArrayList<>();
    	listInServer.addAll(listInDatabase);
    	return listInServer;
    }
    
    
    
    public Document createDocumentFromUser(User user) {
        return Document.newBuilder()
            .setId(user.getUsername())
            .addField(Field.newBuilder().setName("id").setAtom(user.getId().toString()))
            .addField(Field.newBuilder().setName("username").setAtom(user.getUsername()))
            .addField(Field.newBuilder().setName("name").setText(user.getName()))
            .addField(Field.newBuilder().setName("address").setText(user.getAddress()))
            .addField(Field.newBuilder().setName("phoneNumber").setText(user.getPhoneNumber()))
            .build();
    }
    
    
    public void indexUser(User user) throws InterruptedException {

        Document doc = createDocumentFromUser(user);
        
        final int maxRetry = 3;
        int attempts = 0;
        int delay = 2;
        while (true) {
            try {
                index.put(doc);
            } catch (PutException e) {
                if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult().getCode())
                        && ++attempts < maxRetry) {
                    Thread.sleep(delay * 1000);
                    delay *= 2;
                    continue;
                } else {
                    throw e;
                }
            }
            break;
        }
    }
    
    
    public  List<User> searchUser(String queryString) {
        List<User> users = new ArrayList<>();
        final int maxRetry = 3;
        int attempts = 0;
        int delay = 2;
        
        // Set the desired number of results
        int maxResults = 100; // Change this to the desired number of results

        // Build the query with options
        QueryOptions options = QueryOptions.newBuilder().setLimit(maxResults).build();
        Query query = Query.newBuilder().setOptions(options).build(queryString);
        
        while (true) {
            try {
                Results<ScoredDocument> results = index.search(query);
                System.out.print("Hello" + results.toString());
               
                for (ScoredDocument document : results) {
                	Long id = Long.valueOf(document.getOnlyField("id").getAtom());
                    String username = document.getOnlyField("username").getAtom();
                    String name = document.getOnlyField("name").getText();
                    String address = document.getOnlyField("address").getText();
                    String phoneNumber = document.getOnlyField("phoneNumber").getText();
                    users.add(new User(id,username,name,address,phoneNumber));
                    System.out.println("Username: " + username + ", Name: " + name + ", Address: " + address + ", Phone Number: " + phoneNumber);
                }
            } catch (SearchException e) {
                if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult().getCode())
                        && ++attempts < maxRetry) {
                    try {
                        Thread.sleep(delay * 1000);
                    } catch (InterruptedException e1) {
                        // ignore
                    }
                    delay *= 2;
                    continue;
                } else {
                    throw e;
                }
            }
            break;
        }
     
        return users;
    }
    
    private void deleteIndex(String documentId) {

        final int maxRetry = 3;
        int attempts = 0;
        int delay = 2;
        while (true) {
            try {
                index.delete(documentId);
            } catch (DeleteException e) {
                if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult().getCode())
                        && ++attempts < maxRetry) {
                    try {
                        Thread.sleep(delay * 1000);
                    } catch (InterruptedException e1) {
                        // ignore
                    }
                    delay *= 2;
                    continue;
                } else {
                    throw e;
                }
            }
            break;
        }
    }
    
}
