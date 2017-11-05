package com.noyan.util;

import java.util.Objects;

public class NullUtil {

	public static boolean isNull(Object object) {
		return Objects.isNull(object);
	}

	public static boolean isNotNull(Object object) {
		return !isNull(object);
	}

	public static boolean isAnyNull(Object... objects) {
		if (isNull(objects)) {
			return true;
		}

		for (Object object : objects) {
			if (isNull(object)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isAllNull(Object... objects) {
		if (isNull(objects)) {
			return true;
		}

		for (Object object : objects) {
			if (isNotNull(object)) {
				return false;
			}
		}

		return true;
	}
}
