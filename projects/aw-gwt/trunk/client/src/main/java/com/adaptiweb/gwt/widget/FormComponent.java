package com.adaptiweb.gwt.widget;

import com.adaptiweb.gwt.framework.GwtGoodies;
import com.adaptiweb.gwt.framework.HandlerRegistrations;
import com.adaptiweb.gwt.framework.style.DynamicStyle;
import com.adaptiweb.gwt.framework.validation.ValidationEvent;
import com.adaptiweb.gwt.framework.validation.ValidationHandler;
import com.adaptiweb.gwt.framework.validation.ValidationModel;
import com.adaptiweb.gwt.framework.validation.ValidationModelFactory;
import com.adaptiweb.gwt.framework.validation.ValidationModelSet;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

public class FormComponent extends Composite {

	private ValidationModelSet validation;
	protected final HandlerRegistrations registrations = new HandlerRegistrations();

	protected ValidationModelSet validations(ValidationModel...initModels) {
		if (validation == null) {
			validation = ValidationModelFactory.and(initModels);
		}
		if(initModels.length > 0) {
			validation.add(initModels);
		}
		return validation;
	}
	
	public ValidationModel getValidation() {
		return validation;
	}
	
	public ValidationModel addValidation(ValidationModel validation) {
		if (validations().add(validation)) return validation;
		else throw new IllegalStateException();
	}

	public boolean removeValidation(ValidationModel validation) {
		if (this.validation == null) return false;
		else return this.validation.remove(validation);
	}

	public HandlerRegistration setValudationStyle(final DynamicStyle style) {
		return registrations.add(validations().addValidationHandler(new ValidationHandler() {
			@Override
			public void onValidationChange(ValidationEvent event) {
				if (validation.isValid()) style.cancel(getElement());
				else style.apply(getElement());
			}
		}, true));
	}
	
	public void discard() {
		removeFromParent();
		registrations.discard();
	}

	public void enbaleValidationDebuging() {
		registrations.add(validation.addValidationHandler(new ValidationHandler() {
			@Override
			public void onValidationChange(ValidationEvent event) {
				GWT.log(GwtGoodies.toDebugString(validation), null);
			}
		}, true));
	}
}
