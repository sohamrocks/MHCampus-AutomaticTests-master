package com.mcgraw.test.automation.tests.myclasses;


import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.TegrityInstanceApplicationMyClasses;
import com.mcgraw.test.automation.ui.connect.ConnectCourseDetailsScreen;
import com.mcgraw.test.automation.ui.connect.ConnectLoginScreen;
import com.mcgraw.test.automation.ui.connect.ConnectStudentCompleteRegistrationScreen;
import com.mcgraw.test.automation.ui.connect.ConnectStudentCourseDetailsScreen;
import com.mcgraw.test.automation.ui.connect.ConnectStudentRegistrationScreen;
import com.mcgraw.test.automation.ui.connect.ConnectWelcomeScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityCourseDetailsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityIntroductionScreen;

public class IntegrationTegrityWithConnect extends BaseTest {

	@Autowired
	protected TegrityInstanceApplicationMyClasses tegrityInstanceApplicationMyClasses;

	private String studentLogin;
	private String studentFullName;

	private String instructorLogin = "mcuiins@mail.com";
	private String instructorFullName = "MCUI Instructor";

	private String password = "Mc123456";
	
	private String courseName1 = "UI Testing";
	
	private String courseName2 = "UI Testing 2";
	private final String urlToConnect = "http://connectqastg.mheducation.com/";
	private final String urlToRegisterNewStudent = "http://connectqastg.mheducation.com/class/m-instructor-ui-testing-1";
	private final String urlForExistingStudent = "http://connectqastg.mheducation.com/class/m-instructor-ui-testing-2-2";
	
	private final String accessCode = "20OF-Q07C-IBIK-GFFR-EJ87";

	private ConnectLoginScreen connectLoginScreen;
	private ConnectWelcomeScreen connectWelcomeScreen;
	private ConnectCourseDetailsScreen connectCourseDetailsScreen;
	private ConnectStudentRegistrationScreen connectStudentRegistrationScreen;
	private ConnectStudentCompleteRegistrationScreen connectStudentCompleteRegistrationScreen;
	private ConnectStudentCourseDetailsScreen connectStudentCourseDetailsScreen;
	private TegrityCourseDetailsScreen tegrityCourseDetailsScreen;
	private TegrityIntroductionScreen tegrityIntroductionScreen;

	@BeforeClass
	public void testSuiteSetup() throws Exception {		
		prepareTestData();
	}
	
	@AfterMethod  
	public void closeAllWindowsExceptFirst() throws Exception {
		browser.closeAllWindowsExceptFirst();
	}
	
	@Test(description = "Check Direct Login into Connect for Instructor")
	public void checkDirectLoginIntoConnectForInstructor() throws Exception {
		
		connectLoginScreen = tegrityInstanceApplicationMyClasses.openConnectLoginScreenForInstructor(urlToConnect);
		connectWelcomeScreen = connectLoginScreen.loginIntoConnect(instructorLogin, password);
		
		Assert.verifyEquals(connectWelcomeScreen.getUserFullNameName(), instructorFullName, "Instructor name is incorrect");
		Assert.verifyTrue(connectWelcomeScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");	
		Assert.verifyTrue(connectWelcomeScreen.isCoursePresent(courseName2), "Course " + courseName2 + " is absent");	
		
		connectCourseDetailsScreen = connectWelcomeScreen.goToCourse(courseName1);
	}
	
	@Test(description = "Check Tegrity link behaves correctly for Instructor", dependsOnMethods = { "checkDirectLoginIntoConnectForInstructor" })
	public void checkTegrityLinkBehavesCorrectlyForInstructor() throws Exception {
		
		tegrityCourseDetailsScreen = connectCourseDetailsScreen.goToTegrity();
		
		Assert.verifyEquals(tegrityCourseDetailsScreen.getUserNameText(), instructorFullName, "Greetin text is incorrect");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isCoursePresent(courseName1), "Course is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isSearchOptionPresent(), "Search option is absent");	
		Assert.verifyTrue(tegrityCourseDetailsScreen.isReportsLinkPresent(), "Reports link is absent");	
		Assert.verifyTrue(tegrityCourseDetailsScreen.isCourseSettingsOptionPresent(), "Course Settings option is absent");	
		
		tegrityIntroductionScreen = tegrityCourseDetailsScreen.goToCourses();

		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), instructorFullName);	
			
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName2), "Course " + courseName2 + " is absent");
		String sandboxCourse = instructorFullName+ " " + "sandbox course";   
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is absent");	
		
		Assert.verifyTrue(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");	
	}
	
	@Test(description = "Check Tegrity link behaves correctly for Student", dependsOnMethods = { "checkTegrityLinkBehavesCorrectlyForInstructor" })
	public void checkTegrityLinkBehavesCorrectlyForStudentWithCourse1() throws Exception {
		
		connectStudentRegistrationScreen = tegrityInstanceApplicationMyClasses.openConnectLoginScreenForStudent(urlToRegisterNewStudent);  
		connectStudentCompleteRegistrationScreen = connectStudentRegistrationScreen.registerAsNewStudentToConnect(studentLogin, password, accessCode);
		connectStudentCourseDetailsScreen = connectStudentCompleteRegistrationScreen.goToConnect(false);
		tegrityCourseDetailsScreen = connectStudentCourseDetailsScreen.goToTegrity(courseName1);
		
		Assert.verifyEquals(tegrityCourseDetailsScreen.getUserNameText(), studentFullName, "Greetin text is incorrect");
		Assert.verifyFalse(tegrityCourseDetailsScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isCoursePresent(courseName1), "Course is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isSearchOptionPresent(), "Search option is absent");	
		Assert.verifyFalse(tegrityCourseDetailsScreen.isReportsLinkPresent(), "Reports link is present");	
		Assert.verifyFalse(tegrityCourseDetailsScreen.isCourseSettingsOptionPresent(), "Course Settings option is present");	
		
		tegrityIntroductionScreen = tegrityCourseDetailsScreen.goToCourses();
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");
		String sandboxCourse = "sandbox course";   
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is present");
		
		Assert.verifyFalse(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
	}
	
	@Test(description = "Check Tegrity link behaves correctly for Student", dependsOnMethods = { "checkTegrityLinkBehavesCorrectlyForStudentWithCourse1" })
	public void checkTegrityLinkBehavesCorrectlyForStudentWithCourse2() throws Exception {
		
		connectStudentRegistrationScreen = tegrityInstanceApplicationMyClasses.openConnectLoginScreenForStudent(urlForExistingStudent);
		connectStudentCompleteRegistrationScreen = connectStudentRegistrationScreen.loginAsStudentToConnect(studentLogin, password);
		connectStudentCourseDetailsScreen = connectStudentCompleteRegistrationScreen.goToConnect(true);
		tegrityCourseDetailsScreen = connectStudentCourseDetailsScreen.goToTegrity(courseName2);
		
		Assert.verifyEquals(tegrityCourseDetailsScreen.getUserNameText(), studentFullName, "Greetin text is incorrect");
		Assert.verifyFalse(tegrityCourseDetailsScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isCoursePresent(courseName1), "Course is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isSearchOptionPresent(), "Search option is absent");	
		Assert.verifyFalse(tegrityCourseDetailsScreen.isReportsLinkPresent(), "Reports link is present");	
		Assert.verifyFalse(tegrityCourseDetailsScreen.isCourseSettingsOptionPresent(), "Course Settings option is present");	
		
		tegrityIntroductionScreen = tegrityCourseDetailsScreen.goToCourses();
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName2), "Course " + courseName2 + " is absent");
		String sandboxCourse = "sandbox course";   
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is present");
		
		Assert.verifyFalse(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
	}
	
	private void prepareTestData() {
		studentLogin = "studentLogin_" + getRandomString();
		studentFullName =studentLogin + " " + studentLogin;
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}
}