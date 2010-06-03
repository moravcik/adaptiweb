package com.adaptiweb.utils.ci;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.adaptiweb.utils.ci.PropertyConverter.DefaultPopertyConverter;
import com.adaptiweb.utils.commons.VariableResolver;

public class ConfigProvider implements BeanPostProcessor, InitializingBean {
	
	private VariableResolver variables;
	
	private List<ValueInjector> targets = new ArrayList<ValueInjector>();
	
	private Map<Class<?>, PropertyConverter<?>> converters = new HashMap<Class<?>, PropertyConverter<?>>();
	
	private static final Comparator<ValueInjector> PRIORITYCOMPARATOR  = new Comparator<ValueInjector>() {
		@Override
		public int compare(ValueInjector o1, ValueInjector o2) {
			return o1.priority() - o2.priority();
		}
	};
	
	@Required
	public void setVariableResolver(VariableResolver variables) {
		this.variables = variables;
	}
	
	public VariableResolver getVariableResolver() {
		return variables;
	}

	public void setConverters(Collection<PropertyConverter<?>> converters) {
		this.converters.clear();
		initBasicConverters();
		for (PropertyConverter<?> converter : converters)
			registerConverter(converter);
	}

	private void registerConverter(PropertyConverter<?> converter) {
		this.converters.put(converter.getType(), converter);
		this.converters.put(converter.getClass(), converter);
	}
	
	private void initBasicConverters() {
		registerConverter(new DefaultIntegerPropertyConverter());
		registerConverter(new DefaultStringPropertyConverter());
		registerConverter(new DefaultFilePropertyConverter());
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		List<ValueInjector> recentlyAdded = new LinkedList<ValueInjector>();
		
		for (Field field : bean.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(AutoConfig.class)) {
				ValueInjector injector = new FieldReflectionInjector(field, bean);
				recentlyAdded.add(injector);
				targets.add(injector);
			}
		}
		for (Method method : bean.getClass().getDeclaredMethods()) {
			if (method.isAnnotationPresent(AutoConfig.class)) {
				ValueInjector injector = new MethodReflectionInjector(method, bean);
				recentlyAdded.add(injector);
				targets.add(injector);
			}
		}
		Collections.sort(recentlyAdded, PRIORITYCOMPARATOR);
		for (ValueInjector injector: recentlyAdded) performInjection(injector);
		Collections.sort(targets, PRIORITYCOMPARATOR);
		return bean;
	}
	
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException { return bean; };

	public <T> T getTypedProperty(String expr, Class<T> type) {
		return getTypedProperty(expr, type, null);
	}
	
	public <T> T getTypedProperty(String expr, Class<T> type, T defaultValue) {
		assert converters.containsKey(type) : "PropertyConverter for type " + type + " was not found!";
		return type.cast(converters.get(type).convert(variables, expr, null));
	}
	
	public void afterPropertiesSet() throws Exception {
		if (converters.size() == 0) initBasicConverters();
		for (ValueInjector injector : targets) performInjection(injector);
	}

	private void performInjection(ValueInjector injector) {
		Class<?> converterType = injector.preferedPropertyConverter();
		if (converterType == DefaultPopertyConverter.class) converterType = injector.getType();
		PropertyConverter<?> converter = converters.get(converterType);
		
		if (converter == null) {
			if (PropertyConverter.class.isAssignableFrom(converterType)) {
				try {
					converter = PropertyConverter.class.cast(converterType.newInstance());
				} catch (Exception e) {
					throw new IllegalStateException("Can't resolve property converter " + converterType.getName(), e);
				}
			}
			else throw new IllegalStateException("Not found PropertyConverter for type " + converterType.getName());
		}

		injector.inject(converter.convert(variables, injector.getExpression(), injector.configValue()));
	}
}
