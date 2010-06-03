package com.adaptiweb.utils.ci;

import com.adaptiweb.utils.commons.VariableResolver;

public interface DynamicVariableResolver {

	String resolve(String variableName, VariableResolver variables);

}
