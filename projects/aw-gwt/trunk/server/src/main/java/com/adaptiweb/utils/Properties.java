package com.adaptiweb.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.adaptiweb.exeptions.ResourceErrorException;


public class Properties implements Iterable<String> {
	
	private final Map<String,String> properties;
	private final String prefix;
	
	public Properties(Class<?> typePattern) throws ResourceErrorException {
		this(typePattern.getSimpleName(), typePattern.getPackage(), typePattern.getClassLoader(), "ISO-8859-1");
	}
	
	public Properties(Class<?> typePattern, String charset) throws ResourceErrorException {
		this(typePattern.getSimpleName(), typePattern.getPackage(), typePattern.getClassLoader(), charset);
	}
	
	public Properties(String resourceName, Class<?> packageNeighbour) throws ResourceErrorException {
		this(resourceName, packageNeighbour.getPackage(), packageNeighbour.getClassLoader(), "ISO-8859-1");
	}
	
	public Properties(String resourceName, Class<?> packageNeighbour, String charset) throws ResourceErrorException {
		this(resourceName, packageNeighbour.getPackage(), packageNeighbour.getClassLoader(), charset);
	}
	
	public Properties(String resourceName, Package localtion, ClassLoader loader, String charset) throws ResourceErrorException {
		properties = Collections.synchronizedMap(new LinkedHashMap<String, String>());
		prefix = "";
		
		if(!resourceName.endsWith(".properties")) resourceName += ".properties";
		resourceName = localtion == null ? resourceName :
				localtion.getName().replace('.', '/').concat("/").concat(resourceName);
		
		URL resource = loader.getResource(resourceName);
		if(resource == null)
			throw new IllegalArgumentException("Can't find resource " + resourceName);
		
		InputStream resourceStream = null;
		java.util.Properties aux = new java.util.Properties();
		
		try {
			aux.load(new InputStreamReader(resourceStream = resource.openStream(), charset));
		} catch(IOException e) {
			throw new ResourceErrorException(e, resource.toString());
		} finally {
			try {
			    if (resourceStream != null) resourceStream.close();
			} catch (IOException ignore) {}
		}
		
		Enumeration<?> enumerator = aux.propertyNames();
		while(enumerator.hasMoreElements()) {
			String key = (String) enumerator.nextElement();
			this.properties.put(key, aux.getProperty(key));
		}
	}
	
	private Properties(String prefix, Map<String,String> properties) {
		this.properties = properties;
		this.prefix = prefix.length() == 0 || prefix.endsWith(".") ? prefix : prefix + ".";
	}
	
	public String getProperty(String key, String defaultValue) {
		key = prefix + key;
		return properties.containsKey(key) ? properties.get(key) : defaultValue;
	}

	public String getProperty(String key) {
		key = prefix + key;
		return properties.get(key);
	}

	public String setProperty(String key, String value) {
		key = prefix + key;
		return properties.put(key, value);
	}

	public String removeProperty(String key) {
		key = prefix + key;
		return properties.remove(key);
	}
	
	public String getDefaultProperty(String defaultValue) {
		String key = location();
		return properties.containsKey(key) ? properties.get(key) : defaultValue;
	}

	public String getDefaultProperty() {
		return properties.get(location());
	}

	public String setDefaultProperty(String value) {
		return properties.put(location(), value);
	}

	public String removeDefaultProperty() {
		return properties.remove(location());
	}
	
	public String location() {
		return prefix.length() == 0 ? null : prefix.substring(0, prefix.length() - 1);
	}
	
	public Properties select(String...prefix) {
		StringBuffer sb = new StringBuffer(this.prefix);
		for(String part : prefix) sb.append(part).append('.');
		return new Properties(sb.toString(), properties);
	}

	public Properties parent() {
		if(prefix.length() == 0) return null;
		int index = prefix.lastIndexOf('.', prefix.lastIndexOf('.') - 1);
		return new Properties(index == -1 ? "" : prefix.substring(0, index), properties);
	}

	public Properties top() {
		return prefix.length() == 0 ? this : new Properties("", properties);
	}

	public Iterator<String> iterator() {
		return new Iterator<String>() {
			Iterator<String> iterator = properties.keySet().iterator();
			String next;

			public boolean hasNext() {
				do {
					if(!iterator.hasNext()) return false;
					next = iterator.next();
				} while(!next.startsWith(prefix));
				return true;
			}

			public String next() {
				return next.substring(prefix.length());
			}

			public void remove() {
				iterator.remove();
			}
		};
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(obj instanceof Properties == false) return false;
		Properties other = (Properties) obj;
		return this.properties == other.properties
			&& this.prefix.equals(other.prefix);
	}

	@Override
	public int hashCode() {
		int result = 7;
		result = result * 31 + System.identityHashCode(properties);
		result = result * 31 + prefix.hashCode();
		return result;
	}

	public String reconstructPropertyDefinition(String key) {
		key = prefix + key;
		return key + "=" + properties.get(key);
	}

	public String selectProperty(String...keyParts) {
		StringBuffer key = new StringBuffer();
		for(String part : keyParts) key.append('.').append(part);
		return getProperty(key.toString().substring(1));
	}

	public int size() {
		if(prefix.length() == 0) return properties.size();
		int result = 0;
		for(Iterator<String> i = iterator(); i.hasNext(); i.next()) result++;
		return result;
	}
	
}
