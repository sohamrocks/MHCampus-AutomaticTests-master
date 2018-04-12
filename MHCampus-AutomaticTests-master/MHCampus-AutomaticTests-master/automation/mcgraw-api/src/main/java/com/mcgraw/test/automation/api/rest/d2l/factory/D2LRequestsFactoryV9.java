package com.mcgraw.test.automation.api.rest.d2l.factory;

import com.mcgraw.test.automation.api.rest.d2l.D2LUserRole;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.D2LCreateEnrollmentRQ;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.D2LUserRQ;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.version9.D2LCreateEnrollmentRQV9;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.version9.D2LUserRQV9;

/**
 * D2l request factory for supprting old d2l version D2LUserRole
 * @author Andrei_Turavets
 *
 */
public class D2LRequestsFactoryV9 extends D2LRequestsFactory {
	
	@Override
	public D2LUserRQ createUser(int orgDefinedId, String firstName, String middleName, String lastName, String externalEmail,
			String userName, D2LUserRole role, boolean active, boolean sendCreationEmail) {
		D2LUserRQ d2lUserRQ = new D2LUserRQV9();
		d2lUserRQ.setOrgDefinedId(orgDefinedId);
		d2lUserRQ.setFirstName(firstName);
		d2lUserRQ.setMiddleName(middleName);
		d2lUserRQ.setLastName(lastName);
		d2lUserRQ.setExternalEmail(externalEmail);
		d2lUserRQ.setUserName(userName);
		d2lUserRQ.setRole(role);
		d2lUserRQ.setActive(active);
		d2lUserRQ.setSendCreationEmail(sendCreationEmail);
		return d2lUserRQ;
	}
	
	@Override
	public D2LCreateEnrollmentRQ createEnrollment(int userId, int orgUnitId, D2LUserRole role){
		D2LCreateEnrollmentRQ d2LCreateEnrollmentRQ = new D2LCreateEnrollmentRQV9();
		d2LCreateEnrollmentRQ.setUserId(userId);
		d2LCreateEnrollmentRQ.setOrgUnitId(orgUnitId);
		d2LCreateEnrollmentRQ.setRole(role);
		return d2LCreateEnrollmentRQ;
	}

}
