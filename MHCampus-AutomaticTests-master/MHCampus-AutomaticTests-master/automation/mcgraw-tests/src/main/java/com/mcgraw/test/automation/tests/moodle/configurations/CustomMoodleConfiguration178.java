package com.mcgraw.test.automation.tests.moodle.configurations;

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

/**
 * LMS = Moodle 
 * DI: Off 
 * Course ID - User ID Mapping: off 
 * Gradebook Connector:
 * Yes 
 * SSO Connector: Yes 
 * Instructor Level Token: Off 
 * Use Existing Assignments:Off 
 * Fallback to user_id and context_id: Off 
 * Asvnc: Off
 */
public class CustomMoodleConfiguration178 extends BaseTest {

	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	private MoodleApplication moodleApplication;

	@Autowired
	private MoodleApiUtils moodleApiUtils;

	private String studentRandom = getRandomString(6);
	private String instructorRandom = getRandomString(6);
	private String courseRandom = getRandomString(5);

	private String studentLogin1 = "student1" + studentRandom;
	private String studentName1 = "StudentName1" + studentRandom;
	private String studentSurname1 = "StudentSurname1" + studentRandom;

	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String instructorSurname = "InstructorSurname" + instructorRandom;

	private String category1 = "Category1" + courseRandom;
	private String courseFullName1 = "CourseFull1" + courseRandom;
	private String courseShortName1 = "CourseShort1" + courseRandom;

	private String password = "123qweA@";

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

	MoodleHomeScreen moodleHomeScreen;
	MoodleCourseDetailsScreen moodleCourseDetailsScreen;
	MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	MoodleGradesScreen moodleGradesScreen;
	MoodleGradesScreenOldVer moodleGradesScreenOldVer;
	MoodleCourseScreen moodleCourseScreen;

	private MoodleUserRS instructorRS;
	private MoodleUserRS studentRS1;
	private MoodleCourseRS courseRS1;
	private MoodleCategoryRS categoryRS1;

	private MhCampusIntroductionScreen mhCampusIntroductionScreen;

	private String customerNumber = "MLXZ-LSDK-QLDG";
	private String institution = "CustomMoodleConfiguration178";
	private String sharedSecret = "249C46";
	private String instUsername = "campus.com";
	private String instPassword = "hqkjwtey";

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration: CustomMoodleConfiguration178");
		Logger.info("LMS = Moodle | DI: Off ");
		Logger.info("Course ID РІР‚вЂњ User ID Mapping: Off | Gradebook Connector: Yes | SSO Connector: Yes ");
		Logger.info(
				"Instructor Level Token: Off | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Async: Off");

		prepareDataInMoodle();
		moodleApplication.completeMhCampusSetupWithMoodle(customerNumber, sharedSecret);
		moodleHomeScreen = moodleApplication.loginToMoodle(moodleApplication.moodleAdminLogin,
				moodleApplication.moodleAdminPassword);
		moodleCourseScreen = moodleHomeScreen.clickOnAllCoursesLink();
		moodleCourseDetailsScreen = moodleCourseScreen.findAndSelectCourse(courseFullName1);
		moodleCourseDetailsScreen.addBlockToCourse(moodleApplication.moodleBlockName);
	}

	@AfterMethod
	public void closeAllWindowsExceptFirst() throws Exception {
		browser.closeAllWindowsExceptFirst();
	}

	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearMoodleData();
	}

	@Test(description = "Check SSO to  MHC as Moodle instructor")
	public void checkSsoAsInstructorMoodle() throws Exception {
		moodleHomeScreen = moodleApplication.loginToMoodle(instructorLogin, password);
		moodleCourseScreen = moodleHomeScreen.clickOnAllCoursesLink();
		moodleCourseDetailsScreen = moodleCourseScreen.findAndSelectCourse(courseFullName1);
		Assert.assertEquals(moodleCourseDetailsScreen.getMhCampusLinksCount(), 1,
				"Wrong count of MH Campus links for instructor's course " + courseFullName1 + ". Expected [1], actual ["
						+ moodleCourseDetailsScreen.getMhCampusLinksCount() + "]\n");
		String expectedGreetingText = "Hi " + instructorName + " " + instructorSurname + " -";
		mhCampusIntroductionScreen = moodleCourseDetailsScreen.clickMhCampusLink();
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

	@Test(description = "Check SSO to  MHC as Moodle student", dependsOnMethods = { "checkSsoAsInstructorMoodle" })
	public void checkSsoAsStudentMoodle() throws Exception {
		moodleHomeScreen = moodleApplication.loginToMoodle(studentLogin1, password);
		moodleCourseScreen = moodleHomeScreen.clickOnAllCoursesLink();
		moodleCourseDetailsScreen = moodleCourseScreen.findAndSelectCourse(courseFullName1);
		Assert.assertEquals(moodleCourseDetailsScreen.getMhCampusLinksCount(), 1,
				"Wrong count of MH Campus links for student's course " + courseFullName1 + ". Expected [1], actual ["
						+ moodleCourseDetailsScreen.getMhCampusLinksCount() + "]\n");
		String expectedGreetingText = "Hi " + studentName1 + " " + studentSurname1 + " -";
		mhCampusIntroductionScreen = moodleCourseDetailsScreen.clickMhCampusLink();
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(), expectedGreetingText,
				"Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(), "Rules text is incorrect");
		mhCampusIntroductionScreen.acceptRules();
		String expectedUserName = (studentName1 + " " + studentSurname1).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName);
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseShortName1),
				"Course " + courseShortName1 + " is absent");
		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(), "Search option is present");
	}

	@Test(description = "Check TestScorableItem form is submitted successfully", dependsOnMethods = {
			"checkSsoAsStudentMoodle" })
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
				courseRS1.getId(), assignmentId, studentLogin1, commentForStudent1, dateSubmitted,
				scoreReceivedForStudent1, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForMoodle(), "TestScore form submit failed");

		// check the student has correct data
		moodleHomeScreen = moodleApplication.loginToMoodle(studentLogin1, password);
		moodleCourseScreen = moodleHomeScreen.clickOnAllCoursesLink();
		moodleCourseDetailsScreen = moodleCourseScreen.findAndSelectCourse(courseFullName1);
		moodleGradesScreen = moodleCourseDetailsScreen.clickGradesLink();
		Assert.verifyEquals(moodleGradesScreen.getScoreReceived(), scoreReceivedForStudent1,
				"SoreReceived did not match");
	}

	public void prepareDataInMoodle() throws Exception {
		studentRS1 = moodleApiUtils.createUser(studentLogin1, password, studentName1, studentSurname1);
		instructorRS = moodleApiUtils.createUser(instructorLogin, password, instructorName, instructorSurname);

		categoryRS1 = moodleApiUtils.createCategory(category1);
		courseRS1 = moodleApiUtils.createCourseInsideCategory(courseFullName1, courseShortName1, categoryRS1);

		moodleApiUtils.enrollToCourseAsStudent(studentRS1, courseRS1);
		moodleApiUtils.enrollToCourseAsInstructor(instructorRS, courseRS1);

	}

	private void clearMoodleData() throws Exception {
		if (categoryRS1 != null)
			moodleApiUtils.deleteCategoryWithCourses(categoryRS1);
		if (studentRS1 != null)
			moodleApiUtils.deleteUser(studentRS1);
		if (instructorRS != null)
			moodleApiUtils.deleteUser(instructorRS);

	}

	private String getRandomString(int count) {
		return RandomStringUtils.randomNumeric(count);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}
}
