package com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.CacheLookup;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.mcgraw.test.automation.framework.core.exception.PredefinedLocatorException;
import com.mcgraw.test.automation.framework.selenium2.ui.ElementUtils;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;

/**
 * Processor for fields marked with
 * {@link com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators}
 * annotation
 * 
 * @author Andrei Varabyeu
 * 
 */
public class DefinedLocatorFieldProcessor implements IFieldProcessor {

	@Override
	public boolean isLookupCached(AnnotatedElement annotatedElement) {
		if (annotatedElement.isAnnotationPresent(CachedLocator.class)) {
			return true;
		}
		if (annotatedElement.isAnnotationPresent(CacheLookup.class)) {
			return true;
		}
		return false;
	}

	@Override
	public Set<By> buildBy(AnnotatedElement annotatedElement) {

		DefinedLocators definedLocatorsAnnotation = getDefinedLocatorsAnnotation(annotatedElement);
		List<DefinedLocator> definedLocators = Arrays.asList(definedLocatorsAnnotation.value());

		if (isEqualPrioritiesInList(definedLocators)) {
			throw new PredefinedLocatorException("There is some locators with the same priority in element '" + annotatedElement + "'");
		}
		// sort according priorities
		Collections.sort(definedLocators, new LocatorPriorityComparator());

		// transform to By
		List<By> byLocators = Lists.transform(definedLocators, new DefinedLocatorToByTransformer());
		return new LinkedHashSet<By>(byLocators);
	}

	@Override
	public boolean isSupported(AnnotatedElement annotatedElement) {
		return annotatedElement.isAnnotationPresent(DefinedLocators.class) || annotatedElement.isAnnotationPresent(DefinedLocator.class)
				|| annotatedElement.isAnnotationPresent(PageIdentificator.class) || annotatedElement.isAnnotationPresent(Block.class)
				|| isFieldBlockElement(annotatedElement) || isFieldBlockElementList(annotatedElement);
	}

	private Class<?> getAnnotatedElementAsType(AnnotatedElement annotatedElement) {

		if (annotatedElement instanceof Class) {
			return Class.class.cast(annotatedElement);
		}
		if (annotatedElement instanceof Field) {
			return Field.class.cast(annotatedElement).getType();
		}
		return null;
	}

	private Field getAnnotatedElementAsField(AnnotatedElement annotatedElement) {
		if (annotatedElement instanceof Field) {
			return Field.class.cast(annotatedElement);
		}
		return null;
	}

	private boolean isFieldBlockElement(AnnotatedElement annotatedElement) {
		Field field = getAnnotatedElementAsField(annotatedElement);
		return (field != null) ? ElementUtils.isBlockElement(field) : false;

	}

	private boolean isFieldBlockElementList(AnnotatedElement annotatedElement) {
		Field field = getAnnotatedElementAsField(annotatedElement);
		return (field != null) ? ElementUtils.isBlockElementList(field) : false;
	}

	private boolean isEqualPrioritiesInList(List<DefinedLocator> locators) {
		Set<Integer> priorities = new HashSet<Integer>();

		for (DefinedLocator locator : locators) {
			if (priorities.contains(locator.priority()))
				return true;
			priorities.add(locator.priority());
		}
		return false;
	}

	/**
	 * Validates annotations on provided field
	 * 
	 * @param annotatedElement
	 */
	private DefinedLocators getDefinedLocatorsAnnotation(AnnotatedElement annotatedElement) {
		if (!isSupported(annotatedElement)) {
			throw new IllegalArgumentException("Unable to process " + annotatedElement.getClass().getSimpleName()
					+ " element as it has no neither '@DefinedLocators' nor @PageIdentificator annotations");
		}
		if (annotatedElement.isAnnotationPresent(DefinedLocators.class)) {
			return annotatedElement.getAnnotation(DefinedLocators.class);
		}
		if (annotatedElement.isAnnotationPresent(PageIdentificator.class)) {
			return annotatedElement.getAnnotation(PageIdentificator.class).locators();
		}
		if (isFieldBlockElement(annotatedElement)) {
			Class<?> fieldClass = getAnnotatedElementAsType(annotatedElement);
			while (fieldClass != Object.class) {
				if (fieldClass.isAnnotationPresent(Block.class)) {
					Block block = fieldClass.getAnnotation(Block.class);
					return block.locators();
				}
				fieldClass = fieldClass.getSuperclass();
			}

		}
		if (isFieldBlockElementList(annotatedElement)) {
			Class<?> fieldClass = ElementUtils.getGenericParameterClass(getAnnotatedElementAsField(annotatedElement));
			while (fieldClass != Object.class) {
				if (fieldClass.isAnnotationPresent(Block.class)) {
					Block block = fieldClass.getAnnotation(Block.class);
					return block.locators();
				}
				fieldClass = fieldClass.getSuperclass();
			}
		}
		return null;
	}

	public static class DefinedLocatorToByTransformer implements Function<DefinedLocator, By> {

		@Override
		public By apply(DefinedLocator locator) {
			String using = locator.using();
			switch (locator.how()) {
			case CLASS_NAME:
				return By.className(using);
			case CSS:
				return By.cssSelector(using);
			case ID:
				return By.id(using);
			case ID_OR_NAME:
				return new ByIdOrName(using);
			case LINK_TEXT:
				return By.linkText(using);
			case NAME:
				return By.name(using);
			case PARTIAL_LINK_TEXT:
				return By.partialLinkText(using);
			case TAG_NAME:
				return By.tagName(using);
			case XPATH:
				return By.xpath(using);
			}

			throw new IllegalArgumentException("Cannot determine how to locate element");
		}
	}

	private static class LocatorPriorityComparator implements Comparator<DefinedLocator> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(DefinedLocator first, DefinedLocator second) {
			return (first.priority() < second.priority()) ? -1 : ((first.priority() == second.priority()) ? 0 : 1);
		}
	}
}
