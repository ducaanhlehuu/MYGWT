package com.hello.client.activities.contact;

import org.gwtbootstrap3.client.ui.Button;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.hello.client.AppManager;
import com.hello.client.activities.basic.BasicViewImpl;
import com.hello.client.events.ActionEvent;
import com.hello.client.events.ActionEvent.Action;
import com.hello.shared.model.User;

public class ContactViewImpl extends BasicViewImpl implements ContactView {

	private static ContactViewImplUiBinder uiBinder = GWT.create(ContactViewImplUiBinder.class);

	interface ContactViewImplUiBinder extends UiBinder<Widget, ContactViewImpl> {
	}

	@UiField HTML htmlName, htmlAddress;
	@UiField Button btnBack, btnEvent;
	
	public ContactViewImpl() {
		this.layout.getContainerPanel().add((uiBinder.createAndBindUi(this)));
		btnEvent.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				AppManager.CLIENT_FACTORY.getEventBus().fireEvent(new ActionEvent(Action.CLICK, new User("123", "456")));
			}
		});
	}
	
	@Override
	public void showContact(User user) {
		if(user != null) {
			htmlName.setText("Name : " + user.getName());
			htmlAddress.setText("Address : " + user.getAddress());
		}
	}
	
	@Override
	public Button getBtnEvent() {
		return btnEvent;
	}
	
	@Override
	public Button getBtnBack() {
		return btnBack;
	}

}
