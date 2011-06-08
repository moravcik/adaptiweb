package com.adaptiweb.gwt.codegen;

import com.adaptiweb.gwt.properties.GwtPropertiesDefaultValue;
import com.adaptiweb.gwt.properties.GwtPropertiesKey;
import com.adaptiweb.gwt.properties.GwtPropertiesPrefix;
import com.adaptiweb.gwt.properties.GwtProperties;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;


public class GwtPropertiesSourceGenerator extends Generator { 

	@Override
	public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
		TreeLogger implLogger = logger.branch(TreeLogger.DEBUG, "Generating GwtPropertiesSource implementation for '" + typeName + "'");
		return new MetaPropertiesSourceCreator(context).create(implLogger, typeName);
	}
}

class MetaPropertiesSourceCreator extends AbstractCodeCreator {

	public MetaPropertiesSourceCreator(GeneratorContext context) {
		super(context);
	}
	
	public String create(TreeLogger logger, String typeName) throws UnableToCompleteException {
		JClassType sourceType = context.getTypeOracle().findType(typeName);
		JClassType metaType = context.getTypeOracle().findType(GwtProperties.class.getName());
		if (sourceType == null || metaType == null) {
			logger.log(TreeLogger.ERROR, "Unable to find metadata for type '" + typeName + "'");
			throw new UnableToCompleteException();
		}
		String packageName = sourceType.getPackage().getName();
		String className = sourceType.getSimpleSourceName() + "_Impl";

		// import return types
		for (JMethod method : sourceType.getOverridableMethods()) {
			JClassType returnType = method.getReturnType().isClass();
			if (returnType != null) addImport(returnType.getErasedType().getQualifiedSourceName());
		}
		addImport(GwtProperties.class.getName());
		
		if (openWriter(logger, packageName, className, null, sourceType.getErasedType().getQualifiedSourceName())) {
			printSetPrefix();
			String getterMethod = printMetaProperties(logger, sourceType, metaType);
			for (JMethod method : sourceType.getOverridableMethods()) {
				if (method.getParameters().length > 0) continue; // supporting only no-parameter access methods
				if (method.getName().equals(getterMethod)) continue;
				printAccessMethod(logger, method, getterMethod);
			}
			closeWriter(logger);
		}
		return packageName + "." + className;
	}
	
	private void printSetPrefix() {
		println("\nprivate String ginPrefix;");
		println("\npublic void setPrefix(String prefix) {");
		println("this.ginPrefix = prefix;");
		println("}");
	}
	
	private String printMetaProperties(TreeLogger logger, JClassType sourceType, JClassType metaType) throws UnableToCompleteException {
		// find getter method
		String getterMethod = null;
		for (JMethod method : sourceType.getOverridableMethods()) {
			if (method.getParameters().length > 0) continue;
			if (metaType.equals(method.getReturnType().isClass()))
				getterMethod = method.getName();
		}
		if (getterMethod == null) {
			logger.log(Type.ERROR, "Not found " + GwtProperties.class + " getter method!");
			throw new UnableToCompleteException();
		}
		// find factory method with prefix parameter
		String instanceMethod = findStringFactoryMethodName(metaType);
		if (instanceMethod == null) {
			logger.log(Type.ERROR, "Not found " + GwtProperties.class + " factory method with prefix parameter!");
			throw new UnableToCompleteException();
		}
		GwtPropertiesPrefix sourcePrefix = sourceType.getAnnotation(GwtPropertiesPrefix.class);
		String prefix = (sourcePrefix != null ? "\"" + sourcePrefix.value() + sourcePrefix.separator() + "\"" : "\"\""); 
		// instantiate GwtProperties
		print("\nprivate ").print(GwtProperties.class).println(" meta;");
		printGetMetaPropertiesMethod(getterMethod, instanceMethod, prefix);
		return getterMethod;
	}
	
	private void printGetMetaPropertiesMethod(String getterMethod, String instanceMethod, String prefix) {
		// print getter method
		print("\npublic ").print(GwtProperties.class).print(" ").print(getterMethod).println("() {");
		println("if (meta == null) {");
		print("meta = ").print(GwtProperties.class.getSimpleName()).print(".").print(instanceMethod).print("(ginPrefix + ").print(prefix).println(");");
		println("}");
		println("return meta;");
		println("}");
	}
	
	private void printAccessMethod(TreeLogger logger, JMethod method, String getterMethod) {
		GwtPropertiesKey key = method.getAnnotation(GwtPropertiesKey.class);
		String metaKey = key != null ? key.value() : method.getName();
		GwtPropertiesDefaultValue defaultValue = method.getAnnotation(GwtPropertiesDefaultValue.class);
		
		JClassType accessType = null;
		
		JPrimitiveType primitiveType = method.getReturnType().isPrimitive();
		if (primitiveType != null) {
			String primitiveObjectType = primitiveToObjectType(primitiveType.getSimpleSourceName());
			accessType = context.getTypeOracle().findType("java.lang", primitiveObjectType);
		} else accessType = method.getReturnType().isClass();
		
		if (accessType == null) {
			logger.log(Type.DEBUG, "Skipping method " + method.getName() + " - no target type found!");
			return;
		}
		// find valueOf method
		String valueOf = null;
		if (!accessType.getQualifiedSourceName().equals(String.class.getName())) { // if not String
			valueOf = findStringFactoryMethodName(accessType);
			if (valueOf == null) {
				logger.log(Type.DEBUG, "Skipping method " + method.getName() + " - no target String factory method!");
				return;
			}
		}
		// print method
		print("\npublic ").print(method.getReturnType()).print(" ").print(method.getName()).println("() {");
		print("return ");
		if (valueOf != null) print(accessType.getName()).print(".").print(valueOf).print("(");
		print(getterMethod).print("()").print(".get(").print("\"").print(metaKey).print("\"");
		if (defaultValue != null) print(", ").print("\"").print(defaultValue.value()).print("\"");
		if (valueOf != null) print(")");
		println(");");
		println("}");		
	}
	
	private String findStringFactoryMethodName(JClassType type) {
		for (JMethod method : type.getMethods()) {
			if (!method.isStatic()) continue;
			if (!method.getReturnType().equals(type)) continue;
			if (method.getParameters().length != 1) continue;
			if (method.getParameters()[0].getType().getQualifiedSourceName().equals(String.class.getName())) 
				return method.getName();
		}
		return null;
	}
	
}
