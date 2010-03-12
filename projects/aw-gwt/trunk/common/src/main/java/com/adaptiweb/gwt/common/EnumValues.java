package com.adaptiweb.gwt.common;

import java.util.EnumSet;

public class EnumValues<T extends Enum<T>> {

	public interface EnumFilter<I> {
		boolean fit(I value);
	}

	private final EnumSet<T> values;

	private EnumValues(EnumSet<T> values) {
		this.values = values;
	}

	public static <T extends Enum<T>> EnumValues<T> of(Class<T> enumType) {
		return new EnumValues<T>(EnumSet.allOf(enumType));
	}

	public static <T extends Enum<T>> EnumValues<T> of(EnumSet<T> enumSet) {
		return new EnumValues<T>(EnumSet.copyOf(enumSet));
	}

	public EnumValues<T> retain(EnumFilter<? super T> filter) {
		return filter(true, filter);
	}
	
	public EnumValues<T> remove(EnumFilter<? super T> filter) {
		return filter(false, filter);
	}
	
	public boolean contains(EnumFilter<? super T> filter) {
		for (T value : values)
			if (filter.fit(value))
				return true;
		return false;
	}
	
	private EnumValues<T> filter(boolean positive, EnumFilter<? super T> filter) {
		for (T value : get())
			if (positive != filter.fit(value))
				values.remove(value);
		return this;
	}
	
	public boolean remove(T o) {
		return values.remove(o);
	}

	public EnumSet<T> get() {
		return EnumSet.copyOf(values);
	}
	
	public boolean isEmtpty() {
		return values.isEmpty();
	}

	public int size() {
		return values.size();
	}
}
