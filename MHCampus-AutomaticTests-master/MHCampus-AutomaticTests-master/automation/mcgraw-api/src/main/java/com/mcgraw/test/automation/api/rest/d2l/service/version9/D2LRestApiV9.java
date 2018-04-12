package com.mcgraw.test.automation.api.rest.d2l.service.version9;

import java.io.UnsupportedEncodingException;
import java.net.URI;

import com.mcgraw.test.automation.api.rest.d2l.idkeyauth.ID2LUserContext;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.D2LCreateEnrollmentRQ;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCreateEnrollmentRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.version9.D2LCreateEnrollmentRSV9;
import com.mcgraw.test.automation.api.rest.d2l.service.D2LRestApi;
import com.mcgraw.test.automation.api.rest.endpoint.RestEndpoint;
import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;

public class D2LRestApiV9 extends D2LRestApi {

	public D2LRestApiV9(RestEndpoint endpoint, ID2LUserContext d2lUserContext) {
		super(endpoint, d2lUserContext);
	}

	@Override
	public D2LCreateEnrollmentRS createEnrollment(D2LCreateEnrollmentRQ createEnrollmentRQ) throws RestEndpointIOException,
			UnsupportedEncodingException {
		URI createEnrollmentUri = d2LUserContext.createAuthenticatedUri("/d2l/api/lp/"+d2lVersion.get()+"/enrollments/","POST");
		Logger.info("Creating d2l enrollment");
		D2LCreateEnrollmentRS createEnrollmentRS = endpoint.post(createEnrollmentUri, createEnrollmentRQ, D2LCreateEnrollmentRSV9.class);
		Logger.info("Created: " + createEnrollmentRS);
		return createEnrollmentRS;
	}
	
}
