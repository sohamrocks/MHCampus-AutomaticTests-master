package com.mcgraw.test.automation.ui.applications;

import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.rest.TestRestApi;

public class RestApplication   {

	@Value("${tegrity.restApi.url}")
	public String testRestApiUrl;

	private TestRestApi testRestApiScreen;

	Browser browser;

	public RestApplication(Browser browser) {
		this.browser = browser;
	}

	public TestRestApi fillUnencryptedIdAndPressEncrypt(String id) throws Exception {

		Logger.info("Filling TestScorableItem form...");
		browser.manage().deleteAllCookies();
		try{
			testRestApiScreen = browser.openScreen(testRestApiUrl,
					TestRestApi.class);
		}catch(Exception e){
			Logger.info("Curent URL is: " + browser.getCurrentUrl());
			browser.makeScreenshot();
			e.toString();
			Logger.info("Try again to open 'TestScoreItemsScreen'...");
			browser.manage().deleteAllCookies();
			testRestApiScreen = browser.openScreen(testRestApiUrl,
					TestRestApi.class);
		}
		
		testRestApiScreen.typeEncryptionInput(id);
		testRestApiScreen.clickEncryptBtn();
		browser.makeScreenshot();
		
		return testRestApiScreen;
	}	

}
