package com.mcgraw.test.automation.ui.moodle;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Grade administration')]")))
public class MoodleGradesScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@class = 'boxaligncenter generaltable user-grade']//tbody//tr[1]//td[2]"))
	Element courseName;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@class = 'boxaligncenter generaltable user-grade']//tbody//tr[3]//td[1]"))
	Element assignmentTitle;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@class = 'boxaligncenter generaltable user-grade']//tbody//tr[3]//td[2]"))
	Element scoreReceived;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@class = 'boxaligncenter generaltable user-grade']//tbody//tr[3]//td[3]"))
	Element rangeOfGrades;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@class = 'boxaligncenter generaltable user-grade']//tbody//tr[3]//td[5]//div"))
	Element feedback;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[@class = 'boxaligncenter generaltable user-grade']//tbody//tr[2]//td[2]"))
	Element categoryType;

	private static String SEPARATOR = "–";
	private static String DOT = ".";

	public MoodleGradesScreen(Browser browser) {
		super(browser);
	}

	public String getCourseName() {
		return courseName.getText();
	}

	public String getCategoryType() {
		if (isCategoryTypePresent()) {
			return categoryType.getText();
		} else
			return null;
	}

	public String getAssignmentTitle() {
		if (isAssignmentTitlePresent()) {
			return assignmentTitle.getText();
		} else
			return null;
	}

	public String getRange() {
		return rangeOfGrades.getText();
	}

	public String getScorePossible() {
		if (isRangeOfGradesPresent()) {
			return getRange().substring(getRange().indexOf(SEPARATOR) + 1);
		} else
			return null;
	}

	public String getGrade() {
		return scoreReceived.getText();
	}

	public String getScoreReceived() {
		if (isRangeOfGradesPresent()) {
			return getGrade().substring(0, getGrade().indexOf(DOT));
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

	public boolean isAssignmentTitlePresent() {
		return assignmentTitle.isElementPresent();
	}

	public boolean isRangeOfGradesPresent() {
		return rangeOfGrades.isElementPresent();
	}

	public boolean isFeedbackPresent() {
		return feedback.isElementPresent();
	}

	public int getCountOfCategoryTypes() {
		return browser.getElementsCount(By.xpath("//*[@alt = 'Category']")) - 1;
	}

	public ArrayList<HashMap<String, String>> getAssignments() {
		ArrayList<HashMap<String, String>> gradeInfoArray = new ArrayList<HashMap<String, String>>();

		int countOfAssignments = browser.getElementsCount(By
				.xpath("(//*[1][@alt = 'Quiz'])"));

		for (int i = 1; i <= countOfAssignments; ++i) {
			Element assignment = browser.findElement(By
					.xpath("(//*[@alt = 'Quiz'])[" + i + "]"));
			Element scoreReceived = browser.findElement(By
					.xpath("//*[@alt = 'Quiz'])[" + i + "]//..//..//td[2]"));
			Element scorePossible = browser.findElement(By
					.xpath("(//*[@alt = 'Quiz'])" + "[" + i + "]"));
			Element comment = browser.findElement(By
					.xpath("(//*[@alt = 'Quiz'])" + "[" + i + "]"));
			HashMap<String, String> infoMap = new HashMap<String, String>();
			infoMap.put("assignmentTitle", assignment.getText());
			infoMap.put("scoreReceived", scoreReceived.getText());
			infoMap.put("scoreReceived", scoreReceived.getText());
			infoMap.put("scorePossible", scorePossible.getText());
			infoMap.put("comment", comment.getText());
			gradeInfoArray.add(infoMap);
		}
		return gradeInfoArray;
	}

	public int getCountOfAssignment() {
		return browser.getElementsCount(By.xpath("//*[@alt = 'Quiz']"));
	}
}
