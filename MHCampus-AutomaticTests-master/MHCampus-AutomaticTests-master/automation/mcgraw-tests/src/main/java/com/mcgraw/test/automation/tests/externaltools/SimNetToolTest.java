package com.mcgraw.test.automation.tests.externaltools;

import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.CourseBlockElement;
import com.mcgraw.test.automation.ui.mhcampus.course.simnet.MhCampusSimNetPairingPortalForInstructor;
import com.mcgraw.test.automation.ui.mhcampus.course.simnet.MhCampusSimNetPairingPortalForStudent;

public class SimNetToolTest extends BaseToolTest {

	@Test(description = "Add 'SimNet' for student")
	public void testAddSimNetForStudent() {
		mhCampusInstanceApplication.adoptSimNetForCourse(courseName);
		Assert.assertTrue(browser.isCurrentlyOnPageUrl(MhCampusSimNetPairingPortalForInstructor.class), notOnDesiredPageMessage(MhCampusSimNetPairingPortalForInstructor.class));
	}

	@Test(description = "Test if 'SimNet' available under student", dependsOnMethods = "testAddSimNetForStudent")
	public void testSimNetAvailableUnderStudent() {
		browser.closeAllWindowsExceptFirst();
		MhCampusIntroductionScreen mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(
				instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusIntroductionScreen.acceptRules();
		mhCampusCourseBlock = mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseName, CourseBlockElement.SIMNET);
	}

	@Test(description = "Test if 'simnet' class not configured page opened under student", dependsOnMethods = "testSimNetAvailableUnderStudent")
	public void testSimNetClassNotConfiguredPageOpened() {
		MhCampusSimNetPairingPortalForStudent mhCampusSimNetPairingPortalForStudent = mhCampusCourseBlock.clickSimNetButtonAlreadyConfigured();
		Assert.verifyTrue(browser.isCurrentlyOnPageUrl(MhCampusSimNetPairingPortalForStudent.class), notOnDesiredPageMessage(MhCampusSimNetPairingPortalForStudent.class));
		Assert.assertTrue(mhCampusSimNetPairingPortalForStudent.isClassNotConfiguredMessagePresent(),"Class not configured message absent");
	}

}