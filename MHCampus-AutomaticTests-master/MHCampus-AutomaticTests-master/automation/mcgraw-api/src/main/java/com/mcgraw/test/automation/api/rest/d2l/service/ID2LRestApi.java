package com.mcgraw.test.automation.api.rest.d2l.service;

import java.io.UnsupportedEncodingException;

import com.mcgraw.test.automation.api.rest.d2l.rqmodel.D2LCourseOfferingRQ;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.D2LCreateEnrollmentRQ;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.D2LUserRQ;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.D2lUSerPasswordRQ;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCourseOfferingRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCourseTemplateRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCreateEnrollmentRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LUserRS;
import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;

public interface ID2LRestApi {

	D2LUserRS createUser(D2LUserRQ d2lUserRQ, D2lUSerPasswordRQ d2lUSerPasswordRQ) throws RestEndpointIOException,
			UnsupportedEncodingException;

	void deleteUser(D2LUserRS d2lUserRS) throws RestEndpointIOException;

	D2LCourseTemplateRS createCoursetemplate(String name, String code) throws RestEndpointIOException, UnsupportedEncodingException;
	
	void deleteCourseTemplate(D2LCourseTemplateRS courseTemplate) throws RestEndpointIOException;

	D2LCourseOfferingRS createCourseOffering(D2LCourseOfferingRQ d2lCourseOfferingRQ)
			throws RestEndpointIOException, UnsupportedEncodingException;
	
	void deleteCourseOffering(D2LCourseOfferingRS courseOffering) throws RestEndpointIOException;
	
	D2LCreateEnrollmentRS createEnrollment(D2LCreateEnrollmentRQ createEnrollmentRQ) throws RestEndpointIOException, UnsupportedEncodingException;

}
