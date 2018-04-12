package com.mcgraw.test.automation.tests.d2l.configurations;


import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.d2l.D2LUserRole;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCourseOfferingRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCourseTemplateRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LUserRS;
import com.mcgraw.test.automation.api.rest.d2l.service.D2LApiUtils;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.D2LApplication;
import com.mcgraw.test.automation.ui.d2l.base.D2lContentCourseScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lCourseDetailsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lEditHomepageScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lHomeScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lManageExternalToolsScreen;

/**
* LMS = D2L
* DI: Enabled
* Course ID – User ID Mapping: None
* Gradebook Connector: No
* Gradebook Connector type : none
* SSO Connector: No
* Data Connector: No
* Canvas Mapping: No
* Instructor Level Token: Off
* Use Existing Assignments: Off
* Fallback to user_id and context_id: Off
* Legacy: off
* Async: Off
*/

public class CustomD2LConfiguration127 extends BaseTest {

	@Autowired
	private D2LApplication d2lApplication;
	
	@Autowired
	private D2LApiUtils d2LApiUtils;

	private String instructorRandom = getRandomString();
	private String instructorLogin2 = "instructor2" + instructorRandom;
	private String instructorName2 = "InstructorName" + instructorRandom;
	private String instructorSurname2 = "InstructorSurame" + instructorRandom;

	private String studentRandom = getRandomString();
	private String studentLogin2 = "student2" + studentRandom;
	private String studentName2 = "StudentName" + studentRandom;
	private String studentSurname2 = "StudentSurname" + studentRandom;
		
	private String password = "123qweA@";

	private String courseName2 = "CourseName2" + getRandomString();
	private String courseId2;
	private String moduleName = "Module" + getRandomNumber();
	private String widgetName = "MHE_PQA_" + getRandomString();
	private String lunchUrl = "https://login-aws-qa.mhcampus.com/sso/di/d2l/lti/Connect";
	
    private final String customerNumber = "9M89-V0J0-GT67";
    private final String institution = "CustomCanvasConfiguration127";
    private final String sharedSecret = "7DDABC";
    
	private D2LUserRS studentRS;
	private D2LUserRS instructorRS;
	private D2LCourseTemplateRS courseTemplateRS2;
	private D2LCourseOfferingRS courseOfferingRS2;	
	
	private D2lHomeScreen d2lHomeScreen;
	private D2lCourseDetailsScreen d2lCourseDetailsScreen;
	private D2lManageExternalToolsScreen d2lManageExternalToolsScreen;
	private D2lEditHomepageScreen d2lEditHomepageScreen;
	private D2lContentCourseScreen d2lContentCourseScreen;

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("LMS = D2L");
		Logger.info("DI: Enabled");
		Logger.info("ID – User ID Mapping: None");
		Logger.info("Gradebook Connector: No");
		Logger.info("Gradebook Connector type : none");
		Logger.info("SSO Connector: No");
		Logger.info("Data Connector: No");
		Logger.info("Canvas Mapping: No");
		Logger.info("Instructor Level Token: Off");
		Logger.info("Use Existing Assignments: Off");
		Logger.info("Fallback to user_id and context_id: Off");
		Logger.info("Legacy: off");
		Logger.info("Async: Off");
		
		prepareDataInD2l();
		
	    d2lHomeScreen = d2lApplication.loginToD2lAsAdmin();
		d2lApplication.createNewWidgetPlugin(widgetName, lunchUrl, customerNumber, sharedSecret, courseName2);
		d2lManageExternalToolsScreen = d2lApplication.openD2LManageExternalToolsPage();
		d2lApplication.bindingPluginToCourse(courseName2,customerNumber,sharedSecret, widgetName);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);
		d2lEditHomepageScreen = d2lCourseDetailsScreen.clickEditHomepageBtn();
		d2lCourseDetailsScreen = d2lEditHomepageScreen.addWidget(widgetName);
		
		d2lContentCourseScreen = d2lApplication.openD2lContentCoursePage(courseId2);
		d2lContentCourseScreen.addModule(moduleName);
		browser.switchTo().defaultContent();
		d2lApplication.d2lLogout(d2lHomeScreen);
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearD2lData();
		browser.pause(3000);
		d2lApplication.loginToD2lAsAdmin();
		browser.pause(3000);
		d2lManageExternalToolsScreen = d2lApplication.openD2LManageExternalToolsPage();
		d2lManageExternalToolsScreen.deleteWidgetLink(widgetName);
	}

	@Test(description = "For D2L instructor2 MH Campus link baheves correctly")
	public void checkMHCampusLinkBehavesCorrectlyForD2LInstructor2() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin2, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);
		browser.pause(2000);
		d2lCourseDetailsScreen.clickOnContinueBtn();
		Assert.verifyTrue(d2lCourseDetailsScreen.isExistErrorMessageText(), "error message is not shown as expected");
		browser.switchTo().defaultContent();
		d2lApplication.d2lLogout(d2lHomeScreen);	
	}
	
	@Test(description = "For D2L student2 MH Campus link baheves correctly", 
			dependsOnMethods = "checkMHCampusLinkBehavesCorrectlyForD2LInstructor2")
	public void checkMHCampusLinkBehavesCorrectlyForD2LStudent2() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin2, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);		
		Assert.verifyTrue(d2lCourseDetailsScreen.isExistErrorMessageText(), "error message is not shown as expected");
		browser.switchTo().defaultContent();
		d2lApplication.d2lLogout(d2lHomeScreen);
	}


	private void prepareDataInD2l() throws Exception {
		studentRS = d2LApiUtils.createUser(studentName2, studentSurname2, studentLogin2, password, D2LUserRole.STUDENT);
		instructorRS = d2LApiUtils.createUser(instructorName2, instructorSurname2, instructorLogin2, password, D2LUserRole.INSTRUCTOR);

		courseTemplateRS2 = d2LApiUtils.createCourseTemplate("name" + getRandomString(), "code" + RandomStringUtils.randomNumeric(3));
		courseOfferingRS2 = d2LApiUtils.createCourseOfferingByTemplate(courseTemplateRS2, courseName2,
				"code" + RandomStringUtils.randomNumeric(3));
		courseId2 = Integer.toString(courseOfferingRS2.getId());
		
		d2LApiUtils.createEnrollment(studentRS, courseOfferingRS2, D2LUserRole.STUDENT);
		d2LApiUtils.createEnrollment(instructorRS, courseOfferingRS2, D2LUserRole.INSTRUCTOR);
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphabetic(5).toUpperCase();
	}
	
	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}
	
    private void clearD2lData() throws Exception {
	
		if(studentRS != null)
			d2LApiUtils.deleteUser(studentRS);
		if(instructorRS != null)
			d2LApiUtils.deleteUser(instructorRS);
		if(courseOfferingRS2 != null)
			d2LApiUtils.deleteCourseOffering(courseOfferingRS2);
		if(courseTemplateRS2 != null)
			d2LApiUtils.deleteCourseTemplate(courseTemplateRS2);
	}
}
