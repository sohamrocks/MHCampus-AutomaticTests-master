package com.mcgraw.test.automation.ui.applications;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Value;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.ui.canvas.CanvasHomeScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasLoginScreen;

public class CanvasApplication {
	
	@Value("${tegrity.servicelocation}")
	public String tegrityServiceLocation;

	@Value("${canvas.api.baseurl}")
	public String canvasBaseUrl;

	@Value("${canvas.api.accesstoken}")
	public String canvasAccessToken;

	@Value("${canvas.title}")
	public String canvasTitle;

	@Value("${canvas.fqdn}")
	public String canvasFqdn;

	@Value("${canvas.interlinktype}")
	public String canvasInterlinkType;

	@Value("${canvas.useridorigin}")
	public String canvasUserIdOrigin;

	@Value("${canvas.courseidorigin}")
	public String canvasCourseIdOrigin;

	@Value("${canvas.securegateway}")
	public String canvasSecureGateway;

	@Value("${canvas.adminlogin}")
	public String canvasAdminLogin;

	@Value("${canvas.adminpassword}")
	public String canvasAdminPassword;
	
	@Value("${lti.launch.url}")
	public String ltiLaunchUrl;
	
	@Value("${canvas.connect.baseurl}")
	public String connectBaseUrl;
	
	@Value("${tegrity.path.to.resources}")
	public String pathToFile;	
	
	Browser browser;

	private CanvasLoginScreen canvasLoginScreen;

	public CanvasApplication(Browser browser) {
		this.browser = browser;
	}

    public CanvasHomeScreen loginToCanvasAndAcceptTerms(String username, String password) {
		
		try{
			Logger.info("Logging in to Canvas...");
			browser.pause(3000);
			browser.manage().deleteAllCookies();
			canvasLoginScreen = browser.openScreen(canvasBaseUrl,
					CanvasLoginScreen.class);
		}catch(Exception e){
			Logger.info("Try Login in to Canvas again...");
			logoutFromCanvas();
			browser.pause(3000);
			browser.manage().deleteAllCookies();
			canvasLoginScreen = browser.openScreen(canvasBaseUrl,
					CanvasLoginScreen.class);
		}
		return canvasLoginScreen.loginToCanvasAndAcceptTerms(username, password);
	}
	
	public CanvasHomeScreen loginToCanvas(String username, String password) {
		
		try{
			Logger.info("Logging in to Canvas...");
			browser.pause(3000);
			browser.manage().deleteAllCookies();
			canvasLoginScreen = browser.openScreen(canvasBaseUrl,
					CanvasLoginScreen.class);
		}catch(Exception e){
			Logger.info("Try Login in to Canvas again...");
			logoutFromCanvas();
			browser.pause(3000);
			browser.manage().deleteAllCookies();
			canvasLoginScreen = browser.openScreen(canvasBaseUrl,
					CanvasLoginScreen.class);
		}
		return canvasLoginScreen.loginToCanvas(username, password);
	}
	
    public CanvasLoginScreen logoutFromCanvas() {
		
		Logger.info("Logout from Canvas...");
		try{
			Element account = browser.waitForElement(By.id("global_nav_profile_link"),10);
			account.click();
		}
		catch(Exception e){
			browser.switchTo().defaultContent();
			Element account = browser.waitForElement(By.id("global_nav_profile_link"),10);
			account.click();
		}
		//account.click();
		browser.pause(1000);
		browser.makeScreenshot();
		Element logoutButton = browser.waitForElement(By.xpath("//button[contains(text(),'Logout')]"));
		logoutButton.jsClick(browser);
		browser.pause(1000);
		
		return browser.waitForPage(CanvasLoginScreen.class);
    }
}
