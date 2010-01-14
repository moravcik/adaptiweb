package com.adaptiweb.gwt.framework.modify;

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

}
