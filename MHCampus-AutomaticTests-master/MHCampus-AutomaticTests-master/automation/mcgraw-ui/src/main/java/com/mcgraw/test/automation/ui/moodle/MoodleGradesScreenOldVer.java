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

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@id = 'user-grades']/tbody/tr[1]/th"))
    Element courseNameForInstructor; 
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@class = 'boxaligncenter generaltable user-grade']//tbody//tr[3]//th[1]"))
    Element assignmentTitle;   
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@class = 'boxaligncenter generaltable user-grade']//tbody//tr[4]//th[1]"))
    Element assignmentTitleForSecondAssignment;         

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@id = 'user-grades']/tbody/tr[3]/th[3]/span"))
    Element assignmentTitleForInstructor;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@id = 'user-grades']/tbody/tr[3]/th[4]/span"))
    Element assignmentTitleForInstructorForSecondAssignment;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@class = 'boxaligncenter generaltable user-grade']//tbody//tr[3]//td[2]"))
	Element scoreReceived;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@class = 'boxaligncenter generaltable user-grade']//tbody//tr[4]//td[2]"))
	Element scoreReceivedForSecondAssignment;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@class = 'boxaligncenter generaltable user-grade']//tbody//tr[3]//td[3]"))
	Element rangeOfGrades; 
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@class = 'generaltable']/tbody/tr/td[2]"))
	Element rangeOfGradesForInstructor; 
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@class = 'boxaligncenter generaltable user-grade']//tbody//tr[4]//td[3]"))
	Element rangeOfGradesForSecondAssignment;                                                       

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@class = 'boxaligncenter generaltable user-grade']//tbody//tr[3]//td[3]//div"))
	Element feedback;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@class = 'boxaligncenter generaltable user-grade']//tbody//tr[2]//th[1]"))
	Element categoryType;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@id = 'user-grades']/tbody/tr[2]/th"))
	Element categoryTypeForInstructor;

	private static String SEPARATOR = "–";
	private static String SEPARATOR_SHORT = "-";
	private static String DOT = ".";

	public MoodleGradesScreenOldVer(Browser browser) {
		super(browser);
	}

	public String getCourseName() {
		Logger.info("Getting Course Name...");
		return courseName.getText();
	}
	
	public String getCourseNameForInstructor() {
		Logger.info("Getting Course Namee for Instructor...");
		return courseNameForInstructor.getText();
	}

	public String getCategoryType() {
		Logger.info("Getting Category Type...");
		if (isCategoryTypePresent()) {
			return categoryType.getText();
		} else
			return null;
	}
	
	public String getCategoryTypeForInstructor() {
		Logger.info("Getting Category Type for Instructor...");
		if (isCategoryTypePresentForInstructor()) {
			return categoryTypeForInstructor.getText();
		} else
			return null;
	}

	public String getAssignmentTitle() {
		Logger.info("Getting Assignment Title...");
		if (isAssignmentTitlePresent()) {
			return assignmentTitle.getText();
		} else
			return null;
	}
	
	public String getAssignmentTitleForSecondAssignment() {
		Logger.info("Getting Assignment Title for Second Assignment...");
		if (isAssignmentTitlePresentForSecondAssignment()) {
			return assignmentTitleForSecondAssignment.getText();
		} else
			return null;
	}
	
	public String getAssignmentTitleForInstructor() {
		Logger.info("Getting Assignment Title for Instructor...");
		if (isAssignmentTitlePresentForInstructor()) {
			return assignmentTitleForInstructor.getText();
		} else
			return null;
	}
	
	public String getAssignmentTitleForInstructorForSecondAssignment() {
		Logger.info("Getting Assignment Title for Instructor for Second Assignment...");
		if (isAssignmentTitlePresentForInstructorForSecondAssignment()) {
			return assignmentTitleForInstructorForSecondAssignment.getText();
		} else
			return null;
	}

	public String getRange() {
		Logger.info("rangeOfGrades.getText(): "+rangeOfGrades.getText());
		return rangeOfGrades.getText();
	}
	
	public String getRangeForInstructor() {		
		Logger.info("rangeOfGradesForInstructor.getText(): "+rangeOfGradesForInstructor.getText());		
		return rangeOfGradesForInstructor.getText();
	}
	
	public String getRangeForSecondAssignment() {
		Logger.info("rangeOfGradesForSecondAssignment.getText(): "+rangeOfGradesForSecondAssignment.getText());
		return rangeOfGradesForSecondAssignment.getText();
	}

	public String getScorePossible() {
		Logger.info("Getting Possible Score...");
		if (isRangeOfGradesPresent()) {
			return getRange().substring(getRange().indexOf(SEPARATOR) + 1);
		} else
			return null;
	}
	
	public String getScorePossibleForInstructor() {
		Logger.info("Getting Possible Score for Instructor...");
		Element singleView = browser.waitForElement(By.xpath("//table[@id = 'user-grades']/tbody/tr[3]/th[3]/a[2]"));
		singleView.click();
		browser.pause(6000);
		if (isRangeOfGradesPresentForInstructor()) {
			String temp = getRangeForInstructor().substring(getRangeForInstructor().indexOf(SEPARATOR_SHORT) + 2);
			return temp.substring(0, temp.indexOf(DOT));
		} else		
			return null;
	}
	
	public String getScorePossibleForSecondAssignment() {
		Logger.info("Getting Possible Score for Second Assignment...");
		if (isRangeOfGradesPresentForSecondAssignment()) {
			return getRangeForSecondAssignment().substring(getRangeForSecondAssignment().indexOf(SEPARATOR) + 1);
		} else
			return null;
	}

	public String getGrade() {
		return scoreReceived.getText();
	}
	
	public String getGradeForSecondAssignment() {
		return scoreReceivedForSecondAssignment.getText();
	}

	public String getScoreReceived() {
		Logger.info("Getting Received Score...");
		if (isRangeOfGradesPresent()) {
			return getGrade().substring(0, getGrade().indexOf(DOT));
		} else
			return null;
	}
	
	public String getScoreReceivedForSecondAssignment() {
		Logger.info("Getting Received Score for Second Assignment...");
		if (isRangeOfGradesPresentForSecondAssignment()) {
			return getGradeForSecondAssignment().substring(0, getGradeForSecondAssignment().indexOf(DOT));
		} else
			return null;
	}

	public String getFeedback() {
		if (isFeedbackPresent()) {
			return feedback.getText();
		} else
			return null;
	}

	public boolean isCategoryTypePresent() {
		return categoryType.isElementPresent();
	}
	
	public boolean isCategoryTypePresentForInstructor() {
		return categoryTypeForInstructor.isElementPresent();
	}

	public boolean isAssignmentTitlePresent() {
		return assignmentTitle.isElementPresent();
	}
	
	public boolean isAssignmentTitlePresentForSecondAssignment() {
		return assignmentTitleForSecondAssignment.isElementPresent();
	}
	
	public boolean isAssignmentTitlePresentForInstructor() {
		return assignmentTitleForInstructor.isElementPresent();
	}
	
	public boolean isAssignmentTitlePresentForInstructorForSecondAssignment() {
		return assignmentTitleForInstructorForSecondAssignment.isElementPresent();
	}

	public boolean isRangeOfGradesPresent() {
		return rangeOfGrades.isElementPresent();
	}
	
	public boolean isRangeOfGradesPresentForInstructor() {
		return rangeOfGradesForInstructor.isElementPresent();
	}
	
	public boolean isRangeOfGradesPresentForSecondAssignment() {
		return rangeOfGradesForSecondAssignment.isElementPresent();
	}

	public boolean isFeedbackPresent() {
		Logger.info("Getting Feedback...");
		return feedback.isElementPresent();
	}

	public int getCountOfCategoryTypes() {
		Logger.info("Getting Count of Category Types...");
		int res = browser.getElementsCount(By.xpath("//*[@alt = 'Category']")) - 1;
		return res;
	}
	
	public int getCountOfCategoryTypesForInstructor(String categoryType) {
		Logger.info("Getting Count of Category Types for Instructor...");
		int res =  browser.getElementsCount(By.xpath("//th[contains(text(), '" + categoryType + "')]"));
		return res;
	}

	public int getCountOfAssignment() {
		Logger.info("Getting Count of Assignment...");
		int res = browser.getElementsCount(By.xpath("//*[@alt = 'Manual item']"));
		return res;
	}
	
	public int getCountOfAssignmentForInstructor(String title) {
		Logger.info("Getting Count of Assignment for Instructor...");
		int res = browser.getElementsCount(By.xpath("//table[@id = 'user-grades']//span[contains(text(), '" + title + "')]"));
		return res;
	}
}