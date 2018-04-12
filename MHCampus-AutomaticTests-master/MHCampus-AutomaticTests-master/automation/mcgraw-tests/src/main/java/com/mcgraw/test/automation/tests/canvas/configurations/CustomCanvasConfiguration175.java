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
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewSite;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewUser;
import com.mcgraw.test.automation.api.sakai.service.ISakaiApiService;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.CanvasApplication;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.applications.RestApplication;
import com.mcgraw.test.automation.ui.applications.SakaiApplication;
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasHomeScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectCreateAccountScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiAdminHomePage;
import com.mcgraw.test.automation.ui.sakai.SakaiCourseDetailsScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiExternalToolsScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiHomeScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiSetupExternalToolsScreen;
/**
* LMS = Canvas 
* DI: Enabled
* Course ID – User ID Mapping:  Internal – Internal 
* Gradebook Connector: Yes 
* SSO Connector: Off 
* Canvas Mapping: On 
* Instructor Level Token: Off 
* Use Existing Assignments: Off
* Fallback to user_id and context_id: Off	
* Async: Off
*/
public class CustomCanvasConfiguration175 extends BaseTest {

	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	private RestApplication restApp;

	private String password = "123qweA@";

	// for Deep integration
	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom = getRandomString();

	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	
	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;
	
	private String courseName = "CourseName" + courseRandom;

	private String encryptedCourseId;
	private String encryptedStudentId;

	private String xmlFileConfiguration;
	private String fullPathToFile;
	private String fileName = "QA_DI_Cartridge.xml";

	// Canvas user Data
	private CanvasUser studentCanvas;
	private CanvasUser instructorCanvas;
	private CanvasCourseRS courseCanvas;
	private CanvasUserEnrollmentRS studentEnrollmentCanvas;
	private CanvasUserEnrollmentRS instructorEnrollmentCanvas;

	// data for creating assignment
	private String providerId = "Connect";
	private String assignmentId = getRandomNumber();
	private String assignmentTitle = "title_" + getRandomString();
	private String category = "Test";
	private String description = "description_" + getRandomString();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String scoreType = "Percentage";
	private String scorePossible = "100";
	//private String categoryType = "Blog";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;
	private String commentForStudent = "comment_2" + getRandomString();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent = GradebookApplication.getRandomScore();

	private CanvasAssignmentRS canvasAssignmentRS;

	// screen objects
	private CanvasHomeScreen canvasHomeScreen;
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;
	private CanvasConnectCreateAccountScreen canvasConnectCreateAccountScreen;

	MhCampusIntroductionScreen mhCampusIntroductionScreen;
	MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	SakaiHomeScreen sakaiHomeScreen;
	SakaiCourseDetailsScreen sakaiCourseDetailsScreen;
	SakaiAdminHomePage sakaiAdminHomePage;
	SakaiExternalToolsScreen sakaiExternalToolsScreen;
	SakaiSetupExternalToolsScreen sakaiSetupExternalToolsScreen;
	//private SakaiGradesScreen sakaiGradesScreen;

	// other data
	private String customerNumber = "2QO9-TI1F-EI0M";
	private String sharedSecret = "2CA221";
	// private String tegrityUsername =
	// "admin@CustomCanvasConfiguration174.mhcampus.com";
	// private String tegrityPassword = "kkyksgtp";

	private String deepAppName = "AConnectDI";
	private String tool_ID = "Connect";

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration:");
		Logger.info("LMS = Canvas | DI: Enabled ");
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

	// CANVAS Deep
	@Test(description = "Check SSO to MHC as Instructor for Canvas", enabled = true)
	public void ssoAsInstructorCanvas() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),
				"link doesn't present");

		canvasConnectCreateAccountScreen = canvasCourseDetailsScreen.ClickOnToolLink(deepAppName, true);
		String errorMsg = canvasConnectCreateAccountScreen.getErrorMessageText();
		Assert.verifyEquals(canvasConnectCreateAccountScreen.getErrorMessageText(), errorMsg);
		canvasApplication.logoutFromCanvas();
	}

	@Test(description = "Check SSO to MHC as Student for Canvas", dependsOnMethods = { "ssoAsInstructorCanvas" })
	public void ssoAsStudentCanvas() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),
				"link doesn't present");

		canvasConnectCreateAccountScreen = canvasCourseDetailsScreen.ClickOnToolLink(deepAppName, false);
		String errorMsg = canvasConnectCreateAccountScreen.getErrorMessageText();
		Assert.verifyEquals(canvasConnectCreateAccountScreen.getErrorMessageText(), errorMsg);
		canvasApplication.logoutFromCanvas();
	}

	@Test(description = "Check TestScorableItem form is submitted successfully for Canvas", dependsOnMethods = {
			"ssoAsStudentCanvas" })
	public void checkSubmittingTestScorableItemFormIsSuccessfullCanvas() throws Exception {
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemFormDI(customerNumber,
				providerId, encryptedCourseId, assignmentId, assignmentTitle, category, description, startDate,
				dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				canvasApplication.tegrityServiceLocation, tool_ID);

		// get module ID - is not implemented yet
		// Fill in module ID - is not implemented yet

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvasDI(),
				"TestScoreItems form submit failed");
		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(courseCanvas, assignmentTitle);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");
	}

	@Test(description = "Check TestScore form is submitted successfully for Canvas", dependsOnMethods = {
			"checkSubmittingTestScorableItemFormIsSuccessfullCanvas" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudentCanvas() throws Exception {
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreFormDI(customerNumber, providerId,
				encryptedCourseId, assignmentId, encryptedStudentId, commentForStudent, dateSubmitted,
				scoreReceivedForStudent, canvasApplication.tegrityServiceLocation, tool_ID);

		// get module ID - is not implemented yet
		// Fill in module ID - is not implemented yet

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvasDI(), "TestScore form submit failed");
		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS,
				studentCanvas);
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

		encryptedCourseId = restApp.fillUnencryptedIdAndPressEncrypt(Integer.toString(courseCanvas.getId()))
				.getResult();
		encryptedStudentId = restApp.fillUnencryptedIdAndPressEncrypt(studentCanvas.getLoginId()).getResult();
		xmlFileConfiguration = getFile();

		canvasHomeScreen = canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin,
				canvasApplication.canvasAdminPassword);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseName);
		canvasCourseDetailsScreen.createApplicationLink(customerNumber, sharedSecret, xmlFileConfiguration,
				deepAppName);
		canvasApplication.logoutFromCanvas();
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}

	private String getFile() throws Exception {

		Logger.info("Get file from resources folder...");
		fullPathToFile = canvasApplication.pathToFile + fileName;
		File file = new File(fullPathToFile);
		byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));

		return new String(encoded, "UTF-8");
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
