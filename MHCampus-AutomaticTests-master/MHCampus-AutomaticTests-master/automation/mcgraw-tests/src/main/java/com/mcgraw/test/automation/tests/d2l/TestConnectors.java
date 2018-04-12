package com.mcgraw.test.automation.tests.d2l;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.d2l.D2LUserRole;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCourseOfferingRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCourseTemplateRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LUserRS;
import com.mcgraw.test.automation.api.rest.d2l.service.D2LApiUtils;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.D2LApplication;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class TestConnectors extends BaseTest {

	@Autowired
	private D2LApplication d2lApplication;

	MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;

	private String studentRandom = getRandomString();
	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;
	private String studentSurname = "StudentSurname" + studentRandom;

	private String instructorRandom = getRandomString();
	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String instructorSurname = "InstructorSurame" + instructorRandom;

	private String password = "123qweA@";

	private String courseName = "CourseName" + getRandomString();
	
	@Autowired
	private D2LApiUtils d2LApiUtils;

	private D2LUserRS studentRS;
	private D2LUserRS instructorRS;
	private D2LCourseTemplateRS courseTemplateRS;
	private D2LCourseOfferingRS courseOfferingRS;
	
	private int numOfSlave = 3;
	
	private InstanceCredentials instance;

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		
		prepareDataInD2l();
		
		instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
		mhCampusInstanceConnectorsScreen.configureCustomGradebookConnector(d2lApplication.d2lTitle, d2lApplication.d2lGradebookServiceUrl,
				d2lApplication.d2lGradebookExtendedPropertiesNone);
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();	
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		  clearD2lData();
	}
	
	@Test(description = "Check Gradebook connector behaves correctly")
	public void checkGradebookConnector() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String returnedMessage = "Successfully parsed extended properties";
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForGradebook();
		Assert.verifyTrue(result.contains(returnedMessage), "Not found the message in the result string");
		Assert.verifyTrue(result.contains("SUCCESS"), "Don't found 'SUCCESS' in the result string");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForGradebook();
		Assert.verifyTrue(result.contains(returnedMessage), "Not found the message in the result string");
		Assert.verifyTrue(result.contains("SUCCESS"), "Don't found 'SUCCESS' in the result string");
	}
	
	private void prepareDataInD2l() throws Exception {

		studentRS = d2LApiUtils.createUser(studentName, studentSurname, studentLogin, password, D2LUserRole.STUDENT);
		instructorRS = d2LApiUtils.createUser(instructorName, instructorSurname, instructorLogin, password, D2LUserRole.INSTRUCTOR);

		courseTemplateRS = d2LApiUtils.createCourseTemplate("name" + getRandomString(), "code" + RandomStringUtils.randomNumeric(3));
		courseOfferingRS = d2LApiUtils.createCourseOfferingByTemplate(courseTemplateRS, courseName,
				"code" + RandomStringUtils.randomNumeric(3));

		d2LApiUtils.createEnrollment(studentRS, courseOfferingRS, D2LUserRole.STUDENT);
		d2LApiUtils.createEnrollment(instructorRS, courseOfferingRS, D2LUserRole.INSTRUCTOR);
	}

	
	private String getRandomString() {
		return RandomStringUtils.randomAlphabetic(5).toUpperCase();
	}
	
	private void clearD2lData() throws Exception {
	
		if(studentRS != null)
			d2LApiUtils.deleteUser(studentRS);
		if(instructorRS != null)
			d2LApiUtils.deleteUser(instructorRS);
		if(courseOfferingRS != null)
			d2LApiUtils.deleteCourseOffering(courseOfferingRS);
		if(courseTemplateRS != null)
			d2LApiUtils.deleteCourseTemplate(courseTemplateRS);
	}
}
