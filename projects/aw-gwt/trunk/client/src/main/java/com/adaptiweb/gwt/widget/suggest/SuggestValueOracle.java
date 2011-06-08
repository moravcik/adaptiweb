package com.adaptiweb.gwt.widget.suggest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gwt.user.client.ui.SuggestOracle;

public abstract class SuggestValueOracle<T> extends SuggestOracle {

	protected Collection<Suggestion> convertToSuggestions(T[] values) {
		List<Suggestion> suggestions = new ArrayList<Suggestion>(values.length);
		for (T i : values) suggestions.add(convertToSuggestion(i));
		return suggestions;
	}

	protected abstract Suggestion convertToSuggestion(T value);
	
	protected abstract T convertToValue(Suggestion suggestion);

}
