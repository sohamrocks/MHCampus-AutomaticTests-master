package com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver.factory;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.mcgraw.test.automation.framework.core.exception.FirefixProfileNotFoundException;
import com.mcgraw.test.automation.framework.selenium2.configuration.GlobalConfiguration;
import com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver.WebDriverFactory;
import com.mcgraw.test.automation.framework.selenium2.ui.ConvertFirefoxDriver;

/**
 * Factory implementation for Firefox WebDriver
 *
 * @author Andrei Varabyeu
 *
 */
public class FirefoxWebDriverFactory implements WebDriverFactory {
	/** Path to Mozilla Firefox profile */
	private FirefoxProfile firefoxProfile;

	/** Path to Mozilla Firefox binary file */
	private FirefoxBinary firefoxBinary;

	/**
	 * Creates FirefoxDriver using profile and binary path
	 *
	 * @param pathToProfile
	 *            - path to Firefox profile
	 * @param pathToBinary
	 *            - path to Firefox binary file
	 */
	public FirefoxWebDriverFactory(String pathToProfile, String pathToBinary) {
		File profileDir = new File(pathToProfile);
		if (!profileDir.exists()) {
			throw new FirefixProfileNotFoundException(
					"Cannot find Selenium profile using path '" + pathToProfile
							+ "'");
		}

		File binaryFile = new File(pathToBinary);
		if (!binaryFile.exists()) {
			throw new FirefixProfileNotFoundException(
					"Cannot find Selenium binary file using path '"
							+ pathToBinary + "'");
		}
		this.firefoxProfile = new FirefoxProfile(profileDir);
		this.firefoxBinary = new FirefoxBinary(binaryFile);
	}

	/**
	 * Creates FirefoxDriver using binary path
	 *
	 * @param pathToBinary
	 *            - path to Firefox binary file
	 */
	public FirefoxWebDriverFactory(String pathToBinary) {
		File binaryFile = new File(pathToBinary);
		if (!binaryFile.exists()) {
			throw new FirefixProfileNotFoundException(
					"Cannot find Selenium binary file using path '"
							+ pathToBinary + "'");
		}
		this.firefoxProfile = generateDefaultProfile();
		this.firefoxBinary = new FirefoxBinary(binaryFile);
	}

	/**
	 * Creates default FirefoxDriver
	 */
	public FirefoxWebDriverFactory() {
		this.firefoxProfile = generateDefaultProfile();
		this.firefoxProfile.setEnableNativeEvents(true);
		this.firefoxProfile.setAssumeUntrustedCertificateIssuer(false);
		this.firefoxProfile.setPreference("security.mixed_content.block_active_content", false);
		this.firefoxProfile.setPreference("security.mixed_content.block_display_content", true);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mcgraw.test.automation.framework.selenium2.runner.config.factory.WebDriverFactory#
	 * createWebDriver()
	 */
	@Override
	public synchronized FirefoxDriver createWebDriver() {
		DesiredCapabilities caps = DesiredCapabilities.firefox();
	    caps.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true); 
	    
	    /* Old approach for creating firefoxWebDriver
		FirefoxDriver firefoxWebDriver = new FirefoxDriver(firefoxBinary,
				firefoxProfile, caps);*/
	    
	    // New approach for creating firefoxWebDriver
	    ConvertFirefoxDriver firefoxWebDriver = new ConvertFirefoxDriver(firefoxBinary,
				firefoxProfile, caps);
	    
		((RemoteWebDriver) firefoxWebDriver).setLogLevel(Level.ALL);
		return firefoxWebDriver;
	}

	@Override
	public synchronized FirefoxDriver createWebDriver(
			DesiredCapabilities capabilities) {
		FirefoxDriver firefoxWebDriver = new FirefoxDriver(firefoxBinary,
				firefoxProfile, capabilities);
		((RemoteWebDriver) firefoxWebDriver).setLogLevel(Level.ALL);
		return firefoxWebDriver;
	}
	
	private FirefoxProfile generateDefaultProfile() {		
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		firefoxProfile.setPreference("browser.download.folderList", 2);
		firefoxProfile.setPreference(
				"browser.download.manager.showWhenStarting", false);
		firefoxProfile.setPreference("browser.download.dir",
				GlobalConfiguration.getInstance().getDownloadsDir());
		firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				StringUtils.join(MIME_TYPES, ','));
		firefoxProfile.setPreference("pdfjs.disabled", true);

		return firefoxProfile;
	}

	private static final Set<String> MIME_TYPES = new HashSet<String>() {

		private static final long serialVersionUID = 4007757631290226897L;

		{
			add("application/pdf");

			add("image/png");
			add("image/jpeg");

			// TODO CIDM-1940 workaround
			// add("\"image/svg+xml\"");

			add("text/csv");
		}
	};

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
