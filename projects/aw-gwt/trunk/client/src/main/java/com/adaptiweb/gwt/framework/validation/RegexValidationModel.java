package com.adaptiweb.gwt.framework.validation;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.TextBoxBase;

public class RegexValidationModel extends AbstractValidationModel {

	public RegexValidationModel(final TextBoxBase textBox, final String regex) {
		textBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				validate(textBox.getText(), regex);
			}
		});
		validate(textBox.getText(), regex);
	}
	
	protected void validate(String text, String regex) {
		setValid(text.matches(regex));
	}
}
