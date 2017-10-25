package com.noyan.util.log;

import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Function;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class Log {

	public static ConsoleAppender createConsoleAppender(String pattern) {
		try {
			ConsoleAppender consoleAppender = new ConsoleAppender();
			consoleAppender.setLayout(getPatternLayout.apply(pattern));
			consoleAppender.activateOptions();
			return consoleAppender;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static FileAppender addFileAppender(String pathAndFilename, String pattern) {
		try {
			FileAppender fileAppender = new FileAppender();
			if (pathAndFilename.endsWith(".log")) {
				pathAndFilename = pathAndFilename.substring(0, pathAndFilename.indexOf(".log")) + "-" + LocalDate.now() + ".log";
			}
			if (pathAndFilename.endsWith(".txt")) {
				pathAndFilename = pathAndFilename.substring(0, pathAndFilename.indexOf(".txt")) + "-" + LocalDate.now() + ".txt";
			}
			fileAppender.setFile(pathAndFilename, true, false, 8 * 1024);
			fileAppender.setLayout(getPatternLayout.apply(pattern));
			fileAppender.activateOptions();
			return fileAppender;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Function<String, PatternLayout> getPatternLayout = (pattern) -> {
		if (Objects.isNull(pattern)) {
			pattern = "%-7p %d [%t] %c %x - %m%n";
		}
		PatternLayout layout = new PatternLayout();
		layout.setConversionPattern(pattern);
		return layout;

	};

	public static void main(String[] args) {
		Logger.getRootLogger().setLevel(Level.ALL);
		Logger.getRootLogger().addAppender(createConsoleAppender(null));
		Logger.getLogger(Log.class).debug("Test 123");
	}

}
