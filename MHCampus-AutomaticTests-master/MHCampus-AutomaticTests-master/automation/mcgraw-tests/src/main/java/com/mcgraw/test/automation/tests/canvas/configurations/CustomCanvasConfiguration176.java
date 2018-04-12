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
import com.mcgraw.test.automation.api.sakai.SakaiTool;
import com.mcgraw.test.automation.api.sakai.SakaiUserRole;
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
 * LMS = Canvas / Sakai
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
public class CustomCanvasConfiguration176 extends BaseTest {

	@Autowired
	private SakaiApplication sakaiApplication;

	@Autowired
	private ISakaiApiService sakaiApiService;

	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	private RestApplication restApp;

	private String password = "123qweA@";

	// for Classic integration
	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom = getRandomString();

	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String instructorSurname = "InstructorSurname" + instructorRandom;

	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;
	private String studentSurname = "StudentSurname" + instructorRandom;

	private String courseName = "CourseName" + courseRandom;
	
	//for Deep integration
	private String studentRandomDi = getRandomString();
	private String instructorRandomDi = getRandomString();
	private String courseRandomDi = getRandomString();

	private String instructorLoginDi = "instructorDi" + instructorRandomDi;
	private String instructorNameDi = "InstructorNameDi" + instructorRandomDi;
	private String instructorSurnameDi = "InstructorSurnameDi" + instructorRandomDi;

	private String studentLoginDi = "studentDi" + studentRandomDi;
	private String studentNameDi = "StudentNameDi" + studentRandomDi;
	private String studentSurnameDi = "StudentSurnameDi" + instructorRandomDi;

	private String courseNameDi = "CourseNameDi" + courseRandomDi;

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
	
	private CanvasUser studentCanvasDi;
	private CanvasUser instructorCanvasDi;
	private CanvasCourseRS courseCanvasDi;
	private CanvasUserEnrollmentRS studentEnrollmentCanvasDi;
	private CanvasUserEnrollmentRS instructorEnrollmentCanvasDi;
	
	// Sakai User Data
	private AddNewUser studentSakai;
	private AddNewUser instructorSakai;
	private AddNewSite courseSakai;
	
	private AddNewUser studentSakaiDi;
	private AddNewUser instructorSakaiDi;
	private AddNewSite courseSakaiDi;

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
	private String categoryType = "Blog";
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

	// other data
	private String appName = "McGraw Hill Campus";
	private String moduleName = "MH Campus";
	
	private String customerNumber = "1C4G-MA1H-BIBH";
	private String sharedSecret = "8A7807";
	// private String tegrityUsername =
	// "admin@CustomCanvasConfiguration176.mhcampus.com";
	// private String tegrityPassword = "kkyksgtp";

	private String deepAppName = "AConnectDI";
	private String tool_ID = "Connect";
	private static final String CANVAS_FRAME = "tool_content";

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration:");
		Logger.info("LMS = Canvas/Sakai | DI: Enabled ");
		Logger.info(
				"Course ID – User ID Mapping: Internal – Internal | Gradebook Connector: Yes | SSO Connector: No | Canvas Mapping: Yes ");
		Logger.info(
				"* Instructor Level Token: Off | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Async: Off");

		prepareDataInCanvas();
		prepareDataInSakai();

	}

	// @AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearSakaiData();
		clearCanvasData();
	}

	// SAKAI 
	@Test(description = "Check MH Campus link is present for Sakai instructor", enabled = true)
	public void checkMhCampusLinkIsPresentInInstructorsCourseSakai() throws Exception {

		sakaiHomeScreen = sakaiApplication.loginToSakai(instructorLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(courseSakai.getSiteid());
		// sakaiGradesScreen = sakaiCourseDetailsScreen.clickGradebookBtn();

		Assert.assertEquals(sakaiCourseDetailsScreen.getMhCampusLinksCount(), 1,
				"Wrong count of MH Campus links for instructor's course " + courseSakai.getSiteid());
		sakaiApplication.logoutFromSakai();
	}

	@Test(description = "Check MH Campus link is present for Sakai student", dependsOnMethods = {
			"checkMhCampusLinkIsPresentInInstructorsCourseSakai" })
	public void checkMhCampusLinkIsPresentInStudentsCourseSakai() throws Exception {

		sakaiHomeScreen = sakaiApplication.loginToSakai(studentLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(courseSakai.getSiteid());

		Assert.assertEquals(sakaiCourseDetailsScreen.getMhCampusLinksCount(), 1,
				"Wrong count of MH Campus links for student's course " + courseSakai.getSiteid());
		sakaiApplication.logoutFromSakai();
	}

	@Test(description = "Check TestScorableItem form is submitted successfully", dependsOnMethods = {
			"checkMhCampusLinkIsPresentInStudentsCourseSakai" })
	public void checkSubmittingTestScorableItemFormIsSuccessfullSakai() throws Exception {

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber,
				providerId, courseSakai.getSiteid(), assignmentId, assignmentTitle, categoryType, description,
				startDate, dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForSakai(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = {
			"checkSubmittingTestScorableItemFormIsSuccessfullSakai" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudentSakai() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId,
				courseSakai.getSiteid(), assignmentId, studentLogin, commentForStudent, dateSubmitted,
				scoreReceivedForStudent, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForSakai(), "TestScore form submit failed");
	}

	// CANVAS Classic
	@Test(description = "Check SSO to MHC as Instructor with ILT ", dependsOnMethods = {
	"checkSubmittingTestScoreFormIsSuccessfullForStudentSakai" })
	public void ssoAsInstructorCanvasClassic() throws Exception {

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

	@Test(description = "Check SSO to MHC as Student", dependsOnMethods = { "ssoAsInstructorCanvasClassic" })
	public void ssoAsStudentCanvasClassic() throws Exception {

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

	@Test(description = "Check TestScorableItem form is submitted successfully", dependsOnMethods = { "ssoAsStudentCanvasClassic" })
	public void checkSubmittingTestScorableItemFormIsSuccessfullClassicCanvas() throws Exception {

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber,
				providerId, Integer.toString(courseCanvas.getId()), assignmentId, assignmentTitle, category,
				description, startDate, dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(courseCanvas, assignmentTitle);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = {
			"checkSubmittingTestScorableItemFormIsSuccessfullClassicCanvas" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudentClassicCanvas() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId,
				Integer.toString(courseCanvas.getId()), assignmentId, Integer.toString(studentCanvas.getId()),
				commentForStudent, dateSubmitted, scoreReceivedForStudent, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS,
				studentCanvas);
		Assert.assertEquals(canvasSubmissions.size(), 1,
				"Student " + studentLogin + " can see the comment and Score received of");
	}

	// CANVAS Deep
	@Test(description = "Check SSO to MHC as Instructor for Canvas Deep", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudentClassicCanvas" })
	public void ssoAsInstructorDeepCanvas() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLoginDi, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseNameDi);
		Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),
				"link doesn't present");

		canvasConnectCreateAccountScreen = canvasCourseDetailsScreen.ClickOnToolLink(deepAppName, true);
		String errorMsg = canvasConnectCreateAccountScreen.getErrorMessageText();
		Assert.verifyEquals(canvasConnectCreateAccountScreen.getErrorMessageText(), errorMsg);
		canvasApplication.logoutFromCanvas();
	}

	@Test(description = "Check SSO to MHC as Student for Canvas Deep", dependsOnMethods = { "ssoAsInstructorDeepCanvas" })
	public void ssoAsStudentDeepCanvas() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentLoginDi, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseNameDi);
		Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),
				"link doesn't present");

		canvasConnectCreateAccountScreen = canvasCourseDetailsScreen.ClickOnToolLink(deepAppName, false);
		String errorMsg = canvasConnectCreateAccountScreen.getErrorMessageText();
		Assert.verifyEquals(canvasConnectCreateAccountScreen.getErrorMessageText(), errorMsg);
		canvasApplication.logoutFromCanvas();
	}

	@Test(description = "Check TestScorableItem form is submitted successfully for Canvas Deep", dependsOnMethods = {
			"ssoAsStudentDeepCanvas" })
	public void checkSubmittingTestScorableItemFormIsSuccessfullDeepCanvas() throws Exception {
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

	@Test(description = "Check TestScore form is submitted successfully for Canvas Deep", dependsOnMethods = {
			"checkSubmittingTestScorableItemFormIsSuccessfullDeepCanvas" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudentDeepCanvas() throws Exception {
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
		
		//Deep Integration users & course
		studentCanvasDi = canvasApiUtils.createUser(studentLoginDi, password, studentNameDi);
		instructorCanvasDi = canvasApiUtils.createUser(instructorLoginDi, password, instructorNameDi);

		courseCanvasDi = canvasApiUtils.createPublishedCourse(courseNameDi);
			
		studentEnrollmentCanvasDi = canvasApiUtils.enrollToCourseAsActiveStudent(studentCanvasDi, courseCanvasDi);
		instructorEnrollmentCanvasDi = canvasApiUtils.enrollToCourseAsActiveTeacher(instructorCanvasDi, courseCanvasDi);
		
		encryptedCourseId = restApp.fillUnencryptedIdAndPressEncrypt(Integer.toString(courseCanvasDi.getId())).getResult();
		encryptedStudentId = restApp.fillUnencryptedIdAndPressEncrypt(studentCanvasDi.getLoginId()).getResult();
		xmlFileConfiguration = getFile();

		canvasHomeScreen = canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin,
				canvasApplication.canvasAdminPassword);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseName);
		canvasCourseDetailsScreen.createMhCampusApplication(appName, customerNumber, sharedSecret,
				canvasApplication.ltiLaunchUrl);
		browser.pause(2000);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseNameDi);
		canvasCourseDetailsScreen.createApplicationLink(customerNumber, sharedSecret, xmlFileConfiguration, deepAppName);
		browser.pause(2000);
		canvasApplication.logoutFromCanvas();

		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		canvasCourseDetailsScreen.createModuleWithApplication(moduleName, appName);
		canvasApplication.logoutFromCanvas();
	}

	public void prepareDataInSakai() throws Exception {

		studentSakai = sakaiApiService.createUser(studentLogin, password, studentName, studentSurname);
		instructorSakai = sakaiApiService.createUser(instructorLogin, password, instructorName, instructorSurname);
		
		studentSakaiDi = sakaiApiService.createUser(studentLoginDi, password, studentNameDi, studentSurnameDi);
		instructorSakaiDi = sakaiApiService.createUser(instructorLoginDi, password, instructorNameDi, instructorSurnameDi);


		courseSakai = sakaiApiService.addNewSite(courseRandom);
		sakaiApiService.addMemberToSiteWithRole(courseSakai, studentSakai, SakaiUserRole.STUDENT);
		sakaiApiService.addMemberToSiteWithRole(courseSakai, instructorSakai, SakaiUserRole.INSTRUCTOR);
		sakaiApiService.addNewToolToSite(courseSakai, SakaiTool.LINK_TOOL);
		
		courseSakaiDi = sakaiApiService.addNewSite(courseRandomDi);
		sakaiApiService.addMemberToSiteWithRole(courseSakaiDi, studentSakaiDi, SakaiUserRole.STUDENT);
		sakaiApiService.addMemberToSiteWithRole(courseSakaiDi, instructorSakaiDi, SakaiUserRole.INSTRUCTOR);
		sakaiApiService.addNewToolToSite(courseSakaiDi, SakaiTool.LINK_TOOL);

		sakaiApplication.completeMhCampusSetupWithSakai(courseSakai.getSiteid(), customerNumber, sharedSecret);
		sakaiApplication.completeMhCampusSetupWithSakai(courseSakaiDi.getSiteid(), customerNumber, sharedSecret);
		sakaiApplication.logoutFromSakai();
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
		if((studentEnrollmentCanvas != null) & (courseCanvas != null))
			canvasApiUtils.deleteEnrollment(studentEnrollmentCanvas, courseCanvas);
		if((studentEnrollmentCanvasDi != null) & (courseCanvasDi != null))
			canvasApiUtils.deleteEnrollment(studentEnrollmentCanvasDi, courseCanvasDi);
		if((instructorEnrollmentCanvas != null) & (courseCanvas != null))
			canvasApiUtils.deleteEnrollment(instructorEnrollmentCanvas, courseCanvas);
		if((instructorEnrollmentCanvasDi != null) & (courseCanvasDi != null))
			canvasApiUtils.deleteEnrollment(instructorEnrollmentCanvasDi, courseCanvasDi);
		if(courseCanvas != null)
			canvasApiUtils.deleteCourse(courseCanvas);
		if(courseCanvasDi != null)
			canvasApiUtils.deleteCourse(courseCanvasDi);
			
		if(studentCanvas != null){
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(studentCanvas);
			canvasApiUtils.deleteUser(studentToDelete);
		}
		if(studentCanvasDi != null){
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(studentCanvasDi);
			canvasApiUtils.deleteUser(studentToDelete);
		}
		if(instructorCanvas != null){
			CanvasUserRS instructorToDelete = new CanvasUserRS();
			instructorToDelete.setUser(instructorCanvas);
			canvasApiUtils.deleteUser(instructorToDelete);
		}		
		if(instructorCanvasDi != null){
			CanvasUserRS instructorToDelete = new CanvasUserRS();
			instructorToDelete.setUser(instructorCanvasDi);
			canvasApiUtils.deleteUser(instructorToDelete);
		}
	}

	private void clearSakaiData() throws Exception {

		if (courseSakai != null)
			sakaiApiService.deletePageWithToolFromSite(courseSakai, SakaiTool.LINK_TOOL);
		if (studentSakai != null)
			sakaiApiService.deleteUser(studentSakai.getEid());
		if (instructorSakai != null)
			sakaiApiService.deleteUser(instructorSakai.getEid());
		if (courseSakai != null)
			sakaiApiService.deleteSite(courseSakai.getSiteid());
	}
}

