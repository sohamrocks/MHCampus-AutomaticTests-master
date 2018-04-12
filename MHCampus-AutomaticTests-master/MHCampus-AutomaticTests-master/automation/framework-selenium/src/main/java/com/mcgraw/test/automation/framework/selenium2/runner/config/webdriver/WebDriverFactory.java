package com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Factory for WebDriver instances
 *
 * @author Andrei Varabyeu
 *
 */
public interface WebDriverFactory {
	/**
	 * Creates new WebDriver instance
	 *
	 * @return - WebDriver
	 */
	WebDriver createWebDriver();

	/**
	 * Create WebDriver instance with additional capabilities
	 *
	 * @param capabilities
	 * @return - WebDriver
	 */
	WebDriver createWebDriver(DesiredCapabilities capabilities);
	
	/**
	 * Create WebDriver instance using path to folder with current web driver
	 *
	 * @param path to folder
	 * @return - WebDriver
	 */
	WebDriver createWebDriver(String path);
	
	/**
	 * Create WebDriver instance with additional capabilities using path to folder with current web driver
	 *
	 * @param capabilities, path to folder
	 * @return - WebDriver
	 */
	WebDriver createWebDriver(DesiredCapabilities capabilities, String path);

}
