/**
 *
 */
package com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver.factory;

import java.io.File;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver.WebDriverFactory;

/**
 * @author Elvira Polyakov
 *
 */
public class ChromeWebDriverFactory implements WebDriverFactory {

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mcgraw.test.automation.framework.selenium2.runner.config.factory.WebDriverFactory#
	 * createWebDriver()
	 */
	
	@Override
	public WebDriver createWebDriver(String path) {
		
		File chromeDriver = new File(path);
		
		System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
	    options.addArguments("test-type");	    
	    options.addArguments(Arrays.asList("allow-running-insecure-content", "ignore-certificate-errors"));	    
	    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
	    
	    return new ChromeDriver(capabilities);
	}

	@Override
	public WebDriver createWebDriver(DesiredCapabilities capabilities, String path) {
		
		File chromeDriver = new File(path);
		
		System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
		ChromeOptions options = new ChromeOptions();
	    options.addArguments("test-type");
	    options.addArguments(Arrays.asList("allow-running-insecure-content", "ignore-certificate-errors"));
	    capabilities.setCapability(ChromeOptions.CAPABILITY, options);

	    return new ChromeDriver(capabilities);
	}

	@Override
	public WebDriver createWebDriver() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebDriver createWebDriver(DesiredCapabilities capabilities) {
		// TODO Auto-generated method stub
		return null;
	}

}
