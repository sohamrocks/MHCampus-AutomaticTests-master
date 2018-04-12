package com.mcgraw.test.automation.api.rest.base.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * Base class for requests which created using
 * List<NameValuePair> parameters as body
 * 
 * @author Andrei_Turavets
 *
 */
public class BaseNameValueRQ implements IBaseNameValueRQ{

	protected List<NameValuePair> params = new ArrayList<NameValuePair>();
	
	public List<NameValuePair> getParams() {
		return params;
	}

	protected void setBodyParameter(String name, String value ){
		NameValuePair nameValuePair = new BasicNameValuePair(name, value);
		ListIterator<NameValuePair> iterator = params.listIterator();
		int indexofRemoved = 0;
		boolean isArrayListChanged = false;
		while (iterator.hasNext()) {
			if (iterator.next().getName().equals(nameValuePair.getName())) {
				indexofRemoved = iterator.previousIndex();
				iterator.remove();
				isArrayListChanged = true;
			}
		}
		if (isArrayListChanged)
			params.add(indexofRemoved, new BasicNameValuePair(nameValuePair.getName(), nameValuePair.getValue()));
		else {
			params.add(new BasicNameValuePair(nameValuePair.getName(), nameValuePair.getValue()));
		}
	}

}
