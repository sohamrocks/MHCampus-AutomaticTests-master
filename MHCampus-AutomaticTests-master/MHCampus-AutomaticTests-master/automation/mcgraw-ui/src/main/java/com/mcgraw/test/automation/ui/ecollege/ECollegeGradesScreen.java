package com.mcgraw.test.automation.ui.ecollege;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;


@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@class = 'pageTitle'][contains(.,'Gradebook')]")))
public class ECollegeGradesScreen extends ECollegeCourseGeneralScreen{
	
	private static String SEPARATOR = "/";
	
	public ECollegeGradesScreen(Browser browser) {
		super(browser);
	}
	
	private int getNumberOfRawContainsTitle(String assignmentTitle) {
		List<WebElement> courseTitles = browser.findElements(By.id("stuUnitViewItemCap-0"));  
		int i = 0;
		for (WebElement title : courseTitles) {
			if (title.getText().equals(assignmentTitle)) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public boolean isAssignmentPresent(String assignmentTitle){
		Logger.info("Checking if assignment title is present...");
		return (getNumberOfRawContainsTitle(assignmentTitle) >= 0);
	}
	
	public String getScoreReceivedByAssignment(String assignmentTitle){
		Logger.info("Getting received scores...");
		String scoreRange = "";
		int plase = getNumberOfRawContainsTitle(assignmentTitle);
		if(plase >= 0){
			List<WebElement> receivedScores = browser.findElements(By.id("spNumericGrade-0"));  
			WebElement receivedScore = receivedScores.get(plase);
			scoreRange = receivedScore.getText();
		}	
		return scoreRange;
	}
	
	public String getScorePossibleByAssignment(String assignmentTitle){
		Logger.info("Getting possible scores...");
		String scoreRange = "";
		int plase = getNumberOfRawContainsTitle(assignmentTitle);
		if(plase >= 0){
			List<WebElement> possibleScores = browser.findElements(By.id("spPointPossible-0"));  
			WebElement possibleScore = possibleScores.get(plase);
			scoreRange = possibleScore.getText();
			return scoreRange.substring(scoreRange.indexOf(SEPARATOR) + 2);
		}	
		return scoreRange;
	}
	
	public ECollegeAssignmentDetailsScreen clickOnGrades(String assignmentTitle) {
		Logger.info("Clicking on grades...");
		int plase = getNumberOfRawContainsTitle(assignmentTitle);
		if (plase >= 0) {			
			List<WebElement> receivedScores = browser.findElements(By.id("spNumericGrade-0"));  
			WebElement receivedScore = receivedScores.get(plase);
			receivedScore.click();
			browser.switchTo().defaultContent();
			browser.switchToLastWindow();
			ECollegeAssignmentDetailsScreen eCollegeAssignmentDetailsScreen = browser.waitForPage(ECollegeAssignmentDetailsScreen.class, 10);
			browser.makeScreenshot();
			return eCollegeAssignmentDetailsScreen;
		} else 
			return null;  
	}
}
