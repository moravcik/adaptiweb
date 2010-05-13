package com.adaptiweb.gwt.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ListUtils {
	
	private ListUtils() {}
	
	public interface Collector<X,Y> {
		Y collect(X item);
	}
	
	public static <X,Y> List<Y> collect(Collection<X> list, Collector<X,Y> collector) {
		ArrayList<Y> result = new ArrayList<Y>(list.size());
		for (X item : list) result.add(collector.collect(item));
		return result;
	}

	public <X> List<X> join(Collection<X>...lists) {
		int size = 0;
		for (Collection<X> list : lists) size += list.size();
		List<X> result = new ArrayList<X>(size);
		for (Collection<X> list : lists) result.addAll(list);
		return result;
	}
}
