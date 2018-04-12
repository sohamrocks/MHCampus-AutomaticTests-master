package com.mcgraw.test.automation.tests.blackboard;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.blackboard.serviceapi.BlackBoardApi;
import com.mcgraw.test.automation.api.blackboard.serviceapi.BlackBoardApi.BlackboardApiRoleIdentifier;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.BlackboardApplication;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceDashboardScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class DirectLoginTest extends BaseTest {

	@Autowired
	private BlackBoardApi blackBoardApi;

	@Autowired
	private BlackboardApplication blackboardApplication;

	private String studentId;
	private String studentLogin;
	private String studentName;
	private String studentSurname;

	private String instructorId;
	private String instructorLogin;
	private String instructorName;
	private String instructorSurname;

	private String courseId1;
	private String courseName1;

	private String courseId2;
	private String courseName2;

	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	private MhCampusInstanceDashboardScreen mhCampusInstanceDashboardScreen;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;

	private InstanceCredentials instance;
	
	@BeforeClass
	public void testSuiteSetup() throws Exception {
	
		prepareTestDataInBlackBoard();	
		
		try{
			instance = tegrityAdministrationApplication.createNewMhCampusInstance();
		}catch(Exception e){
			Logger.info("Failed to create MH Campus instance, trying again...");
			browser.pause(60000);
			instance = tegrityAdministrationApplication.createNewMhCampusInstance();
		}
		browser.pause(mhCampusInstanceApplication.CREATE_NEW_INSTANCE_TIMEOUT);	
		
		blackboardApplication.completeMhCampusSetupWithBlackBoard(instance.customerNumber, instance.sharedSecret);
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAndAcceptTermsOfUse(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);	
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
		mhCampusInstanceConnectorsScreen.configureBlackboardAuthorizationConnector(blackboardApplication.title, blackboardApplication.address);
		mhCampusInstanceConnectorsScreen.configureBlackboardAuthenticationConnector(blackboardApplication.title, blackboardApplication.address);
		mhCampusInstanceDashboardScreen = mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
					
		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		
		if(instance != null)
			tegrityAdministrationApplication.deleteMhCampusInstance(instance.customerNumber);
		if(studentId != null) 
			blackBoardApi.deleteUser(studentId);
		if(instructorId != null)
			blackBoardApi.deleteUser(instructorId);
		if(courseId1 != null)
			blackBoardApi.deleteCourse(courseId1);
		if(courseId2 != null)
			blackBoardApi.deleteCourse(courseId2);
		blackBoardApi.logout();
	}

	@Test(description = "Check connectors are availiable")
	public void checkAuthenticationAndAuthorizationConnectorsAreAvailable() throws InterruptedException {

		mhCampusInstanceConnectorsScreen = mhCampusInstanceDashboardScreen.clickManageAairs();

		Assert.assertTrue(mhCampusInstanceConnectorsScreen.isAuthenticationConnectorsAvailable());
		Assert.assertTrue(mhCampusInstanceConnectorsScreen.isAuthorizationConnectorsAvailable());
	}

	@Test(description = "For BlackBoard instructor MH Campus link bahaves correctly", dependsOnMethods = { "checkAuthenticationAndAuthorizationConnectorsAreAvailable" })
	public void checkMHCampusLinkBahavesCorrectlyForBlackBoardInstructor() {

		mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution,
				instructorLogin, instructorLogin);

		String expectedGreetingText = "Hi " + instructorName + " " + instructorSurname + " -";
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(), expectedGreetingText, "Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(), "Rules text is incorrect");

		mhCampusIntroductionScreen.acceptRules();
		
		String expectedUserName = (instructorName + " " + instructorSurname).toUpperCase();	
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName,
				"User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1), "Course "+courseName1+" is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName2), "Course "+courseName2+" is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
	}

	@Test(description = "For BlackBoard student MH Campus link bahaves correctly", dependsOnMethods = { "checkAuthenticationAndAuthorizationConnectorsAreAvailable" })
	public void checkMHCampusLinkBahavesCorrectlyForBlackBoardStudent() {

		mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution,
				studentLogin, studentLogin);

		String expectedGreetingText = "Hi " + studentName + " " + studentSurname + " -";
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(), expectedGreetingText, "Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(), "Rules text is incorrect");
		
		mhCampusIntroductionScreen.acceptRules();
		
		String expectedUserName = (studentName + " " + studentSurname).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName,
				"User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1), "Course "+courseName1+" is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName2), "Course "+courseName2+" is absent");
		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(), "Search option is present");
		
		
	}

	private void prepareTestDataInBlackBoard() throws Exception {

		String studentPrefix = "Student";
		String instructorPrefix = "Instructor";

		String studentRandom = getRandomString();
		String instructorRandom = getRandomString();
		String courseRandom1 = getRandomString();
		String courseRandom2 = getRandomString();

		studentLogin = studentPrefix + studentRandom;
		studentName = studentPrefix + "Name" + studentRandom;
		studentSurname = studentPrefix + "Surname" + studentRandom;

		instructorLogin = instructorPrefix + instructorRandom;
		instructorName = "InstructorName" + instructorRandom;
		instructorSurname = "InstructorSurname" + instructorRandom;
		courseName1 = "Course" + courseRandom1;
		courseName2 = "Course" + courseRandom2;

		blackBoardApi.loginAndInitialiseBlackBoardServices();

		blackBoardApi.deleteAllUsersByLoginPrefix(studentPrefix);
		blackBoardApi.deleteAllUsersByLoginPrefix(instructorPrefix);

		studentId = blackBoardApi.createUser(studentLogin, studentName, studentSurname);
		instructorId = blackBoardApi.createUser(instructorLogin, instructorName, instructorSurname);

		courseId1 = blackBoardApi.createCourse(courseName1);
		courseId2 = blackBoardApi.createCourse(courseName2);

		blackBoardApi.createEnrollment(studentId, courseId1, BlackboardApiRoleIdentifier.STUDENT);
		blackBoardApi.createEnrollment(instructorId, courseId1, BlackboardApiRoleIdentifier.INSTRUCTOR);
		blackBoardApi.createEnrollment(studentId, courseId2, BlackboardApiRoleIdentifier.STUDENT);
		blackBoardApi.createEnrollment(instructorId, courseId2, BlackboardApiRoleIdentifier.INSTRUCTOR);
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}
}
