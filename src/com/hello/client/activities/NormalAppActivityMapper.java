package com.hello.client.activities;

import com.google.gwt.place.shared.Place;
import com.hello.client.activities.contact.ContactActivity;
import com.hello.client.activities.contact.ContactPlace;
import com.hello.client.activities.home.HomeActivity;
import com.hello.client.activities.home.HomePlace;
import com.hello.client.others.ActivityCallbackHandler;
import com.hello.client.others.AsyncActivityMapper;


public class NormalAppActivityMapper implements AsyncActivityMapper {
	
	private ClientFactory clientFactory;
	
	public NormalAppActivityMapper(ClientFactory clientFactory){
		this.clientFactory = clientFactory;
	}

	@Override
	public void getActivity(final Place place, final ActivityCallbackHandler activityCallbackHandler) {
		if (place instanceof HomePlace) {
			activityCallbackHandler.onRecieveActivity( new HomeActivity(clientFactory, place));
		} 
		else if (place instanceof ContactPlace) {
			activityCallbackHandler.onRecieveActivity( new ContactActivity(clientFactory, place));
		} 
	}
}
