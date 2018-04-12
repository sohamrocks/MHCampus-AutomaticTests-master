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
import com.mcgraw.test.automation.ui.applications.TegrityInstanceApplicationNoLocalConnector;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceDashboardScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityIntroductionScreen;

public class TegrityDirectLogin extends BaseTest {

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

	private TegrityIntroductionScreen tegrityIntroductionScreen;
	private TegrityInstanceConnectorsScreen tegrityInstanceConnectorsScreen;
	private TegrityInstanceDashboardScreen tegrityInstanceDashboardScreen;

	@Autowired
	private ISakaiApiService sakaiApiService;

	private AddNewUser student;
	private AddNewUser instructor;
	private AddNewSite site1;
	private AddNewSite site2;

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		prepareDataInSakai();
		tegrityInstanceConnectorsScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdminAndClickManageAairsLink();		
		tegrityInstanceConnectorsScreen.deleteAllConnectors();	
		
		tegrityInstanceConnectorsScreen.configureSakaiAuthorizationConnector(sakaiApplication.sakaiAuthorizationExtendedProperties
				.replace("<CustomerNumber>", tegrityInstanceApplicationNoLocalConnector.customerNumber));
		tegrityInstanceConnectorsScreen.configureSakaiAuthenticationConnector(sakaiApplication.sakaiServiceUrl, sakaiApplication.sakaiAuthenticationExtendedProperties
				.replace("<CustomerNumber>", tegrityInstanceApplicationNoLocalConnector.customerNumber));
		tegrityInstanceDashboardScreen = tegrityInstanceConnectorsScreen.clickSaveAndContinueButton();
		
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

	@Test(description = "Check connectors are availiable")
	public void checkAuthenticationAndAuthorizationConnectorsAreAvailable() throws InterruptedException {
		
		tegrityInstanceConnectorsScreen = tegrityInstanceDashboardScreen.clickManageAairs();
		Assert.assertTrue(tegrityInstanceConnectorsScreen.isAuthenticationConnectorsAvailable());
		Assert.assertTrue(tegrityInstanceConnectorsScreen.isAuthorizationConnectorsAvailable());
		tegrityInstanceConnectorsScreen.logOut();
	}

	@Test(description = "For Sakai instructor Tegrity link bahaves correctly", dependsOnMethods = { "checkAuthenticationAndAuthorizationConnectorsAreAvailable" })
	public void checkDirectLoginForSakaiInstructor() {
        
        tegrityIntroductionScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityAsUser(instructorLogin, password);
	
		String expectedUserName = (instructorName + " " + instructorSurname);   
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), expectedUserName);		
		
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(site1.getShortdesc()), "Course " + site1.getShortdesc() + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(site2.getShortdesc()), "Course " + site2.getShortdesc() + " is absent");
		String sandboxCourse = (instructorName + " " + instructorSurname+ " " + "sandbox course");   
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is absent");
		
		Assert.verifyTrue(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");	
		
		tegrityIntroductionScreen.logOut();
	}

	@Test(description = "For Sakai student Tegrity link bahaves correctly", dependsOnMethods = { "checkAuthenticationAndAuthorizationConnectorsAreAvailable" })
	public void checkDirectLoginForSakaiStudent() {
		
        tegrityIntroductionScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityAsUser(studentLogin, password);
		
		String expectedUserName = (studentName + " " + studentSurname);
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), expectedUserName);
				
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(site1.getShortdesc()), "Course " + site1.getShortdesc() + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(site2.getShortdesc()), "Course " + site2.getShortdesc() + " is absent");	
		String sandboxCourse = "sandbox course";   
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is present");
		
		Assert.verifyFalse(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
		
		tegrityIntroductionScreen.logOut();
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
