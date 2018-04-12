package com.mcgraw.test.automation.framework.selenium2.ui.proxyhandlers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import com.mcgraw.test.automation.framework.selenium2.ui.ElementInstanceFactory;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;

public class ElementListProxyHandler<T extends Element> implements InvocationHandler {

	private final Class<T> elementClass;
	private final ElementLocator locator;

	public ElementListProxyHandler(Class<T> elementClass, ElementLocator locator) {
		this.elementClass = elementClass;
		this.locator = locator;
	}

	@Override
	public Object invoke(Object object, Method method, Object[] objects) throws Throwable {

		List<WebElement> elements = locator.findElements();

		List<T> proxiedElements = new ArrayList<T>();
		for (WebElement webElement : elements) {
			proxiedElements.add(ElementInstanceFactory.createElementInstance(elementClass, webElement));
		}

		try {
			return method.invoke(proxiedElements, objects);
		} catch (InvocationTargetException e) {
			// Unwrap the underlying exception
			throw e.getCause();
		}
	}

}
