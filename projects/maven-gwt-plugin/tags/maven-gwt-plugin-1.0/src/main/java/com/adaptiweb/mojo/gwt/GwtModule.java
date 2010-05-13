package com.adaptiweb.mojo.gwt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.adaptiweb.utils.xmlbind.BindUtils;
import com.adaptiweb.utils.xmlbind.annotation.BindAttribute;
import com.adaptiweb.utils.xmlbind.annotation.BindClass;
import com.adaptiweb.utils.xmlbind.annotation.BindElement;

@BindClass
public class GwtModule {
	
	private static final String[] EXTENSION = { "gwt.xml" };
	
	private final String name;
	
	private Inheritance[] inherits;

	public GwtModule(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public GwtModule(File file, File dir) throws IOException {
		this(toModuleName(file, dir));
		parseFile(file);
	}

	private void parseFile(File file) throws IOException {
		BindUtils.unmarshal(file, "UTF-8", this);
	}

	private void parseStream(InputStream is) throws IOException {
		BindUtils.unmarshal(is, "UTF-8", this);
	}
	
	private static String toModuleName(File file, File dir) {
		String absolutePath = file.getAbsolutePath();
		int beginIndex = dir.getAbsolutePath().length() + 1;
		int endIndex = absolutePath.length() - ".gwt.xml".length();
		return absolutePath.substring(beginIndex, endIndex).replace(File.separatorChar, '.');
	}

	public Inheritance[] getInherits() {
		return inherits;
	}

	@BindElement(tagName="inherits")
	public void setInherits(Inheritance[] inherits) {
		this.inherits = inherits;
	}

	public static List<GwtModule> listModules(File baseDir) throws IOException {
		@SuppressWarnings("unchecked")
		Collection<File> files = FileUtils.listFiles(baseDir, EXTENSION, true);
		List<GwtModule> result = new ArrayList<GwtModule>(files.size());
		for (File file : files) result.add(new GwtModule(file, baseDir));
		return result;
	}

	public static GwtModule forName(String name) throws IOException {
		return forName(name, GwtModule.class.getClassLoader());
	}

	public static GwtModule forName(String name, ClassLoader cl) throws IOException {
		InputStream is = cl.getResourceAsStream(toResourceName(name));
		if(is == null) return null;
		GwtModule result = new GwtModule(name);
		result.parseStream(is);
		return result;
	}

	private static String toResourceName(String moduleName) {
		return moduleName.replace('.', File.separatorChar) + ".gwt.xml";
	}
}

@BindClass
class Inheritance {
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
