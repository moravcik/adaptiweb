package com.adaptiweb.gwt.framework.validation;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.TextBoxBase;

public class LengthValidationModel extends AbstractValidationModel {
	
	private interface LengthSource {
		int getLength();
	}
	
	private final LengthSource source;
	private int minLength = -1;
	private int maxLength = -1;

	public LengthValidationModel(LengthSource source, int minLength, int maxLength) {
		this.source = source;
		this.minLength = minLength;
		this.maxLength = maxLength;
		validate();
	}
	
	public static LengthValidationModel checkMin(int minLength, TextBoxBase textBox) {
		return check(textBox, minLength, -1);
	}

	public static LengthValidationModel checkMax(int maxLength, TextBoxBase textBox) {
		return check(textBox, -1, maxLength);
	}
	
	public static LengthValidationModel check(final TextBoxBase textBox, int minLength, int maxLength) {
		
		final LengthValidationModel validation = new LengthValidationModel(new LengthSource() {
			@Override
			public int getLength() {
				return textBox.getText().length();
			}
		}, minLength, maxLength);
		
		textBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				validation.validate();
			}
		});
		
		return validation;
	}
	
	protected void validate() {
		int length = source.getLength();
		setValid((minLength == -1 || length >= minLength) && (maxLength == -1 || length <= maxLength));
	}
	
	public void setMinLength(int minLength) {
		if (this.minLength == minLength) return;
		this.minLength = minLength;
		validate();
	}
	
	public void setMaxLength(int maxLength) {
		if (this.maxLength == maxLength) return;
		this.maxLength = maxLength;
		validate();
	}
}
