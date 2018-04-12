package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.Block;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
@Block(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "topHeader")))
public class IWantThisForMyStudentsBlock extends BlockElement {
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@id='topHeader']/div/div[1]"))
	Element iWantThisForMyStudentsButton;
	
	@DefinedLocators({@DefinedLocator(how = How.ID, using = "topHeaderText", priority = 1), @DefinedLocator(how = How.CLASS_NAME, using = "floatL", priority = 2)})
	Element youAreCurrentlyInMessage;

	public IWantThisForMyStudentsBlock(WebElement webElement) {
		super(webElement);
	}
	
	
	public String getYouAreCurrentlyInMessage(){
		return youAreCurrentlyInMessage.getText();
	}
	
	public void clickIWantThisForMyStudentsButton(){
		Logger.info("Click iWantThisForMyStudentsButton");
		try{
			iWantThisForMyStudentsButton.waitForPresence();
			iWantThisForMyStudentsButton.click();
		}catch(Exception e){
			Logger.info("Failed to click 'I Want This For My Students' button, trying again...");
			iWantThisForMyStudentsButton.waitForPresence();
			iWantThisForMyStudentsButton.click();
		}
	}

}
