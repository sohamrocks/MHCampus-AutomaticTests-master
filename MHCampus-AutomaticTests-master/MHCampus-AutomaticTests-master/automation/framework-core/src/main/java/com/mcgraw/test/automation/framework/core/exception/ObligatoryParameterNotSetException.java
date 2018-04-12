/**
 * 
 */
package com.mcgraw.test.automation.framework.core.exception;

/**
 * Some exception with Predefined Locators
 * 
 * @author Andrei Varabyeu
 * 
 */
public class ObligatoryParameterNotSetException extends RuntimeException {
	private static final long serialVersionUID = 6107045312907132867L;

	@SuppressWarnings("unused")
	private ObligatoryParameterNotSetException() {
	}

	public ObligatoryParameterNotSetException(String message) {
		super(message);
	}
}
