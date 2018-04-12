package com.mcgraw.test.automation.tests.sakai.configurations;


import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.sakai.SakaiTool;
import com.mcgraw.test.automation.api.sakai.SakaiUserRole;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewSite;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewUser;
import com.mcgraw.test.automation.api.sakai.service.ISakaiApiService;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.applications.SakaiApplication;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
//import com.mcgraw.test.automation.ui.sakai.SakaiAdminHomePage;
import com.mcgraw.test.automation.ui.sakai.SakaiCourseDetailsScreen;
//import com.mcgraw.test.automation.ui.sakai.SakaiExternalToolsScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiGradesScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiHomeScreen;
//import com.mcgraw.test.automation.ui.sakai.SakaiSetupExternalToolsScreen;

/**
 * LMS = Sakai
 * DI: Enabled
 * Course ID – User ID Mapping:  None
 * Gradebook Connector: Yes 
 * SSO Connector: Off 
 * Canvas Mapping: Off 
 * Instructor Level Token: Off 
 * Use Existing Assignments: Off
 * Fallback to user_id and context_id: Off	
 * Async: Off
 */
public class CustomSakaiConfiguration179 extends BaseTest {

	@Autowired
	private SakaiApplication sakaiApplication;

	@Autowired
	private ISakaiApiService sakaiApiService;

	@Autowired
	private GradebookApplication gradebookApplication;

	private String password = "123qweA@";

	// for Deep integration
	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom = getRandomString();

	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String instructorSurname = "InstructorSurname" + instructorRandom;

	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;
	private String studentSurname = "StudentSurname" + instructorRandom;

	// private String courseName = "CourseName" + courseRandom;

	// Sakai User Data
	private AddNewUser studentSakai;
	private AddNewUser instructorSakai;
	private AddNewSite courseSakai;

	// data for creating assignment
	private String providerId = "Connect";
	private String assignmentId = getRandomNumber();
	private String assignmentTitle = "title_" + getRandomString();
	// private String category = "Test";
	private String description = "description_" + getRandomString();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String scoreType = "Percentage";
	private String scorePossible = "100";
	private String categoryType = "Blog";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;
	private String commentForStudent = "comment_2" + getRandomString();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent = GradebookApplication.getRandomScore();

	private SakaiHomeScreen sakaiHomeScreen;
	private SakaiCourseDetailsScreen sakaiCourseDetailsScreen;
	//private SakaiAdminHomePage sakaiAdminHomePage;
	//private SakaiExternalToolsScreen sakaiExternalToolsScreen;
	//private SakaiSetupExternalToolsScreen sakaiSetupExternalToolsScreen;
	private SakaiGradesScreen sakaiGradesScreen;

	// other data
	private String customerNumber = "3I0U-4RE9-VZQG";
	private String sharedSecret = "5F8C6A";
	// private String tegrityUsername =
	// "admin@CustomCanvasConfiguration179.mhcampus.com";
	// private String tegrityPassword = "sihlvpxn";

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration:");
		Logger.info("LMS = Sakai | DI: Enabled ");
		Logger.info(
				"Course ID – User ID Mapping: None | Gradebook Connector: Yes | SSO Connector: No | Canvas Mapping: No ");
		Logger.info(
				"* Instructor Level Token: Off | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Async: Off");

		prepareDataInSakai();
	}

	// @AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearSakaiData();
	}

	// SAKAI
	@Test(description = "Check MH Campus link is present for Sakai instructor", enabled = true)
	public void checkMhCampusLinkIsPresentInInstructorsCourseSakai() throws Exception {

		sakaiHomeScreen = sakaiApplication.loginToSakai(instructorLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(courseSakai.getSiteid());
		// sakaiGradesScreen = sakaiCourseDetailsScreen.clickGradebookBtn();

		Assert.assertEquals(sakaiCourseDetailsScreen.getMhCampusLinksCount(), 1,
				"Wrong count of MH Campus links for instructor's course " + courseSakai.getSiteid());
		sakaiApplication.logoutFromSakai();
	}

	@Test(description = "Check MH Campus link is present for Sakai student", dependsOnMethods = {
			"checkMhCampusLinkIsPresentInInstructorsCourseSakai" })
	public void checkMhCampusLinkIsPresentInStudentsCourseSakai() throws Exception {

		sakaiHomeScreen = sakaiApplication.loginToSakai(studentLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(courseSakai.getSiteid());

		Assert.assertEquals(sakaiCourseDetailsScreen.getMhCampusLinksCount(), 1,
				"Wrong count of MH Campus links for student's course " + courseSakai.getSiteid());
		sakaiApplication.logoutFromSakai();
	}

	@Test(description = "Check TestScorableItem form is submitted successfully", dependsOnMethods = {
			"checkMhCampusLinkIsPresentInStudentsCourseSakai" })
	public void checkSubmittingTestScorableItemFormIsSuccessfullSakai() throws Exception {

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber,
				providerId, courseSakai.getSiteid(), assignmentId, assignmentTitle, categoryType, description,
				startDate, dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForSakai(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = {
			"checkSubmittingTestScorableItemFormIsSuccessfullSakai" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudentSakai() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId,
				courseSakai.getSiteid(), assignmentId, studentLogin, commentForStudent, dateSubmitted,
				scoreReceivedForStudent, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForSakai(), "TestScore form submit failed");
	}

	@Test(description = "Check Sakai instructor can see Gradebook data", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudentSakai" })
	public void checkInstructorCanSeeAverageGrade() throws Exception {

		sakaiHomeScreen = sakaiApplication.loginToSakai(instructorLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(courseSakai.getSiteid());
		sakaiGradesScreen = sakaiCourseDetailsScreen.clickGradebookBtn();

		Assert.verifyEquals(sakaiGradesScreen.getAssignmentTitle(), assignmentTitle, "Assignment title did not match");
		Assert.verifyEquals(sakaiGradesScreen.getScorePossible(), scorePossible, "Score possible did not match");
		Assert.verifyEquals(sakaiGradesScreen.getDueToDate(), dueToDate, "Due to date did not match");
		Assert.verifyEquals(sakaiGradesScreen.getScoreReceived(),
				String.valueOf(Integer.parseInt(scoreReceivedForStudent)), "Score is not correct");

		sakaiApplication.logoutFromSakai();
	}

	public void prepareDataInSakai() throws Exception {

		studentSakai = sakaiApiService.createUser(studentLogin, password, studentName, studentSurname);
		instructorSakai = sakaiApiService.createUser(instructorLogin, password, instructorName, instructorSurname);

		courseSakai = sakaiApiService.addNewSite(courseRandom);
		sakaiApiService.addMemberToSiteWithRole(courseSakai, studentSakai, SakaiUserRole.STUDENT);
		sakaiApiService.addMemberToSiteWithRole(courseSakai, instructorSakai, SakaiUserRole.INSTRUCTOR);
		sakaiApiService.addNewToolToSite(courseSakai, SakaiTool.LINK_TOOL);

		sakaiApplication.completeMhCampusSetupWithSakai(courseSakai.getSiteid(), customerNumber, sharedSecret);
		sakaiApplication.logoutFromSakai();
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}

	private void clearSakaiData() throws Exception {

		if (courseSakai != null)
			sakaiApiService.deletePageWithToolFromSite(courseSakai, SakaiTool.LINK_TOOL);
		if (studentSakai != null)
			sakaiApiService.deleteUser(studentSakai.getEid());
		if (instructorSakai != null)
			sakaiApiService.deleteUser(instructorSakai.getEid());
		if (courseSakai != null)
			sakaiApiService.deleteSite(courseSakai.getSiteid());
	}
}
