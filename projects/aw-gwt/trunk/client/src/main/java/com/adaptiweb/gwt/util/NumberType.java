package com.adaptiweb.gwt.util;

public interface NumberType<N extends Number> {

	N convert(Number number);
	
	NumberFomatter<N> defaultFormatter();
	
	static abstract class AbstractNumberType<T extends Number> implements NumberType<T> {
		
		private final String format;
		private NumberFomatter<T> formatter;
		
		public AbstractNumberType(String format) {
			this.format = format;
		}

		@Override
		public NumberFomatter<T> defaultFormatter() {
			if (formatter == null)
				formatter = NumberFomatter.create(this, format);
			return formatter;
		}
	}
	
	public static final NumberType<Byte> BYTE = new AbstractNumberType<Byte>("#") {
		@Override
		public Byte convert(Number number) {
			return number.byteValue();
		}
	};

	public static final NumberType<Double> DOUBLE = new AbstractNumberType<Double>("#,###.##") {
		@Override
		public Double convert(Number number) {
			return number.doubleValue();
		}
	};

	public static final NumberType<Float> FLOAT = new AbstractNumberType<Float>("#.#") {
		@Override
		public Float convert(Number number) {
			return number.floatValue();
		}
	};

	public static final NumberType<Integer> INT = new AbstractNumberType<Integer>("#") {
		@Override
		public Integer convert(Number number) {
			return number.intValue();
		}
	};

	public static final NumberType<Long> LONG = new AbstractNumberType<Long>("#") {
		@Override
		public Long convert(Number number) {
			return number.longValue();
		}
	};

	public static final NumberType<Short> SHORT = new AbstractNumberType<Short>("#") {
		@Override
		public Short convert(Number number) {
			return number.shortValue();
		}
	};
}
