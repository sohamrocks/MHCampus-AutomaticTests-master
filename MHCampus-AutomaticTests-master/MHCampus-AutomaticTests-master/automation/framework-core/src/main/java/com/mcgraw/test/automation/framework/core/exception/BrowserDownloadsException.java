package com.mcgraw.test.automation.framework.core.exception;

/**
 * Base exception related to Downloads folder exceptions
 *
 * @author Andrei Varabyeu
 *
 */
public class BrowserDownloadsException extends RuntimeException {

	private static final long serialVersionUID = 8161080669552874744L;

	public BrowserDownloadsException(String message) {
		super(message);
	}

	public BrowserDownloadsException(String message, Throwable e) {
		super(message, e);
	}

}
