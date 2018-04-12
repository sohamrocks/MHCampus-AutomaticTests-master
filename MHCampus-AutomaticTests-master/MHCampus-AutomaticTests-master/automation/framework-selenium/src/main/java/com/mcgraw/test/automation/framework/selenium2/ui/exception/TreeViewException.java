package com.mcgraw.test.automation.framework.selenium2.ui.exception;

import com.mcgraw.test.automation.framework.core.exception.test.CommonTestRuntimeException;

/**
 * Exception related to TreeView UI element
 * 
 * @author Andrei Varabyeu
 * 
 */
public class TreeViewException extends CommonTestRuntimeException {

	private static final long serialVersionUID = -8992484614931685876L;

	public TreeViewException(String errorMessage) {
		super(errorMessage);
	}

	public TreeViewException(String errorMessage, Throwable e) {
		super(errorMessage, e);
	}

}
