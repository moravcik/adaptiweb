package com.adaptiweb.gwt.framework.modify;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;

abstract class AbstractHasCahangeHandlersModifiedModel<T> extends AbstractModifiedModel<T> implements ChangeHandler {

	private HandlerRegistration handlerRegistration;
	
	public AbstractHasCahangeHandlersModifiedModel() {}
	
	public AbstractHasCahangeHandlersModifiedModel(HasChangeHandlers hasHandlers) {
		init(hasHandlers);
	}
	
	protected void init(HasChangeHandlers hasHandlers) {
		if (handlerRegistration != null) handlerRegistration.removeHandler();
		handlerRegistration = hasHandlers.addChangeHandler(this);
		burn();
	}
	
	@Override
	public void onChange(ChangeEvent event) {
		update();
	}
}
