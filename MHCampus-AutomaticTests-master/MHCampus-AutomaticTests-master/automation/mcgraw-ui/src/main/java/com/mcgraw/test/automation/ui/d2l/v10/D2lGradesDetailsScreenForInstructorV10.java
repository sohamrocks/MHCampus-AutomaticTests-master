package com.mcgraw.test.automation.ui.d2l.v10;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.d2l.base.D2lGradesDetailsScreen;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Enter Grades')]")))
public class D2lGradesDetailsScreenForInstructorV10 extends D2lGradesDetailsScreen {

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table [@id='z_bl']//label[contains(text(), 'category')]"))
	Element categoryElement;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table [@id='z_bl']//a[contains(text(), 'title')]"))
	Element assignmentTitleElement;

	public D2lGradesDetailsScreenForInstructorV10(Browser browser) {
		super(browser);
	}

	@Override
	public String getCategory() {
		return categoryElement.getText();

	}

	@Override
	public String getAssignmentTitle() {
		return assignmentTitleElement.getText();
	}

	@Override
	public int getCountOfAssignments() {
		int res = browser.getElementsCount(By.xpath("//table [@id='z_bl']//a[contains(text(), 'title')]"));
		return res;
	}

	@Override
	public int getCountOfCategory() {
		int res =  browser.getElementsCount(By.xpath("//table [@id='z_bl']//label[contains(text(), 'category')]"));
		return res;
	}
	//commented by AleksandrY
//	public String getScorePossible(String title) {
//		Logger.info("Get score possible for assigment title: " + title);
//		String maxPoints = null;
//		Element viewActions = browser.waitForElement(By.id("z_bq"), 10);
//		viewActions.click();
//		Element edit = browser.waitForElement(By.xpath("//span[contains(text(),'Edit')]"), 10);
//		edit.click();
//		Element titleNameInput = browser.waitForElement(By.id("z_h"), 10);
//		String titleName = titleNameInput.getIdentifyingText();
//		if(titleName.equals(title)){
//			Element maxPointsInput = browser.waitForElement(By.id("z_ba"), 10);
//			maxPoints = maxPointsInput.getIdentifyingText();
//		}else{
//			Logger.info("The title doesn't correct, expected: " + title + " , found: " + titleName);
//		}
//		Element cansel = browser.waitForElement(By.id("z_d"), 10);
//		cansel.click();
//
//		return maxPoints;
//	}
	//added by AleksandrY
	public String getScorePossible(String title) {
		Logger.info("Get score possible for assigment title: " + title);
		String maxPoints = null;
		clickEditAssignment(title);
		Element titleNameInput = browser.waitForElement(By.id("z_h"), 10);
		String titleName = titleNameInput.getIdentifyingText();
		if(titleName.equals(title)){
			Element maxPointsInput = browser.waitForElement(By.id("z_ba"), 10);
			maxPoints = maxPointsInput.getIdentifyingText();
		}else{
			Logger.info("The title doesn't correct, expected: " + title + " , found: " + titleName);
		}
		Element cansel = browser.waitForElement(By.id("z_d"), 10);
		cansel.click();

		return maxPoints;
	}

	public String getScoreReceived(String title, String studentName) {
		Logger.info("Get score received for student: " + studentName + " under assigment title: " + title);
		String grade = null;
		Element student = browser.waitForElement(By.xpath("//a[contains(text(),'" + studentName + "')]"), 10);
		student.click();
		Element titleNameInput = browser.waitForElement(By.xpath("//strong[contains(text(),'" + title + "')]"), 10);
		String titleName = titleNameInput.getIdentifyingText();
		if(titleName.equals(title)){	
			Element gradeInput = browser.waitForElement(By.id("z_cq"), 10);
			grade = gradeInput.getIdentifyingText();
		}else{
			Logger.info("The title doesn't correct, expected: " + title + " , found: " + titleName);
		}
		Element cansel = browser.waitForElement(By.id("z_c"), 10);
		cansel.click();
		
		return grade;
	}

	@Override
	protected String getPoints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getCategoryAndAssignmentXpath() {
		// TODO Auto-generated method stub
		return null;
	}

	//added by AleksandrY
	@Override
	public boolean isAssignmentPresent(String assignmentName) {
		return browser.isElementPresentWithWait(
				By.xpath("//table [@id='z_bl']//a[contains(text(), '" + assignmentName + "')]"), 20);
	}

	//added by AleksandrY
	@Override
	public void clickEditAssignment(String assignmentName) {
		Logger.info("Click Edit button for assigment with title: " + assignmentName);
		List<WebElement> assignmentList = browser.waitForElements(By.xpath(".//table//th"), 30);
		for(WebElement elem: assignmentList){
			if(elem.findElements(By.xpath(".//a[contains(text(),'" + assignmentName + "')]")).size() == 1){
				elem.findElement(By.xpath(".//a[contains(@title,'View Actions')]")).click();
				Element edit = browser.waitForElement(By.xpath("//span[contains(text(),'Edit')]"), 10);
				edit.click();
				return;
			}
		}
		Logger.info("Can not click Edit button for assigment with title: " + assignmentName);
		return;
	}

}
