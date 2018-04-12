package com.mcgraw.test.automation.tests.angel;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.angel.AngelSectionRole;
import com.mcgraw.test.automation.api.rest.angel.model.AddSectionModel;
import com.mcgraw.test.automation.api.rest.angel.model.AddUserModel;
import com.mcgraw.test.automation.api.rest.angel.service.AngelApiUtils;
import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.AngelApplication;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class TestConnectors extends BaseTest {

	@Autowired
	private AngelApplication angelApplication;

	@Autowired
	private AngelApiUtils angelApiUtils;

	private String password = "123qweA@";

	private String notExistingUsername = "automationTestUser";
	
	private AddUserModel instructor;
	private String instructorUserName;
	private String instructorFirstname;
	private String instructorLastname;

	private AddUserModel student;
	private String studentUserName;
	private String studentFirstname;
	private String studentLastname;

	private AddSectionModel course1;
	private String courseid1;
	private String courseName1;
	
	private AddSectionModel course2;
	private String courseid2;
	private String courseName2;	

	private int numOfSlave = 2;
	
	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	private InstanceCredentials instance;
	
	@BeforeClass
	public void testSuiteSetup() throws Exception {
		
		instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);

		angelApplication.completeMhCampusSetupWithAngel(instance.sharedSecret, instance.customerNumber);
		prepareTestDataInAngel();

		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
		mhCampusInstanceConnectorsScreen.configureAngelAuthorizationConnector(angelApplication.angelTitle,
				angelApplication.angelAuthorizationServiceUrl, angelApplication.angelAuthorizationExtendedProperties);
		mhCampusInstanceConnectorsScreen.configureAngelAuthenticationConnector(angelApplication.angelTitle,
				angelApplication.angelAuthenticationServiceUrl, angelApplication.angelAuthenticationExtendedProperties);	
		mhCampusInstanceConnectorsScreen.configureAngelGradebookConnector(angelApplication.angelTitle,
				angelApplication.angelGradeBookServiceUrl, angelApplication.angelGradebookExtendedProperties);		
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		
		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {

		try{
			if(courseid1 != null)
				angelApplication.deleteCourseWithEnrollUsers(courseid1);
			if(courseid2 != null)
				angelApplication.deleteCourseWithEnrollUsers(courseid2);
			if(instructorUserName != null)
				angelApplication.deleteUserFromAngel(instructorUserName);
			if(studentUserName != null)
				angelApplication.deleteUserFromAngel(studentUserName);
		}catch(Exception e){
			Logger.info("Error happens during deliting users/courses from Angel LMS...");
		}
	}
		
	@Test(description = "Check Authorization connector behaves correctly for Instructor")
	public void checkAuthorizationConnectorForInstructor() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String instructorFullName = instructorFirstname+" "+instructorLastname;
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthorization(instructorUserName);
		Assert.verifyTrue(result.contains(instructorUserName), "Instructor username doesn't present");
		Assert.verifyTrue(result.contains(instructorFullName), "Instructor full name doesn't present");
		Assert.verifyTrue(result.contains(courseid1), "Id of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseid2), "Id of the second course doesn't present");
		Assert.verifyTrue(result.contains(courseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"02"), "Instructor role doesn't right");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthorization(instructorUserName);
		Assert.verifyTrue(result.contains(instructorUserName), "Instructor username doesn't present");
		Assert.verifyTrue(result.contains(instructorFullName), "Instructor full name doesn't present");
		Assert.verifyTrue(result.contains(courseid1), "Id of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseid2), "Id of the second course doesn't present");
		Assert.verifyTrue(result.contains(courseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"02"), "Instructor role doesn't right");
	}
	
	@Test(description = "Check Authorization connector behaves correctly for Student")
	public void checkAuthorizationConnectorForStudent() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String studentFullName = studentFirstname+" "+studentLastname;
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthorization(studentUserName);
		Assert.verifyTrue(result.contains(studentUserName), "Student username doesn't present");
		Assert.verifyTrue(result.contains(studentFullName), "Student full name doesn't present");
		Assert.verifyTrue(result.contains(courseid1), "Id of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseid2), "Id of the second course doesn't present");
		Assert.verifyTrue(result.contains(courseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"01"), "Student role doesn't right");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthorization(studentUserName);
		Assert.verifyTrue(result.contains(studentUserName), "Student username doesn't present");
		Assert.verifyTrue(result.contains(studentFullName), "Student full name doesn't present");
		Assert.verifyTrue(result.contains(courseid1), "Id of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseid2), "Id of the second course doesn't present");
		Assert.verifyTrue(result.contains(courseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"01"), "Student role doesn't right");
	}
	
	@Test(description = "Check Authorization connector behaves correctly for not existing user")
	public void checkAuthorizationConnectorForNotExistingUser() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthorization(notExistingUsername);
		Assert.verifyFalse(result.contains(notExistingUsername), "Instructor username is present");
		Assert.verifyTrue(result.contains("An error occured during request processing"), "Not found the message in the result string");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthorization(notExistingUsername);
		Assert.verifyFalse(result.contains(notExistingUsername), "Instructor username is present");
		Assert.verifyTrue(result.contains("An error occured during request processing"), "Not found the message in the result string");
	}
	
	@Test(description = "Check Authentication connector behaves correctly for Instructor")
	public void checkAuthenticationConnectorForInstructor() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthentication(instructorUserName, password);
		Assert.verifyTrue(result.contains(instructorUserName), "Instructor username doesn't present");
		Assert.verifyTrue(result.contains("SUCCESS"), "Don't found 'SUCCESS' in the result string");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthentication(instructorUserName, password);
		Assert.verifyTrue(result.contains(instructorUserName), "Instructor username doesn't present");
		Assert.verifyTrue(result.contains("SUCCESS"), "Don't found 'SUCCESS' in the result string");
	}
	
	@Test(description = "Check Authentication connector behaves correctly for Student")
	public void checkAuthenticationConnectorForStudent() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthentication(studentUserName, password);
		Assert.verifyTrue(result.contains(studentUserName), "Student username doesn't present");
		Assert.verifyTrue(result.contains("SUCCESS"), "Don't found 'SUCCESS' in the result string");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthentication(studentUserName, password);
		Assert.verifyTrue(result.contains(studentUserName), "Student username doesn't present");
		Assert.verifyTrue(result.contains("SUCCESS"), "Don't found 'SUCCESS' in the result string");
	}
	
	@Test(description = "Check Authentication connector behaves correctly for for not existing user")
	public void checkAuthenticationConnectorForNotExistingUser() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthentication(notExistingUsername, password);
		Assert.verifyFalse(result.contains(notExistingUsername), "Instructor username is present");
		Assert.verifyTrue(result.contains("FAILURE"), "Don't found 'FAILURE' in the result string");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthentication(notExistingUsername, password);
		Assert.verifyFalse(result.contains(notExistingUsername), "Instructor username is present");
		Assert.verifyTrue(result.contains("FAILURE"), "Don't found 'FAILURE' in the result string");
	}
	
	@Test(description = "Check Gradebook connector behaves correctly")
	public void checkGradebookConnector() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForGradebook();
		Assert.verifyTrue(result.contains("Testing is not yet supported for this connector type"), "A message doesn't present");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForGradebook();
		Assert.verifyTrue(result.contains("Testing is not yet supported for this connector type"), "A message doesn't present");
	}
	
	@Test(description = "Check result of Common 'Test' button for Instructor")
	public void checkResultOfCommonTestButtonForInstructor() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin,
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfCommonTestButton(instructorUserName, password);
		String instructorFullName = instructorFirstname+" "+instructorLastname;
		Assert.verifyTrue(result.contains(instructorUserName), "Instructor username doesn't present");
		Assert.verifyTrue(result.contains(instructorFullName), "Instructor full name doesn't present");
		Assert.verifyTrue(result.contains(courseid1), "Id of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseid2), "Id of the second course doesn't present");
		Assert.verifyTrue(result.contains(courseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"02"), "Instructor role doesn't right");
	}
	
	@Test(description = "Check result of Common 'Test' button fo Student")
	public void checkResultOfCommonTestButtonForStudent() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfCommonTestButton(studentUserName, password);
		String studentFullName = studentFirstname+" "+studentLastname;
		Assert.verifyTrue(result.contains(studentUserName), "Student username doesn't present");
		Assert.verifyTrue(result.contains(studentFullName), "Student full name doesn't present");
		Assert.verifyTrue(result.contains(courseid1), "Id of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseid2), "Id of the second course doesn't present");
		Assert.verifyTrue(result.contains(courseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"01"), "Student role doesn't right");
	}
	
	@Test(description = "Check result of Common 'Test' button for for not existing user")
	public void checkResultOfCommonTestButtonForNotExistingUser() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfCommonTestButton(notExistingUsername, password);
		Assert.verifyTrue(result.contains("An error occured during request processing"), "Not found the message in the result string");
		Assert.verifyTrue(result.contains("FAILURE"), "Don't found 'FAILURE' in the result string");	
	}
	
	private void prepareTestDataInAngel() throws RestEndpointIOException, UnsupportedEncodingException {
		String instructorRandom = getRandomString();
		String student1Random = getRandomString();
		String courseRandom1 = getRandomString();
		String courseRandom2 = getRandomString();

		instructorUserName = "instructor" + instructorRandom;
		instructorFirstname = "instructorFirstname" + instructorRandom;
		instructorLastname = "instructorLastname" + instructorRandom;

		studentUserName = "student" + student1Random;
		studentFirstname = "studentFirstname" + student1Random;
		studentLastname = "studentLastname" + student1Random;

		courseid1 = "id" + courseRandom1;
		courseName1 = "courseName" + courseRandom1;		
		courseid2 = "id" + courseRandom2;
		courseName2 = "courseName" + courseRandom2;		

		student = angelApiUtils.createStudent(studentUserName, password, studentFirstname, studentLastname);
		instructor = angelApiUtils.createInstructor(instructorUserName, password, instructorFirstname, instructorLastname);
		course1 = angelApiUtils.createCourse(instructor, courseid1, courseName1);
		course2 = angelApiUtils.createCourse(instructor, courseid2, courseName2);
		angelApiUtils.enrolUserToCourse(student, course1, AngelSectionRole.STUDENT);
		angelApiUtils.enrolUserToCourse(student, course2, AngelSectionRole.STUDENT);
	}

	private String getRandomString() {
		return RandomStringUtils.randomNumeric(5);
	}
}
