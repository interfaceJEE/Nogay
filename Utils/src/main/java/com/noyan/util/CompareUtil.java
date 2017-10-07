package com.noyan.util;

import java.util.Locale;

public class CompareUtil {
	public static boolean equal(Object object01, Object object02) {
		if (NullUtil.isAllNull(object01, object02)) {
			return true;
		}

		if (NullUtil.isAnyNull(object01, object02)) {
			return false;
		}

		return object01.equals(object02);
	}

	public static boolean equalIgnoreCase(Object object01, Object object02) {
		if (!(object01 instanceof String) || !(object02 instanceof String)) {
			return equal(object01, object02);
		}

		String string01 = (String) object01;
		String string02 = (String) object02;

		return string01.toUpperCase(Locale.US).equals(string02.toUpperCase(Locale.US));
	}
}
