package com.mcgraw.test.automation.api.rest.endpoint;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;

/**
 * Interface of endpoint of REST web service
 * 
 */
public interface RestEndpoint {

	<RS> RS post(String resource, List<NameValuePair> params, Class<RS> clazz) throws RestEndpointIOException, UnsupportedEncodingException;

	<RS> RS post(String resource, Map<String, String> URIParameters, List<NameValuePair> bodyParams, Class<RS> clazz)
			throws RestEndpointIOException, UnsupportedEncodingException;

	<RQ, RS> RS post(URI uri, RQ rq, Class<RS> clazz) throws RestEndpointIOException, UnsupportedEncodingException;
	
	<RQ, RS> RS put(URI uri, RQ rq, Class<RS> clazz) throws RestEndpointIOException;

	<RQ, RS> RS delete(String resource, RQ rq, Class<RS> clazz) throws RestEndpointIOException, UnsupportedEncodingException;
	
	<RS> RS delete(URI uri, Class<RS> clazz) throws RestEndpointIOException;
	
	<RS> RS get(URI uri, Class<RS> clazz) throws RestEndpointIOException;

	/**
	 * HTTP POST method
	 * 
	 * @param resource
	 *            - REST resource
	 * @param rq
	 *            - Request body
	 * @param clazz
	 *            - Type of returned response
	 * @return - Response body
	 * 
	 * @throws RestEndpointIOException
	 * @throws UnsupportedEncodingException
	 */
	<RQ, RS> RS post(String resource, RQ rq, Class<RS> clazz) throws RestEndpointIOException, UnsupportedEncodingException;

	/**
	 * HTTP MultiPart POST. May contain whether serialized and binary parts
	 * 
	 * @param resource
	 *            - REST resource
	 * @param request
	 *            - MultiPart request
	 * @param clazz
	 *            - Type of returned response
	 * @return - Response Body
	 * @throws RestEndpointIOException
	 */
	<RQ, RS> RS post(String resource, MultiPartRequest<RQ> request, Class<RS> clazz) throws RestEndpointIOException;

	/**
	 * HTTP PUT
	 * 
	 * @param resource
	 *            - REST resource
	 * @param rq
	 *            - Request body
	 * @param clazz
	 *            - Type of Response
	 * @return - Response body
	 * @throws RestEndpointIOException
	 */
	<RQ, RS> RS put(String resource, RQ rq, Class<RS> clazz) throws RestEndpointIOException;

	/**
	 * HTTP DELETE
	 * 
	 * @param resource
	 *            - REST Resource
	 * @param clazz
	 *            - Response Body Type
	 * @return - Response Body
	 * @throws RestEndpointIOException
	 */
	<RS> RS delete(String resource, Class<RS> clazz) throws RestEndpointIOException;

	/**
	 * HTTP GET
	 * 
	 * @param resource
	 *            - REST Resource
	 * @param clazz
	 *            - Response Body Type
	 * @return - Response Body
	 * @throws RestEndpointIOException
	 */
	<RS> RS get(String resource, Class<RS> clazz) throws RestEndpointIOException;

	/**
	 * HTTP GET with parameters
	 * 
	 * @param resource
	 *            - REST Resource
	 * @param parameters
	 *            - Map of query parameters
	 * @param clazz
	 *            - Response body type
	 * @return - Response Body
	 * @throws RestEndpointIOException
	 */
	<RS> RS get(String resource, Map<String, String> parameters, Class<RS> clazz) throws RestEndpointIOException;

}
