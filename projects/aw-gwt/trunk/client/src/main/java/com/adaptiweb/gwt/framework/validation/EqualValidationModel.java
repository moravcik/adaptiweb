package com.adaptiweb.gwt.framework.validation;

import com.adaptiweb.gwt.framework.GwtGoodies;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.TextBoxBase;

public class EqualValidationModel extends AbstractValidationModel {
	
	public static final String DEFAULT_ERROR_MESSAGE = "Values '$0' and '$1' are not same!"; 
	
	private interface ObjectSource {
		Object getObject();
	}
	
	private static class TextBoxBaseSource implements ObjectSource {

		private TextBoxBase tb;

		public TextBoxBaseSource(TextBoxBase tb) {
			this.tb = tb;
		}
		
		@Override
		public Object getObject() {
			return tb.getText();
		}
	}
	
	private final ObjectSource a;
	private final ObjectSource b;
	private String errorMessage = DEFAULT_ERROR_MESSAGE;
	
	private EqualValidationModel(ObjectSource a, ObjectSource b) {
		this.a = a;
		this.b = b;
		validate();
	}
	
	protected void validate() {
		Object a = this.a.getObject();
		Object b = this.b.getObject();
		setValid(a == null ? b == null : a.equals(b));
	}

	public static ValidationModel create(final TextBoxBase a, final TextBoxBase b) {
		final EqualValidationModel validator = new EqualValidationModel(
				new TextBoxBaseSource(a),
				new TextBoxBaseSource(b));
		
		KeyUpHandler handler = new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				validator.validate();
			}
		};
		a.addKeyUpHandler(handler);
		b.addKeyUpHandler(handler);
		return validator;
	}
	
	@Override
	public String getErrorMessage() {
		return GwtGoodies.format(errorMessage, a.getObject(), b.getObject());
	}
	
	/**
	 * Default: {@value #DEFAULT_ERROR_MESSAGE}
	 * 
	 * @param errorMessage can has two parameters: <ul>
	 * <li>firstValue
	 * <li>secondValue
	 * </ul>
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
