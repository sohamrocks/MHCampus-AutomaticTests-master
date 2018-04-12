package com.mcgraw.test.automation.ui.sakai;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.CheckBox;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageFrameIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.CLASS_NAME, using = "portletMainIframe")))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//h2[contains(.,'Course Grade Options')]")))
public class SakaiCourseGradeOptionsScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.CSS, using = "input[name*='displayCourseGrades']"))
	CheckBox displayCourseGradeToStudents;

	@DefinedLocators(@DefinedLocator(how = How.CSS, using = "input[name*='saveButton']"))
	Element saveButton;

	public SakaiCourseGradeOptionsScreen(Browser browser) {
		super(browser);
	}

	public SakaiGradesScreen setDisplayCourseGradeToStudents(boolean checked) {
		browser.switchTo().frame(0);
		displayCourseGradeToStudents.setChecked(checked);
		saveButton.click();
		browser.switchTo().defaultContent();
		return browser.waitForPage(SakaiGradesScreen.class);
	}

	public SakaiGradesScreen allowDisplayCourseGradeForStudents() {
		return setDisplayCourseGradeToStudents(true);
	}
}
