package com.adaptiweb.gwt.framework.modify;

import java.util.Comparator;

import com.adaptiweb.gwt.framework.GwtGoodies;
import com.adaptiweb.gwt.framework.HasDebugInfo;

public abstract class AbstractModifiedModel<T> extends BaseModifiedModel
implements ConfigureableModifiedModel<T>, HasDebugInfo {
	
	public static final Equalator<Object> DEFAULT_EQUALATOR = new Equalator<Object>() {
		@Override
		public boolean equal(Object x1, Object x2) {
			return x1 == null ? x2 == null : x1.equals(x2);
		}
	};
	
	private Equalator<? super T> equalator = DEFAULT_EQUALATOR;
	private T originalValue;

	@Override
	public ConfigureableModifiedModel<T> setModifiedTester(Equalator<? super T> equalator) {
		this.equalator = equalator;
		return this;
	}

	@Override
	public ConfigureableModifiedModel<T> setModifiedTester(final Comparator<? super T> comparator) {
		return setModifiedTester(new Equalator<T>() {
			@Override
			public boolean equal(T x1, T x2) {
				return comparator.compare(x1, x2) == 0;
			}
		});
	}
	
	protected AbstractModifiedModel<T> init(T originalValue) {
		this.originalValue = originalValue;
		super.burn();
		return this;
	}
	
	protected void update() {
		setModified(!equalator.equal(originalValue, getCurrentValue()));
	}
	
	@Override
	public void burn() {
		init(getCurrentValue());
	}
	
	@Override
	public void revert() {
		setOriginalValue(originalValue);
		update();
	}
	
	protected abstract void setOriginalValue(T originalValue);

	protected abstract T getCurrentValue();

	@Override
	public String toDebugString() {
		return GwtGoodies.toDebugString(originalValue) + (isModified() ? "*" : "");
	}
}
