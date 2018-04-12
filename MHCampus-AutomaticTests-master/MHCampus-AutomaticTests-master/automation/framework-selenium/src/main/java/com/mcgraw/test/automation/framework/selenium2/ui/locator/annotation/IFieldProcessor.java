package com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation;

import java.lang.reflect.AnnotatedElement;
import java.util.Set;

import org.openqa.selenium.By;

/**
 * Interface for classes which should process annotation on fields of different
 * types of elements<br>
 *
 * @author Andrei Varabyeu
 *
 */
public interface IFieldProcessor {
	/**
	 * Returns true if locator should be cached
	 *
	 * @param field
	 *            - field for processing
	 * @return true if locator should be cached
	 */
	boolean isLookupCached(AnnotatedElement field);

	/**
	 * Builds set of Selenium locators
	 *
	 * @param field
	 *            - field for processing
	 * @return set of Selenium locators
	 */
	Set<By> buildBy(AnnotatedElement field);

	boolean isSupported(AnnotatedElement field);
}
