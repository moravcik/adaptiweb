package com.adaptiweb.utils.commons;

public class EnvironmentVariableSource implements VariableSource {

	private String prefix = "";
	
	public EnvironmentVariableSource() {}
	
	public EnvironmentVariableSource(String prefix) {
		setPrefix(prefix);
	}
	
	@Override
	public String getRawValue(String variableName) throws NullPointerException {
		return variableName.startsWith(prefix) ? 
				System.getenv(variableName.substring(prefix.length())) : null;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix.toString();
	}
}
