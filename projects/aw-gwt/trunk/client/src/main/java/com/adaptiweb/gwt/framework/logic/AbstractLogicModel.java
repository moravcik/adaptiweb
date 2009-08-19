package com.adaptiweb.gwt.framework.logic;

import com.adaptiweb.gwt.framework.AbstractHasHandlers;
import com.adaptiweb.gwt.framework.GwtGoodies;
import com.adaptiweb.gwt.framework.HasDebugInfo;
import com.google.gwt.event.shared.HandlerRegistration;

public abstract class AbstractLogicModel extends AbstractHasHandlers implements LogicModel, HasDebugInfo {
	
	private boolean logicValue;
	
	protected AbstractLogicModel() {
		this(null, false);
	}
	
	protected AbstractLogicModel(boolean initialLogicValue) {
		this(null, initialLogicValue);
	}

	protected AbstractLogicModel(Object source) {
		this(source, false);
	}
	
	protected AbstractLogicModel(Object source, boolean initialLogicValue) {
		super(source);
		logicValue = initialLogicValue;
	}
	
	protected void setLogicValue(boolean value) {
		if (this.logicValue == value) return;
		this.logicValue = value;
		fireValueChangeEvent();
	}

	protected void fireValueChangeEvent() {
		LogicValueChangeEvent.fire(this);
	}
	
	@Override
	public boolean getLogicValue() {
		return logicValue;
	}
	
	@Override
	public HandlerRegistration addLogicValueChangeHandler(LogicValueChangeHandler handler, boolean fireInitEvent) {
		if(fireInitEvent) LogicValueChangeEvent.init(this, handler);
		return handlers.addHandler(LogicValueChangeEvent.getType(), handler);
	}

	@Override
	public String toDebugString() {
		return toDebugString(GwtGoodies.simpleClassName(getClass().getName()));
	}

	protected String toDebugString(String type) {
		return type + "=" + getLogicValue();
	}
}
