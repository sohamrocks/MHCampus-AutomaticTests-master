package com.mcgraw.test.automation.api.rest.d2l.service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mcgraw.test.automation.api.rest.d2l.idkeyauth.ID2LUserContext;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LOrgUnitRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LOrganizationRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LVersionRS;
import com.mcgraw.test.automation.api.rest.endpoint.RestEndpoint;
import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;
import com.mcgraw.test.automation.framework.core.exception.test.CommonTestRuntimeException;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;

public class D2LBaseRestApi {

	protected RestEndpoint endpoint;

	protected ID2LUserContext d2LUserContext;

	/** d2lVersion Supplier. Used for lazy initialization */
	protected Supplier<String> d2lVersion = Suppliers.memoize(new Supplier<String>() {
		@Override
		public String get() {
			return getApiVersion();
		}
	});
	/** departmentForTests Supplier. Used for lazy initialization */
	protected Supplier<D2LOrgUnitRS> departmentForTests = Suppliers.memoize(new Supplier<D2LOrgUnitRS>() {
		@Override
		public D2LOrgUnitRS get() {
			return getFirstExistentDepartment();
		}
	});

	public D2LBaseRestApi(RestEndpoint endpoint, ID2LUserContext d2lUserContext) {
		super();
		this.endpoint = endpoint;
		this.d2LUserContext = d2lUserContext;
	}
	
	protected String getApiVersion(){
		URI getVersionUri = d2LUserContext.createAuthenticatedUri("/d2l/api/lp/versions/", "GET");
		D2LVersionRS d2lVersionRS = null;
		try {
			d2lVersionRS = endpoint.get(getVersionUri, D2LVersionRS.class);
		} catch (RestEndpointIOException e) {
			Logger.info("Failed to get d2l version. Can't proceed with other requests");
			throw new CommonTestRuntimeException(e.getMessage(),e.getCause());
		}
		
		Logger.info("Fetched D2L api latest version: " + d2lVersionRS.getLatestVersion());
		return d2lVersionRS.getLatestVersion();
	}

	protected D2LOrgUnitRS getFirstExistentDepartment() {
		D2LOrganizationRS d2lOrganiztionRS;
		List<D2LOrgUnitRS> orgUnitsUnderOrganization = null;
		try {
			d2lOrganiztionRS = getOrganization();
			orgUnitsUnderOrganization = getOrganizationChildren(d2lOrganiztionRS);
		} catch (RestEndpointIOException e) {
			throw new CommonTestRuntimeException(e.getMessage());
		}
		for (D2LOrgUnitRS d2lOrgUnitRS : orgUnitsUnderOrganization) {
			if (d2lOrgUnitRS.getD2lOuTypeRS().getCode().equals("Department")) {
				return d2lOrgUnitRS;
			}
		}
		throw new CommonTestRuntimeException("No departments found under organization " + d2lOrganiztionRS);
	}

	protected List<D2LOrgUnitRS> getOrganizationChildren(D2LOrganizationRS d2lOrganiztionRS) throws RestEndpointIOException {
		URI getOrganizationIn = d2LUserContext.createAuthenticatedUri(
				"/d2l/api/lp/" + d2lVersion.get() + "/orgstructure/" + d2lOrganiztionRS.getId() + "/children/", "GET");
		D2LOrgUnitRS[] orgUnits = endpoint.get(getOrganizationIn, D2LOrgUnitRS[].class);
		return Arrays.asList(orgUnits);

	}

	protected D2LOrganizationRS getOrganization() throws RestEndpointIOException {
		URI getOrganizationIn = d2LUserContext.createAuthenticatedUri("/d2l/api/lp/" + d2lVersion.get() + "/organization/info", "GET");
		D2LOrganizationRS d2lOrganiztionRS = endpoint.get(getOrganizationIn, D2LOrganizationRS.class);
		Logger.info("Get organization data: " + d2lOrganiztionRS);
		return d2lOrganiztionRS;
	}

}
