package com.adaptiweb.gwt.framework.logic;

import com.google.gwt.event.shared.GwtEvent;

public class LogicValueChangeEvent extends GwtEvent<LogicValueChangeHandler> {

	private static Type<LogicValueChangeHandler> TYPE;

	public static void fire(LogicModel model) {
		if (TYPE != null) model.fireEvent(new LogicValueChangeEvent(model));
	}

	public static void init(LogicModel model, LogicValueChangeHandler handler) {
		if (TYPE != null) new LogicValueChangeEvent(model).dispatch(handler);
	}

	public static Type<LogicValueChangeHandler> getType() {
		if (TYPE == null) TYPE = new Type<LogicValueChangeHandler>();
		return TYPE;
	}

	private final boolean logicValue;
	private final LogicModel model;

	protected LogicValueChangeEvent(LogicModel model) {
		this.logicValue = model.getLogicValue();
		this.model = model;
	}

	@Override
	public final Type<LogicValueChangeHandler> getAssociatedType() {
		return TYPE;
	}

	public boolean getLogicValue() {
		return logicValue;
	}
	
	public LogicModel getModel() {
		return model;
	}

	@Override
	public String toDebugString() {
		return super.toDebugString()
			+ " valid=" + getLogicValue()
			+ ", model.valid=" + getModel().getLogicValue();
	}

	@Override
	protected void dispatch(LogicValueChangeHandler handler) {
		handler.onLogicValueChange(this);
	}

}
