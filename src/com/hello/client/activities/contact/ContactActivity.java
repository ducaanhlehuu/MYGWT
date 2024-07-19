package com.hello.client.activities.contact;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.hello.client.ClientUtils;
import com.hello.client.GreetingService;
import com.hello.client.GreetingServiceAsync;
import com.hello.client.activities.ClientFactory;
import com.hello.client.activities.basic.BasicActivity;
import com.hello.client.activities.home.EditContactPlace;
import com.hello.client.events.ActionEvent;
import com.hello.client.events.ActionEvent.Action;
import com.hello.client.events.ActionEventHandler;
import com.hello.shared.model.User;

public class ContactActivity extends BasicActivity {

	private ContactView view;
	private final GreetingServiceAsync greetingServiceAsync = GWT.create(GreetingService.class);

	public ContactActivity(ClientFactory clientFactory, ContactPlace place) {
		super(clientFactory, place);
		if(ClientData.listUser!=null) {
			updateListUser(place);
		}
		ClientUtils.log("Activity");

	}
	

	private void updateListUser(ContactPlace place) {
		User updatedUser = place.getUpdatedUser();
		if(updatedUser!=null) {
			boolean existed = false;
			for (User user : ClientData.listUser) {
				if (user.getUsername().equals(updatedUser.getUsername())) {
					user.setAddress(updatedUser.getAddress());
					user.setName(updatedUser.getName());
					user.setPhoneNumber(updatedUser.getPhoneNumber());
					existed = true;
					break;
				}
			}
			
			if(!existed) {
				ClientData.listUser.add(updatedUser);
			}
		}

	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getContactView();
		panel.setWidget(view.asWidget());
		super.start(panel, eventBus, view);
	}

	@Override
	public void loadData() {

		if (ClientData.listUser == null) {
			getContactFromServer();
		}
		else {
			view.showContact();
		}
	}

	@Override
	protected void bind() {
		
		super.bind();
		addHandlerRegistration(view.getBtnAdd().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				gotoPage(new EditContactPlace());
			}
		}));

		addHandlerRegistration(eventBus.addHandler(ActionEvent.TYPE, new ActionEventHandler() {
			@Override
			public void onEvent(ActionEvent event) {

				if (event.getAction().equals(Action.DELETE)) {
					deleteContact(event.getUser());
				}
				if (event.getAction().equals(Action.EDIT)) {
					gotoPage(new EditContactPlace(event.getUser()));
				}

			}
		}));
		
		addHandlerRegistration(view.getSearchButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				callFilterService();
			}

		
		}));
		
	}
	
	private void callFilterService() {
		int selectedIndex = view.getSelectBox().getSelectedIndex();
        String selectedValue = view.getSelectBox().getValue(selectedIndex);
        String searchQuery = view.getSearchBox().getText();
//        greetingServiceAsync.findContact(selectedValue, searchQuery, new AsyncCallback<List<User>>() {
//			
//			@Override
//			public void onSuccess(List<User> result) {
//				view.showContact(result);
//			}
//			
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		
        
        ApiClient.filterContact(selectedValue, searchQuery, new AsyncCallback<List<User>>() {
	        @Override
	        public void onFailure(Throwable caught) {
	            ClientUtils.log("Error in getContact");
	        }

	        @Override
	        public void onSuccess(List<User> result) {
	            ClientUtils.log("filterContactSuccess");
	            view.showContact(result);
	        }
	    });
	}
	
	private void deleteContact(final User user) {

//		greetingServiceAsync.deleteContact(user, new AsyncCallback<Boolean>() {
//			@Override
//			public void onSuccess(Boolean result) {
//				if (result) {
//					ClientData.listUser.remove(user);
//					view.showContact();
//					Window.alert("Delete User: " + user.getName() + " successfully");
//
//				} else {
//					Window.alert("Cannot delete User : " + user.getName());
//				}
//
//			}
//
//			@Override
//			public void onFailure(Throwable caught) {
//				Window.alert("Error in delete contact");
//
//			}
//		});
		
		 ApiClient.deleteContact(user, new AsyncCallback<Boolean>() {
		        @Override
		        public void onSuccess(Boolean result) {
		            if (result) {
		                ClientData.listUser.remove(user);
		                view.showContact();
		                Window.alert("Delete User: " + user.getName() + " successfully");
		            } else {
		                Window.alert("Cannot delete User : " + user.getName());
		            }
		        }

		        @Override
		        public void onFailure(Throwable caught) {
		            Window.alert("Error in delete contact");
		        }
		 });
	}
	
	private void getContactFromServer() {
//		greetingServiceAsync.getContact(new AsyncCallback<List<User>>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//
//				ClientUtils.log("Error in getContact");
//			}
//
//			@Override
//			public void onSuccess(List<User> result) {
//				Window.alert("Call BE Service");
//				ClientData.listUser = result;
//				ClientUtils.log("getContactSuccess");
//				view.showContact();
//			}
//		});
		 ApiClient.getContactFromServer(new AsyncCallback<List<User>>() {
		        @Override
		        public void onFailure(Throwable caught) {
		            ClientUtils.log("Error in getContact");
		        }

		        @Override
		        public void onSuccess(List<User> result) {
		            ClientData.listUser = result;
		            ClientUtils.log("getContactSuccess");
		            view.showContact();
		        }
		    });
	}

}
