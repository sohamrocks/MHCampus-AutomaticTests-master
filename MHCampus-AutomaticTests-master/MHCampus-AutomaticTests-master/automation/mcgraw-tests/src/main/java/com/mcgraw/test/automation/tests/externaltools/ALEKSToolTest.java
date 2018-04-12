package com.mcgraw.test.automation.tests.externaltools;

import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.CourseBlockElement;
import com.mcgraw.test.automation.ui.mhcampus.course.aleks.MhCampusALEKSReadyToUseScreen;

public class ALEKSToolTest extends BaseToolTest {

	@Test(description = "Add 'ALEKS' for student")
	public void testAddALEKSForStudent() {

		mhCampusInstanceApplication.adoptALEKSForCourse(courseName);
		Assert.assertTrue(browser.isCurrentlyOnPageUrl(MhCampusALEKSReadyToUseScreen.class));
	}

	@Test(description = "Test if 'ALEKS' available under student", dependsOnMethods = "testAddALEKSForStudent")
	public void testALEKSAvailableUnderStudent() {
		browser.closeAllWindowsExceptFirst();
		MhCampusIntroductionScreen mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(
				instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusIntroductionScreen.acceptRules();
		mhCampusCourseBlock = mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseName, CourseBlockElement.ALEKS);
	}

	@Test(description = "Test if 'ALEKS' school configuration opened under student", dependsOnMethods = "testALEKSAvailableUnderStudent")
	public void testALEKSSchoolConfigurationOpenedUnderStudent() {
		mhCampusCourseBlock.clickAleksButtonThatAlreadyConfigured();
		Assert.verifyTrue(browser.isCurrentlyOnPageUrl(MhCampusALEKSReadyToUseScreen.class));
		Assert.assertTrue(browser.waitForText("School Configuration", 30));
	}

}
