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
import com.mcgraw.test.automation.ui.canvas.CanvasAssignmentDetailsScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasGradebookScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasHomeScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectCreateAccountScreen;
import com.mcgraw.test.automation.ui.rest.TestRestApi;
/**
* LMS = Canvas
* DI: Enabled
* Course ID – User ID Mapping: SIS – Internal
* Gradebook Connector: Yes 
* SSO Connector: No 
* Canvas Mapping: No 
* Instructor Level Token: Off
* Use Existing Assignments: Off
* Fallback to user_id and context_id: Off
* Async:  On
*/
public class CustomCanvasConfiguration43 extends BaseTest {
	
	
	@Autowired
	private RestApplication restApplication;

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
	private String courseRandom2 = getRandomString();

	private String studentWithSisLogin = "student" + studentRandom;
	private String studentWithSisName = "StudentName" + studentRandom;
	private String stuSisId = getRandomString();
	private String instructorWithSisLogin = "instructor" + instructorRandom;
	private String instructorWithSisName = "InstructorName" + instructorRandom;
	private String insSisId = getRandomString();
	private String courseWithSisName = "CourseName" + courseRandom;
	private String courseSisId = getRandomString();

	private String studentWithSisLogin2 = "student" + studentRandom2;
	private String studentWithSisName2 = "StudentName" + studentRandom2;
	private String stuSisId2 = getRandomString();
	private String instructorWithSisLogin2 = "instructor" + instructorRandom2;
	private String instructorWithSisName2 = "InstructorName" + instructorRandom2;
	private String insSisId2 = getRandomString();
	private String courseWithSisName2 = "CourseName" + courseRandom2;
	private String courseSisId2 = getRandomString();
	private String password = "123qweA@";

	private CanvasUser studentWithSis;
	private CanvasCourseRS courseWithSis;
	private CanvasUser instructorWithSis;
	private CanvasUserEnrollmentRS studentWithSisEnrollment;
	private CanvasUserEnrollmentRS instructorWithSisEnrollment;

	private CanvasUser studentWithSis2;
	private CanvasCourseRS courseWithSis2;
	private CanvasUser instructorWithSis2;
	private CanvasUserEnrollmentRS studentWithSisEnrollment2;
	private CanvasUserEnrollmentRS instructorWithSisEnrollment2;

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

	private CanvasAssignmentDetailsScreen canvasAssignmentDetailsScreen;
	private CanvasHomeScreen canvasHomeScreen;
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;
	private CanvasGradebookScreen canvasGradebookScreen;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;
	private TestRestApi testRestApi;
	CanvasAssignmentRS canvasAssignmentRS;
	private CanvasConnectCreateAccountScreen canvasConnectCreateAccountScreen;

	private String customerNumber = "22WU-JCUW-UFDM";
	private String sharedSecret = "20A4E3";

	private String appName = "McGraw Hill Campus";
	private String deepAppName = "AConnectDI";
	private String moduleName = "MH Campus";

	private String errorMsg = "Error: LTI_REQ_004 - Configuration setup is not validated";

	private String canvasCourseI2dencrypted;
	private String canvasStudent2Idencrypted;
	
	private String canvasAdminLogin = "automationadmin3";
	private String canvasAdminPassword = "123456789";
	
	
	private static final String CANVAS_FRAME = "tool_content";

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration:43");
		Logger.info("LMS = Canvas | DI: Enabled ");
		Logger.info(
				"Course ID – User ID Mapping:  SIS – Internal  | Gradebook Connector: Yes | SSO Connector: No | Canvas Mapping: No ");
		Logger.info(
				"* Instructor Level Token: Off | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Async: On");

		prepareTestDataInCanvas();

		// classic
		canvasHomeScreen = canvasApplication.loginToCanvas(canvasAdminLogin,
				canvasAdminPassword);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseWithSisName);
		canvasCourseDetailsScreen.createMhCampusApplication(appName, customerNumber, sharedSecret,
				canvasApplication.ltiLaunchUrl);
		canvasApplication.logoutFromCanvas();

		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorWithSisLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseWithSisName);
		canvasCourseDetailsScreen.createModuleWithApplication(moduleName, appName);
		canvasApplication.logoutFromCanvas();

		// Deep
		testRestApi = restApplication.fillUnencryptedIdAndPressEncrypt(Integer.toString(courseWithSis2.getId()));
		canvasCourseI2dencrypted = testRestApi.getResult();
		testRestApi = restApplication.fillUnencryptedIdAndPressEncrypt(studentWithSis2.getLoginId());
		canvasStudent2Idencrypted = testRestApi.getResult();

		xmlFileConfiguration = getFile();
		canvasHomeScreen = canvasApplication.loginToCanvas(canvasAdminLogin,
				canvasAdminPassword);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseWithSisName2);
		canvasCourseDetailsScreen.createApplicationLink(customerNumber, sharedSecret, xmlFileConfiguration,
				deepAppName);
	}

	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearCanvasData();
	}

	// GB
	// (SIS-Internal)------------------------------------------------------------------------------

	@Test(description = "Check SSO to MHC as Instructor ")
	public void SSOasInstructor() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorWithSisLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseWithSisName);
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForLti(false);
		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(CANVAS_FRAME), "Rules text is incorrect");
		mhCampusIntroductionScreen.acceptRules(CANVAS_FRAME);
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME),
				instructorWithSisName.toUpperCase(), "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseWithSisName, CANVAS_FRAME),
				"Course" + courseWithSisName + " is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(CANVAS_FRAME), "Search option is absent");
		canvasApplication.logoutFromCanvas();

	}

	@Test(description = "Check SSO to MHC as Student", dependsOnMethods = { "SSOasInstructor" })
	public void SSOasStudent() throws Exception {

		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentWithSisLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseWithSisName);
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForLti(false);
		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(CANVAS_FRAME), "Rules text is incorrect");
		mhCampusIntroductionScreen.acceptRules(CANVAS_FRAME);
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), studentWithSisName.toUpperCase(),
				"User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseWithSisName, CANVAS_FRAME),
				"Course" + courseWithSisName + " is absent");
		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(CANVAS_FRAME), "Search option is present");
		canvasApplication.logoutFromCanvas();
	}

	@Test(description = "Check TestScorableItem form is submitted successfully", dependsOnMethods = { "SSOasStudent" })
	public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemFormAsync(customerNumber,
				providerId, Integer.toString(courseWithSis.getId()), assignmentId, assignmentTitle, category, description,
				startDate, dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				canvasApplication.tegrityServiceLocation);

		browser.pause(4000);
		Assert.assertTrue(testScoreItemsScreen.isResultContainsRequest(),
				"TestScoreItems form submit was not successful, invalid requset created");
		gradebookApplication.getAsyncInScorableResult();
		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvasAsync(), "getAsync failed for TestScoreItems");
		browser.pause(4000);
		
		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvasAsync(), "getAsync failed for TestScoreItems");
		
		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(courseWithSis, assignmentTitle);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = {
			"checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreFormAsync(customerNumber, providerId,
				Integer.toString(courseWithSis.getId()), assignmentId, stuSisId, commentForStudent, dateSubmitted,
				scoreReceivedForStudent, canvasApplication.tegrityServiceLocation);
		browser.pause(4000);
		Assert.assertTrue(testScoreScreen.isResultContainsRequest(),
				"TestScoreItems form submit was not successful, invalid requset created");
		gradebookApplication.getAsyncInScoreResult();
		browser.pause(4000);
		
		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvasAsync(), "getAsync failed for TestScore");
		
		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS,
				studentWithSis);
		Assert.assertEquals(canvasSubmissions.size(), 1, "Submission does not match");
	}

	// Deep
	// integration------------------------------------------------------------------------------

	@Test(description = "Check Deep integration SSO to MHC as Instructor ",dependsOnMethods = {
	"checkSubmittingTestScoreFormIsSuccessfullForStudent" })
	public void DeepSSOasInstructor() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorWithSisLogin2, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseWithSisName2);
		Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),
				"link doesn't present");
		canvasConnectCreateAccountScreen = canvasCourseDetailsScreen.ClickOnToolLink(deepAppName, true);

		Assert.verifyEquals(canvasConnectCreateAccountScreen.getErrorMessageText(), errorMsg);
		canvasApplication.logoutFromCanvas();
	}

	@Test(description = "Check Deep integration SSO to MHC as Student ", dependsOnMethods = { "DeepSSOasInstructor" })
	public void DeepSSOasStudent() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentWithSisLogin2, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseWithSisName2);
		Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),
				"link doesn't present");
		canvasConnectCreateAccountScreen = canvasCourseDetailsScreen.ClickOnToolLink(deepAppName, false);
		Assert.verifyEquals(canvasConnectCreateAccountScreen.getErrorMessageText(), errorMsg);
		canvasApplication.logoutFromCanvas();
	}

	@Test(description = "Check TestScorableItem form is submitted successfully in Deep", dependsOnMethods = {
			"DeepSSOasStudent" })
	public void checkDeepSubmittingTestScorableItemFormIsSuccessfull() throws Exception {
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemFormDIAsync(customerNumber,
				providerId, canvasCourseI2dencrypted, assignmentId, assignmentTitle, category, description, startDate,
				dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				canvasApplication.tegrityServiceLocation, tool_ID);
		
		// there must be missing methods "get module id" and "fill in module id"
		Assert.assertTrue(testScoreItemsScreen.isResultContainsRequest(),
				"TestScoreItems form submit was not successful, invalid requset created");
		browser.pause(4000);
		gradebookApplication.getAsyncInScorableResult();

		browser.pause(4000);
		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(courseWithSis2, assignmentTitle);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");
	}

	@Test(description = "Check TestScore form is submitted successfully in Deep", dependsOnMethods = {
			"checkDeepSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkDeppSubmittingTestScoreFormIsSuccessfullForStudent() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreFormDIAsync(customerNumber, providerId,
				canvasCourseI2dencrypted, assignmentId, canvasStudent2Idencrypted, commentForStudent, dateSubmitted,
				scoreReceivedForStudent, canvasApplication.tegrityServiceLocation, tool_ID);
		// there must be missing methods "get module id" and "fill in module id"
		browser.pause(4000);
		Assert.assertTrue(testScoreScreen.isResultContainsRequest(),
				"TestScoreItems form submit was not successful, invalid requset created");
		gradebookApplication.getAsyncInScoreResult();
		browser.pause(4000);
		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS,
				studentWithSis2);
		Assert.assertEquals(canvasSubmissions.size(), 1, "Submission does not match");
	}

	// additional
	// methods--------------------------------------------------------------------------------------------------------
	private void prepareTestDataInCanvas() throws Exception {

		studentWithSis = canvasApiUtils.createUserWithSis(studentWithSisLogin, password, studentWithSisName, stuSisId);
		instructorWithSis = canvasApiUtils.createUserWithSis(instructorWithSisLogin, password, instructorWithSisName,
				insSisId);
		courseWithSis = canvasApiUtils.createPublishedCourse(courseWithSisName);
		studentWithSisEnrollment = canvasApiUtils.enrollToCourseAsActiveStudent(studentWithSis, courseWithSis);
		instructorWithSisEnrollment = canvasApiUtils.enrollToCourseAsActiveTeacher(instructorWithSis, courseWithSis);

		studentWithSis2 = canvasApiUtils.createUserWithSis(studentWithSisLogin2, password, studentWithSisName2,
				stuSisId2);
		instructorWithSis2 = canvasApiUtils.createUserWithSis(instructorWithSisLogin2, password, instructorWithSisName2,
				insSisId2);
		courseWithSis2 = canvasApiUtils.createPublishedCourse(courseWithSisName2);
		studentWithSisEnrollment2 = canvasApiUtils.enrollToCourseAsActiveStudent(studentWithSis2, courseWithSis2);
		instructorWithSisEnrollment2 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructorWithSis2, courseWithSis2);

	}

	private void clearCanvasData() throws Exception {

		if ((studentWithSisEnrollment != null) & (courseWithSis != null))
			canvasApiUtils.deleteEnrollment(studentWithSisEnrollment, courseWithSis);
		if ((studentWithSisEnrollment2 != null) & (courseWithSis2 != null))
			canvasApiUtils.deleteEnrollment(studentWithSisEnrollment2, courseWithSis2);

		if ((instructorWithSisEnrollment != null) & (courseWithSis != null))
			canvasApiUtils.deleteEnrollment(instructorWithSisEnrollment, courseWithSis);
		if ((instructorWithSisEnrollment2 != null) & (courseWithSis2 != null))
			canvasApiUtils.deleteEnrollment(instructorWithSisEnrollment2, courseWithSis2);

		if (courseWithSis != null)
			canvasApiUtils.deleteCourse(courseWithSis);
		if (courseWithSis2 != null)
			canvasApiUtils.deleteCourse(courseWithSis2);
		if (studentWithSis != null) {
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(studentWithSis);
			canvasApiUtils.deleteUser(studentToDelete);
		}

		if (studentWithSis2 != null) {
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(studentWithSis2);
			canvasApiUtils.deleteUser(studentToDelete);
		}
		if (instructorWithSis != null) {
			CanvasUserRS teacherToDelete = new CanvasUserRS();
			teacherToDelete.setUser(instructorWithSis);
			canvasApiUtils.deleteUser(teacherToDelete);
		}
		if (instructorWithSis2 != null) {
			CanvasUserRS teacherToDelete = new CanvasUserRS();
			teacherToDelete.setUser(instructorWithSis2);
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

