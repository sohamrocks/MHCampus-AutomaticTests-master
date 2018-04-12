package com.mcgraw.test.automation.framework.selenium2.ui.exception;

import com.mcgraw.test.automation.framework.core.exception.test.CommonTestRuntimeException;

/**
 * Base Exception for {@link com.mcgraw.test.automation.framework.selenium2.ui.SafeScreen}
 * implementations
 *
 * @author Andrei Varabyeu
 *
 */
public class ScreenIsNotOpenedException extends CommonTestRuntimeException {

	private static final long serialVersionUID = -6144644166361932520L;

	public ScreenIsNotOpenedException(String message) {
		super(message);
	}

	public ScreenIsNotOpenedException(String message, Throwable e) {
		super(message, e);
	}

}
