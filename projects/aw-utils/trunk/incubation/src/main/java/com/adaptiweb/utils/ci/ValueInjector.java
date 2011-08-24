package com.adaptiweb.utils.ci;

public interface ValueInjector {

	Class<?> getType();
	
	void inject(Object value);
	
	String getExpression();
	
	int priority();

	public Class<? extends PropertyConverter<?>> preferedPropertyConverter();
	
	String configValue();

}
