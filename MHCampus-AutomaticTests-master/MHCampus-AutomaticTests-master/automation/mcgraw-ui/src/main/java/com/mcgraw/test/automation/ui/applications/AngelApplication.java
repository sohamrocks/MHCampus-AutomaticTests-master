package com.mcgraw.test.automation.ui.applications;

import org.springframework.beans.factory.annotation.Value;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.ui.angel.AngelAdministrateUsersScreen;
import com.mcgraw.test.automation.ui.angel.AngelAdministratorConsoleScreen;
import com.mcgraw.test.automation.ui.angel.AngelConfigureSecretKeysScreen;
import com.mcgraw.test.automation.ui.angel.AngelFindCourseScreen;
import com.mcgraw.test.automation.ui.angel.AngelHomeScreen;
import com.mcgraw.test.automation.ui.angel.AngelLoginScreen;
import com.mcgraw.test.automation.ui.angel.course.AngelCourseContext.TabMenuItem;
import com.mcgraw.test.automation.ui.angel.course.AngelCourseEnrollmentSettingsScreen;
import com.mcgraw.test.automation.ui.angel.course.AngelCourseManageRosterScreen;
import com.mcgraw.test.automation.ui.angel.course.AngelCourseManageScreen;
import com.mcgraw.test.automation.ui.angel.course.AngelCourseRosterAddUserScreen;

public class AngelApplication {

	@Value("${angel.baseurl}")
	public String angelUrl;

	@Value("${angel.admin.login}")
	public String angelAdminLogin;
	
	@Value("${angel.admin.password}")
	public String angelAdminPassword;
	
	@Value("${angel.title}")
	public String angelTitle;

	@Value("${angel.authorization.service.url}")
	public String angelAuthorizationServiceUrl;

	@Value("${angel.authorization.extendedproperties}")
	public String angelAuthorizationExtendedProperties;

	@Value("${angel.authentication.service.url}")
	public String angelAuthenticationServiceUrl;

	@Value("${angel.authentication.extendedproperties}")
	public String angelAuthenticationExtendedProperties;

	@Value("${angel.gradebook.service.url}")
	public String angelGradeBookServiceUrl;

	@Value("${angel.gradebook.extendedproperties}")
	public String angelGradebookExtendedProperties;
	
	@Value("${angel.config.secretkeys.url}")
	public String angelConfigSecretKeysUrl;

	Browser browser;

	public AngelApplication(Browser browser) {
		this.browser = browser;
	}

	public AngelHomeScreen loginToAngel(String username, String password) {

		Logger.info("Logging in to Angel...");
		browser.switchTo().defaultContent();
		browser.manage().deleteAllCookies();
		AngelLoginScreen angelLoginScreen = browser.openScreen(angelUrl, AngelLoginScreen.class);
		return angelLoginScreen.loginToAngel(username, password);
	}
	
	public void completeMhCampusSetupWithAngel(String sharedSecret, String customerNumber)
	{
		loginToAngel(angelAdminLogin, angelAdminPassword);
		AngelConfigureSecretKeysScreen angelConfigureSecretKeysScreen = browser.openScreen(angelConfigSecretKeysUrl, AngelConfigureSecretKeysScreen.class);
		angelConfigureSecretKeysScreen.submitKeys(sharedSecret, customerNumber);
	}
	
	public void enrollStudentToCreatedCourse(String instructorLogin, String instructorPassword, String courseName, String studentLogin)
	{
		AngelHomeScreen angelHomeScreen = loginToAngel(instructorLogin, instructorPassword);

		AngelCourseManageScreen angelCourseManageScreen = angelHomeScreen.setCourseContext(courseName,  TabMenuItem.Manage);
		
		AngelCourseManageRosterScreen angelCourseManageRosterScreen = angelCourseManageScreen.clickRosterButton();
		
		AngelCourseRosterAddUserScreen angelCourseRosterAddUserScreen = angelCourseManageRosterScreen.clickAddUser();
		
		AngelCourseEnrollmentSettingsScreen angelCourseEnrollmentSettingsScreen = angelCourseRosterAddUserScreen.selectUserToEnroll(studentLogin);
		angelCourseEnrollmentSettingsScreen.chooseUserRights("Student");
		angelCourseEnrollmentSettingsScreen.clickSaveButton();
	}
	
	public void deleteCourseWithEnrollUsers(String courseId)
	{
		Logger.info("Deleting from Angel course with id = " + courseId + " ...");
		AngelHomeScreen angelHomeScreen = loginToAngel(angelAdminLogin, angelAdminPassword);
		AngelFindCourseScreen angelFindCourseScreen = angelHomeScreen.goToSearchCourseScreen();
		angelFindCourseScreen.typeCourseIdToSearch(courseId);
		angelFindCourseScreen.deleteCourseIfPresent(courseId);
	}
	
	public void deleteUserFromAngel(String username)
	{
		Logger.info("Deleting from Angel user with username = " + username + " ...");
		AngelHomeScreen angelHomeScreen = loginToAngel(angelAdminLogin, angelAdminPassword);
		AngelAdministratorConsoleScreen angelAdministratorConsoleScreen = angelHomeScreen.goToAdministratorConsole();
		AngelAdministrateUsersScreen angelAdministrateUsersScreen = angelAdministratorConsoleScreen.goToAdministateUsers();
		angelAdministrateUsersScreen.typeUserToSearch(username);
		angelAdministrateUsersScreen.clickSearchButton();
		angelAdministrateUsersScreen.deleteUserIfPresent(username);
	}

}
