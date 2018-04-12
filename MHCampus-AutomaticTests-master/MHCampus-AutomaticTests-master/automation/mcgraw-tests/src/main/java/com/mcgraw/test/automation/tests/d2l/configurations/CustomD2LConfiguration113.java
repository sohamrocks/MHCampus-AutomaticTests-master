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
import com.mcgraw.test.automation.ui.applications.RestApplication;
import com.mcgraw.test.automation.ui.d2l.base.D2lContentCourseScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lCourseDetailsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lEditHomepageScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lHomeScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lManageExternalToolsScreen;
import com.mcgraw.test.automation.ui.d2l.v10.D2lGradesDetailsScreenForInstructorV10;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;

/**
 * LMS = D2l lti
 * DI: On
 * Course ID – User ID Mapping: off
 * Gradebook Connector: Yes 
 * SSO Connector: No 
 * Canvas Mapping: No 
 * Data connector: On
 * Legasy: Off
 * Instructor Level Token: On
 * Use Existing Assignments: Off
 * Fallback to user_id and context_id: Off
 * Async:  Off
 */
public class CustomD2LConfiguration113 extends BaseTest {
	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	private D2LApplication d2lApplication;

	@Autowired
	private RestApplication restApplication;

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
	private String commentForStudent2 = "comment " + getRandomString();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent1 = GradebookApplication.getRandomScore();
	private String scoreReceivedForStudent2 = GradebookApplication.getRandomScore();
	private String providerId = "Connect";
	private String description = "description_" + getRandomString();
	private String tool_ID = "Connect";

	private D2lHomeScreen d2lHomeScreen;
	private D2lCourseDetailsScreen d2lCourseDetailsScreen;
	private D2lContentCourseScreen d2lContentCourseScreen;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;
	private D2lManageExternalToolsScreen d2lManageExternalToolsScreen;
	private D2lEditHomepageScreen d2lEditHomepageScreen;
	private D2lGradesDetailsScreenForInstructorV10 d2lGradesDetailsScreenForInstructor;

	private static String moduleName = "Module" + RandomStringUtils.randomNumeric(5);
	private String studentRandom = getRandomString();
	private String studentLogin1 = "student" + studentRandom;
	private String studentName1 = "StudentName" + studentRandom;
	private String studentSurname1 = "StudentSurname" + studentRandom;
	private String studentLogin2 = "student2" + studentRandom;
	private String studentName2 = "StudentName2" + studentRandom;
	private String studentSurname2 = "StudentSurname2" + studentRandom;

	private String instructorRandom = getRandomString();
	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String instructorSurname = "InstructorSurame" + instructorRandom;

	private String instructorRandom2 = getRandomString();
	private String instructorLogin2 = "instructor2" + instructorRandom2;
	private String instructorName2 = "InstructorName2" + instructorRandom2;
	private String instructorSurname2 = "InstructorSurame2" + instructorRandom2;

	private static String linkName = "McGraw-Hill Campus";
	private String password = "123qweA@";

	private String courseName1 = "CourseName1" + getRandomString();
	private String courseName2 = "CourseName2" + getRandomString();
	private String courseId2;

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
	private String customerNumber = "1EE4-IFLU-VW0I";
	private String sharedSecret = "55C94F";

	private String courseId1;

	private String pageAddressFromEmail = "http://CustomCanvasConfiguration113.mhcampus.com";

	private String nameOfWidget = "MHE_PQA_" + getRandomString();
	private String lunchUrl = "https://login-aws-qa.mhcampus.com/sso/di/d2l/lti/Connect";

	private String d2LCourse2Encrypted;
	private String d2LStudent2Encrypted;

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		Logger.info("Starting test for configuration:");
		Logger.info("LMS = D2l lti | DI: on");
		Logger.info(
				"Course ID – User ID Mapping:  Off  | Gradebook Connector: Yes | SSO Connector: No | Canvas Mapping: No | Legasy: Off ");
		Logger.info(
				"* Instructor Level Token: On | Data connector: On | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Async: Off");

		prepareTestDataInD2l();

		if (d2lApplication.d2lBaseUrl.equals("https://tegrity.desire2learn.com"))
			D2L_FRAME = "First";
		else
			D2L_FRAME = "NoFrame";

		d2lApplication.createD2lLinkConnectedWithInstance(linkName, courseId1, customerNumber, sharedSecret);
		d2lApplication.addCreatedLinkToD2lModule(courseId1, linkName, moduleName);

		// deep

		d2lHomeScreen = d2lApplication.loginToD2lAsAdmin();
		d2lApplication.createNewWidgetPlugin(nameOfWidget, lunchUrl, customerNumber, sharedSecret, courseName2);
		d2lManageExternalToolsScreen = d2lApplication.openD2LManageExternalToolsPage();
		d2lApplication.bindingPluginToCourse(courseName2, customerNumber, sharedSecret, nameOfWidget);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);
		d2lEditHomepageScreen = d2lCourseDetailsScreen.clickEditHomepageBtn();
		d2lCourseDetailsScreen = d2lEditHomepageScreen.addWidget(nameOfWidget);
		d2lContentCourseScreen = d2lApplication.openD2lContentCoursePage(courseId2);
		d2lContentCourseScreen.addModule(moduleName);

		d2LCourse2Encrypted = restApplication.fillUnencryptedIdAndPressEncrypt(courseId2).getResult();
		d2LStudent2Encrypted = restApplication
				.fillUnencryptedIdAndPressEncrypt(Integer.toString(studentRS2.getUserId())).getResult();
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

	// classic
	// ------------------------------------------------------------------------------------------------
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
		browser.closeAllWindowsExceptFirst();
		d2lApplication.d2lLogout(d2lHomeScreen);

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
		browser.closeAllWindowsExceptFirst();
		d2lApplication.d2lLogout(d2lHomeScreen);
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

	// deep
	// -----------------------------------------------------------------------------
	@Test(description = "Check MH Campus link is present for D2L instructor in Deep")
	public void DeepSSObyInstructor() throws Exception {
		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin2, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);
		d2lCourseDetailsScreen.clickOnContinueBtn();
		Assert.verifyTrue(d2lCourseDetailsScreen.isExistErrorMessageText(), "error message is not shown as expected");

		browser.switchTo().defaultContent();
		d2lApplication.d2lLogout(d2lHomeScreen);
	}

	@Test(description = "Check MH Campus link is present for D2L instructor in Deep")
	public void DeepSSObyStudent() throws Exception {
		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin2, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);
		Assert.verifyTrue(d2lCourseDetailsScreen.isExistErrorMessageText(), "error message is not shown as expected");

		browser.switchTo().defaultContent();
		d2lApplication.d2lLogout(d2lHomeScreen);
	}

	@Test(description = "Check DI TestScorableItem form is submitted successfully", dependsOnMethods = {
			"DeepSSObyStudent" })
	public void checkDiSubmittingTestScorableItemFormIsSuccessfull() throws Exception {
		String moduleId = gradebookApplication.fillTestScorableItemFormGetModuleID(customerNumber, providerId,
				Integer.toString(courseOfferingRS2.getId()), gradebookApplication.tegrityServiceLocation, moduleName);

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemFormDIWithModuleId(
				customerNumber, providerId, d2LCourse2Encrypted, assignmentId, assignmentTitle, category, description,
				startDate, dueToDate, scoreType, scorePossible, moduleId, isStudentViewable, isIncludedInGrade,
				gradebookApplication.tegrityServiceLocation, tool_ID);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForD2lDI(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check DI TestScorableItem form is submitted successfully", dependsOnMethods = {
			"checkDiSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkDiSubmittingTestScoreFormIsSuccessfull() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreFormDI(customerNumber, providerId,
				d2LCourse2Encrypted, assignmentId, d2LStudent2Encrypted, commentForStudent2, dateSubmitted,
				scoreReceivedForStudent2, gradebookApplication.tegrityServiceLocation, tool_ID);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForD2lDI(), "TestScore form submit failed");
		// check scorable  item is exists
		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin2, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);
		d2lGradesDetailsScreenForInstructor = d2lCourseDetailsScreen.clickGradesLinkAsInstructor();
		Assert.verifyEquals(
				d2lGradesDetailsScreenForInstructor.getScoreReceived(assignmentTitle,
						studentSurname2 + ", " + studentName2),
				scoreReceivedForStudent2, "ScoreReceived did not match");

	}

	// Additional
	private void prepareTestDataInD2l() throws Exception {

		studentRS1 = d2LApiUtils.createUser(studentName1, studentSurname1, studentLogin1, password,
				D2LUserRole.STUDENT);
		studentRS2 = d2LApiUtils.createUser(studentName2, studentSurname2, studentLogin2, password,
				D2LUserRole.STUDENT);

		instructorRS = d2LApiUtils.createUser(instructorName, instructorSurname, instructorLogin, password,
				D2LUserRole.INSTRUCTOR);
		instructorRS2 = d2LApiUtils.createUser(instructorName2, instructorSurname2, instructorLogin2, password,
				D2LUserRole.INSTRUCTOR);

		courseTemplateRS1 = d2LApiUtils.createCourseTemplate("name" + getRandomString(),
				"code" + RandomStringUtils.randomNumeric(3));
		courseOfferingRS1 = d2LApiUtils.createCourseOfferingByTemplate(courseTemplateRS1, courseName1,
				"code" + RandomStringUtils.randomNumeric(3));
		courseId1 = Integer.toString(courseOfferingRS1.getId());

		courseTemplateRS2 = d2LApiUtils.createCourseTemplate("name2" + getRandomString(),
				"code2" + RandomStringUtils.randomNumeric(3));
		courseOfferingRS2 = d2LApiUtils.createCourseOfferingByTemplate(courseTemplateRS2, courseName2,
				"code" + RandomStringUtils.randomNumeric(3));
		courseId2 = Integer.toString(courseOfferingRS2.getId());
		courseId1 = Integer.toString(courseOfferingRS1.getId());

		d2LApiUtils.createEnrollment(studentRS1, courseOfferingRS1, D2LUserRole.STUDENT);
		d2LApiUtils.createEnrollment(studentRS2, courseOfferingRS2, D2LUserRole.STUDENT);
		d2LApiUtils.createEnrollment(instructorRS, courseOfferingRS1, D2LUserRole.INSTRUCTOR);
		d2LApiUtils.createEnrollment(instructorRS2, courseOfferingRS2, D2LUserRole.INSTRUCTOR);

	}

	private void clearD2lData() throws Exception {

		d2lApplication.loginToD2lAsAdmin();
		browser.pause(2000);
		d2lManageExternalToolsScreen = d2lApplication.openD2LManageExternalToolsPage();
		d2lManageExternalToolsScreen.deleteWidgetLink(nameOfWidget);

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
