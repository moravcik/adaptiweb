package com.adaptiweb.utils.ci;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.adaptiweb.utils.commons.VariableResolver;

public class ConfigProvider {
	
	private VariableResolver variables;
	
	private List<ValueInjector> targets = new ArrayList<ValueInjector>();
	
	private Map<Class<?>, PropertyConverter<?>> converters = new HashMap<Class<?>, PropertyConverter<?>>();
	
	private static final Comparator<ValueInjector> PRIORITYCOMPARATOR  = new Comparator<ValueInjector>() {
		@Override
		public int compare(ValueInjector o1, ValueInjector o2) {
			return o1.priority() - o2.priority();
		}
	};
	
	@Autowired
	public void setVariableResolver(VariableResolver variables) {
		this.variables = variables;
	}
	
	public VariableResolver getVariableResolver() {
		return variables;
	}

	@Autowired
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

	@Autowired
	public void setTargets(Collection<AutoConfigurable> configurableBeans) {
		targets.clear();
		
		for (AutoConfigurable bean : configurableBeans) {
			for (Field field : bean.getClass().getDeclaredFields()) {
				if (field.isAnnotationPresent(AutoConfig.class)) {
					targets.add(new FieldReflectionInjector(field, bean));
				}
			}
			for (Method method : bean.getClass().getDeclaredMethods()) {
				if (method.isAnnotationPresent(AutoConfig.class)) {
					targets.add(new MethodReflectionInjector(method, bean));
				}
			}
		}
		Collections.sort(targets, PRIORITYCOMPARATOR);
	}
	
	public <T> T getTypedProperty(String expr, Class<T> type) {
		return getTypedProperty(expr, type, null);
	}
	
	public <T> T getTypedProperty(String expr, Class<T> type, T defaultValue) {
		assert converters.containsKey(type) : "PropertyConverter for type " + type + " was not found!";
		return type.cast(converters.get(type).convert(variables, expr, null));
	}
	
	@PostConstruct
	public void applyConfigInjection() {
		for (ValueInjector injector : targets) {
			Class<? extends PropertyConverter<?>> converterType = injector.preferedPropertyConverter();
			
			PropertyConverter<?> converter = converters.get(
					converters.containsKey(converterType) ? converterType : injector.getType());
			
			injector.inject(converter.convert(variables, injector.getExpression(), injector.configValue()));
		}
	}
}
