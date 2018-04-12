package com.mcgraw.test.automation.api.rest.canvas.exception;

import com.mcgraw.test.automation.framework.core.exception.test.CommonTestRuntimeException;

public class SubmissionNotFoundException extends CommonTestRuntimeException {

	private static final long serialVersionUID = 7595811791563634077L;

	public SubmissionNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
