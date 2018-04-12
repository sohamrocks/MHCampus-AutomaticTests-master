package com.mcgraw.test.automation.framework.selenium2.ui.locator.selenium_impl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.IFieldProcessor;

/**
 * Implementation of Selenium
 * {@link org.openqa.selenium.support.pagefactory.ElementLocatorFactory}. Uses
 * {@link om.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.IFieldProcessor}
 * for processing fields
 * 
 * @author Andrei Varabyeu
 * 
 */
public class PredefinedElementLocatorFactory implements ElementLocatorFactory {
	/**
	 * SearchContext for processing the fields
	 */
	private final SearchContext searchContext;

	/**
	 * Field processor
	 */
	private final List<IFieldProcessor> annotationProcessor;

	public PredefinedElementLocatorFactory(SearchContext searchContext, IFieldProcessor... annotationProcessor) {
		this.searchContext = searchContext;
		this.annotationProcessor = Arrays.asList(annotationProcessor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.openqa.selenium.support.pagefactory.ElementLocatorFactory#createLocator
	 * (java.lang.reflect.Field)
	 */
	@Override
	public ElementLocator createLocator(Field field) {
		for (IFieldProcessor fieldProcessor : annotationProcessor) {
			if (fieldProcessor.isSupported(field)) {
				return new PredefinedElementLocator(searchContext, fieldProcessor.buildBy(field), fieldProcessor.isLookupCached(field));
			}
		}
		throw new IllegalArgumentException("Unable to get locators from field " + field);
	}

}
