package com.adaptiweb.utils.spel;

import java.lang.reflect.Method;
import java.util.Map;

public interface SpelEvaluator {

	SpelEvaluator setExpression(String expressionString);
	
	SpelEvaluator setVariable(String name, Object value);
	
	SpelEvaluator setVariables(Map<String, Object> variables);
	
	SpelEvaluator registerFunction(String name, Method method);
	
	SpelEvaluator setRootObject(Object rootObject);
	
	Object evaluate();
	
	<T> T evaluate(Class<T> desiredResultType);
	
	void dispose();

}