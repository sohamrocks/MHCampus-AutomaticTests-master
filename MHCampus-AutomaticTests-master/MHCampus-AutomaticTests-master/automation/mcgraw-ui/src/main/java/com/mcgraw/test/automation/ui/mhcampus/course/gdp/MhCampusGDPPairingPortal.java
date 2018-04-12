package com.mcgraw.test.automation.ui.mhcampus.course.gdp;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl(relative = false, value = "lms.gdp11.com")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//div[contains(text(),'What GDP')]")))
public class MhCampusGDPPairingPortal extends Screen {

	public MhCampusGDPPairingPortal(Browser browser) {
		super(browser);
	}

}
