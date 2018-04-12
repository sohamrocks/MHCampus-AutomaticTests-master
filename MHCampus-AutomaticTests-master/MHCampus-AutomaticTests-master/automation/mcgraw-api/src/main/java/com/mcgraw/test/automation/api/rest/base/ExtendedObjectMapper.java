package com.mcgraw.test.automation.api.rest.base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Factory bean for Jackson's object mapper.<br>
 * Extends its functionality
 * 
 * @author Andrei_Turavets
 * 
 */
public class ExtendedObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = -460516216981238955L;
	
	public void setTimeZone(String timeZone){
		this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		this.setDateFormat(dateFormat);		
	}
}
