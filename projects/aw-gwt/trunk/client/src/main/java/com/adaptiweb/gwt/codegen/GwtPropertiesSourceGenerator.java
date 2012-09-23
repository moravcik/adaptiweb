package com.adaptiweb.gwt.codegen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.adaptiweb.gwt.properties.GwtProperties;
import com.adaptiweb.gwt.properties.GwtPropertiesDefaultValue;
import com.adaptiweb.gwt.properties.GwtPropertiesKey;
import com.adaptiweb.gwt.properties.GwtPropertiesPrefix;
import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JArrayType;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;


public class GwtPropertiesSourceGenerator extends Generator { 

	@Override
	public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
		TreeLogger implLogger = logger.branch(TreeLogger.DEBUG, "Generating GwtPropertiesSource implementation for '" + typeName + "'");
		return new MetaPropertiesSourceCreator(context).create(implLogger, typeName);
	}
}

class MetaPropertiesSourceCreator extends AbstractCodeCreator {

	private final String gwtPropertiesPrefix;
	
	public MetaPropertiesSourceCreator(GeneratorContext context) {
		super(context);
		try {
			gwtPropertiesPrefix = context.getPropertyOracle().getConfigurationProperty("gwtPropertiesPrefix").getValues().get(0);
		} catch (BadPropertyValueException e) {
			throw new RuntimeException("Missing gwt configuration: <set-configuration-property name=\"gwtPropertiesPrefix\" value=\"...\"/>", e);
		} 
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

		importReturnTypes(sourceType);
		addImport(GwtProperties.class.getName());
		
		if (openWriter(logger, packageName, className, null, sourceType.getErasedType().getQualifiedSourceName())) {
			// print prefix field
			print("\nprivate final String prefix = \"").print(gwtPropertiesPrefix).println("\";");
			// print properties getter method
			String getterMethod = printMetaProperties(logger, sourceType, metaType);
			// print interface methods
			for (JMethod method : sourceType.getOverridableMethods()) {
				if (method.getParameters().length > 0) continue; // supporting only no-parameter access methods
				if (method.getName().equals(getterMethod)) continue;
				printAccessFieldAndMethod(logger, method, getterMethod);
			}
			closeWriter(logger);
		}
		return packageName + "." + className;
	}
	
	private void importReturnTypes(JClassType sourceType) {
		for (JMethod method : sourceType.getOverridableMethods()) {
			JType returnType = method.getReturnType();
			if (!isArray(returnType)) addImport(returnType);
			JType targetType = findTargetType(returnType);
			if (targetType != null) {
				addImport(targetType);
				if (isCollectionOrListType(returnType)) addImport(ArrayList.class.getName());
				else if (isEnumSetType(returnType)) addImport(EnumSet.class.getName());
				else if (isSetType(returnType)) addImport(HashSet.class.getName());
			}
		}
	}
	
	private boolean isCollectionOrListType(JType type) {
		JParameterizedType parameterized = type.isParameterized();
		if (parameterized != null) {
			return equalsType(parameterized, Collection.class) || equalsType(parameterized, List.class);
		}
		return false;
	}
	
	private boolean isEnumSetType(JType type) {
		JParameterizedType parameterized = type.isParameterized();
		if (parameterized != null) {
			return equalsType(parameterized, Set.class) && parameterized.getTypeArgs()[0].isEnum() != null;
		}
		return false;		
	}

	private boolean isSetType(JType type) {
		JParameterizedType parameterized = type.isParameterized();
		if (parameterized != null) {
			return equalsType(parameterized, Set.class);
		}
		return false;		
	}

	private boolean isArray(JType type) {
		return type.isArray() != null;
	}
	
	private boolean isArrayOrParameterizedType(JType type) {
		return type.isArray() != null || type.isParameterized() != null;
	}

	private JType findTargetType(JType type) {
		if (isArrayOrParameterizedType(type)) {
			JArrayType arrayType = type.isArray();
			if (arrayType != null) return arrayType.getComponentType();
			JParameterizedType parameterizedType = type.isParameterized();
			if (parameterizedType != null) return parameterizedType.getTypeArgs()[0];
		}
		return null;
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
		print("meta = ").print(instanceMethod).print("(prefix + ").print(prefix).println(");");
		println("}");
		println("return meta;");
		println("}");
	}
	
	private void printAccessFieldAndMethod(TreeLogger logger, JMethod method, String getterMethod) {
		GwtPropertiesKey key = method.getAnnotation(GwtPropertiesKey.class);
		String methodName = method.getName();
		String metaKey = key != null ? key.value() : methodName;
		GwtPropertiesDefaultValue defaultValue = method.getAnnotation(GwtPropertiesDefaultValue.class);
		
		JType returnType = method.getReturnType();
		JType accessType = convertToNonPrimitiveType(returnType);
		
		JClassType accessTargetType = findAccessTargetType(returnType);
		boolean isAccessField = accessTargetType != null;
		
		String factoryMethod = null;
		try {
			JClassType targetType = accessTargetType != null ? accessTargetType : accessType.isClassOrInterface();
			factoryMethod = findFactoryMethod(targetType, method);
		} catch (Exception e) {
			logger.log(Type.DEBUG, "Skipping method " + methodName + " - no target String factory method!");
			return;
		}
		
		// print field
		if (isAccessField) {
			print("\nprivate ").print(returnType).print(" ").print(methodName).println(";");
		}
		
		// print method
		print("\npublic ").print(returnType).print(" ").print(methodName).println("() {");
		if (isAccessField) {
			printFieldAccessMethodBody(factoryMethod, getterMethod, metaKey, accessTargetType, methodName, returnType, defaultValue);
		} else printSimpleAccessMethodBody(factoryMethod, getterMethod, metaKey, defaultValue);
		println("}");		
	}
	
	private JClassType findAccessTargetType(JType returnType) {
		JType targetType = findTargetType(returnType);
		return targetType != null
			?  convertToNonPrimitiveType(targetType).isClassOrInterface() : null;
	}
	
	private void printFieldAccessMethodBody(String factoryMethod, String getterMethod, String metaKey, 
			JClassType accessTargetType, String accessFieldName, JType returnType,
			GwtPropertiesDefaultValue defaultValue) {
		print("if (").print(accessFieldName).println(" == null) {");
		print("String[] strArray = ");
		printGetterMethod(getterMethod, metaKey, defaultValue);
		println(".split(\",\");");
		// instantiate field
		print(accessFieldName).print(" = ");
		if (isArray(returnType)) {
			print("new ").print(accessTargetType).println("[strArray.length];");
		} else if (isCollectionOrListType(returnType)) {
			print("new ").print(ArrayList.class).print("<").print(accessTargetType).println(">();");
		} else if (isEnumSetType(returnType)) {
			print(EnumSet.class).print(".noneOf(").print(accessTargetType).println(".class);");
		} else if (isSetType(returnType)) {
			print("new ").print(HashSet.class).print("<").print(accessTargetType).println(">();");
		}
		// value loop
		println("for (int i = 0; i < strArray.length; i++) {");
		if (isArray(returnType)) {
			print(accessFieldName).print("[i] = ");
			printStrArray(factoryMethod);
			println(";");
		} else {
			print(accessFieldName).print(".add(");
			printStrArray(factoryMethod);
			println(");");
		}
		println("}");
		// end of loop
		println("}");
		print("return ").print(accessFieldName).println(";");
	}
	
	private void printStrArray(String factoryMethod) {
		if (factoryMethod != null) print(factoryMethod).print("(");
		print("strArray[i]");
		if (factoryMethod != null) print(")");
	}
	
	private void printSimpleAccessMethodBody(String factoryMethod, String getterMethod, String metaKey,
			GwtPropertiesDefaultValue defaultValue) {
		print("return ");
		if (factoryMethod != null) print(factoryMethod).print("(");
		printGetterMethod(getterMethod, metaKey, defaultValue);
		if (factoryMethod != null) print(")");
		println(";");
	}

	private void printGetterMethod(String getterMethod, String metaKey, GwtPropertiesDefaultValue defaultValue) {
		print(getterMethod).print("()").print(".get(").print("\"").print(metaKey).print("\"");
		if (defaultValue != null) print(", ").print("\"").print(defaultValue.value()).print("\"");
		print(")");
	}
	
	private JType convertToNonPrimitiveType(JType type) {
		JPrimitiveType primitiveType = type.isPrimitive();
		if (primitiveType != null) {
			String primitiveObjectType = primitiveToObjectType(primitiveType.getSimpleSourceName());
			return context.getTypeOracle().findType("java.lang", primitiveObjectType);
		}
		return type;
	}
	
	private String findFactoryMethod(JClassType accessType, JMethod method) {
		String factoryMethod = null;
		if (!accessType.getQualifiedSourceName().equals(String.class.getName())) { // if not String
			factoryMethod = findStringFactoryMethodName(accessType);
			if (factoryMethod == null) {
				throw new RuntimeException("Skipping method " + method.getName() + " - no target String factory method!");
			}
		}
		return factoryMethod;
	}
	
	private String findStringFactoryMethodName(JClassType type) {
		if (type.getQualifiedSourceName().equals(SafeUri.class.getName())) {
			return UriUtils.class.getName() + ".fromString";
		}
		for (JMethod method : type.getMethods()) {
			if (!method.isStatic()) continue;
			if (!method.getReturnType().equals(type)) continue;
			if (method.getParameters().length != 1) continue;
			if (method.getParameters()[0].getType().getQualifiedSourceName().equals(String.class.getName())) 
				return type.getName() + "." + method.getName();
		}
		return null;
	}
	
}
