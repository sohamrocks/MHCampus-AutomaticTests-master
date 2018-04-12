package com.mcgraw.test.automation.tests.blackboard;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
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
import com.mcgraw.test.automation.ui.blackboard.BlackboardCourseDetails;
import com.mcgraw.test.automation.ui.blackboard.BlackboardHomeScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceConnectorsScreen;

public class TegrityCsvFilter extends BaseTest {

	@Autowired
	private BlackBoardApi blackBoardApi;

	@Autowired
	private BlackboardApplication blackboardApplication;

	// For the local run is recommended change to: FULL_PATH_FILE = "C:\\Temp\\filter_blackboard.csv";
	// For the server run is recommended change to:FULL_PATH_FILE = "C:\\filter_blackboard.csv";
	private static String FULL_PATH_FILE = "C:\\filter_blackboard.csv";

	private String studentId;
	private String studentLogin;

	private String instructorId;
	private String instructorLogin;

	private String courseId1;
	private String courseName1;

	private String courseId2;
	private String courseName2;

	private TegrityInstanceConnectorsScreen tegrityInstanceConnectorsScreen;
	private BlackboardHomeScreen blackboardHomeScreen;
	private BlackboardCourseDetails blackboardCourseDetails;

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		
		blackboardApplication.completeTegritySetupWithBlackBoard(tegrityInstanceApplication.customerNumber, tegrityInstanceApplication.sharedSecret);
		prepareTestDataInBlackBoard();
		
		tegrityInstanceConnectorsScreen = tegrityInstanceApplication.loginToTegrityInstanceAsAdminAndClickManageAairsLink();
		tegrityInstanceConnectorsScreen.deleteAllConnectors();
        tegrityInstanceConnectorsScreen.configureBlackboardAuthorizationConnector(blackboardApplication.title, blackboardApplication.address);
		tegrityInstanceConnectorsScreen.configureBlackboardSsoLinkConnector(blackboardApplication.title, blackboardApplication.address);
		tegrityInstanceApplication.createCsvFile(FULL_PATH_FILE, courseName1);
		tegrityInstanceConnectorsScreen.chooseCvsListsFilter(FULL_PATH_FILE);
		tegrityInstanceConnectorsScreen.clickSaveAndContinueButton();
		tegrityInstanceConnectorsScreen.logOut();
		
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

	@Test(description = "For BlackBoard instructor Tegrity link is only present for CSV courses")
	public void checkTegrityLinkIsPresentInInstructorsCoursesAccordingToCsvFilter() {

		blackboardHomeScreen = blackboardApplication.loginToBlackBoard(instructorLogin, instructorLogin);
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName1);
		for(int i = 0; i < 20; i++){		// AleksandrY fix instability on server side
			if(blackboardCourseDetails.getTegrityLinksCount() == 0){
				Logger.info(String.format("Waiting whether Tegrity link appears on UI. Try on <%s> of 5", i));
				browser.pause(60000);
			} else {
				break;
			}
		}
		Assert.verifyEquals(blackboardCourseDetails.getTegrityLinksCount(), 1, "Wrong count of Tegrity links for instructor's course " + courseName1);
		blackboardCourseDetails.clickMyInstitutionLink();
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName2);		
		Assert.verifyEquals(blackboardCourseDetails.getTegrityLinksCount(), 0, "Wrong count of Tegrity links for instructor's course " + courseName2);
		blackboardCourseDetails.clickLogout();
	}

	@Test(description = "For BlackBoard student Tegrity link is only present for CSV courses")
	public void checkTegrityLinkIsPresentInStudentsCoursesAccordingToCsvFilter() {
		
		blackboardHomeScreen = blackboardApplication.loginToBlackBoard(studentLogin, studentLogin);
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName1);		
		Assert.verifyEquals(blackboardCourseDetails.getTegrityLinksCount(), 1, "Wrong count of Tegrity links for student's course " + courseName1);
		blackboardCourseDetails.clickMyInstitutionLink();
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName2);
		Assert.verifyEquals(blackboardCourseDetails.getTegrityLinksCount(), 0, "Wrong count of Tegrity links for student's course " + courseName2);
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
