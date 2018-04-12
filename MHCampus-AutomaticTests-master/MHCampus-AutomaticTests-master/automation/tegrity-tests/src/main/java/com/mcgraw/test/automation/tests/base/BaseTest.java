package com.mcgraw.test.automation.tests.base;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.ui.applications.TegrityInstanceApplication;

@ContextConfiguration(locations = { "classpath:spring/general-context.xml" }, initializers = MhCampusTestsInitializer.class)
public class BaseTest extends AbstractTestNGSpringContextTests {

	@Autowired
	protected Browser browser;

	@Value("${browser.webdriver.type}")
	public String webdriverName;
	
	@Value("${browser.webdriver.path}")
	public String webdriverPath;

	@Autowired
	protected TegrityInstanceApplication tegrityInstanceApplication;
	
	@BeforeClass(description = "Preparing WebDriver to run test")
	public void prepareTest() {

		Logger.info("--------------------------------------------------------------------------------------------------------------------");
		browser.createBrowser(webdriverName, webdriverPath);
		Logger.info("STARTING TEST CLASS " + this.getClass().getName());
		browser.manage().timeouts().setScriptTimeout(1L, TimeUnit.MINUTES);
		browser.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		browser.manage().window().maximize();
	}

	@AfterMethod(description = "Making screenshot after each test method")
	public void makeScreenshot() {
		// Guarantees we have screenshot after test assert failures and
		// exceptions
		// occurred during test method execution
		browser.makeScreenshot();
	}

	@AfterClass(description = "Close WebDriver after tests", alwaysRun=true)
	public void closeAllThreads() {
		browser.close();
		browser.quit();
		Logger.info("FINISHING TEST CLASS " + this.getClass().getName());
	}
}
