package com.mcgraw.test.automation.tests.moodle;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
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
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.moodle.MoodleCourseDetailsScreen;
import com.mcgraw.test.automation.ui.moodle.MoodleHomeScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class SSOLink extends BaseTest {

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
	MhCampusIntroductionScreen mhCampusIntroductionScreen;
	MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;

	@Autowired
	private MoodleApiUtils moodleApiUtils;

	private MoodleUserRS studentRS;
	private MoodleUserRS instructorRS;
	private MoodleCourseRS courseRS1;
	private MoodleCourseRS courseRS2;
	private MoodleCategoryRS categoryRS1;
	private MoodleCategoryRS categoryRS2;

	private InstanceCredentials instance;
	
	private boolean checkBoxShowActiveCourse = false;

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		prepareDataInMoodle();

		try{
			instance = tegrityAdministrationApplication.createNewMhCampusInstance();
		}catch(Exception e){
			Logger.info("Failed to create MH Campus instance, trying again...");
			browser.pause(60000);
			instance = tegrityAdministrationApplication.createNewMhCampusInstance();
		}
		
		browser.pause(mhCampusInstanceApplication.CREATE_NEW_INSTANCE_TIMEOUT);
		
		checkBoxShowActiveCourse = tegrityAdministrationApplication.getCheckBoxShowActiveCourse();
		moodleApplication.completeMhCampusSetupWithMoodle(instance.customerNumber, instance.sharedSecret);

		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAndAcceptTermsOfUse(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.configureMoodleAuthorizationConnector(moodleApplication.moodleAuthorizationExtendedProperties
				.replace("<secret_value>", instance.sharedSecret));
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();

		moodleHomeScreen = moodleApplication.loginToMoodle(moodleApplication.moodleAdminLogin, moodleApplication.moodleAdminPassword);
		moodleCourseDetailsScreen = moodleHomeScreen.goToCreatedCourse(courseFullName1);
		moodleCourseDetailsScreen.addBlockToCourse(moodleApplication.moodleBlockName);

		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		if(instance != null)
			tegrityAdministrationApplication.deleteMhCampusInstance(instance.customerNumber);
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
		browser.closeAllWindowsExceptFirst();
	}

	@Test(description = "Check MH Campus link is present for Moodle instructor")
	public void checkMhCampusLinkIsPresentInInstructorsCourse() throws Exception {

		moodleHomeScreen = moodleApplication.loginToMoodle(instructorLogin, password);
		moodleCourseDetailsScreen = moodleHomeScreen.goToCreatedCourse(courseFullName1);
		Assert.assertEquals(moodleCourseDetailsScreen.getMhCampusLinksCount(), 1, "Wrong count of MH Campus links for instructor's course "
				+ courseFullName1 + ". Expected [1], actual [" + moodleCourseDetailsScreen.getMhCampusLinksCount() + "]\n");
	}

	@Test(description = "Check MH Campus link is present for Moodle student")
	public void checkMhCampusLinkIsPresentInStudentsCourse() throws Exception {

		moodleHomeScreen = moodleApplication.loginToMoodle(studentLogin, password);
		moodleCourseDetailsScreen = moodleHomeScreen.goToCreatedCourse(courseFullName1);
		Assert.assertEquals(moodleCourseDetailsScreen.getMhCampusLinksCount(), 1, "Wrong count of MH Campus links for student's course "
				+ courseFullName1 + ". Expected [1], actual [" + moodleCourseDetailsScreen.getMhCampusLinksCount() + "]\n");
	}

	@Test(description = "Check MH Campus link behaves correctly for Moodle instructor", dependsOnMethods = { "checkMhCampusLinkIsPresentInInstructorsCourse" })
	public void checkMhCampusLinkBehavesCorrectlyForMoodleInstructor() throws Exception {

		moodleHomeScreen = moodleApplication.loginToMoodle(instructorLogin, password);
		moodleCourseDetailsScreen = moodleHomeScreen.goToCreatedCourse(courseFullName1);
		mhCampusIntroductionScreen = moodleCourseDetailsScreen.clickMhCampusLink();
		
		String expectedGreetingText = "Hi " + instructorName + " " + instructorSurname + " -";
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(), expectedGreetingText,"Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(),"Rules text is incorrect");

		mhCampusIntroductionScreen.acceptRules();

		String expectedUserName = (instructorName + " " + instructorSurname).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName);
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseShortName1),"Course " + courseShortName1 + " is absent");
		if(!checkBoxShowActiveCourse){
			Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseShortName2),"Course " + courseShortName2 + " is absent");
		}
		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(),"Search option is absent");
		
	}

	@Test(description = "Check MH Campus link behaves correctly for Moodle student", dependsOnMethods = { "checkMhCampusLinkIsPresentInStudentsCourse" })
	public void checkMhCampusLinkBehavesCorrectlyForMoodleStudent() throws Exception {

		moodleHomeScreen = moodleApplication.loginToMoodle(studentLogin, password);
		moodleCourseDetailsScreen = moodleHomeScreen.goToCreatedCourse(courseFullName1);
		mhCampusIntroductionScreen = moodleCourseDetailsScreen.clickMhCampusLink();

		String expectedGreetingText = "Hi " + studentName + " " + studentSurname + " -";
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(), expectedGreetingText,"Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(),"Rules text is incorrect");

		mhCampusIntroductionScreen.acceptRules();

		String expectedUserName = (studentName + " " + studentSurname).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName);
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseShortName1),"Course " + courseShortName1 + " is absent");
		if(!checkBoxShowActiveCourse){
			Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseShortName2),"Course " + courseShortName2 + " is absent");
		}
		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(),"Search option is present");
	
	}

	private String getRandomString() {
		return RandomStringUtils.randomNumeric(5);
	}

	public void prepareDataInMoodle() throws Exception {
		studentRS = moodleApiUtils.createUser(studentLogin, password, studentName, studentSurname);
		instructorRS = moodleApiUtils.createUser(instructorLogin, password, instructorName, instructorSurname);

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
