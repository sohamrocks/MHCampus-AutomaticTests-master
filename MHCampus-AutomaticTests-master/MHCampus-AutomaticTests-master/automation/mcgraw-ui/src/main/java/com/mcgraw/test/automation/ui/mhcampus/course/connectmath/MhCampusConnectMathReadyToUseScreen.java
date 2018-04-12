package com.mcgraw.test.automation.ui.mhcampus.course.connectmath;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.RadioButton;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl(relative = false, value = "connectmath.com")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Ready to Use Connect Math?')]")))
public class MhCampusConnectMathReadyToUseScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.CSS, using = "input[value='request_class_test']"))
	RadioButton requestClassTest;

	@DefinedLocators(@DefinedLocator(how = How.CSS, using = "input[value='request_use_now']"))
	RadioButton requestUseNow;

	public MhCampusConnectMathReadyToUseScreen(Browser browser) {
		super(browser);
	}

}
