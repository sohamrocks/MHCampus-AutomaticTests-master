package com.mcgraw.test.automation.framework.selenium2.ui.proxyhandlers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.openqa.selenium.remote.UnreachableBrowserException;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.Screenshotable;

/**
 * Invocation handler delegate<br>
 * If some WebDriver exception occurs, catches, makes screenshoot, and throws
 * higher
 * 
 * @author Andrei Varabyeu
 * 
 */
public class ScreenshotableElementHandler extends InvocationHandlerDecorator {

	private Screenshotable screenshotable;

	public ScreenshotableElementHandler(InvocationHandler handler, Screenshotable screenshootable) {
		super(handler);
		this.screenshotable = screenshootable;
	}

	@Override
	public Object invoke(Object object, Method method, Object[] objects)
			throws Throwable {
		try {
			return super.invoke(object, method, objects);
		} catch (InvocationTargetException e) {
			try {
				if (!Browser.DISABLE_LOGGER_WARNINGS) {
					Logger.error("Making screenshoot for error '" + e.getMessage() + "'");
					screenshotable.makeScreenshot();
				}
			} catch (Exception ex) {
				Logger.error("Unable to get screenshoot after error: "
						+ e.getTargetException().getClass().getSimpleName());
				// do nothing
			}
			throw e.getTargetException();
		} catch (UnreachableBrowserException e) {
			Logger.warn("Browser is unreachable");
			throw e;
		} catch (Exception e) {
			if (!Browser.DISABLE_LOGGER_WARNINGS) {
				Logger.error("Making screenshoot for error '" + e.getMessage() + "'");
				screenshotable.makeScreenshot();
			}
			throw e;
		}
	}
}
