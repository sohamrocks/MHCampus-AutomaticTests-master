package com.mcgraw.test.automation.framework.selenium2.ui;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;

import com.google.common.collect.Lists;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocatorFieldProcessor.DefinedLocatorToByTransformer;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

/**
 * Processor for classes marked with
 * {@link com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator}
 * annotation
 * 
 * @author Andrei_Turavets
 *
 */
public class PageFrameIdentificatorTypeProcessor {
	/**
	 * @param annotatedElement
	 * @return Set of By elements
	 */
	public Set<By> buildBy(AnnotatedElement annotatedElement) {

		DefinedLocators definedLocatorsAnnotation = getDefinedLocatorsAnnotation(annotatedElement);
		if (definedLocatorsAnnotation != null) {
			List<DefinedLocator> definedLocators = Arrays.asList(definedLocatorsAnnotation.value());
			// transform to By
			List<By> byLocators = Lists.transform(definedLocators, new DefinedLocatorToByTransformer());
			return new LinkedHashSet<By>(byLocators);
		}

		return null;
	}
	
	public boolean isFrameCheckCanBeSkipped(AnnotatedElement annotatedElement){
		return annotatedElement.getAnnotation(PageFrameIdentificator.class).frameCheckCanBeSkipped(); 		
	}

	/**
	 * Validates annotations on provided field
	 * 
	 * @param annotatedElement
	 */
	private DefinedLocators getDefinedLocatorsAnnotation(AnnotatedElement annotatedElement) {
		if (annotatedElement.isAnnotationPresent(PageFrameIdentificator.class)) {
			return annotatedElement.getAnnotation(PageFrameIdentificator.class).locators();
		}
		return null;
	}

}
