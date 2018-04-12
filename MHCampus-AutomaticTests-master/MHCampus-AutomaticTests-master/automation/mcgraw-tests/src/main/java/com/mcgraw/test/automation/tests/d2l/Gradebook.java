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
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class Gradebook extends BaseTest {

	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	private D2LApplication d2lApplication;

	private String assignmentId = getRandomNumber();
	private String assignmentTitle = "title_" + getRandomString();
	private String category = "category_" + getRandomString();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String scoreType = "Percentage";
	private String scorePossible = "100";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;
	private String commentForStudent1 = "comment_" + getRandomString();
	private String commentForStudent2 = "comment_" + getRandomString();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent1 = GradebookApplication.getRandomScore();
	private String scoreReceivedForStudent2 = GradebookApplication.getRandomScore();

	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	private D2lHomeScreen d2lHomeScreen;
	private D2lCourseDetailsScreen d2lCourseDetailsScreen;
	private D2lGradesDetailsScreen d2lGradesDetailsScreen;

	private String studentRandom = getRandomString();
	private String studentLogin1 = "student" + studentRandom;
	private String studentName1 = "StudentName" + studentRandom;
	private String studentSurname1 = "StudentSurname" + studentRandom;
	private String studentLogin2 = "student2" + studentRandom;
	private String studentName2 = "StudentName2" + studentRandom;
	private String studentSurname2 = "StudentSurname2" + studentRandom;

	private String instructorRandom = getRandomString();
	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String instructorSurname = "InstructorSurame" + instructorRandom;

	private String password = "123qweA@";

	private String courseName1 = "CourseName1" + getRandomString();
	private String courseName2 = "CourseName2" + getRandomString();
	private String courseCode;

	@Autowired
	private D2LApiUtils d2LApiUtils;

	private D2LUserRS studentRS1;
	private D2LUserRS studentRS2;
	private D2LUserRS instructorRS;
	private D2LCourseTemplateRS courseTemplateRS1;
	private D2LCourseOfferingRS courseOfferingRS1;
	private D2LCourseTemplateRS courseTemplateRS2;
	private D2LCourseOfferingRS courseOfferingRS2;

	private int numOfSlave = 3;
	
	private InstanceCredentials instance;

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		prepareTestDataInD2l();

		if(d2lApplication.d2lBaseUrl.equals("https://tegrity.desire2learn.com"))
			numOfSlave = 1;
		instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);

		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
		mhCampusInstanceConnectorsScreen.configureCustomGradebookConnector(d2lApplication.d2lTitle, d2lApplication.d2lGradebookServiceUrl,
				d2lApplication.d2lGradebookExtendedPropertiesNone);
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearD2lData();
	}

	@Test(description = "Check TestScorableItem form is submitted successfully")
	public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {
		
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(instance.customerNumber, "", courseCode,
				assignmentId, assignmentTitle, category, "", startDate, dueToDate, scoreType, scorePossible, isStudentViewable,
				isIncludedInGrade, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForD2l(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = { "checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent1() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, "", courseCode, assignmentId,
				studentLogin1, commentForStudent1, dateSubmitted, scoreReceivedForStudent1, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForD2l(), "TestScore form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = { "checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent2() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, "", courseCode, assignmentId,
				studentLogin2, commentForStudent2, dateSubmitted, scoreReceivedForStudent2, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForD2l(), "TestScore form submit failed");
	}

	@Test(description = "Check Gradebook data related to the first Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" }, priority = 0)
	public void checkGradebookItemsAreCorrectForStudent1Course1() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin1, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		d2lGradesDetailsScreen = d2lCourseDetailsScreen.clickGradesLink();
		// check the first student doesn't have the data from second student
		Assert.verifyEquals(d2lGradesDetailsScreen.getCountOfAssignments(), 1, "Ammount of assignments of Student " + studentLogin1 + " is incorrect");
		Assert.verifyEquals(d2lGradesDetailsScreen.getCountOfCategory(), 1, "Ammount of categories of Student " + studentLogin1 + " is incorrect");
		// check the first student has correct data
		Assert.verifyEquals(d2lGradesDetailsScreen.getCategory(), category, "Category did not match");
		Assert.verifyEquals(d2lGradesDetailsScreen.getAssignmentTitle(),assignmentTitle, "Assignment title did not match");
		Assert.verifyEquals(d2lGradesDetailsScreen.getScorePossible(), scorePossible,"Score Possible did not match");
		Assert.verifyEquals(d2lGradesDetailsScreen.getScoreReceived(),scoreReceivedForStudent1,"ScoreReceived did not match");
	}
	
	@Test(description = "Check Gradebook data related to the second Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" }, priority = 0)
	public void checkGradebookItemsAreCorrectForStudent2Course1() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin2, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		d2lGradesDetailsScreen = d2lCourseDetailsScreen.clickGradesLink();
		// check the first student doesn't have the data from second student
		Assert.verifyEquals(d2lGradesDetailsScreen.getCountOfAssignments(), 1, "Ammount of assignments of Student " + studentLogin2 + " is incorrect");
		Assert.verifyEquals(d2lGradesDetailsScreen.getCountOfCategory(), 1, "Ammount of categories of Student " + studentLogin2 + " is incorrect");
		// check the first student has correct data
		Assert.verifyEquals(d2lGradesDetailsScreen.getCategory(), category, "Category did not match");
		Assert.verifyEquals(d2lGradesDetailsScreen.getAssignmentTitle(),assignmentTitle, "Assignment title did not match");
		Assert.verifyEquals(d2lGradesDetailsScreen.getScorePossible(), scorePossible,"Score Possible did not match");
		Assert.verifyEquals(d2lGradesDetailsScreen.getScoreReceived(),scoreReceivedForStudent2,"ScoreReceived did not match");
	}

	@Test(description = "Check sent Gradebook items are present in the D2L unused course for the first Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" }, priority = 2)
	public void checkGradebookItemsAreNotPresentForStudent1Course2() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin1, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);
		d2lGradesDetailsScreen = d2lCourseDetailsScreen.clickGradesLink();
		Assert.verifyEquals(d2lGradesDetailsScreen.getCountOfAssignments(), 0, "Assignment is present in wrong course");
		Assert.verifyEquals(d2lGradesDetailsScreen.getCountOfCategory(), 0, "Category is present in wrong course");
	}
	
	@Test(description = "Check sent Gradebook items are present in the D2L unused course for the second Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" }, priority = 2)
	public void checkGradebookItemsAreNotPresentForStudent2Course2() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin2, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);
		d2lGradesDetailsScreen = d2lCourseDetailsScreen.clickGradesLink();
		Assert.verifyEquals(d2lGradesDetailsScreen.getCountOfAssignments(), 0, "Assignment is present in wrong course");
		Assert.verifyEquals(d2lGradesDetailsScreen.getCountOfCategory(), 0, "Category is present in wrong course");
	}

	private void prepareTestDataInD2l() throws Exception {

		studentRS1 = d2LApiUtils.createUser(studentName1, studentSurname1, studentLogin1, password, D2LUserRole.STUDENT);
		studentRS2 = d2LApiUtils.createUser(studentName2, studentSurname2, studentLogin2, password, D2LUserRole.STUDENT);
		instructorRS = d2LApiUtils.createUser(instructorName, instructorSurname, instructorLogin, password, D2LUserRole.INSTRUCTOR);

		courseTemplateRS1 = d2LApiUtils.createCourseTemplate("name" + getRandomString(), "code" + RandomStringUtils.randomNumeric(3));
		courseOfferingRS1 = d2LApiUtils.createCourseOfferingByTemplate(courseTemplateRS1, courseName1,
				"code" + RandomStringUtils.randomNumeric(3));
		courseCode = courseOfferingRS1.getCode();
		courseTemplateRS2 = d2LApiUtils.createCourseTemplate("name" + getRandomString(), "code" + RandomStringUtils.randomNumeric(3));
		courseOfferingRS2 = d2LApiUtils.createCourseOfferingByTemplate(courseTemplateRS2, courseName2,
				"code" + RandomStringUtils.randomNumeric(3));

		d2LApiUtils.createEnrollment(studentRS1, courseOfferingRS1, D2LUserRole.STUDENT);
		d2LApiUtils.createEnrollment(studentRS2, courseOfferingRS1, D2LUserRole.STUDENT);
		d2LApiUtils.createEnrollment(instructorRS, courseOfferingRS1, D2LUserRole.INSTRUCTOR);
		d2LApiUtils.createEnrollment(studentRS1, courseOfferingRS2, D2LUserRole.STUDENT);
		d2LApiUtils.createEnrollment(studentRS2, courseOfferingRS2, D2LUserRole.STUDENT);
		d2LApiUtils.createEnrollment(instructorRS, courseOfferingRS2, D2LUserRole.INSTRUCTOR);
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}
	
	private void clearD2lData() throws Exception {
		
		if(studentRS1 != null)
			d2LApiUtils.deleteUser(studentRS1);
		if(studentRS2 != null)
			d2LApiUtils.deleteUser(studentRS2);
		if(instructorRS != null)
			d2LApiUtils.deleteUser(instructorRS);
		if(courseOfferingRS1 != null)
			d2LApiUtils.deleteCourseOffering(courseOfferingRS1);
		if(courseTemplateRS1 != null)
			d2LApiUtils.deleteCourseTemplate(courseTemplateRS1);
		if(courseOfferingRS2 != null)
			d2LApiUtils.deleteCourseOffering(courseOfferingRS2);
		if(courseTemplateRS2 != null)
			d2LApiUtils.deleteCourseTemplate(courseTemplateRS2);
	}
}
