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
 * Legacy: Off
 * Async: Off
 */
public class CustomD2LConfiguration131 extends BaseTest {

	@Autowired
	private D2LApplication d2lApplication;

	@Autowired
	private D2LApiUtils d2LApiUtils;
	
	private String customerNumber = "12BQ-GFES-4K9E";
	private String sharedSecret = "CEB028";
//	private String institution = "CustomCanvasConfiguration131";
//	private String pageAddressFromEmail = "http://"+institution+".mhcampus.com";
	
	private String password = "123qweA@";
	
	private String studentRandom2 = getRandomString();
	private String studentLogin2 = "student" + studentRandom2;
	private String studentName2 = "StudentName" + studentRandom2;
	private String studentSurname2 = "StudentSurname" + studentRandom2;	
	
	private String courseName2 = "CourseName2" + getRandomString();
	private String courseId2;

	//============= D2l user data Deep =============
	private D2LUserRS studentRS2;
	private D2LCourseTemplateRS courseTemplateRS2;
	private D2LCourseOfferingRS courseOfferingRS2;

	private D2lHomeScreen d2lHomeScreen;
	private D2lCourseDetailsScreen d2lCourseDetailsScreen;
	private D2lContentCourseScreen d2lContentCourseScreen;
	private D2lManageExternalToolsScreen d2lManageExternalToolsScreen;
	private D2lEditHomepageScreen d2lEditHomepageScreen;

	private static String moduleName = "Module" + RandomStringUtils.randomNumeric(5);	

	private String nameOfWidget = "MHE_PQA_" + getRandomString();
	private String launchUrl = "https://login-aws-qa.mhcampus.com/sso/di/d2l/lti/Connect";


	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration:");
		Logger.info("LMS = D2L | DI: Enabled  ");
		Logger.info("Course ID – User ID Mapping: None | Gradebook Connector: No | Gradebook Connector type : none | SSO Connector: No | Data Connector: No | Canvas Mapping: No ");
		Logger.info("* Instructor Level Token: Off | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Legacy: Off | Async: Off");
		
		prepareDataInD2lDeep();
	}

	// @AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearD2lDataDeep();
	}
	// Tests starts
	//================== Deep Integration ====================
	@Test(description = "SSO for student2")
	public void checkMHCampusLinkBehavesCorrectlyForD2LStudent2() throws Exception {				
		
		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin2, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);		
		
		Assert.verifyTrue(d2lCourseDetailsScreen.isExistErrorMessageText(), "Error message is not shown");   		
		
		browser.switchTo().defaultContent();
		d2lApplication.d2lLogout(d2lHomeScreen);
	}
	// Test Finish

	// private methods for tests
	private void prepareDataInD2lDeep() throws Exception {
		
		studentRS2 = d2LApiUtils.createUser(studentName2, studentSurname2, studentLogin2, password, D2LUserRole.STUDENT);
		courseTemplateRS2 = d2LApiUtils.createCourseTemplate("name2" + getRandomString(), "code2" + RandomStringUtils.randomNumeric(3));
		courseOfferingRS2 = d2LApiUtils.createCourseOfferingByTemplate(courseTemplateRS2, courseName2,
				"code" + RandomStringUtils.randomNumeric(3));
		courseId2 = Integer.toString(courseOfferingRS2.getId());

		d2LApiUtils.createEnrollment(studentRS2, courseOfferingRS2, D2LUserRole.STUDENT);

		d2lHomeScreen =d2lApplication.loginToD2lAsAdmin();
		d2lApplication.createNewWidgetPlugin(nameOfWidget, launchUrl, customerNumber, sharedSecret, courseName2);		
		d2lManageExternalToolsScreen = d2lApplication.openD2LManageExternalToolsPage();		

		d2lApplication.bindingPluginToCourse(courseName2,customerNumber,sharedSecret, nameOfWidget);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);
		d2lEditHomepageScreen = d2lCourseDetailsScreen.clickEditHomepageBtn();
		d2lCourseDetailsScreen = d2lEditHomepageScreen.addWidget(nameOfWidget);		
		
		d2lContentCourseScreen = d2lApplication.openD2lContentCoursePage(courseId2);
		d2lContentCourseScreen.addModule(moduleName);
		
		browser.switchTo().defaultContent();
		d2lApplication.d2lLogout(d2lHomeScreen);
		
	}

	private void clearD2lDataDeep() throws Exception {
		
		if (studentRS2 != null)
			d2LApiUtils.deleteUser(studentRS2);
		if (courseOfferingRS2 != null)
			d2LApiUtils.deleteCourseOffering(courseOfferingRS2);
		if (courseTemplateRS2 != null)
			d2LApiUtils.deleteCourseTemplate(courseTemplateRS2);
		d2lApplication.loginToD2lAsAdmin();
		browser.pause(3000);
		d2lManageExternalToolsScreen = d2lApplication.openD2LManageExternalToolsPage();
		browser.pause(3000);
		d2lManageExternalToolsScreen.deleteWidgetLink(nameOfWidget);
		
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(6);
	}
}
