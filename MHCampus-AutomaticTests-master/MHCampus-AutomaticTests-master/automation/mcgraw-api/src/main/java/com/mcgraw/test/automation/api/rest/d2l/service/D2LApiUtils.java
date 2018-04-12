package com.mcgraw.test.automation.api.rest.d2l.service;

import java.io.UnsupportedEncodingException;

import com.mcgraw.test.automation.api.rest.d2l.D2LUserRole;
import com.mcgraw.test.automation.api.rest.d2l.factory.D2LRequestsFactory;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.D2LCourseOfferingRQ;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.D2LCreateEnrollmentRQ;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.D2LUserRQ;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.D2lUSerPasswordRQ;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCourseOfferingRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCourseTemplateRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCreateEnrollmentRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LUserRS;
import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;

/**
 * Class for usage of D2L rest api functionality
 * 
 * @author Andrei_Turavets
 * 
 */
public class D2LApiUtils {

	private ID2LRestApi restApi;

	private D2LRequestsFactory requestsFactory;

	private D2LApiUtils(ID2LRestApi restApi, D2LRequestsFactory requestsFactory) {
		super();
		this.restApi = restApi;
		this.requestsFactory = requestsFactory;
	}

	public D2LUserRS createUser(String firstName, String lastName, String userName, String password, D2LUserRole role)
			throws RestEndpointIOException, UnsupportedEncodingException {
		D2LUserRQ d2lUserRQ = requestsFactory.createUser(firstName, lastName, userName, role);
		D2lUSerPasswordRQ d2lUSerPasswordRQ = new D2lUSerPasswordRQ();
		d2lUSerPasswordRQ.setPassword(password);
		return restApi.createUser(d2lUserRQ, d2lUSerPasswordRQ);
	}

	public D2LCourseTemplateRS createCourseTemplate(String name, String code) throws RestEndpointIOException, UnsupportedEncodingException {
		return restApi.createCoursetemplate(name, code);
	}

	public D2LCourseOfferingRS createCourseOfferingByTemplate(D2LCourseTemplateRS d2lCourseTemplateRS, String name, String code)
			throws RestEndpointIOException, UnsupportedEncodingException {
		D2LCourseOfferingRQ d2lCourseOfferingRQ = requestsFactory.createCourseOffering(name, code, d2lCourseTemplateRS.getId());
		D2LCourseOfferingRS d2lCourseOfferingRS = restApi.createCourseOffering(d2lCourseOfferingRQ);
		return d2lCourseOfferingRS;
	}

	public D2LCreateEnrollmentRS createEnrollment(D2LUserRS d2lUserRS, D2LCourseOfferingRS d2lCourseOfferingRS, D2LUserRole role)
			throws RestEndpointIOException, UnsupportedEncodingException {
		D2LCreateEnrollmentRQ d2LCreateEnrollmentRQ = requestsFactory.createEnrollment(d2lUserRS.getUserId(), d2lCourseOfferingRS.getId(),
				role);
		D2LCreateEnrollmentRS d2lCreateEnrollmentRS = restApi.createEnrollment(d2LCreateEnrollmentRQ);
		return d2lCreateEnrollmentRS;
	}

	public void deleteUser(D2LUserRS d2lUserRS) throws RestEndpointIOException {
		restApi.deleteUser(d2lUserRS);
	}

	public void deleteCourseTemplate(D2LCourseTemplateRS courseTemplate) throws RestEndpointIOException {
		restApi.deleteCourseTemplate(courseTemplate);
	}

	public void deleteCourseOffering(D2LCourseOfferingRS courseOffering) throws RestEndpointIOException {
		restApi.deleteCourseOffering(courseOffering);
	}

}
