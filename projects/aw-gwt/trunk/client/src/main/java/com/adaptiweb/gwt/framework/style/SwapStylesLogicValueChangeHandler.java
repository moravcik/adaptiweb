package com.adaptiweb.gwt.framework.style;

import com.adaptiweb.gwt.framework.logic.LogicModel;
import com.adaptiweb.gwt.framework.logic.LogicValueChangeEvent;
import com.adaptiweb.gwt.framework.logic.LogicValueChangeHandler;
import com.google.gwt.user.client.Element;

public class SwapStylesLogicValueChangeHandler implements LogicValueChangeHandler {
	private final Element element;
	private final Style onStyle;
	private final Style offStyle;

	private SwapStylesLogicValueChangeHandler(Element element, Style onStyle, Style offStyle) {
		this.element = element;
		this.onStyle = onStyle;
		this.offStyle = offStyle;
	}
	
	public static SwapStylesLogicValueChangeHandler create(Element element, Style onStyle, Style offStyle) {
		return new SwapStylesLogicValueChangeHandler(element, onStyle, offStyle);
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
		Style inactiveStyle = !valid ? onStyle : offStyle;
		if (inactiveStyle instanceof DynamicStyle)
			((DynamicStyle) inactiveStyle).cancel(element);
		
		Style activeStyle = valid ? onStyle : offStyle;
		if (activeStyle != null) activeStyle.apply(element); 
	}
}
