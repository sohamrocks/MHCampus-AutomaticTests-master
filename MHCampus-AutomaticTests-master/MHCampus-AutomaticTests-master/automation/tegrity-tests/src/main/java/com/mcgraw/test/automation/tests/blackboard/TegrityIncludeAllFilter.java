package com.mcgraw.test.automation.tests.blackboard;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.blackboard.serviceapi.BlackBoardApi;
import com.mcgraw.test.automation.api.blackboard.serviceapi.BlackBoardApi.BlackboardApiRoleIdentifier;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.BlackboardApplication;
import com.mcgraw.test.automation.ui.blackboard.BlackboardCourseDetails;
import com.mcgraw.test.automation.ui.blackboard.BlackboardHomeScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityCourseDetailsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityIntroductionScreen;

public class TegrityIncludeAllFilter extends BaseTest {
	
	private static final String BLACKBOARD_SUB_URL = "blackboard";

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

	private TegrityInstanceConnectorsScreen tegrityInstanceConnectorsScreen;
	private TegrityCourseDetailsScreen tegrityCourseDetailsScreen;
	private TegrityIntroductionScreen tegrityIntroductionScreen;
	
	private BlackboardHomeScreen blackboardHomeScreen;
	private BlackboardCourseDetails blackboardCourseDetails;

	@BeforeClass
	public void testSuiteSetup() throws Exception {
			
		blackboardApplication.completeTegritySetupWithBlackBoard(tegrityInstanceApplication.customerNumber, tegrityInstanceApplication.sharedSecret);	
		prepareTestDataInBlackBoard();
		
		tegrityInstanceConnectorsScreen = tegrityInstanceApplication.loginToTegrityInstanceAsAdminAndClickManageAairsLink();
		tegrityInstanceConnectorsScreen.deleteAllConnectors();
		tegrityInstanceConnectorsScreen.configureBlackboardAuthorizationConnector(blackboardApplication.title, blackboardApplication.address);
		tegrityInstanceConnectorsScreen.configureBlackboardSsoLinkConnector(blackboardApplication.title, blackboardApplication.address);		
		tegrityInstanceConnectorsScreen.chooseIncludeFilter();
		tegrityInstanceConnectorsScreen.clickSaveAndContinueButton();	
		tegrityInstanceConnectorsScreen.logOut();
		
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
	
	@AfterMethod  
	public void closeAllWindowsExceptFirst() throws Exception {
		browser.closeAllWindowsExceptCurrentWithSubURL(BLACKBOARD_SUB_URL);
		blackboardApplication.logoutFromBlackBoard();
	}

	@Test(description = "For BlackBoard instructor Tegrity link is present for all courses")
	public void checkTegrityLinkIsPresentInInstructorsCoursesForIncludeAllFilter() {

		blackboardHomeScreen = blackboardApplication.loginToBlackBoard(instructorLogin, instructorLogin);
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName1);
		for(int i = 0; i < 20; i++){    // AleksandrY fix instability on server side
			if(blackboardCourseDetails.getTegrityLinksCount() == 0){
				Logger.info(String.format("Waiting whether Tegrity link appears on UI. Try on <%s> of 5", i));
				browser.pause(60000);
			} else {
				break;
			}
		}
		Assert.verifyEquals(blackboardCourseDetails.getTegrityLinksCount(), 1, "Wrong count of Tegrity links for instructor's course " + courseName1);
		blackboardHomeScreen = blackboardCourseDetails.clickMyInstitutionLink();
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName2);
		for(int i = 0; i < 20; i++){    // AleksandrY fix instability on server side
			if(blackboardCourseDetails.getTegrityLinksCount() == 0){
				Logger.info(String.format("Waiting whether Tegrity link appears on UI. Try on <%s> of 5", i));
				browser.pause(60000);
			} else {
				break;
			}
		}
		Assert.verifyEquals(blackboardCourseDetails.getTegrityLinksCount(), 1, "Wrong count of Tegrity links for instructor's course " + courseName2);
	}

	@Test(description = "For BlackBoard student Tegrity link is present for all courses")
	public void checkTegrityLinkIsPresentInStudentsCoursesForIncludeAllFilter() {

		blackboardHomeScreen = blackboardApplication.loginToBlackBoard(studentLogin, studentLogin);
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName1);
		Assert.verifyEquals(blackboardCourseDetails.getTegrityLinksCount(), 1, "Wrong count of Tegrity links for student's course " + courseName1);
		blackboardHomeScreen = blackboardCourseDetails.clickMyInstitutionLink();
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName2);		
		Assert.verifyEquals(blackboardCourseDetails.getTegrityLinksCount(), 1, "Wrong count of Tegrity links for student's course " + courseName2);
	}

	@Test(description = "For BlackBoard instructor Tegrity link bahaves correctly", dependsOnMethods = { "checkTegrityLinkIsPresentInInstructorsCoursesForIncludeAllFilter" })
	public void checkTegrityLinkBahavesCorrectlyForBlackBoardInstructor() {

		blackboardHomeScreen = blackboardApplication.loginToBlackBoard(instructorLogin, instructorLogin);
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName1);
		tegrityCourseDetailsScreen = blackboardCourseDetails.clickTegrityLink();

		String expectedGreetingText = instructorName + " " + instructorSurname;		
		
		Assert.verifyEquals(tegrityCourseDetailsScreen.getUserNameText(), expectedGreetingText, "Greetin text is incorrect");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isCoursePresent(courseName1), "Course is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isSearchOptionPresent(), "Search option is absent");			
	}

	@Test(description = "For BlackBoard student Tegrity link bahaves correctly", dependsOnMethods = { "checkTegrityLinkIsPresentInStudentsCoursesForIncludeAllFilter" })
	public void checkTegrityLinkBahavesCorrectlyForBlackBoardStudent() {

		blackboardHomeScreen = blackboardApplication.loginToBlackBoard(studentLogin, studentLogin);
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName1);
		tegrityCourseDetailsScreen = blackboardCourseDetails.clickTegrityLink();
		
		String expectedGreetingText = studentName + " " + studentSurname;
	
		Assert.verifyEquals(tegrityCourseDetailsScreen.getUserNameText(), expectedGreetingText, "Greeting text is incorrect");
		Assert.verifyFalse(tegrityCourseDetailsScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isCoursePresent(courseName1), "Course is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isSearchOptionPresent(), "Search option is absent");			
	}

	@Test(description = "Check Tegrity Welcome page for BlackBoard instructor", dependsOnMethods = { "checkTegrityLinkIsPresentInInstructorsCoursesForIncludeAllFilter" })
	public void checkTegrityWelcomePageIsCorrectForInstructor() throws Exception {
	
		blackboardHomeScreen = blackboardApplication.loginToBlackBoard(instructorLogin, instructorLogin);
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName1);
		tegrityCourseDetailsScreen = blackboardCourseDetails.clickTegrityLink();
		tegrityIntroductionScreen = tegrityCourseDetailsScreen.goToCourses();

		String expectedUserName = instructorName + " " + instructorSurname;   
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), expectedUserName);	
			
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName2), "Course " + courseName2 + " is absent");
		String sandboxCourse = instructorName + " " + instructorSurname+ " " + "sandbox course";   
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is absent");	
		
		Assert.verifyTrue(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");	
	}

	@Test(description = "Check Tegrity Welcome page for BlackBoard student", dependsOnMethods = { "checkTegrityLinkIsPresentInStudentsCoursesForIncludeAllFilter" })
	public void checkTegrityWelcomePageIsCorrectForStudent() throws Exception {                       
		
		blackboardHomeScreen = blackboardApplication.loginToBlackBoard(studentLogin, studentLogin);
		blackboardCourseDetails = blackboardHomeScreen.clickOnCreatedCourse(courseName1);
		tegrityCourseDetailsScreen = blackboardCourseDetails.clickTegrityLink();
		tegrityIntroductionScreen = tegrityCourseDetailsScreen.goToCourses();

		String expectedUserName = studentName + " " + studentSurname;
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), expectedUserName);
				
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName2), "Course " + courseName2 + " is absent");	
		String sandboxCourse = "sandbox course";   
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is present");
		
		Assert.verifyFalse(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");	
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
		return RandomStringUtils.randomAlphanumeric(6);
	}
}