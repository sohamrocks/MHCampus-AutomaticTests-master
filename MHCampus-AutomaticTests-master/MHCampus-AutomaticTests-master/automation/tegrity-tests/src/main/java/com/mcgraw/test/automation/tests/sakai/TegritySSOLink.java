package com.mcgraw.test.automation.tests.sakai;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.sakai.SakaiTool;
import com.mcgraw.test.automation.api.sakai.SakaiUserRole;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewSite;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewUser;
import com.mcgraw.test.automation.api.sakai.service.ISakaiApiService;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.SakaiApplication;
import com.mcgraw.test.automation.ui.applications.TegrityInstanceApplicationNoLocalConnector;
import com.mcgraw.test.automation.ui.sakai.SakaiAdminHomePage;
import com.mcgraw.test.automation.ui.sakai.SakaiCourseDetailsScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiExternalToolsScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiHomeScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiSetupExternalToolsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityCourseDetailsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityIntroductionScreen;

public class TegritySSOLink extends BaseTest {
	
	private static final String SAKAI_SUB_URL = "sakai"; 

	@Autowired
	private SakaiApplication sakaiApplication;
	
	@Autowired
	protected TegrityInstanceApplicationNoLocalConnector tegrityInstanceApplicationNoLocalConnector;

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

	private AddNewUser student;
	private AddNewUser instructor;
	private AddNewSite site1;
	private AddNewSite site2;

	private TegrityIntroductionScreen tegrityIntroductionScreen;
	private TegrityInstanceConnectorsScreen tegrityInstanceConnectorsScreen;
	private TegrityCourseDetailsScreen tegrityCourseDetailsScreen;
	
	SakaiHomeScreen sakaiHomeScreen;
	SakaiCourseDetailsScreen sakaiCourseDetailsScreen;
	SakaiAdminHomePage sakaiAdminHomePage;
	SakaiExternalToolsScreen sakaiExternalToolsScreen;
	SakaiSetupExternalToolsScreen sakaiSetupExternalToolsScreen;

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		prepareDataInSakai();

		tegrityInstanceConnectorsScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdminAndClickManageAairsLink();			
		tegrityInstanceConnectorsScreen.deleteAllConnectors();	

		tegrityInstanceConnectorsScreen.configureSakaiAuthorizationConnector(sakaiApplication.sakaiAuthorizationExtendedProperties
				.replace("<CustomerNumber>", tegrityInstanceApplicationNoLocalConnector.customerNumber));
		tegrityInstanceConnectorsScreen.clickSaveAndContinueButton();
		
		sakaiApplication.completeTegritySetupWithSakai(site1.getSiteid(), 
				tegrityInstanceApplicationNoLocalConnector.customerNumber, tegrityInstanceApplicationNoLocalConnector.sharedSecret);
		
		browser.pause(tegrityInstanceApplicationNoLocalConnector.DIRECT_LOGIN_TIMEOUT);
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
	
	@BeforeMethod  
	public void closeAllWindowsExceptFirst() throws Exception {
		browser.closeAllWindowsExceptCurrentWithSubURL(SAKAI_SUB_URL);
		sakaiApplication.logoutFromSakai();
	}

	@Test(description = "Check Tegrity link is present for Sakai instructor")
	public void checkTegrityLinkIsPresentInInstructorsCourse() throws Exception {

		sakaiHomeScreen = sakaiApplication.loginToSakai(instructorLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(site1.getSiteid());
		Assert.assertEquals(sakaiCourseDetailsScreen.getTegrityLinksCount(), 1, "Wrong count of Tegrity links for instructor's course "
				+ site1.getSiteid());
	}

	@Test(description = "Check Tegrity link is present for Sakai student")
	public void checkTegrityLinkIsPresentInStudentsCourse() throws Exception {

		sakaiHomeScreen = sakaiApplication.loginToSakai(studentLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(site1.getSiteid());
		Assert.assertEquals(sakaiCourseDetailsScreen.getTegrityLinksCount(), 1, "Wrong count of Tegrity links for student's course "
				+ site1.getSiteid());
	}

	@Test(description = "Check Tegrity link behaves correctly for Sakai instructor", dependsOnMethods = { "checkTegrityLinkIsPresentInInstructorsCourse" })
	public void checkTegrityLinkBehavesCorrectlyForInstructor() throws Exception {

		sakaiHomeScreen = sakaiApplication.loginToSakai(instructorLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(site1.getSiteid());
		tegrityCourseDetailsScreen = sakaiCourseDetailsScreen.clickTegrityLink();
		
		String expectedGreetingText = instructorName + " " + instructorSurname;
		
		Assert.verifyEquals(tegrityCourseDetailsScreen.getUserNameText(), expectedGreetingText, "Greetin text is incorrect");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isCoursePresent(site1.getSiteid()), "Course " + site1.getSiteid() + " is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isSearchOptionPresent(), "Search option is absent");	
	}
	
	@Test(description = "Check Tegrity link behaves correctly for Sakai student", dependsOnMethods = { "checkTegrityLinkIsPresentInStudentsCourse" })
	public void checkTegrityLinkBehavesCorrectlyForStudent() throws Exception {

		sakaiHomeScreen = sakaiApplication.loginToSakai(studentLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(site1.getSiteid());
		tegrityCourseDetailsScreen = sakaiCourseDetailsScreen.clickTegrityLink();
		
		String expectedGreetingText = studentName + " " + studentSurname;

		Assert.verifyEquals(tegrityCourseDetailsScreen.getUserNameText(), expectedGreetingText, "Greeting text is incorrect");
		Assert.verifyFalse(tegrityCourseDetailsScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isCoursePresent(site1.getSiteid()), "Course " + site1.getSiteid() + " is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isSearchOptionPresent(), "Search option is absent");		
	}

	@Test(description = "Check Tegrity Welcome page for Sakai instructor", dependsOnMethods = { "checkTegrityLinkIsPresentInInstructorsCourse" })
	public void checkTegrityWelcomePageForInstructor() throws Exception {
	
		sakaiHomeScreen = sakaiApplication.loginToSakai(instructorLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(site1.getSiteid());
		tegrityCourseDetailsScreen = sakaiCourseDetailsScreen.clickTegrityLink();
		tegrityIntroductionScreen = tegrityCourseDetailsScreen.goToCourses();

		String expectedUserName = instructorName + " " + instructorSurname;   
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), expectedUserName);	
			
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(site1.getSiteid()), "Course " + site1.getSiteid() + " is absent");
		// Temporarily disabled
		//Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(site2.getSiteid()), "Course " + site2.getSiteid() + " is absent");
		String sandboxCourse = instructorName + " " + instructorSurname+ " " + "sandbox course";   
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is absent");	
		
		Assert.verifyTrue(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");	
	}

	@Test(description = "Check Tegrity Welcome page for Sakai student", dependsOnMethods = { "checkTegrityLinkIsPresentInStudentsCourse" })
	public void checkTegrityWelcomePageForStudent() throws Exception {
		
		sakaiHomeScreen = sakaiApplication.loginToSakai(studentLogin, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(site1.getSiteid());
		tegrityCourseDetailsScreen = sakaiCourseDetailsScreen.clickTegrityLink();
		tegrityIntroductionScreen = tegrityCourseDetailsScreen.goToCourses();

		String expectedUserName = studentName + " " + studentSurname;
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), expectedUserName);
				
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(site1.getSiteid()), "Course " + site1.getSiteid() + " is absent");
		// Temporarily disabled
		//Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(site2.getSiteid()), "Course " + site2.getSiteid() + " is absent");	
		String sandboxCourse = "sandbox course";   
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is present");
		
		Assert.verifyFalse(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
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
