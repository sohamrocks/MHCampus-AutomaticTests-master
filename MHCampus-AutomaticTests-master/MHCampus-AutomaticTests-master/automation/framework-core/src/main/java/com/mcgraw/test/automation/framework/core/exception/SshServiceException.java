package com.mcgraw.test.automation.framework.core.exception;

public class SshServiceException extends Exception {

	private static final long serialVersionUID = -1835070182615838582L;

	public SshServiceException(String errorMessage) {
		super(errorMessage);
	}

	public SshServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
