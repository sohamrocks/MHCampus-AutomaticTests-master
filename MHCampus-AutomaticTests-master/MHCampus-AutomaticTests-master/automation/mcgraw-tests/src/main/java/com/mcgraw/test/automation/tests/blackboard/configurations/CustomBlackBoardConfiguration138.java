package com.mcgraw.test.automation.tests.blackboard.configurations;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.blackboard.serviceapi.BlackBoardApi;
import com.mcgraw.test.automation.api.blackboard.serviceapi.BlackBoardApi.BlackboardApiRoleIdentifier;
import com.mcgraw.test.automation.api.blackboard.serviceapi.BlackBoardApi.GradebookInfo;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.BlackboardApplication;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;

public class CustomBlackBoardConfiguration138 extends BaseTest {

	@Autowired
	private BlackBoardApi blackBoardApi;

	@Autowired
	private BlackboardApplication blackboardApplication;

	@Autowired
	private GradebookApplication gradebookApplication;

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

	private MhCampusIntroductionScreen mhCampusIntroductionScreen;

	private String customerNumber = "EVKZ-RCB4-XOXZ";
	private String sharedSecret = "592EAC";
	private String institution = "CustomBlackboardConfiguration138";
	private String pageAddressForLogin = "CustomBlackboardConfiguration138.mhcampus.com";

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

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		blackboardApplication.completeMhCampusSetupWithBlackBoard(customerNumber, sharedSecret);
		prepareDataInBlackBoard();
	}

	// @AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {

		if (studentId != null)
			blackBoardApi.deleteUser(studentId);
		if (instructorId != null)
			blackBoardApi.deleteUser(instructorId);
		if (courseId1 != null)
			blackBoardApi.deleteCourse(courseId1);
		blackBoardApi.logout();
	}

	@Test(description = "SSO direct login as Instructor to Blackboard")
	public void SSOasInstructor() {

		mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(pageAddressForLogin, institution,
				instructorLogin, instructorLogin);

		String expectedGreetingText = "Hi " + instructorName + " " + instructorSurname + " -";
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(), expectedGreetingText,
				"Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(), "Rules text is incorrect");

		mhCampusIntroductionScreen.acceptRules();

		String expectedUserName = (instructorName + " " + instructorSurname).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName, "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1),
				"Course " + courseName1 + " is absent");

		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
	}

	@Test(description = "SSO direct login as Sudentt to Blackboard", dependsOnMethods = { "SSOasInstructor" })
	public void SSoasStudent() {

		mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(pageAddressForLogin, institution,
				studentLogin, studentLogin);

		String expectedGreetingText = "Hi " + studentName + " " + studentSurname + " -";
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(), expectedGreetingText,
				"Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(), "Rules text is incorrect");

		mhCampusIntroductionScreen.acceptRules();

		String expectedUserName = (studentName + " " + studentSurname).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName, "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1),
				"Course " + courseName1 + " is absent");
		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(), "Search option is present");

	}

	@Test(description = "Check blackboard TestScorableItem form is submitted successfully", dependsOnMethods = { "SSoasStudent" })
	public void checkBBSubmittingTestScorableItemFormIsSuccessfull() throws Exception {

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber,
				providerId, courseName1, assignmentId, assignmentTitle, category, description, startDate, dueToDate,
				scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				gradebookApplication.tegrityServiceLocation);

		//Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForBlackboard(), "TestScoreItems form submit failed");
		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForBBTitleOnly(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check blackboard TestScore form is submitted successfully", dependsOnMethods = {
			"checkBBSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkBBSubmittingTestScoreFormIsSuccessfullForStudent() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId,
				courseName1, assignmentId, studentLogin, commentForStudent, dateSubmitted, scoreReceivedForStudent,
				gradebookApplication.tegrityServiceLocation);

		//Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForBlackboard(), "TestScore form submit failed");
		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForBBTitleOnly(), "TestScoreItems form submit failed");
	}

	private void prepareDataInBlackBoard() throws Exception {

		String studentPrefix = "Student";
		String instructorPrefix = "Instructor";

		String studentRandom = getRandomString();
		String instructorRandom = getRandomString();
		String courseRandom1 = getRandomString();

		studentLogin = studentPrefix + studentRandom;
		studentName = studentPrefix + "Name" + studentRandom;
		studentSurname = studentPrefix + "Surname" + studentRandom;

		instructorLogin = instructorPrefix + instructorRandom;
		instructorName = "InstructorName" + instructorRandom;
		instructorSurname = "InstructorSurname" + instructorRandom;
		courseName1 = "Course" + courseRandom1;

		blackBoardApi.loginAndInitialiseBlackBoardServices();

		//blackBoardApi.deleteAllUsersByLoginPrefix(studentPrefix);
		//blackBoardApi.deleteAllUsersByLoginPrefix(instructorPrefix);

		studentId = blackBoardApi.createUser(studentLogin, studentName, studentSurname);
		instructorId = blackBoardApi.createUser(instructorLogin, instructorName, instructorSurname);
		courseId1 = blackBoardApi.createCourse(courseName1);

		blackBoardApi.createEnrollment(studentId, courseId1, BlackboardApiRoleIdentifier.STUDENT);
		blackBoardApi.createEnrollment(instructorId, courseId1, BlackboardApiRoleIdentifier.INSTRUCTOR);

	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}
}
