package com.mcgraw.test.automation.ui.sakai;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageFrameIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.CLASS_NAME, using = "portletMainIframe")))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//h2[contains(.,'Gradebook Items')]")))
public class SakaiGradesScreen extends Screen {

	@DefinedLocators(@DefinedLocator(using = "//tr[@id = '_id_0__hide_division_']//td[1]//a"))
	Element assignmentTitle;

	@DefinedLocators(@DefinedLocator(using = "//tr[@id = '_id_0__hide_division_']//td[3]"))
	Element grades;

	@DefinedLocators(@DefinedLocator(using = "//tr[@id = '_id_0__hide_division_']//td[4]"))
	Element dueToDate;

	@DefinedLocators(@DefinedLocator(using = "//ul[contains(@class,'navIntraTool')]//a[contains(.,'Course Grade Options')]"))
	Element courseGradeOptionsButton;

	private static String SEPARATOR = "/";

	public SakaiGradesScreen(Browser browser) {
		super(browser);
	}

	public String getAssignmentTitle() {
		Logger.info("Getting Assignment Title...");
		if (isAssignmentTitlePresent()) {
			browser.switchTo().frame(0);
			String title = assignmentTitle.getText();
			browser.switchTo().defaultContent();
			return title;
		} else
			return null;
	}

	public String getDueToDate() throws ParseException {
		Logger.info("Getting Due Date...");
		if (isDueToDatePresent()) {
			browser.switchTo().frame(0);
			SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
			Date date = formatter.parse(dueToDate.getText());
			SimpleDateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy");
			String dueToDate = formatter2.format(date);
			browser.switchTo().defaultContent();
			return dueToDate;
		} else
			return null;
	}

	private String getGrades() {
		browser.switchTo().frame(0);
		String grade = grades.getText();
		browser.switchTo().defaultContent();
		return grade;
	}

	public String getScorePossible() {
		Logger.info("Getting Score Possible...");
		if (isGradesPresent()) {
			return getGrades().substring(getGrades().indexOf(SEPARATOR) + 1);
		} else
			return null;
	}

	public String getScoreReceived() {
		Logger.info("Getting Received Score...");
		if (isGradesPresent()) {
			return getGrades().substring(0, getGrades().indexOf(SEPARATOR));
		} else
			return null;
	}

	public boolean isAssignmentTitlePresent() {
		browser.switchTo().frame(0);
		boolean isTitlePresent = assignmentTitle.isElementPresent();
		browser.switchTo().defaultContent();
		return isTitlePresent;
	}

	public boolean isDueToDatePresent() {
		browser.switchTo().frame(0);
		boolean isDueToDatePresent = dueToDate.isElementPresent();
		browser.switchTo().defaultContent();
		return isDueToDatePresent;
	}

	public boolean isGradesPresent() {
		browser.switchTo().frame(0);
		boolean isGradesPresent = grades.isElementPresent();
		browser.switchTo().defaultContent();
		return isGradesPresent;
	}

	public SakaiCourseGradeOptionsScreen goToCourseGradeOptions() {
		browser.switchTo().frame(0);
		browser.waitForElementPresent(courseGradeOptionsButton).click();
		return browser.waitForPage(SakaiCourseGradeOptionsScreen.class);
	}

	public SakaiGradesDetailsScreen goToDetailsOfAssignment() {
		browser.switchTo().frame(0);
		assignmentTitle.click();
		browser.switchTo().defaultContent();
		return browser.waitForPage(SakaiGradesDetailsScreen.class);
	}
}
