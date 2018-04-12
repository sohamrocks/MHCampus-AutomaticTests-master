package com.mcgraw.test.automation.tests.externaltools;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;
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
import com.mcgraw.test.automation.ui.mhcampus.course.MhCampusCourseBlock;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

import com.mcgraw.test.automation.api.sakai.SakaiTool;
import com.mcgraw.test.automation.api.sakai.SakaiUserRole;

public class ActiveSimToolTest extends BaseTest {
	
	@Autowired
	private SakaiApplication sakaiApplication;

	@Autowired
	private ISakaiApiService sakaiApiService;

	protected AddNewUser student;
	protected AddNewUser instructor;

	protected AddNewSite siteZoology;

	protected String studentLogin;
	protected String studentName;
	protected String studentSurname;

	protected String instructorLogin;
	protected String instructorName;
	protected String instructorSurname;

	protected String password = "123qweA@";

	protected String courseIdZoology;
	protected String courseNameZoology;

	protected String bookIsbn1 = "0072528362";

	protected InstanceCredentials instance;	
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;
	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	
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
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication
				.loginToMhCampusInstanceAndAcceptTermsOfUse(instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.configureSakaiAuthorizationConnector(sakaiApplication.sakaiAuthorizationExtendedProperties
				.replace("<CustomerNumber>", instance.customerNumber));
		mhCampusInstanceConnectorsScreen.configureSakaiAuthenticationConnector(sakaiApplication.sakaiAuthenticationExtendedProperties
				.replace("<CustomerNumber>", instance.customerNumber));
		MhCampusInstanceDashboardScreen mhCampusInstanceDashboardScreen = mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		mhCampusInstanceDashboardScreen.configSakaiIntegration("MHCampus");
		
		sakaiApplication.completeMhCampusSetupWithSakai(siteZoology.getSiteid(), instance.customerNumber, instance.sharedSecret);
		
		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);		
		
		mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(
				instance.pageAddressForLogin, instance.institution, instructorLogin, password);
		mhCampusIntroductionScreen.acceptRules();		
		mhCampusInstanceApplication.findAndSelectBookForCourse(courseNameZoology, bookIsbn1);
	}
		
	@AfterMethod(description = "Guarantee to close new windows even if test method failed")
	public void closeExtraBrowserWindows() {									
		browser.closeAllWindowsExceptFirst();
		browser.manage().deleteAllCookies();
	}			
	
	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		
		if(instance != null)
			tegrityAdministrationApplication.deleteMhCampusInstance(instance.customerNumber);
		if(siteZoology != null)
			sakaiApiService.deletePageWithToolFromSite(siteZoology, SakaiTool.LINK_TOOL);
		if(student != null)
			sakaiApiService.deleteUser(student.getEid());
		if(instructor != null)
			sakaiApiService.deleteUser(instructor.getEid());
		if(siteZoology != null)
			sakaiApiService.deleteSite(siteZoology.getSiteid());
	}
		
	@Test(description = "Check ActiveSim for instructor")
	public void checkActiveSimToolForInstructor() throws Exception {
		mhCampusInstanceApplication.adoptActivSimForCourse(courseNameZoology);
		
		Assert.assertTrue(browser.getCurrentUrl().contains("activsim.com"), "The page url doesn't contain a ActiveSim domain");
		Assert.assertTrue(browser.getCurrentUrl().contains(instructorName), "The page url doesn't contain a FIRST name of instructor");
		Assert.assertTrue(browser.getCurrentUrl().contains(instructorSurname), "The page url doesn't contain a LAST name of instructor");
	}

	@Test(description = "Check ActiveSim for student")
	public void checkActiveSimToolForStudent() throws Exception {

		mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(
				instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusIntroductionScreen.acceptRules();
		mhCampusInstanceApplication.clickActivSimButtonAlreadyConfigured(courseNameZoology);
				
		Assert.assertTrue(browser.getCurrentUrl().contains("activsim.com"), "The page url doesn't contain a activsim domain");	
		Assert.assertTrue(browser.getCurrentUrl().contains(studentName), "The page url doesn't contain a FIRST name of student");
		Assert.assertTrue(browser.getCurrentUrl().contains(studentSurname), "The page url doesn't contain a LAST name of student");
	}	
		
	public void prepareDataInSakai() throws Exception {

		String studentRandom = getRandomString();
		String instructorRandom = getRandomString();
		String courseRandom1 = getRandomString();

		studentLogin = "student" + studentRandom;
		studentName = "StudentName" + studentRandom;
		studentSurname = "StudentSurname" + studentRandom;

		instructorLogin = "instructor" + instructorRandom;
		instructorName = "InstructorName" + instructorRandom;
		instructorSurname = "InstructorSurname" + instructorRandom;

		courseNameZoology = "course" + courseRandom1;

		student = sakaiApiService.createUser(studentLogin, password, studentName, studentSurname);
		instructor = sakaiApiService.createUser(instructorLogin, password, instructorName, instructorSurname);

		siteZoology = sakaiApiService.addNewSite(courseNameZoology);
		sakaiApiService.addMemberToSiteWithRole(siteZoology, student, SakaiUserRole.STUDENT);
		sakaiApiService.addMemberToSiteWithRole(siteZoology, instructor, SakaiUserRole.INSTRUCTOR);
		sakaiApiService.addNewToolToSite(siteZoology, SakaiTool.LINK_TOOL);

	}	
		
	protected String getRandomString() {
		return RandomStringUtils.randomNumeric(6);
	}

	
}
