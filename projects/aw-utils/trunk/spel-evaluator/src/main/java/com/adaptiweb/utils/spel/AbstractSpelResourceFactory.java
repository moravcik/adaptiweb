package com.adaptiweb.utils.spel;


public class AbstractSpelResourceFactory extends AbstractTemplateResourceFactory {

	SpelEvaluator evaluator;
	boolean useAsRootContext = false;
	
	public void setUseAsRootContext(boolean useAsRootContext) {
		this.useAsRootContext = useAsRootContext;
	}
	
	public void setEvaluator(SpelEvaluator evaluator) {
		this.evaluator = evaluator;
	}
	
	public SpelEvaluator getEvaluator() {
		return evaluator;
	}
	
	public boolean isUseAsRootContext() {
		return useAsRootContext;
	}
	
}
