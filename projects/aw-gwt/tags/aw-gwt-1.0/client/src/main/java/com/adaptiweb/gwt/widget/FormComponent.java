package com.adaptiweb.gwt.widget;

import com.adaptiweb.gwt.framework.GwtGoodies;
import com.adaptiweb.gwt.framework.HandlerRegistrations;
import com.adaptiweb.gwt.framework.logic.LogicModel;
import com.adaptiweb.gwt.framework.logic.LogicModelFactory;
import com.adaptiweb.gwt.framework.logic.LogicModelSet;
import com.adaptiweb.gwt.framework.logic.LogicValueChangeEvent;
import com.adaptiweb.gwt.framework.logic.LogicValueChangeHandler;
import com.adaptiweb.gwt.framework.style.DynamicStyle;
import com.adaptiweb.gwt.framework.validation.ValidationModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

public class FormComponent extends Composite {

	private LogicModelSet validation;
	protected final HandlerRegistrations registrations = new HandlerRegistrations();

	protected LogicModelSet validations(ValidationModel...models) {
		if (validation == null) {
			validation = LogicModelFactory.and(models);
		}
		if(models.length > 0) {
			for (ValidationModel model : models)
				validation.add(model);
		}
		return validation;
	}
	
	public LogicModel getValidation() {
		return validation;
	}
	
	public ValidationModel addValidation(ValidationModel validation) {
		if (validations().contains(validation)) throw new IllegalStateException();
		validations().add(validation);
		return validation;
	}

	public void removeValidation(ValidationModel validation) {
		if (this.validation != null) this.validation.remove(validation);
	}

	public HandlerRegistration setValudationStyle(final DynamicStyle style) {
		return registrations.add(validations().addLogicValueChangeHandler(new LogicValueChangeHandler() {
			@Override
			public void onLogicValueChange(LogicValueChangeEvent event) {
				if (validation.getLogicValue()) style.cancel(getElement());
				else style.apply(getElement());
			}
		}, true));
	}
	
	public void discard() {
		removeFromParent();
		registrations.discard();
	}

	public void enbaleValidationDebuging() {
		registrations.add(validation.addLogicValueChangeHandler(new LogicValueChangeHandler() {
			@Override
			public void onLogicValueChange(LogicValueChangeEvent event) {
				GWT.log(GwtGoodies.toDebugString(validation), null);
			}
		}, true));
	}
}
