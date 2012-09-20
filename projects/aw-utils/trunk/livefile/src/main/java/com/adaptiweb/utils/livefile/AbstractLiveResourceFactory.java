package com.adaptiweb.utils.livefile;

import java.io.IOException;

import javax.annotation.PostConstruct;

import com.adaptiweb.utils.livefile.LiveFile.FileLoader;
import com.adaptiweb.utils.spel.AbstractSpelResourceFactory;

public class AbstractLiveResourceFactory extends AbstractSpelResourceFactory {
	private static final String DEFAULT_CHECK_PERIOD_SYSTEM_PROPERTY = "liveFile_defaultCheckPeriod";
	private static final int DEFAULT_CHECK_PERIOD = 2 * 60 * 1000; //default 2 minutes;
	
	Long checkPeriod;

	FileLoader loader;
	
	@PostConstruct
	protected void initCheckPeriod() {
		if (checkPeriod == null) {
			String systemCheckPeriod = System.getProperty(DEFAULT_CHECK_PERIOD_SYSTEM_PROPERTY);
			checkPeriod = systemCheckPeriod != null 
				? Long.parseLong(systemCheckPeriod) : DEFAULT_CHECK_PERIOD;
		}
	}
	
	/**
	 * Minimal change checking period in seconds.
	 * Optional, default is 2 minutes.
	 */
	public void setCheckPeriod(Long checkPeriod) {
		this.checkPeriod = checkPeriod;
	}

	/**
	 * Set default {@link FileLoader}
	 */
	public void setLoader(FileLoader loader) {
		this.loader = loader;
	}
	
	protected LiveFile createLiveResource() {
		try {
			LiveFile live = new LiveFileHandler(getResource().getFile(), checkPeriod);
			if (loader != null) live.addFileLoader(loader);
			return live;
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
}
