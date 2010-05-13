package com.adaptiweb.gwt.framework.modify;

import java.util.Comparator;

public interface ConfigureableModifiedModel<T> extends ModifiedModel {
	
	public interface Equalator<X> {
		boolean equal(X x1, X x2);
	}
	
	public ConfigureableModifiedModel<T> setModifiedTester(Equalator<? super T> equalator);

	public ConfigureableModifiedModel<T> setModifiedTester(Comparator<? super T> comparator);
}
