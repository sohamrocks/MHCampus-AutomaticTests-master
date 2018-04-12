package com.mcgraw.test.automation.tests.moodle;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleCategoryRS;
import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleCourseRS;
import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleUserRS;
import com.mcgraw.test.automation.api.rest.moodle.service.MoodleApiUtils;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.MoodleApplication;
import com.mcgraw.test.automation.ui.moodle.MoodleCourseDetailsScreen;
import com.mcgraw.test.automation.ui.moodle.MoodleHomeScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceDashboardScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityIntroductionScreen;

public class TegrityDirectLogin extends BaseTest {

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

	private String category1 = "Category1" + courseRandom;
	private String courseFullName1 = "CourseFull1" + courseRandom;
	private String courseShortName1 = "CourseShort1" + courseRandom;

	private String category2 = "Category2" + courseRandom;
	private String courseFullName2 = "CourseFull2" + courseRandom;
	private String courseShortName2 = "CourseShort2" + courseRandom;

	private String password = "123qweA@";

	MoodleHomeScreen moodleHomeScreen;
	MoodleCourseDetailsScreen moodleCourseDetailsScreen;
	
	TegrityIntroductionScreen tegrityIntroductionScreen;
	TegrityInstanceConnectorsScreen tegrityInstanceConnectorsScreen;
	TegrityInstanceDashboardScreen tegrityInstanceDashboardScreen;

	@Autowired
	private MoodleApiUtils moodleApiUtils;

	private MoodleUserRS studentRS;
	private MoodleUserRS instructorRS;
	private MoodleCourseRS courseRS1;
	private MoodleCategoryRS categoryRS1;
	private MoodleCourseRS courseRS2;
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
		tegrityInstanceConnectorsScreen.configureMoodleAuthenticationConnector(moodleApplication.moodleAuthenticationExtendedProperties
				.replace("<secret_value>", tegrityInstanceApplication.sharedSecret));
		tegrityInstanceConnectorsScreen.configureMoodleAuthorizationConnector(moodleApplication.moodleAuthorizationExtendedProperties
				.replace("<secret_value>", tegrityInstanceApplication.sharedSecret));
		tegrityInstanceDashboardScreen = tegrityInstanceConnectorsScreen.clickSaveAndContinueButton();
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

	@Test(description = "Check connectors are availiable")
	public void checkAuthenticationAndAuthorizationConnectorsAreAvailable() throws InterruptedException {

		tegrityInstanceConnectorsScreen = tegrityInstanceDashboardScreen.clickManageAairs();
		Assert.assertTrue(tegrityInstanceConnectorsScreen.isAuthenticationConnectorsAvailable());
		Assert.assertTrue(tegrityInstanceConnectorsScreen.isAuthorizationConnectorsAvailable());
		tegrityInstanceConnectorsScreen.logOut();
	}

	@Test(description = "For Moodle instructor Tegrity link bahaves correctly", dependsOnMethods = { "checkAuthenticationAndAuthorizationConnectorsAreAvailable" })
	public void checkDirectLoginForMoodleInstructor() {
		
        tegrityIntroductionScreen = tegrityInstanceApplication.loginToTegrityAsUser(instructorLogin, password);
        browser.pause(2000);
		
		String expectedUserName = (instructorName + " " + instructorSurname);   
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), expectedUserName);		
		
		if (!tegrityIntroductionScreen.isCoursePresent(courseShortName1)){
			Logger.info(String.format("Course with name <%s> is absent on page, trying login again...", courseShortName1));
			browser.waitForElement(By.xpath(".//a[@id='SignOutLink']"), 10).click();
			browser.pause(1000);
			browser.navigate().refresh();
			tegrityIntroductionScreen = tegrityInstanceApplication.loginToTegrityAsUser(instructorLogin, password);
		}		
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseShortName1), "Course " + courseShortName1 + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseShortName2), "Course " + courseShortName2 + " is absent");
		String sandboxCourse = (instructorName + " " + instructorSurname+ " " + "sandbox course");   
		if (!tegrityIntroductionScreen.isCoursePresent(sandboxCourse)){
			Logger.info(String.format("Course with name <%s> is absent on page, trying login again...", sandboxCourse));
			browser.waitForElement(By.xpath(".//a[@id='SignOutLink']"), 10).click();
			browser.pause(1000);
			browser.navigate().refresh();
			tegrityIntroductionScreen = tegrityInstanceApplication.loginToTegrityAsUser(instructorLogin, password);
		}
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is absent");
		
		Assert.verifyTrue(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
		
		tegrityIntroductionScreen.logOut();

	}
	
	@Test(description = "For Moodle student Tegrity link bahaves correctly", dependsOnMethods = { "checkAuthenticationAndAuthorizationConnectorsAreAvailable" })
	public void checkDirectLoginForMoodleStudent() {

        tegrityIntroductionScreen = tegrityInstanceApplication.loginToTegrityAsUser(studentLogin, password);
		
		String expectedUserName = (studentName + " " + studentSurname);
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), expectedUserName);
				
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseShortName1), "Course " + courseShortName1 + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseShortName2), "Course " + courseShortName2 + " is absent");	
		String sandboxCourse = "sandbox course";   
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is present");
		
		Assert.verifyFalse(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
		
		tegrityIntroductionScreen.logOut();
		
	}

	private String getRandomString(int count) {
		return RandomStringUtils.randomNumeric(count);
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
