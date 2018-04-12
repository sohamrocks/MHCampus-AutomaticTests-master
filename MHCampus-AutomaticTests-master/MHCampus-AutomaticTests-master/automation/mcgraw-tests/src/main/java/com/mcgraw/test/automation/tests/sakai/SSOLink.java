package com.mcgraw.test.automation.tests.sakai;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.sakai.SakaiTool;
import com.mcgraw.test.automation.api.sakai.SakaiUserRole;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewSite;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewUser;
import com.mcgraw.test.automation.api.sakai.service.ISakaiApiService;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.SakaiApplication;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceDashboardScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiAdminHomePage;
import com.mcgraw.test.automation.ui.sakai.SakaiCourseDetailsScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiExternalToolsScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiHomeScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiSetupExternalToolsScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class SSOLink extends BaseTest {

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

	@Autowired
	private ISakaiApiService sakaiApiService;

	private static String SAKAI_FRAME = "First";

	private AddNewUser student;
	private AddNewUser instructor;
	private AddNewSite site1;
	private AddNewSite site2;

	MhCampusIntroductionScreen mhCampusIntroductionScreen;
	MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	SakaiHomeScreen sakaiHomeScreen;
	SakaiCourseDetailsScreen sakaiCourseDetailsScreen;
	SakaiAdminHomePage sakaiAdminHomePage;
	SakaiExternalToolsScreen sakaiExternalToolsScreen;
	SakaiSetupExternalToolsScreen sakaiSetupExternalToolsScreen;

	private InstanceCredentials instance;
	
	private boolean checkBoxShowActiveCourse = false;

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		prepareDataInSakai();

		try{
			instance = tegrityAdministrationApplication.createNewMhCampusInstance();
		}catch(Exception e){
			Logger.info("Failed to create MH Campus instance, trying again...");
			browser.pause(60000);
			instance = tegrityAdministrationApplication.createNewMhCampusInstance();
		}
		
		browser.pause(mhCampusInstanceApplication.CREATE_NEW_INSTANCE_TIMEOUT);
		
		checkBoxShowActiveCourse = tegrityAdministrationApplication.getCheckBoxShowActiveCourse();
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAndAcceptTermsOfUse(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);	
		mhCampusInstanceConnectorsScreen.configureSakaiAuthorizationConnector(sakaiApplication.sakaiAuthorizationExtendedProperties
				.replace("<CustomerNumber>", instance.customerNumber));	
		MhCampusInstanceDashboardScreen mhCampusInstanceDashboardScreen = mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		mhCampusInstanceDashboardScreen.configSakaiIntegration("MHCampus");

		sakaiApplication.completeMhCampusSetupWithSakai(site1.getSiteid(), instance.customerNumber, instance.sharedSecret);
		
		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		
		if(instance != null)
			tegrityAdministrationApplication.deleteMhCampusInstance(instance.customerNumber);
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
	
	@AfterMethod
	public void logoutFromSakai() throws Exception {		
		sakaiApplication.logoutFromSakai();
	}

	@Test(description = "Check MH Campus link is present for Sakai instructor")
	public void checkMhCampusLinkIsPresentInInstructorsCourse() throws Exception {

		sakaiHomeScreen = sakaiApplication.loginToSakai(instructorLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(site1.getSiteid());

		Assert.assertEquals(sakaiCourseDetailsScreen.getMhCampusLinksCount(), 1, "Wrong count of MH Campus links for instructor's course "
				+ site1.getSiteid());
	}

	@Test(description = "Check MH Campus link is present for Sakai student")
	public void checkMhCampusLinkIsPresentInStudentsCourse() throws Exception {

		sakaiHomeScreen = sakaiApplication.loginToSakai(studentLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(site1.getSiteid());
		Assert.assertEquals(sakaiCourseDetailsScreen.getMhCampusLinksCount(), 1, "Wrong count of MH Campus links for student's course "
				+ site1.getSiteid());
	}

	@Test(description = "Check MH Campus link behaves correctly for Sakai instructor", dependsOnMethods = { "checkMhCampusLinkIsPresentInInstructorsCourse" })
	public void checkMhCampusLinkBehavesCorrectlyForInstructor() throws Exception {

		sakaiHomeScreen = sakaiApplication.loginToSakai(instructorLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(site1.getSiteid());
		mhCampusIntroductionScreen = sakaiCourseDetailsScreen.clickMhCampusLink();

		String expectedGreetingText = "Hi " + instructorName + " " + instructorSurname + " -";
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(SAKAI_FRAME), expectedGreetingText,
				"Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(SAKAI_FRAME));
		
		Logger.error("mhCampusIntroductionScreen.getFrameAddress(SAKAI_FRAME): " + mhCampusIntroductionScreen.getFrameAddress(SAKAI_FRAME));
		Logger.error("instance.pageAddressFromEmail.toLowerCase(): " + instance.pageAddressFromEmail.toLowerCase());
		mhCampusIntroductionScreen.acceptRules(SAKAI_FRAME);

		String expectedUserName = (instructorName + " " + instructorSurname).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(SAKAI_FRAME), expectedUserName, "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(SAKAI_FRAME), "Search option is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(site1.getSiteid(), SAKAI_FRAME), "Course " + site1.getSiteid() + " is absent");
		// Temporarily disabled
		/*if(!checkBoxShowActiveCourse){
			Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(site2.getSiteid(), SAKAI_FRAME), "Course " + site2.getSiteid() + " is absent");
		}*/
	}

	@Test(description = "Check MH Campus link behaves correctly for Sakai student", dependsOnMethods = { "checkMhCampusLinkIsPresentInStudentsCourse" })
	public void checkMhCampusLinkBehavesCorrectlyForStudent() throws Exception {

		sakaiHomeScreen = sakaiApplication.loginToSakai(studentLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(site1.getSiteid());
		mhCampusIntroductionScreen = sakaiCourseDetailsScreen.clickMhCampusLink();
		String expectedGreetingText = "Hi " + studentName + " " + studentSurname + " -";

		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(SAKAI_FRAME), expectedGreetingText,
				"Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(SAKAI_FRAME), "Rules text is incorrect");
		mhCampusIntroductionScreen.acceptRules(SAKAI_FRAME);

		String expectedUserName = (studentName + " " + studentSurname).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(SAKAI_FRAME), expectedUserName, "User name is incorrect");
		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(SAKAI_FRAME), "Search option is present");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(site1.getSiteid(), SAKAI_FRAME), "Course " + site1.getSiteid() + " is absent");
		// Temporarily disabled
		/*if(!checkBoxShowActiveCourse){
			Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(site2.getSiteid(), SAKAI_FRAME), "Course " + site2.getSiteid() + " is absent");
		}*/
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
