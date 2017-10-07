package com.noyan.util;

public class ConverterUtil {
	public static int toInt(String text) {
		int result = 0;
		try {
			result = Integer.parseInt(text);
		} catch (Exception e) {
		}

		return result;
	}

	public static long toLong(String text) {
		long result = 0;
		try {
			result = Long.parseLong(text);
		} catch (Exception e) {
		}

		return result;
	}
}
