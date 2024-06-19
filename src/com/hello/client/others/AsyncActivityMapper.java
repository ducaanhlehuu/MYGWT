package com.hello.client.others;

import com.google.gwt.place.shared.Place;

/**
*
* @author Saeed Zarinfam
*/
public interface AsyncActivityMapper{
	
	void getActivity(Place place, ActivityCallbackHandler activityCallbackHandler);

}
