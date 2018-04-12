package com.mcgraw.test.automation.tests.canvas;

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
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.CanvasApplication;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.canvas.CanvasAssignmentDetailsScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasGradebookScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasHomeScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class UseExistingAssignment extends BaseTest {

	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	@Autowired
	private GradebookApplication gradebookApplication;

	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom = getRandomString();
	
	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;
	
	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;

	private String courseName = "CourseName" + courseRandom;
	
	private String password = "123qweA@";

	private CanvasUser student;
	private CanvasUser instructor;
	private CanvasCourseRS course;
	private CanvasUserEnrollmentRS studentEnrollment;
	private CanvasUserEnrollmentRS instructorEnrollment;
	
	private String providerId = "provider_" + getRandomString();
	private String assignmentId = getRandomNumber();
	private String assignmentIdNew = getRandomNumber();
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
	private String scoreReceivedForStudentNew = GradebookApplication.getRandomScore();
	
	private CanvasAssignmentDetailsScreen canvasAssignmentDetailsScreen;
	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	private CanvasHomeScreen canvasHomeScreen;
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;
	private CanvasGradebookScreen canvasGradebookScreen;

	private int internalAssignmentId; 
	private int numOfSlave = 3;
	
	private InstanceCredentials instance;

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		
		prepareTestDataInCanvas();
		
		instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
		// configure extend property 'AssignmentCreationMode' to 'new'
		mhCampusInstanceConnectorsScreen.configureCanvasGradebookConnector(canvasApplication.canvasTitle, canvasApplication.canvasFqdn,
				canvasApplication.canvasAccessToken, canvasApplication.canvasUserIdOrigin, canvasApplication.canvasCourseIdOrigin);
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		
		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearCanvasData();
	}

	@Test(description = "Check TestScorableItem form is submitted successfully with SOType: New")
	public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {
		
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(instance.customerNumber, providerId,
				Integer.toString(course.getId()), assignmentId, assignmentTitle, category, description, startDate, dueToDate, scoreType,
				scorePossible, isStudentViewable, isIncludedInGrade, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully with SOType: New", dependsOnMethods = 
		{ "checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, providerId,
				Integer.toString(course.getId()), assignmentId, Integer.toString(student.getId()), commentForStudent, dateSubmitted,
				scoreReceivedForStudent, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
	}

	@Test(description = "Check Gradebook data related to the Instructor", dependsOnMethods = {
	 		"checkSubmittingTestScoreFormIsSuccessfullForStudent"}, priority = 0)
	public void checkGradebookItemsAreCorrectForInstructor() throws Exception {

		CanvasAssignmentRS canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course, assignmentTitle);
		
		// check the instructor has correct data
        Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");
		Assert.verifyEquals(Integer.toString(canvasAssignmentRS.getPointsPossible()), scorePossible, "ScorePoints did not match");

		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		canvasGradebookScreen = canvasCourseDetailsScreen.clickGradesButton();
		canvasAssignmentDetailsScreen = canvasGradebookScreen.clickOnAssignment(assignmentTitle);
		Assert.verifyEquals(canvasAssignmentDetailsScreen.getDescription(),description,"Description did not match");
	}
	
	@Test(description = "Check Gradebook data related to the Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent"}, priority = 0)
	public void checkGradebookItemsAreCorrectForStudent() throws Exception {
		
		CanvasAssignmentRS canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course, assignmentTitle);
		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS, student);
		
		// check the student has correct data
		Assert.verifyEquals(canvasSubmissions.get(0).getSubmissionComment().getComment(), commentForStudent, "Comment did not match");
		Assert.verifyEquals(Integer.toString(canvasSubmissions.get(0).getScoreNumber()), scoreReceivedForStudent,
				"ScoreReceived did not match");
	}
	
	@Test(description = "Check TestScorableItem form is submitted successfully with new assignment ID and SOType: Exist", 
			dependsOnMethods = {"checkGradebookItemsAreCorrectForInstructor", "checkGradebookItemsAreCorrectForStudent"})
	public void checkSubmittingTestScorableItemFormWithNewAssignmentIdSSOTypeExistIsSuccessfull() throws Exception {
		
		// configure extend property 'AssignmentCreationMode' to 'exist' 
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.useExistingAssignmentInCanvas(true);
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);
		
		assignmentIdNew = getRandomNumber();
		String categoryNew = "CategoryNew";
		String scorePossibleNew = "102";
		String descriptionNew = "DescriptionNew";
		// fill the form with new assignment ID, category and score possible
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(instance.customerNumber, providerId,
				Integer.toString(course.getId()), assignmentIdNew, assignmentTitle, categoryNew, descriptionNew, startDate, dueToDate, scoreType,
				scorePossibleNew, isStudentViewable, isIncludedInGrade, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully with new assignment ID and SOType: Exist", dependsOnMethods =
		{ "checkSubmittingTestScorableItemFormWithNewAssignmentIdSSOTypeExistIsSuccessfull"})
	public void checkSubmittingTestScoreFormWithNewAssignmentIdSSOTypeExistIsSuccessfullForStudent() throws Exception {

		// fill the form with new assignment ID and score received for student
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, providerId,
				Integer.toString(course.getId()), assignmentIdNew, Integer.toString(student.getId()), commentForStudent, dateSubmitted,
				scoreReceivedForStudentNew, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
	}
	
   @Test(description = "Check Gradebook data related to the Instructor", dependsOnMethods = {
		"checkSubmittingTestScoreFormWithNewAssignmentIdSSOTypeExistIsSuccessfullForStudent"}, priority = 0)
	public void checkAssignmentIdWasChangedAndTitleNotForInstructor() throws Exception {
	
	// check new assignment was not created with the same title
	Assert.verifyEquals(canvasApiUtils.getCountOfCourseAssignmentByTitle(course, assignmentTitle), 1, "Ammount of assignments of course " + course 
			+ " with Assignment Title: " + assignmentTitle + " is incorrect");
	// check the instructor has correct data: title, category and score possible was not changed
	CanvasAssignmentRS canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course, assignmentTitle);
	Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");
	Assert.verifyEquals(Integer.toString(canvasAssignmentRS.getPointsPossible()), scorePossible, "ScorePoints did not match");
	
	canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin, password);
	canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
	canvasGradebookScreen = canvasCourseDetailsScreen.clickGradesButton();
	canvasAssignmentDetailsScreen = canvasGradebookScreen.clickOnAssignment(assignmentTitle);
	Assert.verifyEquals(canvasAssignmentDetailsScreen.getDescription(),description,"Description did not match");
	
	}
	
	@Test(description = "Check Gradebook data related to the Student", dependsOnMethods = {
		"checkSubmittingTestScoreFormWithNewAssignmentIdSSOTypeExistIsSuccessfullForStudent"}, priority = 0)
	public void checkAssignmentIdWasChangedAndTitleNotForStudent() throws Exception {
	
	CanvasAssignmentRS canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course, assignmentTitle);
	List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS, student);
	
	// check the student has correct data and and score received for student was changed
	Assert.assertEquals(canvasSubmissions.size(), 1, "Ammount of submissions of Student " + studentLogin  + " is incorrect");	
	Assert.verifyEquals(canvasSubmissions.get(0).getSubmissionComment().getComment(), commentForStudent, "Comment did not match");
	Assert.verifyEquals(Integer.toString(canvasSubmissions.get(0).getScoreNumber()), scoreReceivedForStudentNew,
			"ScoreReceived did not match");
	}
	
	@Test(description = "Check TestScorableItem form is submitted successfully with new assignment ID and SOType: New", dependsOnMethods = 
		{"checkAssignmentIdWasChangedAndTitleNotForInstructor", "checkAssignmentIdWasChangedAndTitleNotForStudent"})
	public void checkSubmittingTestScorableItemFormWithNewAssignmentIdSSOTypeNewIsSuccessfull() throws Exception {
		
		// configure extend property 'AssignmentCreationMode' to 'new' 
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.useExistingAssignmentInCanvas(false);
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);
		
		assignmentIdNew = getRandomNumber();
		// fill the form with the same data and with new assignment ID
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(instance.customerNumber, providerId,
				Integer.toString(course.getId()), assignmentIdNew, assignmentTitle, category, description, startDate, dueToDate, scoreType,
				scorePossible, isStudentViewable, isIncludedInGrade, canvasApplication.tegrityServiceLocation);
		
		internalAssignmentId = testScoreItemsScreen.getInternalAssignmentIdForCanvasCourse();

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully with new assignment ID and SOType: New", dependsOnMethods = 
		{"checkSubmittingTestScorableItemFormWithNewAssignmentIdSSOTypeNewIsSuccessfull"})
	public void checkSubmittingTestScoreFormWithNewAssignmentIdSSOTypeNewIsSuccessfullForStudent() throws Exception {

		scoreReceivedForStudentNew = GradebookApplication.getRandomScore();
		// fill the form with the same data and with new assignment ID
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, providerId,
				Integer.toString(course.getId()), assignmentIdNew, Integer.toString(student.getId()), commentForStudent, dateSubmitted,
				scoreReceivedForStudentNew, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
	}
	
	@Test(description = "Check Gradebook data related to the Instructor in the second assignment", dependsOnMethods = {
		"checkSubmittingTestScoreFormWithNewAssignmentIdSSOTypeNewIsSuccessfullForStudent"}, priority = 0)
	public void checkNewAssignmentWasCreatedWithSameTitleForInstructor() throws Exception {
	
		// check new 'Assignment title' was created 
		Assert.verifyEquals(canvasApiUtils.getCountOfCourseAssignmentByTitle(course, assignmentTitle), 2, "Ammount of assignments of course " + course 
		+ " with Assignment Title: " + assignmentTitle + " is incorrect");
		// check the instructor has correct data in the second assignment
		CanvasAssignmentRS canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByAssignmentId(course, internalAssignmentId);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");
		Assert.verifyEquals(Integer.toString(canvasAssignmentRS.getPointsPossible()), scorePossible, "ScorePoints did not match");
	
	}
	
	@Test(description = "Check Gradebook data related to the Student in the second assignment", dependsOnMethods = {
		"checkSubmittingTestScoreFormWithNewAssignmentIdSSOTypeNewIsSuccessfullForStudent"}, priority = 0)
	public void checkNewAssignmentWasCreatedWithSameTitleForStudent() throws Exception {
	
		CanvasAssignmentRS canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByAssignmentId(course, internalAssignmentId);
		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS, student);
		
		// check the student has correct data in the second assignment
		Assert.assertEquals(canvasSubmissions.size(), 1, "Ammount of submissions of Student " + studentLogin  + " is incorrect");	
		Assert.verifyEquals(canvasSubmissions.get(0).getSubmissionComment().getComment(), commentForStudent, "Comment did not match");
		Assert.verifyEquals(Integer.toString(canvasSubmissions.get(0).getScoreNumber()), scoreReceivedForStudentNew,
				"ScoreReceived did not match");
	}
	
	private void prepareTestDataInCanvas() throws Exception {
		student = canvasApiUtils.createUser(studentLogin, password, studentName);
		instructor = canvasApiUtils.createUser(instructorLogin, password, instructorName);
		course = canvasApiUtils.createPublishedCourse(courseName);
		
		studentEnrollment = canvasApiUtils.enrollToCourseAsActiveStudent(student, course);
		instructorEnrollment = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor, course);
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}
	
	private void clearCanvasData() throws Exception {

		if((studentEnrollment != null) & (course != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment, course);
			if((instructorEnrollment != null) & (course != null))
			canvasApiUtils.deleteEnrollment(instructorEnrollment, course);
		if(course != null)
			canvasApiUtils.deleteCourse(course);
			
		if(student != null){
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(student);
			canvasApiUtils.deleteUser(studentToDelete);
		}
		
		if(instructor != null){
			CanvasUserRS instructorToDelete = new CanvasUserRS();
			instructorToDelete.setUser(instructor);
			canvasApiUtils.deleteUser(instructorToDelete);
		}
		
	}
}
