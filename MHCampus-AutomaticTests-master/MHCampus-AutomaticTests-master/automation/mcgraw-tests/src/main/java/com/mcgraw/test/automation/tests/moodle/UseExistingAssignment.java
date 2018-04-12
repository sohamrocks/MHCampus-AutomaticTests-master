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

public class UseExistingAssignment extends BaseTest {

	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	private MoodleApplication moodleApplication;

	private String studentRandom = getRandomString(6);
	private String instructorRandom = getRandomString(6);
	private String courseRandom = getRandomString(5);

	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;
	private String studentSurname = "StudentSurname" + studentRandom;

	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String instructorSurname = "InstructorSurname" + instructorRandom;

	private String category = "Category1" + courseRandom;
	private String courseFullName = "CourseFull1" + courseRandom;
	private String courseShortName = "CourseShort1" + courseRandom;

	private String password = "123qweA@";

	private String providerId = "provider_" + getRandomString(5);
	private String description = "description_" + getRandomString(5);
	private String assignmentTitle = "title_" + getRandomString(5);
	private String commentForStudent = "comment_" + getRandomString(5);
	private String assignmentId = getRandomNumber();
	private String assignmentIdNew = getRandomNumber();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent = GradebookApplication.getRandomScore();
	private String scoreReceivedForStudentNew = GradebookApplication.getRandomScore();
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

	private MoodleUserRS studentRS;
	private MoodleUserRS instructorRS;
	private MoodleCourseRS courseRS;
	private MoodleCategoryRS categoryRS;
	
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
		// configure extend property 'AssignmentCreationMode' to 'new'
		mhCampusInstanceConnectorsScreen.configureMoodleGradebookConnector(moodleApplication.moodleGradebookExtendedPropertiesNew);
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);
		
		moodleVersion = moodleApplication.getMoodleVersion();
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		
		if(categoryRS != null)
			moodleApiUtils.deleteCategoryWithCourses(categoryRS);
		if(studentRS != null)
			moodleApiUtils.deleteUser(studentRS);
		if(instructorRS != null)
			moodleApiUtils.deleteUser(instructorRS);
	}

	@Test(description = "Check TestScorableItem form is submitted successfully with SOType: New")
	public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(instance.customerNumber, providerId,
				courseRS.getId(), assignmentId, assignmentTitle, categoryType, description, startDate, dueToDate, scoreType,
				scorePossible, isStudentViewable, isIncludedInGrade, gradebookApplication.tegrityServiceLocation);
		testScoreItemsScreen.submitForm();

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForMoodle(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully with SOType: New", dependsOnMethods = { "checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, providerId, courseRS.getId(),
				assignmentId, studentLogin, commentForStudent, dateSubmitted, scoreReceivedForStudent,
				gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForMoodle(), "TestScore form submit failed");
	}
	
	@Test(description = "Check Gradebook data related to the Instructor", dependsOnMethods = {
		"checkSubmittingTestScoreFormIsSuccessfullForStudent"}, priority = 0)
	public void checkGradebookItemsAreCorrectForInstructor() throws Exception {
	
		moodleHomeScreen = moodleApplication.loginToMoodle(instructorLogin, password);
		moodleCourseDetailsScreen = moodleHomeScreen.goToCreatedCourse(courseFullName);
		
		// check the version of Moodle
		if(moodleVersion < 260){
			moodleGradesScreen = moodleCourseDetailsScreen.clickGradesLink();
			// TO DO for this Moodle version
			
		}else{
			moodleGradesScreenOldVer = moodleCourseDetailsScreen.clickGradesLink(moodleVersion);			
			// check the instructor has correct data
			Assert.verifyEquals(moodleGradesScreenOldVer.getCourseNameForInstructor(), courseFullName, "Course full name did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getCategoryTypeForInstructor(), categoryType, "Category did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getAssignmentTitleForInstructor(), assignmentTitle, "Assignment title did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getScorePossibleForInstructor(), scorePossible, "Score possible did not match");
		}
	}

	@Test(description = "Check Gradebook data related to the Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent"}, priority = 0)
	public void checkGradebookItemsAreCorrectForStudent() throws Exception {

		moodleHomeScreen = moodleApplication.loginToMoodle(studentLogin, password);
		moodleCourseDetailsScreen = moodleHomeScreen.goToCreatedCourse(courseFullName);
		
		// check the version of Moodle
		if(moodleVersion < 260){
			moodleGradesScreen = moodleCourseDetailsScreen.clickGradesLink();	
			// check the student has correct data
			Assert.verifyEquals(moodleGradesScreen.getCourseName(), courseFullName, "Course full name did not match");
			Assert.verifyEquals(moodleGradesScreen.getCategoryType(), categoryType, "Category did not match");
			Assert.verifyEquals(moodleGradesScreen.getAssignmentTitle(), assignmentTitle, "Assignment title did not match");
			Assert.verifyEquals(moodleGradesScreen.getScorePossible(), scorePossible, "Score possible did not match");
			Assert.verifyEquals(moodleGradesScreen.getFeedback(), commentForStudent, "Comment did not match");
			Assert.verifyEquals(moodleGradesScreen.getScoreReceived(), scoreReceivedForStudent, "ScoreReceived did not match"); 
		}else{
			moodleGradesScreenOldVer = moodleCourseDetailsScreen.clickGradesLink(moodleVersion);			
			// check the student has correct data
			Assert.verifyEquals(moodleGradesScreenOldVer.getCourseName(), courseFullName, "Course full name did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getCategoryType(), categoryType, "Category did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getAssignmentTitle(), assignmentTitle, "Assignment title did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getScorePossible(), scorePossible, "Score possible did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getScoreReceived(), scoreReceivedForStudent, "ScoreReceived did not match"); 
		}
	}
	
	@Test(description = "Check TestScorableItem form is submitted successfully with new assignment ID and SOType: Exist", 
			dependsOnMethods = {"checkGradebookItemsAreCorrectForInstructor", "checkGradebookItemsAreCorrectForStudent"})
	public void checkSubmittingTestScorableItemFormWithNewAssignmentIdSSOTypeExistIsSuccessfull() throws Exception {
		
		// configure extend property 'AssignmentCreationMode' to 'exist' 
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
		mhCampusInstanceConnectorsScreen.configureMoodleGradebookConnector(moodleApplication.moodleGradebookExtendedPropertiesExist);
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);
		
		assignmentIdNew = getRandomNumber();
		String categoryNew = "TestNew";
		String scorePossibleNew = "102";
		// fill the form with new assignment ID, category and score possible
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(instance.customerNumber, providerId,
				courseRS.getId(), assignmentIdNew, assignmentTitle, categoryNew, description, startDate, dueToDate, scoreType,
				scorePossibleNew, isStudentViewable, isIncludedInGrade, gradebookApplication.tegrityServiceLocation);
		testScoreItemsScreen.submitForm();

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForMoodle(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully with new assignment ID and SOType: Exist", dependsOnMethods =
		{ "checkSubmittingTestScorableItemFormWithNewAssignmentIdSSOTypeExistIsSuccessfull"})
	public void checkSubmittingTestScoreFormWithNewAssignmentIdSSOTypeExistIsSuccessfullForStudent() throws Exception {

		// fill the form with new assignment ID and score received for student
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, providerId, courseRS.getId(),
				assignmentIdNew, studentLogin, commentForStudent, dateSubmitted, scoreReceivedForStudentNew,
				gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForMoodle(), "TestScore form submit failed");
	}
	
	@Test(description = "Check Gradebook data related to the Instructor", dependsOnMethods = {
	"checkSubmittingTestScoreFormWithNewAssignmentIdSSOTypeExistIsSuccessfullForStudent"}, priority = 0)
	public void checkAssignmentIdWasChangedAndTitleNotForInstructor() throws Exception {
	
		moodleHomeScreen = moodleApplication.loginToMoodle(instructorLogin, password);
		moodleCourseDetailsScreen = moodleHomeScreen.goToCreatedCourse(courseFullName);
		
		// check the version of Moodle
		if(moodleVersion < 260){
			moodleGradesScreen = moodleCourseDetailsScreen.clickGradesLink();
		    // check the instructor has correct data: title and category was not changed
			// TO DO for this Moodle version
			
		}else{
			moodleGradesScreenOldVer = moodleCourseDetailsScreen.clickGradesLink(moodleVersion);
			
			// check new assignment was not created with the same title
			Assert.verifyEquals(moodleGradesScreenOldVer.getCountOfCategoryTypesForInstructor(categoryType), 1, 
					"Amount of categories for instructor " + instructorLogin + " is incorrect");
			Assert.verifyEquals(moodleGradesScreenOldVer.getCountOfAssignmentForInstructor(assignmentTitle), 1, 
					"Amount of assignments for instructor " + instructorLogin + " is incorrect");
			// check the instructor has correct data: title and category was not changed
			Assert.verifyEquals(moodleGradesScreenOldVer.getCourseNameForInstructor(), courseFullName, "Course full name did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getCategoryTypeForInstructor(), categoryType, "Category did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getAssignmentTitleForInstructor(), assignmentTitle, "Assignment title did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getScorePossibleForInstructor(), scorePossible, "Score possible did not match");
		}
		
	}
		
	@Test(description = "Check Gradebook data related to the Student", dependsOnMethods = {
	"checkSubmittingTestScoreFormWithNewAssignmentIdSSOTypeExistIsSuccessfullForStudent"}, priority = 0)
	public void checkAssignmentIdWasChangedAndTitleNotForStudent() throws Exception {
		
		moodleHomeScreen = moodleApplication.loginToMoodle(studentLogin, password);
		moodleCourseDetailsScreen = moodleHomeScreen.goToCreatedCourse(courseFullName);
		
		// check the version of Moodle
		if(moodleVersion < 260){
			moodleGradesScreen = moodleCourseDetailsScreen.clickGradesLink();	
			
			// check new assignment was not created with the same title
			Assert.verifyEquals(moodleGradesScreen.getCountOfAssignment(), 1, "Amount of assignments for student " 
					+ studentLogin + " is incorrect");
			Assert.verifyEquals(moodleGradesScreen.getCountOfCategoryTypes(), 1, "Amount of categories for student "
					+ studentLogin + " is incorrect");
			// check the student has correct data and and score received for student was changed
			Assert.verifyEquals(moodleGradesScreen.getCourseName(), courseFullName, "Course full name did not match");
			Assert.verifyEquals(moodleGradesScreen.getCategoryType(), categoryType, "Category did not match");
			Assert.verifyEquals(moodleGradesScreen.getAssignmentTitle(), assignmentTitle, "Assignment title did not match");
			Assert.verifyEquals(moodleGradesScreen.getScorePossible(), scorePossible, "Score possible did not match");
			Assert.verifyEquals(moodleGradesScreen.getFeedback(), commentForStudent, "Comment did not match");
			Assert.verifyEquals(moodleGradesScreen.getScoreReceived(), scoreReceivedForStudentNew, "ScoreReceived did not match"); 			
			
		}else{
			moodleGradesScreenOldVer = moodleCourseDetailsScreen.clickGradesLink(moodleVersion);		
			
			// check new assignment was not created with the same title
			Assert.verifyEquals(moodleGradesScreenOldVer.getCountOfAssignment(), 1, "Amount of assignments for student "
					+ studentLogin + " is incorrect");
			Assert.verifyEquals(moodleGradesScreenOldVer.getCountOfCategoryTypes(), 1, "Amount of categories for student "
					+ studentLogin + " is incorrect");
			// check the student has correct data and and score received for student was changed
			Assert.verifyEquals(moodleGradesScreenOldVer.getCourseName(), courseFullName, "Course full name did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getCategoryType(), categoryType, "Category did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getAssignmentTitle(), assignmentTitle, "Assignment title did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getScorePossible(), scorePossible, "Score possible did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getScoreReceived(), scoreReceivedForStudentNew, "ScoreReceived did not match"); 
			
		}
	}
	
	@Test(description = "Check TestScorableItem form is submitted successfully with new assignment ID and SOType: New", dependsOnMethods = 
		{"checkAssignmentIdWasChangedAndTitleNotForInstructor", "checkAssignmentIdWasChangedAndTitleNotForStudent"})
	public void checkSubmittingTestScorableItemFormWithNewAssignmentIdSSOTypeNewIsSuccessfull() throws Exception {
		
		// configure extend property 'AssignmentCreationMode' to 'new' 
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
		mhCampusInstanceConnectorsScreen.configureMoodleGradebookConnector(moodleApplication.moodleGradebookExtendedPropertiesNew);
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);
		
		assignmentIdNew = getRandomNumber();
		// fill the form with the same data and with new assignment ID
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(instance.customerNumber, providerId,
				courseRS.getId(), assignmentIdNew, assignmentTitle, categoryType, description, startDate, dueToDate, scoreType,
				scorePossible, isStudentViewable, isIncludedInGrade, gradebookApplication.tegrityServiceLocation);
		testScoreItemsScreen.submitForm();

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForMoodle(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully with new assignment ID and SOType: New", dependsOnMethods = 
		{"checkSubmittingTestScorableItemFormWithNewAssignmentIdSSOTypeNewIsSuccessfull"})
	public void checkSubmittingTestScoreFormWithNewAssignmentIdSSOTypeNewIsSuccessfullForStudent() throws Exception {

		scoreReceivedForStudentNew = GradebookApplication.getRandomScore();
		// fill the form with the same data and with new assignment ID
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, providerId, courseRS.getId(),
				assignmentIdNew, studentLogin, commentForStudent, dateSubmitted, scoreReceivedForStudentNew,
				gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForMoodle(), "TestScore form submit failed");
	}
	
	@Test(description = "Check Gradebook data related to the Instructor in the second assignment", dependsOnMethods = {
		"checkSubmittingTestScoreFormWithNewAssignmentIdSSOTypeNewIsSuccessfullForStudent"}, priority = 0)
	public void checkNewAssignmentWasCreatedWithSameTitleForInstructor() throws Exception {
	
		moodleHomeScreen = moodleApplication.loginToMoodle(instructorLogin, password);
		moodleCourseDetailsScreen = moodleHomeScreen.goToCreatedCourse(courseFullName);
		
		// check the version of Moodle
		if(moodleVersion < 260){
			moodleGradesScreen = moodleCourseDetailsScreen.clickGradesLink();
			// check the instructor has correct data: title and category was not changed
			// TO DO for this Moodle version
			
		}else{
			moodleGradesScreenOldVer = moodleCourseDetailsScreen.clickGradesLink(moodleVersion);
			
			// check new assignment was created with the same title
			Assert.verifyEquals(moodleGradesScreenOldVer.getCountOfCategoryTypesForInstructor(categoryType), 1, 
					"Amount of categories for instructor " + instructorLogin + " is incorrect");
			Assert.verifyEquals(moodleGradesScreenOldVer.getCountOfAssignmentForInstructor(assignmentTitle), 2, 
					"Amount of assignments for instructor " + instructorLogin + " is incorrect");
			// check the instructor has correct data: title and category was not changed
			Assert.verifyEquals(moodleGradesScreenOldVer.getCourseNameForInstructor(), courseFullName, "Course full name did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getCategoryTypeForInstructor(), categoryType, "Category did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getAssignmentTitleForInstructorForSecondAssignment(), assignmentTitle, "Assignment title did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getScorePossibleForInstructor(), scorePossible, "Score possible did not match");
		}
	
	}
	
	@Test(description = "Check Gradebook data related to the Student in the second assignment", dependsOnMethods = {
		"checkSubmittingTestScoreFormWithNewAssignmentIdSSOTypeNewIsSuccessfullForStudent"}, priority = 0)
	public void checkNewAssignmentWasCreatedWithSameTitleForStudent() throws Exception {
	
		moodleHomeScreen = moodleApplication.loginToMoodle(studentLogin, password);
		moodleCourseDetailsScreen = moodleHomeScreen.goToCreatedCourse(courseFullName);
		
		// check the version of Moodle
		if(moodleVersion < 260){
			moodleGradesScreen = moodleCourseDetailsScreen.clickGradesLink();	
			
			// check new assignment was not created with the same title
			// TO DO for this Moodle version
			// check the student has correct data and and score received for student was changed
			// TO DO for this Moodle version
			
		}else{
			moodleGradesScreenOldVer = moodleCourseDetailsScreen.clickGradesLink(moodleVersion);		
			
			// check new assignment was created with the same title
			Assert.verifyEquals(moodleGradesScreenOldVer.getCountOfAssignment(), 2, "Amount of assignments for student "
					+ studentLogin + " is incorrect");
			Assert.verifyEquals(moodleGradesScreenOldVer.getCountOfCategoryTypes(), 1, "Amount of categories for student "
					+ studentLogin + " is incorrect");
			// check the student has correct data and and score received for student was changed
			Assert.verifyEquals(moodleGradesScreenOldVer.getCourseName(), courseFullName, "Course full name did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getCategoryType(), categoryType, "Category did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getAssignmentTitleForSecondAssignment(), assignmentTitle, "Assignment title did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getScorePossibleForSecondAssignment(), scorePossible, "Score possible did not match");
			Assert.verifyEquals(moodleGradesScreenOldVer.getScoreReceivedForSecondAssignment(), scoreReceivedForStudentNew, "ScoreReceived did not match"); 
			
		}
	}
		
	private String getRandomString(int count) {
		return RandomStringUtils.randomNumeric(count);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}
 
	public void prepareDataInMoodle() throws Exception {
		studentRS = moodleApiUtils.createUser(studentLogin, password, studentName, studentSurname);
		instructorRS = moodleApiUtils.createUser(instructorLogin, password, instructorName, instructorSurname);

		categoryRS = moodleApiUtils.createCategory(category);
		courseRS = moodleApiUtils.createCourseInsideCategory(courseFullName, courseShortName, categoryRS);
		
		moodleApiUtils.enrollToCourseAsStudent(studentRS, courseRS);
		moodleApiUtils.enrollToCourseAsInstructor(instructorRS, courseRS);
	}
}
