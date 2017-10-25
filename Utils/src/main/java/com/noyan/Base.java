package com.noyan;

import org.apache.log4j.Logger;

public interface Base {

	default public Logger getLogger() {
		return Logger.getLogger(getClass());
	}

	default public void fatal(Object message) {
		getLogger().fatal(getPrefix() + message);
	}

	default public void info(Object message) {
		getLogger().info(getPrefix() + message);
	}

	default public void warn(Object message) {
		getLogger().warn(getPrefix() + message);
	}

	default public void error(Object message) {
		getLogger().error(getPrefix() + message);
	}

	default public void debug(Object message) {
		getLogger().debug(getPrefix() + message);
	}

	default public void trace(Object message) {
		getLogger().trace(getPrefix() + message);
	}

	default public String getPrefix() {
		return "";
	}

}
