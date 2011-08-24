package com.adaptiweb.utils.livefile;

import org.springframework.beans.factory.FactoryBean;

import com.adaptiweb.utils.spel.SpelEvaluator;

public class LivePropertiesFactoryBean extends AbstractLiveResourceFactory implements FactoryBean<LiveProperties> {

	SpelEvaluator evaluator;
	boolean useAsRootContext = false;

	public void setUseAsRootContext(boolean useAsRootContext) {
		this.useAsRootContext = useAsRootContext;
	}
	
	public void setEvaluator(SpelEvaluator evaluator) {
		this.evaluator = evaluator;
	}
	
	@Override
	public LiveProperties getObject() throws Exception {
		LiveFile liveHandler = createLiveResource();
		LiveProperties props = new LiveProperties(liveHandler, evaluator, useAsRootContext);
		liveHandler.addFileLoader(props);
		return props;
	}

	@Override
	public Class<?> getObjectType() {
		return LiveProperties.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
