package com.adaptiweb.utils.graph;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class GraphHierarchy {
	private Map<String, GraphHierarchy> subGraph = new HashMap<String, GraphHierarchy>();
	private Set<Vertex> nodes = new HashSet<Vertex>();
	private final String label;
	
	private GraphHierarchy(String label) {
		this.label = label;
	}
	
	public GraphHierarchy() {
		this.label = null;
	}

	public void add(Vertex node) {
		add(node, 0);
	}
	
	private void add(Vertex node, int pathIndex) {
		if(pathIndex < node.getPath().length) {
			String subGraphLabel = node.getPath()[pathIndex];
			
			if(!subGraph.containsKey(subGraphLabel))
				subGraph.put(subGraphLabel, new GraphHierarchy(subGraphLabel));
			
			subGraph.get(subGraphLabel).add(node, pathIndex + 1);
		}
		else {
			nodes.add(node);
		}
	}
	
	public void clear() {
		subGraph.clear();
		nodes.clear();
	}
	
	public void writeTo(Appendable out) throws IOException {
		int i = 0;
		for(GraphHierarchy g : subGraph.values())
			g.writeTo(String.valueOf(++i), "\t" , out);
		for(Vertex node : nodes)
			out.append('\t').append(node.toDefinitionString()).append(";\n");
	}

	private void writeTo(String subGraphIndex, String prefix, Appendable out) throws IOException {
		String indent = prefix + "\t";
		out.append(prefix).append("subgraph cluster_").append(subGraphIndex).append(" {\n");
		out.append(indent).append("label=\"").append(label).append("\";\n");
		int i = 0;
		for(GraphHierarchy g : subGraph.values())
			g.writeTo(subGraphIndex + "_" + ++i, indent , out);
		for(Vertex node : nodes)
			out.append(indent).append(node.toDefinitionString()).append(";\n");
		out.append(prefix).append("}\n");
	}
	
}
