package com.adaptiweb.tools.test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


public final class MockitoExt {

	private MockitoExt() {}

	public static <T> Answer<Iterator<T>> iterator(T...items) {
		final List<T> list = Arrays.asList(items);

		return new Answer<Iterator<T>>() {
			public Iterator<T> answer(InvocationOnMock invocation) throws Throwable {
				return list.iterator();
			}
		};
	}
}
