package com.mcgraw.test.automation.framework.core.results.logger;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Level;

/**
 * Common project logger.
 *
 * @author mperepelov
 */
public class Logger {
	@SuppressWarnings("unused")
	private static String getHtmlMessage(String message) {
		return StringEscapeUtils.escapeHtml4(message);
	}

	public static org.apache.log4j.Logger getLogger() {
		return org.apache.log4j.Logger.getRootLogger();
	}

	public static final void fatal(String message) {
		getLogger().fatal(message);
	}

	public static final void fatal(String message, Throwable t) {
		getLogger().fatal(message, t);
	}

	public static final void error(String message) {
		getLogger().error(message);
	}

	public static final void error(String message, Throwable t) {
		getLogger().error(message, t);
	}

	public static final void debug(String message, Throwable t) {
		getLogger().debug(message, t);
	}

	public static final void warn(String message, Throwable t) {
		getLogger().warn(message, t);
	}

	public static final void trace(String message) {
		getLogger().trace(message);
	}

	public static final void trace(String message, Throwable t) {
		getLogger().trace(message, t);
	}

	public static final void info(String message, Throwable t) {
		getLogger().info(message, t);
	}

	public static final void warn(String message) {
		getLogger().warn(message);
	}

	public static final void info(String message) {
		getLogger().info(message);
	}

	public static final void debug(String message) {
		getLogger().debug(message);
	}

	public static final void operation(String message) {
		getLogger().log(LoggerLevel.OPERATION, message);
	}

	public static final void action(String message) {
		getLogger().log(LoggerLevel.ACTION, message);
	}

	public static final void htmlOutput(String message) {
		getLogger().log(LoggerLevel.HTML_OUTPUT, message);
	}

	public static final void log(Level priority, String message) {
		getLogger().log(priority, message);
	}

	public static void shutdown() {
		org.apache.log4j.LogManager.shutdown();
	}

	public static String getStringMessageFromHtml(String htmlMessage) {
		try {
			return htmlMessage.replaceAll("&LT;", "<").replaceAll("&GT;", ">")
					.replaceAll("<BR>", "\n");
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
