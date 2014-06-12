package com.adaptiweb.gwt.widget.suggest;

import com.adaptiweb.gwt.framework.logic.LogicModel;
import com.adaptiweb.gwt.framework.validation.NotNullValidationModel;
import com.adaptiweb.gwt.mvc.model.DefaultValueChangeModel;
import com.adaptiweb.gwt.mvc.model.ValueChangeModel;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class SuggestValueBox<T> extends Composite implements HasValue<T>, HasValueChangeHandlers<T>, IsEditor<LeafValueEditor<T>> {
	
	private final ValueChangeModel<T> model = new DefaultValueChangeModel<T>();
	private LeafValueEditor<T> editor;
	private final SuggestBox box;

	public SuggestValueBox(SuggestValueOracle<T> oracle) {
		box = new SuggestBox(oracle);
		initWidget(box);
		// init
		box.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				Suggestion s = getSuggestOracle().convertToSuggestion(model.getValue());
				boolean isEqual = s != null && s.getReplacementString().equals(event.getValue());
				if (!isEqual) model.setValue(null, true); // reset
			}
		});
		box.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				model.setValue(getSuggestOracle().convertToValue(event.getSelectedItem()));
			}
		});
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
		return model.addValueChangeHandler(handler);
	}

	@Override
	public T getValue() {
		return model.getValue();
	}

	@Override
	public void setValue(T value) {
		model.setValue(value);
		Suggestion suggestion = getSuggestOracle().convertToSuggestion(value);
		box.setValue(suggestion != null ? suggestion.getReplacementString() : "");
	}

	@Override
	public void setValue(T value, boolean fireEvents) {
		model.setValue(value, fireEvents);
		box.setValue(getSuggestOracle().convertToSuggestion(value).getReplacementString(), fireEvents);
	}
	
	public void setEnabled(boolean enabled) {
		box.setEnabled(enabled);
	}

	@SuppressWarnings("unchecked")
	private SuggestValueOracle<T> getSuggestOracle() {
		return (SuggestValueOracle<T>) box.getSuggestOracle();
	}
	
	public LogicModel createNotNullValidation() {
		return NotNullValidationModel.create(model);
	}
	
	@Override
	public LeafValueEditor<T> asEditor() {
		if (editor == null) editor = TakesValueEditor.of(this);
	    return editor;
	}
		
}
