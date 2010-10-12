package com.adaptiweb.gwt.framework.modify;

import com.google.gwt.event.shared.GwtEvent;

public class ModifiedEvent extends GwtEvent<ModifiedHandler> {

	private static Type<ModifiedHandler> TYPE;

	public static void fire(ModifiedModel model) {
		if (TYPE != null) model.fireEvent(new ModifiedEvent(model));
	}

	public static void init(ModifiedModel model, ModifiedHandler handler) {
		if (TYPE != null) new ModifiedEvent(model).dispatch(handler);
	}

	public static Type<ModifiedHandler> getType() {
		if (TYPE == null) TYPE = new Type<ModifiedHandler>();
		return TYPE;
	}

	private final boolean modified;
	private final ModifiedModel model;

	protected ModifiedEvent(ModifiedModel model) {
		this.model = model;
		this.modified = model.isModified();
	}

	@Override
	public final Type<ModifiedHandler> getAssociatedType() {
		return TYPE;
	}

	public boolean isModified() {
		return modified;
	}
	
	public ModifiedModel getModel() {
		return model;
	}

	@Override
	public String toDebugString() {
		return super.toDebugString() + " modified=" + isModified();
	}

	@Override
	protected void dispatch(ModifiedHandler handler) {
		handler.onModify(this);
	}
}
