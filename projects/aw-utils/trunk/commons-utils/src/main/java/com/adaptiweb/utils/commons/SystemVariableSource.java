package com.adaptiweb.utils.commons;

public class SystemVariableSource implements VariableSource {
	
	private String prefix = "";
	
	public SystemVariableSource() {}

	public SystemVariableSource(String prefix) {
		setPrefix(prefix);
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public String getRawValue(String variableName) throws NullPointerException {
		return variableName.startsWith(prefix) ?
				System.getProperty(variableName.substring(prefix.length())) : null;
	}

}
