package com.mcgraw.test.automation.framework.selenium2.ui.proxyhandlers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import com.mcgraw.test.automation.framework.selenium2.ui.ElementInstanceFactory;
import com.mcgraw.test.automation.framework.selenium2.ui.ScreenFactory;
import com.mcgraw.test.automation.framework.selenium2.ui.Screenshotable;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.BlockElement;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.IFieldProcessor;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.selenium_impl.PredefinedElementLocatorFactory;

public class BlockElementListProxyHandler<T extends BlockElement> implements InvocationHandler {

	private final Class<T> blockElementClass;
	private final ElementLocator locator;
	private final Screenshotable screenshotable;
	private final IFieldProcessor[] annotationProcessors;

	public BlockElementListProxyHandler(Class<T> blockElementClass, ElementLocator locator, Screenshotable screenshotable,
			IFieldProcessor... annotationProcessors) {
		this.blockElementClass = blockElementClass;
		this.locator = locator;
		this.screenshotable = screenshotable;
		this.annotationProcessors = annotationProcessors;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {

		List<WebElement> webElements = locator.findElements();

		List<T> blockElements = new ArrayList<T>(webElements.size());
		for (WebElement element : webElements) {
			T blockElement = ElementInstanceFactory.createBlockElementInstance(blockElementClass, element);
			ScreenFactory.initElements(new PredefinedElementLocatorFactory(element, annotationProcessors), screenshotable, blockElement);
			blockElements.add(blockElement);
		}

		try {
			return method.invoke(blockElements, objects);
		} catch (InvocationTargetException e) {
			// Unwrap the underlying exception
			throw e.getCause();
		}
	}

}
