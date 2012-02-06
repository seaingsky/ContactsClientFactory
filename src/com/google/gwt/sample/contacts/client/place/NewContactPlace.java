package com.google.gwt.sample.contacts.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class NewContactPlace extends Place {

	private String placeName;

	public NewContactPlace(String token) {
		this.placeName = token;
	}

	public String getPlaceName() {
		return placeName;
	}

	@Prefix("add")
	public static class Tokenizer implements PlaceTokenizer<NewContactPlace> {

		@Override
		public String getToken(NewContactPlace place) {
			return place.getPlaceName();
		}

		@Override
		public NewContactPlace getPlace(String token) {
			return new NewContactPlace(token);
		}

	}
}
