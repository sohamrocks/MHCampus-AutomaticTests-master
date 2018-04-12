package com.mcgraw.test.automation.framework.selenium2.ui;

import static com.mcgraw.test.automation.framework.selenium2.ui.ElementUtils.getGenericParameterClass;
import static com.mcgraw.test.automation.framework.selenium2.ui.ElementUtils.isBlockElement;
import static com.mcgraw.test.automation.framework.selenium2.ui.ElementUtils.isBlockElementList;
import static com.mcgraw.test.automation.framework.selenium2.ui.ElementUtils.isElement;
import static com.mcgraw.test.automation.framework.selenium2.ui.ElementUtils.isElementList;
import static com.mcgraw.test.automation.framework.selenium2.ui.ElementUtils.isSelect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;

import com.mcgraw.test.automation.framework.selenium2.ui.elements.BlockElement;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Select;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocatorFieldProcessor;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.PredefindLocatorFieldProcessor;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.factory.impl.XmlDefinedLocatorFactory;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.selenium_impl.PredefinedElementLocatorFactory;
import com.mcgraw.test.automation.framework.selenium2.ui.proxyhandlers.BlockElementListProxyHandler;
import com.mcgraw.test.automation.framework.selenium2.ui.proxyhandlers.ElementListProxyHandler;
import com.mcgraw.test.automation.framework.selenium2.ui.proxyhandlers.ScreenshotableElementHandler;

/**
 * Decorator for Screen Fields. Wraps WebElement onto
 * {@link com.mcgraw.test.automation.framework.selenium2.ui.elements.Element}
 * representation
 * 
 * @author Andrei Turavets
 * 
 */
public class ScreenFieldDecorator implements FieldDecorator {
	protected ElementLocatorFactory factory;
	protected Screenshotable screenshotable;

	public ScreenFieldDecorator(ElementLocatorFactory factory, Screenshotable screenshotable) {
		this.factory = factory;
		this.screenshotable = screenshotable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.openqa.selenium.support.pagefactory.FieldDecorator#decorate(java.
	 * lang.ClassLoader, java.lang.reflect.Field)
	 */
	@Override
	public Object decorate(ClassLoader loader, Field field) {

		if (!isDecoratable(field)) {
			return null;
		}

		ElementLocator locator = factory.createLocator(field);
		if (locator == null) {
			return null;
		}
		if (isBlockElement(field)) {
			@SuppressWarnings("unchecked")
			Class<BlockElement> blockElementClass = (Class<BlockElement>) field.getType();
			return proxyForBlockLocator(loader, locator, blockElementClass);
		} else if (isBlockElementList(field)) {
			@SuppressWarnings("unchecked")
			Class<BlockElement> blockElementClass = (Class<BlockElement>) getGenericParameterClass(field);
			return proxyForBlockListLocator(loader, locator, blockElementClass);
		} else if (isElementList(field)) {
			@SuppressWarnings("unchecked")
			Class<Element> elementClass = (Class<Element>) getGenericParameterClass(field);
			return proxyForListLocator(loader, locator, elementClass);
		} else if (isElement(field)) {
			@SuppressWarnings("unchecked")
			Class<Element> elementClass = (Class<Element>) field.getType();
			return proxyForLocator(loader, locator, elementClass);
		} else if (isSelect(field)) {
			return proxyForSelect(loader, locator, field.getType());
		} else {
			return null;
		}

	}

	private boolean isDecoratable(Field field) {
		return (isElement(field) || isBlockElement(field) || isElementList(field) || isBlockElementList(field) || isSelect(field));

	}

	protected <T extends Element> T proxyForLocator(ClassLoader loader, ElementLocator locator, Class<T> fieldType) {
		InvocationHandler handler = new ScreenshotableElementHandler(new LocatingElementHandler(locator), screenshotable);

		T proxy = ElementInstanceFactory
				.createElementInstance(fieldType, (WebElement) Proxy.newProxyInstance(loader, new Class[] { WebElement.class,
						WrapsElement.class, Locatable.class }, handler));
		return proxy;
	}

	protected Select proxyForSelect(ClassLoader loader, ElementLocator locator, Class<?> fieldType) {
		InvocationHandler handler = new ScreenshotableElementHandler(new LocatingElementHandler(locator), screenshotable);
		Select proxy = ElementInstanceFactory.createSelectInstance(fieldType,
				(WebElement) Proxy.newProxyInstance(loader, new Class[] { WebElement.class }, handler));
		return proxy;
	}

	@SuppressWarnings("unchecked")
	protected <T extends Element> List<T> proxyForListLocator(ClassLoader loader, ElementLocator locator, Class<T> fieldType) {
		InvocationHandler handler = new ElementListProxyHandler<T>(fieldType, locator);
		return (List<T>) Proxy.newProxyInstance(loader, new Class[]{List.class}, handler);
	}

	protected <T extends BlockElement> T proxyForBlockLocator(ClassLoader loader, ElementLocator locator, Class<T> fieldType) {
		InvocationHandler handler = new ScreenshotableElementHandler(new LocatingElementHandler(locator), screenshotable);
		WebElement webElementToWrap = (WebElement) Proxy.newProxyInstance(loader, new Class[] { WebElement.class, WrapsElement.class,
				Locatable.class }, handler);
		T proxy = ElementInstanceFactory.createBlockElementInstance(fieldType, webElementToWrap);
		PredefindLocatorFieldProcessor fieldProcessor = new PredefindLocatorFieldProcessor();
		fieldProcessor.setPredifinedLocatorFactory(new XmlDefinedLocatorFactory());
		DefinedLocatorFieldProcessor definedLocatorProcessor = new DefinedLocatorFieldProcessor();
		// Recursively initialize elements inside block
		ScreenFactory.initElements(new PredefinedElementLocatorFactory(webElementToWrap, fieldProcessor, definedLocatorProcessor),
				screenshotable, proxy);
		return proxy;
	}
	@SuppressWarnings("unchecked")
	protected <T extends BlockElement> List<T> proxyForBlockListLocator(ClassLoader loader, ElementLocator locator, Class<T> fieldType) {
		PredefindLocatorFieldProcessor fieldProcessor = new PredefindLocatorFieldProcessor();
		fieldProcessor.setPredifinedLocatorFactory(new XmlDefinedLocatorFactory());
		DefinedLocatorFieldProcessor definedLocatorProcessor = new DefinedLocatorFieldProcessor();
		InvocationHandler handler = new BlockElementListProxyHandler<T>(fieldType,locator, screenshotable,fieldProcessor, definedLocatorProcessor );
		return (List<T>) Proxy.newProxyInstance(loader, new Class[] { List.class }, handler);
	}
}
