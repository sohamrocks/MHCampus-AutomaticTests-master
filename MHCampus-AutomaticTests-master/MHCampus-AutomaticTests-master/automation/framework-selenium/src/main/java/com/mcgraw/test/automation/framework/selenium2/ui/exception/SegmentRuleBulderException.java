package com.mcgraw.test.automation.framework.selenium2.ui.exception;

import com.mcgraw.test.automation.framework.core.exception.test.CommonTestRuntimeException;

/**
 * Segment Rule Builder exception
 *
 * @author Andrei Varabyeu
 *
 */
public class SegmentRuleBulderException extends CommonTestRuntimeException {

	private static final long serialVersionUID = -7735445285964577739L;

	public SegmentRuleBulderException(String errorMessage) {
		super(errorMessage);
	}

	public SegmentRuleBulderException(String errorMessage, Throwable e) {
		super(errorMessage, e);
	}
}
