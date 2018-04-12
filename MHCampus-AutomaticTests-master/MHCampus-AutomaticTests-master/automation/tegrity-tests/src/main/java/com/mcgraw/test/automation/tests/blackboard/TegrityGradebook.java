package com.mcgraw.test.automation.tests.blackboard;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.blackboard.exceptions.NullGradebookInfoException;
import com.mcgraw.test.automation.api.blackboard.serviceapi.BlackBoardApi;
import com.mcgraw.test.automation.api.blackboard.serviceapi.BlackBoardApi.BlackboardApiRoleIdentifier;
import com.mcgraw.test.automation.api.blackboard.serviceapi.BlackBoardApi.GradebookInfo;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.BlackboardApplication;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceConnectorsScreen;

public class TegrityGradebook extends BaseTest {

	@Autowired
	private BlackBoardApi blackBoardApi;

	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	private BlackboardApplication blackboardApplication;

	private String studentPrefix = "Student";
	private String instructorPrefix = "Instructor";

	private String studentId1;
	private String studentLogin1;

	private String studentId2;
	private String studentLogin2;

	private String instructorId;
	private String instructorLogin;

	private String courseId1;
	private String courseName1;
	private String courseId2;
	private String courseName2;

	private String providerId = "provider_" + getRandomString();
	private String description = "description_" + getRandomString();
	private String assignmentTitle = "title_" + getRandomString();
	private String assignmentId = getRandomNumber();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String dateSubmitted = GradebookApplication.getRandomStartDate();
	private String scorePossible = "100";
	private String category = "Blog";
	private String scoreType = "Percentage";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = true;

	private String commentForStudent1 = "comment_" + getRandomString();
	private String commentForStudent2 = "comment_" + getRandomString();

	private String scoreReceivedForStudent1 = GradebookApplication.getRandomScore();
	private String scoreReceivedForStudent2 = GradebookApplication.getRandomScore();

	TegrityInstanceConnectorsScreen tegrityInstanceConnectorsScreen;

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		blackboardApplication.completeTegritySetupWithBlackBoard(tegrityInstanceApplication.customerNumber, tegrityInstanceApplication.sharedSecret);
		prepareTestDataInBlackBoard();
		
        tegrityInstanceConnectorsScreen = tegrityInstanceApplication.loginToTegrityInstanceAsAdminAndClickManageAairsLink();			
		tegrityInstanceConnectorsScreen.deleteAllConnectors();	
		tegrityInstanceConnectorsScreen.configureBlackboardGradebookConnector(blackboardApplication.title, blackboardApplication.address);		
		tegrityInstanceConnectorsScreen.clickSaveAndContinueButton();	
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		
		if(studentId1 != null)
			blackBoardApi.deleteUser(studentId1);
		if(studentId2 != null)
			blackBoardApi.deleteUser(studentId2);
		if(instructorId != null)
			blackBoardApi.deleteUser(instructorId);
		if(courseId1 != null)
			blackBoardApi.deleteCourse(courseId1);
		if(courseId2 != null)
			blackBoardApi.deleteCourse(courseId2);
		blackBoardApi.logout();
	}

	@Test(description = "Check TestScorableItem form is submitted successfully")
	public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm( tegrityInstanceApplication.customerNumber, providerId,
				courseName1, assignmentId, assignmentTitle, category, description, startDate, dueToDate, scoreType, scorePossible,
				isStudentViewable, isIncludedInGrade, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForBlackboard(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = { "checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent1() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm( tegrityInstanceApplication.customerNumber, providerId, courseName1,
				assignmentId, studentLogin1, commentForStudent1, dateSubmitted, scoreReceivedForStudent1,
				gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForBlackboard(), "TestScore form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = { "checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent2() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm( tegrityInstanceApplication.customerNumber, providerId, courseName1,
				assignmentId, studentLogin2, commentForStudent2, dateSubmitted, scoreReceivedForStudent2,
				gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForBlackboard(), "TestScore form submit failed");
	}

	@Test(description = "Check Gradebook data related to the first Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" })
	public void checkGradebookItemsAreCorrectForStudent1Course1() throws Exception {

		GradebookInfo gradebookInfo = blackBoardApi.getGradebook(courseId1, assignmentId, studentId1);
		// check the assignment data
		Assert.verifyEquals(gradebookInfo.AssignmentId, assignmentId, "AssignmentId did not match");
		Assert.verifyEquals(gradebookInfo.AssignmentTitle, assignmentTitle, "Assignment title did not match");
		Assert.verifyEquals(gradebookInfo.Category, category, "Category did not match");
		Assert.verifyEquals(gradebookInfo.Description, description, "Description did not match");
		Assert.verifyEquals(gradebookInfo.ScorePoints, scorePossible, "Score possible did not match");
		Assert.verifyEquals(gradebookInfo.ScoreType, scoreType, "Score type did not match");
		Assert.verifyEquals(gradebookInfo.IsStudentViewable, isStudentViewable, "IsStudentViewable did not match");
		Assert.verifyEquals(gradebookInfo.IsIncludedInGrade, isIncludedInGrade, "IsIncludedInGrade did not match");
		// check the first student can't see the comment and score received for
		// the second student
		Assert.verifyEquals(gradebookInfo.Comments.length, 1, "Student " + studentLogin1 + " can see the comment of" + studentLogin2);
		Assert.verifyEquals(gradebookInfo.ScoresReceived.length, 1, "Student " + studentLogin1 + " can see the Score received by "
				+ studentLogin2);
		// check relate only to the first student data
		Assert.verifyEquals(gradebookInfo.ScoresReceived[0], scoreReceivedForStudent1, "Score received did not match");
		Assert.verifyEquals(gradebookInfo.Comments[0], commentForStudent1, "Comment did not match");
	}
	
	@Test(description = "Check Gradebook data related to the second Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" })
	public void checkGradebookItemsAreCorrectForStudent2Course1() throws Exception {

		GradebookInfo gradebookInfo = blackBoardApi.getGradebook(courseId1, assignmentId, studentId2);
		// check the assignment data
		Assert.verifyEquals(gradebookInfo.AssignmentId, assignmentId, "AssignmentId did not match");
		Assert.verifyEquals(gradebookInfo.AssignmentTitle, assignmentTitle, "Assignment title did not match");
		Assert.verifyEquals(gradebookInfo.Category, category, "Category did not match");
		Assert.verifyEquals(gradebookInfo.Description, description, "Description did not match");
		Assert.verifyEquals(gradebookInfo.ScorePoints, scorePossible, "Score possible did not match");
		Assert.verifyEquals(gradebookInfo.ScoreType, scoreType, "Score type did not match");
		Assert.verifyEquals(gradebookInfo.IsStudentViewable, isStudentViewable, "IsStudentViewable did not match");
		Assert.verifyEquals(gradebookInfo.IsIncludedInGrade, isIncludedInGrade, "IsIncludedInGrade did not match");
		// check the second student can't see the comment and score received for
		// the first student
		Assert.verifyEquals(gradebookInfo.Comments.length, 1, "Student " + studentLogin2 + " can see the comment of" + studentLogin1);
		Assert.verifyEquals(gradebookInfo.ScoresReceived.length, 1, "Student " + studentLogin2 + " can see the Score received by "
				+ studentLogin1);
		// check relate only to the second student data
		Assert.verifyEquals(gradebookInfo.ScoresReceived[0], scoreReceivedForStudent2, "Score received did not match");
		Assert.verifyEquals(gradebookInfo.Comments[0], commentForStudent2, "Comment did not match");
	}

	@Test(description = "Check sent Gradebook items are NOT present in the Blackboard unused course for the first Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" }, expectedExceptions = NullGradebookInfoException.class)
	public void checkGradebookItemsAreNotPresentForStudent1Course2() throws Exception {
		blackBoardApi.getGradebook(courseId2, assignmentId, studentId1);
	}
	
	@Test(description = "Check sent Gradebook items are NOT present in the Blackboard unused course for the second Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" }, expectedExceptions = NullGradebookInfoException.class)
	public void checkGradebookItemsAreNotPresentForStudent2Course2() throws Exception {
		blackBoardApi.getGradebook(courseId2, assignmentId, studentId2);
	}

	private void prepareTestDataInBlackBoard() throws Exception {

		studentLogin1 = studentPrefix + getRandomString();
		studentLogin2 = studentPrefix + "2" + getRandomString();
		instructorLogin = instructorPrefix + getRandomString();
		courseName1 = "Course1" + getRandomString();
		courseName2 = "Course2" + getRandomString();

		blackBoardApi.loginAndInitialiseBlackBoardServices();

		blackBoardApi.deleteAllUsersByLoginPrefix(studentPrefix);
		blackBoardApi.deleteAllUsersByLoginPrefix(instructorPrefix);

		studentId1 = blackBoardApi.createUser(studentLogin1, studentLogin1, studentLogin1);
		studentId2 = blackBoardApi.createUser(studentLogin2, studentLogin2, studentLogin2);
		instructorId = blackBoardApi.createUser(instructorLogin, instructorLogin, instructorLogin);
		courseId1 = blackBoardApi.createCourse(courseName1);
		courseId2 = blackBoardApi.createCourse(courseName2);

		blackBoardApi.createEnrollment(studentId1, courseId1, BlackboardApiRoleIdentifier.STUDENT);
		blackBoardApi.createEnrollment(studentId2, courseId1, BlackboardApiRoleIdentifier.STUDENT);
		blackBoardApi.createEnrollment(instructorId, courseId1, BlackboardApiRoleIdentifier.INSTRUCTOR);
		blackBoardApi.createEnrollment(studentId1, courseId2, BlackboardApiRoleIdentifier.STUDENT);
		blackBoardApi.createEnrollment(studentId2, courseId2, BlackboardApiRoleIdentifier.STUDENT);
		blackBoardApi.createEnrollment(instructorId, courseId2, BlackboardApiRoleIdentifier.INSTRUCTOR);
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}
}
