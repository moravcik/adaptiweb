package com.adaptiweb.gwt.framework.validation;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.TextBoxBase;

public class EqualValidationModel extends AbstractValidationModel {
	
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

	public static ValidationModel equal(final TextBoxBase a, final TextBoxBase b) {
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
	
}
