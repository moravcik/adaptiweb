package com.adaptiweb.utils.livefile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;

import com.adaptiweb.utils.livefile.LiveFile.FileLoader;

public class AbstractLiveResourceFactory {
	private static final String DEFAULT_CHECK_PERIOD_SYSTEM_PROPERTY = "liveFile_defaultCheckPeriod";
	private static final int DEFAULT_CHECK_PERIOD = 2 * 60 * 1000; //default 2 minutes;
	
	Long checkPeriod;

	Resource resource;
	Resource templateResource;
	
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
	
	/**
	 * Using standard Spring PropertyEditor to resolve Resource from string
	 * with possible use of prefixes: classpath: file: etc.
	 */
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	/**
	 * Enable auto creating missing file during initialization. Optional.
	 * 
	 * Using standard Spring PropertyEditor to resolve Resource from string
	 * with possible use of prefixes: classpath: file: etc.
	 */
	public void setTemplateResource(Resource templateResource) {
		this.templateResource = templateResource;
	}
	
	@PostConstruct
	protected void initTemplate() {
		try {
			File resourceFile = resource.getFile();
			if (templateResource != null && !resourceFile.exists()) {
				createDefaultTemplate(templateResource, resourceFile);
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	protected LiveFile createLiveResource() {
		try {
			LiveFile live = new LiveFileHandler(resource.getFile(), checkPeriod);
			if (loader != null) live.addFileLoader(loader);
			return live;
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private static void createDefaultTemplate(Resource templateResource, File file) {
		InputStream is = null;
		OutputStream os = null;
		try {
			FileUtils.forceMkdir(file.getParentFile());
			is = templateResource.getInputStream();
			os = new FileOutputStream(file);
			IOUtils.copy(is, os);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}
	}

}
