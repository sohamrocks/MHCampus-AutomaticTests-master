package com.mcgraw.test.automation.api.rest.canvas.exception;

import com.mcgraw.test.automation.framework.core.exception.test.CommonTestRuntimeException;

/**
 * Assignment not found exception
 * 
 * @author Andrei_Turavets
 *
 */
public class AssignmentNotFound extends CommonTestRuntimeException {

	private static final long serialVersionUID = 5198961324143274459L;

	public AssignmentNotFound (String errorMessage){
		super(errorMessage);
	}
}
