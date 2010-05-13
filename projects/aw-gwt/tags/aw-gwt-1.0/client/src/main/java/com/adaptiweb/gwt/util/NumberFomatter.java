package com.adaptiweb.gwt.util;

import com.google.gwt.i18n.client.NumberFormat;

public class NumberFomatter<N extends Number> implements Formatter<N> {
	
	private final NumberFormat format;
	private final NumberType<N> type;
	
	public static <T extends Number> NumberFomatter<T> create(NumberType<T> type, String format) {
		return new NumberFomatter<T>(NumberFormat.getFormat(format), type);
	}

	protected NumberFomatter(NumberFormat format, NumberType<N> type) {
		this.format = format;
		this.type = type;
	}

	@Override
	public String format(N value) {
		return value == null ? "" : format.format(value.doubleValue());
	}

	@Override
	public N parse(String text) throws IllegalArgumentException {
		return text.length() == 0 ? null : convert(format.parse(text));
	}

	public N convert(Double number) {
		N value = type.convert(number);
		if (number.doubleValue() != value.doubleValue())
			throw new IllegalArgumentException("Too big value!");
		return value;
	}
	
}
