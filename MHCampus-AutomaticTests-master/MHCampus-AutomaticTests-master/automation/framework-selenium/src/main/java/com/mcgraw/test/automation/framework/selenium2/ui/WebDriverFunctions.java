package com.mcgraw.test.automation.framework.selenium2.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;

public class WebDriverFunctions {

	public static Function<WebElement, String> getWebElementText() {
		return new Function<WebElement, String>() {
			@Override
			public String apply(WebElement webElement) {
				return webElement.getText().trim();
			}
		};
	}

	public static ExpectedCondition<Boolean> visibilityOfElement(final By by) {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				List<WebElement> element = webDriver.findElements(by);
				return (element.isEmpty() && element.get(0).isDisplayed());
			}
		};
	}

	public static Function<WebDriver, Element> findElement(final By locator,
			final boolean isVisReq) {
		return new Function<WebDriver, Element>() {
			@Override
			public Element apply(WebDriver wd) {
				WebElement element = wd.findElement(locator);
				if (!element.isDisplayed() && isVisReq)
					throw new NoSuchElementException(
							"There is no visible element with locator "
									+ locator);
				return new Element(element);
			}
		};
	}
	
	public static Function<WebDriver, List<WebElement>> findElements(final By locator,
			final boolean isVisReq) {
		return new Function<WebDriver, List<WebElement>>() {
			@Override
			public List<WebElement> apply(WebDriver wd) {
				
				List<WebElement> elements = new ArrayList<WebElement>();
				for (WebElement webElement : wd.findElements(locator)) {
					elements.add(new Element(webElement));					
				}				
				if (elements.size() == 0 && isVisReq)
					throw new NoSuchElementException(
							"There is no visible elements with locator "
									+ locator);
				
				return elements;
			}
		};
	}
	
	public static Function<WebDriver, WebElement> findElement(final Element targetElement, final boolean isVisReq) {
		return new Function<WebDriver, WebElement>() {
			@Override
			public Element apply(WebDriver wd) {
				WebElement element = targetElement.getWebElement();
				if (!element.isDisplayed() && isVisReq)
					throw new NoSuchElementException(
							"There is no visible element with locator "
									+ targetElement.getWebElement());
				return new Element(element);
			}
		};
	}

	public static Predicate<Element> elementToBeClickable() {
		return new Predicate<Element>() {
			@Override
			public boolean apply(@Nullable Element input) {
				try {
					if ((input != null) && (input.isEnabled())
							&& (input.isDisplayed())) {
						return true;
					}
					return false;
				} catch (StaleElementReferenceException e) {
				}
				return false;
			}
		};
	}
}
