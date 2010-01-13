package com.adaptiweb.utils.ci;

import java.lang.reflect.Method;

import org.springframework.util.ReflectionUtils;

class MethodReflectionInjector extends AbstractAnnotationInjector {
	
	private Method method;
	private Object target;

	public MethodReflectionInjector(Method method, AutoConfigurable target) {
		super(method.getAnnotation(AutoConfig.class));
		assert method.isAnnotationPresent(AutoConfig.class);
		assert method.getDeclaringClass() == target.getClass();
		assert method.getParameterTypes().length == 1;
		
		this.method = method;
		this.target = target;
		
		method.setAccessible(true);
	}

	@Override
	public Class<?> getType() {
		return method.getParameterTypes()[0];
	}

	@Override
	public void inject(Object value) {
		ReflectionUtils.invokeMethod(method, target, args(value));
	}

	private static <T> T[] args(T...values) {
		return values;
	}
}
