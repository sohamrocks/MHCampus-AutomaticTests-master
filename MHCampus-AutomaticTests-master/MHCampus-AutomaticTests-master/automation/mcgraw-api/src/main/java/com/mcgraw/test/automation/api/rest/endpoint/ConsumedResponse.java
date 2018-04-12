package com.mcgraw.test.automation.api.rest.endpoint;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

/**
 * Consumed Http Response
 *
 */
public class ConsumedResponse implements HttpResponse {

	private HttpResponse httpResponse;

	public ConsumedResponse(HttpResponse httpResponse) throws IOException {
		httpResponse
				.setEntity(new ConsumedHttpEntity(httpResponse.getEntity()));
		this.httpResponse = httpResponse;
	}

	public StatusLine getStatusLine() {
		return httpResponse.getStatusLine();
	}

	public void setStatusLine(StatusLine statusline) {
		httpResponse.setStatusLine(statusline);
	}

	public ProtocolVersion getProtocolVersion() {
		return httpResponse.getProtocolVersion();
	}

	public void setStatusLine(ProtocolVersion ver, int code) {
		httpResponse.setStatusLine(ver, code);
	}

	public boolean containsHeader(String name) {
		return httpResponse.containsHeader(name);
	}

	public void setStatusLine(ProtocolVersion ver, int code, String reason) {
		httpResponse.setStatusLine(ver, code, reason);
	}

	public Header[] getHeaders(String name) {
		return httpResponse.getHeaders(name);
	}

	public void setStatusCode(int code) throws IllegalStateException {
		httpResponse.setStatusCode(code);
	}

	public Header getFirstHeader(String name) {
		return httpResponse.getFirstHeader(name);
	}

	public Header getLastHeader(String name) {
		return httpResponse.getLastHeader(name);
	}

	public void setReasonPhrase(String reason) throws IllegalStateException {
		httpResponse.setReasonPhrase(reason);
	}

	public Header[] getAllHeaders() {
		return httpResponse.getAllHeaders();
	}

	public HttpEntity getEntity() {
		return httpResponse.getEntity();
	}

	public void addHeader(Header header) {
		httpResponse.addHeader(header);
	}

	public void addHeader(String name, String value) {
		httpResponse.addHeader(name, value);
	}

	public void setEntity(HttpEntity entity) {
		httpResponse.setEntity(entity);
	}

	public void setHeader(Header header) {
		httpResponse.setHeader(header);
	}

	public void setHeader(String name, String value) {
		httpResponse.setHeader(name, value);
	}

	public Locale getLocale() {
		return httpResponse.getLocale();
	}

	public void setHeaders(Header[] headers) {
		httpResponse.setHeaders(headers);
	}

	public void setLocale(Locale loc) {
		httpResponse.setLocale(loc);
	}

	public void removeHeader(Header header) {
		httpResponse.removeHeader(header);
	}

	public void removeHeaders(String name) {
		httpResponse.removeHeaders(name);
	}

	public HeaderIterator headerIterator() {
		return httpResponse.headerIterator();
	}

	public HeaderIterator headerIterator(String name) {
		return httpResponse.headerIterator(name);
	}

	public HttpParams getParams() {
		return httpResponse.getParams();
	}

	public void setParams(HttpParams params) {
		httpResponse.setParams(params);
	}

	private static class ConsumedHttpEntity extends HttpEntityWrapper {

		private byte[] entityContent;

		public ConsumedHttpEntity(HttpEntity wrapped) throws IOException {
			super(wrapped);
			try {
				entityContent = EntityUtils.toByteArray(wrapped);
			} finally {
				EntityUtils.consumeQuietly(wrapped);
			}
		}

		@Override
		public InputStream getContent() throws IOException {
			return new ByteArrayInputStream(entityContent);
		}

	}

}
