package com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver.factory;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.mcgraw.test.automation.framework.core.exception.UnsupportedDriverTypeException;
import com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver.WebDriverFactory;
import com.mcgraw.test.automation.framework.selenium2.ui.WebDriverType;

/**
 * Factory implementation for RemoteWebDriver
 *
 * @author Andrei Varabyeu
 *
 */
public class RemoteWebDriverFactory implements WebDriverFactory {

	/** Template for Selenium 2 server URL */
	private static final String SERVER_URL_TEMPLATE = "http://%1$s:%2$s/wd/hub";

	/** Browser capabilities */
	private DesiredCapabilities desiredCapabilities;

	/** URL to remote Selenium server instance */
	private URL serverUrl;

	/**
	 * Build browser capabilities by Browser type
	 *
	 * @param remoteSeleniumConfiguration
	 * @param webDriverType
	 */
	public RemoteWebDriverFactory(
			RemoteWebDriverConfiguration remoteSeleniumConfiguration,
			WebDriverType webDriverType) {
		this.serverUrl = buildServerUrl(
				remoteSeleniumConfiguration.getSeleniumHost(),
				remoteSeleniumConfiguration.getSeleniumPort());
		this.desiredCapabilities = getCapabalities(webDriverType);
	}

	/**
	 * Build browser capabilities by Browser type
	 *
	 * @param remoteSeleniumConfiguration
	 * @param webDriverType
	 */
	public RemoteWebDriverFactory(String serverUrl,
			DesiredCapabilities desiredCapabilities) {
		try {
			this.serverUrl = new URL(serverUrl);
			this.desiredCapabilities = desiredCapabilities;
		} catch (MalformedURLException e) {
			throw new RuntimeException("Cannot build URL to Selenium Server", e);
		}

	}

	/**
	 * Should be provided browser capabilities and Remote Configuration for
	 * selenium
	 *
	 * @param remoteSeleniumConfiguration
	 * @param desiredCapabilities
	 */
	public RemoteWebDriverFactory(
			RemoteWebDriverConfiguration remoteSeleniumConfiguration,
			DesiredCapabilities desiredCapabilities) {
		this.desiredCapabilities = desiredCapabilities;
		serverUrl = buildServerUrl(
				remoteSeleniumConfiguration.getSeleniumHost(),
				remoteSeleniumConfiguration.getSeleniumPort());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mcgraw.test.automation.framework.selenium2.runner.config.factory.WebDriverFactory#
	 * createWebDriver()
	 */
	@Override
	public synchronized RemoteWebDriver createWebDriver() {
		RemoteWebDriver currentDriver = new RemoteWebDriver(serverUrl,
				desiredCapabilities);
		return currentDriver;
	}

	private DesiredCapabilities getCapabalities(WebDriverType webDriverType) {
		DesiredCapabilities desiredCapabilities;
		switch (webDriverType) {
		case FIREFOX:
			desiredCapabilities = DesiredCapabilities.firefox();
			break;
		case CHROME:
			desiredCapabilities = DesiredCapabilities.chrome();
			break;
		case EXPLORER:
			desiredCapabilities = DesiredCapabilities.internetExplorer();
			break;
		case HTML_UNIT:
			desiredCapabilities = DesiredCapabilities.htmlUnit();
			break;
		default:
			throw new UnsupportedDriverTypeException(
					"Could find DesiredCapabilities for driver type"
							+ webDriverType);
		}
		return desiredCapabilities;
	}

	/**
	 * Build server URL for Selenium instance
	 *
	 * @param seleniumHost
	 * @param seleniumPort
	 * @return
	 */
	private URL buildServerUrl(String seleniumHost, String seleniumPort) {
		try {
			URL serverUrl = new URL(String.format(SERVER_URL_TEMPLATE,
					seleniumHost, seleniumPort));
			return serverUrl;
		} catch (MalformedURLException e) {
			throw new RuntimeException("Cannot build URL to Selenium Server", e);
		}
	}

	@Override
	public WebDriver createWebDriver(DesiredCapabilities capabilities) {
		RemoteWebDriver currentDriver = new RemoteWebDriver(serverUrl,
				desiredCapabilities.merge(capabilities));
		return currentDriver;
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
