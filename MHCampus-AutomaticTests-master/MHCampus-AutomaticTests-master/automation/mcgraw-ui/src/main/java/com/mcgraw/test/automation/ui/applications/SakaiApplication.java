package com.mcgraw.test.automation.ui.applications;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Value;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.ui.sakai.SakaiAdminHomePage;
import com.mcgraw.test.automation.ui.sakai.SakaiCourseDetailsScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiExternalToolsScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiHomeScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiLoginScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiSetupExternalToolsScreen;

public class SakaiApplication {

	@Value("${sakai.baseurl}")
	public String sakaiBaseUrl;

	@Value("${sakai.title}")
	public String sakaiTitle;

	@Value("${sakai.adminlogin}")
	public String sakaiAdminLogin;

	@Value("${sakai.adminpassword}")
	public String sakaiAdminPassword;

	@Value("${sakai.gradebook.service.url}")
	public String sakaiGradebookServiceUrl;

	@Value("${sakai.gradebook.extendedproperties}")
	public String sakaiGradebookExtendedProperties;

	@Value("${sakai.authorization.extendedproperties}")
	public String sakaiAuthorizationExtendedProperties;

	@Value("${sakai.authentication.extendedproperties}")
	public String sakaiAuthenticationExtendedProperties;

	@Value("${sakai.ssolink.url}")
	public String sakaiSSOlinkUrl;

	@Value("${saksi.ssolink.title}")
	public String sakaiSSOlinkTitle;
	
	@Value("${sakai.custom.launch.parameters}")
	public String customLaunchParameters;

	private Browser browser;

	private SakaiLoginScreen sakaiLoginScreen;
	private SakaiAdminHomePage sakaiAdminHomePage;
	private SakaiCourseDetailsScreen sakaiCourseDetailsScreen;
	private SakaiExternalToolsScreen sakaiExternalToolsScreen;
	private SakaiSetupExternalToolsScreen sakaiSetupExternalToolsScreen;

	public SakaiApplication(Browser browser) {
		this.browser = browser;
	}
	
	public void completeMhCampusSetupWithSakai(String courseName,
			String customerNumber, String sharedSecret) {
		Logger.info("Completing setup with Sakai...");
		sakaiAdminHomePage = loginToSakaiAsAdmin(sakaiAdminLogin,
				sakaiAdminPassword);
		sakaiCourseDetailsScreen = sakaiAdminHomePage.goToCreatedCourse(courseName);
		sakaiExternalToolsScreen = sakaiCourseDetailsScreen.clickToolsBtn();   
		sakaiSetupExternalToolsScreen = sakaiExternalToolsScreen.clickSetupBtn();
		
		sakaiSetupExternalToolsScreen.typeParameters(sakaiSSOlinkUrl, sakaiSSOlinkTitle,
				customerNumber, sharedSecret, customLaunchParameters);
		
		sakaiSetupExternalToolsScreen.clickUpdateBtn();
		browser.waitForElementPresent(browser.findElement(By.xpath(".//span[contains(text(),'MhCampus')]")), 20);
	}

	public SakaiHomeScreen loginToSakai(String username, String password) {
		Logger.info("Logging in to Sakai...");
		browser.switchToFirstWindow();
		browser.manage().deleteAllCookies();
		try{
			sakaiLoginScreen = browser.openScreen(sakaiBaseUrl,
					SakaiLoginScreen.class);
		}catch(Exception e){
			logoutFromSakai();
			sakaiLoginScreen = browser.openScreen(sakaiBaseUrl,
					SakaiLoginScreen.class);
		}
		return sakaiLoginScreen.loginToSakai(username, password);
	}

	public SakaiAdminHomePage loginToSakaiAsAdmin(String username,
			String password) {
		Logger.info("Logging in to Sakai...");
		browser.switchToFirstWindow();
		browser.manage().deleteAllCookies();
		try{
			sakaiLoginScreen = browser.openScreen(sakaiBaseUrl,
					SakaiLoginScreen.class);
		}catch(Exception e){
			logoutFromSakai();
			sakaiLoginScreen = browser.openScreen(sakaiBaseUrl,
					SakaiLoginScreen.class);
		}
		return sakaiLoginScreen.loginToSakaiAsAdmin(username, password);
	}
	
	public void logoutFromSakai() {
		Logger.info("Logout from Sakai...");
		browser.switchToFirstWindow();
		try{
			Element logoutButton = browser.waitForElement(By.id("loginLink1"));
			Logger.info("Clicking logout Button...");
			logoutButton.click();
			browser.pause(5000);
		}catch(Exception e){
			Logger.info("Can't logout from Sakai...");
		}
	}
}
