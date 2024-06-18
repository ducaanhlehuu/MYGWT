package com.hello.client.activities;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.hello.client.activities.contact.ContactView;
import com.hello.client.activities.home.HomeView;

public interface ClientFactory {
	
	public PlaceController getPlaceController();
	public EventBus getEventBus();
	public HomeView getHomeView();
	public ContactView getContactView();

}
