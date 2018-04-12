package com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver.factory;

/**
 * 
 * @author Andrei Varabyeu
 * 
 */
public enum WebDriverConfiguration {

	// @formatter:off
	DRIVER_TYPE("driverType"), 
	JS_ENABLED("javaScriptEnabled"), 
	SELENIUM_HOST("seleniumHostAddress"), 
	SELENIUM_PORT("seleniumPort");
	// @formatter:on

	private String propertyName;

	private WebDriverConfiguration(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}

	public static WebDriverConfiguration getByPropertyName(String propertyName) {
		for (WebDriverConfiguration configuration : WebDriverConfiguration
				.values()) {
			if (configuration.getPropertyName().equals(propertyName)) {
				return configuration;
			}
		}
		// TODO add exception here
		throw new RuntimeException("");
	}

}
