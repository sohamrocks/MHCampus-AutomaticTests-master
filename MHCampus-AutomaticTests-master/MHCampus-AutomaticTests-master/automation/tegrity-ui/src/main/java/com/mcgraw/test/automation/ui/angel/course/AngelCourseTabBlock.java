package com.mcgraw.test.automation.ui.angel.course;

import org.openqa.selenium.WebElement;

import com.mcgraw.test.automation.framework.selenium2.ui.elements.BlockElement;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.Block;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@Block(locators = @DefinedLocators(@DefinedLocator(using = "//ul[@id='tabMenu']/li")))
public class AngelCourseTabBlock extends BlockElement {

	@DefinedLocators(@DefinedLocator(using = ".//span[@class='center']"))
	private Element tabName;

	@DefinedLocators(@DefinedLocator(using = "/a[@class='active']"))
	private Element active;

	public AngelCourseTabBlock(WebElement webElement) {
		super(webElement);
	}

	public String getName() {
		return tabName.getText();
	}

	public boolean isActive() {
		return active.waitForPresence(10);
	}
}
