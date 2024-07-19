package com.hello.client.activities.class_divide;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.hello.client.ClientUtils;
import com.hello.client.activities.ClientFactory;
import com.hello.client.activities.basic.BasicActivity;

public class ClassActivity extends BasicActivity {

	private ClassView view;
	
	public static List<ClassInfo> classInfos;
	
	public ClassActivity(ClientFactory clientFactory, Place place) {
		super(clientFactory, place);
	}

	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getClassView();
		ClientUtils.log("setWidget");
		panel.setWidget(view.asWidget());
		ClientUtils.log("startxxx");
		super.start(panel, eventBus, view);
		ClientUtils.log("start ok");
		
		
	}
	@Override
	public void loadData() {
		// Data fake
		// Call Api or do something else here
		ClassInfo classInfo1 = new ClassInfo("DH", "KHMT", "IT1", 300);
        ClassInfo classInfo2 = new ClassInfo("DH", "KTMT", "IT2", 200);
        ClassInfo classInfo3 = new ClassInfo("DH", "CNTT", "IT3", 200);
        ClassInfo classInfo4 = new ClassInfo("DH", "ICT", "ICT", 80);
        ClassInfo classInfo5 = new ClassInfo("CH", "TS", "TS.KHMT", 100);
        
        classInfos = Arrays.asList(classInfo1,classInfo2,classInfo3, classInfo4, classInfo5);
        
		view.showContact();
	}
	
	@Override
	protected void bind() {
		super.bind();
		
		
	}
}