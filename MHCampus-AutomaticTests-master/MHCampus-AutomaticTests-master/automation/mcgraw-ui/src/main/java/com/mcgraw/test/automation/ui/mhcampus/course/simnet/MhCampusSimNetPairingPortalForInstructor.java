package com.mcgraw.test.automation.ui.mhcampus.course.simnet;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.CheckBox;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.RadioButton;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.blackboard.BlackboardCourseDetails;

@PageRelativeUrl(relative = false, value = "simnetonline.com")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//div[contains(@class,'simnet')]")))
public class MhCampusSimNetPairingPortalForInstructor extends Screen {

	private final String What_SIMnet_URL_MESSAGE = "What SIMnet URL would you like to pair the course with?";

	@DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'" + What_SIMnet_URL_MESSAGE + "')]"))
	Element whatSimnetUrlMessage;

	// pair instructor ----------------------------------------------------------------------------
		@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_LoginRadioButtonList_1"))
		RadioButton radioButtonNo;
		
		@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_NewFirstNameTextBox"))
		Input firstNameInput;
		
		@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_NewLastNameTextBox"))
		Input lastNameInput;

		
		@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_NewEmailTextBox"))
		Input emailInput;
		
		@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_NewUsernameTextBox"))
		Input userNameInput;
		
		@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_NewPasswordTextBox"))
		Input passwordInput;
		
		@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_NewConfirmPasswordTextBox"))
		Input passwordConfirmInput;
		
		@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_AgreementCheckBox"))
		CheckBox checkBoxToS;
		
		@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_CreateUserButton"))
		Element createBtn;
		
		@DefinedLocators(@DefinedLocator(how = How.XPATH,using = "//*[contains(text(),'Sign In with your SIMnet credentials')]"))
		Element SimnetLoginMessageIsPresent;
	
		//Added by Yuliya
		@DefinedLocators(@DefinedLocator(how = How.XPATH,using = "//*[@id='returnToBB']"))
		Element retunToBbBtn; 
		
		@DefinedLocators(@DefinedLocator(how = How.XPATH,using = "//span[contains(text(),'Exams')]"))
		Element examsBtn; 
		
		@DefinedLocators(@DefinedLocator(how = How.XPATH,using = "//a[contains(text(),'Create')]"))
		Element createExamsBtn; 
		
		@DefinedLocators(@DefinedLocator(how = How.XPATH,using = "//*[@id='ctl00_ctl00_TopLevelContent_TopLevelContent_TitleBox']"))
		Input titleInput;
		
		@DefinedLocators(@DefinedLocator(how = How.XPATH,using = "//span[contains(text(),'Next')]"))
		Element nextBtn; 
		
		@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[@id='ctl00_ctl00_TopLevelContent_TopLevelContent_Questions_TextbookComboBox_Arrow']"))
		Element openDropDownOfTextbookField;
		
		@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//li[contains(text(),'In Practice - Office 2013 - Nordell')]"))
		Element selectTextbook;
		
		@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[@id='ctl00_ctl00_TopLevelContent_TopLevelContent_Questions_ChapterComboBox_Arrow']"))
		Element openDropDownOfChapterField;
		
		@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//li[contains(text(),'Overview - Chapter 1')]"))
		Element selectChapter; 
		
		@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//tr[@id='ctl00_ctl00_TopLevelContent_TopLevelContent_Questions_LeftRadGrid_ctl00__0']/td[5]"))
		Element selectQuestion; 
		
		@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[text()='Add']"))
		Element addBtn; 
		
		@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[text()='Class Assignments']"))
		Element classAssignmentTab; 
		
		@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@id='ctl00_ctl00_TopLevelContent_TopLevelContent_AssignToClass_LeftRadGrid_ctl00__0']/td[2]"))
		Element selectFirstAvailableClass;
		
		@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@id='DatesEnabled']"))
		CheckBox checkBoxEnableDate; 
		
		@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[text()='Save']"))
		Element saveBtn; 
		
		@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ctl00_TopLevelContent_TopLevelContent_AssignToClass_AssignedClassesNumLabel")) 																																				
		Element countAssignments;
		
		@DefinedLocators(@DefinedLocator(how = How.ID, using = "BBoardConfirmButton")) 																																			
		Element finishDeployBtn;
		
		@DefinedLocators(@DefinedLocator(how = How.ID, using = "BBoardConfirm")) 																																			
		Element confirmBtn;
		
	public MhCampusSimNetPairingPortalForInstructor(Browser browser) {
		super(browser);
	}
		
	public MhCampusSimNetPairCourseScreen pairInstructor(String email,String password){
		Logger.info("Pair instructor ");
	    browser.waitForElementPresent(radioButtonNo).click();
	    browser.waitForElementPresent(emailInput).sendKeys(email);
	    browser.waitForElementPresent(userNameInput).sendKeys(email);
	    browser.waitForElementPresent(passwordInput).sendKeys(password);
	    browser.waitForElementPresent(passwordConfirmInput).sendKeys(password);
	    browser.waitForElementPresent(checkBoxToS).click();
	    browser.waitForElementPresent(createBtn).click();
	    browser.switchTo().defaultContent();  //Edit by lior
		browser.pause(3000);
		browser.switchTo().frame("tool_content");  //Edit by lior
		return browser.waitForPage(MhCampusSimNetPairCourseScreen.class);
		
		
	}
	public boolean simnetLoginMessageIsPresent() {
		Logger.info("Checking if SIMnet login message for is shown");
		browser.pause(5000);
		boolean elementPresent = SimnetLoginMessageIsPresent.isElementPresent();
		return elementPresent;
	}
	
	//Added by Yuliya
	public MhCampusSimNetPairCourseScreen pairInstructorBB(String firstName,String lastName, String email,String password){
		Logger.info("Pair instructor ");
	    browser.waitForElementPresent(radioButtonNo).click();
	    browser.waitForElementPresent(firstNameInput).clear();
	    browser.waitForElementPresent(firstNameInput).sendKeys(firstName);
	    browser.waitForElementPresent(lastNameInput).clear();
	    browser.waitForElementPresent(lastNameInput).sendKeys(lastName);
	    browser.waitForElementPresent(emailInput).clear();
	    browser.waitForElementPresent(emailInput).sendKeys(email);
	    try{browser.waitForElementPresent(userNameInput,10).sendKeys(email);}catch(Exception e){}
	    browser.waitForElementPresent(passwordInput).sendKeys(password);
	    browser.waitForElementPresent(passwordConfirmInput).sendKeys(password);
	    browser.waitForElementPresent(checkBoxToS).click();
	    browser.waitForElementPresent(createBtn).click();
		return browser.waitForPage(MhCampusSimNetPairCourseScreen.class);
		}
	

	public BlackboardCourseDetails retunToBB() { //Added by Yuliya
		Logger.info("Retun to LMS Blackboard...");
		retunToBbBtn.click();
		BlackboardCourseDetails blackboardCourseDetails=browser.waitForPage(BlackboardCourseDetails.class);
		browser.makeScreenshot();
		return blackboardCourseDetails;
	}
	
	public void clickExamsLink() { 
		Logger.info("Cklicking Exams link...");
		browser.pause(3000);
		examsBtn.click();
		browser.makeScreenshot();
	}
	
	public void createAnAssignment(String title) {
		Logger.info("Creating an assignment...");
		browser.pause(3000);
		createExamsBtn.click();
		browser.makeScreenshot();
		titleInput.sendKeys(title);
		nextBtn.click();
		browser.pause(3000);
		browser.makeScreenshot();
		openDropDownOfTextbookField.click();
		selectTextbook.click();
		browser.pause(3000);
		openDropDownOfChapterField.click();
		selectChapter.click();
		browser.pause(3000);
		selectQuestion.click();
		addBtn.click();
		browser.pause(3000);
		browser.makeScreenshot();
		classAssignmentTab.click();
		browser.pause(3000);
		browser.makeScreenshot();
		selectFirstAvailableClass.click();
		addBtn.click();
		browser.switchTo().frame(0);
		browser.waitForElement(By.xpath("//div[contains(text(),'Scheduling')]"));
		checkBoxEnableDate.click();
		saveBtn.click();
		browser.switchTo().defaultContent();
	}
	
	
	public String getCountOfAssignmentClasses() { 
		Logger.info("Checking that an available class is now assigned...");
		browser.pause(6000);
		String countOfAssignmentClasses = countAssignments.getText();
		return countOfAssignmentClasses;
	}
	
	public void dragAndDropAssignment() { 
		Logger.info("Drag and drop an assignment...");
		Element draggable = browser.findElement(By.xpath("//*[@id='simnetTree']/ul/li/ul/li/div/span[2]")); 
		Element target = browser.findElement(By.xpath("//*[@id='lmsTree']/ul/li/div/span[2]")); 
		new Actions(browser).dragAndDrop(draggable, target).perform();
	}
	
	public BlackboardCourseDetails clickToFinishDeployBtn() { 
		Logger.info("Clicking to finish deploy button...");
		browser.pause(6000);
		browser.waitForElement(By.id("BBoardConfirmButton"));
		finishDeployBtn.click();
		browser.pause(3000);
		confirmBtn.click();
		BlackboardCourseDetails blackboardCourseDetails=browser.waitForPage(BlackboardCourseDetails.class);
		return blackboardCourseDetails;
	}
}
