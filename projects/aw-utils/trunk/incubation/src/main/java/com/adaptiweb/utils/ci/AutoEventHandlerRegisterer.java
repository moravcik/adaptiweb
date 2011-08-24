package com.adaptiweb.utils.ci;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import com.adaptiweb.utils.ci.event.AbstractEvent;
import com.adaptiweb.utils.ci.event.EventBus;
import com.adaptiweb.utils.ci.event.EventHandler;

public class AutoEventHandlerRegisterer implements BeanPostProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(AutoEventHandlerRegisterer.class);
	
	private final EventBus eventBus;
	
	@Autowired
	public AutoEventHandlerRegisterer(EventBus eventBus) {
		this.eventBus = eventBus;
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof EventHandler) registerHandler((EventHandler) bean, beanName);
		return bean;
	}

	private void registerHandler(EventHandler bean, String beanName) {
		for (Method m : bean.getClass().getMethods()) {
			Class<?>[] parameterTypes = m.getParameterTypes();
			if (parameterTypes.length != 1 || !AbstractEvent.class.isAssignableFrom(parameterTypes[0])) continue;
			
			@SuppressWarnings("unchecked")
			Class<? extends AbstractEvent<EventHandler,?>> eventType = (Class<? extends AbstractEvent<EventHandler, ?>>) parameterTypes[0];
			
			//TODO only direct extension are supported for now
			Type g = eventType.getGenericSuperclass();
			if (g instanceof ParameterizedType == false) continue;
			ParameterizedType p = (ParameterizedType) g;
			if (AbstractEvent.class != p.getRawType()) continue;
			Type i = p.getActualTypeArguments()[0];
			if (i instanceof Class<?> == false) continue;
			Class<?> interfaceType = (Class<?>) i;
			if (ReflectionUtils.findMethod(interfaceType, m.getName(), m.getParameterTypes()) == null) continue;
			
			eventBus.addHandler(eventType, bean);
			logger.info("Bean '{}' was automatically registered as handler for event {}.", beanName, eventType.getSimpleName());
		}
	}
}
