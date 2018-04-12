package com.mcgraw.test.automation.tests.blackboard;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.blackboard.serviceapi.BlackBoardApi;
import com.mcgraw.test.automation.api.blackboard.serviceapi.BlackBoardApi.BlackboardApiRoleIdentifier;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.BlackboardApplication;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class TestConnectors extends BaseTest {

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
	
	private String notExistingUsername = "automationTestUser";

	private int numOfSlave = 1;
	
	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	private InstanceCredentials instance;
	
	@BeforeClass
	public void testSuiteSetup() throws Exception {
		
		instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);
		
		blackboardApplication.completeMhCampusSetupWithBlackBoard(instance.customerNumber, instance.sharedSecret);
		prepareTestDataInBlackBoard(); 

		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
		mhCampusInstanceConnectorsScreen.configureBlackboardAuthorizationConnector(blackboardApplication.title, blackboardApplication.address);
		mhCampusInstanceConnectorsScreen.configureBlackboardSsoLinkConnector(blackboardApplication.title, blackboardApplication.address);
		mhCampusInstanceConnectorsScreen.configureBlackboardGradebookConnector(blackboardApplication.title, blackboardApplication.address);		
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();	
				
		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);		
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
		
	@Test(description = "Check Authorization connector behaves correctly for Instructor")
	public void checkAuthorizationConnectorForInstructor() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin,
				instance.institution, instance.username, instance.password);
		
        String instructorFullName = instructorName+" "+instructorSurname;
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthorization(instructorLogin);
		Assert.verifyTrue(result.contains(instructorLogin.toLowerCase()), "Instructor username doesn't present");
		Assert.verifyTrue(result.contains(instructorFullName), "Instructor full name doesn't present");
		Assert.verifyTrue(result.contains(courseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"02"), "Instructor role doesn't right");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthorization(instructorLogin);
		Assert.verifyTrue(result.contains(instructorLogin.toLowerCase()), "Instructor username doesn't present");
		Assert.verifyTrue(result.contains(instructorFullName), "Instructor full name doesn't present");
		Assert.verifyTrue(result.contains(courseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"02"), "Instructor role doesn't right");
	}
	
	@Test(description = "Check Authorization connector behaves correctly for Student")
	public void checkAuthorizationConnectorForStudent() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
			
		String studentFullName = studentName+" "+studentSurname;
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthorization(studentLogin);
		Assert.verifyTrue(result.contains(studentLogin.toLowerCase()), "Student username doesn't present");
		Assert.verifyTrue(result.contains(studentFullName), "Student full name doesn't present");
		Assert.verifyTrue(result.contains(courseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"01"), "Student role doesn't right");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthorization(studentLogin);
		Assert.verifyTrue(result.contains(studentLogin.toLowerCase()), "Student username doesn't present");
		Assert.verifyTrue(result.contains(studentFullName), "Student full name doesn't present");
		Assert.verifyTrue(result.contains(courseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"01"), "Student role doesn't right");
	}
	
	@Test(description = "Check Authorization connector behaves correctly for not existing user")
	public void checkAuthorizationConnectorForNotExistingUser() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthorization(notExistingUsername);
		Assert.verifyFalse(result.contains(notExistingUsername), "The username is present");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthorization(notExistingUsername);
		Assert.verifyFalse(result.contains(notExistingUsername), "The username is present");
	}
	
	@Test(description = "Check SSO Link connector behaves correctly")
	public void checkSsoLinkConnector() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForSsoLink();
		Assert.verifyTrue(result.contains(courseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseName2), "Name of the second course doesn't present");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForSsoLink();
		Assert.verifyTrue(result.contains(courseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseName2), "Name of the second course doesn't present");
	}
	
	@Test(description = "Check Gradebook connector behaves correctly")
	public void checkGradebookConnector() {
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForGradebook();
		Assert.verifyTrue(result.contains("SUCCESS"), "Don't found 'SUCCESS' in the result string");
		Assert.verifyTrue(result.contains("Test Blackboard Was Successful"), "Not found the message in the result string");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForGradebook();
		Assert.verifyTrue(result.contains("SUCCESS"), "Don't found 'SUCCESS' in the result string");
		Assert.verifyTrue(result.contains("Test Blackboard Was Successful"), "Not found the message in the result string");
	}
	
	@Test(description = "Check result of Common 'Test' button for Instructor")
	public void checkResultOfCommonTestButtonForInstructor() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin,
				instance.institution, instance.username, instance.password);
		
		String instructorFullName = instructorName+" "+instructorSurname;
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfCommonTestButton(instructorLogin, instructorLogin);
		Assert.verifyTrue(result.contains(instructorLogin.toLowerCase()), "Instructor username doesn't present");
		Assert.verifyTrue(result.contains(instructorFullName), "Instructor full name doesn't present");
		Assert.verifyTrue(result.contains(courseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"02"), "Instructor role doesn't right");
		
	}
	
	@Test(description = "Check result of Common 'Test' button for Angel Student")
	public void checkResultOfCommonTestButtonForStudent() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String studentFullName = studentName+" "+studentSurname;
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfCommonTestButton(studentLogin, studentLogin);
		Assert.verifyTrue(result.contains(studentLogin.toLowerCase()), "Student username doesn't present");
		Assert.verifyTrue(result.contains(studentFullName), "Student full name doesn't present");
		Assert.verifyTrue(result.contains(courseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"01"), "Student role doesn't right");
	}
	
	@Test(description = "Check result of Common 'Test' button for for not existing user")
	public void checkResultOfCommonTestButtonForNotExistingUser() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfCommonTestButton(notExistingUsername, notExistingUsername);
		Assert.verifyFalse(result.contains(notExistingUsername), "The username is present");
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
