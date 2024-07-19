package com.hello.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.hello.client.activities.AppPlaceHistoryMapper;
import com.hello.client.activities.ClientFactory;
import com.hello.client.activities.ClientFactoryImpl;
import com.hello.client.activities.NormalAppActivityMapper;
import com.hello.client.activities.contact.ContactPlace;
import com.hello.client.activities.home.EditContactPlace;
import com.hello.client.others.AsyncActivityManager;
import com.hello.client.others.AsyncActivityMapper;
import com.hello.client.others.MyPlaceHistoryMapper;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class AppManager implements EntryPoint {
	
	public static final GreetingServiceAsync GREETING_SERVICES = GWT.create(GreetingService.class);
	public static final ClientFactory CLIENT_FACTORY = new ClientFactoryImpl();
	private String domain = "http://locahost:8081";
	
	public void onModuleLoad() {
		
		if((Window.Location.getHref().contains("127.0.0.1") || Window.Location.getHref().contains("localhost"))) {
            ServiceDefTarget serviceDef = (ServiceDefTarget)GREETING_SERVICES;
            serviceDef.setServiceEntryPoint(domain  + "/gwt_basic/greet");
		}
		
		SimplePanel display = new SimplePanel();
		AsyncActivityMapper activityMapper = new NormalAppActivityMapper(CLIENT_FACTORY);
		AsyncActivityManager activityManager = new AsyncActivityManager(activityMapper, CLIENT_FACTORY.getEventBus());
		activityManager.setDisplay(display);
		RootPanel.get().add(display);
		final PlaceHistoryMapper myHistoryMapper = GWT.create(MyPlaceHistoryMapper.class);
		PlaceHistoryMapper historyMapper = new AppPlaceHistoryMapper(myHistoryMapper);
		final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(CLIENT_FACTORY.getPlaceController(), CLIENT_FACTORY.getEventBus(), new ContactPlace());
		historyHandler.handleCurrentHistory();
	}
}
