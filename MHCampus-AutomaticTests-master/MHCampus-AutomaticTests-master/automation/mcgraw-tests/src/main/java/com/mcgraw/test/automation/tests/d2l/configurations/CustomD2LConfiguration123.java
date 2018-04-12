package com.mcgraw.test.automation.tests.d2l.configurations;


import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.d2l.D2LUserRole;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCourseOfferingRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCourseTemplateRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LUserRS;
import com.mcgraw.test.automation.api.rest.d2l.service.D2LApiUtils;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.D2LApplication;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.d2l.base.D2lContentCourseScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lCourseDetailsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lGradesDetailsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lHomeScreen;
import com.mcgraw.test.automation.ui.d2l.v10.D2lGradesDetailsScreenForInstructorV10;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;

/**
 * LMS = D2L 
 * DI: Disabled
 * Course ID – User ID Mapping: None
 * Gradebook Connector: Yes 
 * Gradebook Connector type : lti 
 * SSO Connector: No
 * Data Connector: No
 * Canvas Mapping: No
 * Instructor Level Token: On
 * Use Existing Assignments: Off 
 * Fallback to user_id and context_id: Off
 * Legacy: On
 * Async: Off
 */
public class CustomD2LConfiguration123 extends BaseTest {
	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	private D2LApplication d2lApplication;

	private String assignmentId = getRandomNumber();
	private String assignmentTitle = "title_" + getRandomString();
	private String category = "category_" + getRandomString();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String scoreType = "Percentage";
	private String scorePossible = "100";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;
	private String commentForStudent1 = "comment_" + getRandomString();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent1 = GradebookApplication.getRandomScore();

	private D2lHomeScreen d2lHomeScreen;
	private D2lCourseDetailsScreen d2lCourseDetailsScreen;
	private D2lContentCourseScreen d2lContentCourseScreen;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;
	private D2lGradesDetailsScreenForInstructorV10 d2lGradesDetailsScreenForInstructor;
	private D2lGradesDetailsScreen d2lGradesDetailsScreen;

	private static String moduleName = "Module" + RandomStringUtils.randomNumeric(5);
	private String studentRandom = getRandomString();
	private String studentLogin1 = "student" + studentRandom;
	private String studentName1 = "StudentName" + studentRandom;
	private String studentSurname1 = "StudentSurname" + studentRandom;

	private String instructorRandom = getRandomString();
	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String instructorSurname = "InstructorSurame" + instructorRandom;

	private static String linkName = "McGraw-Hill Campus";
	private String password = "123qweA@";

	private String courseName1 = "CourseName1" + getRandomString();

	@Autowired
	private D2LApiUtils d2LApiUtils;

	private D2LUserRS studentRS1;
	private D2LUserRS studentRS2;
	private D2LUserRS instructorRS;
	private D2LUserRS instructorRS2;
	private D2LCourseTemplateRS courseTemplateRS1;
	private D2LCourseOfferingRS courseOfferingRS1;
	private D2LCourseTemplateRS courseTemplateRS2;
	private D2LCourseOfferingRS courseOfferingRS2;
	private static String D2L_FRAME;
	private String customerNumber = "DKBY-0JJV-KNNI";
	private String sharedSecret = "E57E5B";

	private String courseId1;

	private String pageAddressFromEmail = "http://CustomCanvasConfiguration123.mhcampus.com";

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		Logger.info("Starting test for configuration:");
		Logger.info("LMS = D2L | DI: Disabled ");
		Logger.info(
				"Course ID – User ID Mapping: None | Gradebook Connector: Yes | Gradebook Connector type : lti | SSO Connector: No | Data Connector:  No| Canvas Mapping: No ");
		Logger.info(
				"* Instructor Level Token: On | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Legacy: On | Async: Off");

		prepareTestDataInD2l();

		if (d2lApplication.d2lBaseUrl.equals("https://tegrity.desire2learn.com"))
			D2L_FRAME = "First";
		else
			D2L_FRAME = "NoFrame";

		d2lApplication.createD2lLinkConnectedWithInstance(linkName, courseId1, customerNumber, sharedSecret);
		d2lApplication.addCreatedLinkToD2lModule(courseId1, linkName, moduleName);

	}

	// @AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearD2lData();
	}

	@AfterMethod
	public void closeAllWindowsExceptFirst() throws Exception {
		browser.closeAllWindowsExceptFirst();
	}

	@Test(description = "Check MH Campus link is present for D2L instructor")
	public void SSObyInstructor() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		d2lContentCourseScreen = d2lCourseDetailsScreen.clickContentLink();
		d2lContentCourseScreen.chooseModuleBlock(moduleName);
		Assert.assertEquals(d2lContentCourseScreen.getMhCampusLinksCount(), 1,
				"Wrong count of MH Campus links for instructor's course " + courseName1);

		mhCampusIntroductionScreen = d2lContentCourseScreen.clickMhCampusLink();

		if (!d2lApplication.d2lBaseUrl.equals("https://tegrity.desire2learn.com")) {
			Assert.verifyTrue(
					mhCampusIntroductionScreen.getFrameAddress(D2L_FRAME).contains(pageAddressFromEmail.toLowerCase()),
					"The address of frame is not contain instance login page address");
		}

		String expectedGreetingText = "Hi " + instructorName + " " + instructorSurname + " -";

		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(D2L_FRAME), expectedGreetingText,
				"Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(D2L_FRAME), "Rules text is incorrect");

		mhCampusIntroductionScreen.acceptRules(D2L_FRAME);

		String expectedUserName = (instructorName + " " + instructorSurname).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(D2L_FRAME), expectedUserName,
				"User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, D2L_FRAME),
				"Course " + courseName1 + " is absent");

		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(D2L_FRAME), "Search option is absent");

	}

	@Test(description = "Check MH Campus link is present for D2L student", dependsOnMethods = { "SSObyInstructor" })
	public void SSObyStudent() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin1, password);

		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		d2lContentCourseScreen = d2lCourseDetailsScreen.clickContentLink();
		d2lContentCourseScreen.chooseModuleBlock(moduleName);
		Assert.assertEquals(d2lContentCourseScreen.getMhCampusLinksCount(), 1,
				"Wrong count of MH Campus links for student's course " + courseName1);

		mhCampusIntroductionScreen = d2lContentCourseScreen.clickMhCampusLink();

		String expectedGreetingText = "Hi " + studentName1 + " " + studentSurname1 + " -";
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(D2L_FRAME), expectedGreetingText,
				"Greeting text is incorrect");
		if (!d2lApplication.d2lBaseUrl.equals("https://tegrity.desire2learn.com")) {
			Assert.verifyTrue(
					mhCampusIntroductionScreen.getFrameAddress(D2L_FRAME).contains(pageAddressFromEmail.toLowerCase()),
					"The address of frame is not contain instance login page address");
		}

		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(D2L_FRAME), "Rules text is incorrect");

		mhCampusIntroductionScreen.acceptRules(D2L_FRAME);

		String expectedUserName = (studentName1 + " " + studentSurname1).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(D2L_FRAME), expectedUserName,
				"User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, D2L_FRAME),
				"Course " + courseName1 + " is absent");

		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(D2L_FRAME), "Search option is present");
	}

	@Test(description = "Check TestScorableItem form is submitted successfully", dependsOnMethods = { "SSObyStudent" })
	public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber, "",
				Integer.toString(courseOfferingRS1.getId()), assignmentId, assignmentTitle, category, "", startDate,
				dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForD2l(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = {
			"checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent1() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, "",
				Integer.toString(courseOfferingRS1.getId()), assignmentId, Integer.toString(studentRS1.getUserId()),
				commentForStudent1, dateSubmitted, scoreReceivedForStudent1,
				gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForD2l(), "TestScore form submit failed");

		// check the instructor has correct data
		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		d2lGradesDetailsScreenForInstructor = d2lCourseDetailsScreen.clickGradesLinkAsInstructor();
		Assert.verifyEquals(d2lGradesDetailsScreenForInstructor.getCategory(), category, "Category did not match");
		Assert.verifyEquals(d2lGradesDetailsScreenForInstructor.getAssignmentTitle(), assignmentTitle,
				"Assignment title did not match");
		Assert.verifyEquals(d2lGradesDetailsScreenForInstructor.getScorePossible(assignmentTitle), scorePossible,
				"Score Possible did not match");
		Assert.verifyEquals(
				d2lGradesDetailsScreenForInstructor.getScoreReceived(assignmentTitle,
						studentSurname1 + ", " + studentName1),
				scoreReceivedForStudent1, "ScoreReceived did not match");

	}

	// Additional
	private void prepareTestDataInD2l() throws Exception {

		studentRS1 = d2LApiUtils.createUser(studentName1, studentSurname1, studentLogin1, password,
				D2LUserRole.STUDENT);
		instructorRS = d2LApiUtils.createUser(instructorName, instructorSurname, instructorLogin, password,
				D2LUserRole.INSTRUCTOR);

		courseTemplateRS1 = d2LApiUtils.createCourseTemplate("name" + getRandomString(),
				"code" + RandomStringUtils.randomNumeric(3));
		courseOfferingRS1 = d2LApiUtils.createCourseOfferingByTemplate(courseTemplateRS1, courseName1,
				"code" + RandomStringUtils.randomNumeric(3));
		courseId1 = Integer.toString(courseOfferingRS1.getId());

		d2LApiUtils.createEnrollment(studentRS1, courseOfferingRS1, D2LUserRole.STUDENT);
		d2LApiUtils.createEnrollment(instructorRS, courseOfferingRS1, D2LUserRole.INSTRUCTOR);

	}

	private void clearD2lData() throws Exception {

		if (studentRS1 != null)
			d2LApiUtils.deleteUser(studentRS1);
		if (studentRS2 != null)
			d2LApiUtils.deleteUser(studentRS2);
		if (instructorRS != null)
			d2LApiUtils.deleteUser(instructorRS);
		if (instructorRS2 != null)
			d2LApiUtils.deleteUser(instructorRS2);
		if (courseOfferingRS1 != null)
			d2LApiUtils.deleteCourseOffering(courseOfferingRS1);
		if (courseTemplateRS1 != null)
			d2LApiUtils.deleteCourseTemplate(courseTemplateRS1);
		if (courseOfferingRS2 != null)
			d2LApiUtils.deleteCourseOffering(courseOfferingRS2);
		if (courseTemplateRS2 != null)
			d2LApiUtils.deleteCourseTemplate(courseTemplateRS2);
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}
}
