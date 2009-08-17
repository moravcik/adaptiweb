package com.adaptiweb.gwt.framework.modify;

import java.util.Comparator;

public class AbstractModifiedModel<T> extends BaseModifiedModel {
	
	public interface Equalator<X> {
		boolean equal(X x1, X x2);
	}
	
	public static final Equalator<Object> DEFAULT_EQUALATOR = new Equalator<Object>() {
		@Override
		public boolean equal(Object x1, Object x2) {
			return x1 == null ? x2 == null : x1.equals(x2);
		}
	};
	
	private final Equalator<? super T> euqalator;
	private T originalValue;
	
	public AbstractModifiedModel() {
		this(DEFAULT_EQUALATOR);
	}

	public AbstractModifiedModel(final Comparator<? super T> comparator) {
		this(new Equalator<T>() {
			@Override
			public boolean equal(T x1, T x2) {
				return comparator.compare(x1, x2) == 0;
			}
		});
	}
	
	public AbstractModifiedModel(Equalator<? super T> equalator) {
		this.euqalator = equalator;
	}
	
	AbstractModifiedModel<T> init(T originalValue) {
		this.originalValue = originalValue;
		setModified(false);
		return this;
	}
	
	protected void update(T actualValue) {
		setModified(!euqalator.equal(originalValue, actualValue));
	}
}
