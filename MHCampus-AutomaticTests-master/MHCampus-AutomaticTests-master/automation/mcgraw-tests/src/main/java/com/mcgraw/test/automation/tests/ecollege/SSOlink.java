package com.mcgraw.test.automation.tests.ecollege;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.ECollegeApplication;
import com.mcgraw.test.automation.ui.ecollege.ECollegeCourseDetailsScreen;
import com.mcgraw.test.automation.ui.ecollege.ECollegeHomeScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceDashboardScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class SSOlink extends BaseTest {
	
	@Autowired
	private ECollegeApplication eCollegeApplication;

	private String ecollegePassword = "123456";

	private String ecollegeInstructorName1 = "MHCampus eCollege Instructor 1";
	private String ecollegeInstructorLogin1 = "mh_epaminstructor1";

	private String ecollegeInstructorLogin2 = "mh_epaminstructor2";

	private String ecollegeStudentName1 = "MHCampus eCollege Student 1";
	private String ecollegeStudentLogin1 = "mh_epamstudent1";

	private String ecollegeCourseName1 = "MHCAMPUS ECOLLEGE TEST COURSE 1";
	private String ecollegeCourseShortName1 = "mhcampus_epamcourse1";

	private String ecollegeCourseName2 = "MHCAMPUS ECOLLEGE TEST COURSE 2";

	private InstanceCredentials instance;
	
	private boolean checkBoxShowActiveCourse = false;

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		try{
			instance = tegrityAdministrationApplication.createNewMhCampusInstance();
		}catch(Exception e){
			Logger.info("Failed to create MH Campus instance, trying again...");
			browser.pause(60000);
			instance = tegrityAdministrationApplication.createNewMhCampusInstance();
		}	
		
		browser.pause(mhCampusInstanceApplication.CREATE_NEW_INSTANCE_TIMEOUT);
		
		checkBoxShowActiveCourse = tegrityAdministrationApplication.getCheckBoxShowActiveCourse();
		MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAndAcceptTermsOfUse(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.configureCustomAuthorizationConnector(eCollegeApplication.ecollegeTitle,
				eCollegeApplication.ecollegeAuthorizationServiceUrl, eCollegeApplication.ecollegeAuthorizationExtendedProperties);
		MhCampusInstanceDashboardScreen mhCampusInstanceDashboardScreen = mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		mhCampusInstanceDashboardScreen.configEcollegeIntegration(instance.customerNumber, "MHCampus");

		eCollegeApplication.completeMhCampusSetupWithEcollege(ecollegeInstructorLogin2, ecollegePassword, ecollegeCourseShortName1,
				instance.customerNumber);

		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);
	}
	
	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		if(instance != null)
			tegrityAdministrationApplication.deleteMhCampusInstance(instance.customerNumber);
	}
		
	@AfterMethod(description = "Guarantee to close new windows even if test method failed")
	public void closeExtraBrowserWindows() {
		browser.closeAllWindowsExceptFirst();
	}	
	
	@AfterMethod  
	public void closeAllWindowsExceptFirst() throws Exception {
		browser.closeAllWindowsExceptFirst();
	}

	@Test(description = "For instructor MH Campus link bahaves correctly")
	public void checkSSOLinkWorksUnderInstructor() {
		ECollegeHomeScreen eCollegeHomeScreen = eCollegeApplication.loginToEcollege(ecollegeInstructorLogin1, ecollegePassword);
		ECollegeCourseDetailsScreen eCollegeCourseDetailsScreen = eCollegeHomeScreen.goToCourse(ecollegeCourseShortName1);
		MhCampusIntroductionScreen mhCampusIntroductionScreen = eCollegeCourseDetailsScreen.openMhCampusInstance();

		String expectedGreetingText = "Hi " + ecollegeInstructorName1 + " -";
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(), expectedGreetingText, "Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(), "Rules text is incorrect");
		Assert.verifyTrue(browser.getCurrentUrl().replace("https", "http").contains(instance.pageAddressFromEmail.toLowerCase()),
				"Not on the instance address");

		mhCampusIntroductionScreen.acceptRules();

		String expectedUserName = (ecollegeInstructorName1).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName);
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(ecollegeCourseName1), "Course is " + ecollegeCourseName1 + " absent");
		if(!checkBoxShowActiveCourse){
			Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(ecollegeCourseName2), "Course is " + ecollegeCourseName2 + " absent");
		}
		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
	}

	@Test(description = "For student MH Campus link bahaves correctly")
	public void checkSSOLinkWorksUnderStudent() {
		ECollegeHomeScreen eCollegeHomeScreen = eCollegeApplication.loginToEcollege(ecollegeStudentLogin1, ecollegePassword);
		ECollegeCourseDetailsScreen eCollegeCourseDetailsScreen = eCollegeHomeScreen.goToCourse(ecollegeCourseShortName1);
		MhCampusIntroductionScreen mhCampusIntroductionScreen = eCollegeCourseDetailsScreen.openMhCampusInstance();

		String expectedGreetingText = "Hi " + ecollegeStudentName1 + " -";
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(), expectedGreetingText, "Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(), "Rules text is incorrect");
		Assert.verifyTrue(browser.getCurrentUrl().replace("https", "http").contains(instance.pageAddressFromEmail.toLowerCase()));

		mhCampusIntroductionScreen.acceptRules();

		String expectedUserName = (ecollegeStudentName1).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName);
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(ecollegeCourseName1), "Course is " + ecollegeCourseName1 + " absent");
		if(!checkBoxShowActiveCourse){
			Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(ecollegeCourseName2), "Course is " + ecollegeCourseName2 + " absent");
		}
		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(), "Search option is present");

	}
}
