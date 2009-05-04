package com.adaptiweb.mojo.gwt;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import com.google.gwt.i18n.tools.I18NSync;

/**
 * @goal i18n
 * @phase process-sources
 * @requiresProject
 */
public class I18NSyncMojo extends AbstractMojo {

	/**
	 * The Maven project object
	 * 
	 * @parameter expression="${project.resources}"
	 * @required
	 * @readonly
	 */
	private List<Resource> resources;

	/**
	 * @parameter expression="${project.build.sourceDirectory}"
	 * @required
	 * @readonly
	 */
	private File out;
	
	/**
	 * @parameter
	 */
	private String[] messages;

	/**
	 * @parameter
	 */
	private String[] constants;

	public void execute() throws MojoExecutionException {
		
		Thread currentThread = Thread.currentThread();
		ClassLoader customClassLoader = createCustomClassLoader(currentThread.getContextClassLoader());
		
		currentThread.setContextClassLoader(customClassLoader);
		try {
			String[] params = new String[] { "-out", out.getPath(), "-createMessages", null };
			
			if(messages != null) for(String message : messages) {
				if(!isSynchronized(message)) {
					params[params.length - 1] = message;
					I18NSync.main(params);
					getLog().info("GWT i18n synchronizing Messages '" + message + "' done.");
				}
			}
			
			params = new String[] { "-out", out.getPath(), null };
			
			if(constants != null) for(String constant : constants) {
				if(!isSynchronized(constant)) {
					params[params.length - 1] = constant;
					I18NSync.main(params);
					getLog().info("GWT i18n generating Constants '" + constant + "' done.");
				}
			}
		} finally {
			currentThread.setContextClassLoader(currentThread.getContextClassLoader().getParent());
		}
	}

	private ClassLoader createCustomClassLoader(ClassLoader parrentClassLoader) {
		ClassLoader customizedClassLoader = new ClassLoader(parrentClassLoader) {
			@Override
			public URL getResource(String name) {
				for(Resource resource : resources) {
					if(resource.getDirectory() != null) {
						File f = new File(new File(resource.getDirectory()), name);
						if(f.exists()) try {
							return f.toURI().toURL();
						} catch (MalformedURLException e) {
							throw new RuntimeException(e);
						}
					}
				}
				return super.getResource(name);
			}
		};
		return customizedClassLoader;
	}

	private boolean isSynchronized(String name) {
		name = name.replace('.', '/');

		File target = new File(out, (name + ".java"));
		
		name += ".properties";
		
		if(target.exists()) {
			for(Resource resource : resources) {
				if(resource.getDirectory() != null) {
					File source = new File(new File(resource.getDirectory()), name);
					if(source.exists())
						return target.lastModified() > source.lastModified();
				}
			}
		}
		// source not found
		return false;
	}
}
