package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * Representation of Label placed on web pages
 *
 * @author Andrei Varabyeu
 *
 */
public class Label extends Element {

	public Label(WebElement webElement) {
		super(webElement);
	}

	/**
	 * You unable to sendKeys to Label
	 */
	@Override
	@Deprecated
	public void sendKeys(CharSequence... keysToSend) {
		throw new UnsupportedOperationException("You cannot send keys to label");
	}

	/**
	 * You unable to sendKeys to Label
	 */
	@Override
	@Deprecated
	public void sendKeys(Keys keysToSend) {
		throw new UnsupportedOperationException("You cannot send keys to label");
	}

	/**
	 * You unable to submit Label
	 */
	@Override
	@Deprecated
	public void submit() {
		throw new UnsupportedOperationException("You cannot submit label");
	}

}
