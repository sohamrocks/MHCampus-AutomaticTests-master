package com.mcgraw.test.automation.tests.pageappearancesettings;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.mcgraw.test.automation.api.sakai.SakaiTool;
import com.mcgraw.test.automation.api.sakai.SakaiUserRole;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewSite;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewUser;
import com.mcgraw.test.automation.api.sakai.service.ISakaiApiService;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.SakaiApplication;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.TegrityCreateMhCampusCustomerScreen.PageAppearanceSettings;
import com.mcgraw.test.automation.ui.mhcampus.course.MhCampusCourseBlock;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class BasePageAppearanceSettingsTest extends BaseTest {

	@Autowired
	private SakaiApplication sakaiApplication;

	@Autowired
	private ISakaiApiService sakaiApiService;

	protected AddNewUser student;
	protected AddNewUser instructor;

	protected AddNewSite siteZoology;
	protected AddNewSite siteMarketing;
	protected AddNewSite siteAlgebra;
	protected AddNewSite siteEmpty;

	protected String studentLogin;
	protected String studentName;
	protected String studentSurname;

	protected String instructorLogin;
	protected String instructorName;
	protected String instructorSurname;

	protected String password = "123qweA@";

	protected String courseIdZoology;
	protected String courseNameZoology;
	protected String courseIdMarketing;
	protected String courseNameMarketing;
	protected String courseIdAlgebra;
	protected String courseNameAlgebra;
	protected String courseIdEmpty;
	protected String courseNameEmpty;

	protected String bookIsbn1 = "0072528362";
	//protected String bookIsbn2 = "0078028833";
	protected String bookIsbn2 = "0000011111";
	protected String bookIsbn3 = "007728111X";

	protected MhCampusCourseBlock mhCampusCourseBlockZoology;
	protected MhCampusCourseBlock mhCampusCourseBlockMarketing;
	protected MhCampusCourseBlock mhCampusCourseBlockAlgebra;
	protected MhCampusCourseBlock mhCampusCourseBlockEmpty;

	protected InstanceCredentials instance;

	@Parameters("separateStudentSetting")
	@BeforeClass
	public void testSuiteSetup(@Optional String separateStudentSetting) throws Exception {
		
	
		try{
			if (StringUtils.isNotEmpty(separateStudentSetting))
				instance = tegrityAdministrationApplication.createNewMhCampusInstance(PageAppearanceSettings.Enabled,
						PageAppearanceSettings.valueOf(separateStudentSetting));
			else {
				instance = tegrityAdministrationApplication.createNewMhCampusInstance(PageAppearanceSettings.Enabled);
			}
		}catch(Exception e){
			Logger.info("Failed to create MH Campus instance, trying again...");
			browser.pause(60000);
			if (StringUtils.isNotEmpty(separateStudentSetting))
				instance = tegrityAdministrationApplication.createNewMhCampusInstance(PageAppearanceSettings.Enabled,
						PageAppearanceSettings.valueOf(separateStudentSetting));
			else {
				instance = tegrityAdministrationApplication.createNewMhCampusInstance(PageAppearanceSettings.Enabled);
			}
		}
		
		browser.pause(mhCampusInstanceApplication.CREATE_NEW_INSTANCE_TIMEOUT);

		MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication
				.loginToMhCampusInstanceAndAcceptTermsOfUse(instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.configureSakaiAuthorizationConnector(sakaiApplication.sakaiAuthorizationExtendedProperties
				.replace("<CustomerNumber>", instance.customerNumber));
		mhCampusInstanceConnectorsScreen.configureSakaiAuthenticationConnector(sakaiApplication.sakaiAuthenticationExtendedProperties
				.replace("<CustomerNumber>", instance.customerNumber));
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		
		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);
		
		prepareDataInSakai();

		loginToMhcampusAndProcessPopups(instructorLogin, password);
		mhCampusCourseBlockZoology = mhCampusInstanceApplication.findAndSelectBookForCourse(courseNameZoology, bookIsbn1);
		browser.pause(1000);	//AlexandrY added to fix local instability
		mhCampusCourseBlockMarketing = mhCampusInstanceApplication.findAndSelectBookForCourse(courseNameMarketing, bookIsbn2);
		browser.pause(1000);	//AlexandrY added to fix local instability
		mhCampusCourseBlockAlgebra = mhCampusInstanceApplication.findAndSelectBookForCourse(courseNameAlgebra, bookIsbn3);
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		
		if(instance != null)
			tegrityAdministrationApplication.deleteMhCampusInstance(instance.customerNumber);
		if(student != null)
			sakaiApiService.deleteUser(student.getEid());
		if(instructor != null)
			sakaiApiService.deleteUser(instructor.getEid());
		if(siteZoology != null)
			sakaiApiService.deleteSite(siteZoology.getSiteid());
		if(siteMarketing != null)
			sakaiApiService.deleteSite(siteMarketing.getSiteid());
		if(siteMarketing != null)
			sakaiApiService.deleteSite(siteAlgebra.getSiteid());
		if(siteEmpty != null)
			sakaiApiService.deleteSite(siteEmpty.getSiteid());
	}

	public void prepareDataInSakai() throws Exception {

		String studentRandom = getRandomString();
		String instructorRandom = getRandomString();
		String courseRandom1 = getRandomString();
		String courseRandom2 = getRandomString();
		String courseRandom3 = getRandomString();
		String courseRandom4 = getRandomString();

		studentLogin = "student" + studentRandom;
		studentName = "StudentName" + studentRandom;
		studentSurname = "StudentSurname" + studentRandom;

		instructorLogin = "instructor" + instructorRandom;
		instructorName = "InstructorName" + instructorRandom;
		instructorSurname = "InstructorSurname" + instructorRandom;

		courseNameZoology = "course" + courseRandom1;
		courseNameMarketing = "course" + courseRandom2;
		courseNameAlgebra = "course" + courseRandom3;
		courseNameEmpty = "course" + courseRandom4;

		student = sakaiApiService.createUser(studentLogin, password, studentName, studentSurname);
		instructor = sakaiApiService.createUser(instructorLogin, password, instructorName, instructorSurname);

		siteZoology = sakaiApiService.addNewSite(courseNameZoology);
		sakaiApiService.addMemberToSiteWithRole(siteZoology, student, SakaiUserRole.STUDENT);
		sakaiApiService.addMemberToSiteWithRole(siteZoology, instructor, SakaiUserRole.INSTRUCTOR);
		sakaiApiService.addNewToolToSite(siteZoology, SakaiTool.LINK_TOOL);

		siteMarketing = sakaiApiService.addNewSite(courseNameMarketing);
		sakaiApiService.addMemberToSiteWithRole(siteMarketing, student, SakaiUserRole.STUDENT);
		sakaiApiService.addMemberToSiteWithRole(siteMarketing, instructor, SakaiUserRole.INSTRUCTOR);
		sakaiApiService.addNewToolToSite(siteMarketing, SakaiTool.LINK_TOOL);

		siteAlgebra = sakaiApiService.addNewSite(courseNameAlgebra);
		sakaiApiService.addMemberToSiteWithRole(siteAlgebra, student, SakaiUserRole.STUDENT);
		sakaiApiService.addMemberToSiteWithRole(siteAlgebra, instructor, SakaiUserRole.INSTRUCTOR);
		sakaiApiService.addNewToolToSite(siteAlgebra, SakaiTool.LINK_TOOL);

		siteEmpty = sakaiApiService.addNewSite(courseNameEmpty);
		sakaiApiService.addMemberToSiteWithRole(siteEmpty, student, SakaiUserRole.STUDENT);
		sakaiApiService.addMemberToSiteWithRole(siteEmpty, instructor, SakaiUserRole.INSTRUCTOR);
		sakaiApiService.addNewToolToSite(siteEmpty, SakaiTool.LINK_TOOL);

	}

	private String getRandomString() {
		return RandomStringUtils.randomNumeric(5);
	}

	protected void loginToMhcampusAndProcessPopups(String login, String password) {
		MhCampusIntroductionScreen mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(
				instance.pageAddressForLogin, instance.institution, login, password);
		mhCampusIntroductionScreen.acceptRules();
	}
	
	protected <T extends Screen> String notOnDesiredPageMessage(Class<T> clazz) {
		return "Browser is not on '" + browser.getScreenAbsoluteUrl(clazz) + "'page";
	}
}