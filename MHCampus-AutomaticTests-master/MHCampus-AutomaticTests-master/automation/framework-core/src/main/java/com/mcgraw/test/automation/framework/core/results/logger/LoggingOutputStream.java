package com.mcgraw.test.automation.framework.core.results.logger;

import java.io.OutputStream;

import org.apache.log4j.Level;

public class LoggingOutputStream extends OutputStream {

	/** The logger where to log the written bytes. */
	private org.apache.log4j.Logger logger;

	/** The level. */
	private Level level;

	/** The internal memory for the written bytes. */
	private StringBuffer cache;

	private int lastNewLineIndex = 0;

	/**
	 * Creates a new log output stream which logs bytes to the specified logger
	 * with the specified level.
	 *
	 * @param logger
	 *            the logger where to log the written bytes
	 * @param level
	 *            the level
	 */
	public LoggingOutputStream(org.apache.log4j.Logger logger, Level level) {
		setLogger(logger);
		setLevel(level);
		cache = new StringBuffer();
	}

	/**
	 * Sets the logger where to log the bytes.
	 *
	 * @param logger
	 *            the logger
	 */
	public void setLogger(org.apache.log4j.Logger logger) {
		this.logger = logger;
	}

	/**
	 * Returns the logger.
	 *
	 * @return DOCUMENT ME!
	 */
	public org.apache.log4j.Logger getLogger() {
		return logger;
	}

	/**
	 * Sets the logging level.
	 *
	 * @param level
	 *            DOCUMENT ME!
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

	/**
	 * Returns the logging level.
	 *
	 * @return DOCUMENT ME!
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * Writes a byte to the output stream. This method flushes automatically at
	 * the end of a line.
	 *
	 * @param b
	 *            DOCUMENT ME!
	 */
	public synchronized void write(int b) {
		byte[] bytes = new byte[1];
		bytes[0] = (byte) (b & 0xff);
		String symbol = new String(bytes);
		cache.append(symbol);
		if (!symbol.isEmpty() && "\n".equals(symbol)) {
			flushLine();
		}
	}

	private synchronized void flushLine() {
		int length = cache.length();
		logger.log(level, cache.substring(lastNewLineIndex, length - 1));
		lastNewLineIndex = length;
	}

	@Override
	public synchronized String toString() {
		return cache.toString();
	}
}
