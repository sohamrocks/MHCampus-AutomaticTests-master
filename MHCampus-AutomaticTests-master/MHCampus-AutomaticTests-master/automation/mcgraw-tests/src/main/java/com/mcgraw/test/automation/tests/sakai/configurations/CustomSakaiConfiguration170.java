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
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiCourseDetailsScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiCourseGradeOptionsScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiGradesScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiHomeScreen;

/**
 * LMS = Sakai DI: Disable 
 * Gradebook Connector: Yes 
 * SSO Connector: NO 
 * Instructor Level Token: Off 
 * Use Existing Assignments: Off 
 * Fallback to user_id and context_id: Off
 * Async: Off
 */
public class CustomSakaiConfiguration170 extends BaseTest {

	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	private SakaiApplication sakaiApplication;

	@Autowired
	private ISakaiApiService sakaiApiService;

	private AddNewUser studentSakai;
	private AddNewUser instructorSakai;
	private AddNewSite courseSakai;
	private SakaiGradesScreen sakaiGradesScreen;

	private String password = "123qweA@";

	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom = getRandomString();

	private String courseName = "CourseName" + courseRandom;

	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;
	private String studentSurname = "StudentSurname" + studentRandom;

	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String instructorSurname = "InstructorSurname" + instructorRandom;

	private String providerId = "provider_" + getRandomString();
	private String description = "description_" + getRandomString();
	private String assignmentTitle = "title_" + getRandomString();
	private String commentForStudent = "comment_" + getRandomString();
	private String assignmentId = getRandomNumber();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent = GradebookApplication.getRandomScore();
	private String scorePossible = "100";
	private String categoryType = "Blog";
	private String scoreType = "Percentage";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;

	private SakaiHomeScreen sakaiHomeScreen;
	private SakaiCourseDetailsScreen sakaiCourseDetailsScreen;

	private String customerNumber = "3TEO-QLRV-VQ0M";
	private String sharedSecret = "B35D0D";

	private MhCampusIntroductionScreen mhCampusIntroductionScreen;

	private static String SAKAI_FRAME = "First";

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration:");
		Logger.info("LMS = Sakai | DI: Disable ");
		Logger.info(
				"Course ID вЂ“ User ID Mapping: Off | Gradebook Connector: Yes | SSO Connector: No | Canvas Mapping: No ");
		Logger.info(
				"* Instructor Level Token: Off | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Async: Off");

		prepareDataInSakai();

		// allow the students to see the grades
		sakaiHomeScreen = sakaiApplication.loginToSakai(instructorLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(courseSakai.getSiteid());
		sakaiGradesScreen = sakaiCourseDetailsScreen.clickGradebookBtn();
		SakaiCourseGradeOptionsScreen sakaiCourseGradeOptionsScreen = sakaiGradesScreen.goToCourseGradeOptions();
		sakaiCourseGradeOptionsScreen.allowDisplayCourseGradeForStudents();
	}

	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearSakaiData();
	}

	@Test(description = "Check SSO to  MHC as Sakai instructor")
	public void checkSsoAsInstructorSakai() throws Exception {
		sakaiHomeScreen = sakaiApplication.loginToSakai(instructorLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(courseSakai.getSiteid());
		Logger.info("Check MH Campus link is present for Sakai instructor");
		Assert.assertEquals(sakaiCourseDetailsScreen.getMhCampusLinksCount(), 1,
				"Wrong count of MH Campus links for instructor's course " + courseSakai.getSiteid());
		
		mhCampusIntroductionScreen = sakaiCourseDetailsScreen.clickMhCampusLink();
		
		String expectedGreetingText = "Hi " + instructorName + " " + instructorSurname + " -";
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(SAKAI_FRAME), expectedGreetingText,
				"Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(SAKAI_FRAME));
		mhCampusIntroductionScreen.acceptRules(SAKAI_FRAME);
		
		
		String expectedUserName = (instructorName + " " + instructorSurname).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(SAKAI_FRAME), expectedUserName,
				"User name is incorrect");
		Assert.assertTrue(mhCampusIntroductionScreen.isSearchOptionPresent(SAKAI_FRAME), "Search option is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseSakai.getSiteid(), SAKAI_FRAME),
				"Course " + courseSakai.getSiteid() + " is absent");
		Assert.assertEquals(sakaiCourseDetailsScreen.getMhCampusLinksCount(), 1,
				"Wrong count of MH Campus links for instructor's course " + courseSakai.getSiteid());

		sakaiApplication.logoutFromSakai();
	}

	@Test(description = "Check SSO to  MHC as Sakai student", dependsOnMethods = "checkSsoAsInstructorSakai")
	public void checkSsoAsStudentSakai() throws Exception {
		sakaiHomeScreen = sakaiApplication.loginToSakai(studentLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(courseSakai.getSiteid());
		mhCampusIntroductionScreen = sakaiCourseDetailsScreen.clickMhCampusLink();
		String expectedGreetingText = "Hi " + studentName + " " + studentSurname + " -";
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(SAKAI_FRAME), expectedGreetingText,
				"Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(SAKAI_FRAME), "Rules text is incorrect");
		mhCampusIntroductionScreen.acceptRules(SAKAI_FRAME);
		String expectedUserName = (studentName + " " + studentSurname).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(SAKAI_FRAME), expectedUserName,
				"User name is incorrect");
		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(SAKAI_FRAME), "Search option is present");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseSakai.getSiteid(), SAKAI_FRAME),
				"Course " + courseSakai.getSiteid() + " is absent");
		Assert.assertEquals(sakaiCourseDetailsScreen.getMhCampusLinksCount(), 1,
				"Wrong count of MH Campus links for student's course " + courseSakai.getSiteid());

		sakaiApplication.logoutFromSakai();
	}

	@Test(description = "Check TestScorableItem form is submitted successfully", dependsOnMethods = "checkSsoAsStudentSakai")
	public void checkSubmittingTestScorableItemFormIsSuccessfullSakai() throws Exception {
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber,
				providerId, courseSakai.getSiteid(), assignmentId, assignmentTitle, categoryType, description,
				startDate, dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForSakai(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = "checkSubmittingTestScorableItemFormIsSuccessfullSakai")
	public void checkSubmittingTestScoreFormIsSuccessfullForStudentSakai() throws Exception {
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId,
				courseSakai.getSiteid(), assignmentId, studentLogin, commentForStudent, dateSubmitted,
				scoreReceivedForStudent, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForSakai(), "TestScore form submit failed");

		// check the students has correct data
		sakaiHomeScreen = sakaiApplication.loginToSakai(studentLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(courseSakai.getSiteid());
		Assert.verifyEquals(sakaiGradesScreen.getScoreReceived(), scoreReceivedForStudent,
				"ScoreReceived did not match");
		sakaiApplication.logoutFromSakai();
	}

	private void prepareDataInSakai() throws Exception {
		studentSakai = sakaiApiService.createUser(studentLogin, password, studentName, studentSurname);
		instructorSakai = sakaiApiService.createUser(instructorLogin, password, instructorName, instructorSurname);

		courseSakai = sakaiApiService.addNewSite(courseName);
		sakaiApiService.addMemberToSiteWithRole(courseSakai, studentSakai, SakaiUserRole.STUDENT);
		sakaiApiService.addMemberToSiteWithRole(courseSakai, instructorSakai, SakaiUserRole.INSTRUCTOR);
		sakaiApiService.addNewToolToSite(courseSakai, SakaiTool.LINK_TOOL);

		sakaiApplication.completeMhCampusSetupWithSakai(courseSakai.getSiteid(), customerNumber, sharedSecret);
		sakaiApplication.logoutFromSakai();
	}

	private void clearSakaiData() throws Exception {
		if (courseSakai != null)
			sakaiApiService.deletePageWithToolFromSite(courseSakai, SakaiTool.GRADEBOOK);
		if (studentSakai != null)
			sakaiApiService.deleteUser(studentSakai.getEid());
		if (instructorSakai != null)
			sakaiApiService.deleteUser(instructorSakai.getEid());
		if (courseSakai != null)
			sakaiApiService.deleteSite(courseSakai.getSiteid());

	}

	private String getRandomString() {
		return RandomStringUtils.randomNumeric(5);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}

}
