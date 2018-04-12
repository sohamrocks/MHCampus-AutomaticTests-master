package com.mcgraw.test.automation.tests.externaltools;

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
import com.mcgraw.test.automation.ui.mhcampus.MhCampusReaderType;
import com.mcgraw.test.automation.ui.mhcampus.course.MhCampusCourseBlock;
import com.mcgraw.test.automation.ui.mhcampus.createprovider.CreateProviderBookStore;
import com.mcgraw.test.automation.ui.mhcampus.createprovider.CreateProviderScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class CreateLaunchEbook extends BaseTest {
	
	@Autowired
	private SakaiApplication sakaiApplication;

	@Autowired
	private ISakaiApiService sakaiApiService;

	protected AddNewUser student;
	protected AddNewUser instructor;

	protected AddNewSite site;

	protected String studentLogin;
	protected String studentName;
	protected String studentSurname;

	protected String instructorLogin;
	protected String instructorName;
	protected String instructorSurname;

	protected String password = "123qweA@";

	protected String courseIdZoology;
	protected String courseName;

	protected String bookIsbn = "0077957695";
	private String mailDomain = "@email.com";
	private String titleOfBook = "Gregg College Keyboarding and Document Processing, 11th Ed";
	private String fullTitleOfBook = "Gregg College Keyboarding and Document Processing, 11th Edition, Lessons 1-20";
			
	protected MhCampusCourseBlock mhCampusCourseBlock;

	protected InstanceCredentials instance;	
	
	
	@BeforeClass
	public void testSuiteSetup() throws Exception {
		
		prepareDataInSakai();

		try {
			instance = tegrityAdministrationApplication.createNewMhCampusInstance(MhCampusReaderType.CREATE);
		} catch (Exception e) {
			Logger.info("Failed to create MH Campus instance, trying again...");
			browser.pause(60000);
			instance = tegrityAdministrationApplication.createNewMhCampusInstance(MhCampusReaderType.CREATE);
		}
	
		browser.pause(mhCampusInstanceApplication.CREATE_NEW_INSTANCE_TIMEOUT);
		
		MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication
				.loginToMhCampusInstanceAndAcceptTermsOfUse(instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.configureSakaiAuthorizationConnector(sakaiApplication.sakaiAuthorizationExtendedProperties
				.replace("<CustomerNumber>", instance.customerNumber));
		mhCampusInstanceConnectorsScreen.configureSakaiAuthenticationConnector(sakaiApplication.sakaiAuthenticationExtendedProperties
				.replace("<CustomerNumber>", instance.customerNumber));
		MhCampusInstanceDashboardScreen mhCampusInstanceDashboardScreen = mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		mhCampusInstanceDashboardScreen.configSakaiIntegration("MHCampus");
		
		sakaiApplication.completeMhCampusSetupWithSakai(site.getSiteid(), instance.customerNumber, instance.sharedSecret);

		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);
		browser.pause(5000);
	}
	
	@AfterMethod(description = "Guarantee to close new windows even if test method failed")
	public void closeExtraBrowserWindows() {									
		browser.closeAllWindowsExceptFirst();
	}			
	
	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		
		if(instance != null)
    		tegrityAdministrationApplication.deleteMhCampusInstance(instance.customerNumber);
		if(site != null)
			sakaiApiService.deletePageWithToolFromSite(site, SakaiTool.LINK_TOOL);
		if(student != null)
			sakaiApiService.deleteUser(student.getEid());
		if(instructor != null)
			sakaiApiService.deleteUser(instructor.getEid());
		if(site != null)
			sakaiApiService.deleteSite(site.getSiteid());
	}
	
	@Test(description = "Check Create provider for instructor")
	public void checkCreateProviderForInstructor() throws InterruptedException {

		MhCampusIntroductionScreen mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(
				instance.pageAddressForLogin, instance.institution, instructorLogin, password);
		mhCampusIntroductionScreen.acceptRules();

		mhCampusCourseBlock = mhCampusInstanceApplication.findAndSelectBookForCourse(courseName, bookIsbn);
		CreateProviderScreen createProviderScreen = mhCampusCourseBlock.clickLaunchEbookAsInstructor(instructorLogin + mailDomain);

		Assert.assertTrue(browser.isCurrentlyOnPageUrl(CreateProviderScreen.class), "The page url doesn't contain a domain");	
		
		String titleOfBookFromProviderScreen = createProviderScreen.getTitleOfBook();
		Assert.verifyTrue(titleOfBookFromProviderScreen.contains(titleOfBook) , "The textbook title is not correct. Expected [" + titleOfBook + "]," +
				          " actual [" + titleOfBookFromProviderScreen + "]");		
    	Assert.verifyTrue(createProviderScreen.isContentOfBookAvailiable(),"The block with content of book is not present for instructor");

	}

	@Test(description = "Check Create provider for student", dependsOnMethods = "checkCreateProviderForInstructor")
	public void checkCreateProviderForStudent() throws InterruptedException {

		MhCampusIntroductionScreen mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(
				instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusIntroductionScreen.acceptRules();
		mhCampusCourseBlock = mhCampusInstanceApplication.expandCourse(courseName);
		Assert.assertTrue(mhCampusCourseBlock.isBookPresentInBlock(bookIsbn), "The book with isbn: " + bookIsbn
				+ " doesn't present in course " + courseName + "block");

		CreateProviderBookStore createProviderBookStore = mhCampusCourseBlock.clickLaunchEbookAsStudent(studentLogin + mailDomain);
		Assert.verifyTrue(createProviderBookStore.isAddToCardBtnAppear(),"The Add Card button doesn't present");
		Assert.verifyEquals(createProviderBookStore.getTitleOfBook(), fullTitleOfBook,"The textbook title is not correct");

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

		courseName = "course" + courseRandom1;

		student = sakaiApiService.createUser(studentLogin, password, studentName, studentSurname);
		instructor = sakaiApiService.createUser(instructorLogin, password, instructorName, instructorSurname);

		site = sakaiApiService.addNewSite(courseName);
		sakaiApiService.addMemberToSiteWithRole(site, student, SakaiUserRole.STUDENT);
		sakaiApiService.addMemberToSiteWithRole(site, instructor, SakaiUserRole.INSTRUCTOR);
		sakaiApiService.addNewToolToSite(site, SakaiTool.LINK_TOOL);

	}	
		
	protected String getRandomString() {
		return RandomStringUtils.randomNumeric(5);
	}
	
	
}
