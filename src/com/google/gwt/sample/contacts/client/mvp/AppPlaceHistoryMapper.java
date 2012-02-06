package com.google.gwt.sample.contacts.client.mvp;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.google.gwt.sample.contacts.client.place.ContactPlace;
import com.google.gwt.sample.contacts.client.place.EditContactPlace;
import com.google.gwt.sample.contacts.client.place.NewContactPlace;

@WithTokenizers({NewContactPlace.Tokenizer.class, EditContactPlace.Tokenizer.class,
				ContactPlace.Tokenizer.class})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
