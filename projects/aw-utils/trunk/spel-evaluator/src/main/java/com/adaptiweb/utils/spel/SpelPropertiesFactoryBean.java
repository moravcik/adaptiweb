package com.adaptiweb.utils.spel;

import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;

public class SpelPropertiesFactoryBean extends AbstractSpelResourceFactory implements FactoryBean<SpelProperties> {

	Properties properties;

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	@Override
	public SpelProperties getObject() throws Exception {
		SpelProperties props = new SpelProperties(evaluator, useAsRootContext);
		// load statically defined properties first, may be overriden by properties loaded from resource
		if (properties != null) { 
			props.loadProperties(properties);
		}
		props.loadProperties(resource.getFile());
		return props;
	}

	@Override
	public Class<?> getObjectType() {
		return SpelProperties.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
