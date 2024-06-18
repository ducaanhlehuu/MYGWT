package com.hello.client.activities;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.hello.client.activities.contact.ContactView;
import com.hello.client.activities.contact.ContactViewImpl;
import com.hello.client.activities.home.HomeView;
import com.hello.client.activities.home.HomeViewImpl;

public class ClientFactoryImpl implements ClientFactory {
	
	protected SimpleEventBus eventBus;
	protected PlaceController placeController;
	private HomeView homeView;
	private ContactView contactView;
	
	public ClientFactoryImpl() {
		eventBus = new SimpleEventBus();
		placeController = new PlaceController(eventBus);
	}
	
	@Override
	public ContactView getContactView() {
		contactView = new ContactViewImpl();
		return contactView;
	}
	
	@Override
	public HomeView getHomeView() {
		homeView = new HomeViewImpl();
		return homeView;
	}
	
	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}


	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

}
