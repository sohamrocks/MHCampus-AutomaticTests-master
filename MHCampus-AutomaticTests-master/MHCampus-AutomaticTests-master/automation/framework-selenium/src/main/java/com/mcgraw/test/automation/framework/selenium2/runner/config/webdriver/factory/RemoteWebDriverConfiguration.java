package com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver.factory;

/**
 * Remote WebDriver Configuration
 *
 * @author Andrei Varabyeu
 *
 */
public class RemoteWebDriverConfiguration {

	/**
	 * @param seleniumHost
	 * @param seleniumPort
	 */
	public RemoteWebDriverConfiguration(String seleniumHost, String seleniumPort) {
		super();
		this.seleniumHost = seleniumHost;
		this.seleniumPort = seleniumPort;
	}

	private String seleniumHost;

	private String seleniumPort;

	/**
	 * @return the seleniumHost
	 */
	public String getSeleniumHost() {
		return seleniumHost;
	}

	/**
	 * @param seleniumHost
	 *            the seleniumHost to set
	 */
	public void setSeleniumHost(String seleniumHost) {
		this.seleniumHost = seleniumHost;
	}

	public String getSeleniumPort() {
		return seleniumPort;
	}

	public void setSeleniumPort(String seleniumPort) {
		this.seleniumPort = seleniumPort;
	}
}
