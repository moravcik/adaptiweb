package com.adaptiweb.utils.commons;

import java.util.Arrays;


public final class StringUtils {
	
	private StringUtils() {}
	
	public static String trim(String str, CharSequence trimingCharacters) {
		char[] tc = asSortedCharArray(trimingCharacters);
		return trimLeft(trimRight(str, tc), tc); 
	}

	private static String trimLeft(String str, char[] sortedCharArray) {
		int i = 0;
		while(i < str.length() && Arrays.binarySearch(sortedCharArray, str.charAt(i)) > -1) i++;
		return i == 0 ? str : str.substring(i);
	}

	public static String trimLeft(String str, CharSequence trimingCharacters) {
		return trimLeft(str, asSortedCharArray(trimingCharacters)); 
	}

	public static String trimRight(String str, CharSequence trimingCharacters) {
		return trimRight(str, asSortedCharArray(trimingCharacters)); 
	}

	private static String trimRight(String str, char[] sortedCharArray) {
		int i = str.length();
		while(i > 0 && Arrays.binarySearch(sortedCharArray, str.charAt(i - 1)) > -1) i--;
		return i == str.length() ? str : str.substring(0, i);
	}

	public static char[] asSortedCharArray(CharSequence chars) {
		char[] tc = new char[chars.length()];
		for(int i = 0; i < tc.length; i++) tc[i] = chars.charAt(i);
		Arrays.sort(tc);
		return tc;
	}

	public static long parseIP(String ip) {
		long result = 0;
		int aux = 0;
		
		for (char c : ip.toCharArray()) {
			if (c == '.') {
				result = result << 8 | aux & 0xFF;
				aux = 0;
			}
			else {
				aux *= 10;
				aux += c - '0';
			}
		}
		
		return result << 8 | aux & 0xFF;
	}
	
	public static String formatIP(long ip) {
		return (ip >> 24) + "."
			+ (ip >> 16 & 0xFF) + "."
			+ (ip >> 8 & 0xFF) + "."
			+ (ip & 0xFF);
	}
}
