package com.adaptiweb.gwt.properties;

public interface GwtPropertiesSource {

	GwtProperties getMetaProperties();
	
	// TODO 
	// support for direct injecting of Gwt properties values into any class, e.g.
	// 
	// @GwtPropertiesValue("link.system_email")
	// String systemEmail;
	// 
	// implemented as:
	// String systemEmail = GwtProperties.instance().get("link.system_email");
	
}
