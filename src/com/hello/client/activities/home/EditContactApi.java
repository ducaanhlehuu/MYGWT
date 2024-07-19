package com.hello.client.activities.home;

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
import com.hello.shared.model.ResponseDTO;
import com.hello.shared.model.User;
import com.github.nmorel.gwtjackson.client.ObjectMapper;


public class EditContactApi {

    private static final String BASE_URL = "http://localhost:8081/api/users/";

    interface UserMapper extends ObjectMapper<User> {}
    private static  UserMapper userMapper = GWT.create(UserMapper.class);
    
    
    interface ResponseDTOMapper extends ObjectMapper<ResponseDTO> {}
    private static  ResponseDTOMapper responseMapper = GWT.create(ResponseDTOMapper.class);

    public static void checkExist(User user, AsyncCallback<Boolean> callback) {
        String url = BASE_URL + "checkExist";
        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
        builder.setHeader("Content-Type", "application/json");
        try {
        	String requestData = userMapper.write(user);
            builder.sendRequest(requestData, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    if (response.getStatusCode() == Response.SC_OK) {
                    	String requestResponse = response.getText();
                    	ResponseDTO responseDTO = responseMapper.read(requestResponse);
                        callback.onSuccess(responseDTO.getStatusCode().equals("200")?true:false);
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
    
    
    public static void updateUser(User user, AsyncCallback<User> callback) {
        String url = BASE_URL;
        RequestBuilder builder = new RequestBuilder(RequestBuilder.PUT, url);
        builder.setHeader("Content-Type", "application/json");
        try {
        	String requestData = userMapper.write(user);
            builder.sendRequest(requestData, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    if (response.getStatusCode() == Response.SC_OK || response.getStatusCode() == Response.SC_CREATED) {
                    	String requestResponse = response.getText();
                    	User user = userMapper.read(requestResponse);
                        callback.onSuccess(user);
                    } 
                    else {
                    	Window.alert("cannot add/update User");
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
