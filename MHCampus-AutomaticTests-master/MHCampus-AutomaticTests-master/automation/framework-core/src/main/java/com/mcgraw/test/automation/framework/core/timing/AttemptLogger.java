package com.mcgraw.test.automation.framework.core.timing;

/**
 * Inteface for customization of logging of wait attempts in the
 * {@link com.mcgraw.test.automation.framework.core.timing.SmartWait<T>}
 *
 * @author Andrei Varabyeu
 *
 */
public interface AttemptLogger {

	/**
	 * Logging of attempt
	 *
	 * @param attempt
	 *            - Count of waiter's attempt
	 * @param timer
	 *            - Timer representation
	 * @param description
	 *            - Wait description
	 */
	void logAttempt(int attempt, Timer timer, String description);
}
