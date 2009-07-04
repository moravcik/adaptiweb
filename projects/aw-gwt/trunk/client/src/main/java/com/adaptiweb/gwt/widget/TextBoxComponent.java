package com.adaptiweb.gwt.widget;

import com.adaptiweb.gwt.framework.GwtGoodies;
import com.adaptiweb.gwt.framework.validation.RegexValidationModel;
import com.adaptiweb.gwt.framework.validation.ValidationModel;
import com.adaptiweb.gwt.mvc.model.DefaultStringModel;
import com.adaptiweb.gwt.mvc.model.StringModel;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;

public class TextBoxComponent extends FormComponent implements StringModel {
	
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
}
