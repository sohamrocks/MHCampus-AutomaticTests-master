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
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen.canvasMappingType;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectCreateAccountScreen;
import com.mcgraw.test.automation.ui.rest.TestRestApi;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasSubmissionRS;

import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
/**
* LMS = Canvas
* DI: Enabled
* Course ID – User ID Mapping: SIS – SIS
* Gradebook Connector: Yes
* SSO Connector: Off
* Canvas Mapping: Yes
* Instructor Level Token: Off
* Use Existing Assignments: On
* Fallback to user_id and context_id: On
* Async: Off
*/
public class CustomCanvasConfiguration38 extends BaseTest{
	
	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	RestApplication restApp;

	private String studentRandom1 = getRandomString();
	private String stuSisId1 = getRandomString();
	private String instructorRandom1 = getRandomString();
	private String insSisId1 = getRandomString();

	private String studentRandom2 = getRandomString();
	private String stuSisId2 = getRandomString();
	private String instructorRandom2 = getRandomString();
	private String insSisId2 = getRandomString();

	private String courseRandom1 = getRandomString();
	private String courseSisId1 = getRandomString();
	private String courseRandom2 = getRandomString();
	private String courseSisId2 = getRandomString();

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

	private CanvasHomeScreen canvasHomeScreen;
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;


	private String moduleName = "MH Campus";
	private String appName = "McGraw Hill Campus";

	private String providerId = "provider_" + getRandomString();
	private String assignmentId = getRandomString();
	private String assignmentIdExist = getRandomString();
	private String assignmentTitle = "title_" + getRandomString();
	private String category = "Test";
	private String description = "description_" + getRandomString();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String scoreType = "Percentage";
	private String scorePossible = "100";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;
	private String commentForStudent1 = "comment_" + getRandomString();
	private String commentForStudent2 = "comment_2" + getRandomString();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent1 = GradebookApplication.getRandomScore();
	private String scoreReceivedForStudent2 = GradebookApplication.getRandomScore();

	private String customerNumber = "3VN0-RRIC-UD9J";
	private String sharedSecret = "2B6B79";
	private CanvasAssignmentRS canvasAssignmentRS;

	private TestRestApi testRestApp;

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
	
	private String commentForStudent = "comment_" + getRandomString();
	private String scoreReceivedForStudent = GradebookApplication.getRandomScore();

	private String errorMsg = "Error: LTI_REQ_004 - Configuration setup is not validated";
	private String tool_ID = "Connect";
	
	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration: CustomCanvasConfiguration38");
		Logger.info("LMS = Canvas | DI: Enabled ");
		Logger.info("Course ID – User ID Mapping: SIS – SIS | Gradebook Connector: Yes | SSO Connector: No  | Canvas Mapping: Yes ");
		Logger.info("* Instructor Level Token: Off | Use Existing Assignments: On | Fallback to user_id and context_id: On | Async: Off");
		
		prepareTestDataInCanvas();

		canvasHomeScreen = canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin,
				canvasApplication.canvasAdminPassword);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseName1);
		canvasCourseDetailsScreen.createMhCampusApplication(appName, customerNumber, sharedSecret, canvasApplication.ltiLaunchUrl, canvasMappingType.SISTOSIS, true);
		canvasApplication.logoutFromCanvas();

		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin1, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		canvasCourseDetailsScreen.createModuleWithApplication(moduleName, appName);
		canvasApplication.logoutFromCanvas();

		// DI
		testRestApp = restApp.fillUnencryptedIdAndPressEncrypt(student2.getLoginId());
		studentEncrypted = testRestApp.getResult();

		testRestApp = restApp.fillUnencryptedIdAndPressEncrypt(courseSisId2);
		courseEncrypted = testRestApp.getResult();

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
	
	//GB(SIS-SIS)

		@Test(description = "Check SSO to MHC as Instructor")
		public void SSOasInstructor() throws Exception {
			
			canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin1, password);
			canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
			mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForLti(false);
			
			Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(CANVAS_FRAME),"Rules text is incorrect");
			
			mhCampusIntroductionScreen.acceptRules(CANVAS_FRAME);
			
			Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), instructorName1.toUpperCase(),
					"User name is incorrect");
			Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, CANVAS_FRAME),
					"Course" + courseName1 + " is absent");
			Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(CANVAS_FRAME), "Search option is absent");
			canvasApplication.logoutFromCanvas();
		}
		
		
		@Test(description = "Check SSO to MHC as Student", dependsOnMethods = { "SSOasInstructor" })
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
	
	
	@Test(description = "Test scorableitem form success submit", dependsOnMethods="SSOasStudent")
	public void testScorableItemSuccessSubmit() throws Exception {
		
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber,
				providerId, course1.getSisCourseId(), assignmentId, assignmentTitle, category, description,
				startDate, dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScoreItems form submit failed");
		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course1, assignmentTitle);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");

	}

	@Test(description = "Test score form success submit", dependsOnMethods ="testScorableItemSuccessSubmit")
	public void testScoreSuccessSubmit() throws Exception {
		
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId,
				course1.getSisCourseId(), assignmentId, stuSisId1, commentForStudent1,
				dateSubmitted, scoreReceivedForStudent1, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
		Assert.assertTrue(canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS, student1).size() == 1);

	}
	//Exist
	
	@Test(description = "Test scorableitem form success submit", dependsOnMethods="testScoreSuccessSubmit")
	public void testScorableItemSuccessSubmitExist() throws Exception {
		
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber,
				providerId, course1.getSisCourseId(), assignmentIdExist, assignmentTitle, category, description,
				startDate, dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScoreItems form submit failed");
		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course1, assignmentTitle);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");

	}

	@Test(description = "Test score form success submit", dependsOnMethods ="testScorableItemSuccessSubmitExist")
	public void testScoreSuccessSubmitExist() throws Exception {
		
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId,
				course1.getSisCourseId(), assignmentIdExist, stuSisId1, commentForStudent1,
				dateSubmitted, scoreReceivedForStudent1, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
		Assert.assertTrue(canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS, student1).size() == 1);

	}
	
	
	
	//DI tests
	@Test(description = "Test SSO to MHCampus as instructor DI" , dependsOnMethods ="testScoreSuccessSubmitExist")
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

	@Test(description = "Test SSO to MHCampus as student DI" , dependsOnMethods ="testSSOasInstructorDI")
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

		student1 = canvasApiUtils.createUserWithSis(studentLogin1, password, studentName1, stuSisId1);
		instructor1 = canvasApiUtils.createUserWithSis(instructorLogin1, password, instructorName1, insSisId1);

		student2 = canvasApiUtils.createUserWithSis(studentLogin2, password, studentName2, stuSisId2);
		instructor2 = canvasApiUtils.createUserWithSis(instructorLogin2, password, instructorName2, insSisId2);

		course1 = canvasApiUtils.createPublishedCourseWithSis(courseName1, courseSisId1);
		course2 = canvasApiUtils.createPublishedCourseWithSis(courseName2, courseSisId2);

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

