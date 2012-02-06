package com.google.gwt.sample.contacts.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.sample.contacts.client.ContactsServiceAsync;
import com.google.gwt.sample.contacts.client.event.AddContactEvent;
import com.google.gwt.sample.contacts.client.event.EditContactEvent;
import com.google.gwt.sample.contacts.client.factory.ClientFactory;
import com.google.gwt.sample.contacts.client.place.EditContactPlace;
import com.google.gwt.sample.contacts.client.place.NewContactPlace;
import com.google.gwt.sample.contacts.shared.ContactDetails;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Widget;

public class ContactsActivity extends AbstractActivity {

	private List<ContactDetails> contactDetails;

	private final ContactsServiceAsync rpcService;
	private final EventBus eventBus;
	private final IContactsViewDisplay display;

	private PlaceController placeController;

	public interface IContactsViewDisplay {
		HasClickHandlers getAddButton();

		HasClickHandlers getDeleteButton();

		HasClickHandlers getList();

		void setData(List<String> data);

		int getClickedRow(ClickEvent event);

		List<Integer> getSelectedRows();

		Widget asWidget();
	}

	public ContactsActivity(ClientFactory factory) {
		
		this.rpcService = factory.getContactServiceRPC();
		this.eventBus = factory.getEventBus();
		this.display = factory.getContactsView();
		this.placeController = factory.getPlaceController();
	}

	@Override
	public void start(AcceptsOneWidget container, EventBus eventBus) {

		bind();
		container.setWidget(display.asWidget());
		fetchContactDetails();

	}

	public void bind() {
		display.getAddButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
//				eventBus.fireEvent(new AddContactEvent());
				
				ContactsActivity.this.placeController.goTo(new NewContactPlace(""));
				
			}
		});

		display.getDeleteButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				deleteSelectedContacts();
			}
		});

		display.getList().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int selectedRow = display.getClickedRow(event);

				if (selectedRow >= 0) {
					String id = contactDetails.get(selectedRow).getId();
//					eventBus.fireEvent(new EditContactEvent(id));
					ContactsActivity.this.placeController.goTo(new EditContactPlace(id));
				}
			}
		});
	}

	public void sortContactDetails() {

		// Yes, we could use a more optimized method of sorting, but the
		// point is to create a test case that helps illustrate the higher
		// level concepts used when creating MVP-based applications.
		//
		for (int i = 0; i < contactDetails.size(); ++i) {
			for (int j = 0; j < contactDetails.size() - 1; ++j) {
				if (contactDetails.get(j).getDisplayName().compareToIgnoreCase(
						contactDetails.get(j + 1).getDisplayName()) >= 0) {
					ContactDetails tmp = contactDetails.get(j);
					contactDetails.set(j, contactDetails.get(j + 1));
					contactDetails.set(j + 1, tmp);
				}
			}
		}
	}

	public void setContactDetails(List<ContactDetails> contactDetails) {
		this.contactDetails = contactDetails;
	}

	public ContactDetails getContactDetail(int index) {
		return contactDetails.get(index);
	}

	private void fetchContactDetails() {
		rpcService
				.getContactDetails(new AsyncCallback<ArrayList<ContactDetails>>() {
					public void onSuccess(ArrayList<ContactDetails> result) {
						contactDetails = result;
						sortContactDetails();
						List<String> data = new ArrayList<String>();

						for (int i = 0; i < result.size(); ++i) {
							data.add(contactDetails.get(i).getDisplayName());
						}

						display.setData(data);
					}

					public void onFailure(Throwable caught) {
						Window.alert("Error fetching contact details");
					}
				});
	}

	private void deleteSelectedContacts() {
		List<Integer> selectedRows = display.getSelectedRows();
		ArrayList<String> ids = new ArrayList<String>();

		for (int i = 0; i < selectedRows.size(); ++i) {
			ids.add(contactDetails.get(selectedRows.get(i)).getId());
		}

		rpcService.deleteContacts(ids,
				new AsyncCallback<ArrayList<ContactDetails>>() {
					public void onSuccess(ArrayList<ContactDetails> result) {
						contactDetails = result;
						sortContactDetails();
						List<String> data = new ArrayList<String>();

						for (int i = 0; i < result.size(); ++i) {
							data.add(contactDetails.get(i).getDisplayName());
						}

						display.setData(data);

					}

					public void onFailure(Throwable caught) {
						Window.alert("Error deleting selected contacts");
					}
				});
	}

}
