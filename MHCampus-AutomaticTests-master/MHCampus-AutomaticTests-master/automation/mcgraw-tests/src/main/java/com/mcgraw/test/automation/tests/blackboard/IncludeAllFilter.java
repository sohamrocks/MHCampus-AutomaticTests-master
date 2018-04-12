package com.mcgraw.test.automation.tests.blackboard;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.blackboard.serviceapi.BlackBoardApi;
import com.mcgraw.test.automation.api.blackboard.serviceapi.BlackBoardApi.BlackboardApiRoleIdentifier;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.BlackboardApplication;
import com.mcgraw.test.automation.ui.blackboard.BlackboardCourseDetails;
import com.mcgraw.test.automation.ui.blackboard.BlackboardHomeScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class IncludeAllFilter extends BaseTest {

	@Autowired
	private BlackBoardApi blackBoardApi;

	@Autowired
	private BlackboardApplication blackboardApplication;

	private String studentId;
	private String studentLogin;
	private String studentName;
	private String studentSurname;

	private String instructorId;
	private String instructorLogin;
	private String instructorName;
	private String instructorSurname;

	private String courseId1;
	private String courseName1;

	private String courseId2;
	private String courseName2;

	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	private BlackboardHomeScreen blackboardHomeScreen;
	private BlackboardCourseDetails blackboardCourseDetails;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;

	private int numOfSlave = 1;
	
	private InstanceCredentials instance;
	
	private boolean checkBoxShowActiveCourse = false;

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		
		instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);
		checkBoxShowActiveCourse = tegrityAdministrationApplication.getCheckBoxShowActiveCourse();
		
		blackboardApplication.completeMhCampusSetupWithBlackBoard(instance.customerNumber, instance.sharedSecret);
		prepareTestDataInBlackBoard();	
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
		mhCampusInstanceConnectorsScreen.configureBlackboardAuthorizationConnector(blackboardApplication.title, blackboardApplication.address);
		mhCampusInstanceConnectorsScreen.configureBlackboardSsoLinkConnector(blackboardApplication.title, blackboardApplication.address);
		mhCampusInstanceConnectorsScreen.chooseIncludeFilter();
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		
		browser.pause(mhCampusInstanceApplication.LINKS_PROCESSING_TIMEOUT_MS);
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		
		if(studentId != null)
			blackBoardApi.deleteUser(studentId);
		if(instructorId != null)
			blackBoardApi.deleteUser(instructorId);
		if(courseId1 != null)
			blackBoardApi.deleteCourse(courseId1);
		if(courseId2 != null)
			blackBoardApi.deleteCourse(courseId2);
		blackBoardApi.logout();
	}

	@Test(description = "For BlackBoard instructor MH Campus link is present for all courses")
	public void checkMHCampusLinkIsPresentInInstructorsCoursesForIncludeAllFilter() {

		blackboardHomeScreen = blackboardApplication.loginToBlackBoard(instructorLogin, instructorLogin);
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName1);		
		Assert.verifyEquals(blackboardCourseDetails.getMhCampusLinksCount(), 1, "Wrong count of MH Campus links for instructor's course " + courseName1);
		blackboardCourseDetails.clickMyInstitutionLink();
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName2);
		Assert.verifyEquals(blackboardCourseDetails.getMhCampusLinksCount(), 1, "Wrong count of MH Campus links for instructor's course " + courseName2);
		blackboardApplication.logoutFromBlackBoard();
	}

	@Test(description = "For BlackBoard student MH Campus link is present for all courses")
	public void checkMHCampusLinkIsPresentInStudentsCoursesForIncludeAllFilter() {

		blackboardHomeScreen = blackboardApplication.loginToBlackBoard(studentLogin, studentLogin);
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName1);
		Assert.verifyEquals(blackboardCourseDetails.getMhCampusLinksCount(), 1, "Wrong count of MH Campus links for student's course " + courseName1);
		blackboardCourseDetails.clickMyInstitutionLink();
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName2);		
		Assert.verifyEquals(blackboardCourseDetails.getMhCampusLinksCount(), 1, "Wrong count of MH Campus links for student's course " + courseName2);
		blackboardApplication.logoutFromBlackBoard();
	}

	@Test(description = "For BlackBoard instructor MH Campus link bahaves correctly", dependsOnMethods = { "checkMHCampusLinkIsPresentInInstructorsCoursesForIncludeAllFilter" })
	public void checkMHCampusLinkBahavesCorrectlyForBlackBoardInstructor() {

		blackboardHomeScreen = blackboardApplication.loginToBlackBoard(instructorLogin, instructorLogin);
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName1);
		mhCampusIntroductionScreen = blackboardCourseDetails.clickMhCampusLink();

		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(), "Hi " + instructorName + " " + instructorSurname
				+ " -", "Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(), "Rules text is incorrect");

		mhCampusIntroductionScreen.acceptRules();
		
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), (instructorName + " " + instructorSurname).toUpperCase(),
				"User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1), "Course" + courseName1 + " is absent");
		if(!checkBoxShowActiveCourse){
			Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName2), "Course" + courseName2 + " is absent");
		}
		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
		
		browser.switchToFirstWindow();
		browser.closeAllWindowsExceptFirst();
		blackboardApplication.logoutFromBlackBoard();
	}

	@Test(description = "For BlackBoard student MH Campus link bahaves correctly", dependsOnMethods = { "checkMHCampusLinkIsPresentInStudentsCoursesForIncludeAllFilter" })
	public void checkMHCampusLinkBahavesCorrectlyForBlackBoardStudent() {

		blackboardHomeScreen = blackboardApplication.loginToBlackBoard(studentLogin, studentLogin);
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName1);

		mhCampusIntroductionScreen = blackboardCourseDetails.clickMhCampusLink();
		
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(), "Hi " + studentName + " " + studentSurname + " -",
				"Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(), "Rules text is incorrect");

		mhCampusIntroductionScreen.acceptRules();
		
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), (studentName + " " + studentSurname).toUpperCase(),
				"User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");
		if(!checkBoxShowActiveCourse){
			Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName2), "Course " + courseName2 + " is absent");
		}
		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(), "Search option is present");
		
		browser.switchToFirstWindow();
		browser.closeAllWindowsExceptFirst();
		blackboardApplication.logoutFromBlackBoard();
	}

	private void prepareTestDataInBlackBoard() throws Exception {

		String studentPrefix = "Student";
		String instructorPrefix = "Instructor";

		String studentRandom = getRandomString();
		String instructorRandom = getRandomString();
		String courseRandom1 = getRandomString();
		String courseRandom2 = getRandomString();

		studentLogin = studentPrefix + studentRandom;
		studentName = studentPrefix + "Name" + studentRandom;
		studentSurname = studentPrefix + "Surname" + studentRandom;

		instructorLogin = instructorPrefix + instructorRandom;
		instructorName = "InstructorName" + instructorRandom;
		instructorSurname = "InstructorSurname" + instructorRandom;
		courseName1 = "Course" + courseRandom1;
		courseName2 = "Course" + courseRandom2;

		blackBoardApi.loginAndInitialiseBlackBoardServices();

		blackBoardApi.deleteAllUsersByLoginPrefix(studentPrefix);
		blackBoardApi.deleteAllUsersByLoginPrefix(instructorPrefix);

		studentId = blackBoardApi.createUser(studentLogin, studentName, studentSurname);
		instructorId = blackBoardApi.createUser(instructorLogin, instructorName, instructorSurname);
		courseId1 = blackBoardApi.createCourse(courseName1);
		courseId2 = blackBoardApi.createCourse(courseName2);

		blackBoardApi.createEnrollment(studentId, courseId1, BlackboardApiRoleIdentifier.STUDENT);
		blackBoardApi.createEnrollment(instructorId, courseId1, BlackboardApiRoleIdentifier.INSTRUCTOR);
		blackBoardApi.createEnrollment(studentId, courseId2, BlackboardApiRoleIdentifier.STUDENT);
		blackBoardApi.createEnrollment(instructorId, courseId2, BlackboardApiRoleIdentifier.INSTRUCTOR);
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}
}