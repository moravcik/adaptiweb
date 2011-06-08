package com.adaptiweb.gwt.codegen;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

public class AbstractCodeCreator {
	
	private static final Map<String, String> primitiveToObjectType = new HashMap<String, String>();
	
	static {
		primitiveToObjectType.put(byte.class.getName(), Byte.class.getSimpleName());
		primitiveToObjectType.put(short.class.getName(), Short.class.getSimpleName());
		primitiveToObjectType.put(int.class.getName(), Integer.class.getSimpleName());
		primitiveToObjectType.put(long.class.getName(), Long.class.getSimpleName());
		primitiveToObjectType.put(float.class.getName(), Float.class.getSimpleName());
		primitiveToObjectType.put(double.class.getName(), Double.class.getSimpleName());
		primitiveToObjectType.put(boolean.class.getName(), Boolean.class.getSimpleName());
		primitiveToObjectType.put(char.class.getName(), Character.class.getSimpleName());
	}
	
	private SourceWriter writer;
	private final Map<String, String> imports = new HashMap<String, String>();
	protected final GeneratorContext context;
	private ClassSourceFileComposerFactory composer;
	private PrintWriter printWriter;
	
	public AbstractCodeCreator(GeneratorContext context) {
		this.context = context;
	}

	protected String primitiveToObjectType(String primitiveType) {
		return primitiveToObjectType.get(primitiveType);
	}

	public AbstractCodeCreator print(String str) {
		getWriter().print(str);
		return this;
	}
	
	protected void println() {
		getWriter().println();
	}

	public void println(String str) {
		if (str.startsWith("}")) getWriter().outdent();
		getWriter().println(str);
		if (str.endsWith("{")) getWriter().indent();
	}

	public AbstractCodeCreator print(JType paramType) {
		return print(printableType(paramType));
	}
	
	public AbstractCodeCreator print(Class<?> paramType) {
		return print(printableType(paramType));
	}
	
	protected String printableType(JType paramType) {
		return printableType(paramType.getErasedType().getQualifiedSourceName());
	}

	protected String printableType(Class<?> type) {
		return printableType(type.getName());
	}
	
	protected String printableType(String type) {
		if (type.startsWith("java.lang.")) type = type.substring(10);
		else if (imports.containsKey(type)) type = imports.get(type);
		return type;
	}

	protected void addImport(String importType) {
		int index = importType.lastIndexOf('.') + 1;
		if (index == 0 || importType.startsWith("java.lang.")) return;
		String simpleName = importType.substring(index);
		if (imports.containsValue(simpleName)) return;
		imports.put(importType, simpleName);
		if (composer != null) {
			composer.addImport(importType);
			if (writer != null) throw new IllegalStateException("It's too late to add import! Call this method before first print.");
		}
		
	}
	
	protected boolean openWriter(TreeLogger logger, String packageName, String className, String superClass, String... interfaces) {
		printWriter = context.tryCreate(logger, packageName, className);
		// if print writer is null, source code has ALREADY been generated
		if (printWriter == null) return false;
		
		composer = new ClassSourceFileComposerFactory(packageName, className);
		if (superClass != null) composer.setSuperclass(superClass);
		for (String i : interfaces) composer.addImplementedInterface(i);
		for (String i : imports.keySet()) composer.addImport(i);

		return true;
	}

	protected SourceWriter getWriter() {
		if (writer == null) {
			if (composer == null) throw new IllegalStateException("Writer wasn't open yet!");
			writer = composer.createSourceWriter(context, printWriter);
		}
		return writer;
	}

	protected void closeWriter(TreeLogger logger) {
		getWriter().commit(logger);
		writer = null;
		composer = null;
		printWriter = null;
	}

	protected AbstractCodeCreator printCast(JType paramType) {
		print("(").print(printableType(paramType)).print(")");
		
		if (paramType.isPrimitive() != null) {
			String objType = primitiveToObjectType(paramType.getSimpleSourceName());
			print(" (").print(objType).print(")");
		}
		return this;
	}
	
}
