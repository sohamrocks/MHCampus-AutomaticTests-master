package com.mcgraw.test.automation.ui.mhcampus.course.connect;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl(relative = false, value = "newconnectqastg.mheducation.com")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = ".//*")))
public class CanvasConnectStudentCourseDetailsScreen extends Screen {

	// New fix - fix the xpath identifier
	@DefinedLocators(@DefinedLocator(using = "//*[@class='header-text medium-text course-text pull-left']"))
	Element courseNameElement;

	// Previous version - Marked by Or
	// @DefinedLocators(@DefinedLocator(using = "//*[@class='medium-text
	// font-semibold']"))
	// Element courseNameElement;

	// New fix - fix the xpath identifier
	@DefinedLocators(@DefinedLocator(using = "//*[@class='instructor-name medium-text font-bold']"))
	Element instructorNameElement;

	// Previous version - Marked by Or
	// @DefinedLocators(@DefinedLocator(using = "//*[@class='instructor-name
	// x-large-text font-semibold']"))
	// Element instructorNameElement;

	@DefinedLocators(@DefinedLocator(using = "//*[@class='icon-menu_hamburger']"))
	Element goToMenu;

	@DefinedLocators(@DefinedLocator(using = "//*[@id='menu-userinfo']/div"))
	Element studentNameElement;

	// Added by Or
	@DefinedLocators(@DefinedLocator(using = "//*[@class='course-detail-wrapper']/main/ul/li[1]/div/div/div/div/div/h3"))
	Element assignment1;

	// //Marked by Or
	// @DefinedLocators(@DefinedLocator(using = "//section[@class='right
	// course-list-container']/li[1]/div[1]/div/div/div[1]/div[1]/span"))
	// Element assignment1;

	// Added by Or
	@DefinedLocators(@DefinedLocator(using = "//*[@class='course-detail-wrapper']/main/ul/li[2]/div/div/div/div/div/h3"))
	Element assignment2;

	// //Marked by Or
	// @DefinedLocators(@DefinedLocator(using = "//section[@class='right
	// course-list-container']/li[2]/div[1]/div/div/div[1]/div[1]/span"))
	// Element assignment2;

	public CanvasConnectStudentCourseDetailsScreen(Browser browser) {
		super(browser);
	}

	public boolean isCoursePresentInConnect(String courseName) {
		Logger.info("Check course " + courseName + " present in Connect under student");
		browser.switchTo().defaultContent();
		browser.switchTo().frame(0);
		browser.switchTo().frame(0);
		courseNameElement.waitForPresence(20);
		if (courseNameElement.getText().contains(courseName.toUpperCase()))
			return true;
		return false;
	}

	public boolean isInstructorNamePresentInConnect(String instructorName) {
		Logger.info("Check instructor " + instructorName + " present in Connect under student");
		browser.switchTo().defaultContent();
		browser.switchTo().frame(0);
		browser.switchTo().frame(0);
		if (instructorNameElement.getText().contains(instructorName))
			return true;
		return false;
	}

	public boolean isAssignmentPresentInConnect(String assignmentName) {
		Logger.info("Check assignment " + assignmentName + " present in Connect under student");
		browser.switchTo().defaultContent();
		browser.switchTo().frame(0);
		browser.switchTo().frame(0);
		if (assignment1.getText().contains(assignmentName) || assignment2.getText().contains(assignmentName))
			return true;
		return false;
	}

	public boolean isAssignmentNamePresent(String assignmentName) {

		Logger.info("Check assignment " + assignmentName + " present in Connect under student");
		browser.switchTo().defaultContent();
		browser.pause(2000);
		browser.switchTo().frame(0);
		browser.pause(6000);
		browser.switchTo().frame(1);
		try {
			Element assignmentTitle = browser.waitForElement(By.xpath("//*[@id='assignment-info']/h1"));
			System.out.println(assignmentTitle);
			if (assignmentTitle.getText().contains(assignmentName)) {
				return true;
			}
		} catch (Exception e) {
			Logger.info("Assignment " + assignmentName + " doesn't present...");
		}
		return false;

		// Logger.info("Check assignment " + assignmentName + " present in
		// Connect under student");
		// browser.switchTo().defaultContent();
		// browser.pause(2000);
		// browser.switchTo().frame(0);
		// browser.pause(6000);
		// Element iframe = browser.findElement(By.cssSelector("#disable_menu >
		// div:nth-child(10) > iframe"));
		// browser.switchTo().frame(iframe);
		//
		// try{
		// browser.waitForElement(By.xpath("//*[contains(text(), '" +
		// assignmentName + "')]"), 20);
		// return true;
		// }catch(Exception e){
		// Logger.info("Assignment " + assignmentName + " doesn't present...");
		// }
		// return false;
	}

	public boolean isStudentNamePresentInConnect(String studentName) {

		Logger.info("Check assignment " + studentName + " present in Connect under student");
		browser.switchTo().defaultContent();
		browser.switchTo().frame(0);
		browser.switchTo().frame(0);
		goToMenu.click();
		browser.pause(1000);
		browser.makeScreenshot();
		if (studentNameElement.getText().contains(studentName))
			return true;
		return false;
	}

	public String answerTheQuestionAndGetGrade(String studentAnswer) {

		Logger.info("Answer the question and get grade...");
		answerTheQuestion(studentAnswer);
		Element result = (browser.findElement(By.cssSelector("p[class='question__points']")));
		String studentGrage = result.getText().substring(0, 2);
		Logger.info("Student grage is: " + studentGrage);

		return studentGrage;
	}

	// ------------------------------------ Private methods
	// ---------------------------------------------

	private void answerTheQuestion(String studentAnswer) {

		browser.switchTo().defaultContent();
		browser.switchTo().frame(0);
		Element iframe = browser.findElement(By.cssSelector("#disable_menu > div:nth-child(10) > iframe"));
		browser.switchTo().frame(iframe);
		browser.pause(2000);
		Element go = browser.waitForElement(By.cssSelector("button.intro__footer__start"));
		go.click();
		browser.pause(3000);
		browser.makeScreenshot();

		// Switching to the second iFrame
		browser.switchTo().defaultContent();
		browser.switchTo().frame(0);
		Element iframe2 = browser
				.findElement(By.cssSelector("#disable_menu > div:nth-child(10) > iframe:nth-child(1)"));
		browser.switchTo().frame(iframe2);

		Element answersContainer = browser.waitForElement(By.xpath("//div[@class='ember-view']/form/ul"));
		Logger.info("//div[@class='ember-view']/form/ul was found");
		List<WebElement> answers = answersContainer.findElements(By.tagName("li"));
		for (WebElement answer : answers) {
			String text = answer.findElement(By.xpath("div/div/p")).getText();
			if (text.equals(studentAnswer)) {
				Logger.info("Student choose answer: " + text);
				answer.click();
				browser.makeScreenshot();
				break;
			}
		}

		Logger.info("Click Submit button...");
		// Element submit = browser.waitForElement(By.xpath("//*[@class =
		// 'ember-view ic-modal-trigger header__exit header__exit--submit']"));
		// Added By Or
		Element submit = browser.waitForElement(
				By.xpath("//button[@class='header__exit header__exit--submit ember-view ic-modal-trigger']"));
		submit.click();
		browser.makeScreenshot();
		// submit = browser.waitForElement(By.xpath("//*[@class = 'ember-view
		// ic-modal-trigger modal--submit-activity__button--confirm']"));
		// Added By Or
		submit = browser.waitForElement(
				By.xpath("//button[@class='modal--submit-activity__button--confirm ember-view ic-modal-trigger']"));
		submit.click();
		browser.makeScreenshot();
		Element success = browser.waitForElement(By.id("successful-submission"));
		success.click();
		browser.pause(8000);
		browser.makeScreenshot();
	}
}
