package com.mcgraw.test.automation.framework.selenium2.ui.locator.selenium_impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;

/**
 * The default element locator, which will lazily locate an element on a page.
 * This class is designed for use with the
 * {@link om.mcgraw.test.automation.framework.selenium2.ui.ScreenFactory} and understands the
 * annotations
 * {@link om.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.PredefinedLocator}
 * and {@link om.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.CachedLocator}
 * 
 * @author Andrei Varabyeu
 */
public class PredefinedElementLocator implements ElementLocator {

	/**
	 * Selenium WebDriver
	 */
	private final SearchContext searchContext;

	/**
	 * Is element should be cached
	 */
	private final boolean cacheElement;

	/**
	 * Set of locators for element
	 */
	private final Set<By> bySet;

	/**
	 * Cached Element in case when caching is used
	 */
	private WebElement cachedElement;

	/**
	 * Cached Element list in case when caching is used
	 */
	private List<WebElement> cachedElementList;

	

	/**
	 * Creates a new element locator.
	 * 
	 * @param driver
	 *            The driver to use when finding the element
	 * @param field
	 *            The field on the Page Object that will hold the located value
	 */
	public PredefinedElementLocator(SearchContext driver, Set<By> bySet, boolean cacheElement) {
		this.searchContext = driver;
		this.cacheElement = cacheElement;
		this.bySet = bySet;
	}

	/**
	 * Find the element.
	 */
	@Override
	public WebElement findElement() {
		if (cachedElement != null && cacheElement) {
			return cachedElement;
		}

		for (By by : bySet) {
			WebElement element;
			try {
				element = searchContext.findElement(by);
				Logger.debug("Locator '" + by.toString() + "' is used");
				if (cacheElement) {
					cachedElement = element;
				}
				return element;
			} catch (NoSuchElementException e) {
//				if (!Browser.DISABLE_LOGGER_WARNINGS) {
//					Logger.warn("Cannot find element with locator '" + by.toString() + "'. Please, check your definitions.");
//				}
				continue;
			}

		}
		String error = "Cannot find any elements with locators '" + bySet.toString() + "'. Please, check your definitions.";
		if (!Browser.DISABLE_LOGGER_WARNINGS) {
			Logger.error(error);
		}
		throw new NoSuchElementException(error);
	}

	@Override
	public List<WebElement> findElements() {
		if (cachedElementList != null && cacheElement) {
			return cachedElementList;
		}

		for (By by : bySet) {
			List<WebElement> elements = searchContext.findElements(by);
			if (null != elements && !elements.isEmpty()) {
				Logger.debug("Locator '" + by.toString() + "' is used");
				if (cacheElement) {
					cachedElementList = elements;
				}
				return elements;
			}
		}
		Logger.error("Cannot find any elements with locators '" + bySet.toString() + "'. Please, check your definitions.");
		return Collections.emptyList();
	}

}
