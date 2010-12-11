package com.adaptiweb.utils.ci;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import com.adaptiweb.utils.commons.Properties;
import com.adaptiweb.utils.commons.VariableResolver;

@SuppressWarnings("serial")
public class VariableProperties extends java.util.Properties {
	
	private VariableResolver variables;
	
	public void setVariableResolver(VariableResolver variables) {
		this.variables = variables;
	}
	
	@Override
	public String getProperty(String key) {
		String result = super.getProperty(key); // not supporting variables in key
		return variables == null ? result : variables.replaceVariables(result);
	}
	
	public void loadFromFile(File file) throws IOException {
		replaceContent(file.exists() ? new Properties(file).toMap() : Collections.<String,String>emptyMap());
	}
	
	private synchronized void replaceContent(Map<String,String> values) {
		// this.clear(); DO NOT CLEAR - something could be added externally, see javax.mail.Session.setProvider
		this.putAll(values);
	}

}
