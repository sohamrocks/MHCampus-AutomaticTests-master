package com.mcgraw.test.automation.tests.angel;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.angel.AngelSectionRole;
import com.mcgraw.test.automation.api.rest.angel.model.AddSectionModel;
import com.mcgraw.test.automation.api.rest.angel.model.AddUserModel;
import com.mcgraw.test.automation.api.rest.angel.service.AngelApiUtils;
import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.angel.AngelGradesDetailsScreen;
import com.mcgraw.test.automation.ui.angel.AngelHomeScreen;
import com.mcgraw.test.automation.ui.angel.course.AngelCourseContext.TabMenuItem;
import com.mcgraw.test.automation.ui.angel.course.AngelCourseReportScreen;
import com.mcgraw.test.automation.ui.applications.AngelApplication;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class Gradebook extends BaseTest {

	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	private AngelApplication angelApplication;

	@Autowired
	private AngelApiUtils angelApiUtils;

	private String password = "123qweA@";

	private AddUserModel instructor;
	private String instructorUserName;
	private String instructorFirstname;
	private String instructorLastname;

	private AddUserModel student1;
	private String student1UserName;
	private String student1Firstname;
	private String student1Lastname;

	private AddUserModel student2;
	private String student2UserName;
	private String student2Firstname;
	private String student2Lastname;

	private AddSectionModel course1;
	private String course1Id;
	private String course1Name;

	private AddSectionModel course2;
	private String course2Id;
	private String course2Name;

	private int numOfSlave = 2;
	
	private InstanceCredentials instance;

	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	private AngelHomeScreen angelHomeScreen;
	private AngelCourseReportScreen angelCourseReportScreen;
	private AngelGradesDetailsScreen angelGradesDetailsScreen;

	private String providerId = "provider_" + getRandomString();
	private String description = "description_" + getRandomString();
	private String assignmentTitle = "title_" + getRandomString();
	private String commentForStudent1 = "comment_" + getRandomString();
	private String commentForStudent2 = "comment_" + getRandomString();
	private String assignmentId = getRandomNumber();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent1 = GradebookApplication.getRandomScore();
	private String scoreReceivedForStudent2 = GradebookApplication.getRandomScore();
	private String scorePossible = "100";
	private String categoryType = "Test";
	private String scoreType = "Percentage";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = true;

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);

		angelApplication.completeMhCampusSetupWithAngel(instance.sharedSecret, instance.customerNumber);
		prepareTestDataInAngel();

		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
		mhCampusInstanceConnectorsScreen.configureAngelGradebookConnector(angelApplication.angelTitle,
				angelApplication.angelGradeBookServiceUrl, angelApplication.angelGradebookExtendedProperties);
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		
		try{
			if(course1Id != null)
				angelApplication.deleteCourseWithEnrollUsers(course1Id);
			if(student1UserName != null)
				angelApplication.deleteUserFromAngel(student1UserName);
			if(student2UserName != null)
				angelApplication.deleteUserFromAngel(student2UserName);
			if(instructorUserName != null)
				angelApplication.deleteUserFromAngel(instructorUserName);
		}catch(Exception e){
			Logger.info("Error happens during deliting users/courses from Angel LMS...");
		}
	}

	@Test(description = "Check TestScorableItem form is submitted successfully")
	public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(instance.customerNumber, providerId,
				course1Id, assignmentId, assignmentTitle, categoryType, description, startDate, dueToDate, scoreType, scorePossible,
				isStudentViewable, isIncludedInGrade, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForAngel(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = { "checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent1() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, providerId, course1Id,
				assignmentId, student1UserName, commentForStudent1, dateSubmitted, scoreReceivedForStudent1,
				gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForAngel(), "TestScore form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = { "checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent2() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, providerId, course1Id,
				assignmentId, student2UserName, commentForStudent2, dateSubmitted, scoreReceivedForStudent2,
				gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForAngel(), "TestScore form submit failed");
	}

	@Test(description = "Check Gradebook items are correct for first student first course", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" })
	public void checkGradebookItemsAreCorrectForStudent1Course1() throws Exception {

		angelHomeScreen = angelApplication.loginToAngel(student1UserName, password);
		angelCourseReportScreen = angelHomeScreen.setCourseContext(course1Name, TabMenuItem.Report);
		angelCourseReportScreen.chooseReportSettingField();
		angelCourseReportScreen.selectReportCategory("Grades");
		angelCourseReportScreen.selectUserCategory(student1Lastname + "," + student1Firstname);
		angelGradesDetailsScreen = angelCourseReportScreen.clickRun();

		Assert.verifyEquals(angelGradesDetailsScreen.getCountOfAssignment(), 1, "Amount of assignments for is incorrect");
		Assert.verifyEquals(angelGradesDetailsScreen.getAssignmentTitle(), assignmentTitle, "Assignment title did not match");
		Assert.verifyEquals(angelGradesDetailsScreen.getScorePossible(), scorePossible, "Score possible did not match");
		Assert.verifyEquals(angelGradesDetailsScreen.getScoreReceived(), scoreReceivedForStudent1, "Score received did not match");
	}

	@Test(description = "Check Gradebook items are correct for first student second course", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" })
	public void checkGradebookItemsAreNotPresentForStudent1Course2() throws Exception {

		angelHomeScreen = angelApplication.loginToAngel(student1UserName, password);
		angelCourseReportScreen = angelHomeScreen.setCourseContext(course2Name, TabMenuItem.Report);
		angelCourseReportScreen.chooseReportSettingField();
		angelCourseReportScreen.selectReportCategory("Grades");
		angelCourseReportScreen.selectUserCategory(student1Lastname + "," + student1Firstname);
		angelGradesDetailsScreen = angelCourseReportScreen.clickRun();

		Assert.verifyEquals(angelGradesDetailsScreen.getCountOfAssignment(), 0, "Amount of assignments is incorrect");
	}
	
	@Test(description = "Check Gradebook items are correct for second student first course", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" })
	public void checkGradebookItemsAreCorrectForStudent2Course1() throws Exception {

		angelHomeScreen = angelApplication.loginToAngel(student2UserName, password);
		angelCourseReportScreen = angelHomeScreen.setCourseContext(course1Name, TabMenuItem.Report);
		angelCourseReportScreen.chooseReportSettingField();
		angelCourseReportScreen.selectReportCategory("Grades");
		angelCourseReportScreen.selectUserCategory(student2Lastname + "," + student2Firstname);
		angelGradesDetailsScreen = angelCourseReportScreen.clickRun();

		Assert.verifyEquals(angelGradesDetailsScreen.getCountOfAssignment(), 1, "Amount of assignments is incorrect");		
		Assert.verifyEquals(angelGradesDetailsScreen.getAssignmentTitle(), assignmentTitle, "Assignment title did not match");
		Assert.verifyEquals(angelGradesDetailsScreen.getScorePossible(), scorePossible, "Score possible did not match");
		Assert.verifyEquals(angelGradesDetailsScreen.getScoreReceived(), scoreReceivedForStudent2, "Score received did not match");		
	}
	
	@Test(description = "Check Gradebook items are correct for second sudent second course", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" })
	public void checkGradebookItemsAreNotPresentForStudent2Course2() throws Exception {

		angelHomeScreen = angelApplication.loginToAngel(student2UserName, password);
		angelCourseReportScreen = angelHomeScreen.setCourseContext(course2Name, TabMenuItem.Report);
		angelCourseReportScreen.chooseReportSettingField();
		angelCourseReportScreen.selectReportCategory("Grades");
		angelCourseReportScreen.selectUserCategory(student2Lastname + "," + student2Firstname);
		angelGradesDetailsScreen = angelCourseReportScreen.clickRun();

		Assert.verifyEquals(angelGradesDetailsScreen.getCountOfAssignment(), 0, "Amount of assignments is incorrect");
	}	

	private String getRandomString() {
		return RandomStringUtils.randomNumeric(5);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}

	private void prepareTestDataInAngel() throws RestEndpointIOException, UnsupportedEncodingException {
		String instructorRandom = getRandomString();
		String student1Random = getRandomString();
		String student2Random = getRandomString();
		String course1Random = getRandomString();
		String course2Random = getRandomString();

		instructorUserName = "instructor" + instructorRandom;
		instructorFirstname = "instructorFirstname" + instructorRandom;
		instructorLastname = "instructorLastname" + instructorRandom;

		student1UserName = "student" + student1Random;
		student1Firstname = "studentFirstname" + student1Random;
		student1Lastname = "studentLastname" + student1Random;

		student2UserName = "student" + student2Random;
		student2Firstname = "studentFirstname" + student2Random;
		student2Lastname = "studentLastname" + student2Random;

		course1Id = "id" + course1Random;
		course1Name = "courseName" + course1Random;

		course2Id = "id" + course2Random;
		course2Name = "courseName" + course2Random;

		student1 = angelApiUtils.createStudent(student1UserName, password, student1Firstname, student1Lastname);
		student2 = angelApiUtils.createStudent(student2UserName, password, student2Firstname, student2Lastname);
		instructor = angelApiUtils.createInstructor(instructorUserName, password, instructorFirstname, instructorLastname);
		course1 = angelApiUtils.createCourse(instructor, course1Id, course1Name);
		course2 = angelApiUtils.createCourse(instructor, course2Id, course2Name);
		
		angelApiUtils.enrolUserToCourse(student1, course1, AngelSectionRole.STUDENT);
		angelApiUtils.enrolUserToCourse(student1, course2, AngelSectionRole.STUDENT);
		
		angelApiUtils.enrolUserToCourse(student2, course1, AngelSectionRole.STUDENT);
		angelApiUtils.enrolUserToCourse(student2, course2, AngelSectionRole.STUDENT);			
	}
}
