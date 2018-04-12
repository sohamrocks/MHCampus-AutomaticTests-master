package com.mcgraw.test.automation.tests.lti;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.LTIApplication;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class LtiTest extends BaseTest {

	@Autowired
	protected LTIApplication ltiApplication;

	private String studentLogin;
	private String studentFullName;

	private String instructorLogin;
	private String instructorFullName;

	private String courseId;
	private String courseName;

	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;

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

	@Test(description = "Check availability of MhCampus after filling the form for student")
	public void checkLTIforStudent() throws Exception {
		
		mhCampusIntroductionScreen = ltiApplication.fillLtiFormAsStudent(instance.customerNumber, instance.sharedSecret, studentLogin,
				studentFullName, courseId, courseName);
		
		String expectedGreetingText = "Hi " + studentFullName + " -";
		
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(),expectedGreetingText, "Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(), "Rules text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.getCurrentUrl().contains(instance.pageAddressFromEmail.toLowerCase()),"The address of frame is not contain instance login page address");

		mhCampusIntroductionScreen.acceptRules();

		String expectedUserName = studentFullName.toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName, "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName), "Course is absent");
		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(), "Search option is present");
	
	}

	@Test(description = "Check availability of MhCampus after filling the form for instructor")
	public void checkLTIforInstructor() throws Exception {
		
		mhCampusIntroductionScreen = ltiApplication.fillLtiFormAsInstructor(instance.customerNumber, instance.sharedSecret,
				instructorLogin, instructorFullName, courseId, courseName);

		String expectedGreetingText = "Hi " + instructorFullName + " -";
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(),expectedGreetingText, "Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(), "Rules text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.getCurrentUrl().contains(instance.pageAddressFromEmail.toLowerCase()),"The address of frame is not contain instance login page address");

		mhCampusIntroductionScreen.acceptRules();
		
		String expectedUserName = instructorFullName.toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName, "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName), "Course" + courseName + " is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(), "Search option is absent");

	}

	private void prepareTestData() {
		studentLogin = "studentLogin_" + getRandomString();
		studentFullName = "studentFullName_" + getRandomString();
		instructorLogin = "instructorLogin" + getRandomString();
		instructorFullName = "instructorFullName" + getRandomString();
		courseId = "courseId_" + getRandomString();
		courseName = "courseName_" + getRandomString();
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}
}