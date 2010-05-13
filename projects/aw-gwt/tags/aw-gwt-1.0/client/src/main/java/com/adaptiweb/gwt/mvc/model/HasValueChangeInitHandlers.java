package com.adaptiweb.gwt.mvc.model;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public interface HasValueChangeInitHandlers<I> extends HasValueChangeHandlers<I> {
  /**
   * Adds a {@link ValueChangeEvent} handler and immediately calls handler.
   * 
   * @param handler the handler
   * @return the registration for the event
   */
  HandlerRegistration addValueChangeHandlerAndInit(ValueChangeHandler<I> handler);
}
