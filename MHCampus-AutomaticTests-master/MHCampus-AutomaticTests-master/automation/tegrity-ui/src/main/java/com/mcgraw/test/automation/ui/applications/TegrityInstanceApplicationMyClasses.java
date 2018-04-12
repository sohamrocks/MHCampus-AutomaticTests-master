package com.mcgraw.test.automation.ui.applications;

import org.springframework.beans.factory.annotation.Value;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.ui.connect.ConnectLoginScreen;
import com.mcgraw.test.automation.ui.connect.ConnectStudentRegistrationScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceDashboardScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceLoginScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityIntroductionScreen;

public class TegrityInstanceApplicationMyClasses {
	
	@Value("${tegrity.instance.myclasses.institution}")
	public String institution;

	@Value("${tegrity.instance.myclasses.username}")
	public String username;

	@Value("${tegrity.instance.myclasses.password}")
	public String password;

	@Value("${tegrity.instance.myclasses.customernumber}")
	public String customerNumber;

	@Value("${tegrity.instance.myclasses.sharedsecret}")
	public String sharedSecret;

	@Value("${tegrity.instance.myclasses.url}")
	public String url;

	public long DIRECT_LOGIN_TIMEOUT = (1 * 60 * 1000); // 1 minute

	private TegrityInstanceLoginScreen tegrityInstanceLoginScreen;
	private TegrityInstanceDashboardScreen tegrityInstanceDashboardScreen;

	private Browser browser;

	public TegrityInstanceApplicationMyClasses(Browser browser) {
		this.browser = browser;
	}	

	public TegrityInstanceConnectorsScreen loginToTegrityInstanceAsAdminAndClickManageAairsLink(){   

		Logger.info("Logging in to Tegrity instance...");
		browser.manage().deleteAllCookies();
		try{
			tegrityInstanceLoginScreen = browser.openScreen(url, TegrityInstanceLoginScreen.class);
			tegrityInstanceDashboardScreen = tegrityInstanceLoginScreen.loginToTegrityInstanceAsAdmin(username, password);
		}catch(Exception ex){
			Logger.info("Failed login as admin. Trying again...");
			browser.pause(DIRECT_LOGIN_TIMEOUT);
			tegrityInstanceLoginScreen = browser.openScreen(url, TegrityInstanceLoginScreen.class);
			tegrityInstanceDashboardScreen = tegrityInstanceLoginScreen.loginToTegrityInstanceAsAdmin(username, password);
		}
		return tegrityInstanceDashboardScreen.clickManageAairs();
	}	
	
	public TegrityIntroductionScreen loginToTegrityAsUser(String login, String password) {

		Logger.info("Logging in to Tegtity instance: URL=" + url + " as user: username=" + login + ", password=" + password);
		browser.manage().deleteAllCookies();
		tegrityInstanceLoginScreen = browser.openScreen(url, TegrityInstanceLoginScreen.class);
		return tegrityInstanceLoginScreen.loginToCampusAsUser(login, password);
	}

	public ConnectLoginScreen openConnectLoginScreenForInstructor(String url) {

		Logger.info("Open Connect Login Screen for instructor");
		browser.manage().deleteAllCookies();
		return browser.openScreen(url, ConnectLoginScreen.class);
	}
	
	public ConnectStudentRegistrationScreen openConnectLoginScreenForStudent(String url) {

		Logger.info("Open Connect Login Screen for student");
		browser.manage().deleteAllCookies();
		return browser.openScreen(url, ConnectStudentRegistrationScreen.class);
	}
}
