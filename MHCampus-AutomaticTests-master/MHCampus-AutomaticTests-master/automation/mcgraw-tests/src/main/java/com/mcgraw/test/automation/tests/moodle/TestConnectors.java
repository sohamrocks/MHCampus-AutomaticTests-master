package com.mcgraw.test.automation.tests.moodle;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleCategoryRS;
import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleCourseRS;
import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleUserRS;
import com.mcgraw.test.automation.api.rest.moodle.service.MoodleApiUtils;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.MoodleApplication;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class TestConnectors extends BaseTest {

	@Autowired
	private MoodleApplication moodleApplication;
	
	@Autowired
	private MoodleApiUtils moodleApiUtils;

	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom = getRandomString();

	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;
	private String studentSurname = "StudentSurname" + studentRandom;

	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String instructorSurname = "InstructorSurname" + instructorRandom;

	private String category1 = "Category1" + courseRandom;
	private String courseFullName1 = "CourseFull1" + courseRandom;
	private String courseShortName1 = "CourseShort1" + courseRandom;

	private String category2 = "Category2" + courseRandom;
	private String courseFullName2 = "CourseFull2" + courseRandom;
	private String courseShortName2 = "CourseShort2" + courseRandom;

	private String notExistingUsername = "automationTestUser";
	
	private String password = "123qweA@";
	
	private MoodleUserRS studentRS;
	private MoodleUserRS instructorRS;
	private MoodleCourseRS courseRS1;
	private MoodleCategoryRS categoryRS1;
	private MoodleCourseRS courseRS2;
	private MoodleCategoryRS categoryRS2;

	private int numOfSlave = 4;
	
	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	private InstanceCredentials instance;
	
	@BeforeClass
	public void testSuiteSetup() throws Exception {
		
		instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);
			
		prepareDataInMoodle();
		moodleApplication.completeMhCampusSetupWithMoodle(instance.customerNumber, instance.sharedSecret);
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
		mhCampusInstanceConnectorsScreen.configureMoodleAuthenticationConnector(moodleApplication.moodleAuthenticationExtendedProperties
				.replace("<secret_value>", instance.sharedSecret));
		mhCampusInstanceConnectorsScreen.configureMoodleAuthorizationConnector(moodleApplication.moodleAuthorizationExtendedProperties
				.replace("<secret_value>", instance.sharedSecret));
		mhCampusInstanceConnectorsScreen.configureMoodleGradebookConnector(moodleApplication.moodleGradebookExtendedProperties);		
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		
		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		
		if(categoryRS1 != null)
			moodleApiUtils.deleteCategoryWithCourses(categoryRS1);
		if(categoryRS2 != null)
			moodleApiUtils.deleteCategoryWithCourses(categoryRS2);
		if(studentRS != null)
			moodleApiUtils.deleteUser(studentRS);
		if(instructorRS != null)
			moodleApiUtils.deleteUser(instructorRS);
	}
		
	@Test(description = "Check Authorization connector behaves correctly for Instructor")
	public void checkAuthorizationConnectorForInstructor() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin,
				instance.institution, instance.username, instance.password);
		
		String instructorFullName = instructorName+" "+instructorSurname;
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthorization(instructorLogin);
		Assert.verifyTrue(result.contains(instructorLogin), "Instructor username doesn't present");
		Assert.verifyTrue(result.contains(instructorFullName), "Instructor full name doesn't present");
		Assert.verifyTrue(result.contains(courseShortName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseShortName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"02"), "Instructor role doesn't right");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthorization(instructorLogin);
		Assert.verifyTrue(result.contains(instructorLogin), "Instructor username doesn't present");
		Assert.verifyTrue(result.contains(instructorFullName), "Instructor full name doesn't present");
		Assert.verifyTrue(result.contains(courseShortName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseShortName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"02"), "Instructor role doesn't right");
	}
	
	@Test(description = "Check Authorization connector behaves correctly for Student")
	public void checkAuthorizationConnectorForStudent() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String studentFullName = studentName+" "+studentSurname;
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthorization(studentLogin);
		Assert.verifyTrue(result.contains(studentLogin), "Student username doesn't present");
		Assert.verifyTrue(result.contains(studentFullName), "Student full name doesn't present");
		Assert.verifyTrue(result.contains(courseShortName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseShortName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"01"), "Student role doesn't right");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthorization(studentLogin);
		Assert.verifyTrue(result.contains(studentLogin), "Student username doesn't present");
		Assert.verifyTrue(result.contains(studentFullName), "Student full name doesn't present");
		Assert.verifyTrue(result.contains(courseShortName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseShortName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"01"), "Student role doesn't right");
	}
	
	@Test(description = "Check Authorization connector behaves correctly for not existing user")
	public void checkAuthorizationConnectorForNotExistingUser() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String subMessage = "'" + notExistingUsername + "' not found";
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthorization(notExistingUsername);
		Assert.verifyTrue(result.contains(subMessage), "User not found message doesn't present");
		Assert.verifyTrue(result.contains("An error occured during request processing"), "Not found the message in the result string");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthorization(notExistingUsername);
		Assert.verifyTrue(result.contains(subMessage), "User not found message doesn't present");
		Assert.verifyTrue(result.contains("An error occured during request processing"), "Not found the message in the result string");
	}
	
	@Test(description = "Check Authentication connector behaves correctly for Instructor")
	public void checkAuthenticationConnectorForInstructor() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthentication(instructorLogin, password);
		Assert.verifyTrue(result.contains(instructorLogin), "Instructor username doesn't present");
		Assert.verifyTrue(result.contains("SUCCESS"), "Don't found 'SUCCESS' in the result string");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthentication(instructorLogin, password);
		Assert.verifyTrue(result.contains(instructorLogin), "Instructor username doesn't present");
		Assert.verifyTrue(result.contains("SUCCESS"), "Don't found 'SUCCESS' in the result string");
	}
	
	@Test(description = "Check Authentication connector behaves correctly for Student")
	public void checkAuthenticationConnectorForStudent() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthentication(studentLogin, password);
		Assert.verifyTrue(result.contains(studentLogin), "Student username doesn't present");
		Assert.verifyTrue(result.contains("SUCCESS"), "Don't found 'SUCCESS' in the result string");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthentication(studentLogin, password);
		Assert.verifyTrue(result.contains(studentLogin), "Student username doesn't present");
		Assert.verifyTrue(result.contains("SUCCESS"), "Don't found 'SUCCESS' in the result string");
	}
	
	@Test(description = "Check Authentication connector behaves correctly for for not existing user")
	public void checkAuthenticationConnectorForNotExistingUser() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthentication(notExistingUsername, password);
		Assert.verifyTrue(result.contains("FAILURE"), "Don't found 'FAILURE' in the result string");
		Assert.verifyTrue(result.contains("moodleError"), "Don't found 'moodleError' in the result string");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthentication(notExistingUsername, password);
		Assert.verifyTrue(result.contains("FAILURE"), "Don't found 'FAILURE' in the result string");
		Assert.verifyTrue(result.contains("moodleError"), "Don't found 'moodleError' in the result string");
	}
	
	@Test(description = "Check Gradebook connector behaves correctly")
	public void checkGradebookConnector() {
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForGradebook();
		Assert.verifyTrue(result.contains("SUCCESS"), "Don't found 'SUCCESS' in the result string");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForGradebook();
		Assert.verifyTrue(result.contains("SUCCESS"), "Don't found 'SUCCESS' in the result string");
	}
	
	@Test(description = "Check result of Common 'Test' button for Instructor")
	public void checkResultOfCommonTestButtonForInstructor() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin,
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfCommonTestButton(instructorLogin, password);
		Assert.verifyTrue(result.contains(instructorLogin), "Instructor username doesn't present");
		Assert.verifyTrue(result.contains("SUCCESS"), "Don't found 'SUCCESS' in the result string");
	}
	
	@Test(description = "Check result of Common 'Test' button for Student")
	public void checkResultOfCommonTestButtonForStudent() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfCommonTestButton(studentLogin, password);
		Assert.verifyTrue(result.contains(studentLogin), "Student username doesn't present");
		Assert.verifyTrue(result.contains("SUCCESS"), "Don't found 'SUCCESS' in the result string");
	}
	
	@Test(description = "Check result of Common 'Test' button for for not existing user")
	public void checkResultOfCommonTestButtonForNotExistingUser() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfCommonTestButton(notExistingUsername, password);
		Assert.verifyTrue(result.contains("FAILURE"), "Don't found 'FAILURE' in the result string");
		Assert.verifyTrue(result.contains("moodleError"), "Don't found 'moodleError' in the result string");
		Assert.verifyTrue(result.contains("An error occured during request processing"), "Not found the message in the result string");
	}
	
	private String getRandomString() {
		return RandomStringUtils.randomNumeric(5);
	}

	public void prepareDataInMoodle() throws Exception {
		
		studentRS = moodleApiUtils.createUser(studentLogin, password, studentName, studentSurname);
		instructorRS = moodleApiUtils.createUser(instructorLogin, password, instructorName, instructorSurname);

		categoryRS1 = moodleApiUtils.createCategory(category1);
		courseRS1 = moodleApiUtils.createCourseInsideCategory(courseFullName1, courseShortName1, categoryRS1);
		categoryRS2 = moodleApiUtils.createCategory(category2);
		courseRS2 = moodleApiUtils.createCourseInsideCategory(courseFullName2, courseShortName2, categoryRS2);

		moodleApiUtils.enrollToCourseAsStudent(studentRS, courseRS1);
		moodleApiUtils.enrollToCourseAsInstructor(instructorRS, courseRS1);
		moodleApiUtils.enrollToCourseAsStudent(studentRS, courseRS2);
		moodleApiUtils.enrollToCourseAsInstructor(instructorRS, courseRS2);
	}
}
