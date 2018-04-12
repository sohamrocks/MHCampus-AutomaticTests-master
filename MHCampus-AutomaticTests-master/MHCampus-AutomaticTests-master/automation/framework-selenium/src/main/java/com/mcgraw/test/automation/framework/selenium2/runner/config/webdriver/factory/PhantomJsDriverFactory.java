package com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.common.base.Preconditions;
import com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver.WebDriverFactory;

public class PhantomJsDriverFactory implements WebDriverFactory {

	private String phantomJsBinaryPath;

	public PhantomJsDriverFactory(String phantomJsBinaryPath) {
		Preconditions.checkNotNull(phantomJsBinaryPath);

		this.phantomJsBinaryPath = phantomJsBinaryPath;
	}

	@Override
	public WebDriver createWebDriver() {
		DesiredCapabilities capabilities = null;
		return createWebDriver(capabilities);
	}

	@Override
	public WebDriver createWebDriver(DesiredCapabilities capabilities) {
		if (capabilities == null) {
			capabilities = new DesiredCapabilities();
		}

		capabilities.setJavascriptEnabled(true);
		capabilities.setCapability("takesScreenshot", true);
		capabilities.setCapability(
				PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				phantomJsBinaryPath);

		// Disable "web-security", enable all possible "ssl-protocols" and
		// "ignore-ssl-errors" for PhantomJSDriver
		capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
				new String[] { "--web-security=false", "--ssl-protocol=any",
						"--ignore-ssl-errors=true" });

		return new PhantomJSDriver(capabilities);
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
