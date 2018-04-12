package com.mcgraw.test.automation.tests.canvas.configurations;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasHomeScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectCreateAccountScreen;
import com.mcgraw.test.automation.ui.rest.TestRestApi;
/**
* LMS = Canvas
* DI: Enabled
* Course ID â€“ User ID Mapping: SIS â€“ Login
* Gradebook Connector: No
* SSO Connector: No 
* Canvas Mapping: Yes 
* Instructor Level Token: Off
* Use Existing Assignments: Off
* Fallback to user_id and context_id: No
* Async:  Off
*/
public class CustomCanvasConfiguration64 extends BaseTest {
	@Autowired
	private RestApplication restApplication;

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
	private String xmlFileConfiguration;
	private String fullPathToFile;
	private String fileName = "QA_DI_Cartridge.xml";
	private String tool_ID = "Connect";

	
	private CanvasHomeScreen canvasHomeScreen;
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;
	private TestRestApi testRestApi;
	CanvasAssignmentRS canvasAssignmentRS;
	private CanvasConnectCreateAccountScreen canvasConnectCreateAccountScreen;

	private String customerNumber = "2DT6-EG59-K7HY";
	private String sharedSecret = "6A193E";

	private String appName = "McGraw Hill Campus";
	private String deepAppName = "AConnectDI";
	private String moduleName = "MH Campus";

	private String errorMsg = "Error: LTI_REQ_004 - Configuration setup is not validated";
	
	private String canvasCourseI2dencrypted;
	private String canvasStudent2Idencrypted;
	

	private static final String CANVAS_FRAME = "tool_content";

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration:");
		Logger.info("LMS = Canvas | DI: Enabled ");
		Logger.info("Course ID â€“ User ID Mapping:  SIS â€“ Login  | Gradebook Connector: No | SSO Connector: No | Canvas Mapping: Yes ");
		Logger.info("* Instructor Level Token: Off | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Async: Off");
		
		
		prepareTestDataInCanvas();
		
		

		// Deep
		testRestApi = restApplication.fillUnencryptedIdAndPressEncrypt(courseSisId);
		canvasCourseI2dencrypted = testRestApi.getResult();
		testRestApi = restApplication.fillUnencryptedIdAndPressEncrypt((String.valueOf(studentWithSis.getId())));
		canvasStudent2Idencrypted = testRestApi.getResult();

		xmlFileConfiguration = getFile();
		canvasHomeScreen = canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin,canvasApplication.canvasAdminPassword);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseWithSisName);
		canvasCourseDetailsScreen.createApplicationLink(customerNumber, sharedSecret, xmlFileConfiguration,deepAppName);
	}

	 @AfterClass
	 public void testSuiteTearDown() throws Exception {
	 clearCanvasData();
	 }

	
	
		// Deep
		// integration------------------------------------------------------------------------------

		@Test(description = "Check Deep integration SSO to MHC as Instructor ")
		public void DeepSSOasInstructor() throws Exception {
			canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorWithSisLogin, password);
			canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseWithSisName);
			Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),
					"link doesn't present");
			canvasConnectCreateAccountScreen = canvasCourseDetailsScreen.ClickOnToolLink(deepAppName,true);
			
			Assert.verifyEquals(canvasConnectCreateAccountScreen.getErrorMessageText(), errorMsg);
			canvasApplication.logoutFromCanvas();
		}

		@Test(description = "Check Deep integration SSO to MHC as Student ", dependsOnMethods = {
		"DeepSSOasInstructor" } )
		public void DeepSSOasStudent() throws Exception {
			canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentWithSisLogin, password);
			canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseWithSisName);
			Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),
					"link doesn't present");
			canvasConnectCreateAccountScreen = canvasCourseDetailsScreen.ClickOnToolLink(deepAppName,false);
			Assert.verifyEquals(canvasConnectCreateAccountScreen.getErrorMessageText(), errorMsg);
			canvasApplication.logoutFromCanvas();
		}

		@Test(description = "Check TestScorableItem form is submitted successfully in Deep",dependsOnMethods = {
		"DeepSSOasStudent" } )
		public void checkDeepSubmittingTestScorableItemFormIsSuccessfull() throws Exception {
			TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemFormDI(customerNumber,
					providerId, canvasCourseI2dencrypted, assignmentId, assignmentTitle, category, description, startDate,
					dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
					canvasApplication.tegrityServiceLocation, tool_ID);
			// there must be missing methods "get module id" and "fill in module id"
			
			
			Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvasDI(), "TestScore form submit failed");

			canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(courseWithSis, assignmentTitle);
			Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");
		}

		@Test(description = "Check TestScore form is submitted successfully in Deep", dependsOnMethods = {
		"checkDeepSubmittingTestScorableItemFormIsSuccessfull" })
		public void checkDeppSubmittingTestScoreFormIsSuccessfullForStudent() throws Exception {

			TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreFormDI(customerNumber, providerId,
					canvasCourseI2dencrypted, assignmentId, canvasStudent2Idencrypted, commentForStudent, dateSubmitted,
					scoreReceivedForStudent, canvasApplication.tegrityServiceLocation, tool_ID);
			// there must be missing methods "get module id" and "fill in module id"
			
			
			Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvasDI(), "TestScore form submit failed");
			List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS,studentWithSis);
			Assert.assertEquals(canvasSubmissions.size(), 1, "Submission does not match");
		}

	// additional
	// methods--------------------------------------------------------------------------------------------------------
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

	private String getFile() throws Exception {

		Logger.info("Get file from resources folder...");
		fullPathToFile = canvasApplication.pathToFile + fileName;
		File file = new File(fullPathToFile);
		byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));

		return new String(encoded, "UTF-8");
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(6);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}
}

