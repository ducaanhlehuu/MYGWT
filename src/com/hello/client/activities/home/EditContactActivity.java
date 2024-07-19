package com.hello.client.activities.home;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.hello.client.ClientUtils;
import com.hello.client.GreetingService;
import com.hello.client.GreetingServiceAsync;
import com.hello.client.activities.ClientFactory;
import com.hello.client.activities.basic.BasicActivity;
import com.hello.client.activities.contact.ContactPlace;
import com.hello.shared.model.User;

public class EditContactActivity extends BasicActivity {

	private EditContactView view;
	private User existedUser ;
	private final GreetingServiceAsync greetingServiceAsync = GWT.create(GreetingService.class);
	
	public EditContactActivity(ClientFactory clientFactory, EditContactPlace place) {
		super(clientFactory, place);
		existedUser = place.getUser();
	}

	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getEditContactView();
		ClientUtils.log("setWidget");
		panel.setWidget(view.asWidget());
		ClientUtils.log("startxxx");
		super.start(panel, eventBus, view);
		ClientUtils.log("start ok");
	}
	@Override
	public void loadData() {
		if(existedUser!=null) {
			view.showContact(existedUser);
		}
	}
	
	@Override
	protected void bind() {
		super.bind();
		addHandlerRegistration(view.getBtnSave().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				existedUser = view.getUser();
				if(existedUser == null) {
					return;
				}
				checkExist();
				
			}
		}));
		addHandlerRegistration(view.getBtnBack().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				clientFactory.getPlaceController().goTo(new ContactPlace());
			}
		}));
	}
	

	private void saveUser() {
//		greetingServiceAsync.updateContact(existedUser, new AsyncCallback<User>() {
//
//            @Override
//            public void onSuccess(User result) {
//            	
//                if (result.getId().equals(existedUser.getId())) {
//                    Window.alert("Update user: " + result.getUsername() + " successfully");
//                    
//                } else {
//                    Window.alert("Add new user: " + result.getUsername() + " successfully");
//                } 
//
//                clientFactory.getPlaceController().goTo(new ContactPlace(result));
//            }
//
//            @Override
//            public void onFailure(Throwable caught) {
//                Window.alert("Failed to update/add");
//            }
//        });
		EditContactApi.updateUser(existedUser, new AsyncCallback<User>() {
						
			@Override
			public void onSuccess(User result) {
			
			   if (result.getId().equals(existedUser.getId())) {
	               Window.alert("Update user: " + result.getUsername() + " successfully");
	               
	           } else {
	               Window.alert("Add new user: " + result.getUsername() + " successfully");
	           } 
	
	           clientFactory.getPlaceController().goTo(new ContactPlace(result));			
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Failed to update/add!");
				
			}
		});
		
		
	}
	
	private void checkExist() {
//		greetingServiceAsync.checkExist(existedUser, new AsyncCallback<Boolean>() {
//
//            @Override
//            public void onSuccess(Boolean result) {
//            	if((result==true && existedUser.getId()!=null)|(result=false)) {
//            		saveUser();
//            	}
//            	else {Window.alert("Existed username");}
//                return;
//            }
//
//            @Override
//            public void onFailure(Throwable caught) {
//                Window.alert("Failed to check exist");
//            }
//        });
		
		EditContactApi.checkExist(existedUser, new AsyncCallback<Boolean>() {
			
			@Override
			public void onSuccess(Boolean result) {
			
				if((result==true && existedUser.getId()!=null)|(result.equals(false))) {
            		saveUser();
            	}
				else {Window.alert("Existed username");}				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Failed to check exist");
				
			}
		});
	}
}
