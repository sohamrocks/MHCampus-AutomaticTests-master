package com.mcgraw.test.automation.ui.d2l.v10;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.d2l.base.D2lContentCourseScreen;

@PageRelativeUrl("")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//div[contains(text(),'Table of Contents')]")))
public class D2lContentCourseScreenV10 extends D2lContentCourseScreen {

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "(//*[starts-with(@title, 'Actions for')])[1]"))
	Element editModule;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'Tegrity Campus')]"))
	protected Element tegrityLinkElement;
	
	private static By tegrityLink = By.xpath("//a[contains(text(),'Tegrity Campus')]");

	public D2lContentCourseScreenV10(Browser browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void chooseModuleNavigations() {
		editModule.click();
	}

	@Override
	protected void selectToolByName(String toolName) {
		Element linkButton = browser.waitForElement(By
				.xpath("//div[contains(text(),'" + toolName + "')]"));
		linkButton.click();
	}

	@Override
	protected void waitForLinkAppear(String toolName) {
		browser.waitForElement(By
				.xpath("//a[contains(text(),'" + toolName + "')]"));
	}

	@Override
	protected By getXpathForTegrityLink() {
		return tegrityLink;
	}

	@Override
	protected Element getTegrityElement() {
		return tegrityLinkElement;
	}
}
