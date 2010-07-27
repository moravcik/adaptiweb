package com.adaptiweb.utils.ci;

abstract class AbstractAnnotationInjector implements ValueInjector {

	private final AutoConfig preferences;
	
	public AbstractAnnotationInjector(AutoConfig preferences) {
		this.preferences = preferences;
	}

	@Override
	public String configValue() {
		return preferences.configValue();
	}

	@Override
	public String getExpression() {
		return preferences.value();
	}

	@Override
	public Class<? extends PropertyConverter<?>> preferedPropertyConverter() {
		return preferences.converter();
	}

	@Override
	public int priority() {
		return preferences.priority();
	}

}
