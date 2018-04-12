package com.mcgraw.test.automation.framework.core.timing;

/**
 * Base exception for Waitable's
 *
 * @author Andrei Varabyeu
 *
 */
public class WaitException extends RuntimeException {

	private static final long serialVersionUID = -4400683256410277146L;

	public WaitException(Throwable throwable) {
		super(throwable);
	}

	public WaitException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
