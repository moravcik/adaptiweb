package com.adaptiweb.mojo.gwt;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal dependency-graph
 * @requiresDependencyResolution compile
 */
public class DependencyAnalyzerMojo extends AbstractMojo {

	/**
	 * The Maven project object
	 * 
	 * @parameter expression="${project.resources}"
	 * @required
	 * @readonly
	 */
	private List<Resource> resources;
	
	/**
	 * @parameter default-value="${project.compileArtifacts}"
	 */
	private List<Artifact> compileArtifacts;

	public void execute() throws MojoExecutionException, MojoFailureException {

		ClassLoader customClassLoader = createCustomClassLoader(Thread.currentThread().getContextClassLoader());
		
		try {
			getLog().info("DEPENDENCY GRAPH");
			Map<String, GwtModule> map = new LinkedHashMap<String, GwtModule>(); 
			
			for (Resource resource : resources) {
				File dir = new File(resource.getDirectory());
				if (!dir.isDirectory()) continue;
				for (GwtModule module : GwtModule.listModules(dir)) {
					map.put(module.getName(), module);
				}
			}
			LinkedList<GwtModule>  stack = new LinkedList<GwtModule>(map.values());
			while (!stack.isEmpty()) {
				GwtModule module = stack.remove();
				getLog().info("Analyzing GWT Module: " + module.getName());
				if (module.getInherits() != null) {
					for (Inheritance inheritance : module.getInherits()) {
						getLog().info("                   -> " + inheritance.getName());
						if (map.containsKey(inheritance.getName())) continue;
						GwtModule inheritedModule = GwtModule.forName(inheritance.getName(), customClassLoader);
						if (inheritedModule == null)
							throw new MojoFailureException("Cannot find required gwt module " + inheritance.getName());
						map.put(inheritedModule.getName(), inheritedModule);
						stack.add(inheritedModule);
					}
				}
			}
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private ClassLoader createCustomClassLoader(ClassLoader parent) {
		try {
			int i = 0;
			URL[] urls = new URL[compileArtifacts.size()];
			for(Artifact artifact : compileArtifacts) {
				getLog().info("cp: " + artifact.getFile());
				urls[i++] = artifact.getFile().toURL();
			}
			return new URLClassLoader(urls, parent);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
}
