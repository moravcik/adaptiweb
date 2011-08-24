package com.adaptiweb.utils.ci;


public interface DynamicVariableResolver {

	String resolve(String variableName, VariableResolver variables);

}
