package com.mcgraw.test.automation.ui.mhcampus.course.smartbook;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl(relative = false, value = "mhlearnsmart.com")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//title[contains(text(),'Flow runner')]")))
public class MhCampusSmartBookFlowRunner extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "flowFlash"))
	Element smartBookDashboard;

	public MhCampusSmartBookFlowRunner(Browser browser) {
		super(browser);
	}

	public boolean isSmartBookDashboardPresent() {
		Logger.info("Check Smart Book Dashboard is present");
		return smartBookDashboard.waitForPresence(30);
	}

}
