package com.hello.client.activities.home;

import org.gwtbootstrap3.client.ui.Button;

import com.hello.client.activities.basic.BasicView;
import com.hello.shared.model.User;

public interface EditContactView extends BasicView {
	void showContact(User existedUser);

	Button getBtnSend();

	User getUser();
	
	Button getBtnSave();
	
	Button getBtnBack();

}
