package com.mcgraw.test.automation.tests.lti;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.LTIApplication;
import com.mcgraw.test.automation.ui.applications.TegrityInstanceApplicationNoLocalConnector;
import com.mcgraw.test.automation.ui.tegrity.TegrityCourseDetailsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityIntroductionScreen;

public class TegrityLtiTest extends BaseTest {

	private static final String LTI_SUB_URL = "ltiapps.net"; 
	
	@Autowired
	protected LTIApplication ltiApplication;
	
	@Autowired
	protected TegrityInstanceApplicationNoLocalConnector tegrityInstanceApplicationNoLocalConnector;

	private String studentLogin;
	private String studentFullName;

	private String instructorLogin;
	private String instructorFullName;

	private String courseId;
	private String courseName;

	private TegrityInstanceConnectorsScreen tegrityInstanceConnectorsScreen;
	private TegrityIntroductionScreen tegrityIntroductionScreen;
	private TegrityCourseDetailsScreen tegrityCourseDetailsScreen;

	@BeforeClass
	public void testSuiteSetup() throws Exception {
	
		tegrityInstanceConnectorsScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdminAndClickManageAairsLink();			
		tegrityInstanceConnectorsScreen.deleteAllConnectors();
		
		String serviceUrl = ltiApplication.serviceUrl.replace("<instance_url>", (tegrityInstanceApplicationNoLocalConnector.url.replace("http", "https")) );
		String extendedProperties =	ltiApplication.extendedProperties.replace("<customer_number>", tegrityInstanceApplicationNoLocalConnector.customerNumber);		
		tegrityInstanceConnectorsScreen.configureCustomAuthorizationConnector(ltiApplication.title, serviceUrl, extendedProperties);
		
		tegrityInstanceConnectorsScreen.clickSaveAndContinueButton();
		browser.pause(tegrityInstanceApplicationNoLocalConnector.DIRECT_LOGIN_TIMEOUT);
		
		prepareTestData();
	}
	
	@AfterMethod  
	public void closeAllWindowsExceptFirst() throws Exception {
		browser.closeAllWindowsExceptCurrentWithSubURL(LTI_SUB_URL);
	}

	@Test(description = "Check availability of Tegrity after filling the form for instructor")
	public void checkLTIforInstructor() throws Exception {
		
		tegrityCourseDetailsScreen = ltiApplication.fillLtiFormAsInstructor(tegrityInstanceApplicationNoLocalConnector.customerNumber, 
				tegrityInstanceApplicationNoLocalConnector.sharedSecret, instructorLogin, instructorFullName, courseId, courseName);
		
		Assert.verifyEquals(tegrityCourseDetailsScreen.getUserNameText(), instructorFullName, "Greetin text is incorrect");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isCoursePresent(courseName), "Course is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isSearchOptionPresent(), "Search option is absent");	
		
		tegrityIntroductionScreen = tegrityCourseDetailsScreen.goToCourses();

		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), instructorFullName);				
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName), "Course " + courseName + " is absent");
		String sandboxCourse = instructorFullName + " " + "sandbox course";   
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is absent");			
		Assert.verifyTrue(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");	
	}
	
	@Test(description = "Check availability of Tegrity after filling the form for student")
	public void checkLTIforStudent() throws Exception {
		
		tegrityCourseDetailsScreen = ltiApplication.fillLtiFormAsStudent(tegrityInstanceApplicationNoLocalConnector.customerNumber,
				tegrityInstanceApplicationNoLocalConnector.sharedSecret, studentLogin, studentFullName, courseId, courseName);
		
		Assert.verifyEquals(tegrityCourseDetailsScreen.getUserNameText(), studentFullName, "Greeting text is incorrect");
		Assert.verifyFalse(tegrityCourseDetailsScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isCoursePresent(courseName), "Course is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isSearchOptionPresent(), "Search option is absent");	
		
		tegrityIntroductionScreen = tegrityCourseDetailsScreen.goToCourses();

		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), studentFullName);				
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName), "Course " + courseName + " is absent");
		String sandboxCourse = "sandbox course";   
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is present");		
		Assert.verifyFalse(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
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