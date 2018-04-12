package com.mcgraw.test.automation.tests.canvas.instructorleveltoken;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasCourseRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUser;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserEnrollmentRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserRS;
import com.mcgraw.test.automation.api.rest.canvas.service.CanvasApiUtils;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.CanvasApplication;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasHomeScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceDashboardScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class InstructorLevelTokenGradebook extends BaseTest {

	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;
	
	@Autowired
	private GradebookApplication gradebookApplication;

	// For the local run is recommended change to: FULL_PATH_FILE = "C:\\Temp\\tolken_filter_canvas.csv";
	private static String FULL_PATH_FILE = "C:\\Temp\\tolken_filter_canvas.csv";
	
	private String studentRandom = getRandomString();
	private String instructorRandom1 = getRandomString();
	private String instructorRandom2 = getRandomString();
	private String courseRandom = getRandomString();
	
	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;

	private String instructorLogin1 = "instructor1" + instructorRandom1;
	private String instructorName1 = "InstructorName1" + instructorRandom1;
	
	private String instructorLogin2 = "instructor2" + instructorRandom2;
	private String instructorName2 = "InstructorName2" + instructorRandom2;

	private String courseName = "CourseName" + courseRandom;
	
	private String password = "123qweA@";

	private CanvasUser student;
	private CanvasUser instructor1;
	private CanvasUser instructor2;
	private CanvasCourseRS course;
	
	private CanvasUserEnrollmentRS studentEnrollment;
	private CanvasUserEnrollmentRS instructor1Enrollment;
	private CanvasUserEnrollmentRS instructor2Enrollment;
	
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
	private String commentForStudent = "comment_" + getRandomString();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent = GradebookApplication.getRandomScore();
	
	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	private CanvasHomeScreen canvasHomeScreen;
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;
	private MhCampusInstanceDashboardScreen mhCampusInstanceDashboardScreen;

	private static final String CANVAS_FRAME = "tool_content";

	private int numOfSlave = 3; 
	
	private InstanceCredentials instance;

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		prepareTestDataInCanvas();

		instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);
		mhCampusInstanceApplication.createCsvFile(FULL_PATH_FILE, Integer.toString(course.getId()));
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
		
		mhCampusInstanceConnectorsScreen.configureCanvasAuthorizationConnector(canvasApplication.canvasTitle, canvasApplication.canvasFqdn,
				canvasApplication.canvasAccessToken, canvasApplication.canvasUserIdOrigin, canvasApplication.canvasCourseIdOrigin,
				canvasApplication.canvasSecureGateway);
		mhCampusInstanceConnectorsScreen.configureCanvasSsoLinkConnectorWithCsvFilter(canvasApplication.canvasTitle,
				canvasApplication.canvasFqdn, canvasApplication.canvasAccessToken, canvasApplication.canvasInterlinkType,
				canvasApplication.canvasUserIdOrigin, canvasApplication.canvasCourseIdOrigin, canvasApplication.canvasSecureGateway,
				FULL_PATH_FILE);
		mhCampusInstanceConnectorsScreen.configureCanvasGradebookConnector(canvasApplication.canvasTitle, canvasApplication.canvasFqdn,
				canvasApplication.canvasAccessToken, canvasApplication.canvasUserIdOrigin, canvasApplication.canvasCourseIdOrigin);
		mhCampusInstanceDashboardScreen = mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		mhCampusInstanceDashboardScreen.useLevelTolkenInCanvas(true);
		
		browser.pause(mhCampusInstanceApplication.CREATE_NEW_INSTANCE_TIMEOUT);
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearCanvasData();
	}
	
	@Test(description = "Check submitting ScorableItem and TestScore forms WITH Token and WITH Connector under NOT ILT accepted state: Successfull")
	public void checkSubmittingFormsWithTokenAndWithConnectorNotIltAcceptedSuccessfull() throws Exception {
		
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin1, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForCsvFilter(false);

		mhCampusIntroductionScreen.acceptRules(CANVAS_FRAME);
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), instructorName1.toUpperCase(), "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName, CANVAS_FRAME), "Course" + courseName + " is absent");
		
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(instance.customerNumber, providerId,
				Integer.toString(course.getId()), assignmentId, assignmentTitle, category, description, startDate, dueToDate, scoreType,
				scorePossible, isStudentViewable, isIncludedInGrade, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScoreItems form submit failed");
		
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, providerId,
				Integer.toString(course.getId()), assignmentId, Integer.toString(student.getId()), commentForStudent, dateSubmitted,
				scoreReceivedForStudent, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
	}
	
	@Test(description = "Check submitting ScorableItem and TestScore forms WITH Token and WITHOUT Connector under NOT ILT accepted state: Failure", 
			dependsOnMethods = { "checkSubmittingFormsWithTokenAndWithConnectorNotIltAcceptedSuccessfull" })
	public void checkSubmittingFormsWithTokenAndWitOutConnectorNotIltAcceptedFailure() throws Exception {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		//WITHOUT Connector
		mhCampusInstanceConnectorsScreen.clearTokenFromCanvasGradebookConnector();
		
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin1, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		//WITH Token, but NOT ILT accepted
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForCsvFilter(false);

		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), instructorName1.toUpperCase(), "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName, CANVAS_FRAME), "Course" + courseName + " is absent");
	    
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(instance.customerNumber, providerId,
				Integer.toString(course.getId()), assignmentId, assignmentTitle, category, description, startDate, dueToDate, scoreType,
				scorePossible, isStudentViewable, isIncludedInGrade, canvasApplication.tegrityServiceLocation);

		Assert.assertFalse(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScoreItems form submit passed");
		
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, providerId,
				Integer.toString(course.getId()), assignmentId, Integer.toString(student.getId()), commentForStudent, dateSubmitted,
				scoreReceivedForStudent, canvasApplication.tegrityServiceLocation);

		Assert.assertFalse(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit passed");
	}
	
	@Test(description = "Check submitting ScorableItem and TestScore forms WITH OUT Token and WITH OUT Connector under NOT ILT accepted state: Failure", 
			dependsOnMethods = { "checkSubmittingFormsWithTokenAndWitOutConnectorNotIltAcceptedFailure" })
	public void checkSubmittingFormsWithOutTokenAndWitOutConnectorNotIltAcceptedFailure() throws Exception {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceDashboardScreen = mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		//WITH OUT Token
		mhCampusInstanceDashboardScreen.useLevelTolkenInCanvas(false);
		browser.pause(mhCampusInstanceApplication.CREATE_NEW_INSTANCE_TIMEOUT);
		
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin1, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		//NOT ILT accepted
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLink();

		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), instructorName1.toUpperCase(), "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName, CANVAS_FRAME), "Course" + courseName + " is absent");
		
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(instance.customerNumber, providerId,
				Integer.toString(course.getId()), assignmentId, assignmentTitle, category, description, startDate, dueToDate, scoreType,
				scorePossible, isStudentViewable, isIncludedInGrade, canvasApplication.tegrityServiceLocation);

		Assert.assertFalse(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScoreItems form submit passed");
		
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, providerId,
				Integer.toString(course.getId()), assignmentId, Integer.toString(student.getId()), commentForStudent, dateSubmitted,
				scoreReceivedForStudent, canvasApplication.tegrityServiceLocation);

		Assert.assertFalse(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit passed");
	}
	
	@Test(description = "Check submitting ScorableItem and TestScore forms WITH Token and WITH OUT Connector under ILT accepted state: Successfull", 
			dependsOnMethods = { "checkSubmittingFormsWithOutTokenAndWitOutConnectorNotIltAcceptedFailure" })
	public void checkSubmittingFormsWithTokenAndWitOutConnectorIltAcceptedSuccessfull() throws Exception {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceDashboardScreen = mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		//WITH OUT Token
		mhCampusInstanceDashboardScreen.useLevelTolkenInCanvas(true);
		browser.pause(mhCampusInstanceApplication.CREATE_NEW_INSTANCE_TIMEOUT);
		
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin2, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		//NOT ILT accepted
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForCsvFilter(true);

		mhCampusIntroductionScreen.acceptRules(CANVAS_FRAME);
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), instructorName2.toUpperCase(), "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName, CANVAS_FRAME), "Course" + courseName + " is absent");
		
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(instance.customerNumber, providerId,
				Integer.toString(course.getId()), assignmentId, assignmentTitle, category, description, startDate, dueToDate, scoreType,
				scorePossible, isStudentViewable, isIncludedInGrade, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScoreItems form submit failed");
		
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, providerId,
				Integer.toString(course.getId()), assignmentId, Integer.toString(student.getId()), commentForStudent, dateSubmitted,
				scoreReceivedForStudent, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
	}
	
	@Test(description = "Check submitting ScorableItem and TestScore forms WITH OUT Token and WITH OUT Connector under ILT accepted state: Failure", 
			dependsOnMethods = { "checkSubmittingFormsWithTokenAndWitOutConnectorIltAcceptedSuccessfull" })
	public void checkSubmittingFormsWithOutTokenAndWitOutConnectorIltAcceptedFailure() throws Exception {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceDashboardScreen = mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		//WITH OUT Token
		mhCampusInstanceDashboardScreen.useLevelTolkenInCanvas(false);
		browser.pause(mhCampusInstanceApplication.CREATE_NEW_INSTANCE_TIMEOUT);
		
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin1, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		//NOT ILT accepted
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLink();

		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), instructorName1.toUpperCase(), "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName, CANVAS_FRAME), "Course" + courseName + " is absent");
		
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(instance.customerNumber, providerId,
				Integer.toString(course.getId()), assignmentId, assignmentTitle, category, description, startDate, dueToDate, scoreType,
				scorePossible, isStudentViewable, isIncludedInGrade, canvasApplication.tegrityServiceLocation);

		Assert.assertFalse(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScoreItems form submit passed");
		
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, providerId,
				Integer.toString(course.getId()), assignmentId, Integer.toString(student.getId()), commentForStudent, dateSubmitted,
				scoreReceivedForStudent, canvasApplication.tegrityServiceLocation);

		Assert.assertFalse(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit passed");
	}

	private void prepareTestDataInCanvas() throws Exception {

		student = canvasApiUtils.createUser(studentLogin, password, studentName);
		instructor1 = canvasApiUtils.createUser(instructorLogin1, password, instructorName1);
		instructor2 = canvasApiUtils.createUser(instructorLogin2, password, instructorName2);	
		course = canvasApiUtils.createPublishedCourse(courseName);
		
		studentEnrollment = canvasApiUtils.enrollToCourseAsActiveStudent(student, course);		
		instructor1Enrollment = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor1, course);			
		instructor2Enrollment = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor2, course);	
	}

	private void clearCanvasData() throws Exception {

		if((studentEnrollment != null) & (course != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment, course);
		
		if((instructor1Enrollment != null) & (course != null))
		    canvasApiUtils.deleteEnrollment(instructor1Enrollment, course);		
		
		if((instructor2Enrollment != null) & (course != null))
		    canvasApiUtils.deleteEnrollment(instructor2Enrollment, course);		
		
		if(course != null)
			canvasApiUtils.deleteCourse(course);
			
		if(student != null) {
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(student);
			canvasApiUtils.deleteUser(studentToDelete);
		}	
		if(instructor1 != null) {
			CanvasUserRS teacherToDelete = new CanvasUserRS();
			teacherToDelete.setUser(instructor1);
			canvasApiUtils.deleteUser(teacherToDelete);
		}
		if(instructor2 != null) {
			CanvasUserRS teacherToDelete = new CanvasUserRS();
			teacherToDelete.setUser(instructor2);
			canvasApiUtils.deleteUser(teacherToDelete);
		}	
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}
	
	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}
}