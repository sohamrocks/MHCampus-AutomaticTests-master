package com.mcgraw.test.automation.api.rest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.d2l.D2LUserRole;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCourseOfferingRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCourseTemplateRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LUserRS;
import com.mcgraw.test.automation.api.rest.d2l.service.D2LApiUtils;

@ContextConfiguration(locations = { "classpath:spring/test-api-context.xml" })
public class D2lValenceRestApiTests  extends AbstractTestNGSpringContextTests{

	@Autowired
	private D2LApiUtils d2LApiUtils;
	
	@Test(description = "Test d2l ")
	public void test() throws Exception {
		
		D2LUserRS d2lUserRS = d2LApiUtils.createUser("firstName"+getRandomString(), "lastName"+getRandomString(), "userName"+getRandomString(), "123qweA@",D2LUserRole.INSTRUCTOR);
		
		D2LCourseTemplateRS d2lCourseTemplateRS = d2LApiUtils.createCourseTemplate("Name"+getRandomString(),"COD"+RandomStringUtils.randomNumeric(3));
		D2LCourseOfferingRS d2lCourseOfferingRS=d2LApiUtils.createCourseOfferingByTemplate(d2lCourseTemplateRS, "CourseName"+getRandomString(), "COCOD"+RandomStringUtils.randomNumeric(3));
		
		d2LApiUtils.createEnrollment(d2lUserRS, d2lCourseOfferingRS, D2LUserRole.ADMINISTRATOR);
		
		d2LApiUtils.deleteUser(d2lUserRS);
		d2LApiUtils.deleteCourseOffering(d2lCourseOfferingRS);
		d2LApiUtils.deleteCourseTemplate(d2lCourseTemplateRS);
	}
	
	private String getRandomString() {
		return RandomStringUtils.randomNumeric(6);
	}
}
