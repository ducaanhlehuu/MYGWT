package com.hello.client.activities.contact;

import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ListBox;
import org.gwtbootstrap3.client.ui.TextBox;

import com.google.gwt.user.client.ui.SuggestBox;
import com.hello.client.activities.basic.BasicView;
import com.hello.shared.model.User;

public interface ContactView extends BasicView {

	void showContact(List<User> user);

	void showContact();
	
	Button getBtnAdd();
	
	ListBox getSelectBox();
	
	Button getSearchButton();
	
	SuggestBox getSearchBox();
}
