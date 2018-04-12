package com.mcgraw.test.automation.tests.canvas;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasCourseRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUser;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserEnrollmentRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserRS;
import com.mcgraw.test.automation.api.rest.canvas.service.CanvasApiUtils;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.CanvasApplication;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class TestConnectors extends BaseTest {

	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom1 = getRandomString();
	private String courseRandom2 = getRandomString();

	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;
	private String internalStudentID; 

	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String internalInstructorID;

	private String courseName1 = "CourseName1" + courseRandom1;
	private String courseName2 = "CourseName2" + courseRandom2;

	private String notExistingUsername = "automationTestUser";
	
	private String password = "123qweA@";

	private CanvasUser student;
	private CanvasUser instructor;
	private CanvasCourseRS course1;
	private CanvasCourseRS course2;
	private CanvasUserEnrollmentRS studentEnrollment1;
	private CanvasUserEnrollmentRS instructorEnrollment1;
	private CanvasUserEnrollmentRS studentEnrollment2;
	private CanvasUserEnrollmentRS instructorEnrollment2;

	private int numOfSlave = 3;
	
	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;	
	private InstanceCredentials instance;
	
	@BeforeClass
	public void testSuiteSetup() throws Exception {
		
		prepareTestDataInCanvas();
		
		instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
		mhCampusInstanceConnectorsScreen.configureCanvasAuthorizationConnector(canvasApplication.canvasTitle, canvasApplication.canvasFqdn,
				canvasApplication.canvasAccessToken, canvasApplication.canvasUserIdOrigin, canvasApplication.canvasCourseIdOrigin,
				canvasApplication.canvasSecureGateway);
		mhCampusInstanceConnectorsScreen.configureCanvasSsoLinkConnector(canvasApplication.canvasTitle, canvasApplication.canvasFqdn,
				canvasApplication.canvasAccessToken, canvasApplication.canvasInterlinkType, canvasApplication.canvasUserIdOrigin,
				canvasApplication.canvasCourseIdOrigin, canvasApplication.canvasSecureGateway);
		mhCampusInstanceConnectorsScreen.configureCanvasGradebookConnector(canvasApplication.canvasTitle, canvasApplication.canvasFqdn,
				canvasApplication.canvasAccessToken, canvasApplication.canvasUserIdOrigin, canvasApplication.canvasCourseIdOrigin);		
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();	
		
		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);
		
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearCanvasData();
	}
		
	@Test(description = "Check Authorization connector behaves correctly for Instructor")
	public void checkAuthorizationConnectorForInstructor() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin,
				instance.institution, instance.username, instance.password);
		//browser.pause(1000);
        String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthorization(internalInstructorID);
        //browser.pause(90000);
		Assert.verifyTrue(result.contains(internalInstructorID), "Instructor Internal ID doesn't present");
		Assert.verifyTrue(result.contains(instructorName), "Instructor name doesn't present");
		Assert.verifyTrue(result.contains(courseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"02"), "Instructor role doesn't right");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthorization(internalInstructorID);
		Assert.verifyTrue(result.contains(internalInstructorID), "Instructor Internal ID doesn't present");
		Assert.verifyTrue(result.contains(instructorName), "Instructor name doesn't present");
		Assert.verifyTrue(result.contains(courseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"02"), "Instructor role doesn't right");
	}
	
	@Test(description = "Check Authorization connector behaves correctly for Student")
	public void checkAuthorizationConnectorForStudent() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
			
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthorization(internalStudentID);
		Assert.verifyTrue(result.contains(internalStudentID), "Student Internal ID doesn't present");
		Assert.verifyTrue(result.contains(studentName), "Student name doesn't present");
		Assert.verifyTrue(result.contains(courseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"01"), "Student role doesn't right");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthorization(internalStudentID);
		Assert.verifyTrue(result.contains(internalStudentID), "Student Internal ID doesn't present");
		Assert.verifyTrue(result.contains(studentName), "Student name doesn't present");
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
		Assert.verifyTrue(result.contains("Testing Canvas agent is not supported"), "Not found the message in the result string");
	}
	
	@Test(description = "Check Gradebook connector behaves correctly")
	public void checkGradebookConnector() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForGradebook();
		Assert.verifyTrue(result.contains("SUCCESS"), "Don't found 'SUCCESS' in the result string");
		Assert.verifyTrue(result.contains("Account successfully validated"), "Not found the message in the result string");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForGradebook();
		Assert.verifyTrue(result.contains("SUCCESS"), "Don't found 'SUCCESS' in the result string");
		Assert.verifyTrue(result.contains("Account successfully validated"), "Not found the message in the result string");
	}
	
	@Test(description = "Check result of Common 'Test' button for Instructor")
	public void checkResultOfCommonTestButtonForInstructor() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin,
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfCommonTestButton(internalInstructorID, password);
		Assert.verifyTrue(result.contains("A problem occurred during agent test, please check your configuration"), "Not found the message in the result string");		
	}
	
	@Test(description = "Check result of Common 'Test' button for Angel Student")
	public void checkResultOfCommonTestButtonForStudent() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfCommonTestButton(internalStudentID, password);
		Assert.verifyTrue(result.contains("A problem occurred during agent test, please check your configuration"), "Not found the message in the result string");
	}
	
	@Test(description = "Check result of Common 'Test' button for for not existing user")
	public void checkResultOfCommonTestButtonForNotExistingUser() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfCommonTestButton(notExistingUsername, password);
		Assert.verifyTrue(result.contains("A problem occurred during agent test, please check your configuration"), "Not found the message in the result string");
	}
	
	private void prepareTestDataInCanvas() throws Exception {

		student = canvasApiUtils.createUser(studentLogin, password, studentName);
		instructor = canvasApiUtils.createUser(instructorLogin, password, instructorName);
		course1 = canvasApiUtils.createPublishedCourse(courseName1);
		studentEnrollment1 = canvasApiUtils.enrollToCourseAsActiveStudent(student, course1);
		instructorEnrollment1 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor, course1);
		course2 = canvasApiUtils.createPublishedCourse(courseName2);
		studentEnrollment2 = canvasApiUtils.enrollToCourseAsActiveStudent(student, course2);
		instructorEnrollment2 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor, course2);
		
		internalInstructorID = Integer.toString(instructor.getId());
		internalStudentID = Integer.toString(student.getId());
	}
	
	private void clearCanvasData() throws Exception {
		
		if((studentEnrollment1 != null) & (course1 != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment1, course1);
		if((instructorEnrollment1 != null) & (course1 != null))
		    canvasApiUtils.deleteEnrollment(instructorEnrollment1, course1);
		if(course1 != null)
			canvasApiUtils.deleteCourse(course1);
		
		if((studentEnrollment2 != null) & (course2 != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment2, course2);
		if((instructorEnrollment2 != null) & (course2 != null))
			canvasApiUtils.deleteEnrollment(instructorEnrollment2, course2);
		if(course2 != null)
			canvasApiUtils.deleteCourse(course2);
		
		if(student != null) {
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(student);
			canvasApiUtils.deleteUser(studentToDelete);
		}	
		if(instructor != null) {
			CanvasUserRS instructorToDelete = new CanvasUserRS();
			instructorToDelete.setUser(instructor);
			canvasApiUtils.deleteUser(instructorToDelete);
		}	
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}
}
