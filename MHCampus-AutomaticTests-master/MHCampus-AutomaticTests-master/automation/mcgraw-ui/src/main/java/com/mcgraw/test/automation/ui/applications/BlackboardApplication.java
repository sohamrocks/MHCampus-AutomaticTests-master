package com.mcgraw.test.automation.ui.applications;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.testng.Assert;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.ui.blackboard.BlackBoardConfigSecretKeysResultScreen;
import com.mcgraw.test.automation.ui.blackboard.BlackBoardConfigSecretKeysScreen;
import com.mcgraw.test.automation.ui.blackboard.BlackboardHomeScreen;
import com.mcgraw.test.automation.ui.blackboard.BlackboardLoginScreen;

public class BlackboardApplication {

	@Value("${blackboard.url}")
	public String blackboardUrl;

	@Value("${blackboard.config.secretkeys.url}")
	public String blackboardConfigSecretKeysUrl;

	@Value("${blackboard.admin.login}")
	public String blackboardAdminLogin;

	@Value("${blackboard.admin.password}")
	public String blackboardAdminPassword;
	
	@Value("${blackboard.title}")
	public String title;

	@Value("${blackboard.address}")
	public String address;

	private BlackboardLoginScreen blackboardLoginScreen;
	private BlackBoardConfigSecretKeysScreen blackBoardConfigSecretKeysScreen;
	private BlackBoardConfigSecretKeysResultScreen blackBoardConfigSecretKeysResultScreen;

	Browser browser;

	public BlackboardApplication(Browser browser) {
		this.browser = browser;
	}

	public void completeMhCampusSetupWithBlackBoard(String customerNumber,
			String sharedSecret) {
		
		Logger.info("Completing setup with Blackboard, entering secret keys...");
		browser.manage().deleteAllCookies();
		
		try{
			blackboardLoginScreen = browser.openScreen(
					blackboardConfigSecretKeysUrl, BlackboardLoginScreen.class);
		}catch(Exception e){
			Logger.info("Open BlackboardLoginScreen again...");
			blackboardLoginScreen = browser.openScreen(
					blackboardConfigSecretKeysUrl, BlackboardLoginScreen.class);
		}	
		blackBoardConfigSecretKeysScreen = blackboardLoginScreen.loginToBlackBoardForSetup
				   (blackboardAdminLogin,blackboardAdminPassword);
		blackBoardConfigSecretKeysResultScreen = blackBoardConfigSecretKeysScreen
				.submitKeys(customerNumber, sharedSecret);
		browser.makeScreenshot();
		Assert.assertNotNull(blackBoardConfigSecretKeysResultScreen);
		browser.openScreen(blackboardUrl, BlackboardHomeScreen.class);
		logoutFromBlackBoard();
	}

	public void logoutFromBlackBoard() {

		try{
			Logger.info("Logout from Blackboard...");
			browser.switchTo().defaultContent();
			Element logoutButton = browser.waitForElement(By.id("topframe.logout.label"));
			logoutButton.click();
			browser.pause(1000);
		}catch(Exception e){
			browser.openScreen(blackboardUrl, BlackboardHomeScreen.class);
			Logger.info("Try Logout from Blackboard again...");
			Element logoutButton = browser.waitForElement(By.id("topframe.logout.label"));
			logoutButton.click();
			browser.pause(1000);
		}
	}
	
	public BlackboardHomeScreen loginToBlackBoard(String userName,
			String password) {

		Logger.info("Logging in to Blackboard...");
		browser.closeAllWindowsExceptFirst();
		browser.switchTo().defaultContent();

		Set<Cookie> beforeDeletion = browser.manage().getCookies();
		Logger.info("Before deletion cookies size = " + beforeDeletion.size());
		Logger.info("Before deletion cookies are empty = "
				+ beforeDeletion.isEmpty());

		browser.manage().deleteAllCookies();

		Set<Cookie> afterDeletion = browser.manage().getCookies();
		Logger.info("After deletion cookies size = " + afterDeletion.size());
		Logger.info("After deletion cookies are empty = "
				+ afterDeletion.isEmpty());

		try{
			blackboardLoginScreen = browser.openScreen(blackboardUrl,
					BlackboardLoginScreen.class);
		}catch(Exception e){
			logoutFromBlackBoard();
			blackboardLoginScreen = browser.openScreen(blackboardUrl,
					BlackboardLoginScreen.class);
		}
		
		
		return blackboardLoginScreen.loginToBlackBoard(userName, password);
	}

//	Addeed by Yuliia
	public BlackboardHomeScreen loginToBlackBoardSimNet(String userName,
			String password) {
		String blackboardUrlSimNet="https://csub2integrationqa10.blackboard.com/";
		Logger.info("Logging in to Blackboard...");
		browser.closeAllWindowsExceptFirst();
		browser.switchTo().defaultContent();

		Set<Cookie> beforeDeletion = browser.manage().getCookies();
		Logger.info("Before deletion cookies size = " + beforeDeletion.size());
		Logger.info("Before deletion cookies are empty = "
				+ beforeDeletion.isEmpty());

		browser.manage().deleteAllCookies();

		Set<Cookie> afterDeletion = browser.manage().getCookies();
		Logger.info("After deletion cookies size = " + afterDeletion.size());
		Logger.info("After deletion cookies are empty = "
				+ afterDeletion.isEmpty());

		try{
			blackboardLoginScreen = browser.openScreen(blackboardUrlSimNet,
					BlackboardLoginScreen.class);
		}catch(Exception e){
			logoutFromBlackBoard();
			blackboardLoginScreen = browser.openScreen(blackboardUrlSimNet,
					BlackboardLoginScreen.class);
		}
		return  blackboardLoginScreen.loginToBlackBoard(userName, password);}
//	====================================================================================================
}
