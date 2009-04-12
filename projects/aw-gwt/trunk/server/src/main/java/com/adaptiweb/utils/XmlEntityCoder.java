package com.adaptiweb.utils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlEntityCoder {
	
	private static final HashMap<String, Character> entity2char = new HashMap<String, Character>();
	private static final HashMap<Character, String> char2entity = new HashMap<Character, String>();
	private static final Pattern pattern = Pattern.compile("&(?:([a-z]+)|#(x?[0-9]+));");
	
	static {
		Properties properties = new Properties(XmlEntityCoder.class);
		for(String name : properties) {
			Character value = properties.getProperty(name).charAt(0);
			entity2char.put(name, value);
			char2entity.put(value, name);
		}
	}
	
	public static String decode(String content) {
		
		StringBuffer buff = new StringBuffer(content.length());
		
		Matcher m = pattern.matcher(content);
		
		while(m.find()) {
			String replacement = m.group(1) != null ?
					entity2char.get(m.group(1)).toString() : 
					new String(Character.toChars(m.group(2).startsWith("x") ? 
							Integer.parseInt(m.group(2).substring(1), 16) : Integer.parseInt(m.group(2))));
			
			m.appendReplacement(buff, replacement);
		}
		m.appendTail(buff);
		
		return buff.toString();
	}
	
	public static String encode(String content) {
		StringBuffer buff = new StringBuffer(content);
		for(int i = content.length() - 1; i >= 0; i--) {
			Character c = buff.charAt(i);
			if(char2entity.containsKey(c))
				buff.replace(i, i + 1, '&' + char2entity.get(c) + ';');
		}
		return buff.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(decode("&#100;&#x64;"));
	}	
}
