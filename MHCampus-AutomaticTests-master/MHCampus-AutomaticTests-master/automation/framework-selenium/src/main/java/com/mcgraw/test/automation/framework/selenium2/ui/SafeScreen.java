/**
 *
 */
package com.mcgraw.test.automation.framework.selenium2.ui;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Predicate;
import com.mcgraw.test.automation.framework.core.timing.SmartWait;
import com.mcgraw.test.automation.framework.selenium2.ui.exception.ScreenIsNotOpenedException;

/**
 * Screen with opening check
 *
 * @author Andrei Varabyeu
 *
 */
abstract public class SafeScreen extends Screen {

	public SafeScreen(final Browser browser) {
		super(browser);

		new SmartWait<Browser>(browser) {
			protected RuntimeException timeoutException(String message,
					Throwable lastException) {
				browser.makeScreenshot();
				throw new ScreenIsNotOpenedException(getErrorMessage(),
						lastException);
			}
		}.describe("Waiting for '" + this.getClass() + "' screen")
				.pollingEvery(1l, TimeUnit.SECONDS)
				.withTimeout(5, TimeUnit.SECONDS)
				.until(new Predicate<Browser>() {

					@Override
					public boolean apply(Browser arg0) {
						return isPageOpened(browser);
					}
				});

	}

	private String getErrorMessage() {
		return "Screen '" + this.getClass() + "' is not opened";
	}

	abstract protected boolean isPageOpened(Browser browser);

}
