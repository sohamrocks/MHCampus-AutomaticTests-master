package com.mcgraw.test.automation.api.rest.d2l.service;

import java.io.UnsupportedEncodingException;
import java.net.URI;

import com.mcgraw.test.automation.api.rest.d2l.idkeyauth.ID2LUserContext;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.D2LCourseOfferingRQ;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.D2LCreateCourseTemplateRQ;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.D2LCreateEnrollmentRQ;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.D2LUserRQ;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.D2lUSerPasswordRQ;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCourseOfferingRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCourseTemplateRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCreateEnrollmentRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LUserRS;
import com.mcgraw.test.automation.api.rest.endpoint.RestEndpoint;
import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;

public class D2LRestApi extends D2LBaseRestApi implements ID2LRestApi{
	
	public D2LRestApi(RestEndpoint endpoint, ID2LUserContext d2lUserContext) {
		super(endpoint, d2lUserContext);
	}

	@Override
	public D2LUserRS createUser(D2LUserRQ d2lUserRQ, D2lUSerPasswordRQ d2lUSerPasswordRQ) throws RestEndpointIOException, UnsupportedEncodingException {
		Logger.info("Creating d2l user");
		URI createUserURI = d2LUserContext.createAuthenticatedUri("/d2l/api/lp/"+d2lVersion.get()+"/users/", "POST");
		D2LUserRS d2lUserRS = endpoint.post(createUserURI, d2lUserRQ, D2LUserRS.class);	
		Logger.info("Created: " + d2lUserRS);
		
		Logger.info("Setting password for created user");
		URI updatePasswordUri = d2LUserContext.createAuthenticatedUri("/d2l/api/lp/"+d2lVersion.get()+"/users/"+d2lUserRS.getUserId()+"/password", "PUT");
		endpoint.put(updatePasswordUri, d2lUSerPasswordRQ, null);
		Logger.info("Password for user is set: password=" + d2lUSerPasswordRQ.getPassword());
		return d2lUserRS;
	}

	@Override
	public void deleteUser(D2LUserRS d2lUserRS) throws RestEndpointIOException {
		Logger.info("Deleting d2l user");
		URI deleteUserUri = d2LUserContext.createAuthenticatedUri("/d2l/api/lp/"+d2lVersion.get()+"/users/"+d2lUserRS.getUserId(),"DELETE");
		endpoint.delete(deleteUserUri, null);
		Logger.info("Deleted: "+d2lUserRS);		
	}



	@Override
	public D2LCourseTemplateRS createCoursetemplate(String name, String code) throws RestEndpointIOException, UnsupportedEncodingException {
		URI createCourseTemplateUri = d2LUserContext.createAuthenticatedUri("/d2l/api/lp/"+d2lVersion.get()+"/coursetemplates/","POST");
		D2LCreateCourseTemplateRQ createCourseTemplateRQ = new D2LCreateCourseTemplateRQ();
		createCourseTemplateRQ.setName(name);
		createCourseTemplateRQ.setCode(code);
		createCourseTemplateRQ.addParentOrgUnitIds(departmentForTests.get().getId());
		Logger.info("Creating d2l course template");
		D2LCourseTemplateRS d2lCourseTemplateRS = endpoint.post(createCourseTemplateUri, createCourseTemplateRQ, D2LCourseTemplateRS.class);
		Logger.info("Created: " + d2lCourseTemplateRS + " for " + departmentForTests.get());
		
		return d2lCourseTemplateRS;
	}

	@Override
	public D2LCourseOfferingRS createCourseOffering(D2LCourseOfferingRQ d2lCourseOfferingRQ )
			throws RestEndpointIOException, UnsupportedEncodingException {
		URI createCourseOfferingUri = d2LUserContext.createAuthenticatedUri("/d2l/api/lp/"+d2lVersion.get()+"/courses/","POST");
		Logger.info("Creating d2l course offering");
		D2LCourseOfferingRS d2lCourseOfferingRS = endpoint.post(createCourseOfferingUri, d2lCourseOfferingRQ, D2LCourseOfferingRS.class);
		Logger.info("Created: " + d2lCourseOfferingRS + " for " + departmentForTests.get());
		return d2lCourseOfferingRS;
	}

	@Override
	public D2LCreateEnrollmentRS createEnrollment(D2LCreateEnrollmentRQ createEnrollmentRQ) throws RestEndpointIOException,
			UnsupportedEncodingException {
		URI createEnrollmentUri = d2LUserContext.createAuthenticatedUri("/d2l/api/lp/"+d2lVersion.get()+"/enrollments/","POST");
		Logger.info("Creating d2l enrollment");
		D2LCreateEnrollmentRS createEnrollmentRS = endpoint.post(createEnrollmentUri, createEnrollmentRQ, D2LCreateEnrollmentRS.class);
		Logger.info("Created: " + createEnrollmentRS);
		return createEnrollmentRS;
	}

	@Override
	public void deleteCourseTemplate(D2LCourseTemplateRS courseTemplate) throws RestEndpointIOException {
		URI deleteCourseTemplateUri = d2LUserContext.createAuthenticatedUri("/d2l/api/lp/"+d2lVersion.get()+"/coursetemplates/"+courseTemplate.getId(),"DELETE");
		Logger.info("Deleting d2l course template");
		endpoint.delete(deleteCourseTemplateUri, null);
		Logger.info("Deleted: "+courseTemplate);
		
	}

	@Override
	public void deleteCourseOffering(D2LCourseOfferingRS courseOffering) throws RestEndpointIOException {
		Logger.info("Deleting d2l course offering");
		URI deleteCourseOfferingUri = d2LUserContext.createAuthenticatedUri("/d2l/api/lp/"+d2lVersion.get()+"/courses/"+courseOffering.getId(),"DELETE");
		endpoint.delete(deleteCourseOfferingUri, null);
		Logger.info("Deleted: "+courseOffering);
		
	}
	
	
	
}
