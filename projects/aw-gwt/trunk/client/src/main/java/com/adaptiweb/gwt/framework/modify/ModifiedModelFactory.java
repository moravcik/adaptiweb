package com.adaptiweb.gwt.framework.modify;

import com.adaptiweb.gwt.mvc.model.NumberModel;
import com.google.gwt.user.client.ui.HasValue;

public class ModifiedModelFactory {
	
	public static <T> ConfigureableModifiedModel<T> create(final HasValue<T> hasValue) {
		return new AbstractHasValueCahangeHandlersModifiedModel<T>(hasValue) {
			@Override
			protected T getCurrentValue() {
				return hasValue.getValue();
			}
		};
	}

	public static <T extends Number> ConfigureableModifiedModel<T> create(final NumberModel<T> numberModel) {
		return new AbstractHasValueCahangeHandlersModifiedModel<T>(numberModel) {
			@Override
			protected T getCurrentValue() {
				return numberModel.getNumber();
			}
		};
	}
}
