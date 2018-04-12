package com.mcgraw.test.automation.tests.d2l.configurations;

/**
* LMS = D2L 
* DI: Disabled
* Course ID – User ID Mapping: None
* Gradebook Connector: Yes 
* Gradebook Connector type : lti 
* SSO Connector: No
* Data Connector: No
* Canvas Mapping: No
* Instructor Level Token: Off
* Use Existing Assignments: On 
* Fallback to user_id and context_id: Off
* Legacy: Off
* Async: Off
*/
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
import com.mcgraw.test.automation.ui.rest.TestRestApi;

public class CustomD2LConfiguration96 extends BaseTest {
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
	private String commentForStudentDI = "comment_" + getRandomString();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudentDI = GradebookApplication.getRandomScore();

	private D2lHomeScreen d2lHomeScreen;
	private D2lCourseDetailsScreen d2lCourseDetailsScreen;
	private D2lContentCourseScreen d2lContentCourseScreen;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;
	private D2lGradesDetailsScreenForInstructorV10 d2lGradesDetailsScreenForInstructor;
	private D2lManageExternalToolsScreen d2lManageExternalToolsScreen;
	private D2lEditHomepageScreen d2lEditHomepageScreen;
	private boolean checkBoxShowActiveCourse = false;

	private String d2LCourse2Encrypted;
	private String d2LStudent2Encrypted;

	private String courseId;
	private String courseIdDI;

	private static String moduleName = "Module" + RandomStringUtils.randomNumeric(5);
	private String studentRandom = getRandomString();
	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;
	private String studentSurname = "StudentSurname" + studentRandom;

	private String instructorRandom = getRandomString();
	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String instructorSurname = "InstructorSurame" + instructorRandom;

	// Data for Deep Integration
	private String studentRandomDI = getRandomString();
	private String studentLoginDI = "studentDI" + studentRandomDI;
	private String studentNameDI = "StudentNameDI" + studentRandomDI;
	private String studentSurnameDI = "StudentSurnameDI" + studentRandomDI;

	private String instructorRandomDI = getRandomString();
	private String instructorLoginDI = "instructorDI" + instructorRandomDI;
	private String instructorNameDI = "InstructorNameDI" + instructorRandomDI;
	private String instructorSurnameDI = "InstructorSurameDI" + instructorRandomDI;

	private String nameOfWidget = "MHE_PQA_" + getRandomString();
	private String launchUrl = "https://login-aws-qa.mhcampus.com/sso/di/d2l/lti/Connect";

	private static String linkName = "McGraw-Hill Campus";
	private String password = "123qweA@";

	private String courseName = "CourseName" + getRandomString();
	private String courseNameDI = "CourseNameDI" + getRandomString();

	@Autowired
	private D2LApiUtils d2LApiUtils;

	private D2LUserRS studentRS;
	private D2LUserRS studentRSDI;
	private D2LUserRS instructorRS;
	private D2LUserRS instructorRSDI;
	private D2LCourseTemplateRS courseTemplateRS;
	private D2LCourseOfferingRS courseOfferingRS;
	private D2LCourseTemplateRS courseTemplateRSDI;
	private D2LCourseOfferingRS courseOfferingRSDI;

	private String providerId = "Connect";
	private String description = " ";

	private String tool_ID = "Connect";
	private static String D2L_FRAME;
	private String customerNumber = "2OLJ-XTUL-JJHJ";
	private String sharedSecret = "7CF23C";

	private String pageAddressFromEmail = "http://CustomCanvasConfiguration96.mhcampus.com";

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		Logger.info("Starting test for configuration:");
		Logger.info("LMS = D2L | DI: Enabled ");
		Logger.info(
				"Course ID – User ID Mapping: None | Gradebook Connector: No | Gradebook Connector type : None | SSO Connector: No | Data Connector:  No| Canvas Mapping: No ");
		Logger.info(
				"* Instructor Level Token: Off | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Legacy: Off | Async: Off");

		prepareTestDataInD2l();

		if (d2lApplication.d2lBaseUrl.equals("https://tegrity.desire2learn.com"))
			D2L_FRAME = "First";
		else
			D2L_FRAME = "NoFrame";

		d2lApplication.createD2lLinkConnectedWithInstance(linkName, courseId, customerNumber, sharedSecret);
		d2lApplication.addCreatedLinkToD2lModule(courseId, linkName, moduleName);

	}

	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearD2lData();
	}

	// Test Start
	// Classic integration
	@Test(description = "Check MH Campus link is present for D2L instructor")
	public void ssoByInstructor() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName);
		d2lContentCourseScreen = d2lCourseDetailsScreen.clickContentLink();
		d2lContentCourseScreen.chooseModuleBlock(moduleName);
		Assert.assertEquals(d2lContentCourseScreen.getMhCampusLinksCount(), 1,
				"Wrong count of MH Campus links for instructor's course " + courseName);

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
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName, D2L_FRAME),
				"Course " + courseName + " is absent");

		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(D2L_FRAME), "Search option is absent");

		browser.closeAllWindowsExceptFirst();
		d2lApplication.d2lLogout(d2lHomeScreen);

	}

	@Test(description = "Check MH Campus link is present for D2L student", dependsOnMethods = { "ssoByInstructor" })
	public void ssoByStudent() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin, password);

		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName);
		d2lContentCourseScreen = d2lCourseDetailsScreen.clickContentLink();
		d2lContentCourseScreen.chooseModuleBlock(moduleName);
		Assert.assertEquals(d2lContentCourseScreen.getMhCampusLinksCount(), 1,
				"Wrong count of MH Campus links for student's course " + courseName);

		mhCampusIntroductionScreen = d2lContentCourseScreen.clickMhCampusLink();

		String expectedGreetingText = "Hi " + studentName + " " + studentSurname + " -";
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(D2L_FRAME), expectedGreetingText,
				"Greeting text is incorrect");
		if (!d2lApplication.d2lBaseUrl.equals("https://tegrity.desire2learn.com")) {
			Assert.verifyTrue(
					mhCampusIntroductionScreen.getFrameAddress(D2L_FRAME).contains(pageAddressFromEmail.toLowerCase()),
					"The address of frame is not contain instance login page address");
		}

		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(D2L_FRAME), "Rules text is incorrect");

		mhCampusIntroductionScreen.acceptRules(D2L_FRAME);

		String expectedUserName = (studentName + " " + studentSurname).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(D2L_FRAME), expectedUserName,
				"User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName, D2L_FRAME),
				"Course " + courseName + " is absent");

		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(D2L_FRAME), "Search option is present");

		browser.closeAllWindowsExceptFirst();
		d2lApplication.d2lLogout(d2lHomeScreen);
	}

	// Deep Integration
	@Test(description = "Check MH Campus link is present for D2L instructor in Deep Integration", dependsOnMethods = {
			"ssoByStudent" })
	public void ssoByInstructorDeep() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(instructorLoginDI, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseNameDI);
		d2lCourseDetailsScreen.clickOnContinueBtn();

		Assert.verifyTrue(d2lCourseDetailsScreen.isExistErrorMessageText(), "Error message is not shown");

		browser.switchTo().defaultContent();
		d2lApplication.d2lLogout(d2lHomeScreen);
	}

	@Test(description = "Check MH Campus link is present for D2L instructor in Deep Integration", dependsOnMethods = {
			"ssoByInstructorDeep" })
	public void ssoByStudentDeep() throws Exception {
		d2lHomeScreen = d2lApplication.loginToD2l(studentLoginDI, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseNameDI);

		Assert.verifyTrue(d2lCourseDetailsScreen.isExistErrorMessageText(), "Error message is not shown");

		browser.switchTo().defaultContent();
		d2lApplication.d2lLogout(d2lHomeScreen);
	}

	@Test(description = "Check TestScorableItem form is submitted successfully", dependsOnMethods = "ssoByStudentDeep")
	public void checkSubmittingTestScorableItemFormIsSuccessfullDeep() throws Exception {

		String moduleId = gradebookApplication.fillTestScorableItemFormGetModuleID(customerNumber, providerId,
				Integer.toString(courseOfferingRSDI.getId()), gradebookApplication.tegrityServiceLocation, moduleName);

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemFormDIWithModuleId(
				customerNumber, providerId, d2LCourse2Encrypted, assignmentId, assignmentTitle, category, description,
				startDate, dueToDate, scoreType, scorePossible, moduleId, isStudentViewable, isIncludedInGrade,
				gradebookApplication.tegrityServiceLocation, tool_ID);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForD2lDI(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = {
			"checkSubmittingTestScorableItemFormIsSuccessfullDeep" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudentDeep() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreFormDI(customerNumber, providerId,
				d2LCourse2Encrypted, assignmentId, d2LStudent2Encrypted, commentForStudentDI, dateSubmitted,
				scoreReceivedForStudentDI, gradebookApplication.tegrityServiceLocation, tool_ID);
					    
		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForD2lDI(), "TestScore form submit failed");

		// check the instructor for a correct data
		d2lHomeScreen = d2lApplication.loginToD2l(instructorLoginDI, password);
	    d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseNameDI);
	    d2lGradesDetailsScreenForInstructor = d2lCourseDetailsScreen.clickGradesLinkAsInstructor();
	    Assert.verifyEquals(
	      d2lGradesDetailsScreenForInstructor.getScoreReceived(assignmentTitle,
	        studentSurnameDI + ", " + studentNameDI),
	      scoreReceivedForStudentDI, "ScoreReceived did not match");


		d2lApplication.d2lLogout(d2lHomeScreen);
	}
	// Test Finish

	// Private methods for tests
	private void prepareTestDataInD2l() throws Exception {
		// Classic
		studentRS = d2LApiUtils.createUser(studentName, studentSurname, studentLogin, password, D2LUserRole.STUDENT);
		instructorRS = d2LApiUtils.createUser(instructorName, instructorSurname, instructorLogin, password,
				D2LUserRole.INSTRUCTOR);

		courseTemplateRS = d2LApiUtils.createCourseTemplate("name" + getRandomString(),
				"code" + RandomStringUtils.randomNumeric(3));
		courseOfferingRS = d2LApiUtils.createCourseOfferingByTemplate(courseTemplateRS, courseName,
				"code" + RandomStringUtils.randomNumeric(3));
		courseId = Integer.toString(courseOfferingRS.getId());

		d2LApiUtils.createEnrollment(studentRS, courseOfferingRS, D2LUserRole.STUDENT);
		d2LApiUtils.createEnrollment(instructorRS, courseOfferingRS, D2LUserRole.INSTRUCTOR);

		if (d2lApplication.d2lBaseUrl.equals("https://tegrity.desire2learn.com"))
			D2L_FRAME = "First";
		else
			D2L_FRAME = "NoFrame";
		checkBoxShowActiveCourse = tegrityAdministrationApplication.getCheckBoxShowActiveCourse();

		d2lApplication.createD2lLinkConnectedWithInstance(linkName, courseId, customerNumber, sharedSecret);
		d2lApplication.addCreatedLinkToD2lModule(courseId, linkName, moduleName);

		// d2lApplication.d2lLogout(d2lHomeScreen);

		// Deep Integration
		studentRSDI = d2LApiUtils.createUser(studentNameDI, studentSurnameDI, studentLoginDI, password, D2LUserRole.STUDENT);
		instructorRSDI = d2LApiUtils.createUser(instructorNameDI, instructorSurnameDI, instructorLoginDI, password, D2LUserRole.INSTRUCTOR);

		courseTemplateRSDI = d2LApiUtils.createCourseTemplate("name2" + getRandomString(), "code2" + RandomStringUtils.randomNumeric(3));
		courseOfferingRSDI = d2LApiUtils.createCourseOfferingByTemplate(courseTemplateRSDI, courseNameDI,
				"code" + RandomStringUtils.randomNumeric(3));
		courseIdDI = Integer.toString(courseOfferingRSDI.getId());
		
	    d2LCourse2Encrypted = restApplication.fillUnencryptedIdAndPressEncrypt(courseIdDI).getResult();
	    d2LStudent2Encrypted = restApplication.fillUnencryptedIdAndPressEncrypt(Integer.toString(studentRSDI.getUserId())).getResult();

		d2LApiUtils.createEnrollment(studentRSDI, courseOfferingRSDI, D2LUserRole.STUDENT);
		d2LApiUtils.createEnrollment(instructorRSDI, courseOfferingRSDI, D2LUserRole.INSTRUCTOR);

		d2lHomeScreen =d2lApplication.loginToD2lAsAdmin();
		d2lApplication.createNewWidgetPlugin(nameOfWidget, launchUrl, customerNumber, sharedSecret, courseNameDI);		
		d2lManageExternalToolsScreen = d2lApplication.openD2LManageExternalToolsPage();		

		d2lApplication.bindingPluginToCourse(courseNameDI,customerNumber,sharedSecret, nameOfWidget);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseNameDI);
		d2lEditHomepageScreen = d2lCourseDetailsScreen.clickEditHomepageBtn();
		d2lCourseDetailsScreen = d2lEditHomepageScreen.addWidget(nameOfWidget);		
		
		d2lContentCourseScreen = d2lApplication.openD2lContentCoursePage(courseIdDI);
		d2lContentCourseScreen.addModule(moduleName);
		
		browser.switchTo().defaultContent();
		d2lApplication.d2lLogout(d2lHomeScreen);
	}

	private void clearD2lData() throws Exception {
		if (studentRS != null)
			d2LApiUtils.deleteUser(studentRS);
		if (studentRSDI != null)
			d2LApiUtils.deleteUser(studentRSDI);
		if (instructorRS != null)
			d2LApiUtils.deleteUser(instructorRS);
		if (instructorRSDI != null)
			d2LApiUtils.deleteUser(instructorRSDI);
		if (courseOfferingRS != null)
			d2LApiUtils.deleteCourseOffering(courseOfferingRS);
		if (courseOfferingRSDI != null)
			d2LApiUtils.deleteCourseOffering(courseOfferingRSDI);
		if (courseTemplateRS != null)
			d2LApiUtils.deleteCourseTemplate(courseTemplateRS);
		if (courseTemplateRSDI != null)
			d2LApiUtils.deleteCourseTemplate(courseTemplateRSDI);
		d2lApplication.loginToD2lAsAdmin();
		browser.pause(2000);
		d2lManageExternalToolsScreen = d2lApplication.openD2LManageExternalToolsPage();
		d2lManageExternalToolsScreen.deleteWidgetLink(nameOfWidget);
		
		d2lApplication.d2lLogout(d2lHomeScreen);
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}
}
