package com.mcgraw.test.automation.ui.sakai;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageFrameIdentificator(locators = @DefinedLocators({@DefinedLocator(how = How.CSS, using = ".portletMainWrap iframe")}))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//span[contains(text(),'General')]")))
public class SakaiExternalToolsScreen extends Screen {

	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "sakai.basiclti"))
	Element externalToolBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "title_sakai.basiclti"))
	Element title;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "revise"))
	Element reviseBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.NAME, using = "Continue"))
	Element continueBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*/span[contains(text(),'Tegrity')]"))
	Element tegrityLink;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "jsr-edit"))
	Element editBtn;

	public SakaiExternalToolsScreen(Browser browser) {
		super(browser);
	}
	
	public SakaiSetupExternalToolsScreen clickSetupBtn() {
		browser.switchTo().frame(0);
		externalToolBtn.waitForPresence(5);
		externalToolBtn.click();
		continueBtn.waitForPresence(5);
		continueBtn.click();
		browser.makeScreenshot();
		title.waitForPresence(5);
		title.clear();
		title.sendKeys("Tegrity Link");
		browser.makeScreenshot();
		continueBtn.waitForPresence(5);
		continueBtn.click();
		browser.makeScreenshot();
		reviseBtn.waitForPresence(5);
		reviseBtn.click();
		browser.makeScreenshot();
		
		tegrityLink.waitForPresence(5);
		tegrityLink.click();
		browser.makeScreenshot();
		editBtn.waitForPresence(5);
		editBtn.click();
		browser.makeScreenshot();
		
		browser.switchTo().defaultContent();
		return browser.waitForPage(SakaiSetupExternalToolsScreen.class);
	}

}
