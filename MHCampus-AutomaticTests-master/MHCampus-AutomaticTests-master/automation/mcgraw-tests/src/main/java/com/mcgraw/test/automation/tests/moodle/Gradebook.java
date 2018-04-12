package com.mcgraw.test.automation.tests.moodle;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleCategoryRS;
import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleCourseRS;
import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleUserRS;
import com.mcgraw.test.automation.api.rest.moodle.service.MoodleApiUtils;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.applications.MoodleApplication;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.moodle.MoodleCourseDetailsScreen;
import com.mcgraw.test.automation.ui.moodle.MoodleGradesScreen;
import com.mcgraw.test.automation.ui.moodle.MoodleGradesScreenOldVer;
import com.mcgraw.test.automation.ui.moodle.MoodleHomeScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class Gradebook extends BaseTest {

	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	private MoodleApplication moodleApplication;

	private String studentRandom = getRandomString(6);
	private String instructorRandom = getRandomString(6);
	private String courseRandom = getRandomString(5);

	private String studentLogin1 = "student" + studentRandom;
	private String studentName1 = "StudentName" + studentRandom;
	private String studentSurname1 = "StudentSurname" + studentRandom;

	private String studentLogin2 = "student2" + studentRandom;
	private String studentName2 = "StudentName2" + studentRandom;
	private String studentSurname2 = "StudentSurname2" + studentRandom;

	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String instructorSurname = "InstructorSurname" + instructorRandom;

	private String category1 = "Category1" + courseRandom;
	private String courseFullName1 = "CourseFull1" + courseRandom;
	private String courseShortName1 = "CourseShort1" + courseRandom;

	private String category2 = "Category2" + courseRandom;
	private String courseFullName2 = "CourseFull2" + courseRandom;
	private String courseShortName2 = "CourseShort2" + courseRandom;

	private String password = "123qweA@";

	private String providerId = "provider_" + getRandomString(5);
	private String description = "description_" + getRandomString(5);
	private String assignmentTitle = "title_" + getRandomString(5);
	private String commentForStudent1 = "comment_" + getRandomString(5);
	private String commentForStudent2 = "comment_" + getRandomString(5);
	private String assignmentId = getRandomNumber();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent1 = GradebookApplication.getRandomScore();
	private String scoreReceivedForStudent2 = GradebookApplication.getRandomScore();
	private String scorePossible = "101";
	private String categoryType = "Blog";
	private String scoreType = "Percentage";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;

	MoodleHomeScreen moodleHomeScreen;
	MoodleCourseDetailsScreen moodleCourseDetailsScreen;
	MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	MoodleGradesScreen moodleGradesScreen;
	MoodleGradesScreenOldVer moodleGradesScreenOldVer;

	@Autowired
	private MoodleApiUtils moodleApiUtils;

	private MoodleUserRS studentRS1;
	private MoodleUserRS studentRS2;
	private MoodleUserRS instructorRS;
	private MoodleCourseRS courseRS1;
	private MoodleCategoryRS categoryRS1;
	private MoodleCourseRS courseRS2;
	private MoodleCategoryRS categoryRS2;

	private int numOfSlave = 4;
	
	private InstanceCredentials instance;
	
	private int moodleVersion;

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		prepareDataInMoodle();
		
		instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);
		
		moodleApplication.completeMhCampusSetupWithMoodle(instance.customerNumber, instance.sharedSecret);
				
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
		mhCampusInstanceConnectorsScreen.configureMoodleGradebookConnector(moodleApplication.moodleGradebookExtendedProperties);
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		
		moodleVersion = moodleApplication.getMoodleVersion();
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		
		if(categoryRS1 != null)
			moodleApiUtils.deleteCategoryWithCourses(categoryRS1);
		if(categoryRS2 != null)
			moodleApiUtils.deleteCategoryWithCourses(categoryRS2);
		if(studentRS1 != null)
			moodleApiUtils.deleteUser(studentRS1);
		if(studentRS2 != null)
			moodleApiUtils.deleteUser(studentRS2);
		if(instructorRS != null)
			moodleApiUtils.deleteUser(instructorRS);
	}

	@Test(description = "Check TestScorableItem form is submitted successfully")
	public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(instance.customerNumber, providerId,
				courseRS1.getId(), assignmentId, assignmentTitle, categoryType, description, startDate, dueToDate, scoreType,
				scorePossible, isStudentViewable, isIncludedInGrade, gradebookApplication.tegrityServiceLocation);
		testScoreItemsScreen.submitForm();

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForMoodle(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = { "checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent1() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, providerId, courseRS1.getId(),
				assignmentId, studentLogin1, commentForStudent1, dateSubmitted, scoreReceivedForStudent1,
				gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForMoodle(), "TestScore form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = { "checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent2() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, providerId, courseRS1.getId(),
				assignmentId, studentLogin2, commentForStudent2, dateSubmitted, scoreReceivedForStudent2,
				gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForMoodle(), "TestScore form submit failed");
	}

	@Test(description = "Check Gradebook data related to the first Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" }, priority = 0)
	public void checkGradebookItemsAreCorrectForStudent1Course1() throws Exception {

		moodleHomeScreen = moodleApplication.loginToMoodle(studentLogin1, password);
		moodleCourseDetailsScreen = moodleHomeScreen.goToCreatedCourse(courseFullName1);
		
		// check the version of Moodle
		if(moodleVersion < 260){
			moodleGradesScreen = moodleCourseDetailsScreen.clickGradesLink();
	
			// check the first student has correct data
			Assert.verifyEquals(moodleGradesScreen.getCourseName(), courseFullName1, "Course full name did not match");
			Assert.verifyEquals(moodleGradesScreen.getCategoryType(), categoryType, "Category did not match");
			Assert.verifyEquals(moodleGradesScreen.getAssignmentTitle(), assignmentTitle, "Assignment title did not match");
			Assert.verifyEquals(moodleGradesScreen.getScorePossible(), scorePossible, "Score possible did not match");
			Assert.verifyEquals(moodleGradesScreen.getFeedback(), commentForStudent1, "Comment did not match");
			Assert.verifyEquals(moodleGradesScreen.getScoreReceived(), scoreReceivedForStudent1, "ScoreReceived did not match"); 
			// check the first student doesn't have the data from second student
			Assert.verifyEquals(moodleGradesScreen.getCountOfAssignment(), 1, "Amount of assignments for student " + studentLogin1
					+ " is incorrect");
			Assert.verifyEquals(moodleGradesScreen.getCountOfCategoryTypes(), 1, "Amount of categories for student " + studentLogin1
					+ " is incorrect");
		}else{
			moodleGradesScreenOldVer = moodleCourseDetailsScreen.clickGradesLink(moodleVersion);
			
			// check the first student has correct data
			Assert.verifyEquals(moodleGradesScreenOldVer.getCourseName(), courseFullName1, "Course full name did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getCategoryType(), categoryType, "Category did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getAssignmentTitle(), assignmentTitle, "Assignment title did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getScorePossible(), scorePossible, "Score possible did not match");
			//Assert.verifyEquals(moodleGradesScreenOldVer.getFeedback(), commentForStudent1, "Comment did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getScoreReceived(), scoreReceivedForStudent1, "ScoreReceived did not match"); 
			// check the first student doesn't have the data from second student
			Assert.verifyEquals(moodleGradesScreenOldVer.getCountOfAssignment(), 1, "Amount of assignments for student " + studentLogin1
					+ " is incorrect");
			Assert.verifyEquals(moodleGradesScreenOldVer.getCountOfCategoryTypes(), 1, "Amount of categories for student " + studentLogin1
					+ " is incorrect");
		}
	}
	
	@Test(description = "Check Gradebook data related to the second Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" }, priority = 0)
	public void checkGradebookItemsAreCorrectForStudent2Course1() throws Exception {

		moodleHomeScreen = moodleApplication.loginToMoodle(studentLogin2, password);
		moodleCourseDetailsScreen = moodleHomeScreen.goToCreatedCourse(courseFullName1);
		
		// check the version of Moodle
		if(moodleVersion < 260){
			moodleGradesScreen = moodleCourseDetailsScreen.clickGradesLink();
	
			// check the second student has correct data
			Assert.verifyEquals(moodleGradesScreen.getCourseName(), courseFullName1, "Course full name did not match");
			Assert.verifyEquals(moodleGradesScreen.getCategoryType(), categoryType, "Category did not match");
			Assert.verifyEquals(moodleGradesScreen.getAssignmentTitle(), assignmentTitle, "Assignment title did not match");
			Assert.verifyEquals(moodleGradesScreen.getScorePossible(), scorePossible, "Score possible did not match");
			Assert.verifyEquals(moodleGradesScreen.getFeedback(), commentForStudent2, "Comment did not match");
			Assert.verifyEquals(moodleGradesScreen.getScoreReceived(), scoreReceivedForStudent2, "ScoreReceived did not match");
			// check the second student doesn't have the data from first student
			Assert.verifyEquals(moodleGradesScreen.getCountOfAssignment(), 1, "Amount of assignments for student " + studentLogin2
					+ " is incorrect");
			Assert.verifyEquals(moodleGradesScreen.getCountOfCategoryTypes(), 1, "Amount of categories for student " + studentLogin2
					+ " is incorrect");
		}else{
			moodleGradesScreenOldVer = moodleCourseDetailsScreen.clickGradesLink(moodleVersion);
			
			// check the second student has correct data
			Assert.verifyEquals(moodleGradesScreenOldVer.getCourseName(), courseFullName1, "Course full name did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getCategoryType(), categoryType, "Category did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getAssignmentTitle(), assignmentTitle, "Assignment title did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getScorePossible(), scorePossible, "Score possible did not match");
			//Assert.verifyEquals(moodleGradesScreenOldVer.getFeedback(), commentForStudent2, "Comment did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getScoreReceived(), scoreReceivedForStudent2, "ScoreReceived did not match");
			// check the second student doesn't have the data from first student
			Assert.verifyEquals(moodleGradesScreenOldVer.getCountOfAssignment(), 1, "Amount of assignments for student " + studentLogin2
					+ " is incorrect");
			Assert.verifyEquals(moodleGradesScreenOldVer.getCountOfCategoryTypes(), 1, "Amount of categories for student " + studentLogin2
					+ " is incorrect");
		}
	}

	@Test(description = "Check sent Gradebook items are NOT present in the Moodle unused course for the first Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" }, priority = 2)
	public void checkGradebookItemsAreNotPresentForStudent1Course2() throws Exception {

		moodleHomeScreen = moodleApplication.loginToMoodle(studentLogin1, password);
		moodleCourseDetailsScreen = moodleHomeScreen.goToCreatedCourse(courseFullName2);
		
		// check the version of Moodle
		if(moodleVersion < 260){
			moodleGradesScreen = moodleCourseDetailsScreen.clickGradesLink();
			
			Assert.verifyEquals(moodleGradesScreen.getCountOfAssignment(), 0, "Amount of assignments is incorrect");
			Assert.verifyEquals(moodleGradesScreen.getCountOfCategoryTypes(), 0, "Amount of categories is incorrect");
		}else{
			moodleGradesScreenOldVer = moodleCourseDetailsScreen.clickGradesLink(moodleVersion);
			
			Assert.verifyEquals(moodleGradesScreenOldVer.getCountOfAssignment(), 0, "Amount of assignments is incorrect");
			Assert.verifyEquals(moodleGradesScreenOldVer.getCountOfCategoryTypes(), 0, "Amount of categories is incorrect");
		}
	}
	
	@Test(description = "Check sent Gradebook items are NOT present in the Moodle unused course for the second Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" }, priority = 2)
	public void checkGradebookItemsAreNotPresentForStudent2Course2() throws Exception {

		moodleHomeScreen = moodleApplication.loginToMoodle(studentLogin2, password);
		moodleCourseDetailsScreen = moodleHomeScreen.goToCreatedCourse(courseFullName2);
		
		// check the version of Moodle
		if(moodleVersion < 260){
			moodleGradesScreen = moodleCourseDetailsScreen.clickGradesLink();
			
			Assert.verifyEquals(moodleGradesScreen.getCountOfAssignment(), 0, "Amount of assignments is incorrect");
			Assert.verifyEquals(moodleGradesScreen.getCountOfCategoryTypes(), 0, "Amount of categories is incorrect");
		}else{
			moodleGradesScreenOldVer = moodleCourseDetailsScreen.clickGradesLink(moodleVersion);
			
			Assert.verifyEquals(moodleGradesScreenOldVer.getCountOfAssignment(), 0, "Amount of assignments is incorrect");
			Assert.verifyEquals(moodleGradesScreenOldVer.getCountOfCategoryTypes(), 0, "Amount of categories is incorrect");
		}
	}

	private String getRandomString(int count) {
		return RandomStringUtils.randomNumeric(count);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}
 
	public void prepareDataInMoodle() throws Exception {
		studentRS1 = moodleApiUtils.createUser(studentLogin1, password, studentName1, studentSurname1);
		studentRS2 = moodleApiUtils.createUser(studentLogin2, password, studentName2, studentSurname2);
		instructorRS = moodleApiUtils.createUser(instructorLogin, password, instructorName, instructorSurname);

		categoryRS1 = moodleApiUtils.createCategory(category1);
		courseRS1 = moodleApiUtils.createCourseInsideCategory(courseFullName1, courseShortName1, categoryRS1);
		categoryRS2 = moodleApiUtils.createCategory(category2);
		courseRS2 = moodleApiUtils.createCourseInsideCategory(courseFullName2, courseShortName2, categoryRS2);

		moodleApiUtils.enrollToCourseAsStudent(studentRS1, courseRS1);
		moodleApiUtils.enrollToCourseAsStudent(studentRS2, courseRS1);
		moodleApiUtils.enrollToCourseAsInstructor(instructorRS, courseRS1);
		moodleApiUtils.enrollToCourseAsStudent(studentRS1, courseRS2);
		moodleApiUtils.enrollToCourseAsStudent(studentRS2, courseRS2);
		moodleApiUtils.enrollToCourseAsInstructor(instructorRS, courseRS2);
	}
}
