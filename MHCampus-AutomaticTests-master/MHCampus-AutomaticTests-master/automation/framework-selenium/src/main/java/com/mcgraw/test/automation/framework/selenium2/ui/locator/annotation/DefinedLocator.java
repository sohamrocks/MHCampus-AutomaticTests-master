package com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.openqa.selenium.support.How;

/**
 * Defined Locator annotation. Should be used instead of
 * {@link org.openqa.selenium.support.FindBy}<br>
 * Contains priority. Set of Defined locators may be defined using
 * {@link com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators}
 *
 *
 * @author Andrei Varabyeu
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface DefinedLocator {

	/**
	 * Default WebDriver's search method representation
	 */
	How how() default How.XPATH;

	/**
	 * Locator
	 */
	String using();

	/**
	 * Locator's priority
	 */
	int priority() default 1;

}
