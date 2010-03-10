package com.adaptiweb.utils.commons;

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
	
	public EnumValues<T> retain(EnumFilter<? super T> filter) {
		return filter(filter, true);
	}
	
	public EnumValues<T> remove(EnumFilter<? super T> filter) {
		return filter(filter, false);
	}
	
	private EnumValues<T> filter(EnumFilter<? super T> filter, boolean positive) {
		for (T value : values.clone())
			if (positive != filter.fit(value))
				values.remove(value);
		return this;
	}

	public EnumSet<T> get() {
		return values.clone();
	}
}
