package com.mcgraw.test.automation.ui.applications;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Value;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.ui.moodle.MoodleConfigSecretKeysScreen;
import com.mcgraw.test.automation.ui.moodle.MoodleHomeScreen;
import com.mcgraw.test.automation.ui.moodle.MoodleLoginScreen;

public class MoodleApplication {

	@Value("${moodle.config.secretkeys.url}")
	public String moodleConfigSecretKeysUrl;

	@Value("${moodle.admin.login}")
	public String moodleAdminLogin;

	@Value("${moodle.admin.password}")
	public String moodleAdminPassword;

	@Value("${moodle.blockname}")
	public String moodleBlockName;

	@Value("${moodle.authorization.extendedproperies}")
	public String moodleAuthorizationExtendedProperties;

	@Value("${moodle.authentication.extendedproperies}")
	public String moodleAuthenticationExtendedProperties;

	@Value("${moodle.gradebook.extendedproperties}")
	public String moodleGradebookExtendedProperties;

	@Value("${moodle.baseurl}")
	public String moodleBaseUrl;

	private Browser browser;

	private MoodleLoginScreen moodleLoginScreen;
	private MoodleConfigSecretKeysScreen moodleConfigSecretKeysScreen;

	public MoodleApplication(Browser browser) {
		this.browser = browser;
	}

	public void completeTegritySetupWithMoodle(String customerNumber,
			String sharedSecret) {

		try{
			Logger.info("Completing setup with Moodle, entering secret keys...");
			browser.manage().deleteAllCookies();
			moodleLoginScreen = browser.openScreen(moodleConfigSecretKeysUrl,
					MoodleLoginScreen.class);
			moodleConfigSecretKeysScreen = moodleLoginScreen.loginToMoodleForSetup(
					moodleAdminLogin, moodleAdminPassword);
			Logger.info("Submitting the keys..."); 		
			moodleConfigSecretKeysScreen.submitKeys(customerNumber, sharedSecret);
			moodleConfigSecretKeysScreen.tickAvailableServices();
		}catch(Exception e){
			Logger.info("Failed to complete setup with Moodle, trying again...");
			browser.pause(6000);
			browser.manage().deleteAllCookies();
			moodleLoginScreen = browser.openScreen(moodleConfigSecretKeysUrl,
					MoodleLoginScreen.class);
			moodleConfigSecretKeysScreen = moodleLoginScreen.loginToMoodleForSetup(
					moodleAdminLogin, moodleAdminPassword);
			Logger.info("Submitting the keys..."); 		
			moodleConfigSecretKeysScreen.submitKeys(customerNumber, sharedSecret);
			moodleConfigSecretKeysScreen.tickAvailableServices();
		}
	}

	public MoodleHomeScreen loginToMoodle(String username, String password) {
		Logger.info("Logging in to Moodle...");
		browser.manage().deleteAllCookies();
		moodleLoginScreen = browser.openScreen(moodleBaseUrl,MoodleLoginScreen.class);
		return moodleLoginScreen.loginToMoodle(username, password);
	}
	
	public void logoutFromMoodle() {
		Logger.info("Logout from Moodle...");
		Element clickButton = browser.waitForElement
				(By.xpath("//*/a[contains(@id,'action-menu-toggle-0')]/b"));
		clickButton.click();
		Element logoutButton = browser.waitForElement
				(By.xpath("//*/span[contains(text(),'Log out')]"));
		logoutButton.click();
	}
	
	public int getMoodleVersion() {
		String moodleVersionString;
		int moodleVersion;
		loginToMoodle(moodleAdminLogin, moodleAdminPassword);
		
		Logger.info("Entering in Site Administration for geting a Moodle Version..."); 		
		Element siteAdministration = browser.waitForElement(By.xpath("//span[text()='Site administration']"));
		siteAdministration.click();
		Element notifications = browser.waitForElement(By.xpath("//a[text()='Notifications']"));
		notifications.click();
		Element build = browser.waitForElement(By.xpath("//a[contains(text(),'Build')]"));
		moodleVersionString = build.getText();
		Logger.info("Moodle Version is: " + moodleVersionString); 
		
		moodleVersion = Integer.parseInt(moodleVersionString.substring(0, 1) + moodleVersionString.substring(2, 3) + moodleVersionString.substring(4, 5));	
		
		return moodleVersion;
	}	
}
