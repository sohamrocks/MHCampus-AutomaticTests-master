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
import com.mcgraw.test.automation.ui.applications.RestApplication;
import com.mcgraw.test.automation.ui.canvas.CanvasAssignmentDetailsScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen.canvasMappingType;
import com.mcgraw.test.automation.ui.canvas.CanvasGradebookScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasHomeScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.rest.TestRestApi;

/**
* LMS = Canvas
* DI: Disabled
* Course ID – User ID Mapping: Internal – Internal
* Gradebook Connector: Yes
* SSO Connector: Yes
* Canvas Mapping: No
* Instructor Level Token: On
* Use Existing Assignments: Off
* Fallback to user_id and context_id: Off
* Async: Off
*/

public class CustomCanvasConfiguration9 extends BaseTest {

	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	@Autowired
	private GradebookApplication gradebookApplication;

	private String studentRandom = getRandomString();
	private String studentRandom2 = getRandomString();
	private String instructorRandom = getRandomString();
	private String instructorRandom2 = getRandomString();
	private String courseRandom = getRandomString();

	private String studentLogin1 = "student" + studentRandom;
	private String studentName1 = "StudentName" + studentRandom;
	private String instructorLogin1 = "instructor" + instructorRandom;
	private String instructorName1 = "InstructorName" + instructorRandom;
	private String courseName1 = "CourseName1" + courseRandom;
	private String password = "123qweA@";

	private CanvasUser student1;
	private CanvasUser instructor1;
	private CanvasCourseRS course1;

	private CanvasUserEnrollmentRS studentEnrollment11;
	private CanvasUserEnrollmentRS instructorEnrollment11;

	private String providerId = "provider_" + getRandomString();
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

	private CanvasAssignmentDetailsScreen canvasAssignmentDetailsScreen;
	private CanvasHomeScreen canvasHomeScreen;
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;
	private CanvasGradebookScreen canvasGradebookScreen;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;
	private CanvasAssignmentRS canvasAssignmentRS;

	private String customerNumber = "2SEZ-QCDC-BHHN";
	private String sharedSecret = "D5CE8B";

	private String appName = "McGraw Hill Campus";
	private String moduleName = "MH Campus";

	private static final String CANVAS_FRAME = "tool_content";

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration:");
		Logger.info("LMS = Canvas | DI: Disabled ");
		Logger.info("Course ID – User ID Mapping:  internal – Internal  | Gradebook Connector: Yes | SSO Connector: Yes | Canvas Mapping: No ");
		Logger.info("* Instructor Level Token: On | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Async: Off");

		prepareTestDataInCanvas();

		canvasHomeScreen = canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin,
				canvasApplication.canvasAdminPassword);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseName1);
		canvasCourseDetailsScreen.createMhCampusApplication(appName, customerNumber, sharedSecret,
				canvasApplication.ltiLaunchUrl , canvasMappingType.INTERNALTOINTERNAL , false);
		canvasApplication.logoutFromCanvas();

		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin1, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		canvasCourseDetailsScreen.createModuleWithApplication(moduleName, appName);
		canvasApplication.logoutFromCanvas();
 		
	}

	 @AfterClass
	 public void testSuiteTearDown() throws Exception {
	 clearCanvasData();
	 }

	@Test(description = "Check SSO to MHC as Instructor ")
	public void SSOasInstructor() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin1, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForLti(true);
		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(CANVAS_FRAME),"Rules text is incorrect");
		mhCampusIntroductionScreen.acceptRules(CANVAS_FRAME);
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), instructorName1.toUpperCase(),
				"User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, CANVAS_FRAME),
				"Course" + courseName1 + " is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(CANVAS_FRAME), "Search option is absent");
		canvasApplication.logoutFromCanvas();

	}

	@Test(description = "Check SSO to MHC as Student", dependsOnMethods = {"SSOasInstructor" })
	public void SSOasStudent() throws Exception {

		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentLogin1, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForLti(false);
		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(CANVAS_FRAME),"Rules text is incorrect");
		mhCampusIntroductionScreen.acceptRules(CANVAS_FRAME);
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), studentName1.toUpperCase(),
				"User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, CANVAS_FRAME),
				"Course" + courseName1 + " is absent");
		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(CANVAS_FRAME), "Search option is present");
		canvasApplication.logoutFromCanvas();
	}

	@Test(description = "Check TestScorableItem form is submitted successfully", dependsOnMethods = {"SSOasStudent" })
	public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber,
				providerId, Integer.toString(course1.getId()), assignmentId, assignmentTitle, category, description,
				startDate, dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course1, assignmentTitle);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = {
			"checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId,
				Integer.toString(course1.getId()), assignmentId, Integer.toString(student1.getId()), commentForStudent,
				dateSubmitted, scoreReceivedForStudent, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS,
				student1);
		Assert.assertEquals(canvasSubmissions.size(), 1, "Submission does not match");
	}
	
	
	

	private void prepareTestDataInCanvas() throws Exception {
		student1 = canvasApiUtils.createUser(studentLogin1, password, studentName1);
		instructor1 = canvasApiUtils.createUser(instructorLogin1, password, instructorName1);
		course1 = canvasApiUtils.createPublishedCourse(courseName1);
		
		studentEnrollment11 = canvasApiUtils.enrollToCourseAsActiveStudent(student1, course1);
		instructorEnrollment11 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor1, course1);


	}

	 private void clearCanvasData() throws Exception {
	
	 if((studentEnrollment11 != null) & (course1 != null))
	 canvasApiUtils.deleteEnrollment(studentEnrollment11, course1);
	 
	
	 if((instructorEnrollment11 != null) & (course1 != null))
	 canvasApiUtils.deleteEnrollment(instructorEnrollment11, course1);
	
	
	 if(course1 != null)
	 canvasApiUtils.deleteCourse(course1);
	 
	 if(student1 != null) {
	 CanvasUserRS studentToDelete = new CanvasUserRS();
	 studentToDelete.setUser(student1);
	 canvasApiUtils.deleteUser(studentToDelete);
	 }
	
	 
	 if(instructor1 != null) {
	 CanvasUserRS teacherToDelete = new CanvasUserRS();
	 teacherToDelete.setUser(instructor1);
	 canvasApiUtils.deleteUser(teacherToDelete);
	 }
	 
	 }
	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(6);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}

}
