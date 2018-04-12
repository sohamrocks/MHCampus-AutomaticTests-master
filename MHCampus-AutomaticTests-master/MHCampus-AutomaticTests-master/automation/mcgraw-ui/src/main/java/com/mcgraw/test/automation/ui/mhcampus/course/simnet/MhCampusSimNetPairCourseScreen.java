package com.mcgraw.test.automation.ui.mhcampus.course.simnet;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.RadioButton;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl(relative = false, value = "simnetonline.com")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//div[contains(@class,'logo-simnet')]")))
public class MhCampusSimNetPairCourseScreen extends Screen {

	// pair class
	// ----------------------------------------------------------------------------
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_PairingRadioButtonList_0"))
	RadioButton radioButtonNewSimNetClass;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClassBox"))
	Input classTitleinput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_SectionBox"))
	Input SectionTitleInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_CourseNextButton"))
	Element nextBtn;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_LoginButton"))
	Element continuetoSimNetBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//h2[contains(text(),'Pair your class with SIMnet')]"))
	Element pairYourClassMessage;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='registrationPanel']/div[2]/h2"))
	Element successfullPairedMessage;


	public MhCampusSimNetPairCourseScreen(Browser browser) {
		super(browser);
	}

	public void pairCourse(String classTitle, String sectionTitle) {
		Logger.info("Pair new SimNet class ");
		browser.waitForElementPresent(radioButtonNewSimNetClass).click();
		browser.waitForElementPresent(classTitleinput).sendKeys(classTitle);
		browser.waitForElementPresent(SectionTitleInput).sendKeys(sectionTitle);
		browser.waitForElementPresent(nextBtn).click();
		browser.switchTo().defaultContent();
	}

	public void clickContinueBtn() {

		continuetoSimNetBtn.click();
	}

	public boolean pairYourClassMessageIsPresent() {
		Logger.info("Checking if SIMnet class message for is shown");
		browser.pause(5000);
		boolean elementPresent = browser.waitForElementPresent(pairYourClassMessage).isElementPresent();
		return elementPresent;
	}

	public boolean successfullPairedMessageIsPresent() {
		Logger.info("Checking if successfull Paired Message for is shown");
		browser.pause(6000);
		browser.switchTo().frame("tool_content");
		boolean elementPresent = successfullPairedMessage.isElementPresent();
		return elementPresent;
	}
	
	public boolean successfullPairedMessageIsPresentBB() { //added by Yuliya
		Logger.info("Checking if successfull Paired Message for is shown");
		browser.pause(6000);
		boolean elementPresent = successfullPairedMessage.isElementPresent();
		return elementPresent;
	}


}
