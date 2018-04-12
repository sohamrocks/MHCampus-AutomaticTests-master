package com.mcgraw.test.automation.framework.core.timing;

/**
 * Representation of destination for logging<br>
 *
 * @author Andrei Varabyeu
 *
 */
public interface Flushable {

	/**
	 * Flushes message
	 *
	 * @param message
	 *            - message to be flushed
	 */
	void flush(String message);

}
