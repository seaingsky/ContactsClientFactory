package com.google.gwt.sample.contacts.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ContactPlace extends Place {

	private String placeName;

	public ContactPlace(String token) {
		this.placeName = token;
	}

	public String getPlaceName() {
		return placeName;
	}

	@Prefix("list")
	public static class Tokenizer implements PlaceTokenizer<ContactPlace> {

		@Override
		public String getToken(ContactPlace place) {
			return place.getPlaceName();
		}

		@Override
		public ContactPlace getPlace(String token) {
			return new ContactPlace(token);
		}

	}
}
