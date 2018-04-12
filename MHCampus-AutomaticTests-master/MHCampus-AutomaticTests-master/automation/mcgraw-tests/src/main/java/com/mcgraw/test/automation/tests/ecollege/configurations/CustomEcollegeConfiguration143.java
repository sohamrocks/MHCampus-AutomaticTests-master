package com.mcgraw.test.automation.tests.ecollege.configurations;



import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.commons.lang.RandomStringUtils;
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
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;

/**
 * LMS = eCollege
 * DI: Disabled
 * Course ID – User ID Mapping: None
 * Gradebook Connector: Yes 
 * Gradebook Connector type : none 
 * SSO Connector: Yes
 * Data Connector: No
 * Canvas Mapping: No
 * Instructor Level Token: Off
 * Use Existing Assignments: Off 
 * Fallback to user_id and context_id: Off
 * Legacy: On
 * Async: Off
 */
public class CustomEcollegeConfiguration143 extends BaseTest {
	@Autowired
	private ECollegeApplication eCollegeApplication;

	@Autowired
	private GradebookApplication gradebookApplication;

	private String ecollegePassword = "123qweA@";

	private String ecollegeInstructorName1 = "InstructorName1414wa";
	private String ecollegeInstructorLogin1 = "instructor1414wa";

	private String ecollegeStudentName1 = "StudentNamert4554";
	private String ecollegeStudentLogin1 = "studentrt4554";
	private String ecollegeStudentId1 = "50906895";

	private String ecollegeCourseName1 = "MH_QT_Ecollege";
	private String ecollegeCourseShortName1 = "MH_QT_Ecollege";
	private String ecollegeCourseId1 = "14447307";

	private String customerNumber = "27WV-OUBR-LO5G";

	private String assignmentId = getRandomNumber();
	private String assignmentTitle = "title_" + getRandomString();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String scoreType = "Percentage";
	private String scorePossible = "102";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;
	private String commentForStudent1 = "comment_" + getRandomString();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent1 = GradebookApplication.getRandomScore();

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration:");
		Logger.info("LMS = Ecollege | DI: Off ");
		Logger.info(
				"Course ID – User ID Mapping:  Off  | Gradebook Connector: Yes | SSO Connector: Yes | Canvas Mapping: No ");
		Logger.info(
				"* Instructor Level Token: Off | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Async: Off | Legasy: On");

	}

	@AfterClass
	public void testSuiteTearDown() throws Exception {
		deleteGradebookItem();
	}

	@AfterMethod
	public void closeAllWindowsExceptFirst() throws Exception {
		browser.closeAllWindowsExceptFirst();
	}

	@Test(description = "For instructor MH Campus link bahaves correctly")
	public void checkSSOLinkWorksUnderInstructor() {
		ECollegeHomeScreen eCollegeHomeScreen = eCollegeApplication.loginToEcollege(ecollegeInstructorLogin1,
				ecollegePassword);
		ECollegeCourseDetailsScreen eCollegeCourseDetailsScreen = eCollegeHomeScreen
				.goToCourse(ecollegeCourseShortName1);
		MhCampusIntroductionScreen mhCampusIntroductionScreen = eCollegeCourseDetailsScreen.openMhCampusInstance();

		String expectedUserName = (ecollegeInstructorName1 + " " + ecollegeInstructorName1).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName);
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(ecollegeCourseName1),
				"Course is " + ecollegeCourseName1 + " absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
	}

	@Test(description = "For student MH Campus link bahaves correctly", dependsOnMethods = {
			"checkSSOLinkWorksUnderInstructor" })
	public void checkSSOLinkWorksUnderStudent() {
		ECollegeHomeScreen eCollegeHomeScreen = eCollegeApplication.loginToEcollege(ecollegeStudentLogin1,
				ecollegePassword);
		ECollegeCourseDetailsScreen eCollegeCourseDetailsScreen = eCollegeHomeScreen
				.goToCourse(ecollegeCourseShortName1);
		MhCampusIntroductionScreen mhCampusIntroductionScreen = eCollegeCourseDetailsScreen.openMhCampusInstance();

		String expectedUserName = (ecollegeStudentName1 + " " + ecollegeStudentName1).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName);
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(ecollegeCourseName1),
				"Course is " + ecollegeCourseName1 + " absent");

		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(), "Search option is present");

	}

	@Test(description = "Check TestScorableItem form is submitted successfully", dependsOnMethods = {
			"checkSSOLinkWorksUnderStudent" })
	public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber, "",
				ecollegeCourseId1, assignmentId, assignmentTitle, "", "", startDate, dueToDate, scoreType,
				scorePossible, isStudentViewable, isIncludedInGrade, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForEcollege(),
				"TestScoreItems form submit failed");

	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = {
			"checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent1() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, "", ecollegeCourseId1,
				assignmentId, ecollegeStudentId1, commentForStudent1, dateSubmitted, scoreReceivedForStudent1,
				gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForEcollege(), "TestScore form submit failed");
		// Check Gradebook data related to the Student
		ECollegeHomeScreen eCollegeHomeScreen = eCollegeApplication.loginToEcollege(ecollegeStudentLogin1,
				ecollegePassword);
		ECollegeCourseDetailsScreen eCollegeCourseDetailsScreen = eCollegeHomeScreen
				.goToCourse(ecollegeCourseShortName1);
		ECollegeGradesScreen eCollegeGradesScreen = eCollegeCourseDetailsScreen.clickGradesLink(assignmentTitle);

		Assert.assertTrue(eCollegeGradesScreen.isAssignmentPresent(assignmentTitle),
				"Assignment title " + assignmentTitle + " did not present");
		Assert.verifyEquals(eCollegeGradesScreen.getScorePossibleByAssignment(assignmentTitle), scorePossible,
				"Score possible did not match");
		Assert.verifyEquals(eCollegeGradesScreen.getScoreReceivedByAssignment(assignmentTitle),
				scoreReceivedForStudent1, "Score received did not match");

		ECollegeAssignmentDetailsScreen eCollegeAssignmentDetailsScreen = eCollegeGradesScreen
				.clickOnGrades(assignmentTitle);

		Assert.verifyEquals(eCollegeAssignmentDetailsScreen.getComment(), commentForStudent1 + " ",
				"Comment did not match");
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}

	private void deleteGradebookItem() throws Exception {
		try {
			ECollegeHomeScreen eCollegeHomeScreen = eCollegeApplication.loginToEcollege(ecollegeInstructorLogin1,
					ecollegePassword);
			ECollegeCourseDetailsForInstructorScreen eCollegeCourseDetailsForInstructorScreen = eCollegeHomeScreen
					.goToCourseUsInsrtuctor(ecollegeCourseShortName1);
			eCollegeCourseDetailsForInstructorScreen.deleteAssignmentTitle(assignmentTitle);
		} catch (Exception ex) {
			Logger.info("An exception happens during deleting an Assignment Title. Trying to delete the item again...");
			ECollegeHomeScreen eCollegeHomeScreen = eCollegeApplication.loginToEcollege(ecollegeInstructorLogin1,
					ecollegePassword);
			ECollegeCourseDetailsForInstructorScreen eCollegeCourseDetailsForInstructorScreen = eCollegeHomeScreen
					.goToCourseUsInsrtuctor(ecollegeCourseShortName1);
			eCollegeCourseDetailsForInstructorScreen.deleteAssignmentTitle(assignmentTitle);
		}
	}
}