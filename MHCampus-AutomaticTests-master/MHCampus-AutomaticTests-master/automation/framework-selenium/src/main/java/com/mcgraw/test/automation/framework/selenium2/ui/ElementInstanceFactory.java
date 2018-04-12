package com.mcgraw.test.automation.framework.selenium2.ui;

import org.openqa.selenium.WebElement;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.BlockElement;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Select;

/**
 * Factory for creating instances of different kind of elements
 * 
 * @author Andrei_Turavets
 *
 */
public class ElementInstanceFactory {
	
	public static <T extends Element> T createElementInstance(Class<T> elementClass, WebElement webElement) {
		try {
			return (T) elementClass.getConstructor(WebElement.class).newInstance(webElement);
		} catch (Exception e) {
			Logger.error(e.getMessage());
			return null;
		}
	}

	public static Select createSelectInstance(Class<?> elementClass, WebElement webElement) {
		try {
			return (Select) elementClass.getConstructor(WebElement.class).newInstance(webElement);
		} catch (Exception e) {
			Logger.error(e.getMessage());
			return null;
		}
	}

	public static <T extends BlockElement> T createBlockElementInstance(Class<T> elementClass, WebElement webElement) {
		try {
			return elementClass.getConstructor(WebElement.class).newInstance(webElement);
		} catch (Exception e) {
			Logger.error(e.getMessage());
			return null;
		}
	}

}
