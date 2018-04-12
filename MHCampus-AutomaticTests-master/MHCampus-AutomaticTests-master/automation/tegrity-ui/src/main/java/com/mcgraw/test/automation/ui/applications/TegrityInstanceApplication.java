package com.mcgraw.test.automation.ui.applications;

import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceDashboardScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceLoginScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityIntroductionScreen;

public class TegrityInstanceApplication {
	
	@Value("${tegrity.instance.institution}")
	public String institution;

	@Value("${tegrity.instance.username}")
	public String username;

	@Value("${tegrity.instance.password}")
	public String password;

	@Value("${tegrity.instance.customernumber}")
	public String customerNumber;

	@Value("${tegrity.instance.sharedsecret}")
	public String sharedSecret;

	@Value("${tegrity.instance.url}")
	public String url;

	public long DIRECT_LOGIN_TIMEOUT = (1 * 60 * 1000); // 1 minute

	private TegrityInstanceLoginScreen tegrityInstanceLoginScreen;
	private TegrityInstanceDashboardScreen tegrityInstanceDashboardScreen;

	private Browser browser;

	public TegrityInstanceApplication(Browser browser) {
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
		browser.pause(2000);
		tegrityInstanceLoginScreen = browser.openScreen(url, TegrityInstanceLoginScreen.class);
		return tegrityInstanceLoginScreen.loginToCampusAsUser(login, password);
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
	
	public void createCsvFile(String filePath, String []courses) {
		//Logger.info("Creating CSV file containing the text: " + fileContent1 + " and: " + fileContent2);
		Logger.info("Creating CSV file containing the text: ");
		for (int i=0;i<courses.length;i++){
			Logger.info(courses[i]);
		}
		FileWriter os;
		try {
			os = new FileWriter(filePath);
			for (int i=0;i<(courses.length)-1;i++){
				os.write(courses[i]);
				os.write(System.getProperty("line.separator"));
			}
			os.write(courses[(courses.length)-1]);
			//os.write(fileContent2);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
