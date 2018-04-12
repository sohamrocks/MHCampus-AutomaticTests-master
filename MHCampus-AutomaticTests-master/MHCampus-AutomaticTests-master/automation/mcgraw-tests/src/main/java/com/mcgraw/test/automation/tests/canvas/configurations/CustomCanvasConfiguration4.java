package com.mcgraw.test.automation.tests.canvas.configurations;







import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
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
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen.canvasMappingType;
import com.mcgraw.test.automation.ui.canvas.CanvasGradebookScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasHomeScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectCreateAccountScreen;
import com.mcgraw.test.automation.ui.rest.TestRestApi;
/**
* LMS = Canvas


* DI: Enabled
* Course ID – User ID Mapping: internal – Internal
* Gradebook Connector: Yes 
* SSO Connector: Yes 
* Canvas Mapping: Yes 
* Instructor Level Token: Off
* Use Existing Assignments: Off
* Fallback to user_id and context_id: On
* Async:  Off
*/
public class CustomCanvasConfiguration4 extends BaseTest {
	
	
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

	private String studentLogin1 = "student" + studentRandom;
	private String studentName1 = "StudentName" + studentRandom;
	private String studentLogin2 = "student" + studentRandom2;
	private String studentName2 = "StudentName" + studentRandom2;
	private String instructorLogin1 = "instructor" + instructorRandom;
	private String instructorName1 = "InstructorName" + instructorRandom;
	private String instructorLogin2 = "instructor" + instructorRandom2;
	private String instructorName2 = "InstructorName" + instructorRandom2;
	private String courseName1 = "CourseName1" + courseRandom;
	private String courseName2 = "CourseName2" + courseRandom;
	private String password = "123qweA@";

	private CanvasUser student1;
	private CanvasUser student2;
	private CanvasUser instructor1;
	private CanvasUser instructor2;
	private CanvasCourseRS course1;
	private CanvasCourseRS course2;
	private CanvasUserEnrollmentRS studentEnrollment11;
	private CanvasUserEnrollmentRS instructorEnrollment11;
	private CanvasUserEnrollmentRS studentEnrollment22;
	private CanvasUserEnrollmentRS instructorEnrollment22;

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

	private String customerNumber = "MFI7-QWLP-O0BS";
	private String sharedSecret = "9959A6";

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
		Logger.info("Course ID – User ID Mapping:  internal – Internal  | Gradebook Connector: Yes | SSO Connector: Yes | Canvas Mapping: Yes ");
		Logger.info("* Instructor Level Token: Off | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Async: Off");
		
		
		prepareTestDataInCanvas();
		
		//classic
		 canvasHomeScreen =canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin,canvasApplication.canvasAdminPassword);
		 canvasCourseDetailsScreen =canvasHomeScreen.goToCreatedCourseAsAdmin(courseName1);
		 canvasCourseDetailsScreen.createMhCampusApplication(appName,customerNumber, sharedSecret,canvasApplication.ltiLaunchUrl , canvasMappingType.INTERNALTOINTERNAL , true);
		 canvasApplication.logoutFromCanvas();
		
		 canvasHomeScreen =canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin1,password);
		 canvasCourseDetailsScreen =canvasHomeScreen.goToCreatedCourse(courseName1);
		 canvasCourseDetailsScreen.createModuleWithApplication(moduleName,appName);
		 canvasApplication.logoutFromCanvas();

		// Deep
		testRestApi = restApplication.fillUnencryptedIdAndPressEncrypt(Integer.toString(course2.getId()));
		canvasCourseI2dencrypted = testRestApi.getResult();
		testRestApi = restApplication.fillUnencryptedIdAndPressEncrypt((student2.getLoginId()));
		canvasStudent2Idencrypted = testRestApi.getResult();

		xmlFileConfiguration = getFile();
		canvasHomeScreen = canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin,canvasApplication.canvasAdminPassword);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseName2);
		canvasCourseDetailsScreen.createApplicationLink(customerNumber, sharedSecret, xmlFileConfiguration,
				deepAppName);
	}

	 @AfterClass
	 public void testSuiteTearDown() throws Exception {
	 clearCanvasData();
	 }

	// Classic
	// Integration------------------------------------------------------------------------------

	@Test(description = "Check SSO to MHC as Instructor " )
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

	@Test(description = "Check SSO to MHC as Student", dependsOnMethods = {
	"SSOasInstructor" })
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

	@Test(description = "Check TestScorableItem form is submitted successfully", dependsOnMethods = {
	"SSOasStudent" })
	public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber,
				providerId, Integer.toString(course1.getId()), assignmentId, assignmentTitle, category, description,
				startDate, dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course1, assignmentTitle);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = {
			"checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId,
				Integer.toString(course1.getId()), assignmentId, Integer.toString(student1.getId()), commentForStudent,
				dateSubmitted, scoreReceivedForStudent, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS,
				student1);
		Assert.assertEquals(canvasSubmissions.size(), 1, "Submission does not match");
	}

	// Deep
	// integration------------------------------------------------------------------------------

	@Test(description = "Check Deep integration SSO to MHC as Instructor ",dependsOnMethods = {
	"checkSubmittingTestScoreFormIsSuccessfullForStudent" })
	public void DeepSSOasInstructor() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin2, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName2);
		Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),
				"link doesn't present");
		canvasConnectCreateAccountScreen = canvasCourseDetailsScreen.ClickOnToolLink(deepAppName  , true);
		
		Assert.verifyEquals(canvasConnectCreateAccountScreen.getErrorMessageText(), errorMsg);
		canvasApplication.logoutFromCanvas();
	}

	@Test(description = "Check Deep integration SSO to MHC as Student ", dependsOnMethods = {
	"DeepSSOasInstructor" } )
	public void DeepSSOasStudent() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentLogin2, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName2);
		Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),
				"link doesn't present");
		canvasConnectCreateAccountScreen = canvasCourseDetailsScreen.ClickOnToolLink(deepAppName , false);
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

		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course2, assignmentTitle);
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
		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS,student2);
		Assert.assertEquals(canvasSubmissions.size(), 1, "Submission does not match");
	}

	// additional
	// methods--------------------------------------------------------------------------------------------------------
	private void prepareTestDataInCanvas() throws Exception {
		student1 = canvasApiUtils.createUser(studentLogin1, password, studentName1);
		student2 = canvasApiUtils.createUser(studentLogin2, password, studentName2);
		instructor1 = canvasApiUtils.createUser(instructorLogin1, password, instructorName1);
		instructor2 = canvasApiUtils.createUser(instructorLogin2, password, instructorName2);

		course1 = canvasApiUtils.createPublishedCourse(courseName1);
		course2 = canvasApiUtils.createPublishedCourse(courseName2);

		studentEnrollment11 = canvasApiUtils.enrollToCourseAsActiveStudent(student1, course1);
		instructorEnrollment11 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor1, course1);

		instructorEnrollment22 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor2, course2);
		studentEnrollment22 = canvasApiUtils.enrollToCourseAsActiveStudent(student2, course2);

	}

	private void clearCanvasData() throws Exception {

		if ((studentEnrollment11 != null) & (course1 != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment11, course1);
		if ((studentEnrollment22 != null) & (course2 != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment22, course2);

		if ((instructorEnrollment11 != null) & (course1 != null))
			canvasApiUtils.deleteEnrollment(instructorEnrollment11, course1);
		if ((instructorEnrollment22 != null) & (course2 != null))
			canvasApiUtils.deleteEnrollment(instructorEnrollment22, course2);

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
			studentToDelete.setUser(student2);
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

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(6);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}

}

