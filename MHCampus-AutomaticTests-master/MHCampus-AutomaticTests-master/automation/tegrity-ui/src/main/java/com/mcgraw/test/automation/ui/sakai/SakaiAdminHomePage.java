package com.mcgraw.test.automation.ui.sakai;

import org.openqa.selenium.By;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@class = 'portletMainWrap']")))
public class SakaiAdminHomePage extends Screen {

	@DefinedLocators(@DefinedLocator(using = "//*[@title = 'More Sites']"))
	Element moreSitesBtn;
	
	@DefinedLocators(@DefinedLocator(using = "//*[@id='otherSiteCloseW']/a/span[1]"))
	Element closeBtn;
	
	private String courseBtnForAdminPattern = "//*/span[contains(text(),'<currentCourse>')]";

	public SakaiAdminHomePage(Browser browser) {
		super(browser);
	}

	public SakaiCourseDetailsScreen goToCreatedCourse(String course) {
		
		clickMoreSitesBtn();	
		browser.makeScreenshot();
		if (browser.isElementPresent(By.xpath(courseBtnForAdminPattern.replace(
				"<currentCourse>", course)))) {
			Element courseBtn = browser.findElement(By
					.xpath(courseBtnForAdminPattern.replace("<currentCourse>",course)));
			Logger.info("Clicking Course button...");
			courseBtn.click();
			browser.makeScreenshot();
		} else {
			Logger.info("Clicking Close button...");
			closeBtn.click();
			browser.makeScreenshot();
			if (browser.isElementPresent(By.xpath(".//span[contains(text(),'<currentCourse>')]"
					.replace("<currentCourse>",course)))) {
				Element courseBtn = browser.findElement(By
						.xpath(".//span[contains(text(),'<currentCourse>')]".replace("<currentCourse>",course)));
				Logger.info("Clicking Course button...");
				courseBtn.click();
				browser.makeScreenshot();
			}else
				Logger.info(course + " doesn't present");
		}
		return browser.waitForPage(SakaiCourseDetailsScreen.class);
	}

	private void clickMoreSitesBtn() {;
		browser.waitForElementPresent(moreSitesBtn);
		Logger.info("Clicking 'More Sites' button...");
		moreSitesBtn.click();
	}
}
