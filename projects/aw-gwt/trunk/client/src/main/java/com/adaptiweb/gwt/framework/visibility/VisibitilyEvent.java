package com.adaptiweb.gwt.framework.visibility;

import com.google.gwt.event.shared.GwtEvent;

public class VisibitilyEvent extends GwtEvent<VisibilitityHandler> {

	private static Type<VisibilitityHandler> TYPE;

	public static void fire(VisibilityModel model) {
		if (TYPE != null) model.fireEvent(new VisibitilyEvent(model));
	}

	public static Type<VisibilitityHandler> getType() {
		if (TYPE == null) TYPE = new Type<VisibilitityHandler>();
		return TYPE;
	}

	private final boolean visible;
	private final VisibilityModel model;

	protected VisibitilyEvent(VisibilityModel model) {
		this.visible = model.isVisible();
		this.model = model;
	}

	@Override
	public final Type<VisibilitityHandler> getAssociatedType() {
		return TYPE;
	}

	public boolean isVisible() {
		return visible;
	}
	
	public VisibilityModel getModel() {
		return model;
	}

	@Override
	public String toDebugString() {
		return super.toDebugString()
			+ " visible=" + isVisible()
			+ ", model.visible=" + getModel().isVisible();
	}

	@Override
	protected void dispatch(VisibilitityHandler handler) {
		handler.onVisibilityChange(this);
	}
	
	public static void initDispatch(VisibilityModel model, VisibilitityHandler...handlers) {
		VisibitilyEvent initEvent = new VisibitilyEvent(model);
		for (VisibilitityHandler handler : handlers) {
			initEvent.dispatch(handler);
		}
	}
}
