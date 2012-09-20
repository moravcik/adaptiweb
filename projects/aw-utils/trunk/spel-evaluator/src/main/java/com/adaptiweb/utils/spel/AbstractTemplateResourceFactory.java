package com.adaptiweb.utils.spel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;

public class AbstractTemplateResourceFactory {
	Resource resource;
	Resource templateResource;
	
	/**
	 * Using standard Spring PropertyEditor to resolve Resource from string
	 * with possible use of prefixes: classpath: file: etc.
	 */
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	public Resource getResource() {
		return resource;
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
