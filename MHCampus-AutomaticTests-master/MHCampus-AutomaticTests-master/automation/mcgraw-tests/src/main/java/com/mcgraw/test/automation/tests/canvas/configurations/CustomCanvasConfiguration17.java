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
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectCreateAccountScreen;
import com.mcgraw.test.automation.ui.rest.TestRestApi;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

/**
* LMS = Canvas
* DI: Enabled
* Course ID – User ID Mapping: None
* Gradebook Connector: No
* SSO Connector: No
* Canvas Mapping: No
* Instructor Level Token: Off
* Use Existing Assignments: Off
* Fallback to user_id and context_id: Off
* Async: Off
*/
public class CustomCanvasConfiguration17 extends BaseTest{
	
	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	RestApplication restApp;

	private String studentRandom1 = getRandomString();
	private String instructorRandom1 = getRandomString();

	private String studentRandom2 = getRandomString();
	private String instructorRandom2 = getRandomString();

	private String courseRandom1 = getRandomString();
	private String courseRandom2 = getRandomString();

	private String studentLogin1 = "student" + studentRandom1;
	private String studentName1 = "StudentName" + studentRandom1;

	private String studentLogin2 = "student" + studentRandom2;
	private String studentName2 = "StudentName" + studentRandom2;

	private String instructorLogin1 = "instructor" + instructorRandom1;
	private String instructorName1 = "InstructorName" + instructorRandom1;

	private String instructorLogin2 = "instructor" + instructorRandom2;
	private String instructorName2 = "InstructorName" + instructorRandom2;

	private String courseName1 = "CourseName1" + courseRandom1;
	private String courseName2 = "CourseName2" + courseRandom2;

	private String password = "123qweA@";

	private String fakeCourseId = "1234234522387";
	private String fakeCourseId2 = "1239478457564747472";
	private String fakeCourseId3 = "4783463683476373";

	private CanvasUser student1;
	private CanvasUser instructor1;
	private CanvasUser student2;
	private CanvasUser instructor2;
	private CanvasCourseRS course1;
	private CanvasCourseRS course2;

	private CanvasUserEnrollmentRS studentEnrollment1;
	private CanvasUserEnrollmentRS instructorEnrollment1;
	private CanvasUserEnrollmentRS studentEnrollment2;
	private CanvasUserEnrollmentRS instructorEnrollment2;

	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	private CanvasHomeScreen canvasHomeScreen;
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;

	private String moduleName = "MH Campus";
	private String appName = "McGraw Hill Campus";

	private String providerId = "provider_" + getRandomString();
	private String assignmentId = getRandomString();
	private String assignmentTitle = "title_" + getRandomString();
	private String category = "Test";
	private String description = "description_" + getRandomString();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String scoreType = "Percentage";
	private String scorePossible = "100";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;


	private String customerNumber = "IHDK-NQFB-U8BQ";
	private String sharedSecret = "74EC86";
	private CanvasAssignmentRS canvasAssignmentRS;

	private TestRestApi testResrtApp;

	private String courseEncrypted;
	private String studentEncrypted;
	private String instructorEncrypted;

	private MhCampusIntroductionScreen mhCampusIntroductionScreen;
	private final String CANVAS_FRAME = "tool_content";

	private String fullPathToFile;
	private String fileName = "QA_DI_Cartridge.xml";
	private String deepAppName = "AConnectDI";

	private String xmlFileConfiguration;

	private CanvasConnectCreateAccountScreen canvasConnectCreateAccountScreen;
	
	private String tool_ID = "Connect";
	
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();

	
	private String commentForStudent = "comment_" + getRandomString();
	private String scoreReceivedForStudent = GradebookApplication.getRandomScore();
	
	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration: CustomCanvasConfiguration17");
		Logger.info("LMS = Canvas | DI: Enabled ");
		Logger.info("Course ID – User ID Mapping: internal – Internal | Gradebook Connector: No | SSO Connector: No  | Canvas Mapping: No ");
		Logger.info("* Instructor Level Token: Off | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Async: Off");
		
		prepareTestDataInCanvas();

		// DI
		testResrtApp = restApp.fillUnencryptedIdAndPressEncrypt(student2.getLoginId());
		studentEncrypted = testResrtApp.getResult();
		testResrtApp = restApp.fillUnencryptedIdAndPressEncrypt(Integer.toString(instructor2.getId()));
		instructorEncrypted = testResrtApp.getResult();
		testResrtApp = restApp.fillUnencryptedIdAndPressEncrypt(Integer.toString(course2.getId()));
		courseEncrypted = testResrtApp.getResult();

		xmlFileConfiguration = getFile();

		canvasHomeScreen = canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin,
				canvasApplication.canvasAdminPassword);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseName2);
		canvasCourseDetailsScreen.createApplicationLink(customerNumber, sharedSecret, xmlFileConfiguration,
				deepAppName);
	}

	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearCanvasData();
	}
	
	//DI tests
		@Test(description = "Test SSO to MHCampus as instructor DI")
		public void testSSOasInstructorDI() throws Exception {
			
			canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin2, password);
			canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName2);
			Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),
					"link doesn't present");

			canvasConnectCreateAccountScreen = canvasCourseDetailsScreen.ClickOnToolLink(deepAppName, true);
			String errorMsg = canvasConnectCreateAccountScreen.getErrorMessageText();
			Assert.verifyEquals(canvasConnectCreateAccountScreen.getErrorMessageText(), errorMsg);
			canvasApplication.logoutFromCanvas();

		}
		@Test(description = "Test SSO to MHCampus as student DI" , dependsOnMethods = "testSSOasInstructorDI")
		public void testSSOasStudentDI() throws Exception {
			
			canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentLogin2, password);
			canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName2);
			Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),
					"link doesn't present");

			canvasConnectCreateAccountScreen = canvasCourseDetailsScreen.ClickOnToolLink(deepAppName, false);
			String errorMsg = canvasConnectCreateAccountScreen.getErrorMessageText();
			Assert.verifyEquals(canvasConnectCreateAccountScreen.getErrorMessageText(), errorMsg);
			canvasApplication.logoutFromCanvas();
		}
		
		@Test(description = "Check TestScorableItem form is submitted successfully in Deep" , dependsOnMethods ="testSSOasStudentDI")
		public void testScorableItemSuccessSubmitDI() throws Exception {
			 TestScoreItemsScreen testScoreItemsScreen =gradebookApplication.fillTestScorableItemFormDI(customerNumber, providerId,
		courseEncrypted, assignmentId, assignmentTitle, category, description, startDate, dueToDate, scoreType, scorePossible,
		isStudentViewable, isIncludedInGrade, canvasApplication.tegrityServiceLocation, tool_ID);
			// missing methods
			 Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvasDI(), "TestScore form submit failed");
			 
			canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course2, assignmentTitle);
			Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");
		}
		
		@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = { "testScorableItemSuccessSubmitDI" })
		 public void testScoreSuccessSubmitDI() throws Exception {  
		  TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreFormDI(customerNumber, providerId,
				  courseEncrypted, assignmentId, studentEncrypted, commentForStudent, dateSubmitted,
		    scoreReceivedForStudent, canvasApplication.tegrityServiceLocation, tool_ID);

		  // get module ID - is not implemented yet
		  // Fill in module ID - is not implemented yet  
		  
		  Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvasDI(), "TestScore form submit failed");
		  List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS,
		    student2);
		  Assert.assertEquals(canvasSubmissions.size(), 1,
		    "Submission does not match");
		 }
	
	
	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	private void prepareTestDataInCanvas() throws Exception {

		student1 = canvasApiUtils.createUser(studentLogin1, password, studentName1);
		instructor1 = canvasApiUtils.createUser(instructorLogin1, password, instructorName1);

		student2 = canvasApiUtils.createUser(studentLogin2, password, studentName2);
		instructor2 = canvasApiUtils.createUser(instructorLogin2, password, instructorName2);

		course1 = canvasApiUtils.createPublishedCourse(courseName1);
		course2 = canvasApiUtils.createPublishedCourse(courseName2);

		studentEnrollment1 = canvasApiUtils.enrollToCourseAsActiveStudent(student1, course1);
		instructorEnrollment1 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor1, course1);

		instructorEnrollment2 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor2, course2);
		studentEnrollment2 = canvasApiUtils.enrollToCourseAsActiveStudent(student2, course2);
	}

	private void clearCanvasData() throws Exception {

		if ((studentEnrollment1 != null) & (course1 != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment1, course1);
		if ((studentEnrollment2 != null) & (course2 != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment2, course2);

		if ((instructorEnrollment1 != null) & (course1 != null))
			canvasApiUtils.deleteEnrollment(instructorEnrollment1, course1);
		if ((instructorEnrollment2 != null) & (course2 != null))
			canvasApiUtils.deleteEnrollment(instructorEnrollment2, course2);

		if (course1 != null)
			canvasApiUtils.deleteCourse(course1);
		if (course2 != null)
			canvasApiUtils.deleteCourse(course2);
		if (student1 != null) {
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(student1);
			canvasApiUtils.deleteUser(studentToDelete);
		}

		if (student2 != null) {
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(student1);
			canvasApiUtils.deleteUser(studentToDelete);
		}
		if (instructor1 != null) {
			CanvasUserRS teacherToDelete = new CanvasUserRS();
			teacherToDelete.setUser(instructor1);
			canvasApiUtils.deleteUser(teacherToDelete);
		}
		if (instructor2 != null) {
			CanvasUserRS teacherToDelete = new CanvasUserRS();
			teacherToDelete.setUser(instructor2);
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

}
