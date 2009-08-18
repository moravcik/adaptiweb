package com.adaptiweb.gwt.framework.modify;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

abstract class AbstractHasValueCahangeHandlersModifiedModel<T> extends AbstractModifiedModel<T> implements ValueChangeHandler<T> {

	private HandlerRegistration handlerRegistration;
	
	public AbstractHasValueCahangeHandlersModifiedModel() {}
	
	public AbstractHasValueCahangeHandlersModifiedModel(HasValueChangeHandlers<T> hasHandlers) {
		init(hasHandlers);
	}
	
	protected void init(HasValueChangeHandlers<T> hasHandlers) {
		if (handlerRegistration != null) handlerRegistration.removeHandler();
		handlerRegistration = hasHandlers.addValueChangeHandler(this);
		burn();
	}
	
	@Override
	public void onValueChange(ValueChangeEvent<T> event) {
		update(getCurrentValue());
	}

}
