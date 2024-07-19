package com.hello.client.activities.home;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.hello.client.activities.basic.BasicViewImpl;
import com.hello.shared.model.User;

public class EditContactViewImpl extends BasicViewImpl implements EditContactView {

	private static EditContactViewImplUiBinder uiBinder = GWT.create(EditContactViewImplUiBinder.class);

	interface EditContactViewImplUiBinder extends UiBinder<Widget, EditContactViewImpl> {
	}

	@UiField
	TextBox tbName, tbAddress, tbPhoneNumber, tbUsername;
	@UiField
	Button btnSave, btnBack;
	
	@UiField
	InputElement userId;

	public EditContactViewImpl() {
		this.layout.getContainerPanel().add(uiBinder.createAndBindUi(this));
		tbName.setPlaceholder("Nhập tên");
		tbAddress.setPlaceholder("Nhập địa chỉ");
		tbPhoneNumber.setPlaceholder("Nhập số điện thoại");
		tbUsername.setPlaceholder("Nhập tên đăng nhập");
		btnSave.getElement().setInnerHTML("Add Contact");
		btnBack.getElement().setInnerHTML("View Contact");
		btnBack.getElement().replaceClassName("btn-danger","btn-success");
		userId.setValue("new");
	}

	@Override
	public void showContact(User existedUser) {
		
		tbName.setText(existedUser.getName());
		tbAddress.setText(existedUser.getAddress());
		tbPhoneNumber.setText(existedUser.getPhoneNumber());
		tbUsername.setText(existedUser.getUsername());
		tbUsername.setEnabled(false);
		btnSave.getElement().setInnerHTML("Save");
		btnBack.getElement().setInnerHTML("Back");
		btnBack.getElement().replaceClassName("btn-success","btn-danger");
		
		userId.setValue(existedUser.getId().toString());
	}

	@Override
	public User getUser() {
		String name = tbName.getText().trim();
		String address = tbAddress.getText().trim();
		String phoneNumber = tbPhoneNumber.getText().trim();
		String username = tbUsername.getText().trim();
		Long id = userId.getValue()=="new"?null:Long.valueOf(userId.getValue());

		if (name.isEmpty()) {
			Window.alert("Vui lòng nhập tên");
			return null;
		}
		if (address.isEmpty()) {
			Window.alert("Vui lòng nhập địa chỉ");
			return null;
		}
		if (phoneNumber.isEmpty()) {
			Window.alert("Vui lòng nhập số điện thoại");
			return null;
		}
		if (username.isEmpty()) {
			Window.alert("Vui lòng nhập tên đăng nhập");
			return null;
		}
		return new User(id,username, name, address, phoneNumber);
	}

	@Override
	public Button getBtnSend() {
		return null;
	}

	@Override
	public Button getBtnSave() {
		return btnSave;
	}

	@Override
	public Button getBtnBack() {
		return btnBack;
	}

}
