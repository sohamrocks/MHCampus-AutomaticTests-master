package com.mcgraw.test.automation.tests.blackboard;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.blackboard.serviceapi.BlackBoardApi;
import com.mcgraw.test.automation.api.blackboard.serviceapi.BlackBoardApi.BlackboardApiRoleIdentifier;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.BlackboardApplication;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceDashboardScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityIntroductionScreen;

public class TegrityDirectLoginTest extends BaseTest {

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

	private TegrityInstanceConnectorsScreen tegrityInstanceConnectorsScreen;
	private TegrityInstanceDashboardScreen tegrityInstanceDashboardScreen;
	private TegrityIntroductionScreen tegrityIntroductionScreen;

	@BeforeClass
	public void testSuiteSetup() throws Exception {
			
		blackboardApplication.completeTegritySetupWithBlackBoard(tegrityInstanceApplication.customerNumber, tegrityInstanceApplication.sharedSecret);
		prepareTestDataInBlackBoard(); 

		tegrityInstanceConnectorsScreen = tegrityInstanceApplication.loginToTegrityInstanceAsAdminAndClickManageAairsLink();
		tegrityInstanceConnectorsScreen.deleteAllConnectors();
		tegrityInstanceConnectorsScreen.configureBlackboardAuthorizationConnector(blackboardApplication.title, blackboardApplication.address);
		tegrityInstanceConnectorsScreen.configureBlackboardAuthenticationConnector(blackboardApplication.title, blackboardApplication.address);
		tegrityInstanceDashboardScreen = tegrityInstanceConnectorsScreen.clickSaveAndContinueButton();	
		
		browser.pause(tegrityInstanceApplication.DIRECT_LOGIN_TIMEOUT);
		
		
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		
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

		tegrityInstanceConnectorsScreen = tegrityInstanceDashboardScreen.clickManageAairs();
		Assert.assertTrue(tegrityInstanceConnectorsScreen.isAuthenticationConnectorsAvailable());
		Assert.assertTrue(tegrityInstanceConnectorsScreen.isAuthorizationConnectorsAvailable());
		tegrityInstanceConnectorsScreen.logOut();
	}

	@Test(description = "For BlackBoard instructor Tegrity link bahaves correctly", dependsOnMethods = { "checkAuthenticationAndAuthorizationConnectorsAreAvailable" })
	public void checkTegrityLinkBahavesCorrectlyForBlackBoardInstructor() {

		tegrityIntroductionScreen = tegrityInstanceApplication.loginToTegrityAsUser(instructorLogin, instructorLogin);

		String expectedUserName = (instructorName + " " + instructorSurname);   
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), expectedUserName);		
		
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName2), "Course " + courseName2 + " is absent");
		String sandboxCourse = (instructorName + " " + instructorSurname+ " " + "sandbox course");   
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is absent");
		
		Assert.verifyTrue(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
		
		tegrityIntroductionScreen.logOut();
		
	}
	
	@Test(description = "For BlackBoard student Tegrity link bahaves correctly", dependsOnMethods = { "checkAuthenticationAndAuthorizationConnectorsAreAvailable" })
	public void checkTegrityLinkBahavesCorrectlyForBlackBoardStudent() {

		tegrityIntroductionScreen = tegrityInstanceApplication.loginToTegrityAsUser(studentLogin, studentLogin);

		String expectedUserName = (studentName + " " + studentSurname);
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), expectedUserName);
				
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName2), "Course " + courseName2 + " is absent");	
		String sandboxCourse = "sandbox course";   
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is present");
		
		Assert.verifyFalse(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
		
		tegrityIntroductionScreen.logOut();
		
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
		return RandomStringUtils.randomAlphanumeric(6);
	}
}
