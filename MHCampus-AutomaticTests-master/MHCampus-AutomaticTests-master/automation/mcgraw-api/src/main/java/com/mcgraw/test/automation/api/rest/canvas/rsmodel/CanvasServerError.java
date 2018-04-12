package com.mcgraw.test.automation.api.rest.canvas.rsmodel;

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
public class CanvasServerError implements IRestServerError{
	
	@JsonProperty(value = "error_report_id")
	private int errorReportId;
	
	@JsonProperty(value = "message")
	private String message;
	
	@JsonProperty(value = "status")
	private String status;

	public int getErrorReportId() {
		return errorReportId;
	}

	public void setErrorReportId(int errorReportId) {
		this.errorReportId = errorReportId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString(){
		return "Canvas ServerError [errorReportId " + errorReportId + ", message=" + message + ", status=" + status + "]";		
	}
}
