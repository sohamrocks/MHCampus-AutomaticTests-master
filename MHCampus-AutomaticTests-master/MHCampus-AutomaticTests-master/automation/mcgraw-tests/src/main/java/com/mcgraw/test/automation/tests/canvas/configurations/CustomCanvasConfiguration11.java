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
 * Course ID – User ID Mapping: internal – Internal 
 * Gradebook Connector: NO
 * SSO Connector: NO
 * Canvas Mapping: Yes
 * Instructor Level Token: Off
 * Use Existing Assignments: Off
 * Fallback to user_id and context_id: Off
 */

public class CustomCanvasConfiguration11 extends BaseTest {

	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	@Autowired
	private GradebookApplication gradebookApplication;
	
	@Autowired
	private RestApplication restApp;

	private String password = "123qweA@";

	//for Classic integration
	private String appName = "McGraw Hill Campus";
	private String moduleName = "MH Campus";
	private static final String CANVAS_FRAME = "tool_content";
	
	private String studentRandom1 = getRandomString();
	private String instructorRandom1 = getRandomString();
	private String courseRandom1 = getRandomString();

	private String instructorLogin1 = "instructor1" + instructorRandom1;
	private String instructorName1 = "InstructorName" + instructorRandom1;
	private String studentLogin1 = "student1" + studentRandom1;
	private String studentName1 = "StudentName" + studentRandom1;
	private String courseName1 = "CourseName1" + courseRandom1;
	
	private CanvasUser student1;
	private CanvasUser instructor1;
	private CanvasCourseRS course1;
	private CanvasUserEnrollmentRS studentEnrollment1;
	private CanvasUserEnrollmentRS instructorEnrollment1;

	//for Deep integration
	private String deepAppName = "AConnectDI";
	private String tool_ID = "Connect";
	
	private String studentRandom2 = getRandomString();
	private String instructorRandom2 = getRandomString();
	private String courseRandom2 = getRandomString();

	private String instructorLogin2 = "instructor2" + instructorRandom2;
	private String instructorName2 = "InstructorName" + instructorRandom2;
	private String studentLogin2 = "student2" + studentRandom2;
	private String studentName2 = "StudentName" + studentRandom2;
	private String courseName2 = "CourseName2" + courseRandom2;
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
	private String assignmentId = getRandomNumber();
	private String assignmentId2 = getRandomNumber();
	private String assignmentTitle = "title_" + getRandomString();
	private String assignmentTitle2 = "title_" + getRandomString();
	private String category = "Test";
	private String description = "description_" + getRandomString();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String scoreType = "Percentage";
	private String scorePossible = "100";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;
	private String commentForStudent1 = "comment_1" + getRandomString();
	private String commentForStudent2 = "comment_2" + getRandomString();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent1 = GradebookApplication.getRandomScore();
	private String scoreReceivedForStudent2 = GradebookApplication.getRandomScore();
	private CanvasAssignmentRS canvasAssignmentRS;

	// screen objects
	private CanvasHomeScreen canvasHomeScreen;
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;
	private CanvasConnectCreateAccountScreen canvasConnectCreateAccountScreen;

	// other data
	private String customerNumber = "238N-V2HJ-9M7W";
	private String sharedSecret = "F6CDB9";

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		prepareTestDataInCanvas();
		encryptedCourse2Id = restApp.fillUnencryptedIdAndPressEncrypt(Integer.toString(course2.getId())).getResult();
		encryptedStudent2Id = restApp.fillUnencryptedIdAndPressEncrypt(student2.getLoginId()).getResult();
		xmlFileConfiguration = getFile();
		
		canvasHomeScreen = canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin, canvasApplication.canvasAdminPassword);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseName1);
		canvasCourseDetailsScreen.createMhCampusApplication(appName, customerNumber, sharedSecret, canvasApplication.ltiLaunchUrl , canvasMappingType.INTERNALTOINTERNAL , true);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseName2);
		canvasCourseDetailsScreen.createApplicationLink(customerNumber, sharedSecret, xmlFileConfiguration, deepAppName);
		canvasApplication.logoutFromCanvas();
		
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin1, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		canvasCourseDetailsScreen.createModuleWithApplication(moduleName, appName);
		canvasApplication.logoutFromCanvas();	
	}
	
	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearCanvasData();
	}
	
	@Test(description = "Check SSO to MHC as Instructor Classic")
	public void checkSsoAsInstructorClassic() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin1, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForLti(false);
		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(CANVAS_FRAME));
		mhCampusIntroductionScreen.acceptRules(CANVAS_FRAME);
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), instructorName1.toUpperCase(), "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, CANVAS_FRAME), "Course" + courseName1 + " is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(CANVAS_FRAME), "Search option is absent");
		canvasApplication.logoutFromCanvas();
	}
	
	@Test(description = "Check SSO to MHC as Student Classic", dependsOnMethods = { "checkSsoAsInstructorClassic" })
	public void checkSsoAsStudentClassic() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentLogin1, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForLti(false);
		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(CANVAS_FRAME));
		mhCampusIntroductionScreen.acceptRules(CANVAS_FRAME);
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), studentName1.toUpperCase(), "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, CANVAS_FRAME), "Course" + courseName1 + " is absent");
		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(CANVAS_FRAME), "Search option is present");
		canvasApplication.logoutFromCanvas();
	}
	
	@Test(description = "Check TestScorableItem form is submitted successfully Classic", dependsOnMethods = {"checkSsoAsStudentClassic"})
	public void checkSubmittingTestScorableItemFormIsSuccessfullClassic() throws Exception {
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber, providerId,
				Integer.toString(course1.getId()), assignmentId, assignmentTitle, category, description, startDate, dueToDate, scoreType,
				scorePossible, isStudentViewable, isIncludedInGrade, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScoreItems form submit failed");
		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course1, assignmentTitle);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");
	}

	@Test(description = "Check TestScore form is submitted successfully Classic", dependsOnMethods = { "checkSubmittingTestScorableItemFormIsSuccessfullClassic" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudentClassic() throws Exception {
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId,
				Integer.toString(course1.getId()), assignmentId, Integer.toString(student1.getId()), commentForStudent1, dateSubmitted,
				scoreReceivedForStudent1, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS,
				student1);
		Assert.assertEquals(canvasSubmissions.size(), 1,
				"Student " + studentLogin1 + " can see the comment and Score received of");
	}
	
	@Test(description = "Check SSO to MHC as Instructor Deep integration", dependsOnMethods = {"checkSubmittingTestScoreFormIsSuccessfullForStudentClassic"})
	public void checkSsoAsInstructorDeep() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin2, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName2);
		Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),"link doesn't present");

		canvasConnectCreateAccountScreen = canvasCourseDetailsScreen.ClickOnToolLink(deepAppName , true);
		String errorMsg = canvasConnectCreateAccountScreen.getErrorMessageText();
		Assert.verifyEquals(canvasConnectCreateAccountScreen.getErrorMessageText(), errorMsg);
		canvasApplication.logoutFromCanvas();
	}
	
	@Test(description = "Check SSO to MHC as Student Deep integration", dependsOnMethods = { "checkSsoAsInstructorDeep" })
	public void checkSsoAsStudentDeep() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentLogin2, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName2);
		Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),"link doesn't present");

		canvasConnectCreateAccountScreen = canvasCourseDetailsScreen.ClickOnToolLink(deepAppName , false);
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
		student1 = canvasApiUtils.createUser(studentLogin1, password, studentName1);
		student2 = canvasApiUtils.createUser(studentLogin2, password, studentName2);
		instructor1 = canvasApiUtils.createUser(instructorLogin1, password, instructorName1);
		instructor2 = canvasApiUtils.createUser(instructorLogin2, password, instructorName2);
		
		course1 = canvasApiUtils.createPublishedCourse(courseName1);
		course2 = canvasApiUtils.createPublishedCourse(courseName2);
		
		studentEnrollment1 = canvasApiUtils.enrollToCourseAsActiveStudent(student1, course1);
		instructorEnrollment1 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor1, course1);
		studentEnrollment2 = canvasApiUtils.enrollToCourseAsActiveStudent(student2, course2);
		instructorEnrollment2 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor2, course2);
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

		if((studentEnrollment1 != null) & (course1 != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment1, course1);
		if((studentEnrollment2 != null) & (course2 != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment2, course2);
		if((instructorEnrollment1 != null) & (course1 != null))
			canvasApiUtils.deleteEnrollment(instructorEnrollment1, course1);
		if((instructorEnrollment2 != null) & (course2 != null))
			canvasApiUtils.deleteEnrollment(instructorEnrollment2, course2);
		if(course1 != null)
			canvasApiUtils.deleteCourse(course1);
		if(course2 != null)
			canvasApiUtils.deleteCourse(course2);
		
		if(student1 != null){
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(student1);
			canvasApiUtils.deleteUser(studentToDelete);
		}
		if(student2 != null){
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(student2);
			canvasApiUtils.deleteUser(studentToDelete);
		}
		if(instructor1 != null){
			CanvasUserRS instructorToDelete = new CanvasUserRS();
			instructorToDelete.setUser(instructor1);
			canvasApiUtils.deleteUser(instructorToDelete);
		}
		if(instructor2 != null){
			CanvasUserRS instructorToDelete = new CanvasUserRS();
			instructorToDelete.setUser(instructor2);
			canvasApiUtils.deleteUser(instructorToDelete);
		}
		
	}

}
