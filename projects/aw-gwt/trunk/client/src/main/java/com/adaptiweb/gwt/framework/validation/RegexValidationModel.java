package com.adaptiweb.gwt.framework.validation;

import com.adaptiweb.gwt.framework.GwtGoodies;
import com.adaptiweb.gwt.mvc.model.StringModel;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.TextBoxBase;

public class RegexValidationModel extends AbstractValidationModel {

	private static final String DEFAULT_ERROR_MESSAGE = "Value '$0' doesn't match pattern /$1/!";
	
	private final HandlerRegistration registration;
	private String errorMessage = DEFAULT_ERROR_MESSAGE;
	private String lastTestedValue;
	private final String regex;
	
	public RegexValidationModel(final TextBoxBase textBox, final String regex) {
		this.regex = regex;
		registration = textBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				validate(textBox.getText());
			}
		});
		validate(textBox.getText());
	}
	
	public RegexValidationModel(final StringModel model, final String regex) {
		this.regex = regex;
		registration = model.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				validate(model.getText());
			}
		});
		validate(model.getText());
	}

	protected void validate(String text) {
		lastTestedValue = text;
		setValid(text.matches(regex));
	}
	
	public void discard() {
		registration.removeHandler();
	}
	
	@Override
	public String getErrorMessage() {
		return GwtGoodies.format(errorMessage, lastTestedValue, regex);
	}
	
	/**
	 * Defualt: {@value #DEFAULT_ERROR_MESSAGE}
	 * @param errorMessage can have two parameters:<ul>
	 * <li>stringValue
	 * <li>pattern
	 * </ul>
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
