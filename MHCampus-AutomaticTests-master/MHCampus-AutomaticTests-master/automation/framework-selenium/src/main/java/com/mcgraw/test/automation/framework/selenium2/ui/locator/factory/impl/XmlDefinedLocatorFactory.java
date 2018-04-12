package com.mcgraw.test.automation.framework.selenium2.ui.locator.factory.impl;

import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.mcgraw.test.automation.framework.core.common.JaxbMarshaller;
import com.mcgraw.test.automation.framework.core.common.ResourceUtils;
import com.mcgraw.test.automation.framework.core.exception.PredefinedLocatorException;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.binding.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.binding.Elements;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.binding.Locator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.factory.PredifinedLocatorFactory;

/**
 * Implementation of predefined locator factory. Uses files in XML format for
 * defining locators. <br>
 * Schema and binding for files located
 * {@link om.mcgraw.test.automation.framework.selenium2.ui.locator.binding}
 *
 * @author Andrei Varabyeu
 *
 */
public class XmlDefinedLocatorFactory implements PredifinedLocatorFactory {

	private static final String JAXB_CONTEXT_PATH = Elements.class.getPackage()
			.getName();

	private Map<String, Elements> sourcesCache = Collections
			.synchronizedMap(new HashMap<String, Elements>());

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * om.mcgraw.test.automation.framework.selenium2.ui.locator.factory.PredifinedLocatorFactory#
	 * createLocator(java.lang.String, java.lang.String)
	 */
	@Override
	public Set<By> createLocator(String locatorName, String source) {
		Elements elementsList;

		/*
		 * Cache for unmarshalled objects
		 */
		if (!sourcesCache.containsKey(source)) {
			InputStream sourceUrl = ResourceUtils
					.getResourceAsInputStream(source);

			if (null == sourceUrl) {
				throw new PredefinedLocatorException(
						" There is no defined locator with name '"
								+ locatorName + "' because source '" + source
								+ "' doesn't exist");
			}

			Source definitionSource = new StreamSource(sourceUrl);
			elementsList = JaxbMarshaller.getInstanceForContext(
					JAXB_CONTEXT_PATH).unmarshall(definitionSource,
					Elements.class);
			sourcesCache.put(source, elementsList);
		} else {
			elementsList = sourcesCache.get(source);
		}

		List<Element> elements = elementsList.getElement();
		Optional<Element> elementSearchResult = Iterables.tryFind(elements,
				new FindElementByNamePredicate(locatorName));
		if (!elementSearchResult.isPresent()) {
			throw new PredefinedLocatorException(
					" There is no defined locator with name '" + locatorName
							+ "' in file " + source);
		}
		Set<Locator> locatorsSet = getLocatorsSet(elementSearchResult.get());
		return transformSet(locatorsSet, new LocatorToByTransformer());
	}

	/**
	 * Transforms each element of set using specified transformer
	 *
	 * @param set
	 * @param transformer
	 * @return
	 */
	private <T, V> Set<V> transformSet(Set<T> set, Function<T, V> transformer) {
		Set<V> transformedSet = new LinkedHashSet<V>();
		for (T t : set) {
			transformedSet.add(transformer.apply(t));
		}
		return transformedSet;
	}

	/**
	 * Converts list of locators to ordered set (ordering by priority)
	 *
	 * @param element
	 * @return
	 */
	private Set<Locator> getLocatorsSet(Element element) {
		List<Locator> allLocators = element.getLocator();
		Set<Locator> locatorsSet = new TreeSet<Locator>(
				new LocatorPriorityComparator());
		if (isEqualPrioritiesInList(allLocators)) {
			throw new PredefinedLocatorException(
					"There is some locators with the same priority in element with name '"
							+ element.getName() + "'");
		}
		locatorsSet.addAll(allLocators);
		return locatorsSet;
	}

	/**
	 * Check whether there are some locators with same priority in locators list
	 *
	 * @param locators
	 * @return
	 */
	private boolean isEqualPrioritiesInList(List<Locator> locators) {
		Set<Integer> priorities = new HashSet<Integer>();

		for (Locator locator : locators) {
			if (priorities.contains(locator.getPriority()))
				return true;
			priorities.add(locator.getPriority());
		}
		return false;
	}

	/**
	 * Predicate for finding element with defined name in collection of elements
	 *
	 * @author Andrei Varabyeu
	 *
	 */
	private static class FindElementByNamePredicate implements
			Predicate<Element> {
		private String elementName;

		public FindElementByNamePredicate(String elementName) {
			this.elementName = elementName;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see com.google.common.base.Predicate#apply(java.lang.Object)
		 */
		@Override
		public boolean apply(Element element) {
			return elementName.equals(element.getName());
		}
	}

	/**
	 * Comparator for ordering locators from high to low priority
	 *
	 * @author Andrei Varabyeu
	 *
	 */
	private static class LocatorPriorityComparator implements
			Comparator<Locator> {

		/*
		 * (non-Javadoc)
		 *
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Locator first, Locator second) {
			return (first.getPriority() < second.getPriority()) ? -1 : ((first
					.getPriority() == second.getPriority()) ? 0 : 1);
		}
	}

	/**
	 * Transforms locator in XML-style to Selenium locator
	 *
	 * @author Andrei Varabyeu
	 *
	 */
	private static class LocatorToByTransformer implements
			Function<Locator, By> {

		/*
		 * (non-Javadoc)
		 *
		 * @see
		 * org.apache.commons.collections.Transformer#transform(java.lang.Object
		 * )
		 */
		@Override
		public By apply(Locator locator) {
			String locatorContent = locator.getContent().trim();
			By by;
			try {
				switch (locator.getType()) {
				case CLASS_NAME:
					by = By.className(locatorContent);
					break;
				case CSS:
					by = By.cssSelector(locatorContent);
					break;
				case ID:
					by = By.id(locatorContent);
					break;
				case LINK_TEXT:
					by = By.linkText(locatorContent);
					break;
				case NAME_ATTRIBUTE:
					by = By.name(locatorContent);
					break;
				case PARTIAL_LINK_TEXT:
					by = By.partialLinkText(locatorContent);
					break;
				case TAG_NAME:
					by = By.tagName(locatorContent);
					break;
				case X_PATH:
					by = By.xpath(locatorContent);
					break;
				default:
					String message = "Unknown locator type ["
							+ locator.getType() + "] in locator ["
							+ locator.getContent() + "]";
					Logger.error(message);
					throw new PredefinedLocatorException(message);
				}
			} catch (InvalidSelectorException e) {
				String message = "Exception on creating locator with type ["
						+ locator.getType() + "] and content ["
						+ locator.getContent() + "]";
				Logger.error(message);
				throw new PredefinedLocatorException(message, e);
			}
			return by;
		}

	}

}
