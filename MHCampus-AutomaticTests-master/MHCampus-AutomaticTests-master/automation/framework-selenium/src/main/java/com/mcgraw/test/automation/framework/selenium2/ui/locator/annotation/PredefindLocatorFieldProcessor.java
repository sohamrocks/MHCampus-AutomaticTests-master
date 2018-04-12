package com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation;

import java.lang.reflect.AnnotatedElement;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.support.CacheLookup;

import com.mcgraw.test.automation.framework.selenium2.ui.locator.factory.PredifinedLocatorFactory;

/**
 * Implementation of field processor for
 * {@link om.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.PredefinedLocator} and
 *
 * {@link om.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.CachedLocator}
 *
 * @author Andrei Varabyeu
 *
 */
public class PredefindLocatorFieldProcessor implements IFieldProcessor {

	private PredifinedLocatorFactory predifinedLocatorFactory;

	public void setPredifinedLocatorFactory(
			PredifinedLocatorFactory predifinedLocatorFactory) {
		this.predifinedLocatorFactory = predifinedLocatorFactory;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * om.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.IFieldProcessor#isLookupCached
	 * (java.lang.reflect.Field)
	 */
	@Override
	public boolean isLookupCached(AnnotatedElement field) {
		if (field.isAnnotationPresent(CachedLocator.class)) {
			return true;
		}
		if (field.isAnnotationPresent(CacheLookup.class)) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * om.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.IFieldProcessor#buildBy
	 * (java.lang.reflect.Field)
	 */
	@Override
	public Set<By> buildBy(AnnotatedElement field) {
		validateAnnotations(field);
		PredefinedLocator predefinedLocatorAnnotation = field
				.getAnnotation(PredefinedLocator.class);
		return predifinedLocatorFactory.createLocator(
				predefinedLocatorAnnotation.name(),
				predefinedLocatorAnnotation.source());
	}

	/**
	 * Validates annotations on provided field
	 *
	 * @param field
	 */
	private void validateAnnotations(AnnotatedElement field) {
		PredefinedLocator predefinedLocator = field
				.getAnnotation(PredefinedLocator.class);
		if (null == predefinedLocator.name()
				|| predefinedLocator.name().isEmpty()) {
			throw new IllegalArgumentException(
					"'name' property in '@PredefinedLocator' annotation "
							+ "shouldn't be empty");
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * om.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.IFieldProcessor#isSupported
	 * (java.lang.reflect.Field)
	 */
	@Override
	public boolean isSupported(AnnotatedElement field) {
		return field.isAnnotationPresent(PredefinedLocator.class);
	}

}
