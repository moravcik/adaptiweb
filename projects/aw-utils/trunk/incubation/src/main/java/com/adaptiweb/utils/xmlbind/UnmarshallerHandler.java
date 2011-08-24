package com.adaptiweb.utils.xmlbind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.adaptiweb.utils.typeanalyzer.TypeAnalysis;

public class UnmarshallerHandler implements XmlHandler {
	private final UnmarshallMapper mapper;
	private Element root = null;
	private Element current = null;
	
	public UnmarshallerHandler(TypeAnalysis type) {
		mapper = UnmarshallMapper.getInstance(type);
	}

	public void contentString(String content) {
		if(current != null) current.value = content;
	}
	
	@Override
	public void cdataString(String cdata) {
		if(current != null) current.value = cdata;
	}

	public void ignoreableWhitespaces(String chars) {
		if(chars.length() == 0) contentString(chars);
	}

	public void endElement(String tagName) {
		current = current.parent;
	}

	public void beginElement(String tagName, Map<String, String> attrs) {
		current = new Element(current, tagName);
		current.attrs = attrs;
		if(root == null) root = current;
	}

	public void processInstruction(String name, Map<String, String> attrs) {
	}

	public Object done() {
		//System.out.println(root); //DEBUG
		return mapper.map(root);
	}
}

class Element implements XmlSource {
	public final String name;
	public final Element parent;
	public Element next;
	public final int position;
	public final ArrayList<Element> children = new ArrayList<Element>();
	public String value = null;
	public Map<String,String> attrs = new HashMap<String,String>();
	
	public Element(final Element parent, String name) {
		this.parent = parent;
		this.name = name;
		if(parent != null) {
			position = parent.addChild(this);
//			parent.children.add(this);
//			position = parent.count(name);
		}
		else position = 1;
	}
	
	private int addChild(Element element) {
		String name = element.getName();
		for(Element child : children) {
			if(child.name.equals(name)) {
				while(child.next != null) child = child.next;
				child.next = element;
				return child.position + 1;
			}
		}
		
		children.add(element);
		return 1;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(getPath());
		
		if(isComposite()) {
			result.append('\n');
			for(Element child : children) result.append(child.toString());
		}
		else 
			result.append('=').append(value).append('\n');
		
		if(next != null)
			result.append(next.toString());
		return result.toString();
	}
	
	public String getPath() {
		String result = '/' + name + '[' + position + ']';
		return parent != null ? parent.getPath() + result : result;
	}

	public boolean isMultiple() {
		return position > 1 || next != null;
	}
	
	public boolean isComposite() {
		return !children.isEmpty();
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public Element[] getChildren() {
		return children.toArray(new Element[children.size()]);
	}

	public Element getNext() {
		return next;
	}

}
