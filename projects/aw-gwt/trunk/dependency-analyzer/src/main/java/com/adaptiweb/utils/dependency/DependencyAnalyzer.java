package com.adaptiweb.utils.dependency;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.adaptiweb.utils.graph.DotGraphHandler;
import com.adaptiweb.utils.graph.Vertex;
import com.adaptiweb.utils.xmlbind.BindUtils;
import com.adaptiweb.utils.xmlbind.annotation.BindAttribute;
import com.adaptiweb.utils.xmlbind.annotation.BindClass;
import com.adaptiweb.utils.xmlbind.annotation.BindElement;

/**
 * Command to launch this analyzer:
 * 
 * <pre>
 * $ java -jar \
 * /home/milan/after/workspace/geomodel/solargis/gwt-dependency-analyzer/target/gwt-dependency-analyzer-1.0-SNAPSHOT-jar-with-dependencies.jar \
 * | dot -ogwt-dep.png -Tpng; f-spot -view gwt-dep.png
 * </pre>
 * 
 * <b>Note1:</b> if you need change path "/home/milan/after/workspace/",
 * it's also required change this path in code!
 * <b>Note2:</b> if required jar doesn't exist create it by command:
 * <pre>
 * /home/milan/after/workspace/geomodel/solargis/gwt-dependency-analyzer$ mvn assembly:assembly
 * </pre>!
 *
 */
public class DependencyAnalyzer {
	
	private static class GwtConfigFileScanner implements Iterable<File> {
		
		private static final String[] EXTENSION = { "gwt.xml" };
		private final File baseDir;

		public GwtConfigFileScanner(File baseDir) {
			this.baseDir = baseDir;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Iterator<File> iterator() {
			return FileUtils.iterateFiles(baseDir, EXTENSION, true);
		}
		
	}
	
	public static class InheritsNameColector implements Iterable<String> {
		@BindClass
		public static class Inherit {
			private String name;

			public String getName() {
				return name;
			}

			@BindAttribute
			public void setName(String name) {
				this.name = name;
			}
			
			@Override
			public String toString() {
				return name;
			}
		}
		
		private Inherit[] inherits;

		public Inherit[] getInherits() {
			return inherits;
		}

		@BindElement(tagName="inherits")
		public void setInherits(Inherit[] inherits) {
			this.inherits = inherits;
		}
		
		@Override
		public String toString() {
			return Arrays.toString(inherits);
		}

		@Override
		public Iterator<String> iterator() {
			return new Iterator<String>() {
				int i = 0;

				@Override
				public boolean hasNext() {
					return inherits != null && i < inherits.length;
				}

				@Override
				public String next() {
					return inherits[i++].toString();
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}
	}
	
	public static void main(String[] args) throws IOException {
		final File baseDir = new File("/home/miso/workspace/solargis/solargis/clients");

		DotGraphHandler handler = new DotGraphHandler();
		handler.setCatToBound(false);
		handler.handleStart();
		
		for (File file : new GwtConfigFileScanner(baseDir)) {
			String module = toModule(file.getAbsolutePath());
			if (module != null) {
				for (String refModule : BindUtils.unmarshal(file, "UTF-8", InheritsNameColector.class)) {
					handler.handle(toVertex(module), toVertex(refModule));
				}
			}
		}
		
		handler.handleEnd();
		handler.writeTo(System.out);
	}

	private static Vertex toVertex(String module) {
		Vertex result = new Vertex(module);
		result.setLabel(module.substring(module.lastIndexOf('.') + 1));
		return result;
	}

	private static final Pattern PATTERN = Pattern.compile(".*[\\\\/]src[\\\\/]main[\\\\/](?:java|resources)[\\\\/](.*)\\.gwt\\.xml");
	
	private static String toModule(String path) {
		Matcher m = PATTERN.matcher(path);
		return m.matches() ? m.group(1).replace(File.separatorChar, '.') : null;
	}
}
