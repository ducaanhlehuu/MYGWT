package com.hello.client.activities;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.hello.client.ClientUtils;
import com.hello.client.activities.class_divide.ClassPlace;
import com.hello.client.activities.contact.ContactPlace;
import com.hello.client.activities.home.EditContactPlace;

@WithTokenizers({EditContactPlace.Tokenizer.class, ContactPlace.Tokenizer.class })

public class AppPlaceHistoryMapper implements PlaceHistoryMapper {
	public static final String DELIMITER = "/";
	public static final String COLON = "=";
	
	private PlaceHistoryMapper placeHistoryMapper;
	
    public AppPlaceHistoryMapper() {
		super();
	}
    public AppPlaceHistoryMapper(PlaceHistoryMapper placeHistoryMapper) {
    	super();
    	this.placeHistoryMapper = placeHistoryMapper;
    }
    
	@Override
	public Place getPlace(final String token) {
		String[] tokens = token.split(DELIMITER); 
		ClientUtils.log("Tokens: " + tokens + " tokens.length: " + tokens.length);
		if(tokens.length == 0) {
			return getDefaultPlace();
		}
		String tokenPlace = tokens[0].trim();
		ClientUtils.log("tokenPlace: " + tokenPlace);
		Place nextPlace = null;
        if (tokenPlace.indexOf(PlaceToken.CONTACT) == 0) {
        	nextPlace = new ContactPlace();
        } else if (tokenPlace.indexOf(PlaceToken.ADD_EDIT) == 0) {
        	nextPlace = new EditContactPlace();
        }
        else {
        	nextPlace = new ClassPlace();
        }
        return nextPlace;
	}
	
	public long getValue(String str){
		String itemIdValue = "";
		if (str.contains(COLON)) {
			String [] tokens = str.split(COLON);
			itemIdValue = tokens[1];
		}
		else {
			itemIdValue = str;
		}
		try{
			return Long.valueOf(itemIdValue);
		} catch(Exception e){
			return -1;
		}
	}
	
	private Place getDefaultPlace() {
		return new EditContactPlace();
	}
	
	@Override
	public String getToken(Place place) {
		String token = placeHistoryMapper.getToken(place);
		if(token != null && token.contains(COLON)){
			if (token.endsWith(COLON)) {
				token = token.replace(COLON, "");
	        }else {
	        	token = token.replace(COLON, "");
	        }
			return token;
		}
		if (place != null && place instanceof ContactPlace) {
			return ((ContactPlace)place).getToken();
		}
		if(place != null && place instanceof ClassPlace) {
			return ((ClassPlace)place).getToken();
		}
		else {
			return ((EditContactPlace)place).getToken();
		}
	}
}