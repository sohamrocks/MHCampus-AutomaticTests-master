package com.mcgraw.test.automation.ui.moodle;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Grade administration')]")))
public class MoodleGradesScreenOldVer extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@class = 'boxaligncenter generaltable user-grade']//tbody//th[1]"))
    Element courseName;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@class = 'boxaligncenter generaltable user-grade']//tbody//tr[3]//th[1]"))
    Element assignmentTitle;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@class = 'boxaligncenter generaltable user-grade']//tbody//tr[3]//td[2]"))
	Element scoreReceived;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@class = 'boxaligncenter generaltable user-grade']//tbody//tr[3]//td[3]"))
	Element rangeOfGrades;                                                       

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@class = 'boxaligncenter generaltable user-grade']//tbody//tr[3]//td[3]//div"))
	Element feedback;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@class = 'boxaligncenter generaltable user-grade']//tbody//tr[2]//th[1]"))
	Element categoryType;

	private static String SEPARATOR = "–";
	private static String DOT = ".";
	
	public MoodleGradesScreenOldVer(Browser browser) {
		super(browser);
	}

	public String getCourseName() {
		Logger.info("Getting Course Name...");
		if (isCategoryTypePresent()) 
			return courseName.getText();
		else
			return null;
	}

	public String getCategoryType() {
		Logger.info("Getting Category Type...");
		if (isCategoryTypePresent())
			return categoryType.getText();
		else
			return null;
	}

	public String getAssignmentTitle() {
		Logger.info("Getting Assignment Title...");
		if (isAssignmentTitlePresent())
			return assignmentTitle.getText();
		else
			return null;
	}

	public String getRangeOfGrades() {
		if(isRangeOfGradesPresent())
		  return rangeOfGrades.getText();
		else
			return null;
	}

	public String getScorePossible() {
		Logger.info("Getting Possible Score...");
		if (isRangeOfGradesPresent()) 
			return getRangeOfGrades().substring(getRangeOfGrades().indexOf(SEPARATOR) + 1);
		else
			return null;
	}

	public String getScoreReceived() {
		Logger.info("Getting Received Score...");
		if (isScoreReceivedPresent()) 
			return scoreReceived.getText().substring(0, scoreReceived.getText().indexOf(DOT));
		else
			return null;
	}

	public String getFeedback() {
		Logger.info("Getting Feedback...");
		if (isFeedbackPresent())
			return feedback.getText();
		else
			return null;
	}

	public int getCountOfCategoryTypes() {
		Logger.info("Getting Count of Category Types...");
		return browser.getElementsCount(By.xpath("//*[@alt = 'Category']")) - 1;
	}
	
	public int getCountOfAssignment() {
		Logger.info("Getting Count of Assignment...");
		return browser.getElementsCount(By.xpath("//*[@alt = 'Manual item']"));
	}

	public boolean isScoreReceivedPresent() {
		return scoreReceived.isElementPresent();
	}
	
	public boolean isCourseNamePresent() {
		return courseName.isElementPresent();
	}
	
	public boolean isCategoryTypePresent() {
		return categoryType.isElementPresent();
	}

	public boolean isAssignmentTitlePresent() {
		return assignmentTitle.isElementPresent();
	}

	public boolean isRangeOfGradesPresent() {
		return rangeOfGrades.isElementPresent();
	}

	public boolean isFeedbackPresent() {
		return feedback.isElementPresent();
	}
}
