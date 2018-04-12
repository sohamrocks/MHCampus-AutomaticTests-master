package com.mcgraw.test.automation.ui.gradebook;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.timing.WaitForConditionUtils;
import com.mcgraw.test.automation.framework.core.timing.WaitForConditionUtils.TestCondition;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.CheckBox;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;


@PageRelativeUrl("/")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = ".//*[contains(text(),'User ID')]")))
public class TestScoreScreen extends TestScoreBase {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtUserID"))
	Input userIdInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtComment"))
	Input commentInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtCurrentAttempt"))
	Input currentAttemptInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtDatetimeSubmitted"))
	Input dateSubmittedInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtScoreReceived"))
	Input scoreReceivedInput;
	
	//Added by Yuval for Lti 1.1 test
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtSourceDid"))
	Input sourcedIdInput;
	
	//Added by Yuval for Lti 1.1 test
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtOutcomeUrl"))
	Input outcomeUrlInput;
	
	//Added by Yuval for Lti 1.1 test
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "btnSubmitOutcome"))
	Input submitViaOutcome;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtToolID"))
	Input toolIdInput;	

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "cbAsync"))
	CheckBox asyncCb;	
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtResult"))
	Input result;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "btnGetAsyncResults"))
	Input getAsyncResultsBtn;		
	
	public TestScoreScreen(Browser browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	public void typeUserId(String userId) {
		userIdInput.typeValue(userId);
	}

	public void typeComment(String comment) {
		commentInput.typeValue(comment);
	}

	public void typeCurrentAttempt(String currentAttempt) {
		currentAttemptInput.typeValue(currentAttempt);
	}

	public void typeDateSubmitted(String dateSubmitted) {
		dateSubmittedInput.typeValue(dateSubmitted);
	}

	public void typeScoreReceived(String scoreReceived) {
		scoreReceivedInput.typeValue(scoreReceived);
	}
	
	public void typeToolID(String toolId) {
		toolIdInput.typeValue(toolId);		
	}
	
	public void checkAsync() {
		asyncCb.setChecked(true);		
	}
	
	public String getResult() {
		return result.getAttribute("value");		
	}			
	
	public void getAsyncResults() {
		getAsyncResultsBtn.click();		
	}			
	
	public boolean isResultContainsRequest()
	{
		String result="";
		result = getResult();
		return result.contains("Req");
	}			
	
	public boolean isOutcomeUrlCorrect (){
		String actualOutcomeUrl = getOutcomeUrl();
		String expectedOutcomeUrl = "";
		if (isOutcomeUrlEqual(expectedOutcomeUrl, actualOutcomeUrl)){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean isOutcomeUrlCorrect (String customerId){
		String actualOutcomeUrl = getOutcomeUrl();
		String expectedOutcomeUrl = "https://login-aws-qa.mhcampus.com/LTIHandlers/LTIOutcome.ashx?customerId=" + customerId;
		return isOutcomeUrlEqual(expectedOutcomeUrl, actualOutcomeUrl);
	}
	
	public boolean isSourcedIdCorrect (String courseId, String userId, String resourceId){
		browser.makeScreenshot();
		String expectedsourceId = courseId + "::" + userId + "::" + resourceId;
		String actualSourcedId = getSourcedId();
		return isSourcedIdEqual(expectedsourceId, actualSourcedId);
		}
	
	public boolean isSourcedIdCorrect (){
		browser.makeScreenshot();
		String expectedsourceId = "";
		String actualSourcedId = getSourcedId();
		return isSourcedIdEqual(expectedsourceId, actualSourcedId);
		}
	
	public void submitViaOutcome(String score) throws Exception{
		scoreReceivedInput.clear();
		scoreReceivedInput.sendKeys(score);
		submitViaOutcome.click();
		waitForResultToAppear();
		browser.pause(3000);
		browser.makeScreenshot();
	}
	
	
	
	public String getSourcedId(){
		
		return sourcedIdInput.getAttribute("value");
	}
	
	private String  getOutcomeUrl(){
		//browser.switchToLastWindow();
		//browser.manage().window().maximize();
		browser.makeScreenshot();
		if (outcomeUrlInput.getAttribute("value")!= null){
			return outcomeUrlInput.getAttribute("value");
		}
		else{
			return "";
		}
		//return outcomeUrlInput.getAttribute("value");
	}
	
	private class ResultAppear implements TestCondition {
		public Boolean condition() throws Exception {
			return (resultInput.getAttribute("value").length() > 0);
		}
	}
	
	private void waitForResultToAppear() throws Exception {
		WaitForConditionUtils.WaitForCondition(new ResultAppear(), 20000,
				"No results apeear in 20 seconds!");
		Logger.info("Value of Result field is: " + getSubmitResult());
	}
	private boolean isOutcomeUrlEqual(String expected , String actual){
		if ((expected).equals(actual)){
			return true;
		}
		else {
			return false;
		}
	}
	private boolean isSourcedIdEqual(String expected , String actual){
		if (actual.equals(expected)){
			return true;
		}
		else
		{
			return false;
		}
	}

	
}
