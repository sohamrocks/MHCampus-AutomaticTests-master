package com.mcgraw.test.automation.ui.angel;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageFrameIdentificator(locators = @DefinedLocators({ @DefinedLocator(how = How.ID, using = "contentWin"),
		@DefinedLocator(how = How.XPATH, using = "//*[@id = 'defaultReportPanel']//iframe") }))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id = 'UserGrades1_panelOverall']")))
public class AngelGradesDetailsScreen extends Screen {

	@DefinedLocators(@DefinedLocator(using = "(//*[@class = 'row']//*[@class = 'gridcolumn']//span)[1]"))
	Element assignmentAndScoreElement;

	@DefinedLocators(@DefinedLocator(using = "(//*[@class = 'row']//*[@class = 'gridcolumn']//span)[3]"))
	Element scoreReceivedElement;

	private By assignmentTitleXpath = By.xpath("//span[starts-with(@title,'title')]");

	private static String bracket = "(";
	private static String pointsAbbreviation = "pts";
	private static String pointsAbbreviationWithDot = "pts.";

	public AngelGradesDetailsScreen(Browser browser) {
		super(browser);
	}

	// private void jumpToGradeFrame()
	// {
	// browser.switchTo().frame("contentWin");
	// browser.switchTo().frame(browser.findElement(By.xpath("//*[@id = 'defaultReportPanel']//iframe")));
	// }

	public int getCountOfAssignment() {
		// jumpToGradeFrame();
		int countOfAssignments = browser.getElementsCount(assignmentTitleXpath);
		// browser.switchTo().defaultContent();
		return countOfAssignments;
	}

	public String getAssignmentTitle() {
		if (getCountOfAssignment() == 1) {
			// jumpToGradeFrame();
			String assignmentAndScore = assignmentAndScoreElement.getText();
			String assignmentTitle = assignmentAndScore.substring(0, assignmentAndScore.indexOf(bracket) - 1);
			// browser.switchTo().defaultContent();
			return assignmentTitle;
		} else {
			// browser.switchTo().defaultContent();
			return null;
		}
	}

	public String getScorePossible() {
		if (getCountOfAssignment() == 1) {
			// jumpToGradeFrame();
			String assignmentAndScore = assignmentAndScoreElement.getText();
			String scorePossible = assignmentAndScore.substring(assignmentAndScore.indexOf(bracket) + 1,
					assignmentAndScore.indexOf(pointsAbbreviationWithDot) - 1);
			// browser.switchTo().defaultContent();
			return scorePossible;
		} else {
			// browser.switchTo().defaultContent();
			return null;
		}
	}

	public String getScoreReceived() {
		if (getCountOfAssignment() == 1) {
			// jumpToGradeFrame();
			String scoreReceivedText = scoreReceivedElement.getText();
			String scoreReceived = scoreReceivedText.substring(0, scoreReceivedText.indexOf(pointsAbbreviation));
			// browser.switchTo().defaultContent();
			return scoreReceived;
		} else {
			// browser.switchTo().defaultContent();
			return null;
		}
	}

}
