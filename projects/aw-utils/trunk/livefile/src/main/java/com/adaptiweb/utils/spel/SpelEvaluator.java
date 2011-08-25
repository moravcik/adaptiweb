package com.adaptiweb.utils.spel;

import java.util.Map;

public interface SpelEvaluator {

	SpelEvaluator setExpression(String expressionString);
	
	SpelEvaluator setVariable(String name, Object value);
	
	SpelEvaluator setVariables(Map<String, Object> variables);
	
	SpelEvaluator setRootObject(Object rootObject);
	
	Object evaluate();
	
	<T> T evaluate(Class<T> desiredResultType);
	
	void dispose();

}