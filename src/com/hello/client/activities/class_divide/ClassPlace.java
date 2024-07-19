package com.hello.client.activities.class_divide;

import com.google.gwt.place.shared.PlaceTokenizer;
import com.hello.client.activities.PlaceToken;
import com.hello.client.activities.basic.BasicPlace;
import com.hello.shared.model.User;

public class ClassPlace extends BasicPlace {
	
	private User user;
	
	public ClassPlace() {
		super();
	}
	
	
	
	public User getUser() {
		return user;
	}


	public ClassPlace(User user) {
		super();
		this.user = user;
	}
	
	@Override
	public String getToken() {
		return PlaceToken.CLASS_DIVIDE;
	}
	
	public static class Tokenizer implements PlaceTokenizer<ClassPlace> {
        @Override
        public String getToken(ClassPlace place) {
            return place.getToken();
        }

        @Override
        public ClassPlace getPlace(String token) {
            return new ClassPlace();
        }
    }
}
