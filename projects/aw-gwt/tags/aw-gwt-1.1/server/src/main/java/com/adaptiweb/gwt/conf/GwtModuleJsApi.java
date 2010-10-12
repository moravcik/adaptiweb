package com.adaptiweb.gwt.conf;

import com.adaptiweb.utils.commons.VariableResolver;

public class GwtModuleJsApi extends UriRelatedConfiguration {
	
	private String url;

	public GwtModuleJsApi() {
		super();
	}

	public GwtModuleJsApi(VariableResolver variables, String variableBaseName) {
		super(variables, variableBaseName);
		setUrl(variables.resolveValue(variableBaseName + ".url"));
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
