package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import org.openqa.selenium.WebElement;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;

public class Select implements ISelectWrapper {

	private org.openqa.selenium.support.ui.Select selector;
	
	public Select(WebElement webElement) {
		selector = new org.openqa.selenium.support.ui.Select(webElement);
	}

	@Override
	public org.openqa.selenium.support.ui.Select getWrappedSelector() {
		return selector;
	}

	public void selectOptionByIndex(int index){
		Logger.debug("Selecting option by index: " + index);
		selector.selectByIndex(index);
	}
	
	public void selectOptionByName(String value){
		Logger.debug("Selecting option by value: " + value);
		selector.selectByVisibleText(value);
	}

	public int getOptionsCount(){
		Logger.debug("Fetching options count");
		return selector.getOptions().size();
	}

}
