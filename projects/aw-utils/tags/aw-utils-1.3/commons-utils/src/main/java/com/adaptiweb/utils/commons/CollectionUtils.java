package com.adaptiweb.utils.commons;

import java.util.Iterator;


public final class CollectionUtils {

	private CollectionUtils() {}
	
	public static <T> Iterable<T> asIterable(final Iterator<T> iterator) {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return iterator;
			}
		};
	}
}
