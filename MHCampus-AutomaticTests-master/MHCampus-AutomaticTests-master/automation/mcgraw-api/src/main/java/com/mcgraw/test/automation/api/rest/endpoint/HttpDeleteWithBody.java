package com.mcgraw.test.automation.api.rest.endpoint;

import java.net.URI;

import org.apache.http.client.methods.HttpPost;

/**
 * Custom HttpDelete request which extends
 * HttpPost and allow to use body parameters
 * 
 * @author Andrei_Turavets
 *
 */
public class HttpDeleteWithBody extends HttpPost {
	
	public static final String METHOD_NAME = "DELETE";
	public HttpDeleteWithBody() {
		super();
	}

	public HttpDeleteWithBody(URI uri) {
		super();
		setURI(uri);
	}

	public HttpDeleteWithBody(String uri) {
		super();
		setURI(URI.create(uri));
	}

	@Override
	public String getMethod() {
		return METHOD_NAME;
	}

}
