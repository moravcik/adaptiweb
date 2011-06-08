package com.adaptiweb.gwt.properties;

import com.adaptiweb.gwt.properties.gin.annotation.GwtPropertiesPrefixInject;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public interface GwtPropertiesSource {

	@Inject
	void setPrefix(@GwtPropertiesPrefixInject String prefix);
	
	GwtProperties getMetaProperties();
	
}
