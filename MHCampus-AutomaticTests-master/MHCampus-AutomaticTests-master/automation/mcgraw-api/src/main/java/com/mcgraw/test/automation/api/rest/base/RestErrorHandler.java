package com.mcgraw.test.automation.api.rest.base;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.mcgraw.test.automation.api.rest.endpoint.ConsumedResponse;
import com.mcgraw.test.automation.api.rest.endpoint.ErrorHandler;
import com.mcgraw.test.automation.api.rest.endpoint.StatusType;
import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointClientException;
import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointException;
import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;
import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointServerException;

public class RestErrorHandler implements ErrorHandler<ConsumedResponse> {

	@Override
	public boolean hasError(ConsumedResponse rs) {
		try {
			return (isHttpStatusError(rs) || isResponseContainsErrorInBody(rs));
		} catch (IOException e) {
			return true;
		}
	}

	@Override
	public void handle(ConsumedResponse response) throws RestEndpointIOException {
		if (!hasError(response)) {
			return;
		}

		StatusType statusType = StatusType.valueOf(response.getStatusLine().getStatusCode());
		int statusCode = getStatusCode(response);
		String statusMessage = response.getStatusLine().getReasonPhrase();
		byte[] errorBody = getResponseBody(response);

		switch (statusType) {
		case CLIENT_ERROR:
			handleClientError(statusCode, statusMessage, errorBody);
			break;
		case SERVER_ERROR:
			handleServerError(statusCode, statusMessage, errorBody);
			break;
		default:
			handleDefaultError(statusCode, statusMessage, errorBody);
			break;
		}

	}

	/**
	 * Handler methods for HTTP client errors
	 * 
	 * @param statusCode
	 *            - HTTP status code
	 * @param statusMessage
	 *            - HTTP status message
	 * @param errorBody
	 *            - HTTP response body
	 * @throws RestEndpointIOException
	 */
	protected void handleClientError(int statusCode, String statusMessage, byte[] errorBody) throws RestEndpointIOException {
		throw new RestEndpointClientException(statusCode, statusMessage, errorBody);
	}

	/**
	 * Handler methods for HTTP server errors
	 * 
	 * @param statusCode
	 *            - HTTP status code
	 * @param statusMessage
	 *            - HTTP status message
	 * @param errorBody
	 *            - HTTP response body
	 * @throws RestEndpointIOException
	 */
	protected void handleServerError(int statusCode, String statusMessage, byte[] errorBody) throws RestEndpointIOException {
		throw new RestEndpointServerException(statusCode, statusMessage, errorBody);
	}

	/**
	 * Handler methods for unclassified errors
	 * 
	 * @param statusCode
	 *            - HTTP status code
	 * @param statusMessage
	 *            - HTTP status message
	 * @param errorBody
	 *            - HTTP response body
	 * @throws RestEndpointIOException
	 */
	protected void handleDefaultError(int statusCode, String statusMessage, byte[] errorBody) throws RestEndpointIOException {
		throw new RestEndpointException(statusCode, statusMessage, errorBody);
	}

	private int getStatusCode(ConsumedResponse response) {
		return response.getStatusLine().getStatusCode();
	}

	protected boolean isHttpStatusError(ConsumedResponse response) {
		int statusCode = getStatusCode(response);
		int series = statusCode / 100;
		return ((series == 4) || (series == 5));
	}

	protected boolean isResponseContainsErrorInBody(ConsumedResponse response) throws IllegalStateException, IOException {
		return IOUtils.toString(response.getEntity().getContent()).contains("error");
	}

	private byte[] getResponseBody(HttpResponse response) throws RestEndpointIOException {
		HttpEntity entity = null;
		try {
			entity = response.getEntity();
			return EntityUtils.toByteArray(response.getEntity());
		} catch (IOException e) {
			throw new RestEndpointIOException("Unable to read body from error", e);
		} finally {
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				throw new RestEndpointIOException("Unable to consume response entity", e);
			}
		}

	}
}
