/**
* LMS = D2L
* DI: Enabled 
* Course ID вЂ“ User ID Mapping: None
* Gradebook Connector: No
* Gradebook Connector type : none
* SSO Connector: No
* Data Connector: No
* Canvas Mapping: No
* Instructor Level Token: Off 
* Use Existing Assignments: Off
* Fallback to user_id and context_id: Off
* Legacy: On
* Async: Off
*/

package com.mcgraw.test.automation.tests.d2l.configurations;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
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
import com.mcgraw.test.automation.ui.d2l.base.D2lEditWidgetScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lHomeScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lManageExternalToolsScreen;
import com.mcgraw.test.automation.ui.d2l.v10.D2lGradesDetailsScreenForInstructorV10;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;

public class CustomD2LConfiguration106 extends BaseTest {

	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	private D2LApplication d2lApplication;

	@Autowired
	private D2LApiUtils d2LApiUtils;

	@Autowired
	private RestApplication restApplication;

	// prepare user data Deep
	private String studentRandom = getRandomString();
	private String studentLogin2 = "student2" + studentRandom;
	private String studentName2 = "StudentName2" + studentRandom;
	private String studentSurname2 = "StudentSurname2" + studentRandom;
	private String instructorRandom = getRandomString();
	private String instructorLogin2 = "instructor2" + instructorRandom;
	private String instructorName2 = "InstructorName2" + instructorRandom;
	private String instructorSurname2 = "InstructorSurame2" + instructorRandom;
	private String password = "123qweA@";
	private String courseName2 = "CourseName2" + getRandomString();
	private String commentForStudent2 = "comment for student" + getRandomString();
	private String providerId = "Connect";



	// D2L user data Deep
	private D2LUserRS studentRS2;
	private D2LUserRS instructorRS2;
	private D2LCourseTemplateRS courseTemplateRS2;
	private D2LCourseOfferingRS courseOfferingRS2;
	private String launchUrl = "https://login-aws-qa.mhcampus.com/sso/di/d2l/lti/Connect";
	private String widgetName = "MHE_PQA_" + getRandomString();
	private String courseId2;
	// data for creation assignment

	private String assignmentId = getRandomNumber();
	private String assignmentTitle = "title_" + getRandomString();
	private String category = "category_" + getRandomString();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String scoreType = "Percentage";
	private String scorePossible = "100";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;
	private boolean checkBoxShowActiveCourse = false;
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent2 = GradebookApplication.getRandomScore();

	private D2lHomeScreen d2lHomeScreen;
	private D2lCourseDetailsScreen d2lCourseDetailsScreen;
	private D2lContentCourseScreen d2lContentCourseScreen;
	private D2lManageExternalToolsScreen d2lManageExternalToolsScreen;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;
	private D2lEditHomepageScreen d2lEditHomepageScreen;
	private D2lEditWidgetScreen d2lEditWidgetScreen;
	private D2lGradesDetailsScreenForInstructorV10 d2lGradesDetailsScreenForInstructor;

	private static String linkName = "McGraw-Hill Campus";
	private static String D2L_FRAME;
	private static String moduleName = "Module" + RandomStringUtils.randomNumeric(5);

	
	  private String customerNumber = "3RD9-7J2J-NK3V"; 
	  private String sharedSecret = "A99F7E";
	 
	/*private String customerNumber = "3QTX-LHUX-B2PO";
	private String sharedSecret = "33E8F7";*/
	private String pageAddressFromEmail = "http://CustomCanvasConfiguration106.mhcampus.com";
	private String description = "description" + getRandomString();
	private String tool_ID = "Connect";

	private String d2LCourse2Encrypted;
	private String d2LStudent2Encrypted;

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration:");
		Logger.info("LMS = D2L | DI: Enabled  ");
		Logger.info(
				"Course ID вЂ“ User ID Mapping: None | Gradebook Connector: No | Gradebook Connector type : none | SSO Connector: No | Data Connector: No | Canvas Mapping: No ");
		Logger.info(
				"* Instructor Level Token: Off | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Legacy: On | Async: Off");

		prepareDataInD2lDeep();
	}

	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearD2lDataDeep();
	}

	// Deep Integration
	@Test(description = "For D2L instructor2 MH Campus link baheves correctly(Deep integration)")
	public void checkMHCampusLinkBehavesCorrectlyForD2LInstructor2() throws Exception {
		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin2, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);
		d2lCourseDetailsScreen.clickOnContinueBtn();
		Assert.verifyTrue(d2lCourseDetailsScreen.isExistErrorMessageText(), "error message is not shown as expected");
		browser.switchTo().defaultContent();
		d2lApplication.d2lLogout(d2lHomeScreen);
	}

	@Test(description = "For D2L student2 MH Campus link baheves correctly(Deep integration)", dependsOnMethods = "checkMHCampusLinkBehavesCorrectlyForD2LInstructor2")
	public void checkMHCampusLinkBehavesCorrectlyForD2LStudent2() throws Exception {
		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin2, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);
		Assert.verifyTrue(d2lCourseDetailsScreen.isExistErrorMessageText(), "error message is not shown as expected");
		browser.switchTo().defaultContent();
		d2lApplication.d2lLogout(d2lHomeScreen);
	}

	@Test(description = "Check TestScorableItem form is submitted successfully for DI", dependsOnMethods = "checkMHCampusLinkBehavesCorrectlyForD2LStudent2")
	public void checkSubmittingTestScorableItemFormIsSuccessfullDeep() throws Exception {

		String moduleId = gradebookApplication.fillTestScorableItemFormGetModuleID(customerNumber, providerId,
				Integer.toString(courseOfferingRS2.getId()), gradebookApplication.tegrityServiceLocation, moduleName);

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemFormDIWithModuleId(
				customerNumber, providerId, d2LCourse2Encrypted, assignmentId, assignmentTitle, category, description,
				startDate, dueToDate, scoreType, scorePossible, moduleId, isStudentViewable, isIncludedInGrade,
				gradebookApplication.tegrityServiceLocation, tool_ID);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForD2lDI(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully for DI", dependsOnMethods = "checkSubmittingTestScorableItemFormIsSuccessfullDeep")
	public void checkSubmittingTestScoreFormIsSuccessfullForStudentDeep() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreFormDI(customerNumber, providerId,
				d2LCourse2Encrypted, assignmentId, d2LStudent2Encrypted, commentForStudent2, dateSubmitted,
				scoreReceivedForStudent2, gradebookApplication.tegrityServiceLocation, tool_ID);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForD2lDI(), "TestScore form submit failed");

		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin2, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);
		d2lGradesDetailsScreenForInstructor = d2lCourseDetailsScreen.clickGradesLinkAsInstructor();
		Assert.verifyEquals(
				d2lGradesDetailsScreenForInstructor.getScoreReceived(assignmentTitle,
						studentSurname2 + ", " + studentName2),
				scoreReceivedForStudent2, "ScoreReceived did not match");
	}

	private void prepareDataInD2lDeep() throws Exception {
		// Users and course
		studentRS2 = d2LApiUtils.createUser(studentName2, studentSurname2, studentLogin2, password,
				D2LUserRole.STUDENT);
		instructorRS2 = d2LApiUtils.createUser(instructorName2, instructorSurname2, instructorLogin2, password,
				D2LUserRole.INSTRUCTOR);
		courseTemplateRS2 = d2LApiUtils.createCourseTemplate("name" + getRandomString(),
				"code" + RandomStringUtils.randomNumeric(3));
		courseOfferingRS2 = d2LApiUtils.createCourseOfferingByTemplate(courseTemplateRS2, courseName2,
				"code" + RandomStringUtils.randomNumeric(3));
		courseId2 = Integer.toString(courseOfferingRS2.getId());
		d2LApiUtils.createEnrollment(studentRS2, courseOfferingRS2, D2LUserRole.STUDENT);
		d2LApiUtils.createEnrollment(instructorRS2, courseOfferingRS2, D2LUserRole.INSTRUCTOR);
		// Widget
		d2lHomeScreen = d2lApplication.loginToD2lAsAdmin();
		d2lApplication.createNewWidgetPlugin(widgetName, launchUrl, customerNumber, sharedSecret, courseName2);
		d2lManageExternalToolsScreen = d2lApplication.openD2LManageExternalToolsPage();
		d2lApplication.bindingPluginToCourse(courseName2, customerNumber, sharedSecret, widgetName);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);
		d2lEditHomepageScreen = d2lCourseDetailsScreen.clickEditHomepageBtn();
		d2lCourseDetailsScreen = d2lEditHomepageScreen.addWidget(widgetName);

		d2lContentCourseScreen = d2lApplication.openD2lContentCoursePage(courseId2);
		d2lContentCourseScreen.addModule(moduleName);

		d2LCourse2Encrypted = restApplication.fillUnencryptedIdAndPressEncrypt(courseId2).getResult();
		d2LStudent2Encrypted = restApplication
				.fillUnencryptedIdAndPressEncrypt(Integer.toString(studentRS2.getUserId())).getResult();

	}

	private void clearD2lDataDeep() throws Exception {
		d2lHomeScreen = d2lApplication.loginToD2lAsAdmin();
		browser.pause(2000);
		d2lManageExternalToolsScreen = d2lApplication.openD2LManageExternalToolsPage();
		d2lManageExternalToolsScreen.deleteWidgetLink(widgetName);

		if (studentRS2 != null)
			d2LApiUtils.deleteUser(studentRS2);
		if (instructorRS2 != null)
			d2LApiUtils.deleteUser(instructorRS2);
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
