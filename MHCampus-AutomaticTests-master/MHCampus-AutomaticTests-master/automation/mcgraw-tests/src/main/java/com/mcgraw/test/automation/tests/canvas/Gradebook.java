package com.mcgraw.test.automation.tests.canvas;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.canvas.exception.AssignmentNotFound;
import com.mcgraw.test.automation.api.rest.canvas.exception.SubmissionNotFoundException;
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

public class Gradebook extends BaseTest {

	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	@Autowired
	private GradebookApplication gradebookApplication;

	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom1 = getRandomString();
	private String courseRandom2 = getRandomString();
	
	private String studentLogin1 = "student" + studentRandom;
	private String studentName1 = "StudentName" + studentRandom;
	private String studentLogin2 = "student2" + studentRandom;
	private String studentName2 = "StudentName2" + studentRandom;

	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;

	private String courseName1 = "CourseName1" + courseRandom1;
	private String courseName2 = "CourseName2" + courseRandom2;

	private String password = "123qweA@";

	private CanvasUser student1;
	private CanvasUser student2;
	private CanvasUser instructor;
	private CanvasCourseRS course1;
	private CanvasCourseRS course2;
	private CanvasUserEnrollmentRS studentEnrollment11;
	private CanvasUserEnrollmentRS instructorEnrollment1;
	private CanvasUserEnrollmentRS studentEnrollment12;
	private CanvasUserEnrollmentRS instructorEnrollment2;
	private CanvasUserEnrollmentRS studentEnrollment21;
	private CanvasUserEnrollmentRS studentEnrollment22;

	private String providerId = "provider_" + getRandomString();
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
	private String commentForStudent1 = "comment_" + getRandomString();
	private String commentForStudent2 = "comment_2" + getRandomString();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent1 = GradebookApplication.getRandomScore();
	private String scoreReceivedForStudent2 = GradebookApplication.getRandomScore();

	private CanvasAssignmentDetailsScreen canvasAssignmentDetailsScreen;
	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	private CanvasHomeScreen canvasHomeScreen;
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;
	private CanvasGradebookScreen canvasGradebookScreen;

	private int numOfSlave = 3;
	
	private InstanceCredentials instance;

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		
		prepareTestDataInCanvas();
		
		instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
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

	@Test(description = "Check TestScorableItem form is submitted successfully")
	public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {
		
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(instance.customerNumber, providerId,
				Integer.toString(course1.getId()), assignmentId, assignmentTitle, category, description, startDate, dueToDate, scoreType,
				scorePossible, isStudentViewable, isIncludedInGrade, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = { "checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent1() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, providerId,
				Integer.toString(course1.getId()), assignmentId, Integer.toString(student1.getId()), commentForStudent1, dateSubmitted,
				scoreReceivedForStudent1, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = { "checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent2() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, providerId,
				Integer.toString(course1.getId()), assignmentId, Integer.toString(student2.getId()), commentForStudent2, dateSubmitted,
				scoreReceivedForStudent2, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
	}

	@Test(description = "Check Gradebook data related to the first Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" }, priority = 0)
	public void checkGradebookItemsAreCorrectForStudent1Course1() throws Exception {
		
		CanvasAssignmentRS canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course1, assignmentTitle);

		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS, student1);

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");
		//Assert.verifyEquals(dateFormat.format(canvasAssignmentRS.getDueDate()), dueToDate, "Due to date did not match");
		Assert.verifyEquals(Integer.toString(canvasAssignmentRS.getPointsPossible()), scorePossible, "ScorePoints did not match");

		// check the first student contains only one submission data
		Assert.assertEquals(canvasSubmissions.size(), 1, "Student " + studentLogin1 + " can see the comment and Score received of"
				+ studentLogin2);
		// if the first student contains only one submission data, check the
		// data is correct
		Assert.verifyEquals(canvasSubmissions.get(0).getSubmissionComment().getComment(), commentForStudent1, "Comment did not match");
		Assert.verifyEquals(Integer.toString(canvasSubmissions.get(0).getScoreNumber()), scoreReceivedForStudent1,
				"ScoreReceived did not match");

		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		canvasGradebookScreen = canvasCourseDetailsScreen.clickGradesButton();
		canvasAssignmentDetailsScreen = canvasGradebookScreen.clickOnAssignment(assignmentTitle);
		Assert.verifyEquals(canvasAssignmentDetailsScreen.getDescription(),description,"Description did not match");
	}
	
	@Test(description = "Check Gradebook data related to the second Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" }, priority = 0)
	public void checkGradebookItemsAreCorrectForStudent2Course1() throws Exception {

		CanvasAssignmentRS canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course1, assignmentTitle);

		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS, student2);

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");
		//Assert.verifyEquals(dateFormat.format(canvasAssignmentRS.getDueDate()), dueToDate, "Due to date did not match");
		Assert.verifyEquals(Integer.toString(canvasAssignmentRS.getPointsPossible()), scorePossible, "ScorePoints did not match");

		// check the second student contains only one submission data
		Assert.assertEquals(canvasSubmissions.size(), 1, "Student " + studentLogin2 + " can see the comment and Score received of"
				+ studentLogin1);
		// if the second student contains only one submission data, check the
		// data is correct
		Assert.verifyEquals(canvasSubmissions.get(0).getSubmissionComment().getComment(), commentForStudent2, "Comment did not match");
		Assert.verifyEquals(Integer.toString(canvasSubmissions.get(0).getScoreNumber()), scoreReceivedForStudent2,
				"ScoreReceived did not match");
	}

	@Test(description = "Check sent Gradebook items are NOT present in the Canvas unused course for the first Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" }, expectedExceptions = {
			AssignmentNotFound.class, SubmissionNotFoundException.class })
	public void checkGradebookItemsAreNotPresentForStudent1Course2() throws Exception {
		CanvasAssignmentRS canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course2, assignmentTitle);
		canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS, student1);
	}
	
	@Test(description = "Check sent Gradebook items are NOT present in the Canvas unused course for the second Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" }, expectedExceptions = {
			AssignmentNotFound.class, SubmissionNotFoundException.class })
	public void checkGradebookItemsAreNotPresentForStudent2Course2() throws Exception {
		CanvasAssignmentRS canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course2, assignmentTitle);
		canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS, student2);
	}

	private void prepareTestDataInCanvas() throws Exception {
		student1 = canvasApiUtils.createUser(studentLogin1, password, studentName1);
		student2 = canvasApiUtils.createUser(studentLogin2, password, studentName2);
		instructor = canvasApiUtils.createUser(instructorLogin, password, instructorName);
		
		course1 = canvasApiUtils.createPublishedCourse(courseName1);
		course2 = canvasApiUtils.createPublishedCourse(courseName2);
		
		studentEnrollment11 = canvasApiUtils.enrollToCourseAsActiveStudent(student1, course1);
		instructorEnrollment1 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor, course1);
		studentEnrollment21 = canvasApiUtils.enrollToCourseAsActiveStudent(student2, course1);
		studentEnrollment12 = canvasApiUtils.enrollToCourseAsActiveStudent(student1, course2);
		instructorEnrollment2 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor, course2);
		studentEnrollment22 = canvasApiUtils.enrollToCourseAsActiveStudent(student2, course2);
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}
	
	private void clearCanvasData() throws Exception {

		if((studentEnrollment11 != null) & (course1 != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment11, course1);
		if((studentEnrollment21 != null) & (course1 != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment21, course1);
		if((instructorEnrollment1 != null) & (course1 != null))
			canvasApiUtils.deleteEnrollment(instructorEnrollment1, course1);
		if(course1 != null)
			canvasApiUtils.deleteCourse(course1);
		
		if((studentEnrollment12 != null) & (course2 != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment12, course2);
		if((studentEnrollment22 != null) & (course2 != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment22, course2);
		if((instructorEnrollment2 != null) & (course1 != null))
			canvasApiUtils.deleteEnrollment(instructorEnrollment2, course2);
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
		if(instructor != null){
			CanvasUserRS instructorToDelete = new CanvasUserRS();
			instructorToDelete.setUser(instructor);
			canvasApiUtils.deleteUser(instructorToDelete);
		}
		
	}
}
