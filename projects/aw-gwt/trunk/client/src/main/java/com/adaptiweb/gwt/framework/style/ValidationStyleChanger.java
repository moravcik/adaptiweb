package com.adaptiweb.gwt.framework.style;

import com.adaptiweb.gwt.framework.validation.ValidationEvent;
import com.adaptiweb.gwt.framework.validation.ValidationHandler;
import com.adaptiweb.gwt.framework.validation.ValidationModel;
import com.google.gwt.user.client.Element;

public class ValidationStyleChanger implements ValidationHandler {
	private final Element element;
	private final Style validStyle;
	private final Style invalidStyle;

	private ValidationStyleChanger(Element element, Style validStyle, Style invalidStyle) {
		this.element = element;
		this.validStyle = validStyle;
		this.invalidStyle = invalidStyle;
	}
	
	public static ValidationStyleChanger create(Element element, Style validStyle, Style invalidStyle) {
		return new ValidationStyleChanger(element, validStyle, invalidStyle);
	}

	@Override
	public void onValidationChange(ValidationEvent event) {
		apply(event.getModel().isValid()); 
	}
	
	public ValidationModel register(ValidationModel validation) {
		validation.addValidationHandler(this, true);
		return validation;
	}

	private void apply(boolean valid) {
		Style inactiveStyle = !valid ? validStyle : invalidStyle;
		if (inactiveStyle instanceof DynamicStyle)
			((DynamicStyle) inactiveStyle).cancel(element);
		
		Style activeStyle = valid ? validStyle : invalidStyle;
		if (activeStyle != null) activeStyle.apply(element); 
	}
}
