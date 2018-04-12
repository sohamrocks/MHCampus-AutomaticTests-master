package com.mcgraw.test.automation.api.rest.moodle.rsmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mcgraw.test.automation.api.rest.base.IRestServerError;

/**
 * Server error REST response representation. <br>
 * Uses Jackson (2.x) for data-binding
 *
 * @see <a href="http://fasterxml.com/">http://fasterxml.com/</a>
 *
 * @author Andrei_Turavets
 *
 */
@JsonInclude(Include.NON_NULL)
public class MoodleServerError implements IRestServerError{
	
	@JsonProperty(value = "debuginfo")
	private String debuginfo;
	
	@JsonProperty(value = "errorcode")
	private String errorCode;
	
	@JsonProperty(value = "exception")
	private String exception;
	
	@JsonProperty(value = "message")
	private String message;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString(){
		return "Moodle ServerError [debuginfo " + debuginfo + ", errorcode=" + errorCode + ", exception=" + exception
				+ ", message=" + message + "]";		
	}
	
	
	
	
}
