package com.mcgraw.test.automation.ui.gradebook;

import org.openqa.selenium.support.How;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
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
}
