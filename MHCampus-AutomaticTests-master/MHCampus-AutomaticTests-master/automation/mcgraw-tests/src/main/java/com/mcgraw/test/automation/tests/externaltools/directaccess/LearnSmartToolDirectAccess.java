package com.mcgraw.test.automation.tests.externaltools.directaccess;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.ExternalToolsApplication;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.learnsmart.MhCampusLearnSmartScreenWithOutBar;
import com.mcgraw.test.automation.ui.mhcampus.course.learnsmart.MhCampusLearnSmartStudentRegistrationScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class LearnSmartToolDirectAccess extends BaseTest {

	@Autowired
	protected ExternalToolsApplication externalToolsApplication;

	private String studentLogin;
	private String studentFullName;
	
	private String instructorLogin;
	private String instructorFullName;
	
	private String courseId;
	private String courseName;

	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	
	private InstanceCredentials instance;
	
	@BeforeClass
	public void testSuiteSetup() throws Exception {
	    
		try{
			instance = tegrityAdministrationApplication.createNewMhCampusInstance();
		}catch(Exception e){
			Logger.info("Failed to create MH Campus instance, trying again...");
			browser.pause(60000);
			instance = tegrityAdministrationApplication.createNewMhCampusInstance();
		}
		
		browser.pause(mhCampusInstanceApplication.CREATE_NEW_INSTANCE_TIMEOUT);
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAndAcceptTermsOfUse(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();		
		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);
		prepareTestData();
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testTearDown() throws Exception {
		if(instance != null)
			tegrityAdministrationApplication.deleteMhCampusInstance(instance.customerNumber);
	}
	
	@AfterMethod  
	public void closeAllWindowsExceptFirst() throws Exception {
		browser.closeAllWindowsExceptFirst();
	}

	@Test(description = "Check availability of MhCampus with Learn Smart tool after filling the form for instructor")
	public void checkToolForInstructor() throws Exception {
		
		externalToolsApplication.fillLtiFormAsInstructorForLearnSmart(instance.customerNumber,
				instance.sharedSecret, instructorLogin, instructorFullName, courseId, courseName);
		
		Assert.assertTrue(browser.isCurrentlyOnPageUrl(MhCampusLearnSmartScreenWithOutBar.class), notOnDesiredPageMessage(MhCampusLearnSmartScreenWithOutBar.class));
		browser.closeAllWindowsExceptFirst(true);
	}
	
	@Test(description = "Check availability of MhCampus with Learn Smart after filling the form for student")
	public void checkToolForStudent() throws Exception {
		
		MhCampusLearnSmartStudentRegistrationScreen mhCampusLearnSmartStudentRegistrationScreen = externalToolsApplication.
				fillLtiFormAsStudentForLearnSmart(instance.customerNumber, instance.sharedSecret,
				studentLogin, studentFullName, courseId, courseName);
		
		Assert.verifyTrue(browser.isCurrentlyOnPageUrl(MhCampusLearnSmartStudentRegistrationScreen.class), notOnDesiredPageMessage(MhCampusLearnSmartStudentRegistrationScreen.class));
		Assert.assertTrue(mhCampusLearnSmartStudentRegistrationScreen.isCreateSuccessTextPresent(),"Success message is absent");
	}

	private void prepareTestData() {
		studentLogin = "studentLogin" + getRandomString();
		studentFullName = "studentFullName" + getRandomString();
		instructorLogin = "instructorLogin" + getRandomString();
		instructorFullName = "instructorFullName" + getRandomString();
		courseId = "courseId" + getRandomString();
		courseName = "courseName" + getRandomString();
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}
	
	protected <T extends Screen> String notOnDesiredPageMessage(Class<T> clazz) {
		return "Browser is not on '" + browser.getScreenAbsoluteUrl(clazz) + "'page";
	}

}