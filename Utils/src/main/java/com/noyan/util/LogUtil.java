package com.noyan.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtil {
	public static void logMessage(Class<?> clazz, String message) {
		logMessage(clazz, Level.INFO, message);
	}

	public static void logMessage(Class<?> clazz, Level level, String message) {
		Logger.getLogger(clazz.getName()).log(level, message);

	}

	public static void logMessage(Object object, String message) {
		logMessage(object.getClass(), Level.INFO, message);
	}
	
}
