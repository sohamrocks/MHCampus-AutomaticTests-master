package com.mcgraw.test.automation.framework.selenium2.ui.exception;

import com.mcgraw.test.automation.framework.core.exception.test.CommonTestRuntimeException;

public class ScreenFrameNotFoundException extends CommonTestRuntimeException{
	
	private static final long serialVersionUID = -4115625619560862472L;

	public ScreenFrameNotFoundException(String errorMessage) {
		super(errorMessage);
	}
	
	public ScreenFrameNotFoundException(String errorMessage, Throwable t) {
		super(errorMessage, t);
	}

	

}
