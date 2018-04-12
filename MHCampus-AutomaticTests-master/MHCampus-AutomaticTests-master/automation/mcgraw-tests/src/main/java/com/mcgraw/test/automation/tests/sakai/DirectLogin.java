package com.mcgraw.test.automation.tests.sakai;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.sakai.SakaiTool;
import com.mcgraw.test.automation.api.sakai.SakaiUserRole;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewSite;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewUser;
import com.mcgraw.test.automation.api.sakai.service.ISakaiApiService;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.SakaiApplication;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceDashboardScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class DirectLogin extends BaseTest {

	@Autowired
	private SakaiApplication sakaiApplication;

	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom1 = "epamcourse1" + getRandomString();
	private String courseRandom2 = "epamcourse2" + getRandomString();

	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;
	private String studentSurname = "StudentSurname" + studentRandom;

	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String instructorSurname = "InstructorSurname" + instructorRandom;

	private String password = "123qweA@";

	MhCampusIntroductionScreen mhCampusIntroductionScreen;
	MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	MhCampusInstanceDashboardScreen mhCampusInstanceDashboardScreen;

	@Autowired
	private ISakaiApiService sakaiApiService;

	private AddNewUser student;
	private AddNewUser instructor;
	private AddNewSite site1;
	private AddNewSite site2;

	private int numOfSlave = 4;
	
	private InstanceCredentials instance;

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		prepareDataInSakai();

		instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);

		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
		mhCampusInstanceConnectorsScreen.configureSakaiAuthorizationConnector(sakaiApplication.sakaiAuthorizationExtendedProperties
				.replace("<CustomerNumber>", instance.customerNumber));
		mhCampusInstanceConnectorsScreen.configureSakaiAuthenticationConnector(sakaiApplication.sakaiAuthenticationExtendedProperties
				.replace("<CustomerNumber>", instance.customerNumber));
		mhCampusInstanceDashboardScreen = mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();

		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		
		if(site1 != null)
			sakaiApiService.deletePageWithToolFromSite(site1, SakaiTool.LINK_TOOL);
		if(site2 != null)
			sakaiApiService.deletePageWithToolFromSite(site2, SakaiTool.LINK_TOOL);
		if(student != null)
			sakaiApiService.deleteUser(student.getEid());
		if(instructor != null)
			sakaiApiService.deleteUser(instructor.getEid());
		if(site1 != null)
			sakaiApiService.deleteSite(site1.getSiteid());
		if(site2 != null)
			sakaiApiService.deleteSite(site2.getSiteid());
	}

	@Test(description = "Check connectors are availiable")
	public void checkAuthenticationAndAuthorizationConnectorsAreAvailable() throws InterruptedException {
        
		mhCampusInstanceConnectorsScreen = mhCampusInstanceDashboardScreen.clickManageAairs();
		Assert.assertTrue(mhCampusInstanceConnectorsScreen.isAuthenticationConnectorsAvailable());
		Assert.assertTrue(mhCampusInstanceConnectorsScreen.isAuthorizationConnectorsAvailable());

	}

	@Test(description = "For Sakai instructor MH Campus link bahaves correctly", dependsOnMethods = { "checkAuthenticationAndAuthorizationConnectorsAreAvailable" })
	public void checkDirectLoginForSakaiInstructor() {

		mhCampusIntroductionScreen = mhCampusInstanceApplication
				.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, instructorLogin, password);

		String expectedGreetingText = "Hi " + instructorName + " " + instructorSurname + " -";

		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(), expectedGreetingText, "Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent());

		mhCampusIntroductionScreen.acceptRules();

		String expectedUserName = (instructorName + " " + instructorSurname).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName, "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(site1.getShortdesc()), "Course" + site1.getShortdesc() + " is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(site2.getShortdesc()), "Course" + site2.getShortdesc() + " is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(), "Search option is absent");

	}

	@Test(description = "For Sakai student MH Campus link bahaves correctly", dependsOnMethods = { "checkAuthenticationAndAuthorizationConnectorsAreAvailable" })
	public void checkDirectLoginForSakaiStudent() {

		mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);

		String expectedGreetingText = "Hi " + studentName + " " + studentSurname + " -";
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(), expectedGreetingText, "Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent());

		mhCampusIntroductionScreen.acceptRules();

		String expectedUserName = (studentName + " " + studentSurname).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(), expectedUserName, "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(site1.getShortdesc()), "Course" + site1.getShortdesc() + " is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(site2.getShortdesc()), "Course" + site2.getShortdesc() + " is absent");
		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(), "Search option is absent");

	}

	private String getRandomString() {
		return RandomStringUtils.randomNumeric(5);
	}

	public void prepareDataInSakai() throws Exception {

		student = sakaiApiService.createUser(studentLogin, password, studentName, studentSurname);
		instructor = sakaiApiService.createUser(instructorLogin, password, instructorName, instructorSurname);

		site1 = sakaiApiService.addNewSite(courseRandom1);
		sakaiApiService.addMemberToSiteWithRole(site1, student, SakaiUserRole.STUDENT);
		sakaiApiService.addMemberToSiteWithRole(site1, instructor, SakaiUserRole.INSTRUCTOR);
		sakaiApiService.addNewToolToSite(site1, SakaiTool.LINK_TOOL);

		site2 = sakaiApiService.addNewSite(courseRandom2);
		sakaiApiService.addMemberToSiteWithRole(site2, student, SakaiUserRole.STUDENT);
		sakaiApiService.addMemberToSiteWithRole(site2, instructor, SakaiUserRole.INSTRUCTOR);
		sakaiApiService.addNewToolToSite(site2, SakaiTool.LINK_TOOL);

	}
}
