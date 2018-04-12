package com.mcgraw.test.automation.tests.ecollege;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.ECollegeApplication;
import com.mcgraw.test.automation.ui.ecollege.ECollegeCourseDetailsScreen;
import com.mcgraw.test.automation.ui.ecollege.ECollegeHomeScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityCourseDetailsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityIntroductionScreen;

public class TegritySSOlink extends BaseTest {
	
	private static final String ECOLLEGE_SUB_URL = "ecollege"; 
	
	@Autowired
	private ECollegeApplication eCollegeApplication;
	private ECollegeHomeScreen eCollegeHomeScreen;
	private ECollegeCourseDetailsScreen eCollegeCourseDetailsScreen;

	private String ecollegePassword = "123456";

	//private String ecollegeInstructorName1 = "MHCampus eCollege Instructor 1";
	private String ecollegeInstructorLogin1 = "mh_epaminstructor1";

	private String ecollegeInstructorName2 = "MHCampus eCollege Instructor 2";
	private String ecollegeInstructorLogin2 = "mh_epaminstructor2";

	private String ecollegeStudentName1 = "MHCampus eCollege Student 1";
	private String ecollegeStudentLogin1 = "mh_epamstudent1";

	private String ecollegeCourseName1 = "MHCampus eCollege Test Course 1";
	private String ecollegeCourseShortName1 = "mhcampus_epamcourse1";

	private String ecollegeCourseName2 = "MHCampus eCollege Test Course 2";
	
	private TegrityIntroductionScreen tegrityIntroductionScreen;
	private TegrityInstanceConnectorsScreen tegrityInstanceConnectorsScreen;
	private TegrityCourseDetailsScreen tegrityCourseDetailsScreen;

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		tegrityInstanceConnectorsScreen = tegrityInstanceApplication.loginToTegrityInstanceAsAdminAndClickManageAairsLink();			
		tegrityInstanceConnectorsScreen.deleteAllConnectors();
		
		tegrityInstanceConnectorsScreen.configureCustomAuthorizationConnector(eCollegeApplication.ecollegeTitle,
				eCollegeApplication.ecollegeAuthorizationServiceUrl, eCollegeApplication.ecollegeAuthorizationExtendedProperties);
		tegrityInstanceConnectorsScreen.clickSaveAndContinueButton();
		
		eCollegeApplication.completeTegritySetupWithEcollege(ecollegeInstructorLogin1, ecollegePassword, ecollegeCourseShortName1,
				tegrityInstanceApplication.customerNumber);		
		browser.pause(tegrityInstanceApplication.DIRECT_LOGIN_TIMEOUT);
	}
	
	@AfterMethod(description = "Guarantee to close new windows even if test method failed")
	public void closeExtraBrowserWindows() {
		browser.closeAllWindowsExceptCurrentWithSubURL(ECOLLEGE_SUB_URL);
    	eCollegeApplication.logoutFromEcollege();
	}	

	@Test(description = "For eCollege instructor check Tegrity link is present")
	public void checkTegrityLinkIsPresentInInstructorCourse() throws Exception {

		eCollegeHomeScreen = eCollegeApplication.loginToEcollege(ecollegeInstructorLogin2, ecollegePassword);
		eCollegeCourseDetailsScreen = eCollegeHomeScreen.goToCourse(ecollegeCourseShortName1);
		Assert.assertEquals(eCollegeCourseDetailsScreen.getTegrityLinksCount(), 1, "Wrong count of Tegrity links for instructor's course " + ecollegeCourseShortName1);
	}

	@Test(description = "For eCollege student check Tegrity link is present")
	public void checkTegrityLinkIsPresentInStudentCourse() throws Exception {

		eCollegeHomeScreen = eCollegeApplication.loginToEcollege(ecollegeStudentLogin1, ecollegePassword);
		eCollegeCourseDetailsScreen = eCollegeHomeScreen.goToCourse(ecollegeCourseShortName1);
		Assert.assertEquals(eCollegeCourseDetailsScreen.getTegrityLinksCount(), 1, "Wrong count of Tegrity links for student's course " + ecollegeCourseShortName1);
	}
	
	@Test(description = "For instructor Tegrity link bahaves correctly")
	public void checkSSOLinkWorksUnderInstructor() {
		
		eCollegeHomeScreen = eCollegeApplication.loginToEcollege(ecollegeInstructorLogin2, ecollegePassword);
		eCollegeCourseDetailsScreen = eCollegeHomeScreen.goToCourse(ecollegeCourseShortName1);
		tegrityCourseDetailsScreen = eCollegeCourseDetailsScreen.openTegrityInstance();
		
		Assert.verifyEquals(tegrityCourseDetailsScreen.getUserNameText(), ecollegeInstructorName2, "Greetin text is incorrect");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isCoursePresent(ecollegeCourseName1), "Course " + ecollegeCourseName1 + " is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isSearchOptionPresent(), "Search option is absent");		
	}
	
	@Test(description = "For student Tegrity link bahaves correctly")
	public void checkSSOLinkWorksUnderStudent() {
		
		eCollegeHomeScreen = eCollegeApplication.loginToEcollege(ecollegeStudentLogin1, ecollegePassword);
		eCollegeCourseDetailsScreen = eCollegeHomeScreen.goToCourse(ecollegeCourseShortName1);
		tegrityCourseDetailsScreen = eCollegeCourseDetailsScreen.openTegrityInstance();

		Assert.verifyEquals(tegrityCourseDetailsScreen.getUserNameText(), ecollegeStudentName1, "Greeting text is incorrect");
		Assert.verifyFalse(tegrityCourseDetailsScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isCoursePresent(ecollegeCourseName1), "Course " + ecollegeCourseName1 + " is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isSearchOptionPresent(), "Search option is absent");			
	}

	@Test(description = "Check Tegrity Welcome page for Sakai instructor", dependsOnMethods = { "checkTegrityLinkIsPresentInInstructorCourse" })
	public void checkTegrityWelcomePageForInstructor() throws Exception {
	
		eCollegeHomeScreen = eCollegeApplication.loginToEcollege(ecollegeInstructorLogin2, ecollegePassword);
		eCollegeCourseDetailsScreen = eCollegeHomeScreen.goToCourse(ecollegeCourseShortName1);
		tegrityCourseDetailsScreen = eCollegeCourseDetailsScreen.openTegrityInstance();
		tegrityIntroductionScreen = tegrityCourseDetailsScreen.goToCourses();

		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), ecollegeInstructorName2);				
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(ecollegeCourseName1), "Course " + ecollegeCourseName1 + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(ecollegeCourseName2), "Course " + ecollegeCourseName2 + " is absent");
		String sandboxCourse = ecollegeInstructorName2 + " " + "sandbox course";   
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is absent");			
		Assert.verifyTrue(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");	
	}

	@Test(description = "Check Tegrity Welcome page for Sakai student", dependsOnMethods = { "checkTegrityLinkIsPresentInStudentCourse" })
	public void checkTegrityWelcomePageForStudent() throws Exception {
		
		eCollegeHomeScreen = eCollegeApplication.loginToEcollege(ecollegeStudentLogin1, ecollegePassword);
		eCollegeCourseDetailsScreen = eCollegeHomeScreen.goToCourse(ecollegeCourseShortName1);
		tegrityCourseDetailsScreen = eCollegeCourseDetailsScreen.openTegrityInstance();
		tegrityIntroductionScreen = tegrityCourseDetailsScreen.goToCourses();

		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), ecollegeStudentName1);				
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(ecollegeCourseName1), "Course " + ecollegeCourseName1 + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(ecollegeCourseName2), "Course " + ecollegeCourseName2 + " is absent");	
		String sandboxCourse = "sandbox course";   
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is present");		
		Assert.verifyFalse(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
	}
}
