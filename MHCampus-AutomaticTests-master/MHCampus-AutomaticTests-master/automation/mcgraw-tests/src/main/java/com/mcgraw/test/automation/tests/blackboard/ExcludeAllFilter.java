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
import com.mcgraw.test.automation.ui.blackboard.BlackboardCourseDetails;
import com.mcgraw.test.automation.ui.blackboard.BlackboardHomeScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class ExcludeAllFilter extends BaseTest {

	@Autowired
	private BlackBoardApi blackBoardApi;

	@Autowired
	private BlackboardApplication blackboardApplication;

	private String studentId;
	private String studentLogin;

	private String instructorId;
	private String instructorLogin;

	private String courseId1;
	private String courseName1;

	private String courseId2;
	private String courseName2;

	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	private BlackboardHomeScreen blackboardHomeScreen;
	private BlackboardCourseDetails blackboardCourseDetails;

	private int numOfSlave = 1;
	
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
		mhCampusInstanceConnectorsScreen.chooseExcludeFilter();
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();		
		
		browser.pause(mhCampusInstanceApplication.LINKS_PROCESSING_TIMEOUT_MS);
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

	@Test(description = "For BlackBoard instructor MH Campus link is absent")
	public void checkMHCampusLinkIsAbsentInInstructorsCoursesForExcludeAllFilter() {

		blackboardHomeScreen = blackboardApplication.loginToBlackBoard(instructorLogin, instructorLogin);
        blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName1);
		Assert.verifyEquals(blackboardCourseDetails.getMhCampusLinksCount(), 0, "Wrong count of MH Campus links for instructor's course " + courseName1);
		blackboardCourseDetails.clickMyInstitutionLink();
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName2);
		Assert.verifyEquals(blackboardCourseDetails.getMhCampusLinksCount(), 0, "Wrong count of MH Campus links for instructor's course " + courseName2);
		blackboardCourseDetails.clickLogout();
	}

	@Test(description = "For BlackBoard student MH Campus link is absent")
	public void checkMHCampusLinkIsAbsentInStudentsCoursesForExcludeAllFilter() {

		blackboardHomeScreen = blackboardApplication.loginToBlackBoard(studentLogin, studentLogin);
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName1);	
		Assert.verifyEquals(blackboardCourseDetails.getMhCampusLinksCount(), 0, "Wrong count of MH Campus links for student's course " + courseName1);
		blackboardCourseDetails.clickMyInstitutionLink();
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName2);
		Assert.verifyEquals(blackboardCourseDetails.getMhCampusLinksCount(), 0, "Wrong count of MH Campus links for student's course " + courseName2);
		blackboardCourseDetails.clickLogout();
	}

	private void prepareTestDataInBlackBoard() throws Exception {

		String studentPrefix = "Student";
		String instructorPrefix = "Instructor";

		String studentRandom = getRandomString();
		String instructorRandom = getRandomString();
		String courseRandom1 = getRandomString();
		String courseRandom2 = getRandomString();

		studentLogin = studentPrefix + studentRandom;
		instructorLogin = instructorPrefix + instructorRandom;
		courseName1 = "Course" + courseRandom1;
		courseName2 = "Course" + courseRandom2;

		blackBoardApi.loginAndInitialiseBlackBoardServices();

		blackBoardApi.deleteAllUsersByLoginPrefix(studentPrefix);
		blackBoardApi.deleteAllUsersByLoginPrefix(instructorPrefix);

		studentId = blackBoardApi.createUser(studentLogin, studentPrefix + "Name" + studentRandom, studentPrefix + "Surname"
				+ studentRandom);
		instructorId = blackBoardApi.createUser(instructorLogin, instructorPrefix + "Name" + instructorRandom, instructorPrefix + "Surname"
				+ instructorRandom);

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
