package com.mcgraw.test.automation.framework.selenium2.ui;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocatorFieldProcessor;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.PredefindLocatorFieldProcessor;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.factory.impl.XmlDefinedLocatorFactory;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.selenium_impl.PredefinedElementLocatorFactory;

/**
 * Screen factory
 * 
 * @author Andrei Varabyeu
 * 
 */
public class ScreenFactory {
/**
   * Instantiate an instance of the given class, and set a lazy proxy for each of the WebElement fields that have been
   * declared, assuming that the field name is also the HTML element's "id" or "name". This means that for the class:
   *
   * <code>
   * public class Screen {
   *     private WebElement submit;
   * }
   * </code>
   *
   * there will be an element that can be located using the xpath expression "//*[@id='submit']" or
   * "//*[@name='submit']"
   *
   * By default, the element is looked up each and every time a method is called upon it. To change this behaviour,
   * simply annnotate the field with the {@link @com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.CachedLocator}. To
   * change how the element is located, use the {@link
   * @com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.PredefinedLocator} annotation.
   *
   * This method will attempt to instantiate the class given to it, preferably using a constructor which takes a
   * WebDriver instance as its only argument or falling back on a no-arg constructor. An exception will be thrown if the
   * class cannot be instantiated.
   *
   * @see @com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.PredefinedLocator}
   * @see @com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.CachedLocator
   * @param Browser The driver wrapper that will be used to look up the elements
   * @param pageClassToProxy A class which will be initialised.
   * @param params Additional parameters if any for initialize.
   * @return An instantiated instance of the class with WebElement fields proxied
   */
	public static <T extends Screen> T initElements(Browser browser, Class<T> pageClassToProxy, Object... params) {
		T page = instantiatePage(browser, pageClassToProxy, params);
		initElements(browser, page);
		return page;
	}

	/**
	 * As {@link
	 * com.mcgraw.test.automation.framework.selenium2.ui.ScreenFactory.initElements(WebDriver,
	 * Class<T>)} but will only replace the fields of an already instantiated
	 * Page Object.
	 * 
	 * @param driver
	 *            The driver that will be used to look up the elements
	 * @param page
	 *            The object with WebElement fields that should be proxied.
	 */
	public static void initElements(Browser browser, Object page) {
		final Browser driverRef = browser;
		PredefindLocatorFieldProcessor fieldProcessor = new PredefindLocatorFieldProcessor();
		fieldProcessor.setPredifinedLocatorFactory(new XmlDefinedLocatorFactory());
		DefinedLocatorFieldProcessor definedLocatorProcessor = new DefinedLocatorFieldProcessor();

		initElements(new PredefinedElementLocatorFactory(driverRef, fieldProcessor, definedLocatorProcessor), driverRef, page);
	}

	/**
	 * Similar to the other "initElements" methods, but takes an
	 * {@link ElementLocatorFactory} which is used for providing the mechanism
	 * for fniding elements. If the ElementLocatorFactory returns null then the
	 * field won't be decorated.
	 * 
	 * @param factory
	 *            The factory to use
	 * @param page
	 *            The object to decorate the fields of
	 */
	public static void initElements(ElementLocatorFactory factory, Screenshotable screenshotable, Object page) {
		final ElementLocatorFactory factoryRef = factory;
		initElements(new ScreenFieldDecorator(factoryRef, screenshotable), page);
	}

	/**
	 * Similar to the other "initElements" methods, but takes an
	 * {@link FieldDecorator} which is used for decorating each of the fields.
	 * 
	 * @param decorator
	 *            the decorator to use
	 * @param page
	 *            The object to decorate the fields of
	 */
	public static void initElements(FieldDecorator decorator, Object page) {
		Class<?> proxyIn = page.getClass();
		while (proxyIn != Object.class) {
			proxyFields(decorator, page, proxyIn);
			proxyIn = proxyIn.getSuperclass();
		}
	}

	private static void proxyFields(FieldDecorator decorator, Object page, Class<?> proxyIn) {
		Field[] fields = proxyIn.getDeclaredFields();
		for (Field field : fields) {
			Object value = decorator.decorate(page.getClass().getClassLoader(), field);
			if (value != null) {
				try {
					field.setAccessible(true);
					field.set(page, field.getType().cast(value));
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	private static <T> T instantiatePage(Browser browser, Class<T> pageClassToProxy, Object... additionalScreenParams) {
		try {
			try {
				if (additionalScreenParams.length == 0) {
					Constructor<T> constructor = pageClassToProxy.getConstructor(Browser.class);
					return constructor.newInstance(browser);
				}
				//process classes and instances for screens with parameters
				Class<?>[] classes = new Class<?>[additionalScreenParams.length + 1];
				Object[] paramArrayOfObjectInstances = new Object[additionalScreenParams.length + 1];
				classes[0]=Browser.class;
				paramArrayOfObjectInstances[0]=browser;
				
				for (int i = 1; i < additionalScreenParams.length+1; i++) {
					classes[i] = additionalScreenParams[i-1].getClass();
					paramArrayOfObjectInstances[i]=additionalScreenParams[i-1];
				}
				Constructor<T> constructor = pageClassToProxy.getConstructor(classes);				
				return constructor.newInstance(paramArrayOfObjectInstances);
			} catch (NoSuchMethodException e) {
				return pageClassToProxy.newInstance();
			}
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}
