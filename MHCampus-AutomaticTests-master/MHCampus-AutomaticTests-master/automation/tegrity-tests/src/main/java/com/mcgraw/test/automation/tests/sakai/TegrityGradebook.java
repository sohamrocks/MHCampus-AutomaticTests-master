package com.mcgraw.test.automation.tests.sakai;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.sakai.SakaiTool;
import com.mcgraw.test.automation.api.sakai.SakaiUserRole;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewSite;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewUser;
import com.mcgraw.test.automation.api.sakai.service.ISakaiApiService;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.applications.SakaiApplication;
import com.mcgraw.test.automation.ui.applications.TegrityInstanceApplicationNoLocalConnector;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiCourseDetailsScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiCourseGradeOptionsScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiGradesDetailsScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiGradesScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiHomeScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceConnectorsScreen;

public class TegrityGradebook extends BaseTest {

	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	private SakaiApplication sakaiApplication;
	
	@Autowired
	protected TegrityInstanceApplicationNoLocalConnector tegrityInstanceApplicationNoLocalConnector;

	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom1 = "epamcourse1" + getRandomString();
	private String courseRandom2 = "epamcourse2" + getRandomString();

	private String studentLogin1 = "student" + studentRandom;
	private String studentName1 = "StudentName" + studentRandom;
	private String studentSurname1 = "StudentSurname" + studentRandom;

	private String studentLogin2 = "student2" + studentRandom;
	private String studentName2 = "StudentName2" + studentRandom;
	private String studentSurname2 = "StudentSurname2" + studentRandom;

	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String instructorSurname = "InstructorSurname" + instructorRandom;

	private String password = "123qweA@";

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
	private String categoryType = "Blog";
	private String scoreType = "Percentage";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;

	private TegrityInstanceConnectorsScreen tegrityInstanceConnectorsScreen;
	
	private SakaiHomeScreen sakaiHomeScreen;
	private SakaiCourseDetailsScreen sakaiCourseDetailsScreen;
	private SakaiGradesScreen sakaiGradesScreen;

	@Autowired
	private ISakaiApiService sakaiApiService;

	private AddNewUser student1;
	private AddNewUser student2;
	private AddNewUser instructor;
	private AddNewSite course1;
	private AddNewSite course2;

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		prepareDataInSakai();

        tegrityInstanceConnectorsScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdminAndClickManageAairsLink();			
		tegrityInstanceConnectorsScreen.deleteAllConnectors();
		tegrityInstanceConnectorsScreen.configureCustomGradebookConnector(sakaiApplication.sakaiTitle,
				sakaiApplication.sakaiGradebookServiceUrl, sakaiApplication.sakaiGradebookExtendedProperties);
		tegrityInstanceConnectorsScreen.clickSaveAndContinueButton();

		// allow the students to see the grades
		sakaiHomeScreen = sakaiApplication.loginToSakai(instructorLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(course1.getSiteid());
		sakaiGradesScreen = sakaiCourseDetailsScreen.clickGradebookBtn();
		SakaiCourseGradeOptionsScreen sakaiCourseGradeOptionsScreen = sakaiGradesScreen.goToCourseGradeOptions();
		sakaiCourseGradeOptionsScreen.allowDisplayCourseGradeForStudents();
		sakaiApplication.logoutFromSakai();
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		
		if(course1 != null)
			sakaiApiService.deletePageWithToolFromSite(course1, SakaiTool.GRADEBOOK);
		if(course2 != null)
			sakaiApiService.deletePageWithToolFromSite(course2, SakaiTool.GRADEBOOK);
		if(student1 != null)
			sakaiApiService.deleteUser(student1.getEid());
		if(student2 != null)
			sakaiApiService.deleteUser(student2.getEid());
		if(instructor != null)
			sakaiApiService.deleteUser(instructor.getEid());
		if(course1 != null)
			sakaiApiService.deleteSite(course1.getSiteid());
		if(course2 != null)
			sakaiApiService.deleteSite(course2.getSiteid());
	}	

	@Test(description = "Check TestScorableItem form is submitted successfully")
	public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(tegrityInstanceApplicationNoLocalConnector.customerNumber, providerId,
				course1.getSiteid(), assignmentId, assignmentTitle, categoryType, description, startDate, dueToDate, scoreType,
				scorePossible, isStudentViewable, isIncludedInGrade, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForSakai(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = { "checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent1() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(tegrityInstanceApplicationNoLocalConnector.customerNumber, providerId, course1.getSiteid(),
				assignmentId, studentLogin1, commentForStudent1, dateSubmitted, scoreReceivedForStudent1,
				gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForSakai(), "TestScore form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = { "checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent2() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(tegrityInstanceApplicationNoLocalConnector.customerNumber, providerId, course1.getSiteid(),
				assignmentId, studentLogin2, commentForStudent2, dateSubmitted, scoreReceivedForStudent2,
				gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForSakai(), "TestScore form submit failed");
	}
	
	@Test(description = "Check with Sakai instructor student's gradebook info", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" })
	public void checkInstructorCanSeeGradesForEachStudent() throws Exception {

		sakaiHomeScreen = sakaiApplication.loginToSakai(instructorLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(course1.getSiteid());		
		SakaiGradesDetailsScreen sakaiGradesDetailsScreen = sakaiGradesScreen.goToDetailsOfAssignment();
		
		Assert.verifyEquals(sakaiGradesDetailsScreen.getScoreReceivedForStudent(studentLogin1), scoreReceivedForStudent1);
		Assert.verifyEquals(sakaiGradesDetailsScreen.getScoreReceivedForStudent(studentLogin2), scoreReceivedForStudent2);
		
		sakaiApplication.logoutFromSakai();
	}
	
	@Test(description = "Check Sakai instructor can see Gradebook data", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" })
	public void checkInstructorCanSeeAverageGrade() throws Exception {

		sakaiHomeScreen = sakaiApplication.loginToSakai(instructorLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(course1.getSiteid());

		Assert.verifyEquals(sakaiGradesScreen.getAssignmentTitle(), assignmentTitle, "Assignment title did not match");
		Assert.verifyEquals(sakaiGradesScreen.getScorePossible(), scorePossible, "Score possible did not match");
		Assert.verifyEquals(sakaiGradesScreen.getDueToDate(), dueToDate, "Due to date did not match");
		Assert.verifyEquals(sakaiGradesScreen.getScoreReceived(),
				String.valueOf(Math.round((Integer.parseInt(scoreReceivedForStudent1) + Integer.parseInt(scoreReceivedForStudent2)) / 2)),
				"Average score is not correct");
		
		sakaiApplication.logoutFromSakai();
	}	
				
	@Test(enabled = false, description = "Check Gradebook is correct for the first student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2"})
	public void checkGradebookItemsAreCorrectForStudent1Course1() throws Exception {

		sakaiHomeScreen = sakaiApplication.loginToSakai(studentLogin1, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(course1.getSiteid());
		
		Assert.verifyEquals(sakaiGradesScreen.getAssignmentTitle(), assignmentTitle, "Assignment title did not match");
		Assert.verifyEquals(sakaiGradesScreen.getScorePossible(), scorePossible, "Score possible did not match");
		Assert.verifyEquals(sakaiGradesScreen.getDueToDate(), dueToDate, "Due to date did not match");
		Assert.verifyEquals(sakaiGradesScreen.getScoreReceived(), scoreReceivedForStudent1,"ScoreReceived did not match");	
		
		sakaiApplication.logoutFromSakai();
	}
	
	@Test(enabled = false, description = "Check Gradebook is correct for the second student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2"})
	public void checkGradebookItemsAreCorrectForStudent2Course1() throws Exception {

		sakaiHomeScreen = sakaiApplication.loginToSakai(studentLogin2, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(course1.getSiteid());
		
		Assert.verifyEquals(sakaiGradesScreen.getAssignmentTitle(), assignmentTitle, "Assignment title did not match");
		Assert.verifyEquals(sakaiGradesScreen.getScorePossible(), scorePossible, "Score possible did not match");
		Assert.verifyEquals(sakaiGradesScreen.getDueToDate(), dueToDate, "Due to date did not match");
		Assert.verifyEquals(sakaiGradesScreen.getScoreReceived(), scoreReceivedForStudent2,"ScoreReceived did not match");
		
		sakaiApplication.logoutFromSakai();
	}
	
	@Test(description = "Check under instrucotr sent Gradebook items are NOT present in the Sakai unused course for students", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2"})
	public void checkGradebookItemsAreNotPresentForInstructorCourse2() throws Exception {
		sakaiHomeScreen = sakaiApplication.loginToSakai(instructorLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(course2.getSiteid());		
		Assert.assertNull(sakaiGradesScreen.getAssignmentTitle(), "Assignment is present in wrong course");
		
		sakaiApplication.logoutFromSakai();
	}	

	@Test(description = "Check sent Gradebook items are NOT present in the Sakai unused course for the first Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2"})
	public void checkGradebookItemsAreNotPresentForStudent1Course2() throws Exception {

		sakaiHomeScreen = sakaiApplication.loginToSakai(studentLogin1, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(course2.getSiteid());		
		Assert.assertNull(sakaiGradesScreen.getAssignmentTitle(), "Assignment is present in wrong course");
		
		sakaiApplication.logoutFromSakai();
	}
	
	@Test(description = "Check sent Gradebook items are NOT present in the Sakai unused course for the second Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2"})
	public void checkGradebookItemsAreNotPresentForStudent2Course2() throws Exception {

		sakaiHomeScreen = sakaiApplication.loginToSakai(studentLogin2, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(course2.getSiteid());		
		Assert.assertNull(sakaiGradesScreen.getAssignmentTitle(), "Assignment is present in wrong course");
		
		sakaiApplication.logoutFromSakai();
	}
	
	private String getRandomString() {
		return RandomStringUtils.randomNumeric(5);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}

	public void prepareDataInSakai() throws Exception {

		student1 = sakaiApiService.createUser(studentLogin1, password, studentName1, studentSurname1);
		student2 = sakaiApiService.createUser(studentLogin2, password, studentName2, studentSurname2);
		instructor = sakaiApiService.createUser(instructorLogin, password, instructorName, instructorSurname);

		course1 = sakaiApiService.addNewSite(courseRandom1);
		sakaiApiService.addMemberToSiteWithRole(course1, student1, SakaiUserRole.STUDENT);
		sakaiApiService.addMemberToSiteWithRole(course1, student2, SakaiUserRole.STUDENT);
		sakaiApiService.addMemberToSiteWithRole(course1, instructor, SakaiUserRole.INSTRUCTOR);
		sakaiApiService.addNewToolToSite(course1, SakaiTool.GRADEBOOK);

		course2 = sakaiApiService.addNewSite(courseRandom2);
		sakaiApiService.addMemberToSiteWithRole(course2, student1, SakaiUserRole.STUDENT);
		sakaiApiService.addMemberToSiteWithRole(course2, student2, SakaiUserRole.STUDENT);
		sakaiApiService.addMemberToSiteWithRole(course2, instructor, SakaiUserRole.INSTRUCTOR);
		sakaiApiService.addNewToolToSite(course2, SakaiTool.GRADEBOOK);
	}
}
