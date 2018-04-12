package com.mcgraw.test.automation.ui.mhcampus.course.connect;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//a[contains(text(),'My account')] | //a[contains(text(),'Help')] | //a[contains(text(),'Sign out')]")))
public class CanvasConnectCourseDetailsScreen extends Screen {

	@DefinedLocators(@DefinedLocator(using = "//div[@id='headerStripe']//ul/li[1]"))
	Element userFullName;

	@DefinedLocators(@DefinedLocator(using = "//div[@id='header']//h1/span[1]"))
	Element course;

	@DefinedLocators(@DefinedLocator(using = "//div[@id='header']//h1/span[2]"))
	Element section;
	
	@DefinedLocators(@DefinedLocator(using = "//div[@class='textbook-title-tbo-rc-10']/a[2]"))
	Element book;
	
	@DefinedLocators(@DefinedLocator(using = "//a[contains(text(),'Sign out')]"))
	Element logOut;
	
	@DefinedLocators(@DefinedLocator(using = "//a[contains(text(),'Add assignment')]"))
	Element addAssignment;
	
	@DefinedLocators(@DefinedLocator(using = "//a[contains(text(),'Question Bank')]"))
	Element questionBank;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "reviewAssign_Assign"))
	Element assign;
	
	@DefinedLocators(@DefinedLocator(using = "//a[contains(text(),'My courses')]"))
	Element myCoursesButton;
	
	public CanvasConnectCourseDetailsScreen(Browser browser) {
		super(browser);
	}
	
	public String getUserFullNameName() {
		Logger.info("User full name is: " + userFullName.getText());
		return userFullName.getText();
	}
	
	public String getCourseName() {
		Logger.info("Course name is: " + course.getText());
		return course.getText();
	}
	
	public String getSectionName() {
		Logger.info("Section name is: " + section.getText());
		return section.getText();
	}
	
	public String getBookName() {
		Logger.info("Book name is: " + book.getText());
		return book.getText();
	}
	
	public void clickLogout() {
		Logger.info("Clicking Logout button...");
		logOut.click();
	}
	
	public String addAssignment(Boolean startDateInFurther, String assignmentName, String start, String end, String hourMin) {
		Logger.info("Add assignment to course...");
		String rightAnswer = null;
		createAssignment(assignmentName);
		chooseQuestion();
		if(!startDateInFurther)
			rightAnswer = getRightAnswer();
		addQuestion();
		chooseStartAndEndDates(startDateInFurther, start, end, hourMin);
		assign.click(); 
		browser.pause(6000);
		browser.navigate().refresh();
		browser.makeScreenshot();
		return rightAnswer;
	}
	
	public boolean isAssignmentNamePresent(String name) {
		Logger.info("Check assignment name is present...");
		if(browser.isElementPresentWithWait(By.xpath("//span[contains(text(),'" + name + "')]"), 20))
			return true;
		return false;
	}
	
	public int getCountOfEnabledAssignments() {
		Logger.info("Get counter of enabled assignments...");
		int result = browser.getElementsCount(By.cssSelector("a[title='Click to hide this assignment']"));
		return result;
	}	
	
	public int getCountOfDeployedAssignments() {
		Logger.info("Get counter of deployed assignments...");
		return browser.getElementsCount(By.cssSelector("div[class='canvas_shp_deploy_icon']"));
	}	
	
	//added by Maxym Klymenko
	public CanvasConnectCourseConfigScreen goToMyCoursesPage(){
		Logger.info("Clicking on My courses link");
		myCoursesButton.click();

		return browser.waitForPage(CanvasConnectCourseConfigScreen.class, 30);
	}
	
	//-------------------------------------------  private methods -------------------------------
	
	private void createAssignment(String name){
		addAssignment.waitForPresence(20);
		addAssignment.click();
		questionBank.click();
		browser.pause(5000);	
		try{
			Element questionSource = browser.waitForElement(By.xpath(".//*[@id='sourcessection']/li[1]/a"));
			questionSource.click();
		}catch(Exception ex){
			browser.makeScreenshot();
			Element selectSource = browser.waitForElement(By.id("questionpopup"));
			selectSource.click();
			Element questionSource = browser.waitForElement(By.xpath(".//*[@id='sourcessection']/li[1]/a"));
			questionSource.click();
		}
		browser.makeScreenshot();
		Element selectChapter1 = browser.waitForElement(By.xpath("//*[@id='sourcessection']/li[1]//span[contains(text(),'select')]"));
		selectChapter1.jsClick(browser);
		browser.pause(3000);	
		Element assignmentTitle = browser.waitForElement(By.id("title"));
		assignmentTitle.clear();
		assignmentTitle.sendKeys(name);	
		browser.makeScreenshot();
	}
	
	private void chooseQuestion(){
		WebElement question, chooseQuestion;
		String questionType;
		Element tableOfQuestions = browser.waitForElement(By.id("pool_info"));
		List<WebElement> questions = tableOfQuestions.findElements(By.tagName("tr"));
		
		for(int i=0; i<questions.size(); i++){
			question = questions.get(i);
			questionType = question.findElement(By.xpath("//tr["+(i+1)+"]/td[3]")).getText();				
			if(questionType.equals("Multiple Choice")){
				Logger.info("Mark question with type: " + questionType);
				chooseQuestion = question.findElement(By.xpath("//tr["+(i+1)+"]/td[1]/a"));
				chooseQuestion.click();
				break;
			}		
		}	
		browser.pause(5000);
		browser.makeScreenshot();
	}
	
	private String getRightAnswer(){
		browser.switchTo().frame("question_details");
		List<WebElement> answers = browser.findElements(By.xpath("//*[@class='mctfyn__choice']"));
		WebElement answer = answers.get(0).findElement(By.xpath("//*[@class='icon-answer-indicator--correct']"));
		String rightAnswer = answer.toString();		
		browser.switchTo().defaultContent();
		return rightAnswer;
	}
	
	private void addQuestion(){
		Element addQuestion = browser.waitForElement(By.id("addthisQuestion"));
		addQuestion.jsClick(browser);
		browser.pause(5000);
		
		Element add = browser.waitForElement(By.xpath("//a[contains(text(),'add as an individual question')]"));
		add.jsClick(browser);
		browser.pause(9000);
		browser.makeScreenshot();
		
		Element create = browser.waitForElement(By.id("create_Cont"));
		create.jsClick(browser);
		browser.pause(3000);
		browser.makeScreenshot();
	}
	
	private void chooseStartAndEndDates(Boolean onThisDate, String start, String end, String hourMin){
		Element chooseDate, startDate, endDate, startHourMin, dueHourMin, reviewAndAssign;
		if(onThisDate){
			Logger.info("Create start date of assignment...");
			chooseDate = browser.waitForElement(By.id("assignment_availablity"));
			chooseDate.click();
			browser.pause(3000);
			startDate = browser.waitForElement(By.id("scheduleDateStart"), 30);
			startDate.sendKeys(start);
			browser.pause(1000);
			startHourMin = browser.waitForElement(By.id("startHourMin"), 30);
			startHourMin.clear();
			startHourMin.sendKeys(hourMin);
			browser.pause(3000);
			browser.makeScreenshot();
		}
		Logger.info("Create due date of assignment...");
		endDate = browser.waitForElement(By.id("scheduleDateEnd"), 30);
		endDate.sendKeys(end);
		browser.pause(1000);
		dueHourMin = browser.waitForElement(By.id("dueHourMin"), 30);
		dueHourMin.clear();
		dueHourMin.sendKeys(hourMin);
		browser.pause(1000);
		browser.makeScreenshot();
		
		try{
			reviewAndAssign = browser.waitForElement(By.id("reviewAndAssignBtn"));
			reviewAndAssign.click();
		}catch(Exception ex){
			// date
			if(onThisDate){
				Logger.info("Create start date of assignment...");
				browser.pause(3000);
				startDate = browser.waitForElement(By.id("scheduleDateStart"), 30);
				startDate.clear();
				startDate.sendKeys(start);
				browser.pause(3000);
				startHourMin = browser.waitForElement(By.id("startHourMin"), 30);
				startHourMin.clear();
				startHourMin.sendKeys(hourMin);
				browser.pause(3000);
				browser.makeScreenshot();
			}
			Logger.info("Create due date of assignment...");
			endDate = browser.waitForElement(By.id("scheduleDateEnd"), 30);
			endDate.clear();
			endDate.sendKeys(end);
			browser.pause(3000);
			dueHourMin = browser.waitForElement(By.id("dueHourMin"), 30);
			dueHourMin.clear();
			dueHourMin.sendKeys(hourMin);
			browser.pause(3000);
			browser.makeScreenshot();
			
			reviewAndAssign = browser.waitForElement(By.id("reviewAndAssignBtn"));
				reviewAndAssign.click();
			}
		
		browser.makeScreenshot();	
	}
}
