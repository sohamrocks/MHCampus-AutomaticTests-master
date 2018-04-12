package com.mcgraw.test.automation.ui.mhcampus;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageFrameIdentificator(locators = @DefinedLocators({ @DefinedLocator(how = How.ID, using = "ContentPlaceHolder1_iFrameServiceAdmin") }))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Service Admin Dashboard')]")))
public class MhCampusInstanceDashboardScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ServiceAdminMain1_ServiceAdminDashboard1_LinkButtonManageAAIRS"))
	Element manageAAIRSlink;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ServiceAdminMain1_ServiceAdminDashboard1_LinkButtonManageSSO"))
	Element ssolink;

	public MhCampusInstanceDashboardScreen(Browser browser) {
		super(browser);
	}
	
	public MhCampusInstanceConnectorsScreen clickManageAairs() {
		Logger.info("Clicking manage aairs link...");
		browser.switchTo().frame("ContentPlaceHolder1_iFrameServiceAdmin");
		try{
			manageAAIRSlink.waitForPresence(40);
			manageAAIRSlink.click();
			browser.waitForElement(By.xpath(".//div[@id='DATASET_MAIN']/a[@class='Section_Link editLink']"), 30);
		}catch(Exception e){
			browser.navigate().refresh();
			Logger.info("Trying click manage aairs link again...");
			browser.switchTo().frame("ContentPlaceHolder1_iFrameServiceAdmin");
			manageAAIRSlink.waitForPresence(40);
			manageAAIRSlink.click();
			browser.waitForElement(By.xpath(".//div[@id='DATASET_MAIN']/a[@class='Section_Link editLink']"), 30);
		}
		
		browser.switchTo().defaultContent();
		browser.makeScreenshot();
		return browser.waitForPage(MhCampusInstanceConnectorsScreen.class, 20);
	}
	
	public void configEcollegeIntegration(String customerNumber, String type) throws Exception {
		Logger.info("Configure eCollege integration...");
		MhCampusSSOLinkScreen mhCampusSSOLinkScreen = clickSSOLink() ;	
		mhCampusSSOLinkScreen.configEcollegeIntegration(customerNumber, type);
		browser.switchTo().defaultContent();
		browser.makeScreenshot();
	}
	
	public void configSakaiIntegration(String type) throws Exception {
		Logger.info("Configure Sakai integration...");
		MhCampusSSOLinkScreen mhCampusSSOLinkScreen = clickSSOLink() ;	
		mhCampusSSOLinkScreen.configSakaiIntegration(type);
		browser.switchTo().defaultContent();
		browser.makeScreenshot();
	}
	
	public void useLevelTolkenInCanvas(Boolean flag) throws Exception {
		MhCampusSSOLinkScreen mhCampusSSOLinkScreen = clickSSOLink() ;	
		mhCampusSSOLinkScreen.useLevelTolkenInCanvas(flag);
		browser.switchTo().defaultContent();
		browser.makeScreenshot();
	}
	
	private MhCampusSSOLinkScreen clickSSOLink() {
		Logger.info("Clicking SSO link...");
		browser.switchTo().frame("ContentPlaceHolder1_iFrameServiceAdmin");	
		ssolink.click();
		MhCampusSSOLinkScreen mhCampusSSOLinkScreen = browser.waitForPage(MhCampusSSOLinkScreen.class, 20);	
		browser.switchTo().defaultContent();
		browser.makeScreenshot();
		return mhCampusSSOLinkScreen;
	}
}
