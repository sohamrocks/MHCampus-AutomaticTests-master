package com.mcgraw.test.automation.ui.mhcampus.course.connect;

import org.openqa.selenium.By;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl(relative = false, value = "lmsqastg.mheducation.com")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//div[@id='mainContent']")))
public class D2lConnectCreateAccountScreen extends Screen {

	@DefinedLocators(@DefinedLocator(using = "//a[@class='btntwo closeModal']//b[contains(.,'continue')]"))
	Element continueButton;
	
	@DefinedLocators(@DefinedLocator(using = "//a[contains(text(),'Register for a Connect account')]"))
	Element register;
	
	@DefinedLocators(@DefinedLocator(using = ".//*[@id='bodycontent']/div/section/div/span"))
	Element errorMsg;
	
	public D2lConnectCreateAccountScreen(Browser browser) {
		super(browser);
	}

	public String getErrorMessageText() {
		return errorMsg.getText();
	}
	public void createConnectAccount(String user) {
		Logger.info("Create 'Connect' account for user: " + user);
		continueButton.click();	
		browser.pause(3000);
		browser.makeScreenshot();
		register.click();
		fillForm(user);
	}
	
	public D2lConnectScreen createCourceInConnect(String course, String section, BookForConnect book){	
		Logger.info("Create cource in 'Connect' with name: " + course + " under section: " + section);	
		cooseTheBook(book);
		D2lConnectScreen d2lConnectScreen = createCourse(course, section);
		browser.makeScreenshot();		
		return d2lConnectScreen;
	}

	//------------------------------------------- private methods --------------------------------------------------
	
	private void cooseTheBook(BookForConnect book){
		Element selectSubject = browser.waitForElement(By.xpath(".//*[@class='row-width-9 no-sidepadder mgh-searchbox-row']/input"));
		selectSubject.clear();
		selectSubject.sendKeys(book.getBookId());
		Element searchButton =  browser.findElement(By.xpath(".//div[@class='row-width-3 mgh-searchbox-row']/button"));
		searchButton.click();
		//selectSubject.sendKeys(book.getCategory());
		//Element categoryLink = browser.waitForElement(By.xpath("//strong[contains(text(),'" + book.getCategory() + "')]"));
		//categoryLink.click();
		
		//added by Yuval 18.1.2017
		browser.pause(4000);
		Element bookLink = browser.waitForElement(By.xpath(".//*[@class='row-width-8']"));
		bookLink.click();
		browser.makeScreenshot();
		
		//Element searchBox = browser.waitForElement(By.id("searchBox"));
		//searchBox.clear();
		//searchBox.sendKeys(book.getBookId());
		//Element eBook = browser.waitForElement(By.xpath("//a[@class='ui-corner-all']/div[contains(text(),'" + book.getBookName() + "')]"));
		//eBook.click();
		browser.pause(30000);
		browser.makeScreenshot();
		try{
			Element checkBox = browser.waitForElement(By.xpath(".//button[@type='button' and @value='connect,learnsmart,labsmart,plp,prep,prep,powerofprocess']"), 30);
			checkBox.click();
		}catch (Exception e){
			browser.makeScreenshot();
			Element tryAgain = browser.waitForElement(By.xpath("//a[contains(text(),'Try loading again')]"));
			tryAgain.click();
			browser.pause(30000);
			browser.makeScreenshot();
			Element checkBox = browser.waitForElement(By.xpath("//div[@class='module-list-container']/ul/li[1]/span"));
			checkBox.click();
		}
		//Element next = browser.waitForElement(By.id("next-button"));
		//next.click();
		browser.makeScreenshot();
	}
	
	private D2lConnectScreen createCourse(String course, String section){
		Logger.info("Create cource in 'Connect' with name: " + course + " under section: " + section);	
		Element courseName = browser.waitForElement(By.id("courseNameValue"));
		courseName.clear();
		courseName.sendKeys(course);
		
		Element sectionName = browser.waitForElement(By.id("sectionName"));
		sectionName.clear();
		sectionName.sendKeys(section);
		
		String timeZone = browser.waitForElement(By.xpath("//*[@id='selectedTimezone']/span[1]")).getText();
		if(! timeZone.equals("US/Eastern")){
			Logger.info("The time zone doesn't correct, expected: 'US/Eastern', but found: " + timeZone);
		}
		
		browser.makeScreenshot();
		Element createCourse = browser.waitForElement(By.id("create-button"));
		createCourse.click();
		return browser.waitForPage(D2lConnectScreen.class);
	}
	
	private void fillForm(String user){
		Logger.info("Filling the form for creating account...");
		String qaPassword = "QaPassword1";
		browser.makeScreenshot();
		Element firstName = browser.waitForElement(By.id("new_user_first_name_e"));
		firstName.clear();
		firstName.sendKeys(user);
		Element lastName = browser.waitForElement(By.id("new_user_last_name_e"));
		lastName.clear();
		lastName.sendKeys(user);
		Element email = browser.waitForElement(By.id("new_user_email_e"));
		email.clear();
		email.sendKeys(user + "@gmail.com");
		Element password = browser.waitForElement(By.id("new_user_password_e"));
		password.clear();
		password.sendKeys(qaPassword);
		Element confirmPassword = browser.waitForElement(By.id("new_user_password_retype_e"));
		confirmPassword.clear();
		confirmPassword.sendKeys(qaPassword);
		
		Element selectQuestion = browser.waitForElement(By.id("new_user_last_name_e"));
		selectQuestion.click();
		Element securityQuestion = browser.waitForElement(By.xpath("//*[@id='SecurityQuestions']/option[2]"));
		securityQuestion.click();
		Element securityAnswer = browser.waitForElement(By.id("new_user_sec_ans_e"));
		securityAnswer.clear();
		securityAnswer.sendKeys("Test");
		
		Element iAccept = browser.waitForElement(By.id("chk_agree_terms_e"));
		iAccept.click();
		browser.makeScreenshot();
		Element finishButton = browser.waitForElement(By.xpath("//span[contains(text(),'Finish')]"));
		finishButton.click();
		browser.pause(6000);
		browser.makeScreenshot();
	}
}
