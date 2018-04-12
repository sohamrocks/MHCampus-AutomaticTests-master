package com.mcgraw.test.automation.tests.moodle;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleCategoryRS;
import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleCourseRS;
import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleUserRS;
import com.mcgraw.test.automation.api.rest.moodle.service.MoodleApiUtils;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.MoodleApplication;
import com.mcgraw.test.automation.ui.moodle.MoodleCourseDetailsScreen;
import com.mcgraw.test.automation.ui.moodle.MoodleHomeScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityCourseDetailsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityIntroductionScreen;

public class TegritySSOLink extends BaseTest {
	
	private static final String MOODLE_SUB_URL = "moodle"; 

	@Autowired
	private MoodleApplication moodleApplication;

	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom = getRandomString();

	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;
	private String studentSurname = "StudentSurname" + studentRandom;

	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String instructorSurname = "InstructorSurname" + instructorRandom;

	private String category1 = "Category1" + courseRandom;
	private String category2 = "Category2" + courseRandom;
	private String courseFullName1 = "CourseFull1" + courseRandom;
	private String courseShortName1 = "CourseShort1" + courseRandom;
	private String courseFullName2 = "CourseFull2" + courseRandom;
	private String courseShortName2 = "CourseShort2" + courseRandom;

	private String password = "123qweA@";

	MoodleHomeScreen moodleHomeScreen;
	MoodleCourseDetailsScreen moodleCourseDetailsScreen;
	
	TegrityIntroductionScreen tegrityIntroductionScreen;
	TegrityInstanceConnectorsScreen tegrityInstanceConnectorsScreen;
	TegrityCourseDetailsScreen tegrityCourseDetailsScreen;

	@Autowired
	private MoodleApiUtils moodleApiUtils;

	private MoodleUserRS studentRS;
	private MoodleUserRS instructorRS;
	private MoodleCourseRS courseRS1;
	private MoodleCourseRS courseRS2;
	private MoodleCategoryRS categoryRS1;
	private MoodleCategoryRS categoryRS2;

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		
		try{
			prepareDataInMoodle();			
		}catch(Exception e){
			if(categoryRS1 != null)
				moodleApiUtils.deleteCategoryWithCourses(categoryRS1);
			if(categoryRS2 != null)
				moodleApiUtils.deleteCategoryWithCourses(categoryRS2);
			if(studentRS != null)
				moodleApiUtils.deleteUser(studentRS);
			if(instructorRS != null)
				moodleApiUtils.deleteUser(instructorRS);
			browser.pause(3000);
			prepareDataInMoodle();
		}
		
		moodleApplication.completeTegritySetupWithMoodle(tegrityInstanceApplication.customerNumber, tegrityInstanceApplication.sharedSecret);

        tegrityInstanceConnectorsScreen = tegrityInstanceApplication.loginToTegrityInstanceAsAdminAndClickManageAairsLink();		
		tegrityInstanceConnectorsScreen.deleteAllConnectors();
		
		tegrityInstanceConnectorsScreen.configureMoodleAuthorizationConnector(moodleApplication.moodleAuthorizationExtendedProperties
				.replace("<secret_value>", tegrityInstanceApplication.sharedSecret));
		tegrityInstanceConnectorsScreen.clickSaveAndContinueButton();

		moodleHomeScreen = moodleApplication.loginToMoodle(moodleApplication.moodleAdminLogin, moodleApplication.moodleAdminPassword);
		moodleCourseDetailsScreen = moodleHomeScreen.goToCreatedCourse(courseFullName1);
		moodleCourseDetailsScreen.addBlockToCourse(moodleApplication.moodleBlockName);

		browser.pause(tegrityInstanceApplication.DIRECT_LOGIN_TIMEOUT);
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		
		if(categoryRS1 != null)
			moodleApiUtils.deleteCategoryWithCourses(categoryRS1);
		if(categoryRS2 != null)
			moodleApiUtils.deleteCategoryWithCourses(categoryRS2);
		if(studentRS != null)
			moodleApiUtils.deleteUser(studentRS);
		if(instructorRS != null)
			moodleApiUtils.deleteUser(instructorRS);
	} 

	@AfterMethod  
	public void closeAllWindowsExceptFirst() throws Exception {
		browser.closeAllWindowsExceptCurrentWithSubURL(MOODLE_SUB_URL);
		moodleApplication.logoutFromMoodle();
	}
	
	@Test(description = "Check Tegrity link is present for Moodle instructor")
	public void checkTegrityLinkIsPresentInInstructorsCourse() throws Exception {

		moodleHomeScreen = moodleApplication.loginToMoodle(instructorLogin, password);
		moodleCourseDetailsScreen = moodleHomeScreen.goToCreatedCourse(courseFullName1);
		Assert.assertEquals(moodleCourseDetailsScreen.getTegrityLinksCount(), 1, "Wrong count of Tegrity links for instructor's course "
				+ courseFullName1 + ". Expected [1], actual [" + moodleCourseDetailsScreen.getTegrityLinksCount() + "]\n");
	}

	@Test(description = "Check Tegrity link is present for Moodle student")
	public void checkTegrityLinkIsPresentInStudentsCourse() throws Exception {

		moodleHomeScreen = moodleApplication.loginToMoodle(studentLogin, password);
		moodleCourseDetailsScreen = moodleHomeScreen.goToCreatedCourse(courseFullName1);
		Assert.assertEquals(moodleCourseDetailsScreen.getTegrityLinksCount(), 1, "Wrong count of Tegrity links for student's course "
				+ courseFullName1 + ". Expected [1], actual [" + moodleCourseDetailsScreen.getTegrityLinksCount() + "]\n");
	}

	@Test(description = "Check Tegrity link behaves correctly for Moodle instructor", dependsOnMethods = { "checkTegrityLinkIsPresentInInstructorsCourse" })
	public void checkTegrityLinkBehavesCorrectlyForMoodleInstructor() throws Exception {

		moodleHomeScreen = moodleApplication.loginToMoodle(instructorLogin, password);
		moodleCourseDetailsScreen = moodleHomeScreen.goToCreatedCourse(courseFullName1);
		try{
			tegrityCourseDetailsScreen = moodleCourseDetailsScreen.clickTegrityLink();
		}catch(Exception e){
			if(browser.isElementPresentWithWait(By.xpath(".//div[@id='alertWindow']"), 3)){
				browser.close();
				browser.pause(1000);
				browser.switchToWindow(courseFullName1);
				tegrityCourseDetailsScreen = moodleCourseDetailsScreen.clickTegrityLink();
			} else {
				throw e;
			}
		}
		
		String expectedGreetingText = instructorName + " " + instructorSurname;
		
		Assert.verifyEquals(tegrityCourseDetailsScreen.getUserNameText(), expectedGreetingText, "Greetin text is incorrect");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isCoursePresent(courseShortName1), "Course is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isSearchOptionPresent(), "Search option is absent");		
	}
	
	@Test(description = "Check Tegrity link behaves correctly for Moodle student", dependsOnMethods = { "checkTegrityLinkIsPresentInStudentsCourse" })
	public void checkTegrityLinkBehavesCorrectlyForMoodleStudent() throws Exception {

		moodleHomeScreen = moodleApplication.loginToMoodle(studentLogin, password);
		moodleCourseDetailsScreen = moodleHomeScreen.goToCreatedCourse(courseFullName1);
		tegrityCourseDetailsScreen = moodleCourseDetailsScreen.clickTegrityLink();
		
		String expectedGreetingText = studentName + " " + studentSurname;

		Assert.verifyEquals(tegrityCourseDetailsScreen.getUserNameText(), expectedGreetingText, "Greeting text is incorrect");
		Assert.verifyFalse(tegrityCourseDetailsScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isCoursePresent(courseShortName1), "Course is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isSearchOptionPresent(), "Search option is absent");		
	}
	
	@Test(description = "Check Tegrity Welcome page for Moodle instructor", dependsOnMethods = { "checkTegrityLinkIsPresentInInstructorsCourse" })
	public void checkTegrityWelcomePageForInstructor() throws Exception {
	
		moodleHomeScreen = moodleApplication.loginToMoodle(instructorLogin, password);
		moodleCourseDetailsScreen = moodleHomeScreen.goToCreatedCourse(courseFullName1);
		tegrityCourseDetailsScreen = moodleCourseDetailsScreen.clickTegrityLink();
		tegrityIntroductionScreen = tegrityCourseDetailsScreen.goToCourses();

		String expectedUserName = instructorName + " " + instructorSurname;   
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), expectedUserName);	
			
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseShortName1), "Course " + courseShortName1 + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseShortName2), "Course " + courseShortName2 + " is absent");
		String sandboxCourse = instructorName + " " + instructorSurname+ " " + "sandbox course";   
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is absent");	
		
		Assert.verifyTrue(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");	
	}

	@Test(description = "Check Tegrity Welcome page for Moodle student", dependsOnMethods = { "checkTegrityLinkIsPresentInStudentsCourse" })
	public void checkTegrityWelcomePageForStudent() throws Exception {
		
		moodleHomeScreen = moodleApplication.loginToMoodle(studentLogin, password);
		moodleCourseDetailsScreen = moodleHomeScreen.goToCreatedCourse(courseFullName1);
		tegrityCourseDetailsScreen = moodleCourseDetailsScreen.clickTegrityLink();
		tegrityIntroductionScreen = tegrityCourseDetailsScreen.goToCourses();

		String expectedUserName = studentName + " " + studentSurname;
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), expectedUserName);
				
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseShortName1), "Course " + courseShortName1 + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseShortName2), "Course " + courseShortName2 + " is absent");	
		String sandboxCourse = "sandbox course";   
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is present");
		
		Assert.verifyFalse(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
	}

	private String getRandomString() {
		return RandomStringUtils.randomNumeric(5);
	}

	public void prepareDataInMoodle() throws Exception {
		
		studentRS = moodleApiUtils.createUser(studentLogin, password, studentName, studentSurname);
		browser.pause(1000);
		instructorRS = moodleApiUtils.createUser(instructorLogin, password, instructorName, instructorSurname);
		browser.pause(1000);
		categoryRS1 = moodleApiUtils.createCategory(category1);
		courseRS1 = moodleApiUtils.createCourseInsideCategory(courseFullName1, courseShortName1, categoryRS1);

		categoryRS2 = moodleApiUtils.createCategory(category2);
		courseRS2 = moodleApiUtils.createCourseInsideCategory(courseFullName2, courseShortName2, categoryRS2);

		moodleApiUtils.enrollToCourseAsStudent(studentRS, courseRS1);
		moodleApiUtils.enrollToCourseAsInstructor(instructorRS, courseRS1);

		moodleApiUtils.enrollToCourseAsStudent(studentRS, courseRS2);
		moodleApiUtils.enrollToCourseAsInstructor(instructorRS, courseRS2);
	}
}
