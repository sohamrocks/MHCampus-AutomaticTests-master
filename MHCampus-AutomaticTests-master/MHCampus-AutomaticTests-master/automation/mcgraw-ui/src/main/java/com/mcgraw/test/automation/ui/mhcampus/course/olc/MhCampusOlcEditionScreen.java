package com.mcgraw.test.automation.ui.mhcampus.course.olc;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@alt = 'Online Learning Center']")))
public class MhCampusOlcEditionScreen extends Screen {

	@DefinedLocators(@DefinedLocator(using = ".//*[@id='instructorsEdition']//img[@alt = 'Instructor Edition']"))
	Element instructorEditionTitle;

	@DefinedLocators(@DefinedLocator(using = ".//*[@id='studentEdition']//img[@alt = 'Student Edition']"))
	Element studentEditionTitle;

	@DefinedLocators(@DefinedLocator(using = "//*[@class = 'author']"))
	Element authorsNames;

	@DefinedLocators(@DefinedLocator(using = "//h3"))
	Element titleOfBook;

	public MhCampusOlcEditionScreen(Browser browser) {
		super(browser);
	}

	public String getAuthorsNames() {
		return authorsNames.getText();
	}

	public String getBookTitle() {
		return titleOfBook.getText();
	}

	public boolean isInstructorEditionAppear() {
		return instructorEditionTitle.isElementPresent();
	}

	public boolean isStudentEditionAppear() {
		return studentEditionTitle.isElementPresent();
	}
}
