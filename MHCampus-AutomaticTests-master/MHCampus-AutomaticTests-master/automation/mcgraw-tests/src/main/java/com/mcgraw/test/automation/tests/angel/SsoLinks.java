package com.mcgraw.test.automation.tests.angel;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
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
import com.mcgraw.test.automation.ui.angel.AngelHomeScreen;
import com.mcgraw.test.automation.ui.angel.course.AngelCourseContext.TabMenuItem;
import com.mcgraw.test.automation.ui.angel.course.AngelCourseDetailsScreen;
import com.mcgraw.test.automation.ui.applications.AngelApplication;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class SsoLinks extends BaseTest {

	AngelHomeScreen angelHomeScreen;
	AngelCourseDetailsScreen angelCourseDetailsScreen;
	MhCampusIntroductionScreen mhCampusIntroductionScreen;
	MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;

	@Autowired
	private AngelApplication angelApplication;

	@Autowired
	private AngelApiUtils angelApiUtils;

	private String password = "123qweA@";

	private AddUserModel instructor;
	private String instructorUserName;
	private String instructorFirstname;
	private String instructorLastname;

	private AddUserModel student;
	private String studentUserName;
	private String studentFirstname;
	private String studentLastname;

	private AddSectionModel course1;
	String courseid1;
	String courseName1;
	
	private AddSectionModel course2;
	String courseid2;
	String courseName2;	

	private InstanceCredentials instance;
	
	private boolean checkBoxShowActiveCourse = false;

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
		
		checkBoxShowActiveCourse = tegrityAdministrationApplication.getCheckBoxShowActiveCourse();
		angelApplication.completeMhCampusSetupWithAngel(instance.sharedSecret, instance.customerNumber);
		prepareTestDataInAngel();

		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAndAcceptTermsOfUse(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.configureAngelAuthorizationConnector(angelApplication.angelTitle,
				angelApplication.angelAuthorizationServiceUrl, angelApplication.angelAuthorizationExtendedProperties);
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();

		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass  
	public void testSuiteTearDown() throws Exception {
		
		try{
			if(instance != null)
				tegrityAdministrationApplication.deleteMhCampusInstance(instance.customerNumber);
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
	
	@AfterMethod  
	public void closeAllWindowsExceptFirst() throws Exception {
		browser.closeAllWindowsExceptFirst();
	}

	@Test(description = "For Angel instructor check MH Campus link is present")
	public void checkMHCampusLinkIsPresentInInstructorCourse() throws Exception {
       angelHomeScreen = angelApplication.loginToAngel(instructorUserName, password);
		angelCourseDetailsScreen = angelHomeScreen.setCourseContext(courseName1, TabMenuItem.Course);
		Assert.assertTrue(angelCourseDetailsScreen.isMhCampusLinkPresent(), "MH Campus link is absent for instructor's course " + courseName1);
	}

	@Test(description = "For Angel student check MH Campus link is present")
	public void checkMHCampusLinkIsPresentInStudentCourse() throws Exception {

		angelHomeScreen = angelApplication.loginToAngel(studentUserName, password);
		angelCourseDetailsScreen = angelHomeScreen.setCourseContext(courseName1, TabMenuItem.Course);
		Assert.assertTrue(angelCourseDetailsScreen.isMhCampusLinkPresent(), "MH Campus link is absent for student's course " + courseName1);
	}

	@Test(description = "Check MH Campus link behaves correctly for Angel instructor", dependsOnMethods = { "checkMHCampusLinkIsPresentInInstructorCourse" })
	public void checkMhCampusLinkBehavesCorrectlyForInstructor() throws Exception {

		angelHomeScreen = angelApplication.loginToAngel(instructorUserName, password);
		
		angelCourseDetailsScreen = angelHomeScreen.setCourseContext(courseName1, TabMenuItem.Course);
		mhCampusIntroductionScreen = angelCourseDetailsScreen.clickMhCampusLink();
		
		String expectedGreetingText = "Hi " + instructorFirstname + " " + instructorLastname + " -";
		
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(), expectedGreetingText, "Greetin text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent());
		
		mhCampusIntroductionScreen.acceptRules();
		
		String expectedUserName = (instructorFirstname + " " + instructorLastname).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName, "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1), "Course is absent");
		if(!checkBoxShowActiveCourse){
			Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName2), "Course is absent");
		}
		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
	}

	@Test(description = "Check MH Campus link behaves correctly for Angel student", dependsOnMethods = { "checkMHCampusLinkIsPresentInStudentCourse" })
	public void checkMhCampusLinkBehavesCorrectlyForStudent() throws Exception {

		angelHomeScreen = angelApplication.loginToAngel(studentUserName, password);

		angelCourseDetailsScreen = angelHomeScreen.setCourseContext(courseName1, TabMenuItem.Course);
		mhCampusIntroductionScreen = angelCourseDetailsScreen.clickMhCampusLink();

		String expectedGreetingText = "Hi " + studentFirstname + " " + studentLastname + " -";

		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(), expectedGreetingText, "Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(), "Rules text is incorrect");

		mhCampusIntroductionScreen.acceptRules();

		String expectedUserName = (studentFirstname + " " + studentLastname).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName, "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1), "Course is absent");
		if(!checkBoxShowActiveCourse){
			Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName2), "Course is absent");
		}
		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(), "Search option is present");
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
