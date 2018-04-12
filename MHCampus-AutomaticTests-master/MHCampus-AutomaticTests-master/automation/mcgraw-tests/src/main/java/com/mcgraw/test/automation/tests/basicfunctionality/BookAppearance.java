package com.mcgraw.test.automation.tests.basicfunctionality;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.mcgraw.test.automation.framework.core.testng.Assert;
import org.testng.annotations.AfterClass;
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
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.CourseBlockElement;
import com.mcgraw.test.automation.ui.mhcampus.course.MhCampusCourseBlock;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class BookAppearance extends BaseTest {

	@Autowired
	private SakaiApplication sakaiApplication;
	
	@Autowired
	private ISakaiApiService sakaiApiService;
	
	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom1 = "epamcourse1" + getRandomString();
	private String courseRandom2 = "epamcourse2" + getRandomString();
	
	private String bookIsbnNoLinks = "0321518136";
	private String bookIsbnAllLinks = "0000011111";

	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;
	private String studentSurname = "StudentSurname" + studentRandom;

	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String instructorSurname = "InstructorSurname" + instructorRandom;
	
	private String password = "123qweA@";
	
	private AddNewUser student;
	private AddNewUser instructor;
	private AddNewSite site1;
	private AddNewSite site2;

	private int numOfSlave = 2;
	
	private InstanceCredentials instance;	
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;
	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	private MhCampusCourseBlock courseBlock;
	
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
		
		mhCampusInstanceApplication.findAndSelectBookForCourse(courseRandom1, bookIsbnNoLinks);
		mhCampusInstanceApplication.findAndSelectBookForCourse(courseRandom2, bookIsbnAllLinks);
	
		mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(
				instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusIntroductionScreen.acceptRules();
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
	
	
	@Test()
	public void testBookWithNoLinksIsDisplayedCorrectlyForInstructor() {

		mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution,
				instructorLogin, password);
		courseBlock = mhCampusInstanceApplication.expandCourse(courseRandom1);
		
		Assert.verifyEquals(courseBlock.getBookTitle(),
				"Brief Guide to Biology with Physiology (PK w/Current Issues in Biology, Volume 3 and 4) 1e", "Book title did not match");
		Assert.verifyEquals(courseBlock.getBookAuthor(), "Krogh", "Book author did not match");
		Assert.verifyEquals(courseBlock.getBookPublisher(), "2007 © Pearson Higher Education", "Book publisher did not match");
		Assert.verifyEquals(courseBlock.getBookIsbn10(), "0321518136", "Book ISBN10 did not match");
		Assert.verifyEquals(courseBlock.getBookIsbn13(), "9780321518132", "Book ISBN13 did not match");
		Assert.verifyTrue(courseBlock.isNotYourBookOptionPresent(), "'(not your book?)' link is missing");

		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseRandom1, CourseBlockElement.CREATE,
				CourseBlockElement.CONNECT, CourseBlockElement.ALEKS, CourseBlockElement.TEGRITY, CourseBlockElement.SIMNET,
				CourseBlockElement.GDP, CourseBlockElement.ACTIV_SIM, CourseBlockElement.PRINT_ON_DEMAND, CourseBlockElement.LEARN_SMART,
				CourseBlockElement.REMOTE_PROCTOR, CourseBlockElement.LAUNCH_EBOOK, CourseBlockElement.CUSTOMIZE_BUTTON,
				CourseBlockElement.SMART_BOOK, CourseBlockElement.CONNECT_MATH);
	}

	@Test()
	public void testBookWithNoLinksIsDisplayedCorrectlyForStudent() {

		mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution,
				studentLogin, password);
		courseBlock = mhCampusInstanceApplication.expandCourse(courseRandom1);
		Assert.verifyEquals(courseBlock.getBookTitle(),
				"Brief Guide to Biology with Physiology (PK w/Current Issues in Biology, Volume 3 and 4) 1e", "Book title did not match");
		Assert.verifyEquals(courseBlock.getBookAuthor(), "Krogh", "Book author did not match");
		Assert.verifyEquals(courseBlock.getBookPublisher(), "2007 © Pearson Higher Education", "Book publisher did not match");
		Assert.verifyEquals(courseBlock.getBookIsbn10(), "0321518136", "Book ISBN10 did not match");
		Assert.verifyEquals(courseBlock.getBookIsbn13(), "9780321518132", "Book ISBN13 did not match");
		Assert.verifyFalse(courseBlock.isNotYourBookOptionPresent(), "'(not your book?)' link is present");

		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseRandom1, CourseBlockElement.CREATE,
				CourseBlockElement.CONNECT, CourseBlockElement.ALEKS, CourseBlockElement.TEGRITY, CourseBlockElement.SIMNET,
				CourseBlockElement.GDP, CourseBlockElement.ACTIV_SIM, CourseBlockElement.PRINT_ON_DEMAND, CourseBlockElement.LEARN_SMART,
				CourseBlockElement.REMOTE_PROCTOR, CourseBlockElement.LAUNCH_EBOOK, CourseBlockElement.CUSTOMIZE_BUTTON,
				CourseBlockElement.SMART_BOOK, CourseBlockElement.CONNECT_MATH);
	}

	@Test()
	public void testBookWithAllLinksIsDisplayedCorrectlyForInstructor() {

		mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution,
				instructorLogin, password);
		courseBlock = mhCampusInstanceApplication.expandCourse(courseRandom2);
		Assert.verifyEquals(courseBlock.getBookTitle(), "MHCampus Test Book 1 1e", "Book title did not match");
		Assert.verifyEquals(courseBlock.getBookAuthor(), "Elad", "Book author did not match");
		Assert.verifyEquals(courseBlock.getBookPublisher(), "2005 © McGraw-Hill, Inc.", "Book publisher did not match");
		Assert.verifyEquals(courseBlock.getBookIsbn10(), "0000011111", "Book ISBN10 did not match");
		Assert.verifyEquals(courseBlock.getBookIsbn13(), "9780000011114", "Book ISBN13 did not match");
		Assert.verifyTrue(courseBlock.isNotYourBookOptionPresent(), "'(not your book?)' link is missing");
		Assert.verifyTrue(courseBlock.isViewOnlineResourcesLinkPresent(), "'(View online resources)' link is missing");

		mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseRandom2, CourseBlockElement.CUSTOMIZE_BUTTON,
				CourseBlockElement.LAUNCH_EBOOK, CourseBlockElement.CONNECT, CourseBlockElement.ALEKS, CourseBlockElement.SIMNET,
				CourseBlockElement.LEARN_SMART, CourseBlockElement.SMART_BOOK);

		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseRandom2, CourseBlockElement.CREATE,
				CourseBlockElement.TEGRITY, CourseBlockElement.GDP, CourseBlockElement.ACTIV_SIM, CourseBlockElement.PRINT_ON_DEMAND,
				CourseBlockElement.REMOTE_PROCTOR, CourseBlockElement.CONNECT_MATH);
	}

	@Test()
	public void testBookWithAllLinksIsDisplayedCorrectlyForStudent() {

		mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution,
				studentLogin, password);
		courseBlock = mhCampusInstanceApplication.expandCourse(courseRandom2);
		
		Assert.verifyEquals(courseBlock.getBookTitle(), "MHCampus Test Book 1 1e", "Book title did not match");
		Assert.verifyEquals(courseBlock.getBookAuthor(), "Elad", "Book author did not match");
		Assert.verifyEquals(courseBlock.getBookPublisher(), "2005 © McGraw-Hill, Inc.", "Book publisher did not match");
		Assert.verifyEquals(courseBlock.getBookIsbn10(), "0000011111", "Book ISBN10 did not match");
		Assert.verifyEquals(courseBlock.getBookIsbn13(), "9780000011114", "Book ISBN13 did not match");
		Assert.verifyFalse(courseBlock.isNotYourBookOptionPresent(), "'(not your book?)' link is present");
		Assert.verifyTrue(courseBlock.isViewOnlineResourcesLinkPresent(), "'(View online resources)' link is missing");

		mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseRandom2, CourseBlockElement.LAUNCH_EBOOK);

		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseRandom2, CourseBlockElement.CREATE,
				CourseBlockElement.CONNECT, CourseBlockElement.ALEKS, CourseBlockElement.TEGRITY, CourseBlockElement.SIMNET,
				CourseBlockElement.GDP, CourseBlockElement.ACTIV_SIM, CourseBlockElement.PRINT_ON_DEMAND, CourseBlockElement.LEARN_SMART,
				CourseBlockElement.REMOTE_PROCTOR, CourseBlockElement.CUSTOMIZE_BUTTON, CourseBlockElement.SMART_BOOK,
				CourseBlockElement.CONNECT_MATH);
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
