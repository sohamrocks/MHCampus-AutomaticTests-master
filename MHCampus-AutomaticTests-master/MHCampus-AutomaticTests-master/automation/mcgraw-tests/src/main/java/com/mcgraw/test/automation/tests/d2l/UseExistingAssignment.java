package com.mcgraw.test.automation.tests.d2l; 

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.d2l.D2LUserRole;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCourseOfferingRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCourseTemplateRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LUserRS;
import com.mcgraw.test.automation.api.rest.d2l.service.D2LApiUtils;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.D2LApplication;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.d2l.base.D2lCourseDetailsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lGradesDetailsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lHomeScreen;
import com.mcgraw.test.automation.ui.d2l.v10.D2lGradesDetailsScreenForInstructorV10;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class UseExistingAssignment extends BaseTest {

	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	private D2LApplication d2lApplication;

	private String assignmentId = getRandomNumber();
	private String assignmentIdNew = getRandomNumber();
	private String assignmentTitle = "title_" + getRandomString();
	private String category = "category_" + getRandomString();
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
	
	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	private D2lHomeScreen d2lHomeScreen;
	private D2lCourseDetailsScreen d2lCourseDetailsScreen;
	private D2lGradesDetailsScreen d2lGradesDetailsScreen;
	private D2lGradesDetailsScreenForInstructorV10 d2lGradesDetailsScreenForInstructor;

	private String studentRandom = getRandomString();
	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;
	private String studentSurname = "StudentSurname" + studentRandom;
	private String studentId;

	private String instructorRandom = getRandomString();
	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String instructorSurname = "InstructorSurame" + instructorRandom;

	private String password = "123qweA@";

	private String courseName = "CourseName" + getRandomString();
	private String courseId;

	@Autowired
	private D2LApiUtils d2LApiUtils;

	private D2LUserRS studentRS;
	private D2LUserRS instructorRS;
	private D2LCourseTemplateRS courseTemplateRS;
	private D2LCourseOfferingRS courseOfferingRS;

	private int numOfSlave = 3;
	
	private InstanceCredentials instance;

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		prepareTestDataInD2l();

		instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);

		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
		// configure extend property 'SSOType' to 'lti' and 'AssignmentCreationMode' to'new'
		mhCampusInstanceConnectorsScreen.configureCustomGradebookConnector(d2lApplication.d2lTitle, d2lApplication.d2lGradebookServiceUrl,
				d2lApplication.d2lGradebookExtendedPropertiesNew);
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearD2lData();
	}

	@Test(description = "Check TestScorableItem form is submitted successfully with SOType: New")
	public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {
		
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(instance.customerNumber, "", courseId,
				assignmentId, assignmentTitle, category, "", startDate, dueToDate, scoreType, scorePossible, isStudentViewable,
				isIncludedInGrade, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForD2l(), "TestScoreItems form submit failed");
	}
	
	@Test(description = "Check TestScore form is submitted successfully with SOType: New", dependsOnMethods =
		{ "checkSubmittingTestScorableItemFormIsSuccessfull"})
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, "", courseId, assignmentId,
				studentId, commentForStudent, dateSubmitted, scoreReceivedForStudent, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForD2l(), "TestScore form submit failed");
	}
	
	@Test(description = "Check Gradebook data related to the Instructor", dependsOnMethods = 
		{"checkSubmittingTestScoreFormIsSuccessfullForStudent"})
	public void checkGradebookItemsAreCorrectForInstructorInCourse() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName);
		d2lGradesDetailsScreenForInstructor = d2lCourseDetailsScreen.clickGradesLinkAsInstructor();
		// check the instructor has correct data
		Assert.verifyEquals(d2lGradesDetailsScreenForInstructor.getCategory(), category, "Category did not match");
		Assert.verifyEquals(d2lGradesDetailsScreenForInstructor.getAssignmentTitle(),assignmentTitle, "Assignment title did not match");
		Assert.verifyEquals(d2lGradesDetailsScreenForInstructor.getScorePossible(assignmentTitle), scorePossible,"Score Possible did not match");
		Assert.verifyEquals(d2lGradesDetailsScreenForInstructor.getScoreReceived(assignmentTitle, studentSurname+", "+studentName),scoreReceivedForStudent,"ScoreReceived did not match");
	}

	@Test(description = "Check Gradebook data related to the Student", dependsOnMethods =
		{"checkSubmittingTestScoreFormIsSuccessfullForStudent"})
	public void checkGradebookItemsAreCorrectForStudentInCourse() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName);
		d2lGradesDetailsScreen = d2lCourseDetailsScreen.clickGradesLink();
		
		// check the student has correct data
		Assert.verifyEquals(d2lGradesDetailsScreen.getCategory(), category, "Category did not match");
		Assert.verifyEquals(d2lGradesDetailsScreen.getAssignmentTitle(),assignmentTitle, "Assignment title did not match");
		Assert.verifyEquals(d2lGradesDetailsScreen.getScorePossible(), scorePossible,"Score Possible did not match");
		Assert.verifyEquals(d2lGradesDetailsScreen.getScoreReceived(),scoreReceivedForStudent,"ScoreReceived did not match");
	}
	
	@Test(description = "Check TestScorableItem form is submitted successfully with new assignment ID",
			dependsOnMethods = {"checkGradebookItemsAreCorrectForInstructorInCourse", 
			"checkGradebookItemsAreCorrectForStudentInCourse"})
	public void checkSubmittingTestScorableItemFormWithNewAssignmentIDIsSuccessfull() throws Exception {
		
		// configure extend property 'SSOType' to 'lti' and 'AssignmentCreationMode' to 'exist'
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
		mhCampusInstanceConnectorsScreen.configureCustomGradebookConnector(d2lApplication.d2lTitle, d2lApplication.d2lGradebookServiceUrl,
				d2lApplication.d2lGradebookExtendedPropertiesExist);
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		
		String categoryNew = "TestNew";
		String scorePossibleNew = "102";
		// fill the form with new assignment ID, category and score possible
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(instance.customerNumber, "", courseId,
				assignmentIdNew, assignmentTitle, categoryNew, "", startDate, dueToDate, scoreType, scorePossibleNew, isStudentViewable,
				isIncludedInGrade, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForD2l(), "TestScoreItems form submit failed");
	}
	
	@Test(description = "Check TestScore form is submitted successfully with new assignment ID", dependsOnMethods = 
		{ "checkSubmittingTestScorableItemFormWithNewAssignmentIDIsSuccessfull"})
	public void checkSubmittingTestScoreFormWithNewAssignmentIDIsSuccessfullForStudent() throws Exception {

		// fill the form with new assignment ID and score received for student
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, "", courseId, assignmentIdNew,
				studentId, commentForStudent, dateSubmitted, scoreReceivedForStudentNew, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForD2l(), "TestScore form submit failed");
	}
	
	@Test(description = "Check Gradebook data related to the Instructor", dependsOnMethods = 
		{"checkSubmittingTestScoreFormWithNewAssignmentIDIsSuccessfullForStudent"})
	public void checkAssignmentIdWasChangedAndTitleNotForInstructor() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName);
		d2lGradesDetailsScreenForInstructor = d2lCourseDetailsScreen.clickGradesLinkAsInstructor();
		// check new 'Assignment title' and 'Category' were not created 
		Assert.verifyEquals(d2lGradesDetailsScreenForInstructor.getCountOfAssignments(), 1, "Ammount of assignments of Instructor " + instructorLogin + " is incorrect");
		Assert.verifyEquals(d2lGradesDetailsScreenForInstructor.getCountOfCategory(), 1, "Ammount of categories of Instructor " + instructorLogin + " is incorrect");
		
		// check the instructor has correct data: title, category and score possible was not changed
		Assert.verifyEquals(d2lGradesDetailsScreenForInstructor.getCategory(), category, "Category did not match");
		Assert.verifyEquals(d2lGradesDetailsScreenForInstructor.getAssignmentTitle(),assignmentTitle, "Assignment title did not match");
		Assert.verifyEquals(d2lGradesDetailsScreenForInstructor.getScorePossible(assignmentTitle), scorePossible,"Score Possible did not match");
		// check the score received for student was changed
		Assert.verifyEquals(d2lGradesDetailsScreenForInstructor.getScoreReceived(assignmentTitle, studentSurname+", "+studentName),scoreReceivedForStudentNew,"ScoreReceived did not match");
	}

	@Test(description = "Check Gradebook data related to the Student", dependsOnMethods = 
		{"checkSubmittingTestScoreFormWithNewAssignmentIDIsSuccessfullForStudent"})
	public void checkAssignmentIdWasChangedAndTitleNotForStudent() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName);
		d2lGradesDetailsScreen = d2lCourseDetailsScreen.clickGradesLink();
		// check new 'Assignment title' and 'Category' were not created 
		Assert.verifyEquals(d2lGradesDetailsScreen.getCountOfAssignments(), 1, "Ammount of assignments of Student " + studentLogin + " is incorrect");
		Assert.verifyEquals(d2lGradesDetailsScreen.getCountOfCategory(), 1, "Ammount of categories of Student " + studentLogin + " is incorrect");
		
		// check the instructor has correct data: title, category and score possible was not changed
		// check the student has correct data: title, category and score possible was not changed
		Assert.verifyEquals(d2lGradesDetailsScreen.getCategory(), category, "Category did not match");
		Assert.verifyEquals(d2lGradesDetailsScreen.getAssignmentTitle(),assignmentTitle, "Assignment title did not match");
		Assert.verifyEquals(d2lGradesDetailsScreen.getScorePossible(), scorePossible,"Score Possible did not match");
		// check the score received for student was changed
		Assert.verifyEquals(d2lGradesDetailsScreen.getScoreReceived(),scoreReceivedForStudentNew,"ScoreReceived did not match");
	}
	
	private void prepareTestDataInD2l() throws Exception {

		studentRS = d2LApiUtils.createUser(studentName, studentSurname, studentLogin, password, D2LUserRole.STUDENT);
		studentId = Integer.toString(studentRS.getUserId());
		instructorRS = d2LApiUtils.createUser(instructorName, instructorSurname, instructorLogin, password, D2LUserRole.INSTRUCTOR);

		courseTemplateRS = d2LApiUtils.createCourseTemplate("name" + getRandomString(), "code" + RandomStringUtils.randomNumeric(3));
		courseOfferingRS = d2LApiUtils.createCourseOfferingByTemplate(courseTemplateRS, courseName,
				"code" + RandomStringUtils.randomNumeric(3));
		courseId = Integer.toString(courseOfferingRS.getId());
		
		d2LApiUtils.createEnrollment(studentRS, courseOfferingRS, D2LUserRole.STUDENT);
		d2LApiUtils.createEnrollment(instructorRS, courseOfferingRS, D2LUserRole.INSTRUCTOR);
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}
	
	private void clearD2lData() throws Exception {
		
		if(studentRS != null)
			d2LApiUtils.deleteUser(studentRS);
		if(instructorRS != null)
			d2LApiUtils.deleteUser(instructorRS);
		if(courseOfferingRS != null)
			d2LApiUtils.deleteCourseOffering(courseOfferingRS);
		if(courseTemplateRS != null)
			d2LApiUtils.deleteCourseTemplate(courseTemplateRS);
	}
}
