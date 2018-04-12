package com.mcgraw.test.automation.tests.externaltools;

import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.CourseBlockElement;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.MhCampusConnectSaveCourseSectionPair;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.MhCampusConnectStudentRegistrationScreen;

public class ConnectToolTest extends BaseToolTest {

	@Test(description = "Add 'Connect' for student")
	public void testAddConnectForStudent() throws Exception {
		
		MhCampusConnectSaveCourseSectionPair mhCampusConnectSaveCourseSectionPair = 
				mhCampusInstanceApplication.adoptConnectForCourse(courseName);
		
		Assert.verifyTrue(mhCampusConnectSaveCourseSectionPair.isSuccessMessagePresent(),"Success message is absent");
		Assert.assertTrue(browser.isCurrentlyOnPageUrl(MhCampusConnectSaveCourseSectionPair.class), notOnDesiredPageMessage(MhCampusConnectSaveCourseSectionPair.class));
	}

	@Test(description = "Test if 'Connect' available under student", dependsOnMethods = "testAddConnectForStudent")
	public void testConnectAdoptedUnderStudent() {
		browser.closeAllWindowsExceptFirst();
		MhCampusIntroductionScreen mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(
				instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusIntroductionScreen.acceptRules();
		mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseName, CourseBlockElement.CONNECT);
	}

	@Test(description = "Test if 'Connect' studen registration opened", dependsOnMethods = "testConnectAdoptedUnderStudent")
	public void testConnctStudentRegistrationOpenedUnderStudent() throws Exception {
		MhCampusConnectStudentRegistrationScreen mhCampusConnectStudentRegistrationScreen = 
				mhCampusCourseBlock.clickConnectButtonAlreadyConfigured();
		
		Assert.verifyTrue(browser.isCurrentlyOnPageUrl(MhCampusConnectStudentRegistrationScreen.class));
		Assert.assertTrue(mhCampusConnectStudentRegistrationScreen.isCreateSuccessTextPresent());
	}

}
