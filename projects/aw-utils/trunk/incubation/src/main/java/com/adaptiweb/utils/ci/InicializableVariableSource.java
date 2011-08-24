package com.adaptiweb.utils.ci;

import java.io.IOException;

/**
 * NOTE: planned to use Spring SpelExpression instead
 */
public interface InicializableVariableSource extends VariableSource {
	
	void initSource(VariableResolver variables) throws IOException;
}
