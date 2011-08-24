package com.adaptiweb.utils.typeanalyzer;

/**
 * There are three types of Types, categorized by this enum.
 * @see TypeAnalysis#getNature()
 */
public enum TypeNature {
	/**
	 * First is atomic (e.g. Boolean, int, ...)
	 */
	ATOMIC,
	/**
	 * Second is Arrays or Collections
	 */
	MULTIPLE,
	/**
	 * Third is Objects
	 */
	STUCTURE
}
