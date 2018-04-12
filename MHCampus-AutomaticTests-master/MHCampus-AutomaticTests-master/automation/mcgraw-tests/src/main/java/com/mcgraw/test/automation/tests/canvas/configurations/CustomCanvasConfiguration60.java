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
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen.canvasMappingType;
import com.mcgraw.test.automation.ui.canvas.CanvasHomeScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;


/**
* LMS = Canvas


* DI: Disabled
* Course ID – User ID Mapping: SIS – SIS
* Gradebook Connector: Yes 
* SSO Connector: Yes 
* Canvas Mapping: Yes 
* Instructor Level Token: On
* Use Existing Assignments: On
* Fallback to user_id and context_id: Off
* Async:  Off
*/
public class CustomCanvasConfiguration60 extends BaseTest {

	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	@Autowired
	private GradebookApplication gradebookApplication;

	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom = getRandomString();

	private String studentWithSisLogin = "student" + studentRandom;
	private String studentWithSisName = "StudentName" + studentRandom;
	private String stuSisId = getRandomString();
	private String instructorWithSisLogin = "instructor" + instructorRandom;
	private String instructorWithSisName = "InstructorName" + instructorRandom;
	private String insSisId = getRandomString();
	private String courseWithSisName = "CourseName" + courseRandom;
	private String courseSisId = getRandomString();
	private String password = "123qweA@";

	private CanvasUser studentWithSis;
	private CanvasCourseRS courseWithSis;
	private CanvasUser instructorWithSis;
	private CanvasUserEnrollmentRS studentWithSisEnrollment;
	private CanvasUserEnrollmentRS instructorWithSisEnrollment;
	
	private String providerId = "Connect";
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
	private String assignmentIdForExist = getRandomNumber();

	private CanvasHomeScreen canvasHomeScreen;
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;
	CanvasAssignmentRS canvasAssignmentRS;
	
	private String customerNumber = "1TID-8RI7-8NU0";
	private String sharedSecret = "BA532D";

	private String appName = "McGraw Hill Campus";
	private String moduleName = "MH Campus";


	

	

	private static final String CANVAS_FRAME = "tool_content";

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration:");
		Logger.info("LMS = Canvas | DI: Disabled ");
		Logger.info("Course ID – User ID Mapping:  SIS – SIS  | Gradebook Connector: Yes | SSO Connector: Yes | Canvas Mapping: Yes ");
		Logger.info("* Instructor Level Token: On | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Async: Off");
		
		
		prepareTestDataInCanvas();
		
		//classic
		 canvasHomeScreen =canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin,canvasApplication.canvasAdminPassword);
		 canvasCourseDetailsScreen =canvasHomeScreen.goToCreatedCourseAsAdmin(courseWithSisName);
		 canvasCourseDetailsScreen.createMhCampusApplication(appName,customerNumber, sharedSecret,canvasApplication.ltiLaunchUrl , canvasMappingType.SISTOSIS , false);
		 canvasApplication.logoutFromCanvas();
		
		 canvasHomeScreen =canvasApplication.loginToCanvasAndAcceptTerms(instructorWithSisLogin,password);
		 canvasCourseDetailsScreen =canvasHomeScreen.goToCreatedCourse(courseWithSisName);
		 canvasCourseDetailsScreen.createModuleWithApplication(moduleName,appName);
		 canvasApplication.logoutFromCanvas();
	}
	@AfterClass
	 public void testSuiteTearDown() throws Exception {
	 clearCanvasData();
	 }

	
	// Classic ------------------------------------------------------------------------------

	@Test(description = "Check SSO to MHC as Instructor " )
	public void SSOasInstructor() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorWithSisLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseWithSisName);
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForLti(true);
		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(CANVAS_FRAME),"Rules text is incorrect");
		mhCampusIntroductionScreen.acceptRules(CANVAS_FRAME);
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), instructorWithSisName.toUpperCase(),
				"User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseWithSisName, CANVAS_FRAME),
				"Course" + courseWithSisName + " is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(CANVAS_FRAME), "Search option is absent");
		canvasApplication.logoutFromCanvas();

	}

	@Test(description = "Check SSO to MHC as Student", dependsOnMethods = {
	"SSOasInstructor" })
	public void SSOasStudent() throws Exception {

		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentWithSisLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseWithSisName);
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForLti(false);
		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(CANVAS_FRAME),"Rules text is incorrect");
		mhCampusIntroductionScreen.acceptRules(CANVAS_FRAME);
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), studentWithSisName.toUpperCase(),
				"User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseWithSisName, CANVAS_FRAME),
				"Course" + courseWithSisName + " is absent");
		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(CANVAS_FRAME), "Search option is present");
		canvasApplication.logoutFromCanvas();
	}

	@Test(description = "Check TestScorableItem form is submitted successfully", dependsOnMethods = {
	"SSOasStudent" })
	public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber,
				providerId, courseWithSis.getSisCourseId(), assignmentId, assignmentTitle, category, description,
				startDate, dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(courseWithSis, assignmentTitle);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = {
			"checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId,
				courseWithSis.getSisCourseId(), assignmentId, stuSisId, commentForStudent,
				dateSubmitted, scoreReceivedForStudent, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS,
				studentWithSis);
		Assert.assertEquals(canvasSubmissions.size(), 1, "Submission does not match");
	}
	
	// Exist ------------------------------------------------------------------------------------------
	
		@Test(description = "Check TestScorableItem form is submitted successfully Exist", dependsOnMethods = {
		"checkSubmittingTestScoreFormIsSuccessfullForStudent" })
		public void checkExistSubmittingTestScorableItemFormIsSuccessfull() throws Exception {

			TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber,
					providerId, courseWithSis.getSisCourseId(), assignmentIdForExist, assignmentTitle, category, description,
					startDate, dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
					canvasApplication.tegrityServiceLocation);

			Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
			canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(courseWithSis, assignmentTitle);
			Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");
		}

		@Test(description = "Check TestScore form is submitted successfully for Exist", dependsOnMethods = {
				"checkExistSubmittingTestScorableItemFormIsSuccessfull" })
		public void checkExistSubmittingTestScoreFormIsSuccessfullForStudent() throws Exception {

			TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId,
					courseWithSis.getSisCourseId(), assignmentIdForExist, stuSisId, commentForStudent,
					dateSubmitted, scoreReceivedForStudent, canvasApplication.tegrityServiceLocation);

			Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
			List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS,
					studentWithSis);
			Assert.assertEquals(canvasSubmissions.size(), 1, "Submission does not match");
		}

// additional methods--------------------------------------------------------------------------------------------------------
		private void prepareTestDataInCanvas() throws Exception {
			
			studentWithSis = canvasApiUtils.createUserWithSis(studentWithSisLogin, password, studentWithSisName, stuSisId);
			instructorWithSis = canvasApiUtils.createUserWithSis(instructorWithSisLogin, password, instructorWithSisName, insSisId);
			courseWithSis = canvasApiUtils.createPublishedCourseWithSis(courseWithSisName, courseSisId);
			studentWithSisEnrollment = canvasApiUtils.enrollToCourseAsActiveStudent(studentWithSis, courseWithSis);
			instructorWithSisEnrollment = canvasApiUtils.enrollToCourseAsActiveTeacher(instructorWithSis, courseWithSis);	
		}
		private void clearCanvasData() throws Exception {

			if ((studentWithSisEnrollment != null) & (courseWithSis != null))
				canvasApiUtils.deleteEnrollment(studentWithSisEnrollment, courseWithSis);
			if ((instructorWithSisEnrollment != null) & (courseWithSis != null))
				canvasApiUtils.deleteEnrollment(instructorWithSisEnrollment, courseWithSis);
			if (courseWithSis != null)
				canvasApiUtils.deleteCourse(courseWithSis);
			
			if (studentWithSis != null) {
				CanvasUserRS studentToDelete = new CanvasUserRS();
				studentToDelete.setUser(studentWithSis);
				canvasApiUtils.deleteUser(studentToDelete);
			}
			if (instructorWithSis != null) {
				CanvasUserRS teacherToDelete = new CanvasUserRS();
				teacherToDelete.setUser(instructorWithSis);
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

