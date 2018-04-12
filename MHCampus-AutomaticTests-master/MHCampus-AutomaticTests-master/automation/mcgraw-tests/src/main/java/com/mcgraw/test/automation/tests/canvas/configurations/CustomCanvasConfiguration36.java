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
/**
 * LMS = Canvas
 * DI: Enabled
 * Course ID – User ID Mapping:  None
 * Gradebook Connector: NO
 * SSO Connector: NO
 * Canvas Mapping: Yes
 * Instructor Level Token: Off
 * Use Existing Assignments: Off
 * Fallback to user_id and context_id: Off
 */
public class CustomCanvasConfiguration36 extends BaseTest {

	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	@Autowired
	private GradebookApplication gradebookApplication;
	
	@Autowired
	private RestApplication restApp;
	
	private String customerNumber = "6QU8-V7OW-L2FW";
	private String sharedSecret = "539894";
	private String password = "123qweA@";

	//for Deep integration
	private String deepAppName = "AConnectDI";
	private String tool_ID = "Connect";

	private String studentRandom2 = getRandomString();
	private String instructorRandom2 = getRandomString();
	private String courseRandom2 = getRandomString();

	private String instructorLogin2 = "instructor2" + instructorRandom2;
	private String instructorName2 = "InstructorName" + instructorRandom2;
	private String instructorSis2 = "InstructorSis" + instructorRandom2;
	private String studentLogin2 = "student2" + studentRandom2;
	private String studentName2 = "StudentName" + studentRandom2;
	private String studendSis2 = "StudentSis" + studentRandom2;
	private String courseName2 = "CourseName2" + courseRandom2;
	private String courseSis2 = "CourseSis" + courseRandom2;
	private String encryptedCourse2Id;
	private String encryptedStudent2Id;
	private String xmlFileConfiguration;
	private String fullPathToFile;
	private String fileName = "QA_DI_Cartridge.xml";
	
	private CanvasUser student2;
	private CanvasUser instructor2;
	private CanvasCourseRS course2;
	private CanvasUserEnrollmentRS studentEnrollment2;
	private CanvasUserEnrollmentRS instructorEnrollment2;
		
	// data for creating assignment
	private String providerId = "Connect";
	private String assignmentId2 = getRandomNumber();
	private String assignmentTitle2 = "title_" + getRandomString();
	private String category = "Test";
	private String description = "description_" + getRandomString();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String scoreType = "Percentage";
	private String scorePossible = "100";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;
	private String commentForStudent2 = "comment_2" + getRandomString();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent2 = GradebookApplication.getRandomScore();
	private CanvasAssignmentRS canvasAssignmentRS;

	// screen objects
	private CanvasHomeScreen canvasHomeScreen;
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;
	private CanvasConnectCreateAccountScreen canvasConnectCreateAccountScreen;


	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration: CustomCanvasConfiguration36");
		Logger.info("LMS = Canvas | DI: Enabled ");
		Logger.info("Course ID – User ID Mapping: sis – sis | Gradebook Connector: No | SSO Connector: No  | Canvas Mapping: Yes ");
		Logger.info("* Instructor Level Token: Off | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Async: Off");
		
		
		prepareTestDataInCanvas();
		
		canvasHomeScreen = canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin, canvasApplication.canvasAdminPassword);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseName2);
		canvasCourseDetailsScreen.createApplicationLink(customerNumber, sharedSecret, xmlFileConfiguration, deepAppName);
		canvasApplication.logoutFromCanvas();
	}
	
	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearCanvasData();
	}
	
	@Test(description = "Check SSO to MHC as Instructor Deep integration")
	public void checkSsoAsInstructorDeep() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin2, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName2);
		Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),"link doesn't present");

		canvasConnectCreateAccountScreen = canvasCourseDetailsScreen.ClickOnToolLink(deepAppName, true);
		String errorMsg = canvasConnectCreateAccountScreen.getErrorMessageText();
		Assert.verifyEquals(canvasConnectCreateAccountScreen.getErrorMessageText(), errorMsg);
		canvasApplication.logoutFromCanvas();
	}
	
	@Test(description = "Check SSO to MHC as Student Deep integration", dependsOnMethods = { "checkSsoAsInstructorDeep" })
	public void checkSsoAsStudentDeep() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentLogin2, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName2);
		Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),"link doesn't present");

		canvasConnectCreateAccountScreen = canvasCourseDetailsScreen.ClickOnToolLink(deepAppName, false);
		String errorMsg = canvasConnectCreateAccountScreen.getErrorMessageText();
		Assert.verifyEquals(canvasConnectCreateAccountScreen.getErrorMessageText(), errorMsg);
		canvasApplication.logoutFromCanvas();
	}
	
	@Test(description = "Check TestScorableItem form is submitted successfully Deep", dependsOnMethods = {"checkSsoAsStudentDeep"})
	public void checkSubmittingTestScorableItemFormIsSuccessfullDeep() throws Exception {
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemFormDI(customerNumber, providerId,
				encryptedCourse2Id, assignmentId2, assignmentTitle2, category, description, startDate, dueToDate, scoreType,
				scorePossible, isStudentViewable, isIncludedInGrade, canvasApplication.tegrityServiceLocation, tool_ID);

		// get module ID - is not implemented yet
		// Fill in module ID - is not implemented yet
		
		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvasDI(), "TestScoreItems form submit failed");
		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course2, assignmentTitle2);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle2, "AssignmentTitle did not match");
	}

	@Test(description = "Check TestScore form is submitted successfully Deep", dependsOnMethods = { "checkSubmittingTestScorableItemFormIsSuccessfullDeep" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudentDeep() throws Exception {		
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreFormDI(customerNumber, providerId,
				encryptedCourse2Id, assignmentId2, encryptedStudent2Id, commentForStudent2, dateSubmitted,
				scoreReceivedForStudent2, canvasApplication.tegrityServiceLocation, tool_ID);

		// get module ID - is not implemented yet
		// Fill in module ID - is not implemented yet		
		
		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvasDI(), "TestScore form submit failed");
		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS,
				student2);
		Assert.assertEquals(canvasSubmissions.size(), 1,
				"Student " + studentLogin2 + " can see the comment and Score received of");
	}
	

	private void prepareTestDataInCanvas() throws Exception {	
		student2 = canvasApiUtils.createUserWithSis(studentLogin2, password, studentName2, studendSis2);
		instructor2 = canvasApiUtils.createUserWithSis(instructorLogin2, password, instructorName2, instructorSis2);
		course2 = canvasApiUtils.createPublishedCourseWithSis(courseName2, courseSis2);
		studentEnrollment2 = canvasApiUtils.enrollToCourseAsActiveStudent(student2, course2);
		instructorEnrollment2 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor2, course2);
		
		encryptedCourse2Id = restApp.fillUnencryptedIdAndPressEncrypt(courseSis2).getResult();
		encryptedStudent2Id = restApp.fillUnencryptedIdAndPressEncrypt(student2.getLoginId()).getResult();
		xmlFileConfiguration = getFile();
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

		if((studentEnrollment2 != null) & (course2 != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment2, course2);
		if((instructorEnrollment2 != null) & (course2 != null))
			canvasApiUtils.deleteEnrollment(instructorEnrollment2, course2);
		if(course2 != null)
			canvasApiUtils.deleteCourse(course2);
		if(student2 != null){
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(student2);
			canvasApiUtils.deleteUser(studentToDelete);
		}
		if(instructor2 != null){
			CanvasUserRS instructorToDelete = new CanvasUserRS();
			instructorToDelete.setUser(instructor2);
			canvasApiUtils.deleteUser(instructorToDelete);
		}
		
	}

}