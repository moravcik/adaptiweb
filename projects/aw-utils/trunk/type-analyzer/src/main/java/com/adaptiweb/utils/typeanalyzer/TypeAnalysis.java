package com.adaptiweb.utils.typeanalyzer;

import java.lang.reflect.Type;
import java.util.LinkedList;

/**
 * This interface describes one {@link Type}.
 */
public interface TypeAnalysis {

	/**
	 * @return Described type.
	 */
	public Type getType();
	
	/**
	 * Delegated method. 
	 * @return if this Type is an array it returns type of array items
	 * @see Class#getComponentType()
	 */
	public Type getComponent();
	
	/**
	 * @return the nature of described Type.
	 */
	public TypeNature getNature();

	/**
	 * @return new instance of described Type or null if instance can't be created.
	 */
	public Object createInstance();

	/**
	 * @param list contains component objects which are instance of this Type which
	 * returns {@link #getComponent()}. 
	 * @return instance of described Type initialized by component objects.
	 */
	public Object composite(LinkedList<Object> list);
	
	/**
	 * @param data string representation of described Type.
	 * @return instance of described Type initialized by passed string data
	 * or <code>null</code> if Type can't be created from string. 
	 */
	public Object parse(String data);
}
