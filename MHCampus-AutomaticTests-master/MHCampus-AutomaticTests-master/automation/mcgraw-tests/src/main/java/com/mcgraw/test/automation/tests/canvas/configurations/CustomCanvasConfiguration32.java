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
* Course ID – User ID Mapping: Internal – Internal
* Gradebook Connector: No
* SSO Connector: No 
* Canvas Mapping: Yes 
* Instructor Level Token: Off
* Use Existing Assignments: Off
* Fallback to user_id and context_id: Yes
* Async:  Off
*/
public class CustomCanvasConfiguration32 extends BaseTest {
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
	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;
	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String courseName = "CourseName1" + courseRandom;
	private String password = "123qweA@";

	private CanvasUser student;
	private CanvasUser instructor;
	private CanvasCourseRS course;
	private CanvasUserEnrollmentRS studentEnrollment11;
	private CanvasUserEnrollmentRS instructorEnrollment11;

	

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

	private String customerNumber = "3HV0-AU3A-MF3J";
	private String sharedSecret = "F8290D";

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
		Logger.info("Course ID – User ID Mapping:  Internal – Internal  | Gradebook Connector: No | SSO Connector: No | Canvas Mapping: Yes ");
		Logger.info("* Instructor Level Token: Off | Use Existing Assignments: Off | Fallback to user_id and context_id: On | Async: Off");
		
		
		prepareTestDataInCanvas();
		
		

		// Deep
		testRestApi = restApplication.fillUnencryptedIdAndPressEncrypt(Integer.toString(course.getId()));
		canvasCourseI2dencrypted = testRestApi.getResult();
		testRestApi = restApplication.fillUnencryptedIdAndPressEncrypt((student.getLoginId()));
		canvasStudent2Idencrypted = testRestApi.getResult();

		xmlFileConfiguration = getFile();
		canvasHomeScreen = canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin,canvasApplication.canvasAdminPassword);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseName);
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
			canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin, password);
			canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
			Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),
					"link doesn't present");
			canvasConnectCreateAccountScreen = canvasCourseDetailsScreen.ClickOnToolLink(deepAppName,true);
			
			Assert.verifyEquals(canvasConnectCreateAccountScreen.getErrorMessageText(), errorMsg);
			canvasApplication.logoutFromCanvas();
		}

		@Test(description = "Check Deep integration SSO to MHC as Student ", dependsOnMethods = {
		"DeepSSOasInstructor" } )
		public void DeepSSOasStudent() throws Exception {
			canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentLogin, password);
			canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
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

			canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course, assignmentTitle);
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
			List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS,student);
			Assert.assertEquals(canvasSubmissions.size(), 1, "Submission does not match");
		}

	// additional
	// methods--------------------------------------------------------------------------------------------------------
	private void prepareTestDataInCanvas() throws Exception {
		
		student = canvasApiUtils.createUser(studentLogin, password, studentName);
		instructor = canvasApiUtils.createUser(instructorLogin, password, instructorName);
		
		course = canvasApiUtils.createPublishedCourse(courseName);
	
		studentEnrollment11 = canvasApiUtils.enrollToCourseAsActiveStudent(student, course);
		instructorEnrollment11 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor, course);

		
		


	}

	private void clearCanvasData() throws Exception {

		if ((studentEnrollment11 != null) & (course != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment11, course);
		

		if ((instructorEnrollment11 != null) & (course != null))
			canvasApiUtils.deleteEnrollment(instructorEnrollment11, course);
	

		if (course != null)
			canvasApiUtils.deleteCourse(course);
	
		if (student != null) {
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(student);
			canvasApiUtils.deleteUser(studentToDelete);
		}

		
		if (instructor != null) {
			CanvasUserRS teacherToDelete = new CanvasUserRS();
			teacherToDelete.setUser(instructor);
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
