package com.adaptiweb.gwt.i18n;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.NotFoundException;

import com.adaptiweb.utils.commons.Properties;
import com.google.gwt.i18n.client.LocalizableResource.Key;


public final class I18NSupport { // TODO spring-based refactoring needed
	
	private final Map<String, Properties> labels = new HashMap<String, Properties>();
	
	private final ThreadLocal<String[]> preferedLocales = new ThreadLocal<String[]>();

	private final String resourceBaseName;
	
	private final Class<?> neighbourClass;
	
	public I18NSupport(String resourceBaseName, Class<?> neighbourClass) {
		this.resourceBaseName = resourceBaseName;
		this.neighbourClass = neighbourClass;
	}

	public I18NSupport(Class<?> i18nInterfaceClass) {
		this.resourceBaseName = i18nInterfaceClass.getSimpleName();
		this.neighbourClass = i18nInterfaceClass;
	}

	public String[] getPreferedLocales() {
		return preferedLocales.get();
	}
	
	public void setPreferedLocales(String...preferedLocales) {
		this.preferedLocales.set(preferedLocales);
	}

	public void removePreferedLocales() {
		preferedLocales.remove();
	}

	public String getActiveLocale() {
		if (preferedLocales.get() != null) {
			for (String locale : preferedLocales.get()) {
				Properties result = getProperties(locale);
				if (result != null) return locale;
			}
		}
		return null;
	}

	public String get(String key, Object...params) {
		String result = getProperties().getProperty(key);
		if (result == null) result = getProperties(null).getProperty(key);
		return subst(result, params);
	}
	
	private static Pattern PARAM_FORMAT = Pattern.compile("\\{(\\d+)\\}");
	
	public static String subst(String format, Object[] params) {
		if (params.length == 0 || format == null) return format;
		
		Matcher m = PARAM_FORMAT.matcher(format);
		StringBuffer result = new StringBuffer();
		
		while (m.find()) {
			int paramIndex = Integer.parseInt(m.group(1));
			m.appendReplacement(result, paramIndex <= params.length ? 
					String.valueOf(params[paramIndex]) : m.group());
		}
		
		m.appendTail(result);
		return result.toString();
	}

	protected Properties getProperties() {
		return getProperties(getActiveLocale());
	}
	
	protected Properties getProperties(String locale) {
		if (!labels.containsKey(locale)) {
			labels.put(locale, loadProperties(locale));
		}
		return labels.get(locale);
	}

	protected Properties loadProperties(String locale) {
		String resourceName = getResourceName(locale);
//		System.out.println("loading i18n resource: " + resourceName);
		try {
			return new Properties(resourceName, getNeighbourClass(), "UTF-8");
		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
			return null;
		}
	}

	protected Class<?> getNeighbourClass() {
		return neighbourClass;
	}

	protected String getResourceName(String locale) {
		return locale != null ? getResourceBaseName() + "_" + locale : getResourceBaseName();
	}

	protected String getResourceBaseName() {
		return resourceBaseName;
	}

	public static <T> T createLabels(Class<? extends T> langDef, Class<?> langManager, String staticMethodName) {
		try {
			ClassPool pool = ClassPool.getDefault();
			pool.insertClassPath(new ClassClassPath(langDef));

			CtClass ctClass = pool.makeClass(langManager.getName() + "$$" + langDef.getSimpleName());
			ctClass.addConstructor(CtNewConstructor.defaultConstructor(ctClass));
			CtClass ctLabels = pool.get(langDef.getName());
			ctClass.addInterface(ctLabels);
			
			for (CtMethod m : ctLabels.getDeclaredMethods()) {
				for (Object a : m.getAnnotations()) {
					if (a instanceof Key) {
						String key = ((Key) a).value();
						
						StringBuilder body = new StringBuilder();
						body.append("{ return ").append(langManager.getName())
							.append(".").append(staticMethodName)
							.append("(\"").append(key).append("\", $args); }");
						
						ctClass.addMethod(CtNewMethod.make(m.getReturnType(), m.getName(),
								m.getParameterTypes(), m.getExceptionTypes(), body.toString(), ctClass));
					}
				}
			}
			Class<?> newClass = ctClass.toClass();
			ctClass.detach();
			return langDef.cast(newClass.newInstance());
		} catch (CannotCompileException e) {
			throw new RuntimeException(e);
		} catch (NotFoundException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
