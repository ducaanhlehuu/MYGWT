package com.hello.client.activities.contact;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.hello.client.ClientUtils;
import com.hello.shared.model.User;
import com.github.nmorel.gwtjackson.client.ObjectMapper;


public class ApiClient {

    private static final String BASE_URL = "http://localhost:8081/api/users/";

    interface UserListMapper extends ObjectMapper<List<User>> {}

    public static void getContactFromServer(AsyncCallback<List<User>> callback) {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, BASE_URL);
        try {
            builder.sendRequest(null, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    if (response.getStatusCode() == Response.SC_OK) {
                        String jsonResponse = response.getText();
                        UserListMapper mapper = GWT.create(UserListMapper.class);
                        List<User> users = mapper.read(jsonResponse);
                        Window.alert("Call BE Service");
                        callback.onSuccess(users);
                    } else {
                        ClientUtils.log("Error in getContact: " + response.getStatusText());
                        callback.onFailure(new Exception(response.getStatusText()));
                    }
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    ClientUtils.log("Error in getContact: " + exception.getMessage());
                    callback.onFailure(new Exception(exception));
                }
            });
        } catch (RequestException e) {
            ClientUtils.log("Error in getContact: " + e.getMessage());
            callback.onFailure(e);
        }
    }
    public static void deleteContact(User user, AsyncCallback<Boolean> callback) {
        String url = BASE_URL + user.getId();
        RequestBuilder builder = new RequestBuilder(RequestBuilder.DELETE, url);
        try {
            builder.sendRequest(null, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    if (response.getStatusCode() == Response.SC_OK) {
                        callback.onSuccess(true);
                    } else {
                        callback.onSuccess(false);
                    }
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    callback.onFailure(exception);
                }
            });
        } catch (RequestException e) {
            callback.onFailure(e);
        }
    }
    
    public static void filterContact(String property, String keyword, AsyncCallback<List<User>> callback) {
        String url = BASE_URL + "search?property=" + property +"&keyword=" + keyword;
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
        try {
            builder.sendRequest(null, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    if (response.getStatusCode() == Response.SC_OK) {
                    	 String jsonResponse = response.getText();
                         UserListMapper mapper = GWT.create(UserListMapper.class);
                         List<User> users = mapper.read(jsonResponse);
                        callback.onSuccess(users);
                    } else {
                        if(response.getStatusCode() == Response.SC_BAD_REQUEST) {
                        	Window.alert("Bad Request!");
                        	callback.onSuccess(null);
                        }
                    }
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    callback.onFailure(exception);
                }
            });
        } catch (RequestException e) {
            callback.onFailure(e);
        }
    }
}
