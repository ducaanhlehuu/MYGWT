package com.hello.client.activities;

import com.google.gwt.place.shared.Place;
import com.hello.client.activities.class_divide.ClassActivity;
import com.hello.client.activities.class_divide.ClassPlace;
import com.hello.client.activities.contact.ContactActivity;
import com.hello.client.activities.contact.ContactPlace;
import com.hello.client.activities.home.EditContactActivity;
import com.hello.client.activities.home.EditContactPlace;
import com.hello.client.others.ActivityCallbackHandler;
import com.hello.client.others.AsyncActivityMapper;


public class NormalAppActivityMapper implements AsyncActivityMapper {
	
	private ClientFactory clientFactory;
	
	public NormalAppActivityMapper(ClientFactory clientFactory){
		this.clientFactory = clientFactory;
	}

	@Override
	public void getActivity(final Place place, final ActivityCallbackHandler activityCallbackHandler) {
		if (place instanceof EditContactPlace) {
			activityCallbackHandler.onRecieveActivity( new EditContactActivity(clientFactory, (EditContactPlace) place));
		} 
		else if (place instanceof ContactPlace) {
			activityCallbackHandler.onRecieveActivity( new ContactActivity(clientFactory, (ContactPlace) place));
		}
		
		else if (place instanceof ClassPlace) {
			activityCallbackHandler.onRecieveActivity( new ClassActivity(clientFactory, place));
		} 
	}
}
