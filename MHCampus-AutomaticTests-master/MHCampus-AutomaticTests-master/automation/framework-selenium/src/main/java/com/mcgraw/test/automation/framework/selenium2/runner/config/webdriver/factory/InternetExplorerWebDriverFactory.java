/**
 *
 */
package com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver.factory;

import java.io.File;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver.WebDriverFactory;

/**
 * @author Andrei Varabyeu
 *
 */
public class InternetExplorerWebDriverFactory implements WebDriverFactory {

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mcgraw.test.automation.framework.selenium2.runner.config.factory.WebDriverFactory#
	 * createWebDriver()
	 */
	
	@Override
	public WebDriver createWebDriver(String path) {
		
		File ieDriverPath = new File(path);
		
		System.setProperty("webdriver.ie.driver", ieDriverPath.getAbsolutePath());
		System.setProperty("webdriver.ie.driver.logfile", "C:/work/ielog.txt");
		System.setProperty("webdriver.ie.driver.loglevel", "DEBUG");
		DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
		ieCapabilities.setCapability("ignoreZoomSetting", true);
		ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
		ieCapabilities.setCapability("enablePersistentHover", true);
		ieCapabilities.setCapability("ie.ensureCleanSession", true);
		ieCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,  UnexpectedAlertBehaviour.ACCEPT);
//		ieCapabilities.setCapability("nativeEvents", false);  // to allow click on links
//		ieCapabilities.setCapability("requireWindowFocus", true);
		InternetExplorerDriver ieDriver = new InternetExplorerDriver(ieCapabilities);
		
		return ieDriver;		
	}

	@Override
	public WebDriver createWebDriver(DesiredCapabilities capabilities,	String path) {
		
        File ieDriverPath = new File(path);
		
		System.setProperty("webdriver.ie.driver", ieDriverPath.getAbsolutePath());
		InternetExplorerDriver ieDriver = new InternetExplorerDriver(capabilities);
		
		return ieDriver;	
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
