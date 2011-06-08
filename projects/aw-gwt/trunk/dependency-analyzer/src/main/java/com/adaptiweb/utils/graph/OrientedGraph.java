package com.adaptiweb.utils.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * @param <T> type of vertexes in graph
 * @param <V> type of value of edge
 */
public class OrientedGraph<T extends Vertex, V> implements Iterable<OrientedEdge<T,V>>{
	
	private final Map<T, Map<T, OrientedEdge<T,V>>> graph = new HashMap<T, Map<T, OrientedEdge<T,V>>>();
	
	@SuppressWarnings("unchecked")
	public List<OrientedEdge<T, V>> cutToBone() {
		List<OrientedEdge<T, V>> removed = new LinkedList<OrientedEdge<T,V>>();
		
		for(Entry<T,Map<T, OrientedEdge<T,V>>> entry : graph.entrySet()) {
			T fromNode = entry.getKey();
			Object[] nodes = entry.getValue().keySet().toArray();
			
			for(Object toNode : nodes)
				if(existAlternativePath(fromNode, (T) toNode))
					removed.add(entry.getValue().remove(toNode));
		}
		return removed;
	}

	public int markRemoveable(V markValue) {
		List<OrientedEdge<T, V>> removed = cutToBone();
		int counter = 0;
		
		for(OrientedEdge<T, V> edge : removed) {
			edge.setValue(markValue);
			addEdge(edge);
			counter++;
		}
		
		return counter;
	}

	public boolean existAlternativePath(T fromNode, T toNode) {
		Set<T> forbidenNodes = new HashSet<T>();
		
		if(graph.containsKey(fromNode))
			for(T middleNode : graph.get(fromNode).keySet())
				if(!toNode.equals(middleNode)) {
					forbidenNodes.clear();
					forbidenNodes.add(fromNode);
					
					if(existPath(middleNode, toNode, forbidenNodes))
						return true;
				}
		return false;
	}

	public boolean existPath(T fromNode, T toNode, Set<T> forbidenNodes) {
		if(fromNode.equals(toNode))
			return true;
		
		forbidenNodes.add(fromNode);
		
		if(graph.containsKey(fromNode))
			for(T middleNode : graph.get(fromNode).keySet())
				if(!forbidenNodes.contains(middleNode))
					if(existPath(middleNode, toNode, forbidenNodes))
						return true;
			
		return false;
	}

	public void addEdge(T from, T to, V value) {
		addEdge(new OrientedEdge<T,V>(from, to, value));
	}

	public void addEdge(OrientedEdge<T,V> edge) {
		T key = edge.getFromNode();
		
		if(!graph.containsKey(key))
			graph.put(key, new HashMap<T, OrientedEdge<T,V>>());
		
		graph.get(key).put(edge.getToNode(), edge);
	}

	public void clear() {
		graph.clear();
	}

	public Iterator<OrientedEdge<T,V>> iterator() {
		return new Iterator<OrientedEdge<T,V>>() {
			
			final Iterator<Map<T, OrientedEdge<T,V>>> first = graph.values().iterator();
			Iterator<OrientedEdge<T,V>> second = null;

			public boolean hasNext() {
				if(second == null || !second.hasNext()) {
					while(first.hasNext()) {
						second = first.next().values().iterator();
						if(second.hasNext()) return true;
					}
					return false;
				}
				return true;
			}

			public OrientedEdge<T,V> next() {
				return second.next();
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		};
	}
}
