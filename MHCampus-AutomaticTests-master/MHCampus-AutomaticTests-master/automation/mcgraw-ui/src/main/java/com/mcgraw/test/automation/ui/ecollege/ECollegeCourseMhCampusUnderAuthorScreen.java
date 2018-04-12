package com.mcgraw.test.automation.ui.ecollege;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageFrameIdentificator(locators = @DefinedLocators({ @DefinedLocator(how = How.ID, using = "Main"),
		@DefinedLocator(how = How.ID, using = "Content") }))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//div[@class='tablehead' and contains(.,'MHCampus')]")))
public class ECollegeCourseMhCampusUnderAuthorScreen extends ECollegeCourseGeneralScreen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "RadeEntryText_textArea"))
	Element scriptArea;

	@DefinedLocators(@DefinedLocator(how = How.CSS, using = "input[value='Save Changes']"))
	Element saveChangesButton;
	
	private static String customerNumberPattern = "product=(\\w+-\\w+-\\w+)";

	public ECollegeCourseMhCampusUnderAuthorScreen(Browser browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	public ECollegeCourseMhCampusUnderAuthorScreen changeCustomerNumber(String customerNumber) {
		Logger.info("Change cutomer number to: "+ customerNumber);
		browser.switchTo().frame(mainFrame);
		browser.switchTo().frame(contentFrame);
		String initialScript = scriptArea.getAttribute("textContent");
		String resultScript = initialScript.replaceAll(customerNumberPattern, "product=" + customerNumber);
		scriptArea.clear();
		scriptArea.sendKeys(resultScript);
		saveChangesButton.click();
		browser.waitForAlert(10);
		browser.clickOkInAlertIfPresent();
		browser.switchTo().defaultContent();

		return browser.waitForPage(ECollegeCourseMhCampusUnderAuthorScreen.class);

	}

}
