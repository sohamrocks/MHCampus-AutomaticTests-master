package com.mcgraw.test.automation.framework.core.timing;

/**
 * Time exceeded exception <br>
 * Throwed from {@link com.mcgraw.test.automation.framework.core.timing.SmartWait<T>}
 *
 * @author Andrei Varabyeu
 *
 */
public class TimeoutException extends WaitException {

	private static final long serialVersionUID = 4155150653245963345L;

	public TimeoutException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
