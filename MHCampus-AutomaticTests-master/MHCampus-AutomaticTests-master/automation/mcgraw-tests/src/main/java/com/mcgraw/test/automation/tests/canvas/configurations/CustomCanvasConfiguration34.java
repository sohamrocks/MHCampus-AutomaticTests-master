package com.mcgraw.test.automation.tests.canvas.configurations;





import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;

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
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.CanvasApplication;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.applications.RestApplication;
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasHomeScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen.canvasMappingType;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectCreateAccountScreen;
/**
* LMS = Canvas

* DI: Enabled
* Course ID – User ID Mapping:  SIS – SIS 
* Gradebook Connector: Yes 
* SSO Connector: On 
* Canvas Mapping: Yes 
* Instructor Level Token: On 
* Use Existing Assignments: Yes
* Fallback to user_id and context_id: Off
* Async: Off
*/
public class CustomCanvasConfiguration34 extends BaseTest {
	
	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	@Autowired
	private GradebookApplication gradebookApplication;
	
	@Autowired
	private RestApplication restApp;

	// Preparing users for test (Classic, SIS is generated randomly)
	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom = getRandomString();

	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;
	private String studentSis = getRandomString();

	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String instructorSis = getRandomString();

	private String courseName = "CourseName" + courseRandom;
	private String courseSis = getRandomString();

	private String password = "123qweA@";

	// Preparing users for test (Deep integration, SIS is generated randomly)
	private String studentRandomDi = getRandomString();
	private String instructorRandomDi = getRandomString();
	private String courseRandomDi = getRandomString();

	private String studentLoginDi = "studentDI" + studentRandomDi;
	private String studentNameDi = "StudentNameDI" + studentRandomDi;
	private String studentDiSis = getRandomString();

	private String instructorLoginDi = "instructorDI" + instructorRandomDi;
	private String instructorNameDi = "InstructorNameDI" + instructorRandomDi;
	private String instructorDiSis = getRandomString();

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
	private String assignmentIdForExist = getRandomNumber();
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

	// private String InstitutionName = "CustomCanvasConfiguration54";
	private String customerNumber = "AR0L-4O42-WTJQ";
	private String sharedSecret = "95B970";

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
				"Course ID – User ID Mapping: SIS – SIS | Gradebook Connector: Yes | SSO Connector: Yes | Canvas Mapping: Yes ");
		Logger.info(
				"* Instructor Level Token: On | Use Existing Assignments: On | Fallback to user_id and context_id: Off | Async: Off");

		prepareTestDataInCanvas();
		
		encryptedCourseId = restApp.fillUnencryptedIdAndPressEncrypt(courseDi.getSisCourseId()).getResult();
		encryptedStudentId = restApp.fillUnencryptedIdAndPressEncrypt(studentDi.getLoginId()).getResult();
		xmlFileConfiguration = getFile();

		canvasHomeScreen = canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin,
				canvasApplication.canvasAdminPassword);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseName);
		canvasCourseDetailsScreen.createMhCampusApplication(appName, customerNumber, sharedSecret,
				canvasApplication.ltiLaunchUrl , canvasMappingType.SISTOSIS , true);

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
	@Test(description = "Check SSO to MHC as Instructor with ILT ", enabled = true)
	public void ssoAsInstructorWithLti() throws Exception {

		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForLti(true);
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
				providerId, course.getSisCourseId(), assignmentId, assignmentTitle, category, description, startDate,
				dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course, assignmentTitle);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = {
			"checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId,
				course.getSisCourseId(), assignmentId, Integer.toString(student.getId()), commentForStudent,
				dateSubmitted, scoreReceivedForStudent, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS,
				student);
		Assert.assertEquals(canvasSubmissions.size(), 1,
				"Student " + studentLogin + " can see the comment and Score received of");
	}

	// EXIST CONFIGURATION
	@Test(description = "Check TestScorableItem for Exist Configuration", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent" })
	public void checkSubmittingTestScorableItemForExist() throws Exception {

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber,
				providerId, course.getSisCourseId(), assignmentIdForExist, assignmentTitle, category, description,
				startDate, dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course, assignmentTitle);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");
	}

	@Test(description = "Check TestScore for Exist Configuration", dependsOnMethods = {
			"checkSubmittingTestScorableItemForExist" })
	public void checkSubmittingTestScoreForExist() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId,
				course.getSisCourseId(), assignmentIdForExist, Integer.toString(student.getId()), commentForStudent,
				dateSubmitted, scoreReceivedForStudent, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS,
				student);
		Assert.assertEquals(canvasSubmissions.size(), 1,
				"Student " + studentLogin + " can see the comment and Score received of");
	}

	// DEEP INTEGRATION
	@Test(description = "Check SSO to MHC as Instructor with DI configuration", dependsOnMethods = {
			"checkSubmittingTestScoreForExist" })
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

	@Test(description = "Check TestScorableItem form is submitted successfully", dependsOnMethods = {
			"ssoAsStudentDI" })
	public void checkSubmittingTestScorableItemForDI() throws Exception {
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemFormDI(customerNumber,
				providerId, encryptedCourseId, assignmentIdDi, assignmentTitleDi, category, description, startDate,
				dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				canvasApplication.tegrityServiceLocation, tool_ID);

		// get module ID - is not implemented yet
		// Fill in module ID - is not implemented yet

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvasDI(),
				"TestScoreItems form submit failed");
		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(courseDi, assignmentTitleDi);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitleDi, "AssignmentTitle did not match");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = {
			"checkSubmittingTestScorableItemForDI" })
	public void checkSubmittingTestScoreForStudentDI() throws Exception {
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreFormDI(customerNumber, providerId,
				encryptedCourseId, assignmentIdDi, encryptedStudentId, commentForStudentDi, dateSubmitted,
				scoreReceivedForStudentDi, canvasApplication.tegrityServiceLocation, tool_ID);

		// get module ID - is not implemented yet
		// Fill in module ID - is not implemented yet

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvasDI(), "TestScore form submit failed");
		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS,
				studentDi);
		Assert.assertEquals(canvasSubmissions.size(), 1,
				"Student " + studentLoginDi + " can see the comment and Score received of");
	}
	// Tests end

	// private methods for class
	private String getFile() throws Exception {

		Logger.info("Get file from resources folder...");
		fullPathToFile = canvasApplication.pathToFile + fileName;
		File file = new File(fullPathToFile);
		byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));

		return new String(encoded, "UTF-8");
	}

	private void prepareTestDataInCanvas() throws Exception {
		student = canvasApiUtils.createUserWithSis(studentLogin, password, studentName, studentSis);
		instructor = canvasApiUtils.createUserWithSis(instructorLogin, password, instructorName, instructorSis);

		course = canvasApiUtils.createPublishedCourseWithSis(courseName, courseSis);

		studentEnrollment = canvasApiUtils.enrollToCourseAsActiveStudent(student, course);
		instructorEnrollment = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor, course);

		// Deep Integration users & course

		studentDi = canvasApiUtils.createUserWithSis(studentLoginDi, password, studentNameDi, studentDiSis);
		instructorDi = canvasApiUtils.createUserWithSis(instructorLoginDi, password, instructorNameDi, instructorDiSis);

		courseDi = canvasApiUtils.createPublishedCourseWithSis(courseNameDi, courseDiSis);

		studentEnrollmentDi = canvasApiUtils.enrollToCourseAsActiveStudent(studentDi, courseDi);
		instructorEnrollmentDi = canvasApiUtils.enrollToCourseAsActiveTeacher(instructorDi, courseDi);
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
