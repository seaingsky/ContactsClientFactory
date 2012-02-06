package com.google.gwt.sample.contacts.client.presenter;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.sample.contacts.client.ContactsServiceAsync;
import com.google.gwt.sample.contacts.client.factory.ClientFactory;
import com.google.gwt.sample.contacts.client.place.ContactPlace;
import com.google.gwt.sample.contacts.client.place.EditContactPlace;
import com.google.gwt.sample.contacts.client.place.NewContactPlace;
import com.google.gwt.sample.contacts.shared.Contact;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

public class EditContactActivity extends AbstractActivity {

	public interface IEditDisplay {
		HasClickHandlers getSaveButton();

		HasClickHandlers getCancelButton();

		HasValue<String> getFirstName();

		HasValue<String> getLastName();

		HasValue<String> getEmailAddress();

		Widget asWidget();
	}

	private Contact contact;
	private final ContactsServiceAsync rpcService;
	private final EventBus eventBus;
	private final IEditDisplay display;
	private final PlaceController placeController;

	public EditContactActivity(NewContactPlace place, ClientFactory clientFactory) {
		this.rpcService = clientFactory.getContactServiceRPC();
		this.eventBus = clientFactory.getEventBus();
		this.contact = new Contact();
		this.display = clientFactory.getEditContactView();
		this.placeController = clientFactory.getPlaceController();
		bind();
	}
	
//	public EditContactActivity(ContactsServiceAsync rpcService,
//			EventBus eventBus, IEditDisplay display) {
//		this.rpcService = rpcService;
//		this.eventBus = eventBus;
//		this.contact = new Contact();
//		this.display = display;
//		bind();
//	}

	public EditContactActivity(EditContactPlace place, ClientFactory clientFactory) {
		this.rpcService = clientFactory.getContactServiceRPC();
		this.eventBus = clientFactory.getEventBus();
		this.display = clientFactory.getEditContactView();
		this.placeController = clientFactory.getPlaceController();
		String id = place.getPlaceName();
		
		bind();
		
		rpcService.getContact(id, new AsyncCallback<Contact>() {
			public void onSuccess(Contact result) {
				contact = result;
				EditContactActivity.this.display.getFirstName().setValue(
						contact.getFirstName());
				EditContactActivity.this.display.getLastName().setValue(
						contact.getLastName());
				EditContactActivity.this.display.getEmailAddress().setValue(
						contact.getEmailAddress());
			}

			public void onFailure(Throwable caught) {
				Window.alert("Error retrieving contact");
			}
		});
	}
	
//	public EditContactActivity(ContactsServiceAsync rpcService,
//			EventBus eventBus, IEditDisplay display, String id) {
//		this.rpcService = rpcService;
//		this.eventBus = eventBus;
//		this.display = display;
//		bind();
//
//		rpcService.getContact(id, new AsyncCallback<Contact>() {
//			public void onSuccess(Contact result) {
//				contact = result;
//				EditContactActivity.this.display.getFirstName().setValue(
//						contact.getFirstName());
//				EditContactActivity.this.display.getLastName().setValue(
//						contact.getLastName());
//				EditContactActivity.this.display.getEmailAddress().setValue(
//						contact.getEmailAddress());
//			}
//
//			public void onFailure(Throwable caught) {
//				Window.alert("Error retrieving contact");
//			}
//		});
//
//	}

	public void bind() {
		this.display.getSaveButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doSave();
			}
		});

		this.display.getCancelButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
//				eventBus.fireEvent(new EditContactCancelledEvent());
				EditContactActivity.this.placeController.goTo(new ContactPlace(""));
			}
		});
	}

	@Override
	public void start(AcceptsOneWidget container, EventBus eventBus) {
		container.setWidget(display.asWidget());
	}

	private void doSave() {
		contact.setFirstName(display.getFirstName().getValue());
		contact.setLastName(display.getLastName().getValue());
		contact.setEmailAddress(display.getEmailAddress().getValue());

		rpcService.updateContact(contact, new AsyncCallback<Contact>() {
			public void onSuccess(Contact result) {
//				eventBus.fireEvent(new ContactUpdatedEvent(result));
				EditContactActivity.this.placeController.goTo(new ContactPlace(""));
			}

			public void onFailure(Throwable caught) {
				Window.alert("Error updating contact");
			}
		});
	}

}
