package com.adaptiweb.gwt.framework.changing;

import com.google.gwt.event.shared.GwtEvent;

public class ChangingEvent extends GwtEvent<ChangingHandler> {

	private static Type<ChangingHandler> TYPE;

	public static void fire(ChangingModel model) {
		if (TYPE != null) model.fireEvent(new ChangingEvent(model));
	}

	public static void init(ChangingModel model, ChangingHandler handler) {
		if (TYPE != null) new ChangingEvent(model).dispatch(handler);
	}

	public static Type<ChangingHandler> getType() {
		if (TYPE == null) TYPE = new Type<ChangingHandler>();
		return TYPE;
	}

	private final ChangingModel model;

	protected ChangingEvent(ChangingModel model) {
		this.model = model;
	}

	@Override
	public final Type<ChangingHandler> getAssociatedType() {
		return TYPE;
	}

	public boolean isChanged() {
		return model.isChanged();
	}

	@Override
	public String toDebugString() {
		return super.toDebugString() + " changed=" + isChanged();
	}

	@Override
	protected void dispatch(ChangingHandler handler) {
		handler.onChanging(this);
	}
}
