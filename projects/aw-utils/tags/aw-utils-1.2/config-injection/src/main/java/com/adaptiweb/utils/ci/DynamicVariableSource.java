package com.adaptiweb.utils.ci;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adaptiweb.utils.commons.InicializableVariableSource;
import com.adaptiweb.utils.commons.VariableResolver;

public class DynamicVariableSource implements InicializableVariableSource {

	private final Map<String, DynamicVariableResolver> dynamicProperties;
	private VariableResolver variables = null;
	
	@Autowired
	public DynamicVariableSource(Map<String, DynamicVariableResolver> dynamicProperties) {
		this.dynamicProperties = dynamicProperties;
	}
	
	@Override
	public void initSource(VariableResolver variables) throws IOException {
		this.variables = variables;
	}
	
	@Override
	public String getRawValue(String variableName) throws NullPointerException {
		DynamicVariableResolver dynamic = dynamicProperties.get(variableName);
		if (dynamic == null) return null;
		return dynamic.resolve(variableName, variables);
	}

}
