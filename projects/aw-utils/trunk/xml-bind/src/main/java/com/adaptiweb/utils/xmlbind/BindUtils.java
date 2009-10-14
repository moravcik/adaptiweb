package com.adaptiweb.utils.xmlbind;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adaptiweb.utils.typeanalyzer.TypeAnalyzer;

public abstract class BindUtils {

	private final static Pattern xmlNamePattern = Pattern.compile("-([a-z])");

	public static String toJavaName(String xmlName) {
		Matcher m = xmlNamePattern.matcher(xmlName);
		StringBuffer result = new StringBuffer();
		while(m.find())
			m.appendReplacement(result, m.group(1).toUpperCase());
		m.appendTail(result);
		result.setCharAt(0, Character.toUpperCase(result.charAt(0)));
		return result.toString();
	}

	public static String toXmlName(String javaName) {
		StringBuffer result = new StringBuffer();
		
		for(int  i = 0, n = javaName.length(); i < n; i++) {
			char c = javaName.charAt(i);
			if(i == 0)
				result.append(n == 1 || !Character.isUpperCase(javaName.charAt(i + 1)) ? Character.toLowerCase(c) : c);
			else if(Character.isUpperCase(c) && !Character.isUpperCase(javaName.charAt(i - 1))) 
				result.append('-').append(Character.toLowerCase(c));
			else
				result.append(c);
		}
		return result.toString();
	}

	public static <T> T unmarshal(File file, String characterSetName, Class<T> targetType) throws IOException {
		InputStream is = new FileInputStream(file);
		try {
			return unmarshal(is, characterSetName, targetType);
		} finally {
			try {
				is.close();
			} catch (IOException ignore) {
			}
		}
	}
	
	public static <T> T unmarshal(InputStream is, String characterSetName, Class<T> targetType) throws IOException {
		return unmarshal(new InputStreamReader(is, characterSetName), targetType);
	}

	public static <T> T unmarshal(Reader reader, Class<T> targetType) throws IOException {
		UnmarshallerHandler handler = new UnmarshallerHandler(TypeAnalyzer.examineType(targetType));
		return targetType.cast(XmlParser.parse(reader, handler));
	}
	
	public static <T> T unmarshal(String xmlFragment, Class<T> targetType) throws IOException {
		return unmarshal(new StringReader(xmlFragment), targetType);
	}

	public static String marshall(Object source) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			
			new Marshaller().marshal(source, os);
			
			os.flush();
			os.close();
			
			return new String(os.toByteArray(), "UTF-8");
		} catch (ParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
