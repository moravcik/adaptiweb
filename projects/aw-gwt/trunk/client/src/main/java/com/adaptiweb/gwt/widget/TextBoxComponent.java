package com.adaptiweb.gwt.widget;

import com.adaptiweb.gwt.framework.GwtGoodies;
import com.adaptiweb.gwt.framework.validation.RegexValidationModel;
import com.adaptiweb.gwt.framework.validation.ValidationModel;
import com.adaptiweb.gwt.mvc.model.DefaultStringModel;
import com.adaptiweb.gwt.mvc.model.StringModel;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;

public class TextBoxComponent extends FormComponent implements StringModel, HasKeyDownHandlers, HasKeyUpHandlers, HasBlurHandlers, HasFocusHandlers {
	
	protected final TextBoxBase textBox = new TextBox();
	protected final StringModel model = new DefaultStringModel();
	
	public TextBoxComponent() {
		initWidget(textBox);
		registrations.add(textBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				model.setText(textBox.getValue());
			}
		}));
		registrations.add(model.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				if (!GwtGoodies.areEquals(textBox.getValue(), model.getText()))
					textBox.setValue(model.getText());
			}
		}));
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return model.addValueChangeHandler(handler);
	}

	@Override
	public String getText() {
		return model.getText();
	}

	@Override
	public void setText(String text) {
		model.setText(text);
	}
	
	public ValidationModel setRegexValidation(String regex) {
		return addValidation(new RegexValidationModel(this, regex));
	}

	@Override
	public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
		return textBox.addKeyUpHandler(handler);
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return textBox.addBlurHandler(handler);
	}

	public void focus() {
		textBox.setFocus(true);
	}

	@Override
	public HandlerRegistration addKeyDownHandler(KeyDownHandler handler) {
		return textBox.addKeyDownHandler(handler);
	}

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return textBox.addFocusHandler(handler);
	}

	@Override
	public String getValue() {
		return model.getValue();
	}

	@Override
	public void setValue(String value) {
		model.setValue(value);
	}

	@Override
	public void setValue(String value, boolean fireEvent) {
		model.setValue(value, fireEvent);
	}

	public void setReadOnly(boolean readOnly) {
		textBox.setReadOnly(readOnly);
	}	
}
