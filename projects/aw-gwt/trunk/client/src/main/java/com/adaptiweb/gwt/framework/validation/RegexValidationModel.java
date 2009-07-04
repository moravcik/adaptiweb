package com.adaptiweb.gwt.framework.validation;

import com.adaptiweb.gwt.mvc.model.StringModel;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.TextBoxBase;

public class RegexValidationModel extends AbstractValidationModel {

	private final HandlerRegistration registration;

	public RegexValidationModel(final TextBoxBase textBox, final String regex) {
		registration = textBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				validate(textBox.getText(), regex);
			}
		});
		validate(textBox.getText(), regex);
	}
	
	public RegexValidationModel(final StringModel model, final String regex) {
		registration = model.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				validate(model.getText(), regex);
			}
		});
		validate(model.getText(), regex);
	}

	protected void validate(String text, String regex) {
		setValid(text.matches(regex));
	}
	
	public void discard() {
		registration.removeHandler();
	}
}
