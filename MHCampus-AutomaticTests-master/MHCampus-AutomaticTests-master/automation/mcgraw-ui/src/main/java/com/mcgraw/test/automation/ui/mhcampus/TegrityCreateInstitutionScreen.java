package com.mcgraw.test.automation.ui.mhcampus;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id='ctl00_ContentPlaceHolder1_ClientServicesMain1_NewInstitute_BreadCrumb1_LabelCurrent'][contains(text(),'New Institution')]")))
public class TegrityCreateInstitutionScreen extends
		TegrityCreateMhCampusCustomerScreenBase {
	private static final String START_OF_ID = "ctl00_ContentPlaceHolder1_ClientServicesMain1_";

	@DefinedLocators(@DefinedLocator(how = How.ID, using = START_OF_ID
			+ "NewInstitute_TextBoxNewInstitution"))
	Input userFullInstitution;
	@DefinedLocators(@DefinedLocator(how = How.ID, using = START_OF_ID
			+ "NewInstitute_ButtonSaveNewInstitution"))
	Element saveButton;

	public TegrityCreateInstitutionScreen(Browser browser) {
		super(browser);
	}

	public void typeFullNameOfInstitution(String institution) {
		userFullInstitution.clearAndTypeValue(institution);
	}

	public TegrityCreateMhCampusCustomerScreen saveChanges() {
		Logger.info("Saving full institution name...");
		saveButton.click();
		return browser.waitForPage(TegrityCreateMhCampusCustomerScreen.class);
	}

}
