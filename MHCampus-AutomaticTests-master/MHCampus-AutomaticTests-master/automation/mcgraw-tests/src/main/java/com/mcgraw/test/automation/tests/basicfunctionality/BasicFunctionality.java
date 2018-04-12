package com.mcgraw.test.automation.tests.basicfunctionality;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.sakai.SakaiTool;
import com.mcgraw.test.automation.api.sakai.SakaiUserRole;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewSite;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewUser;
import com.mcgraw.test.automation.api.sakai.service.ISakaiApiService;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.SakaiApplication;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceLoginScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.MhCampusCourseBlock;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class BasicFunctionality extends BaseTest {
		
	@Autowired
	private SakaiApplication sakaiApplication;
	
	@Autowired
	private ISakaiApiService sakaiApiService;
	
	private String password = "123qweA@";

	private AddNewUser instructor;
	private String instructorRandom = getRandomString();
	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String instructorSurname = "InstructorSurname" + instructorRandom;
	
	private AddNewUser student;
	private String studentRandom = getRandomString();
	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;
	private String studentSurname = "StudentSurname" + studentRandom;
	
	
	private AddNewSite site1;
	private String courseRandom1 = "epamcourse1" + getRandomString();
	
	private String essentialsOfBiologyAuthor = "MADER";
	private String essentialsOfBiologyTitle = "Essentials of Biology 3e";
	private String essentialsOfBiologyIsbn10 = "0073525510";
	private String essentialsOfBiologyIsbn13 = "9780073525518";
	
	private int numOfSlave = 2;
	
	private Boolean loggedOut = false;	

	private InstanceCredentials instance;
	private MhCampusCourseBlock mhCampusCourseBlock;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;
	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;

	
	@BeforeClass
	public void testSuiteSetup() throws Exception {
		
		instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);
	
		prepareDataInSakai();
	
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
		mhCampusInstanceConnectorsScreen.configureSakaiAuthorizationConnector(sakaiApplication.sakaiAuthorizationExtendedProperties
				.replace("<CustomerNumber>", instance.customerNumber));
		mhCampusInstanceConnectorsScreen.configureSakaiAuthenticationConnector(sakaiApplication.sakaiAuthenticationExtendedProperties
				.replace("<CustomerNumber>", instance.customerNumber));	
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		
		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);

		mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(
				instance.pageAddressForLogin, instance.institution, instructorLogin, password);
		mhCampusIntroductionScreen.acceptRules();
	}
	
	@AfterMethod
	public void testTearDown() throws Exception {
		if (loggedOut) {
			mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin,
					instance.institution, instructorLogin, password);
		}
	}
	
	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		
		if(site1 != null)
			sakaiApiService.deletePageWithToolFromSite(site1, SakaiTool.LINK_TOOL);
		if(student != null)
			sakaiApiService.deleteUser(student.getEid());
		if(instructor != null)
			sakaiApiService.deleteUser(instructor.getEid());
		if(site1 != null)
			sakaiApiService.deleteSite(site1.getSiteid());
	}
	
	
	@Test()
	public void testLogoutOptionWorks() {
		loggedOut = true;
		MhCampusInstanceLoginScreen mhCampusInstanceLoginScreen = null;
		try{
			mhCampusInstanceLoginScreen = mhCampusIntroductionScreen.logOut();
		}catch(Exception e){
			Logger.info("Failed logout from MH Campus instance. Trying again...");
			mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin,
					instance.institution, instructorLogin, password);
			mhCampusInstanceLoginScreen = mhCampusIntroductionScreen.logOut();
		}
		Assert.assertNotNull(mhCampusInstanceLoginScreen, "MH Campus Login screen didn't appear");
	}

	@Test()
	public void testSearchByBookISBN10ReturnsResults() {
		Assert.assertEquals(mhCampusInstanceApplication.findBooks(courseRandom1, essentialsOfBiologyIsbn10), 1, "Search by ISBN10 "
				+ essentialsOfBiologyIsbn10 + " didn't return any results");
	}

	@Test()
	public void testSearchByBookISBN13ReturnsResults() {
		Assert.assertEquals(mhCampusInstanceApplication.findBooks(courseRandom1, essentialsOfBiologyIsbn13), 1, "Search by ISBN13 "
				+ essentialsOfBiologyIsbn13 + " didn't return any results");
	}

	@Test()
	public void testSearchByBookAuthorReturnsResults() {
		Assert.assertTrue(mhCampusInstanceApplication.findBooks(courseRandom1, essentialsOfBiologyAuthor) >= 1, "Search by author "
				+ essentialsOfBiologyAuthor + " didn't return any results");
	}

	@Test()
	public void testSearchByBookTitleReturnsResults() {
		Assert.assertTrue(mhCampusInstanceApplication.findBooks(courseRandom1, essentialsOfBiologyTitle) >= 1, "Search by title "
				+ essentialsOfBiologyTitle + " didn't return any results");
	}

	@Test()
	public void testNotYourBookOptionBehavesCorrectly() {
		mhCampusCourseBlock = mhCampusInstanceApplication.findAndSelectBookForCourse(courseRandom1, essentialsOfBiologyIsbn10);
		mhCampusCourseBlock.removeCurrentBook();
		Assert.assertTrue(mhCampusCourseBlock.isTextbookSearchPresent(), "Book was not removed successfully");
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
	}

	
}