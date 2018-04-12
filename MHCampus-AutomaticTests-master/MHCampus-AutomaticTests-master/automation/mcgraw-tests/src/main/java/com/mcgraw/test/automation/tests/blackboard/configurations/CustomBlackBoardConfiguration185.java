package com.mcgraw.test.automation.tests.blackboard.configurations;



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
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.blackboard.BlackboardCourseDetails;
import com.mcgraw.test.automation.ui.blackboard.BlackboardHomeScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;

/**
 * LMS = Black board
 * DI: Off
 * Course ID – User ID Mapping: off
 * Gradebook Connector: Yes 
 * SSO Connector: Yes 
 * Canvas Mapping: No 
 * Instructor Level Token: Off
 * Use Existing Assignments: Off
 * Fallback to user_id and context_id: Off
 * Async:  Off
 */

public class CustomBlackBoardConfiguration185 extends BaseTest {

	@Autowired
	private BlackBoardApi blackBoardApi;

	@Autowired
	private GradebookApplication gradebookApplication;

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

	private String courseId;
	private String courseName;

	String studentPrefix = "Student";
	String instructorPrefix = "Instructor";

	String studentRandom = getRandomString();
	String instructorRandom = getRandomString();
	String courseRandom = getRandomString();

	private String providerId = "Connect";
	private String assignmentId = getRandomNumber();
	private String assignmentTitle = "title_" + getRandomString();
	private String category = "Test";
	private String description = "description_" + getRandomString();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String scoreType = "Percentage";
	private String scorePossible = "100";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;
	private String commentForStudent = "comment_" + getRandomString();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent = GradebookApplication.getRandomScore();

	private BlackboardHomeScreen blackboardHomeScreen;
	private BlackboardCourseDetails blackboardCourseDetails;


	private String customerNumber = "271L-WZE9-OK52";
	private String sharedSecret = "BA1708";
//	private String institution = "CustomBlackboardConfiguration182";
//	private String pageAddressForLogin = "CustomBlackboardConfiguration182.mhcampus.com";
//	private String adminUsername = "admin@CustomBlackboardConfiguration182.mhcampus.com";
//	private String adminPassword = "dernrxpa";

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration:");
		Logger.info("LMS = Black board | DI: off ");
		Logger.info(
				"Course ID – User ID Mapping:  Off  | Gradebook Connector: Yes | SSO Connector: Yes | Canvas Mapping: No ");
		Logger.info(
				"* Instructor Level Token: Off | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Async: Off");

		
		prepareDataInBlackBoard();
	}

	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearBlackboardData();
	}

	// Tests Start

	@Test(description = "For BlackBoard instructor MH Campus link is present")
	public void checkMHCampusLinkIsPresentInInstructorsCourses() throws Exception {

		blackboardHomeScreen = blackboardApplication.loginToBlackBoard(instructorLogin, instructorLogin);
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName);

		linkCheckInBlackboard();
		Assert.verifyEquals(blackboardCourseDetails.getMhCampusLinksCount(), 1,
				"Wrong count of MH Campus links for instructor's course " + courseName);

		// blackboardCourseDetails.clickMyInstitutionLink();

		MhCampusIntroductionScreen mhCampusIntroductionScreen = blackboardCourseDetails.clickMhCampusLink();
		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(), "Rules text is incorrect");

		mhCampusIntroductionScreen.acceptRules();

		String expectedUserName = (instructorName + " " + instructorSurname).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName, "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName),
				"Course " + courseName + " is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(), "Search option is absent");

		browser.closeAllWindowsExceptFirst();

		blackboardCourseDetails.clickLogout();
	}

	@Test(description = "For BlackBoard student MH Campus link is present", dependsOnMethods = {
			"checkMHCampusLinkIsPresentInInstructorsCourses" })
	public void checkMHCampusLinkIsPresenttInStudentsCourses() throws Exception {

		blackboardHomeScreen = blackboardApplication.loginToBlackBoard(studentLogin, studentLogin);
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName);

		linkCheckInBlackboard();

		Assert.verifyEquals(blackboardCourseDetails.getMhCampusLinksCount(), 1,
				"Wrong count of MH Campus links for student's course " + courseName);
		// blackboardCourseDetails.clickMyInstitutionLink();
		
		MhCampusIntroductionScreen mhCampusIntroductionScreen = blackboardCourseDetails.clickMhCampusLink();
		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(), "Rules text is incorrect");
		
		mhCampusIntroductionScreen.acceptRules();
		
		String expectedUserName = (studentName + " " + studentSurname).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName,
				"User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName), "Course "+courseName+" is absent");
		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(), "Search option is present");

		browser.closeAllWindowsExceptFirst();
		
		blackboardCourseDetails.clickLogout();
	}

	@Test(description = "Check TestScorableItem form is submitted successfully", dependsOnMethods = {
			"checkMHCampusLinkIsPresenttInStudentsCourses" })
	public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber,
				providerId, courseName, assignmentId, assignmentTitle, category, description, startDate, dueToDate,
				scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForBBTitleOnly(),
				"TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = {
			"checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId, courseName,
				assignmentId, studentLogin, commentForStudent, dateSubmitted, scoreReceivedForStudent,
				gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForBBTitleOnly(), "TestScore form submit failed");
	}

	// Tests Finish

	// private methods for using in class
	private void prepareDataInBlackBoard() throws Exception {
		blackboardApplication.completeMhCampusSetupWithBlackBoard(customerNumber, sharedSecret);

		studentLogin = studentPrefix + studentRandom;
		studentName = studentPrefix + "Name" + studentRandom;
		studentSurname = studentPrefix + "Surname" + studentRandom;

		instructorLogin = instructorPrefix + instructorRandom;
		instructorName = "InstructorName" + instructorRandom;
		instructorSurname = "InstructorSurname" + instructorRandom;
		courseName = "Course" + courseRandom;

		blackBoardApi.loginAndInitialiseBlackBoardServices();

		blackBoardApi.deleteAllUsersByLoginPrefix(studentPrefix);
		blackBoardApi.deleteAllUsersByLoginPrefix(instructorPrefix);

		studentId = blackBoardApi.createUser(studentLogin, studentName, studentSurname);
		instructorId = blackBoardApi.createUser(instructorLogin, instructorName, instructorSurname);
		courseId = blackBoardApi.createCourse(courseName);

		blackBoardApi.createEnrollment(studentId, courseId, BlackboardApiRoleIdentifier.STUDENT);
		blackBoardApi.createEnrollment(instructorId, courseId, BlackboardApiRoleIdentifier.INSTRUCTOR);
	}

	private void clearBlackboardData() throws Exception {

		if (studentId != null)
			blackBoardApi.deleteUser(studentId);
		if (instructorId != null)
			blackBoardApi.deleteUser(instructorId);
		if (courseId != null)
			blackBoardApi.deleteCourse(courseId);
		blackBoardApi.logout();
	}

	private void linkCheckInBlackboard() {
		// Checking for link before assert
		for (int i = 0; i < 20; i++) {
			if (blackboardCourseDetails.getMhCampusLinksCount() == 0) {
				Logger.info(String.format("Waiting whether Campus link appears on UI. Try #<%s> of 20", i + 1));
				browser.pause(60000);
				browser.navigate().refresh();
			} else {
				break;
			}
		}
		// End link check
	}
	
	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}

}

