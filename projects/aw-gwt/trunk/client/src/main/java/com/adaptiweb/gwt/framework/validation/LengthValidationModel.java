package com.adaptiweb.gwt.framework.validation;

import com.adaptiweb.gwt.framework.GwtGoodies;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBoxBase;

public class LengthValidationModel extends AbstractValidationModel {
	
	public static final String DEFAULT_ERROR_MESSAGE_TOO_SHORT = "Length of '$0' must be greater than $1! (actual is $2)";
	public static final String DEFAULT_ERROR_MESSAGE_TOO_LONG = "Length of '$0' must be less than $1! (actual is $2)";

	private interface LengthSource {
		int getLength();
		Object getSourceValue();
	}
	
	private final LengthSource source;
	private String tooLongMessage = DEFAULT_ERROR_MESSAGE_TOO_LONG;
	private String tooShortMessage = DEFAULT_ERROR_MESSAGE_TOO_SHORT;
	private int minLength;
	private int maxLength;

	public LengthValidationModel(LengthSource source, int minLength, int maxLength) {
		this.source = source;
		this.minLength = minLength;
		this.maxLength = maxLength;
		validate();
	}
	
	public static LengthValidationModel checkMin(int minLength, TextBoxBase textBox) {
		return check(minLength, -1, textBox);
	}

	public static LengthValidationModel checkMax(int maxLength, TextBoxBase textBox) {
		return check(-1, maxLength, textBox);
	}
	
	public static LengthValidationModel check(int minLength, int maxLength, final TextBoxBase textBox) {
		
		final LengthValidationModel validation = new LengthValidationModel(new LengthSource() {
			@Override
			public int getLength() {
				return textBox.getText().length();
			}

			@Override
			public Object getSourceValue() {
				return textBox.getText();
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

	public static LengthValidationModel checkMin(int minLength, SuggestBox suggestBox) {
		return check(minLength, -1, suggestBox);
	}

	public static LengthValidationModel checkMax(int maxLength, SuggestBox suggestBox) {
		return check(-1, maxLength, suggestBox);
	}

	public static LengthValidationModel check(int minLength, int maxLength, final SuggestBox suggestBox) {
		
		final LengthValidationModel validation = new LengthValidationModel(new LengthSource() {
			@Override
			public int getLength() {
				return suggestBox.getText().length();
			}

			@Override
			public Object getSourceValue() {
				return suggestBox.getText();
			}
		}, minLength, maxLength);
		
		suggestBox.addKeyUpHandler(new KeyUpHandler() {
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
	
	@Override
	public String getErrorMessage() {
		int length = source.getLength();
		if (length < minLength) return GwtGoodies.format(tooShortMessage, source.getSourceValue(), minLength, length);
		if (length > maxLength) return GwtGoodies.format(tooLongMessage, source.getSourceValue(), maxLength, length);
		return null;
	}
	
	/**
	 * Default: {@value #DEFAULT_ERROR_MESSAGE_TOO_LONG}
	 * @param tooLongMessage can has three parameters:<ul>
	 * <li>value
	 * <li>maxLength
	 * <li>actualLength
	 * </ul>
	 */
	public void setTooLongMessage(String tooLongMessage) {
		this.tooLongMessage = tooLongMessage;
	}
	
	/**
	 * Default: {@value #DEFAULT_ERROR_MESSAGE_TOO_SHORT}
	 * @param tooLongMessage can has three parameters:<ul>
	 * <li>value
	 * <li>maxLength
	 * <li>actualLength
	 * </ul>
	 */
	public void setTooShortMessage(String tooShortMessage) {
		this.tooShortMessage = tooShortMessage;
	}
}
