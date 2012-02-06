package com.google.gwt.sample.contacts.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.sample.contacts.client.factory.ClientFactory;
import com.google.gwt.sample.contacts.client.mvp.AppActivityMapper;
import com.google.gwt.sample.contacts.client.mvp.AppPlaceHistoryMapper;
import com.google.gwt.sample.contacts.client.place.ContactPlace;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class Contacts implements EntryPoint {

	private Place defaultPlace = new ContactPlace("");
	
	private SimplePanel appWidget = new SimplePanel();
	
	public void onModuleLoad() {
//		ContactsServiceAsync rpcService = GWT.create(ContactsService.class);
//		EventBus eventBus = new SimpleEventBus();
//		AppController appViewer = new AppController(rpcService, eventBus);
//		appViewer.go(RootPanel.get());
		
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();
		
		// start activity manager for our main widget with our ActivityMapper
		ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
		activityManager.setDisplay(appWidget);
		
		// start PlaceHistoryHandler with our PlaceHistoryMapper
		AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeController, eventBus, defaultPlace);
		
		RootPanel.get().add(appWidget);
		
		historyHandler.handleCurrentHistory();
		
	}
}
