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
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen.canvasMappingType;
import com.mcgraw.test.automation.ui.canvas.CanvasHomeScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectCreateAccountScreen;
/**
* LMS = Canvas
* DI: Enabled
* Course ID – User ID Mapping:  SIS – Login 
* Gradebook Connector: Yes 
* SSO Connector: Off 
* Canvas Mapping: No 
* Instructor Level Token: Off 
* Use Existing Assignments: Off
* Fallback to user_id and context_id: Off	
* Async: On
*/
public class CustomCanvasConfiguration46 extends BaseTest {

	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	private RestApplication restApp;

	// Preparing users for test (SIS is generated randomly)
	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom = getRandomString();

	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;

	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;

	private String courseName = "CourseName" + courseRandom;
	private String courseSis = getRandomString();

	private String password = "123qweA@";

	// Preparing users for test (Deep integration, SIS is generated randomly)
	private String studentRandomDi = getRandomString();
	private String instructorRandomDi = getRandomString();
	private String courseRandomDi = getRandomString();

	private String studentLoginDi = "studentDI" + studentRandomDi;
	private String studentNameDi = "StudentNameDI" + studentRandomDi;

	private String instructorLoginDi = "instructorDI" + instructorRandomDi;
	private String instructorNameDi = "InstructorNameDI" + instructorRandomDi;

	private String courseNameDi = "DICourseName" + courseRandomDi;
	private String courseDiSis = getRandomString();

	private boolean isLtiForTeacherDi = true;
	private boolean isLtiForStudentDi = false;

	private String encryptedCourseId;
	private String encryptedStudentId;
	private String xmlFileConfiguration;
	private String fullPathToFile;
	private String fileName = "QA_DI_Cartridge.xml";

	// Creating CanvasUsers (will be initialized in beforeClass)
	private CanvasUser student;
	private CanvasUser instructor;
	private CanvasCourseRS course;
	private CanvasUserEnrollmentRS studentEnrollment;
	private CanvasUserEnrollmentRS instructorEnrollment;

	private CanvasUser studentDi;
	private CanvasUser instructorDi;
	private CanvasCourseRS courseDi;
	private CanvasUserEnrollmentRS studentEnrollmentDi;
	private CanvasUserEnrollmentRS instructorEnrollmentDi;

	// scorableItem data
	private String providerId = "Connect";// "provider_" + getRandomString();
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

	private String assignmentIdDi = getRandomNumber();
	private String assignmentTitleDi = "title_" + getRandomString();
	private String commentForStudentDi = "commentDi_" + getRandomString();
	private String scoreReceivedForStudentDi = GradebookApplication.getRandomScore();

	private CanvasHomeScreen canvasHomeScreen;
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;
	private CanvasAssignmentRS canvasAssignmentRS;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;
	private CanvasConnectCreateAccountScreen canvasConnectCreateAccountScreen;

	// private String InstitutionName = "CustomCanvasConfiguration46";
	private String customerNumber = "37OV-UNDD-YJ9U";
	private String sharedSecret = "3AA42F";

	private String appName = "McGraw Hill Campus";
	private String moduleName = "MH Campus";

	private String deepAppName = "AConnectDI";

	private static final String CANVAS_FRAME = "tool_content";
	private String tool_ID = "Connect";

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration:");
		Logger.info("LMS = Canvas | DI: Enabled ");
		Logger.info(
				"Course ID – User ID Mapping: SIS – Login | Gradebook Connector: Yes | SSO Connector: No | Canvas Mapping: No ");
		Logger.info(
				"* Instructor Level Token: Off | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Async: On");
		
		prepareTestDataInCanvas();

		//Regular login data - uncomment for correct test running
//		canvasHomeScreen = canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin,
//				canvasApplication.canvasAdminPassword);
		
		//My login data - comment for correct test running
		canvasHomeScreen = canvasApplication.loginToCanvas("automationadmin6", "123456789");
		
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseName);
		canvasCourseDetailsScreen.createMhCampusApplication(appName, customerNumber, sharedSecret,
				canvasApplication.ltiLaunchUrl , canvasMappingType.LOGINTOSIS , true);

		browser.pause(2000);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseNameDi);
		canvasCourseDetailsScreen.createApplicationLink(customerNumber, sharedSecret, xmlFileConfiguration,
				deepAppName);
		browser.pause(2000);

		canvasApplication.logoutFromCanvas();

		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		canvasCourseDetailsScreen.createModuleWithApplication(moduleName, appName);
		canvasApplication.logoutFromCanvas();

	}

	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearCanvasData();
	}

	// Tests Start
	// CLASSIC INTEGRATION
	@Test(description = "Check SSO to MHC as Instructor", enabled = true)
	public void ssoAsInstructor() throws Exception {

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

	@Test(description = "Check SSO to MHC as Student", dependsOnMethods = { "ssoAsInstructor" })
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

	@Test(description = "Check TestScorableItem form is submitted successfully with Async", dependsOnMethods = { "ssoAsStudent" })
	public void checkSubmittingTestScorableItemFormIsSuccessfullAsync() throws Exception {

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemFormAsync(customerNumber,
				providerId, course.getSisCourseId(), assignmentId, assignmentTitle, category, description, startDate,
				dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.isResultContainsRequest(), 
				"TestScoreItems form submit was not successful, invalid requset created");
		gradebookApplication.getAsyncInScorableResult();
		browser.pause(2000);
		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvasAsync(), "getAsync failed in ScoreItems test");
		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course, assignmentTitle);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");
	}

	@Test(description = "Check TestScore form is submitted successfully with Async", dependsOnMethods = {
			"checkSubmittingTestScorableItemFormIsSuccessfullAsync" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudentAsync() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreFormAsync(customerNumber, providerId,
				course.getSisCourseId(), assignmentId, Integer.toString(student.getId()), commentForStudent,
				dateSubmitted, scoreReceivedForStudent, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.isResultContainsRequest(), 
				"TestScoreItems form submit was not successful, invalid requset created");
		gradebookApplication.getAsyncInScoreResult();
		browser.pause(2000);
		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvasAsync(), "getAsync failed in ScoreScreen test");
		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS,
				student);
		Assert.assertEquals(canvasSubmissions.size(), 1,
				"Student " + studentLogin + " can see the comment and Score received of");
	}

	// DEEP INTEGRATION
	@Test(description = "Check SSO to MHC as Instructor with DI configuration", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudentAsync" })
	public void ssoAsInstructorDI() throws Exception {

		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLoginDi, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseNameDi);

		Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),
				"link doesn't present");
		canvasConnectCreateAccountScreen = canvasCourseDetailsScreen.ClickOnToolLink(deepAppName, isLtiForTeacherDi);
		String errorMsg = canvasConnectCreateAccountScreen.getErrorMessageText();
		Assert.verifyEquals(canvasConnectCreateAccountScreen.getErrorMessageText(), errorMsg);
		canvasApplication.logoutFromCanvas();
	}

	@Test(description = "Check SSO to MHC as Student with DI configuration", dependsOnMethods = { "ssoAsInstructorDI" })
	public void ssoAsStudentDI() throws Exception {

		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentLoginDi, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseNameDi);

		Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),
				"link doesn't present");

		canvasConnectCreateAccountScreen = canvasCourseDetailsScreen.ClickOnToolLink(deepAppName, isLtiForStudentDi);
		String errorMsg = canvasConnectCreateAccountScreen.getErrorMessageText();
		Assert.verifyEquals(canvasConnectCreateAccountScreen.getErrorMessageText(), errorMsg);
		canvasApplication.logoutFromCanvas();
	}

	@Test(description = "Check TestScorableItem form is submitted successfully WIth Async", dependsOnMethods = {
			"ssoAsStudentDI" })
	public void checkSubmittingTestScorableItemForDIAsync() throws Exception {
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemFormDIAsync(customerNumber,
				providerId, encryptedCourseId, assignmentIdDi, assignmentTitleDi, category, description, startDate,
				dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				canvasApplication.tegrityServiceLocation, tool_ID);
		
		browser.pause(2000);

		// get module ID - is not implemented yet
		// Fill in module ID - is not implemented yet

		Assert.assertTrue(testScoreItemsScreen.isResultContainsRequest(), 
				 "TestScoreItems form submit failed");
		gradebookApplication.getAsyncInScorableResult();
		
		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(courseDi, assignmentTitleDi);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitleDi, "AssignmentTitle did not match");
	}

	@Test(description = "Check TestScore form is submitted successfully with Async", dependsOnMethods = {
			"checkSubmittingTestScorableItemForDIAsync" })
	public void checkSubmittingTestScoreForStudentDIAsync() throws Exception {
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreFormDIAsync(customerNumber, providerId,
				encryptedCourseId, assignmentIdDi, encryptedStudentId, commentForStudentDi, dateSubmitted,
				scoreReceivedForStudentDi, canvasApplication.tegrityServiceLocation, tool_ID);
		
		browser.pause(2000);

		// get module ID - is not implemented yet
		// Fill in module ID - is not implemented yet

		Assert.assertTrue(testScoreScreen.isResultContainsRequest(), "TestScore form submit failed");
		gradebookApplication.getAsyncInScoreResult();
		
		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS,
				studentDi);
		Assert.assertEquals(canvasSubmissions.size(), 1,
				"Student " + studentLoginDi + " can see the comment and Score received of");
	}
	// Tests End

	// private methods for class
	private String getFile() throws Exception {

		Logger.info("Get file from resources folder...");
		fullPathToFile = canvasApplication.pathToFile + fileName;
		File file = new File(fullPathToFile);
		byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));

		return new String(encoded, "UTF-8");
	}

	private void prepareTestDataInCanvas() throws Exception {
		student = canvasApiUtils.createUser(studentLogin, password, studentName);
		instructor = canvasApiUtils.createUser(instructorLogin, password, instructorName);

		course = canvasApiUtils.createPublishedCourseWithSis(courseName, courseSis);

		studentEnrollment = canvasApiUtils.enrollToCourseAsActiveStudent(student, course);
		instructorEnrollment = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor, course);

		// Deep Integration users & course

		studentDi = canvasApiUtils.createUser(studentLoginDi, password, studentNameDi);
		instructorDi = canvasApiUtils.createUser(instructorLoginDi, password, instructorNameDi);

		courseDi = canvasApiUtils.createPublishedCourseWithSis(courseNameDi, courseDiSis);

		studentEnrollmentDi = canvasApiUtils.enrollToCourseAsActiveStudent(studentDi, courseDi);
		instructorEnrollmentDi = canvasApiUtils.enrollToCourseAsActiveTeacher(instructorDi, courseDi);
		
		encryptedCourseId = restApp.fillUnencryptedIdAndPressEncrypt(courseDi.getSisCourseId()).getResult();
		encryptedStudentId = restApp.fillUnencryptedIdAndPressEncrypt(Integer.toString(studentDi.getId())).getResult();
		xmlFileConfiguration = getFile();
	}

	private void clearCanvasData() throws Exception {

		if ((studentEnrollment != null) & (course != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment, course);

		if ((studentEnrollmentDi != null) & (courseDi != null))
			canvasApiUtils.deleteEnrollment(studentEnrollmentDi, courseDi);

		if ((instructorEnrollment != null) & (course != null))
			canvasApiUtils.deleteEnrollment(instructorEnrollment, course);

		if ((instructorEnrollmentDi != null) & (courseDi != null))
			canvasApiUtils.deleteEnrollment(instructorEnrollmentDi, courseDi);

		if (course != null)
			canvasApiUtils.deleteCourse(course);

		if (courseDi != null)
			canvasApiUtils.deleteCourse(courseDi);

		if (student != null) {
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(student);
			canvasApiUtils.deleteUser(studentToDelete);
		}

		if (studentDi != null) {
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(studentDi);
			canvasApiUtils.deleteUser(studentToDelete);
		}

		if (student != null) {
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(student);
			canvasApiUtils.deleteUser(studentToDelete);
		}
		if (instructor != null) {
			CanvasUserRS instructorToDelete = new CanvasUserRS();
			instructorToDelete.setUser(instructor);
			canvasApiUtils.deleteUser(instructorToDelete);
		}

		if (instructorDi != null) {
			CanvasUserRS instructorToDelete = new CanvasUserRS();
			instructorToDelete.setUser(instructorDi);
			canvasApiUtils.deleteUser(instructorToDelete);
		}

	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(6);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}

}
