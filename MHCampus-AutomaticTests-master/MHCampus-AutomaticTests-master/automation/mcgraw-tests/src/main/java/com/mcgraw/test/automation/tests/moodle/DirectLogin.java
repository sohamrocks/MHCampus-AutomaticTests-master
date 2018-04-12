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
import com.mcgraw.test.automation.ui.applications.MoodleApplication;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceDashboardScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.moodle.MoodleCourseDetailsScreen;
import com.mcgraw.test.automation.ui.moodle.MoodleHomeScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class DirectLogin extends BaseTest {

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
	private String courseFullName1 = "CourseFull1" + courseRandom;
	private String courseShortName1 = "CourseShort1" + courseRandom;

	private String category2 = "Category2" + courseRandom;
	private String courseFullName2 = "CourseFull2" + courseRandom;
	private String courseShortName2 = "CourseShort2" + courseRandom;

	private String password = "123qweA@";

	MoodleHomeScreen moodleHomeScreen;
	MoodleCourseDetailsScreen moodleCourseDetailsScreen;
	MhCampusIntroductionScreen mhCampusIntroductionScreen;
	MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	MhCampusInstanceDashboardScreen mhCampusInstanceDashboardScreen;

	@Autowired
	private MoodleApiUtils moodleApiUtils;

	private MoodleUserRS studentRS;
	private MoodleUserRS instructorRS;
	private MoodleCourseRS courseRS1;
	private MoodleCategoryRS categoryRS1;
	private MoodleCourseRS courseRS2;
	private MoodleCategoryRS categoryRS2;

	private int numOfSlave = 4;
	
	private InstanceCredentials instance;
	
	

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		prepareDataInMoodle();

		instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);

		moodleApplication.completeMhCampusSetupWithMoodle(instance.customerNumber, instance.sharedSecret);

		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
		mhCampusInstanceConnectorsScreen.configureMoodleAuthenticationConnector(moodleApplication.moodleAuthenticationExtendedProperties
				.replace("<secret_value>", instance.sharedSecret));
		mhCampusInstanceConnectorsScreen.configureMoodleAuthorizationConnector(moodleApplication.moodleAuthorizationExtendedProperties
				.replace("<secret_value>", instance.sharedSecret));
		mhCampusInstanceDashboardScreen = mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		
		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);
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

		mhCampusInstanceConnectorsScreen = mhCampusInstanceDashboardScreen.clickManageAairs();
		Assert.assertTrue(mhCampusInstanceConnectorsScreen.isAuthenticationConnectorsAvailable());
		Assert.assertTrue(mhCampusInstanceConnectorsScreen.isAuthorizationConnectorsAvailable());
	}

	@Test(description = "For Moodle instructor MH Campus link bahaves correctly", dependsOnMethods = { "checkAuthenticationAndAuthorizationConnectorsAreAvailable" })
	public void checkDirectLoginForMoodleInstructor() {

		mhCampusIntroductionScreen = mhCampusInstanceApplication
				.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, instructorLogin, password);

		String expectedGreetingText = "Hi " + instructorName + " " + instructorSurname + " -";
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(), expectedGreetingText,"Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(),"Rules text is incorrect");
		
		mhCampusIntroductionScreen.acceptRules();

		String expectedUserName = (instructorName + " " + instructorSurname).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName);
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseShortName1),"Course " + courseShortName1 + " is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseShortName2),"Course " + courseShortName2 + " is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(),"Search option is absent");
		
	}

	@Test(description = "For Moodle student MH Campus link bahaves correctly", dependsOnMethods = { "checkAuthenticationAndAuthorizationConnectorsAreAvailable" })
	public void checkDirectLoginForMoodleStudent() {

		mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);

		String expectedGreetingText = "Hi " + studentName + " " + studentSurname + " -";		
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(), expectedGreetingText,"Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(),"Rules text is incorrect");

		mhCampusIntroductionScreen.acceptRules();
		
		String expectedUserName = (studentName + " " + studentSurname).toUpperCase();
		
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName);
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseShortName1),"Course " + courseShortName1 + " is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseShortName2),"Course " + courseShortName2 + " is absent");
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
