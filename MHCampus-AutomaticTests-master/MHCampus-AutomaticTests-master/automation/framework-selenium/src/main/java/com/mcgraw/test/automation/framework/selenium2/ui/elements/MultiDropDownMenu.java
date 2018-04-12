package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.JSActions;

public class MultiDropDownMenu extends Element {
	
	public static final String MENU_ITEMS_LOCATOR = "//ul[contains(@class,'ui-multiselect-checkboxes')]/li";
	
	public static final String MENU_ITEMS_TEXT_LOCATOR = MENU_ITEMS_LOCATOR+"/label/span";

	private final String ITEM_XPATH_PATTERN = "//ul/li/..//input[starts-with(@title, '%s')]";

	private Element item;

	public MultiDropDownMenu(WebElement parentWebElement) {
		super(parentWebElement);
	}

	public void selectMenuItem(Browser browser, String itemName) {
		try {
			clickExpander(browser, this.getWebElement());
			Element curItem = buildElement(browser, itemName);
			if (browser.getBrowserType()
					.equals("Microsoft Internet Explorer")) {
				curItem.sendKeys(Keys.ENTER);
			} else {
				browser.fireJavaScriptEventForElement(
						JSActions.CLICK, curItem);
			}
		} catch (NoSuchElementException e) {
			clickExpander(browser, this.getWebElement());
		}
	}
	
	public void selectMenuItems(Browser browser, List<String> itemNames) {
		try {
			clickExpander(browser, this.getWebElement());
			for (String itemName:itemNames){
				Element curItem = buildElement(browser, itemName);
				if (browser.getBrowserType()
						.equals("Microsoft Internet Explorer")) {
					curItem.sendKeys(Keys.ENTER);
				} else {
					browser.fireJavaScriptEventForElement(
							JSActions.CLICK, curItem);
				}
			}
			clickExpander(browser, this.getWebElement());
				
		} catch (NoSuchElementException e) {
			this.click();
		}
	}

	@SuppressWarnings("finally")
	public boolean isMenuItemPresent(Browser browser, String itemName) {
		Element curItem;
		boolean result = true;

		try {
			clickExpander(browser, this.getWebElement());
			curItem = buildElement(browser, itemName);
			if (curItem == null) {
				result = false;
			}
			result = curItem.isDisplayed();
		} catch (NoSuchElementException e) {
			result = false;
		} finally {
			clickExpander(browser, this.getWebElement());
			return result;
		}
	}

	private Element buildElement(Browser browser, String itemName) throws NoSuchElementException {
//		clickExpander(browser, this.getWebElement());
		String relativePath = String.format(ITEM_XPATH_PATTERN, itemName);
		item = this.findElement(By.xpath("./.." + relativePath));
		return item;
	}
	
	/**
	 * Obtains names of all items in the list
	 *
	 * @return List with menu item names
	 */
	public List<String> getMenuItems(Browser browser) {
		clickExpander(browser, this.getWebElement());
		List<WebElement> elements = this.findElements(By
				.xpath(MENU_ITEMS_TEXT_LOCATOR));
		List<String> menuItemNames = new ArrayList<String>();

		for (WebElement element : elements) {
			if (element.isDisplayed()) {
				menuItemNames.add(element.getText());
			}

		}
		clickExpander(browser, this.getWebElement());
		return menuItemNames;
	
	}

	public void uncheckAll(Browser browser) {

		try {
			this.click();
			Element uncheckAllOption = this
					.findElement(By
							.xpath("//div[contains(@class,'ui-multiselect-menu')][contains(@style,'block;')]//li//*[contains(text(),'Uncheck all')]"));
			browser.fireJavaScriptEventForElement(JSActions.CLICK,
					uncheckAllOption);
			// uncheckAllOption.click();
			this.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void checkAll() {

		try {
			this.click();
			this.findElement(
					By.xpath("//div[contains(@class,'ui-multiselect-menu')][contains(@style,'block;')]//li//*[contains(text(),'Check all')]"))
					.click();
			this.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close(Browser browser){
		try{
			for (WebElement cls: browser.findElements(By.xpath("//a[@class='ui-multiselect-close']"))){
				if (cls.isDisplayed()){
					browser.fireJavaScriptEventForElement(JSActions.CLICK, cls);
//					cls.click();
				}
			}
		}
		catch (Exception e){
			Logger.warn(e.getClass().getSimpleName()+" occured on attempt to close dropdown menu");
		}
	}
	
	private void clickExpander(Browser browser, WebElement webElement) {
		try {
			if (browser.getBrowserType()
					.equals("Microsoft Internet Explorer")) {
				webElement.click();
			} else {
				browser.fireJavaScriptEventForElement(
						JSActions.CLICK, webElement);
			}
			browser.pause(500);
		} catch (WebDriverException e) {
			Logger.warn("Webdriver message occured when executing script for clicking element. It may not be executed.");
		}
	}
}
