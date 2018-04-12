package com.mcgraw.test.automation.ui.applications;

import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceDashboardForHelpDeskAdminScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceDashboardScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceLoginScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityIntroductionScreen;

public class TegrityInstanceApplicationNoLocalConnector {
	
	@Value("${tegrity.instance.nolocalconector.institution}")
	public String institution;

	@Value("${tegrity.instance.nolocalconector.username}")
	public String username;

	@Value("${tegrity.instance.nolocalconector.password}")
	public String password;

	@Value("${tegrity.instance.nolocalconector.customernumber}")
	public String customerNumber;

	@Value("${tegrity.instance.nolocalconector.sharedsecret}")
	public String sharedSecret;

	@Value("${tegrity.instance.nolocalconector.url}")
	public String url;

	public long DIRECT_LOGIN_TIMEOUT = (1 * 60 * 1000); // 1 minute

	private TegrityInstanceLoginScreen tegrityInstanceLoginScreen;
	private TegrityInstanceDashboardScreen tegrityInstanceDashboardScreen;

	private Browser browser;

	public TegrityInstanceApplicationNoLocalConnector(Browser browser) {
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
	
	public TegrityInstanceDashboardScreen loginToTegrityInstanceAsAdmin(){   

		Logger.info("Logging in to Tegrity instance...");
		browser.manage().deleteAllCookies();
		browser.pause(1000);
		tegrityInstanceLoginScreen = browser.openScreen(url, TegrityInstanceLoginScreen.class);
		return tegrityInstanceLoginScreen.loginToTegrityInstanceAsAdmin(username, password);
	}
	
	public TegrityInstanceDashboardScreen loginToTegrityInstanceAsAdmin(String login, String password){   

		Logger.info("Logging in to Tegrity instance...");
		browser.manage().deleteAllCookies();
		tegrityInstanceLoginScreen = browser.openScreen(url, TegrityInstanceLoginScreen.class);
		return tegrityInstanceLoginScreen.loginToTegrityInstanceAsAdmin(login, password);
	}
	
	public TegrityInstanceDashboardForHelpDeskAdminScreen loginToTegrityInstanceAsHelpDeskAdmin(String login, String password){   

		Logger.info("Logging in to Tegrity instance...");
		browser.manage().deleteAllCookies();
		browser.pause(1000);
		tegrityInstanceLoginScreen = browser.openScreen(url, TegrityInstanceLoginScreen.class);
		return tegrityInstanceLoginScreen.loginToTegrityInstanceAsHelpDeskAdmin(login, password);
	}
	
	public TegrityIntroductionScreen loginToTegrityAsUser(String login, String password) {

		Logger.info("Logging in to Tegtity instance: URL=" + url + " as user: username=" + login + ", password=" + password);
		browser.manage().deleteAllCookies();
		tegrityInstanceLoginScreen = browser.openScreen(url, TegrityInstanceLoginScreen.class);
		TegrityIntroductionScreen tegrityIntroductionScreen =  tegrityInstanceLoginScreen.loginToCampusAsUser(login, password);
		browser.makeScreenshot();
		return tegrityIntroductionScreen;
	}
	
	public boolean canLoginToTegrityAsUser(String login, String password) {

		Logger.info("Logging in to Tegtity instance: URL=" + url + " as user: username=" + login + ", password=" + password);
		boolean flag = true;
		browser.manage().deleteAllCookies();
		tegrityInstanceLoginScreen = browser.openScreen(url, TegrityInstanceLoginScreen.class);
		try{
			tegrityInstanceLoginScreen.loginToCampusAsUser(login, password);
			Logger.info("Can login into Tegtity instance");
		}catch (Exception e){
			flag = false;
			Logger.info("Can NOT login into Tegtity instance");
		}
		browser.makeScreenshot();
		return flag;
	}

	public void createCsvFile(String filePath, String fileContent) {
		Logger.info("Creating CSV file containing the text: " + fileContent);
		FileWriter os;
		try {
			os = new FileWriter(filePath);
			os.write(fileContent);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}