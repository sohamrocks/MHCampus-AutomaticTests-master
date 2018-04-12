package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.JSActions;

public class ElementWithDropDownMenu extends Element {

	public static final String ITEM_XPATH_PATTERN = "//ul/li/..//*[starts-with(normalize-space(text()),'%s')]";
	
	public static final String ALL_ITEMS_XPATH_PATTERN = "..//ul/li/..//*[string-length(text())>0]";

	public static final String ITEM_COUNT_PATTERN_FOR_SINGLE_FILTER = "//div[@id='sa_form_div']/div[2]/div/ul/li";

	public static final String MENU_ITEMS_LOCATOR = "//ul[contains(@class,'ui-multiselect-checkboxes')]/li";

	public static final String MENU_ITEMS_TEXT_LOCATOR = MENU_ITEMS_LOCATOR+"/label/span";

	public static final String INNER_SEARCH_INPUT = "//div[@id='sa_form_div']/div[2]//input";

	public static final String RANDOM_ITEM_XPATH = "//*[@id='tag_cloud_header']";

	public Element item;

	public ElementWithDropDownMenu(WebElement parentWebElement) {
		super(parentWebElement);
	}

	public void selectMenuItem(Browser browser, String itemName) {
		try {
			Element curItem = buildElement(browser, itemName);
			if (curItem.getTagName().equalsIgnoreCase("span")) {
				curItem.click();
			} else {
				clickExpander(browser, curItem.getWebElement());
			}
			
		} catch (NoSuchElementException e) {
			clickExpander(browser, this.getWebElement());
			Logger.error("Menu subitem not found.");
			throw e;
		} catch (WebDriverException e) {
			Logger.warn("Webdriver message occured when executing script. It may not be executed.");
		}
		finally{
			try{
				browser.switchTo().alert();
			}
			catch (NoAlertPresentException e){
				close(browser);
			}
		}
	}
	
	public void selectMenuItem(Browser browser, int itemIndex) {
		try {
			Element curItem = buildElement(browser, itemIndex);
			if (curItem.getTagName().equalsIgnoreCase("span")) {
				curItem.click();
			} else {
				clickExpander(browser, curItem.getWebElement());
			}
			
		} catch (NoSuchElementException e) {
			clickExpander(browser, this.getWebElement());
			Logger.error("Menu subitem not found.");
			throw e;
		} catch (WebDriverException e) {
			Logger.warn("Webdriver message occured when executing script. It may not be executed.");
		}
		finally{
			try{
				browser.switchTo().alert();
			}
			catch (NoAlertPresentException e){
				close(browser);
			}
		}
	}

	@SuppressWarnings("finally")
	public boolean isMenuItemPresent(Browser browser, String itemName) {
		Element curItem;
		boolean result = true;

		try {
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
	
	@SuppressWarnings("finally")
	public boolean isMenuItemPresent(Browser browser, int itemIndex) {
		Element curItem;
		boolean result = true;

		try {
			curItem = buildElement(browser, itemIndex);
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
		clickExpander(browser, this.getWebElement());
		String relativePath = String.format(ITEM_XPATH_PATTERN, itemName);
		item = this.findElement(By.xpath(".." + relativePath));
		return item;
	}
	
	private Element buildElement(Browser browser, int itemNumber) throws NoSuchElementException {
		clickExpander(browser, this.getWebElement());
		List<WebElement> items = this.findElements(By.xpath(ALL_ITEMS_XPATH_PATTERN));
		if (itemNumber>=items.size()){
			throw new NoSuchElementException("Requested item index ("+itemNumber+") is greater than total items count ("+items.size()+")");
		}
		else{
			return new Element(items.get(itemNumber));
		}
	}

	public int getRowsCountInSingleFilter() {
		int size = this.findElements(
				By.xpath(ITEM_COUNT_PATTERN_FOR_SINGLE_FILTER)).size();
		return size;
	}

	public int getRowsCountInMultiFilter() {
		int size = this.findElements(
				By.xpath(MENU_ITEMS_LOCATOR)).size();
		return size;
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

		} catch (WebDriverException e) {
			Logger.warn("Webdriver message occured whin executing script for clicking element. It may not be executed.");
		}
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
		return menuItemNames;
	}
	
	private void close(Browser browser){
		try{
			for (WebElement cls: this.findElements(By.xpath("//a[@class='ui-multiselect-close']"))){
				if (cls.isDisplayed()){
					browser.fireJavaScriptEventForElement(JSActions.CLICK, cls);
				}
			}
		}
		catch (Exception e){
			Logger.warn(e.getClass().getSimpleName()+" occured on attempt to close dropdown menu");
		}
	}
}
