package com.mcgraw.test.automation.tests.ecollege;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.ECollegeApplication;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.ecollege.ECollegeAssignmentDetailsScreen;
import com.mcgraw.test.automation.ui.ecollege.ECollegeCourseDetailsForInstructorScreen;
import com.mcgraw.test.automation.ui.ecollege.ECollegeCourseDetailsScreen;
import com.mcgraw.test.automation.ui.ecollege.ECollegeGradesScreen;
import com.mcgraw.test.automation.ui.ecollege.ECollegeHomeScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class Gradebook extends BaseTest{

	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	private ECollegeApplication eCollegeApplication;
	
	private String ecollegePassword = "123456";
	
	private String ecollegeStudentLogin1="mh_epamstudent1";
	private String ecollegeStudentId1="22159766";
	
	private String ecollegeStudentLogin2="mh_epamstudent2";
	private String ecollegeStudentId2="22235161";
	
	private String ecollegeCourseShortName1="mhcampus_epamcourse1";
	private String ecollegeCourseId1="8929695";

	private String ecollegeCourseShortName2="mhcampus_epamcourse2";
//	private String ecollegeCourseId2="8929711";
	
	private String ecollegeInstructorLogin1 = "mh_epaminstructor1";

	private String assignmentId = getRandomNumber();
	private String assignmentTitle = "title_" + getRandomString();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String scoreType = "Percentage";
	private String scorePossible = "102";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;
	private String commentForStudent1 = "comment_" + getRandomString();
	private String commentForStudent2 = "comment_" + getRandomString();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent1 = GradebookApplication.getRandomScore();
	private String scoreReceivedForStudent2 = GradebookApplication.getRandomScore();

	private int numOfSlave = 3;
	
	private InstanceCredentials instance;

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		
		instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);

		MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
		mhCampusInstanceConnectorsScreen.configureCustomGradebookConnector(eCollegeApplication.ecollegeTitle, eCollegeApplication.ecollegeGradebookServiceUrl,
				eCollegeApplication.ecollegeGradebookExtendedProperties);
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		deleteGradebookItem();
	}
	
	@AfterMethod  
	public void closeAllWindowsExceptFirst() throws Exception {
		browser.closeAllWindowsExceptFirst();
	}

	@Test(description = "Check TestScorableItem form is submitted successfully")
	public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {
		
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(instance.customerNumber, "", ecollegeCourseId1,
				assignmentId, assignmentTitle, "", "", startDate, dueToDate, scoreType, scorePossible, isStudentViewable,
				isIncludedInGrade, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForEcollege(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = { "checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent1() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, "", ecollegeCourseId1, assignmentId,
				ecollegeStudentId1, commentForStudent1, dateSubmitted, scoreReceivedForStudent1, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForEcollege(), "TestScore form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = { "checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent2() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, "", ecollegeCourseId1, assignmentId,
				ecollegeStudentId2, commentForStudent2, dateSubmitted, scoreReceivedForStudent2, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForEcollege(), "TestScore form submit failed");
	}

	@Test(description = "Check Gradebook data related to the first Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" }, priority = 0)
	public void checkGradebookItemsAreCorrectForStudent1Course1() throws Exception {
		
		ECollegeHomeScreen eCollegeHomeScreen = eCollegeApplication.loginToEcollege(ecollegeStudentLogin1, ecollegePassword);
		ECollegeCourseDetailsScreen eCollegeCourseDetailsScreen = eCollegeHomeScreen.goToCourse(ecollegeCourseShortName1);
		ECollegeGradesScreen eCollegeGradesScreen = eCollegeCourseDetailsScreen.clickGradesLink(assignmentTitle); 
		
		Assert.assertTrue(eCollegeGradesScreen.isAssignmentPresent(assignmentTitle), "Assignment title " + assignmentTitle + " did not present");
		Assert.verifyEquals(eCollegeGradesScreen.getScorePossibleByAssignment(assignmentTitle), scorePossible, "Score possible did not match");
		Assert.verifyEquals(eCollegeGradesScreen.getScoreReceivedByAssignment(assignmentTitle), scoreReceivedForStudent1, "Score received did not match");
		
		ECollegeAssignmentDetailsScreen eCollegeAssignmentDetailsScreen = eCollegeGradesScreen.clickOnGrades(assignmentTitle);
		
		Assert.verifyEquals(eCollegeAssignmentDetailsScreen.getComment(), commentForStudent1+" ", "Comment did not match");
	}
	
	@Test(description = "Check Gradebook data related to the second Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" }, priority = 0)
	public void checkGradebookItemsAreCorrectForStudent2Course1() throws Exception {
		
		ECollegeHomeScreen eCollegeHomeScreen = eCollegeApplication.loginToEcollege(ecollegeStudentLogin2, ecollegePassword);
		ECollegeCourseDetailsScreen eCollegeCourseDetailsScreen = eCollegeHomeScreen.goToCourse(ecollegeCourseShortName1);
		ECollegeGradesScreen eCollegeGradesScreen = eCollegeCourseDetailsScreen.clickGradesLink(assignmentTitle); 
		
		Assert.assertTrue(eCollegeGradesScreen.isAssignmentPresent(assignmentTitle), "Assignment title " + assignmentTitle + " did not present");
		Assert.verifyEquals(eCollegeGradesScreen.getScorePossibleByAssignment(assignmentTitle), scorePossible, "Score possible did not match");
		Assert.verifyEquals(eCollegeGradesScreen.getScoreReceivedByAssignment(assignmentTitle), scoreReceivedForStudent2, "Score received did not match");
		
		ECollegeAssignmentDetailsScreen eCollegeAssignmentDetailsScreen = eCollegeGradesScreen.clickOnGrades(assignmentTitle);
		
		Assert.verifyEquals(eCollegeAssignmentDetailsScreen.getComment(), commentForStudent2+" ", "Comment did not match");
	}

	@Test(description = "Check sent Gradebook items are NOT present in the eCollege unused course for the first Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" }, priority = 2)
	public void checkGradebookItemsAreNotPresentForStudent1Course2() throws Exception {
		
		ECollegeHomeScreen eCollegeHomeScreen = eCollegeApplication.loginToEcollege(ecollegeStudentLogin1, ecollegePassword);
		ECollegeCourseDetailsScreen eCollegeCourseDetailsScreen = eCollegeHomeScreen.goToCourse(ecollegeCourseShortName2);
		ECollegeGradesScreen eCollegeGradesScreen = eCollegeCourseDetailsScreen.clickGradesLink(assignmentTitle); 
		
		Assert.assertFalse(eCollegeGradesScreen.isAssignmentPresent(assignmentTitle), "Assignment is present for unused course");
	}
	
	@Test(description = "Check sent Gradebook items are NOT present in the eCollege unused course for the second Student", dependsOnMethods = {
			"checkSubmittingTestScoreFormIsSuccessfullForStudent1", "checkSubmittingTestScoreFormIsSuccessfullForStudent2" }, priority = 2)
	public void checkGradebookItemsAreNotPresentForStudent2Course2() throws Exception {
		
		ECollegeHomeScreen eCollegeHomeScreen = eCollegeApplication.loginToEcollege(ecollegeStudentLogin2, ecollegePassword);
		ECollegeCourseDetailsScreen eCollegeCourseDetailsScreen = eCollegeHomeScreen.goToCourse(ecollegeCourseShortName2);
		ECollegeGradesScreen eCollegeGradesScreen = eCollegeCourseDetailsScreen.clickGradesLink(assignmentTitle); 
		
		Assert.assertFalse(eCollegeGradesScreen.isAssignmentPresent(assignmentTitle), "Assignment is present for unused course");
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}
	
	private void deleteGradebookItem() throws Exception {
		try{
			ECollegeHomeScreen eCollegeHomeScreen = eCollegeApplication.loginToEcollege(ecollegeInstructorLogin1, ecollegePassword);
			ECollegeCourseDetailsForInstructorScreen eCollegeCourseDetailsForInstructorScreen = eCollegeHomeScreen.goToCourseUsInsrtuctor(ecollegeCourseShortName1);
			eCollegeCourseDetailsForInstructorScreen.deleteAssignmentTitle(assignmentTitle); 
		}catch(Exception ex){
			Logger.info("An exception happens during deleting an Assignment Title. Trying to delete the item again...");
			ECollegeHomeScreen eCollegeHomeScreen = eCollegeApplication.loginToEcollege(ecollegeInstructorLogin1, ecollegePassword);
			ECollegeCourseDetailsForInstructorScreen eCollegeCourseDetailsForInstructorScreen = eCollegeHomeScreen.goToCourseUsInsrtuctor(ecollegeCourseShortName1);
			eCollegeCourseDetailsForInstructorScreen.deleteAssignmentTitle(assignmentTitle); 
		}
	}
}
