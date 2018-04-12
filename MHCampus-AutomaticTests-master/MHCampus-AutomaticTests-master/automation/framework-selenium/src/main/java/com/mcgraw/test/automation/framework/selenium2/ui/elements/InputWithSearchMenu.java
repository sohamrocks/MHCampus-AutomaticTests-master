package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.google.common.base.Function;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.timing.SmartWait;

public class InputWithSearchMenu extends Element {

	public InputWithSearchMenu(WebElement webElement) {
		super(webElement);
	}

	public void selectValue(String value) {
		Logger.operation("Type value '" + value + "' to Input '" + webElement
				+ "'");

		WebElement searchInput = getSearchInput();
		selectItem(searchInput, value);
		clickDone(searchInput);
	}

	public void selectValue(List<String> values) {
		Logger.operation("Type value '" + StringUtils.join(values, " ")
				+ "' to Input '" + webElement + "'");
		WebElement searchInput = getSearchInput();
		for (String value : values) {
			selectItem(searchInput, value);
		}
		clickDone(searchInput);
	}

	private void selectItem(final WebElement searchInput, String value) {
		searchInput.sendKeys(value);
		final String xpath = ".//../div//li[@class='biglist_result' and text()='"
				+ value + "']";
		WebElement searchInputLink = new SmartWait<WebElement>(searchInput)
				.withTimeout(10, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class)
				.until(new Function<WebElement, WebElement>() {
					@Override
					public WebElement apply(WebElement arg0) {
						return searchInput.findElement(By.xpath(xpath));
					}
				});

		searchInputLink.click();
	}

	private void openMenu() {
		webElement.click();
	}

	private WebElement getSearchInput() {
		openMenu();
		List<WebElement> inputs = webElement.findElements(By
				.xpath("//input[@type='search' and @placeholder='Search...']"));
		for (WebElement webElement : inputs) {
			if (webElement.isDisplayed()) {
				return webElement;
			}
		}
		throw new RuntimeException("There is not visible web element");
	}

	@SuppressWarnings("unused")
	private void closeMenu() {
		List<WebElement> webElements = webElement.findElements(By
				.xpath("//a[@role='button' and span/text()='close']"));
		if (webElements.isEmpty()) {
			Logger.debug("Unable to close, because there is no opened menu");
		}
		Logger.debug("Trying to close search menu");
		webElements.get(0).click();
	}

	private void clickDone(WebElement searchInput) {
		Logger.debug("Clicking 'Done' in input with search menu");
		searchInput.findElement(
				By.xpath(".//../../div/button[span[text()='Done']]")).click();

	}

}
