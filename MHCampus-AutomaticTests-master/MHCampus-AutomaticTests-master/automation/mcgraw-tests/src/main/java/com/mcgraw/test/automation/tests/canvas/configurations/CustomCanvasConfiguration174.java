package com.mcgraw.test.automation.tests.canvas.configurations;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasAssignmentRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasCourseRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasSubmissionRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUser;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserEnrollmentRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserRS;
import com.mcgraw.test.automation.api.rest.canvas.service.CanvasApiUtils;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.CanvasApplication;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasHomeScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;

/**
 * LMS = Canvas
 * DI: Disabled
 * Course ID – User ID Mapping:  Internal – Internal 
 * Gradebook Connector: Yes 
 * SSO Connector: Off 
 * Canvas Mapping: On 
 * Instructor Level Token: Off 
 * Use Existing Assignments: Off
 * Fallback to user_id and context_id: Off	
 * Async: Off
 */
public class CustomCanvasConfiguration174 extends BaseTest {

	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	@Autowired
	private GradebookApplication gradebookApplication;
	
	
	//Preparing users for test (SIS is generated randomly)
	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom = getRandomString();
		
	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;
	
	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	
	private String courseName = "CourseName" + courseRandom;
	
	private String password = "123qweA@";

	//Creating CanvasUsers (will be initialized in beforeClass)
	private CanvasUser studentCanvas;
	private CanvasUser instructorCanvas;
	private CanvasCourseRS courseCanvas;
	private CanvasUserEnrollmentRS studentEnrollmentCanvas;
	private CanvasUserEnrollmentRS instructorEnrollmentCanvas;

	//scorableItem data
	private String providerId = "Connect";//"provider_" + getRandomString();
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
	
	private CanvasHomeScreen canvasHomeScreen;
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;
	private CanvasAssignmentRS canvasAssignmentRS;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;

	// other data
	private String customerNumber = "29U6-G6FU-I3ED";
	private String sharedSecret = "8E1D0B";
	// private String tegrityUsername =
	// "admin@CustomCanvasConfiguration174.mhcampus.com";
	// private String tegrityPassword = "kkyksgtp";

	private String appName = "McGraw Hill Campus";
	private String moduleName = "MH Campus";
	
	private static final String CANVAS_FRAME = "tool_content";
	
	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration:");
		Logger.info("LMS = Canvas | DI: Disabled ");
		Logger.info(
				"Course ID – User ID Mapping: Internal – Internal | Gradebook Connector: Yes | SSO Connector: No | Canvas Mapping: Yes ");
		Logger.info(
				"* Instructor Level Token: Off | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Async: Off");
		
		prepareDataInCanvas();
	}

	// @AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearCanvasData();
	}

	// CANVAS Classic
	@Test(description = "Check SSO to MHC as Instructor with ILT ", enabled = true)
	public void ssoAsInstructorWithLti() throws Exception {
		
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForLti(false);
		mhCampusIntroductionScreen.acceptRules(CANVAS_FRAME);
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), instructorName.toUpperCase(),
				"User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName, CANVAS_FRAME),
				"Course" + courseName + " is absent");
		canvasApplication.logoutFromCanvas();
	}
	
	
	@Test(description = "Check SSO to MHC as Student", dependsOnMethods = { "ssoAsInstructorWithLti" })
	public void ssoAsStudent() throws Exception {
		
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForLti(false);
		mhCampusIntroductionScreen.acceptRules(CANVAS_FRAME);
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), studentName.toUpperCase(),
				"User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName, CANVAS_FRAME),
				"Course" + courseName + " is absent");
		canvasApplication.logoutFromCanvas();
	}
	
	@Test(description = "Check TestScorableItem form is submitted successfully", dependsOnMethods = { "ssoAsStudent" })
	public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {
		
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber,
				providerId, Integer.toString(courseCanvas.getId()), assignmentId, assignmentTitle, category, description,
				startDate, dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(courseCanvas, assignmentTitle);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");
	}
	
	
	
	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = { "checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent() throws Exception {
		
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId,
				Integer.toString(courseCanvas.getId()), assignmentId, Integer.toString(studentCanvas.getId()), commentForStudent, dateSubmitted,
				scoreReceivedForStudent, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS, studentCanvas);
		Assert.assertEquals(canvasSubmissions.size(), 1,
				"Student " + studentLogin + " can see the comment and Score received of");		
	}

	// private methods for Tests
	private void prepareDataInCanvas() throws Exception {
		studentCanvas = canvasApiUtils.createUser(studentLogin, password, studentName);
		instructorCanvas = canvasApiUtils.createUser(instructorLogin, password, instructorName);

		courseCanvas = canvasApiUtils.createPublishedCourse(courseName);
		
		studentEnrollmentCanvas = canvasApiUtils.enrollToCourseAsActiveStudent(studentCanvas, courseCanvas);
		instructorEnrollmentCanvas = canvasApiUtils.enrollToCourseAsActiveTeacher(instructorCanvas, courseCanvas);
		
		canvasHomeScreen = canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin,
				canvasApplication.canvasAdminPassword);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseName);
		canvasCourseDetailsScreen.createMhCampusApplication(appName, customerNumber, sharedSecret,
				canvasApplication.ltiLaunchUrl);
		canvasApplication.logoutFromCanvas();

		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		canvasCourseDetailsScreen.createModuleWithApplication(moduleName, appName);
		canvasApplication.logoutFromCanvas();
	}


	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}


	private void clearCanvasData() throws Exception {

		if ((studentEnrollmentCanvas != null) & (courseCanvas != null))
			canvasApiUtils.deleteEnrollment(studentEnrollmentCanvas, courseCanvas);
		if ((instructorEnrollmentCanvas != null) & (courseCanvas != null))
			canvasApiUtils.deleteEnrollment(instructorEnrollmentCanvas, courseCanvas);
		if (courseCanvas != null)
			canvasApiUtils.deleteCourse(courseCanvas);
		if (studentCanvas != null) {
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(studentCanvas);
			canvasApiUtils.deleteUser(studentToDelete);
		}
		if (instructorCanvas != null) {
			CanvasUserRS instructorToDelete = new CanvasUserRS();
			instructorToDelete.setUser(instructorCanvas);
			canvasApiUtils.deleteUser(instructorToDelete);
		}
	}

}
