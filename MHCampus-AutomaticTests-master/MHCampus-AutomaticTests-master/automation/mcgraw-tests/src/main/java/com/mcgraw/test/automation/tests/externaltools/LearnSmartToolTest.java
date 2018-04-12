package com.mcgraw.test.automation.tests.externaltools;

import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.CourseBlockElement;
import com.mcgraw.test.automation.ui.mhcampus.course.learnsmart.MhCampusLearnSmartScreenWithOutBar;
import com.mcgraw.test.automation.ui.mhcampus.course.learnsmart.MhCampusLearnSmartStudentRegistrationScreen;

public class LearnSmartToolTest extends BaseToolTest {
	
	@Test(description = "Add 'Learn Smart' for student")
	public void testAddLearnSmartForStudent() {
		mhCampusInstanceApplication.adoptLearnSmartForCourse(courseName);
		Assert.assertTrue(browser.isCurrentlyOnPageUrl(MhCampusLearnSmartScreenWithOutBar.class),
				notOnDesiredPageMessage(MhCampusLearnSmartScreenWithOutBar.class));
		browser.closeAllWindowsExceptFirst(true);
	}
	
	@Test(description = "Test if 'Learn Smart' available under student", dependsOnMethods = "testAddLearnSmartForStudent")
	public void testLearnSmartAvailableUnderStudent() {
		MhCampusIntroductionScreen mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(
				instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusIntroductionScreen.acceptRules();
		mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseName, CourseBlockElement.LEARN_SMART);
	}

	@Test(description = "Test if 'Learn Smart' student registration opened", dependsOnMethods = "testLearnSmartAvailableUnderStudent")
	public void testLearnSmartStudentRegistrationOpenedUnderStudent() {
		mhCampusCourseBlock = mhCampusInstanceApplication.expandCourse(courseName);
		MhCampusLearnSmartStudentRegistrationScreen mhCampusLearnSmartStudentRegistrationScreen = mhCampusCourseBlock
				.clickLearnSmartButtonAsStudent();

		Assert.verifyTrue(browser.isCurrentlyOnPageUrl(MhCampusLearnSmartStudentRegistrationScreen.class), 
				notOnDesiredPageMessage(MhCampusLearnSmartStudentRegistrationScreen.class));
		Assert.assertTrue(mhCampusLearnSmartStudentRegistrationScreen.isCreateSuccessTextPresent(),"Success message is absent");
	}

}
