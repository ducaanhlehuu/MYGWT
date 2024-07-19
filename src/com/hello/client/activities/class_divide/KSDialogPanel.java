/**
 * 
 */
package com.hello.client.activities.class_divide;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalFooter;
import org.gwtbootstrap3.client.ui.ModalHeader;
import org.gwtbootstrap3.client.ui.constants.ModalBackdrop;
import org.gwtbootstrap3.extras.bootbox.client.callback.ConfirmCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author hungzombxd
 *
 */
public class KSDialogPanel {

	private static KSModalUiBinder uiBinder = GWT.create(KSModalUiBinder.class);
	private boolean isShowing = false;

	interface KSModalUiBinder extends UiBinder<Widget, KSDialogPanel> {
	}

	/**
	 * Because this class has a default constructor, it can
	 * be used as a binder template. In other words, it can be used in other
	 * *.ui.xml files as follows:
	 * <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	  *   xmlns:g="urn:import:**user's package**">
	 *  <g:**UserClassName**>Hello!</g:**UserClassName>
	 * </ui:UiBinder>
	 * Note that depending on the widget that is used, it may be necessary to
	 * implement HasHTML instead of HasText.
	 */
	@UiField protected Modal modal;
	@UiField protected ModalHeader modalHeaderPanel;
	@UiField protected ModalFooter modalFooterPanel;
	@UiField protected HTML modalHeaderHTML;
	@UiField protected FlowPanel modalContentPanel;
	@UiField protected Button confirmButton;
	@UiField protected Button closeButton;
	private ConfirmCallback confirmCallback;
	
	public KSDialogPanel() {
		uiBinder.createAndBindUi(this);
		//this.modal.getElement().getStyle().setZIndex(Config.ZINDEX_LEVEL1);
		confirmButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onConfirm();
			}
		});
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onCancel();
			}
		});
	}
	
	protected void onCancel() {
		if(confirmCallback != null)
			confirmCallback.callback(false);
		confirmButton.setEnabled(false);
		closeButton.setEnabled(false);
		hide();
	}

	protected void onConfirm() {
		if(confirmCallback != null)
			confirmCallback.callback(true);
		confirmButton.setEnabled(false);
		closeButton.setEnabled(false);
		hide();
	}

	public KSDialogPanel(String title, Widget contentWidget, String okText, String cancelText, ConfirmCallback callback) {
		this();
		this.confirmCallback = callback;
		updateInfo(title, contentWidget, okText, cancelText);
	}
	
	public void updateInfo(String title, Widget contentWidget, String okText, String cancelText) {
		modalHeaderHTML.setHTML(title);
		modalContentPanel.clear();
		if(contentWidget!= null)
			modalContentPanel.add(contentWidget);
		if(okText == null || okText.isEmpty()) {
			confirmButton.removeFromParent();
		} else
			confirmButton.setText(okText);
		if(cancelText == null || cancelText.isEmpty()) {
			closeButton.removeFromParent();
		} else
			closeButton.setText(cancelText);
	}
	
	public void show(String title, Widget contentWidget, String okText, String cancelText, ConfirmCallback callback) {
		this.confirmCallback = callback;
		updateInfo(title, contentWidget, okText, cancelText);
		show();
	}
	
	public void show() {
		modal.show();
		confirmButton.setEnabled(true);
		closeButton.setEnabled(true);
		isShowing = true;
	}
	
	public void hide() {
		modal.hide();
		isShowing = false;
	}
	
	public void setEnableHideOverClick(boolean enableHideClick) {
		modal.setClosable(enableHideClick);
		modal.setDataBackdrop(enableHideClick ? ModalBackdrop.TRUE : ModalBackdrop.STATIC);
	}
	
	public FlowPanel getContentPanel() {
		return modalContentPanel;
	}
	
	public ModalFooter getFooter() {
		return modalFooterPanel;
	}

	public boolean isShowing() {
		return isShowing;
	}
	
	public Modal getModal() {
		return modal;
	}
}
