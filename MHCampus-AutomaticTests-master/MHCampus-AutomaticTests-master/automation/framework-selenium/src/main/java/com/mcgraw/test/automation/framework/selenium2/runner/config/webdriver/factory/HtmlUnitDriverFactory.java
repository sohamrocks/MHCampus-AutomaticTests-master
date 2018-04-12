/**
 *
 */
package com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver.WebDriverFactory;

/**
 * @author Andrei Varabyeu
 *
 */
public class HtmlUnitDriverFactory implements WebDriverFactory {

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mcgraw.test.automation.framework.selenium2.runner.config.factory.WebDriverFactory#
	 * createWebDriver()
	 */
	@Override
	public WebDriver createWebDriver() {
		BrowserVersion.setDefault(BrowserVersion.FIREFOX_38) ;
		return new HtmlUnitDriver(true);

	}

	@Override
	public WebDriver createWebDriver(DesiredCapabilities capabilities) {
		BrowserVersion.setDefault(BrowserVersion.FIREFOX_38);
		DesiredCapabilities ds = new DesiredCapabilities();
		ds.setJavascriptEnabled(true);
		return new HtmlUnitDriver(capabilities.merge(ds));
	}

	@Override
	public WebDriver createWebDriver(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebDriver createWebDriver(DesiredCapabilities capabilities,
			String path) {
		// TODO Auto-generated method stub
		return null;
	}

}