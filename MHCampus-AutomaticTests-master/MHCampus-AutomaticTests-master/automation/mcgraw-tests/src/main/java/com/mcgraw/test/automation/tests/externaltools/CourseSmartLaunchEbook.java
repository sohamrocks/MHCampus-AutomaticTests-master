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
import com.mcgraw.test.automation.ui.mhcampus.coursesmart.CourseSmartProfileScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class CourseSmartLaunchEbook extends BaseTest {
	

	@Autowired
	private SakaiApplication sakaiApplication;

	@Autowired
	private ISakaiApiService sakaiApiService;

	protected String password = "123qweA@";
	
	protected AddNewUser student;
	protected AddNewUser instructor;
	
	protected String instructorLogin;
	private String instructorFirstname;
	private String instructorLastname;

	protected String studentLogin;
	private String studentFirstname;
	private String studentLastname;

	
	protected AddNewSite course;

	protected String courseId;
	protected String courseName;

	private String bookIsbn = "0073403466";
	private String titleOfBook = "Basic Statistics For Business And Economics";
	private String mailDomain = "@epam.com";

	protected InstanceCredentials instance;	
	
	protected MhCampusCourseBlock mhCampusCourseBlock;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;
	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	
	
	@BeforeClass
	public void testSuiteSetup() throws Exception {

		prepareDataInSakai();

		try{
		    instance = tegrityAdministrationApplication.createNewMhCampusInstance(MhCampusReaderType.VITAL_SOURCE);
		}catch(Exception e){
			Logger.info("Failed to create MH Campus instance, trying again...");
			browser.pause(60000);
			instance = tegrityAdministrationApplication.createNewMhCampusInstance(MhCampusReaderType.VITAL_SOURCE);
		}
		
		browser.pause(mhCampusInstanceApplication.CREATE_NEW_INSTANCE_TIMEOUT);
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication
				.loginToMhCampusInstanceAndAcceptTermsOfUse(instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.configureSakaiAuthorizationConnector(sakaiApplication.sakaiAuthorizationExtendedProperties
				.replace("<CustomerNumber>", instance.customerNumber));
		mhCampusInstanceConnectorsScreen.configureSakaiAuthenticationConnector(sakaiApplication.sakaiAuthenticationExtendedProperties
				.replace("<CustomerNumber>", instance.customerNumber));
		MhCampusInstanceDashboardScreen mhCampusInstanceDashboardScreen =  mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		mhCampusInstanceDashboardScreen.configSakaiIntegration("MHCampus");
		
		sakaiApplication.completeMhCampusSetupWithSakai(course.getSiteid(), instance.customerNumber, instance.sharedSecret);
		
		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);
		
		mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(
				instance.pageAddressForLogin, instance.institution, instructorLogin, password);
		mhCampusIntroductionScreen.acceptRules();		
		mhCampusCourseBlock = mhCampusInstanceApplication.findAndSelectBookForCourse(courseName, bookIsbn);
	}
	
	@AfterMethod(description = "Guarantee to close new windows even if test method failed")
	public void closeExtraBrowserWindows() {									
		browser.closeAllWindowsExceptFirst();
		mhCampusIntroductionScreen.logOut();
		browser.manage().deleteAllCookies();
	}
	
	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		
		if(instance != null)
			tegrityAdministrationApplication.deleteMhCampusInstance(instance.customerNumber);
		if(course != null)
			sakaiApiService.deletePageWithToolFromSite(course, SakaiTool.LINK_TOOL);
		if(student != null)
			sakaiApiService.deleteUser(student.getEid());
		if(instructor != null)
			sakaiApiService.deleteUser(instructor.getEid());
		if(course != null)
			sakaiApiService.deleteSite(course.getSiteid());
	}		
		
	
	@Test(description = "Check Course Smart provider for instructor")
	public void checkCourseSmartProviderForInstructor() throws InterruptedException {

		mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser
				(instance.pageAddressForLogin, instance.institution, instructorLogin, password);		
		CourseSmartProfileScreen courseSmartProfileScreen = mhCampusCourseBlock.
				clickLaunchEbookLinkAndFillEmail(instructorLogin + mailDomain);
		
		Assert.assertTrue(browser.isCurrentlyOnPageUrl(CourseSmartProfileScreen.class), "The page url doesn't contain a vitalsource domain");
		Assert.verifyTrue(courseSmartProfileScreen.getTitleOfBook().contains(titleOfBook), "The textbook title is not correct");
	}

	@Test(description = "Check Course Smart provider for student")
	public void checkCourseSmartProviderForStudent() throws InterruptedException {

		mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser
				(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusIntroductionScreen.acceptRules();
		mhCampusCourseBlock = mhCampusInstanceApplication.expandCourse(courseName);
		
		CourseSmartProfileScreen courseSmartProfileScreen = mhCampusCourseBlock.clickLaunchEbookLinkAndFillEmail(studentLogin + mailDomain);
		
		Assert.assertTrue(browser.isCurrentlyOnPageUrl(CourseSmartProfileScreen.class), "The page url doesn't contain a vitalsource domain");		
		Assert.verifyTrue(courseSmartProfileScreen.getTitleOfBook().contains(titleOfBook), "The textbook title is not correct");
	}
	
	
	public void prepareDataInSakai() throws Exception {

		String studentRandom = getRandomString();
		String instructorRandom = getRandomString();
		String courseRandom1 = getRandomString();

		studentLogin = "student" + studentRandom;
		studentFirstname = "StudentName" + studentRandom;
		studentLastname = "StudentSurname" + studentRandom;

		instructorLogin = "instructor" + instructorRandom;
		instructorFirstname = "InstructorName" + instructorRandom;
		instructorLastname = "InstructorSurname" + instructorRandom;

		courseName = "course" + courseRandom1;

		student = sakaiApiService.createUser(studentLogin, password, studentFirstname, studentLastname);
		instructor = sakaiApiService.createUser(instructorLogin, password, instructorFirstname, instructorLastname);

		course = sakaiApiService.addNewSite(courseName);
		sakaiApiService.addMemberToSiteWithRole(course, student, SakaiUserRole.STUDENT);
		sakaiApiService.addMemberToSiteWithRole(course, instructor, SakaiUserRole.INSTRUCTOR);
		sakaiApiService.addNewToolToSite(course, SakaiTool.LINK_TOOL);

	}	
	
	protected String getRandomString() {
		return RandomStringUtils.randomNumeric(5);
	}
	
}
