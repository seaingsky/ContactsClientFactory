package com.google.gwt.sample.contacts.client.factory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.sample.contacts.client.ContactsService;
import com.google.gwt.sample.contacts.client.ContactsServiceAsync;
import com.google.gwt.sample.contacts.client.presenter.ContactsActivity.IContactsViewDisplay;
import com.google.gwt.sample.contacts.client.presenter.EditContactActivity.IEditDisplay;
import com.google.gwt.sample.contacts.client.view.ContactsView;
import com.google.gwt.sample.contacts.client.view.EditContactView;

public class ClientFactoryImpl implements ClientFactory {

	private final EventBus eventBus = new SimpleEventBus();
	private final PlaceController placeController = new PlaceController(eventBus);
	private final IEditDisplay editDisplay = new EditContactView();
	private final IContactsViewDisplay contactsView = new ContactsView();
	private final ContactsServiceAsync contactsRpcService = GWT.create(ContactsService.class);
	
	@Override
	public IContactsViewDisplay getContactsView() {
		return contactsView;
	}

	@Override
	public IEditDisplay getEditContactView() {
		return editDisplay;
	}

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public ContactsServiceAsync getContactServiceRPC() {
		return contactsRpcService;
	}

}
