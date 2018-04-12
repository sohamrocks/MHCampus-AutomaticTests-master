package com.mcgraw.test.automation.api.rest.endpoint.exception;

/**
 * Serializer Exception. <br>
 * Throwed from {@link com.mcgraw.test.automation.api.rest.endpoint.Serializer}
 * implementations
 *
 */
public class SerializerException extends RestEndpointIOException {

	private static final long serialVersionUID = 1L;

	public SerializerException(String message) {
		super(message);
	}

	public SerializerException(String message, Throwable e) {
		super(message, e);
	}
}
