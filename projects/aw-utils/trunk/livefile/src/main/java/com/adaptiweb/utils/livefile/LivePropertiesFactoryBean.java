package com.adaptiweb.utils.livefile;

import org.springframework.beans.factory.FactoryBean;

public class LivePropertiesFactoryBean extends AbstractLiveResourceFactory implements FactoryBean<LiveProperties> {

	@Override
	public LiveProperties getObject() throws Exception {
		LiveFile liveHandler = createLiveResource();
		LiveProperties props = new LiveProperties(liveHandler, getEvaluator(), isUseAsRootContext());
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
