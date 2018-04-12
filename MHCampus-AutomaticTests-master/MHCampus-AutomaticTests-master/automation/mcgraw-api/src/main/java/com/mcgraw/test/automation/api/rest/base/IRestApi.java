package com.mcgraw.test.automation.api.rest.base;

import java.io.UnsupportedEncodingException;

import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;

/**
 * Angel Rest API
 * 
 * @author Andrei_Turavets
 *
 */
public interface IRestApi{
	
	IRestServerError testErrorMessage() throws RestEndpointIOException, UnsupportedEncodingException;	
}
