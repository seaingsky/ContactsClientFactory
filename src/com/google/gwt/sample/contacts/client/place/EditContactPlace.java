package com.google.gwt.sample.contacts.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class EditContactPlace extends Place {

	private String placeName;

	public EditContactPlace(String token) {
		this.placeName = token;
	}

	public String getPlaceName() {
		return placeName;
	}

	@Prefix("edit")
	public static class Tokenizer implements PlaceTokenizer<EditContactPlace> {

		@Override
		public String getToken(EditContactPlace place) {
			return place.getPlaceName();
		}

		@Override
		public EditContactPlace getPlace(String token) {
			return new EditContactPlace(token);
		}

	}
}
