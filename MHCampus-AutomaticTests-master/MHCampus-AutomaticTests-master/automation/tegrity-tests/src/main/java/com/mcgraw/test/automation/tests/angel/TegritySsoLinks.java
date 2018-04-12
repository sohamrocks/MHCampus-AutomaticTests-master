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
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.angel.AngelHomeScreen;
import com.mcgraw.test.automation.ui.angel.course.AngelCourseContext.TabMenuItem;
import com.mcgraw.test.automation.ui.angel.course.AngelCourseDetailsScreen;
import com.mcgraw.test.automation.ui.applications.AngelApplication;
import com.mcgraw.test.automation.ui.tegrity.TegrityCourseDetailsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityIntroductionScreen;

public class TegritySsoLinks extends BaseTest {
	
	private static final String ANGEL_SUB_URL = "angel";

	private AngelHomeScreen angelHomeScreen;
	private AngelCourseDetailsScreen angelCourseDetailsScreen;
	
	private TegrityIntroductionScreen tegrityIntroductionScreen;
	private TegrityInstanceConnectorsScreen tegrityInstanceConnectorsScreen;
	private TegrityCourseDetailsScreen tegrityCourseDetailsScreen;

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

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		
		
		angelApplication.completeTegritySetupWithAngel(tegrityInstanceApplication.sharedSecret, tegrityInstanceApplication.customerNumber);			
		prepareTestDataInAngel();

		tegrityInstanceConnectorsScreen = tegrityInstanceApplication.loginToTegrityInstanceAsAdminAndClickManageAairsLink();			
		tegrityInstanceConnectorsScreen.deleteAllConnectors();		
		tegrityInstanceConnectorsScreen.configureAngelAuthorizationConnector(angelApplication.angelTitle,
				angelApplication.angelAuthorizationServiceUrl, angelApplication.angelAuthorizationExtendedProperties);
		tegrityInstanceConnectorsScreen.clickSaveAndContinueButton();
		browser.pause(tegrityInstanceApplication.DIRECT_LOGIN_TIMEOUT);
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass  
	public void testSuiteTearDown() throws Exception {
		
		if(courseid1 != null)
			angelApplication.deleteCourseWithEnrollUsers(courseid1);
		if(courseid2 != null)
			angelApplication.deleteCourseWithEnrollUsers(courseid2);
		if(instructorUserName != null)
			angelApplication.deleteUserFromAngel(instructorUserName);
		if(studentUserName != null)
			angelApplication.deleteUserFromAngel(studentUserName);
	}

	@AfterMethod  
	public void closeAllWindowsExceptFirst() throws Exception {
		browser.closeAllWindowsExceptCurrentWithSubURL(ANGEL_SUB_URL);
		angelApplication.logoutFromAngel();
	}
	
	@Test(description = "For Angel instructor check Tegrity link is present")
	public void checkTegrityLinkIsPresentInInstructorCourse() throws Exception {

		angelHomeScreen = angelApplication.loginToAngel(instructorUserName, password);
		angelCourseDetailsScreen = angelHomeScreen.setCourseContext(courseName1, TabMenuItem.Course);
		Assert.assertTrue(angelCourseDetailsScreen.isTegrityCampusLinkPresent(), "Tegrity Campus link is absent for instructor's course " + courseName1);
	}

	@Test(description = "For Angel student check Tegrity link is present")
	public void checkTegrityLinkIsPresentInStudentCourse() throws Exception {

		angelHomeScreen = angelApplication.loginToAngel(studentUserName, password);
		angelCourseDetailsScreen = angelHomeScreen.setCourseContext(courseName1, TabMenuItem.Course);
		Assert.assertTrue(angelCourseDetailsScreen.isTegrityCampusLinkPresent(), "Tegrity Campus link is absent for student's course " + courseName1);
	}

	@Test(description = "Check Tegrity link behaves correctly for Angel instructor", dependsOnMethods = { "checkTegrityLinkIsPresentInInstructorCourse" })
	public void checkTegrityLinkBehavesCorrectlyForInstructor() throws Exception {

		angelHomeScreen = angelApplication.loginToAngel(instructorUserName, password);		
		angelCourseDetailsScreen = angelHomeScreen.setCourseContext(courseName1, TabMenuItem.Course);
		tegrityCourseDetailsScreen = angelCourseDetailsScreen.clickTegrityCampusLink();
		
		if(!tegrityCourseDetailsScreen.isCoursePresent(courseName1)){
			browser.navigate().refresh();
			browser.pause(6000);
			browser.waitForPage(TegrityCourseDetailsScreen.class, 30);
		}

		String expectedGreetingText = instructorFirstname + " " + instructorLastname;
		
		Assert.verifyEquals(tegrityCourseDetailsScreen.getUserNameText(), expectedGreetingText, "Greetin text is incorrect");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isCoursePresent(courseName1), "Course is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isSearchOptionPresent(), "Search option is absent");		
	}
	
	@Test(description = "Check Tegrity link behaves correctly for Angel student", dependsOnMethods = { "checkTegrityLinkIsPresentInStudentCourse" })
	public void checkTegrityLinkBehavesCorrectlyForStudent() throws Exception {

		angelHomeScreen = angelApplication.loginToAngel(studentUserName, password);
		angelCourseDetailsScreen = angelHomeScreen.setCourseContext(courseName1, TabMenuItem.Course);
		tegrityCourseDetailsScreen = angelCourseDetailsScreen.clickTegrityCampusLink();

		if(!tegrityCourseDetailsScreen.isCoursePresent(courseName1)){
			browser.navigate().refresh();
			browser.pause(6000);
			browser.waitForPage(TegrityCourseDetailsScreen.class, 30);
		}
		
		String expectedGreetingText = studentFirstname + " " + studentLastname;

		Assert.verifyEquals(tegrityCourseDetailsScreen.getUserNameText(), expectedGreetingText, "Greeting text is incorrect");
		Assert.verifyFalse(tegrityCourseDetailsScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isCoursePresent(courseName1), "Course is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isSearchOptionPresent(), "Search option is absent");		
	}
	
	@Test(description = "Check Tegrity Welcome page for Angel instructor", dependsOnMethods = { "checkTegrityLinkIsPresentInInstructorCourse" })
	public void checkTegrityWelcomePageForInstructor() throws Exception {
	
		angelHomeScreen = angelApplication.loginToAngel(instructorUserName, password);
		angelCourseDetailsScreen = angelHomeScreen.setCourseContext(courseName2, TabMenuItem.Course);
		tegrityCourseDetailsScreen = angelCourseDetailsScreen.clickTegrityCampusLink();
		tegrityIntroductionScreen = tegrityCourseDetailsScreen.goToCourses();

		String expectedUserName = instructorFirstname + " " + instructorLastname;   
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), expectedUserName);	
			
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName2), "Course " + courseName2 + " is absent");
		String sandboxCourse = instructorFirstname + " " + instructorLastname+ " " + "sandbox course";   
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is absent");	
		
		Assert.verifyTrue(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");	
	}

	@Test(description = "Check Tegrity Welcome page for Angel student", dependsOnMethods = { "checkTegrityLinkIsPresentInStudentCourse" })
	public void checkTegrityWelcomePageForStudent() throws Exception {
		
		angelHomeScreen = angelApplication.loginToAngel(studentUserName, password);
		angelCourseDetailsScreen = angelHomeScreen.setCourseContext(courseName2, TabMenuItem.Course);
		tegrityCourseDetailsScreen = angelCourseDetailsScreen.clickTegrityCampusLink();
		tegrityIntroductionScreen = tegrityCourseDetailsScreen.goToCourses();

		String expectedUserName = studentFirstname + " " + studentLastname;
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), expectedUserName);
				
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName2), "Course " + courseName2 + " is absent");	
		String sandboxCourse = "sandbox course";   
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is present");
		
		Assert.verifyFalse(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
	}

	private void prepareTestDataInAngel() throws RestEndpointIOException, UnsupportedEncodingException {
		
		String instructorRandom = getRandomString(10);
		String student1Random = getRandomString(10);
		String courseRandom1 = getRandomString(5);
		String courseRandom2 = getRandomString(5);

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

	private String getRandomString(int count) {
		return RandomStringUtils.randomNumeric(count);
	}
}
