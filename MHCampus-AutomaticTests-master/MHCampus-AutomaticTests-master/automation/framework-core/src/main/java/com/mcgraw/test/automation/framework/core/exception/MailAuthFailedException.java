package com.mcgraw.test.automation.framework.core.exception;


public class MailAuthFailedException extends RuntimeException {

	public MailAuthFailedException(String string, Throwable exception) {
		super(string, exception);
	}

	private static final long serialVersionUID = 5595165680594616431L;

}
