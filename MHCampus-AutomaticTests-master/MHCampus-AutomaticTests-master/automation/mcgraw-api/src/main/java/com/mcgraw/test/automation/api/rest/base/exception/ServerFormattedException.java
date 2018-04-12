package com.mcgraw.test.automation.api.rest.base.exception;

import org.apache.http.Header;

import com.mcgraw.test.automation.api.rest.base.IRestServerError;

/**
 * General exception for rest api which uses IRestServerError object
 * to get response body
 * 
 * @author Andrei_Turavets
 *
 */
public class ServerFormattedException extends ServerErrorException {

	private static final long serialVersionUID = -4871800125634002161L;
	
	private IRestServerError restServerError;

	public ServerFormattedException(int status, String statusText,
			Header[] headers, IRestServerError restServerError) {
		super(status, statusText, headers, restServerError.toString());
		this.restServerError = restServerError;
	}

	public IRestServerError getRestServerError() {
		return restServerError;
	}
	
	
	
}
