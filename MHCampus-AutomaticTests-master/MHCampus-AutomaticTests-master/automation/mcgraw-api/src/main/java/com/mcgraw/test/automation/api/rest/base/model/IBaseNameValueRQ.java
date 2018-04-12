package com.mcgraw.test.automation.api.rest.base.model;

import java.util.List;

import org.apache.http.NameValuePair;

/**
 * Interface for BaseNameValueRQ
 * 
 * @author Andrei_Turavets
 *
 */
public interface IBaseNameValueRQ {
	
	List<NameValuePair> getParams();
	
}
