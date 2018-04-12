package com.mcgraw.test.automation.ui.mhcampus.course.aleks;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.RadioButton;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl(relative = false, value = "aleks.com")  // old value is: value = "secure-awa.aleks.com"
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@alt='ALEKS - Assessment and Learning']")))
public class MhCampusALEKSReadyToUseScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.CSS, using = "input[value='request_class_test']"))
	RadioButton requestClassTest;

	@DefinedLocators(@DefinedLocator(how = How.CSS, using = "input[value='request_use_now']"))
	RadioButton requestUseNow;

	public MhCampusALEKSReadyToUseScreen(Browser browser) {
		super(browser);
	}

}
