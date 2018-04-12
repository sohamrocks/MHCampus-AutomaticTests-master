package com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver.factory;

import org.browsermob.core.har.Har;
import org.browsermob.proxy.ProxyServer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.mcgraw.test.automation.framework.core.common.remote_access.NetworkUtils;
import com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver.WebDriverFactory;

/**
 * Proxied WebDriver wrapper
 *
 * @author Andrei Varabyeu
 *
 */
public class ProxyDriverFactory implements WebDriverFactory {

	private WebDriverFactory delegate;

	private ProxyServer server;

	public ProxyDriverFactory(WebDriverFactory delegate) {
		this.delegate = delegate;
		// start the proxy
		server = new ProxyServer(NetworkUtils.findFreePort());
		try {
			server.start();
		} catch (Exception e) {
			throw new WebDriverException(
					"Unable to start proxy server instance", e);
		}
	}

	@Override
	public WebDriver createWebDriver() {
		DesiredCapabilities capabilities = null;
		return createWebDriver(capabilities);
	}

	public void newHar(String name) {
		server.newHar(name);
	}

	@Override
	public WebDriver createWebDriver(DesiredCapabilities capabilities) {
		try {

			// captures the moouse movements and navigations
			server.setCaptureHeaders(true);
			server.setCaptureContent(true);

			// get the Selenium proxy object
			org.openqa.selenium.Proxy proxy = server.seleniumProxy();

			// configure it as a desired capability
			if (null == capabilities) {
				capabilities = new DesiredCapabilities();
			}
			capabilities.setCapability(CapabilityType.PROXY, proxy);
			return delegate.createWebDriver(capabilities);
		} catch (Exception e) {
			throw new WebDriverException(
					"Unable to create proxied WebDriverInstance instance");
		}
	}

	public Har getHar() {
		return server.getHar();
	}

	public void stop() {
		try {
			server.stop();
		} catch (Exception e) {
			throw new WebDriverException(
					"Unable to stop proxy server instance", e);
		}
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
