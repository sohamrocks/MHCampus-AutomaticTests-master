package com.mcgraw.test.automation.api.rest.base.exception;

import org.apache.http.Header;
import org.springframework.web.client.RestClientException;

public class ServerErrorException extends RestClientException {

	private static final long serialVersionUID = -4137124946291180324L;

	/** HTTP Status */
	private int status;

	private String statusText;

	/** HTTP Headers */
	private Header[] headers;

	private String responseBody;

	public ServerErrorException(int status, String statusText,
			Header[] headers, String responseBody) {
		super(buildMessage(status, statusText, headers, responseBody));
		this.headers = headers;
		this.status = status;
		this.responseBody = responseBody;
		this.statusText = statusText;
	}

	public int getStatus() {
		return status;
	}

	public String getStatusText() {
		return statusText;
	}

	public Header[] getHeaders() {
		return headers;
	}

	public String getResponseBody() {
		return responseBody;
	}

	private static String buildMessage(int status, String statusText,
			Header[] headers, String body) {
		StringBuilder builder = new StringBuilder();
		builder.append("Status:");
		builder.append(status);
		builder.append(" ");
		builder.append(statusText);
		builder.append("\n");
		builder.append("-----------------\n");
		builder.append("Headers:");
		builder.append("\n");
		for (Header header : headers) {
			builder.append(header.getName());
			builder.append(":");
			builder.append(header.getValue());
			builder.append("\n");
		}
		builder.append("-----------------\n");
		builder.append("Server error:");
		builder.append(body);
		builder.append("End of error");
		return builder.toString();
	}
}
