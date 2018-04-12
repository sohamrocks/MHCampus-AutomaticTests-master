package com.mcgraw.test.automation.tests.externaltools;

import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.CourseBlockElement;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.MhCampusConnectSaveCourseSectionPair;
import com.mcgraw.test.automation.ui.mhcampus.course.smartbook.MhCampusSmartBookFlowRunner;
import com.mcgraw.test.automation.ui.mhcampus.course.smartbook.MhCampusSmartBookScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.smartbook.MhCampusSmartBookStudentRegistrationScreen;

public class SmartBookTool extends BaseToolTest {

	@Test(description = "Check Smart Book Tool for instructor")
	public void checkSmartBookToolForInstructor() throws Exception {
		MhCampusSmartBookScreen mhCampusSmartBookScreen = mhCampusCourseBlock.clickSmartBookButtonAsInstructor();

		Assert.assertTrue(mhCampusSmartBookScreen.getiWantThisForMyStudentsBlock().getYouAreCurrentlyInMessage().contains("LearnSmart SmartBook"));
		MhCampusSmartBookFlowRunner mhCampusSmartBookFlowRunner = mhCampusSmartBookScreen.adoptSmartBook();
		Assert.verifyTrue(mhCampusSmartBookFlowRunner.isSmartBookDashboardPresent(), "The SmartBook dashboard is absent");
		Assert.verifyTrue(browser.isCurrentlyOnPageUrl(MhCampusSmartBookFlowRunner.class), notOnDesiredPageMessage(MhCampusSmartBookFlowRunner.class));
	}

	@Test(description = "Check Smart Book Tool for student", dependsOnMethods = "checkSmartBookToolForInstructor")
	public void checkSmartBookToolForStudent() throws Exception {

		MhCampusIntroductionScreen mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(
				instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusIntroductionScreen.acceptRules();

		mhCampusCourseBlock = mhCampusInstanceApplication.expandCourse(courseName);
		Assert.assertTrue(mhCampusCourseBlock.isCourseBlockElementPresent(CourseBlockElement.SMART_BOOK),
				"Smart Book button doesn't present in course " + courseName + " block");

		MhCampusSmartBookStudentRegistrationScreen mhCampusSmartBookStudentRegistrationScreen = 
				mhCampusCourseBlock.clickSmartBookButtonAsStudent();
		
		Assert.assertTrue(mhCampusSmartBookStudentRegistrationScreen.isSmartBookRegistrationDashboardPresent(),
				"Smart Book Student Registration page is not opened correctly");
	}

}
