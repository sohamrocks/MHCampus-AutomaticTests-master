package com.mcgraw.test.automation.framework.selenium2.ui;

/**
 * {@link org.openqa.selenium.TakesScreenshot} replacement<br>
 * Doesn't care about screenshoot format, implementation should defined it by
 * yourself
 *
 * @author Andrei Varabyeu
 *
 */
public interface Screenshotable {
	void makeScreenshot();
}
