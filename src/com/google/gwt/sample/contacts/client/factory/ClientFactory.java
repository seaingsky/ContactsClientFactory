package com.google.gwt.sample.contacts.client.factory;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.sample.contacts.client.ContactsServiceAsync;
import com.google.gwt.sample.contacts.client.presenter.ContactsActivity.IContactsViewDisplay;
import com.google.gwt.sample.contacts.client.presenter.EditContactActivity.IEditDisplay;

public interface ClientFactory {

	// the eventbus and placecontrollers
	EventBus getEventBus();
    PlaceController getPlaceController();
    
    // the views
    IContactsViewDisplay getContactsView();
    IEditDisplay getEditContactView();
	
    // services
    ContactsServiceAsync getContactServiceRPC();
    
}
