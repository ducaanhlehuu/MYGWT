package com.hello.client.activities.home;

import com.google.gwt.place.shared.PlaceTokenizer;
import com.hello.client.activities.PlaceToken;
import com.hello.client.activities.basic.BasicPlace;
import com.hello.shared.model.User;

public class EditContactPlace extends BasicPlace {
	
	private User user;
	
	public EditContactPlace() {
		super();
	}
	
	
	
	public User getUser() {
		return user;
	}


	public EditContactPlace(User user) {
		super();
		this.user = user;
	}
	
	@Override
	public String getToken() {
		return PlaceToken.ADD_EDIT;
	}
	
	public static class Tokenizer implements PlaceTokenizer<EditContactPlace> {
        @Override
        public String getToken(EditContactPlace place) {
            return place.getToken();
        }

        @Override
        public EditContactPlace getPlace(String token) {
            return new EditContactPlace();
        }
    }
}
