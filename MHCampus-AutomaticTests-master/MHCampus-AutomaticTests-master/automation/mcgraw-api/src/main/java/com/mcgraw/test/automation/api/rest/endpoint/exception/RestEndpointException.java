package com.mcgraw.test.automation.api.rest.endpoint.exception;

/**
 * Base HTTP error representation
 * 
 */
public class RestEndpointException extends RuntimeException {

	private static final long serialVersionUID = 728718628763519460L;

	/** HTTP Status Code */
	protected int statusCode;

	/** HTTP Status Message */
	protected String statusMessage;

	/** HTTP Response Body */
	protected byte[] content;

	public RestEndpointException(int statusCode, String statusMessage,
			byte[] content) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.content = content;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		StringBuilder builder = new StringBuilder();
		builder.append("REST error occured\n").append("Status code: ")
				.append(statusCode).append("\n").append("Status message: ")
				.append(statusMessage).append("\n").append("Message body: ").append(new String(content)).append("\n");
		return builder.toString();
	}

}
