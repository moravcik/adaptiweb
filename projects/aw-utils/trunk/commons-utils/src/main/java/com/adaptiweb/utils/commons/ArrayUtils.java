package com.adaptiweb.utils.commons;

import java.lang.reflect.Array;

public final class ArrayUtils {

	private ArrayUtils() {}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] merge(T[] array, T...items) {
		T[] result = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length + items.length);
		System.arraycopy(array, 0, result, 0, array.length);
		System.arraycopy(items, 0, result, array.length, items.length);
		return result;
	}
}
