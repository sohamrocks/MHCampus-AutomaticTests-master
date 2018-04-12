package com.mcgraw.test.automation.tests.moodle.configurations;

/**
* LMS = Moodle
* DI: Off
* Course ID – User ID Mapping: off
* Gradebook Connector: Yes 
* SSO Connector: Yes 
* Canvas Mapping: No 
* Instructor Level Token: On
* Use Existing Assignments: Off
* Fallback to user_id and context_id: Off
* Async:  Off
*/
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleCategoryRS;
import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleCourseRS;
import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleUserRS;
import com.mcgraw.test.automation.api.rest.moodle.service.MoodleApiUtils;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.applications.MoodleApplication;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.moodle.MoodleCourseDetailsScreen;
import com.mcgraw.test.automation.ui.moodle.MoodleCourseScreen;
import com.mcgraw.test.automation.ui.moodle.MoodleGradesScreen;
import com.mcgraw.test.automation.ui.moodle.MoodleGradesScreenOldVer;
import com.mcgraw.test.automation.ui.moodle.MoodleHomeScreen;

public class CustomMoodleConfiguration190 extends BaseTest {
	@Autowired
	private MoodleApplication moodleApplication;

	@Autowired
	private GradebookApplication gradebookApplication;

	private String studentRandom = getRandomString(6);
	private String instructorRandom = getRandomString(6);
	private String courseRandom = getRandomString(5);

	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;
	private String studentSurname = "StudentSurname" + studentRandom;

	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String instructorSurname = "InstructorSurname" + instructorRandom;

	private String category1 = "Category1" + courseRandom;
	private String courseFullName1 = "CourseFull1" + courseRandom;
	private String courseShortName1 = "CourseShort1" + courseRandom;

	private String password = "123qweA@";

	MoodleHomeScreen moodleHomeScreen;
	MoodleCourseDetailsScreen moodleCourseDetailsScreen;
	MhCampusIntroductionScreen mhCampusIntroductionScreen;
	MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	MoodleGradesScreen moodleGradesScreen;
	MoodleGradesScreenOldVer moodleGradesScreenOldVer;
	MoodleCourseScreen moodleCourseScreen;

	@Autowired
	private MoodleApiUtils moodleApiUtils;

	private MoodleUserRS studentRS;
	private MoodleUserRS instructorRS;
	private MoodleCourseRS courseRS1;
	private MoodleCategoryRS categoryRS1;

	private String customerNumber = "1BL9-4DD0-H896";
	private String sharedSecret = "B332DE";

	private String providerId = "provider_" + getRandomString(5);
	private String description = "description_" + getRandomString(5);
	private String assignmentTitle = "title_" + getRandomString(5);
	private String commentForStudent1 = "comment_" + getRandomString(5);
	private String assignmentId = getRandomNumber();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent1 = GradebookApplication.getRandomScore();
	private String scorePossible = "101";
	private String categoryType = "Blog";
	private String scoreType = "Percentage";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration:");
		Logger.info("LMS = Moodle | DI: Off ");
		Logger.info(
				"Course ID – User ID Mapping:  Off  | Gradebook Connector: Yes | SSO Connector: Yes | Canvas Mapping: No ");
		Logger.info(
				"* Instructor Level Token: On | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Async: Off");

		prepareDataInMoodle();

		moodleApplication.completeMhCampusSetupWithMoodle(customerNumber, sharedSecret);
		moodleHomeScreen = moodleApplication.loginToMoodle(moodleApplication.moodleAdminLogin,
				moodleApplication.moodleAdminPassword);
		moodleCourseScreen = moodleHomeScreen.clickOnCoursesInNavBar();
		moodleCourseDetailsScreen = moodleCourseScreen.findAndSelectCourse(courseFullName1);
		moodleCourseDetailsScreen.addBlockToCourse(moodleApplication.moodleBlockName);

	}

	@AfterClass
	public void testSuiteTearDown() throws Exception {
		if (categoryRS1 != null)
			moodleApiUtils.deleteCategoryWithCourses(categoryRS1);
		if (studentRS != null)
			moodleApiUtils.deleteUser(studentRS);
		if (instructorRS != null)
			moodleApiUtils.deleteUser(instructorRS);
	}

	@AfterMethod
	public void closeAllWindowsExceptFirst() throws Exception {
		browser.closeAllWindowsExceptFirst();
	}

	private String getRandomString(int count) {
		return RandomStringUtils.randomNumeric(count);
	}

	@Test(description = "Check MH Campus link is present for Moodle instructor")
	public void checkMhCampusLinkIsPresentInInstructorsCourse() throws Exception {

		moodleHomeScreen = moodleApplication.loginToMoodle(instructorLogin, password);
		browser.pause(3000);
		moodleCourseScreen = moodleHomeScreen.clickOnAllCoursesLink();
		moodleCourseDetailsScreen = moodleCourseScreen.findAndSelectCourse(courseFullName1);

		Assert.assertEquals(moodleCourseDetailsScreen.getMhCampusLinksCount(), 1,
				"Wrong count of MH Campus links for instructor's course " + courseFullName1 + ". Expected [1], actual ["
						+ moodleCourseDetailsScreen.getMhCampusLinksCount() + "]\n");

		mhCampusIntroductionScreen = moodleCourseDetailsScreen.clickMhCampusLink();

		String expectedGreetingText = "Hi " + instructorName + " " + instructorSurname + " -";
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(), expectedGreetingText,
				"Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(), "Rules text is incorrect");

		mhCampusIntroductionScreen.acceptRules();

		String expectedUserName = (instructorName + " " + instructorSurname).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName);
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseShortName1),
				"Course " + courseShortName1 + " is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
	}

	@Test(description = "Check MH Campus link is present for Moodle student", dependsOnMethods = {
			"checkMhCampusLinkIsPresentInInstructorsCourse" })
	public void checkMhCampusLinkIsPresentInStudentsCourse() throws Exception {

		moodleHomeScreen = moodleApplication.loginToMoodle(studentLogin, password);
		browser.pause(3000);
		moodleCourseScreen = moodleHomeScreen.clickOnAllCoursesLink();
		moodleCourseDetailsScreen = moodleCourseScreen.findAndSelectCourse(courseFullName1);
		Assert.assertEquals(moodleCourseDetailsScreen.getMhCampusLinksCount(), 1,
				"Wrong count of MH Campus links for student's course " + courseFullName1 + ". Expected [1], actual ["
						+ moodleCourseDetailsScreen.getMhCampusLinksCount() + "]\n");

		mhCampusIntroductionScreen = moodleCourseDetailsScreen.clickMhCampusLink();

		String expectedGreetingText = "Hi " + studentName + " " + studentSurname + " -";
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(), expectedGreetingText,
				"Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(), "Rules text is incorrect");

		mhCampusIntroductionScreen.acceptRules();

		String expectedUserName = (studentName + " " + studentSurname).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName);
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseShortName1),
				"Course " + courseShortName1 + " is absent");

		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(), "Search option is present");
	}

	@Test(description = "Check TestScorableItem form is submitted successfully", dependsOnMethods = {
			"checkMhCampusLinkIsPresentInStudentsCourse" })
	public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber,
				providerId, courseRS1.getId(), assignmentId, assignmentTitle, categoryType, description, startDate,
				dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				gradebookApplication.tegrityServiceLocation);
		testScoreItemsScreen.submitForm();

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForMoodle(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = {
			"checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent1() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId,
				courseRS1.getId(), assignmentId, studentLogin, commentForStudent1, dateSubmitted,
				scoreReceivedForStudent1, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForMoodle(), "TestScore form submit failed");

		// check the student has correct data
		moodleHomeScreen = moodleApplication.loginToMoodle(studentLogin, password);
		moodleCourseScreen = moodleHomeScreen.clickOnAllCoursesLink();
		moodleCourseDetailsScreen = moodleCourseScreen.findAndSelectCourse(courseFullName1);
		moodleGradesScreen = moodleCourseDetailsScreen.clickGradesLink();
		Assert.verifyEquals(moodleGradesScreen.getScoreReceived(), scoreReceivedForStudent1,
				"ScoreReceived did not match");

	}

	public void prepareDataInMoodle() throws Exception {
		studentRS = moodleApiUtils.createUser(studentLogin, password, studentName, studentSurname);
		instructorRS = moodleApiUtils.createUser(instructorLogin, password, instructorName, instructorSurname);

		categoryRS1 = moodleApiUtils.createCategory(category1);
		courseRS1 = moodleApiUtils.createCourseInsideCategory(courseFullName1, courseShortName1, categoryRS1);

		moodleApiUtils.enrollToCourseAsStudent(studentRS, courseRS1);
		moodleApiUtils.enrollToCourseAsInstructor(instructorRS, courseRS1);

	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}
}