package com.mcgraw.test.automation.api.rest.d2l.factory;

import java.util.Random;

import com.mcgraw.test.automation.api.rest.d2l.D2LUserRole;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.D2LCourseOfferingRQ;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.D2LCreateEnrollmentRQ;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.D2LUserRQ;

public class D2LRequestsFactory {

	public D2LUserRQ createUser(String firstName, String lastName, String userName, D2LUserRole role){
		return createUser(new Random().nextInt(9), firstName, firstName, lastName, "email@email.com", userName, role, true, false);
	}
	
	public D2LUserRQ createUser(int orgDefinedId, String firstName, String middleName, String lastName, String externalEmail,
			String userName, D2LUserRole role, boolean active, boolean sendCreationEmail) {
		D2LUserRQ d2lUserRQ = new D2LUserRQ();
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
	
	public D2LCourseOfferingRQ createCourseOffering(String name, String code, int courseTemplateId){
		return createCourseOffering(name, code, courseTemplateId, false, true);
	}
	
	public D2LCourseOfferingRQ createCourseOffering(String name, String code, int courseTemplateId, boolean forceLocale, boolean showAddressBook){
		String empty = "";
		D2LCourseOfferingRQ d2lCourseOfferingRQ= new D2LCourseOfferingRQ();
		d2lCourseOfferingRQ.setName(name);
		d2lCourseOfferingRQ.setCode(code);
		d2lCourseOfferingRQ.setPath(empty);
		d2lCourseOfferingRQ.setCourseTemplateId(courseTemplateId);
		d2lCourseOfferingRQ.setForceLocale(forceLocale);
		d2lCourseOfferingRQ.setShowAddressBook(showAddressBook);
		return d2lCourseOfferingRQ;		
	}
	
	public D2LCreateEnrollmentRQ createEnrollment(int userId, int orgUnitId, D2LUserRole role){
		D2LCreateEnrollmentRQ d2LCreateEnrollmentRQ = new D2LCreateEnrollmentRQ();
		d2LCreateEnrollmentRQ.setUserId(userId);
		d2LCreateEnrollmentRQ.setOrgUnitId(orgUnitId);
		d2LCreateEnrollmentRQ.setRole(role);
		return d2LCreateEnrollmentRQ;
	}

}
