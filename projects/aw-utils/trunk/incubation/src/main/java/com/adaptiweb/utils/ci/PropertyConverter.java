package com.adaptiweb.utils.ci;



public interface PropertyConverter<T> {
	
	interface DefaultPopertyConverter extends PropertyConverter<Void> {}
	
	Class<T> getType();
	
	T convert(VariableResolver variables, String name, String defaultValue);
}
