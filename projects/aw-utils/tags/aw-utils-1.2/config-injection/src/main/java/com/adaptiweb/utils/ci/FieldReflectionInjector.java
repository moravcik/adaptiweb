package com.adaptiweb.utils.ci;

import java.lang.reflect.Field;

import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

class FieldReflectionInjector extends AbstractAnnotationInjector {
	
	private Field field;
	private Object target;
	private Object currentValue;

	public FieldReflectionInjector(Field field, Object target) {
		super(field.getAnnotation(AutoConfig.class));
		assert field.isAnnotationPresent(AutoConfig.class);
		this.field = field;
		this.target = target;
		field.setAccessible(true);
		currentValue = ReflectionUtils.getField(field, target);
	}

	@Override
	public Class<?> getType() {
		return field.getType();
	}

	@Override
	public void inject(Object value) {
		if (!ObjectUtils.nullSafeEquals(currentValue, value))
			ReflectionUtils.setField(field, target, currentValue = value);
	}
}
