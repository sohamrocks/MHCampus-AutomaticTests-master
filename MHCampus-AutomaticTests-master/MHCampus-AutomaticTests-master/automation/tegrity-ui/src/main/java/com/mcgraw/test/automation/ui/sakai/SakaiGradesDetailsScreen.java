package com.mcgraw.test.automation.ui.sakai;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageFrameIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.CLASS_NAME, using = "portletMainIframe")))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//h3[contains(.,'Gradebook Item Summary')]")))
public class SakaiGradesDetailsScreen extends Screen{
	
	public SakaiGradesDetailsScreen(Browser browser) {
		super(browser);
	}
	
	public String getScoreReceivedForStudent(String studentLogin) {
		browser.switchTo().frame(0);
		String scoreReceivedXpath = "//*[@class = 'gbTextOnRow'][contains(text(),'"
				+ studentLogin + "')]//..//..//td[4]//div";
		browser.makeScreenshot();
		if (browser.isElementPresent(By.xpath(scoreReceivedXpath))) {
			String scoreReceived = browser.findElement(
					By.xpath(scoreReceivedXpath)).getText();
			browser.switchTo().defaultContent();
			return scoreReceived;
		} else {
			browser.switchTo().defaultContent();
			Logger.info("No student with given login " + studentLogin);
			return null;
		}
	}
}
