package com.noyan.util;

import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public class RandomUtil {
	public static String generateRandomHexString() {
		return generateHexString(0);
	}

	public static String generateHexString(int length) {
		String uuidString = UUID.randomUUID().toString();
		uuidString = uuidString.replaceAll("[-]", "").toUpperCase(Locale.US);
		
		if (length <= 0) {
			return uuidString;
		}

		if (length > 32) {
			return uuidString;
		}

		return uuidString.substring(0, length);
	}

	/**
	 * 
	 * @return 1*32(HEXDIG)
	 */
	public static String generateHexDigit32() {
		String uuidString = UUID.randomUUID().toString();
		uuidString = uuidString.replaceAll("[-]", "").toUpperCase(Locale.US);

		uuidString = new Random().nextInt(8) + uuidString;

		return uuidString.substring(0, 8);
	}
}
