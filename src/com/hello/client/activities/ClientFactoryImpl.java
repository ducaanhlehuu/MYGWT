package com.hello.client.activities;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.hello.client.activities.class_divide.ClassView;
import com.hello.client.activities.class_divide.ClassViewImpl;
import com.hello.client.activities.contact.ContactView;
import com.hello.client.activities.contact.ContactViewImpl;
import com.hello.client.activities.home.EditContactView;
import com.hello.client.activities.home.EditContactViewImpl;

public class ClientFactoryImpl implements ClientFactory {
	
	protected SimpleEventBus eventBus;
	protected PlaceController placeController;
	private EditContactView editContactView;
	private ClassView classView;
	private ContactView contactView = null;
	
	public ClientFactoryImpl() {
		eventBus = new SimpleEventBus();
		placeController = new PlaceController(eventBus);
	}
	
	@Override
	public ContactView getContactView() {
		if(contactView==null) {
			contactView = new ContactViewImpl();
		}
		return contactView;
	}
	
	@Override
	public EditContactView getEditContactView() {
		editContactView = new EditContactViewImpl();
		return editContactView;
	}
	
	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}


	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public ClassView getClassView() {
		classView = new ClassViewImpl();
		return classView;
	}

}
