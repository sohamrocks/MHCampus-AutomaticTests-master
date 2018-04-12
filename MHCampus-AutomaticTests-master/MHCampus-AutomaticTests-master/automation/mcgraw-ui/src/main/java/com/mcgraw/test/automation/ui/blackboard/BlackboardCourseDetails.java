package com.mcgraw.test.automation.ui.blackboard;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.simnet.MhCampusSimNetCreateLinkScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.simnet.MhCampusSimNetHomeScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.simnet.MhCampusSimNetPairingPortalForInstructor;
import com.mcgraw.test.automation.ui.mhcampus.course.simnet.MhCampusSimNetPairingPortalForStudent;

@PageRelativeUrl("")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id = 'Courses.label'][@class = 'active']")))
public class BlackboardCourseDetails extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='MyInstitution.label']/a/span[1]"))
	Element myInstitutionLink;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//span[contains(text(),'McGraw-Hill Campus')]"))
	Element mcGrawHillCampusLink;

	private static By mhCampusLink = By
			.xpath(".//span[contains(text(),'McGraw-Hill Campus')]");
//	Added by Yuliia 
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[contains(text(),'Content')]"))
	Element contentBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[text()='Content Market']"))
	Element contentMarketBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@id='#portfolio-list-MHE']/li[1]/a/span[2]"))
	Element simNetLink;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[contains(text(),'Your SIMnet Course Content')]"))
	Element simNetCourseContent;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@id='anonymous_element_21']/a/span"))
	Element simNetLinkExist;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@id='content_listContainer']/LI[1]//h3/a/span"))
	Element simNetLinkExistForStudent;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[@id='content-handler-resource/x-bb-assignment']"))
	Element assignmentBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@id='topframe.logout.label']"))
	Element logoutButton;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'Close')]"))
	Element closePopUp;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@id='shareUserInfoAgree']"))
	Element agreeRadioBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@id='bottom_launchsubmit']"))
	Element launchBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@id='top_submitButtonRow']/input[2]"))
	Element submitBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[contains(text(),'Success: Content added')]"))
	Element successfullContantAddedMessage;

	public void goToContentTab() {
		Logger.info("Clicking Content Tab...");
		contentBtn.click();
		browser.waitForElement(By.xpath("//*[@id='pageTitleText']/span[(text()='Content')]")); 
		browser.makeScreenshot();
	}
	
	public void goToContentMarket() {
		Logger.info("Clicking Content Tab...");
		contentBtn.click();
		browser.waitForElement(By.xpath("//*[@id='pageTitleText']/span[(text()='Content')]")); 
		Logger.info("Clicking Content Market Link, that is located in the drop-down list 'Partner Content'...");
		contentMarketBtn.jsClick(browser);
		try{
			browser.waitForElement(By.xpath("//h1[contains(text(),'New Partners')]"),10);
			closePopUp.click();
		}catch(Exception e){}
		browser.waitForElement(By.xpath("//span[@id='pageTitleText' and contains(text(),'Content Market')]")); 
		}
	
	public MhCampusSimNetCreateLinkScreen createSimNetLink() {
		Logger.info("Clicking SIMnet link...");
		simNetLink.jsClick(browser);
		accseptUserPrivacyInformation();
		MhCampusSimNetCreateLinkScreen mhCampusSimNetCreateLinkScreen = browser.waitForPage(MhCampusSimNetCreateLinkScreen.class);
		browser.makeScreenshot();
		browser.switchTo().defaultContent();
		return mhCampusSimNetCreateLinkScreen;
	}
	public MhCampusSimNetPairingPortalForInstructor clickSimNetLinkOnContentMarket() {
		Logger.info("Clicking SIMnet link...");
		simNetCourseContent.click();
		MhCampusSimNetPairingPortalForInstructor mhCampusSimNetPairingPortalForInstructor=browser.waitForPage(MhCampusSimNetPairingPortalForInstructor.class);
		browser.makeScreenshot();
		return mhCampusSimNetPairingPortalForInstructor;
	}
	
	public MhCampusSimNetHomeScreen clickSimNetLinkAsInstructorByTitle(String title) {
		Logger.info("Clicking SimNet link as instructor...");
		browser.waitForElement(By.xpath("//*[@id='content_listContainer']"));
		browser.findElement(By.xpath("//span[contains(text(),'"+title+"')]")).click();
		MhCampusSimNetHomeScreen mhCampusSimNetHomeScreen=browser.waitForPage(MhCampusSimNetHomeScreen.class);
		browser.makeScreenshot();
		return mhCampusSimNetHomeScreen;
	}
	public MhCampusSimNetPairingPortalForStudent clickSimNetLinkAsStudent() {
		Logger.info("Clicking SimNet link as student...");
		browser.waitForElement(By.xpath("//*[@id='content_listContainer']"));
		simNetLinkExistForStudent.click();
		MhCampusSimNetPairingPortalForStudent mhCampusSimNetPairingPortalForStudent=browser.waitForPage(MhCampusSimNetPairingPortalForStudent.class);
		browser.makeScreenshot();
		return mhCampusSimNetPairingPortalForStudent;
	}
	
	public MhCampusSimNetHomeScreen clickToSimNetLinkStudentlevelLaunch() {
		Logger.info("Clicking SIMnet Link...");
		goToContentTab();
		browser.waitForElement(By.xpath("//*[@id='content_listContainer']"));
		simNetLinkExist.click();
		browser.switchTo().frame("tool_content");
		browser.pause(3000);
		browser.makeScreenshot();
		MhCampusSimNetHomeScreen mhCampusSimNetHomeScreen = browser.waitForPage(MhCampusSimNetHomeScreen.class);
		browser.makeScreenshot();
		browser.switchTo().defaultContent();
		return mhCampusSimNetHomeScreen;
	}
	
	public void accseptUserPrivacyInformation(){
		Logger.info("Accept user privacy information if this window is present...");
		try{
			browser.waitForElement(By.xpath("//h3[@class='steptitle']"), 10);
			agreeRadioBtn.click();
			
			launchBtn.click();
		}catch(Exception e){}
	}
	
	public void completionOfDeployAssignment(){
		Logger.info("Сompletion of deloy assignment...");
		browser.pause(3000);
		browser.waitForElement(By.xpath("//*[@id='top_submitButtonRow']/input[2]"));
		submitBtn.click();
		browser.makeScreenshot();
	}
	public boolean successfullContentAdded() {
		Logger.info("Checking if successfull Content added");
		browser.pause(6000);
		boolean elementPresent = successfullContantAddedMessage.isElementPresent();
		return elementPresent;
	}
	
	public BlackboardLoginScreen logout() {
		Logger.info("Logout from Blackboard...");
		browser.pause(1000);
		browser.switchTo().defaultContent();
		logoutButton.click();
		return browser.waitForPage(BlackboardLoginScreen.class);
	}
	
//===============================================================================================================
	public BlackboardCourseDetails(Browser browser) {
		super(browser);
	}

	public int getMhCampusLinksCount() {
		clickRefresh();
		browser.makeScreenshot();
		int linksCount = browser.getElementsCount(mhCampusLink);
		return linksCount;
	}

	public MhCampusIntroductionScreen clickMhCampusLink() {
		Logger.info("Clicking Mh Campus link...");
		mcGrawHillCampusLink.click();
		return browser.waitForPage(MhCampusIntroductionScreen.class);
	}

	public BlackboardHomeScreen clickMyInstitutionLink() {
		Logger.info("Clicking Institution link...");
		myInstitutionLink.click();
		browser.pause(6000);
		return browser.waitForPage(BlackboardHomeScreen.class);
	}

	private void clickRefresh() {
		Element refreshLink = browser.findElement(By
				.xpath(".//*[@id='refreshMenuLink']/a//span"));
		refreshLink.click();
	}

}
