package com.google.gwt.sample.contacts.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.sample.contacts.client.factory.ClientFactory;
import com.google.gwt.sample.contacts.client.place.ContactPlace;
import com.google.gwt.sample.contacts.client.place.EditContactPlace;
import com.google.gwt.sample.contacts.client.place.NewContactPlace;
import com.google.gwt.sample.contacts.client.presenter.ContactsActivity;
import com.google.gwt.sample.contacts.client.presenter.EditContactActivity;

public class AppActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;
	
	public AppActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public Activity getActivity(Place place) {
		GWT.log("Place called: " + place);
		
		if(place instanceof ContactPlace) {
			return new ContactsActivity(clientFactory);
		} else if (place instanceof EditContactPlace) {
			return new EditContactActivity((EditContactPlace) place, clientFactory);
		} else if (place instanceof NewContactPlace) {
			return new EditContactActivity((NewContactPlace) place, clientFactory);
		}
		return null;
	}

}
