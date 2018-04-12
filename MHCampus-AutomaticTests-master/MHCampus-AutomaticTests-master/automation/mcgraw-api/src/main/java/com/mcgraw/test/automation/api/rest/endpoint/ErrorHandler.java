package com.mcgraw.test.automation.api.rest.endpoint;

import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;

/**
 * Error Handler for RestEndpoint
 *
 * @param <RS>
 *            - Type of Response
 */
public interface ErrorHandler<RS> {

	/**
	 * Checks whether there is an error in response
	 * 
	 * @param rs
	 * @return
	 */
	boolean hasError(RS rs);

	/**
	 * Handles response if there is an error
	 * 
	 * @param rs
	 * @throws RestEndpointIOException
	 */
	void handle(RS rs) throws RestEndpointIOException;
}
