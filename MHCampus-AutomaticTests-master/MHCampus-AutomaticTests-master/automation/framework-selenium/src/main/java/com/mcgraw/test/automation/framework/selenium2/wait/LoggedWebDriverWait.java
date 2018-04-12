package com.mcgraw.test.automation.framework.selenium2.wait;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.SystemClock;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mcgraw.test.automation.framework.core.timing.Log4jFlushable;

/**
 * {@link WebDriverWait} replacement.<br>
 * Adds log4j logging of attempts and passed seconds
 *
 * @author Andrei Varabyeu
 *
 */
public class LoggedWebDriverWait extends WebDriverWait {

	public LoggedWebDriverWait(WebDriver driver, long timeOutInSeconds) {
		super(driver, new SystemClock(), new LoggedSleeper(
				new Log4jFlushable()), timeOutInSeconds, DEFAULT_SLEEP_TIMEOUT);
	}

	public LoggedWebDriverWait(WebDriver driver, long timeOutInSeconds,
			long sleepInMillis) {
		super(driver, new SystemClock(), new LoggedSleeper(
				new Log4jFlushable()), timeOutInSeconds, sleepInMillis);
	}

}
