package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.timing.SmartWait;
import com.mcgraw.test.automation.framework.core.timing.TimeoutException;
import com.mcgraw.test.automation.framework.core.timing.Waitable;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;

/**
 * Wrapper for Selenium's {@link @org.openqa.selenium.WebElement}
 * 
 * @author Andrei Varabyeu
 * 
 */
public class Element implements WebElement, Locatable, WrapsElement {
	/**
	 * Selenium's WebElement
	 */
	protected WebElement webElement;
	
	public Element(WebElement webElement) {
		this.webElement = webElement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#click()
	 */
	@Override
	public void click() {
		Logger.debug("Clicking element: '" + getIdentifyingText() + "'");
		if(Browser.getBrowser().getBrowserName().equals("ie")){
			webElement.sendKeys(Keys.CONTROL);
		}
		webElement.click();
	}
	
	public void jsClick(Browser browser) {
		Logger.info("Clicking element by using Javascript: '" + getIdentifyingText() + "'");
		browser.executeScript("arguments[0].click();", webElement);
	}

	public void jsSendKeys(Browser browser, String keysToSend) {
		Logger.info("Sending keys by using Javascript into element: '" + getIdentifyingText() + "'");
		browser.executeScript("arguments[0].setAttribute('value', arguments[1]);", webElement, keysToSend);
	}
	
	public void setAttribute(String attName, String attValue, Browser browser) {
		Logger.info(String.format("Setting attribute: %s with value: %s by using Javascript for element: $s", attName, attValue,  getIdentifyingText()));
		browser.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",  webElement, attName, attValue);
    }
	
	// use this click ONLY in those cases when webdriver can't return control to
	// continue the test run
	public void clickAsynchronously() {
		Logger.debug("Clicking element asynchrnously: '" + getIdentifyingText() + "'");
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					webElement.click();
				} catch (Exception ex) {
					// handle error which cannot be thrown back
				}
			}
		}).start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#submit()
	 */
	@Override
	public void submit() {
		Logger.debug("Submitting: '" + getIdentifyingText() + "'");
		webElement.submit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#sendKeys(java.lang.CharSequence[])
	 */
	@Override
	public void sendKeys(CharSequence... keysToSend) {
		Logger.debug("Sending char sequence '" + String.valueOf(keysToSend) + " for element: '" + getIdentifyingText() + "'");
		webElement.sendKeys(keysToSend);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#sendKeys(java.lang.CharSequence[])
	 */
	public void sendKeys(Keys keysToSend) {
		Logger.debug("Sending keyboard key '" + keysToSend.name() + "' for element: '" + getIdentifyingText() + "'");
		webElement.sendKeys(keysToSend);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#clear()
	 */
	@Override
	public void clear() {
		Logger.debug("Clearing element: '" + getIdentifyingText() + "'");
		webElement.clear();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#getTagName()
	 */
	@Override
	public String getTagName() {
		return webElement.getTagName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#getAttribute(java.lang.String)
	 */
	@Override
	public String getAttribute(String name) {
		return webElement.getAttribute(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#isSelected()
	 */
	@Override
	public boolean isSelected() {
		return webElement.isSelected();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return webElement.isEnabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#getText()
	 */
	@Override
	public String getText() {
		return webElement.getText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#findElements(org.openqa.selenium.By)
	 */
	@Override
	public List<WebElement> findElements(By by) {
		List<WebElement> elements = new ArrayList<WebElement>();
		for (WebElement webElementItem : webElement.findElements(by)) {
			elements.add(new Element(webElementItem));
		}
		return elements;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#findElement(org.openqa.selenium.By)
	 */
	@Override
	public Element findElement(By by) {
		return new Element(webElement.findElement(by));
	}

	public boolean isRelativeElementPresent(By by) {
		return (webElement.findElements(by).size() > 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#getCssValue(java.lang.String)
	 */
	@Override
	public String getCssValue(String arg0) {
		return webElement.getCssValue(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#getLocation()
	 */
	@Override
	public Point getLocation() {
		return webElement.getLocation();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#getSize()
	 */
	@Override
	public Dimension getSize() {
		return webElement.getSize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#isDisplayed()
	 */
	@Override
	public boolean isDisplayed() {
		return webElement.isDisplayed();
	}

	public boolean isElementPresent() {
		try {
			isElementAvailable();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public WebElement getWebElement() {
		return webElement;
	}

	public boolean isElementAvailable() {
		return webElement.isEnabled();
	}

	public boolean waitForPresence() {
		return waitForPresence(Browser.DEFAULT_WAIT_FOR_ELEMENT);
	}

	public boolean waitForPresence(long timeInSeconds) {
		// Waiting 30 seconds for an element to be present on the page, checking
		// for its presence once every 1 second.
		Browser.DISABLE_LOGGER_WARNINGS = true;
		Waitable<Element> wait = new SmartWait<Element>(this).withTimeout(timeInSeconds, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class, ElementNotVisibleException.class);
		try {
			wait.until(new Function<Element, Boolean>() {

				@Override
				public Boolean apply(Element element) {
					return element.isElementPresent() && element.isDisplayed();
				}

			});
			Browser.DISABLE_LOGGER_WARNINGS = false;
			return true;
		} catch (TimeoutException e) {
			Browser.DISABLE_LOGGER_WARNINGS = false;
			return false;
		}
	}

	public boolean waitForNonEmptyText(long timeInSeconds) {
		// Waiting 30 seconds for an element to be present on the page, checking
		// for its presence once every 1 second.
		Browser.DISABLE_LOGGER_WARNINGS = true;
		Waitable<Element> wait = new SmartWait<Element>(this).withTimeout(timeInSeconds, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class, ElementNotVisibleException.class);
		try {
			wait.until(new Function<Element, Boolean>() {

				@Override
				public Boolean apply(Element element) {
					return element.isElementPresent() && element.isDisplayed() && !element.getText().isEmpty();
				}

			});
			Browser.DISABLE_LOGGER_WARNINGS = false;
			return true;
		} catch (TimeoutException e) {
			Browser.DISABLE_LOGGER_WARNINGS = false;
			return false;
		}
	}

	public boolean waitForAbsence(long timeInSeconds) {
		// Waiting timeInSeconds seconds for an element to be present on the
		// page, checking
		// for its presence once every 1 second.
		Browser.DISABLE_LOGGER_WARNINGS = true;
		Waitable<Element> wait = new SmartWait<Element>(this).withTimeout(timeInSeconds, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		try {
			wait.until(new Function<Element, Boolean>() {

				@Override
				public Boolean apply(Element element) {
					return !element.isElementPresent();
				}

			});
			Browser.DISABLE_LOGGER_WARNINGS = false;
			return true;
		} catch (TimeoutException e) {
			Browser.DISABLE_LOGGER_WARNINGS = false;
			return false;
		}
	}

	public boolean waitFor(long timeout, TimeUnit timeUnit, Predicate<Element> predicate) {
		// Waiting 30 seconds for an element to be present on the page, checking
		// for its presence once every 5 seconds.
		SmartWait<Element> wait = new SmartWait<Element>(this).withTimeout(timeout, timeUnit).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);
		try {
			wait.until(predicate);
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String getIdentifyingText() {
		try {

			// form tag processing
			if (this.getTagName().equalsIgnoreCase("form")) {
				if (this.isRelativeElementPresent(By.xpath("./preceding-sibling::*[contains(name(.),'H')]"))) {
					return "form with caption '" + this.findElement(By.xpath("./preceding-sibling::*[contains(name(.),'H')]")).getText()
							+ "'";
				} else if (this.isRelativeElementPresent(By.xpath(".//*[contains(name(.),'H')]"))) {
					return "form with caption '" + this.findElement(By.xpath(".//*[contains(name(.),'H')]")).getText() + "'";
				} else {
					return "-- no caption found for form --";
				}
			}

			String tmp;
			if (((tmp = this.getText()) != null) && (!tmp.isEmpty())) {
				return tmp;
			}

			if (((tmp = this.getAttribute("value")) != null) && (!tmp.isEmpty())
					&& (!this.getAttribute("type").equalsIgnoreCase("checkbox"))) {
				return tmp;
			}

			else if (this.getTagName().equalsIgnoreCase("input")) {

				if (((tmp = this.getAttribute("name")) != null) && (!tmp.isEmpty())) {
					return "name=" + tmp;
				}

				if (((tmp = this.getAttribute("id")) != null) && (!tmp.isEmpty())) {
					return "id=" + tmp;
				}

				if (((tmp = this.getAttribute("class")) != null) && (!tmp.isEmpty())) {
					return "class=" + tmp;
				}
			}

			/*
			 * else if ((this.findElement(By.xpath("..")).isElementPresent())) {
			 * String candidateText =
			 * this.findElement(By.xpath("..")).getText();
			 * 
			 * if ((candidateText != null) && (!candidateText.isEmpty())) {
			 * candidateText.replace('\n', '/'); return candidateText; } }
			 */

			else if (this.isRelativeElementPresent(By.xpath("../label[1]"))) {
				String candidateText = this.findElement(By.xpath("../label[1]")).getText();
				if ((candidateText != null) && (!candidateText.isEmpty())) {
					return candidateText;
				}
			}

			else if (((tmp = this.getAttribute("id")) != null) && (!tmp.isEmpty())) {
				return "id=" + tmp;
			}

			else if (((tmp = this.getAttribute("name")) != null) && (!tmp.isEmpty())) {
				return "name=" + tmp;
			}
		} catch (Exception e) {
			Logger.warn(e.getClass().getSimpleName() + " exception occured when getting element identifier!");
		}

		return webElement.toString();

	}

	@Override
	public WebElement getWrappedElement() {
		return this.webElement;
	}

	@Override
	public Coordinates getCoordinates() {
		return ((Locatable) webElement).getCoordinates();
	}

	@Override
	public String toString() {
		return getIdentifyingText();
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> arg0) throws WebDriverException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle getRect() {
		// TODO Auto-generated method stub
		return null;
	}

}
