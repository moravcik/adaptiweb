package com.adaptiweb.gwt.framework.style;

import com.adaptiweb.gwt.framework.logic.LogicModel;
import com.adaptiweb.gwt.framework.logic.LogicValueChangeEvent;
import com.adaptiweb.gwt.framework.logic.LogicValueChangeHandler;
import com.google.gwt.user.client.Element;

public class ValidationStyleChanger implements LogicValueChangeHandler {
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
	public void onLogicValueChange(LogicValueChangeEvent event) {
		apply(event.getModel().getLogicValue()); 
	}
	
	public LogicModel register(LogicModel validation) {
		validation.addLogicValueChangeHandler(this, true);
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
