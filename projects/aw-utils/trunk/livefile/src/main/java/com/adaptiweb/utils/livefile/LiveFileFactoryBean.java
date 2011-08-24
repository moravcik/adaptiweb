package com.adaptiweb.utils.livefile;

import org.springframework.beans.factory.FactoryBean;

public class LiveFileFactoryBean extends AbstractLiveResourceFactory implements FactoryBean<LiveFile> {
	
	@Override
	public LiveFile getObject() throws Exception {
		return createLiveResource();
	}

	@Override
	public Class<?> getObjectType() {
		return LiveFileHandler.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
