package com.mcgraw.test.automation.framework.selenium2.ui;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.desktop.DesktopMouse;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import com.mcgraw.test.automation.framework.core.common.AnnotationUtils;
import com.mcgraw.test.automation.framework.core.common.CommonUtils;
import com.mcgraw.test.automation.framework.core.common.ResourceUtils;
import com.mcgraw.test.automation.framework.core.exception.BrowserDownloadsException;
import com.mcgraw.test.automation.framework.core.exception.test.CommonTestRuntimeException;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.timing.SmartWait;
import com.mcgraw.test.automation.framework.core.timing.TimeoutException;
import com.mcgraw.test.automation.framework.core.timing.Waitable;
import com.mcgraw.test.automation.framework.selenium2.configuration.GlobalConfiguration;
import com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver.WebDriverFactory;
import com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver.factory.ChromeWebDriverFactory;
import com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver.factory.FirefoxWebDriverFactory;
import com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver.factory.InternetExplorerWebDriverFactory;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.exception.ScreenFrameNotFoundException;
import com.mcgraw.test.automation.framework.selenium2.ui.exception.ScreenIsNotOpenedException;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocatorFieldProcessor;

//To use RemoteWebDrive:
//import com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver.factory.RemoteWebDriverConfiguration;
//import com.mcgraw.test.automation.framework.selenium2.runner.config.webdriver.factory.RemoteWebDriverFactory;


/**
 * Wrapper for Selenium WebDriver instances
 * 
 * @author Andrei Varabyeu
 * 
 * 
 * 
 */
public class Browser implements WebDriver, JavascriptExecutor, TakesScreenshot, Screenshotable, WrapsDriver, HasInputDevices, SearchContext {
	
	/** Default WebDriver name */
	public static final String DEFAULT_DRIVER = "defaultDriver";

	public static long DEFAULT_WAIT_FOR_ELEMENT = 100;

	public static final int MAX_JAVASCRIPT_LENGTH_TO_LOG = 160;

	public static final int MAX_ARGUMENTS_LENGTH_TO_LOG = 200;

	private String IE_MOUSE_EVENT_SCRIPT_PATTERN = "arguments[0].fireEvent('on%s');";

	private String FF_CHROME_MOUSE_EVENT_SCRIPT_PATTERN = "var evt = document.createEvent('MouseEvents'); evt.initMouseEvent('%s',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null); arguments[0].dispatchEvent(evt);";
	/** disabling errors and warning while finding element */
	public static boolean DISABLE_LOGGER_WARNINGS = false;

	/** Thread-Safe Browser factory name */
	private static ThreadLocal<WebDriverFactory> defaultWebDriverFactory = new ThreadLocal<WebDriverFactory>();

	/** Selenium WebDriver */
	private WebDriver webDriver;

	/** Instance name */
	private String browserName;

	private static final String downloadsDirectory = GlobalConfiguration.getInstance().getDownloadsDir();

	private static Downloads downloads = new Downloads(downloadsDirectory);

	/** Instances cache */
	private static ThreadLocal<Map<String, Browser>> instancesCache = new ThreadLocal<Map<String, Browser>>() {
		@Override
		protected java.util.Map<String, Browser> initialValue() {
			return new HashMap<String, Browser>();
		};;

	};

	/**
	 * Cross-thread cache. Used only for closing browser instances after test
	 * execution
	 */
	private static List<Browser> browsersGarbage = new ArrayList<Browser>();

	/**
	 * @param webDriverFactory
	 *            the webDriverFactory to set
	 */
	public static synchronized void setDefaultWebDriverFactory(WebDriverFactory webDriverFactory) {
		if (!getDefaultWebBrowserFactory().equals(webDriverFactory)) {
			clearCache();
			Logger.debug("Change default browser factory to factory '" + webDriverFactory.getClass() + "'");
			defaultWebDriverFactory.set(webDriverFactory);
		}
	}

	private static void clearCache() {
		Map<String, Browser> instances = instancesCache.get();
		for (Entry<String, Browser> entry : instances.entrySet()) {
			Logger.debug("Quit browser with name '" + entry.getKey() + "'");
			entry.getValue().quit();
			browsersGarbage.remove(entry.getValue());
		}
		instances.clear();
	}

	/**
	 * !Be carefull! Clear all browser/webdriver instances for all threads!
	 */
	public static synchronized void quitAll() {
		clearCache();
		Logger.debug("Browsers in full cache " + browsersGarbage.size());
		for (Browser browser : browsersGarbage) {
			Logger.info("Closing browser with name " + browser.getBrowserName());
			browser.quit();
		}
	}

	public static WebDriverFactory getDefaultWebBrowserFactory() {
		return defaultWebDriverFactory.get();
	}

	/**
	 * Uses provided Web Driver Factory for producing instances
	 * 
	 * @param name
	 *            - Name of webDriver
	 */
	private Browser(String name, WebDriverFactory webDriverFactory) {
		Logger.info("Create browser instance with name: " + name);
	}
	
	public void createBrowser(String webdriverName, String webdriverPath) {
		this.browserName = webdriverName;
		Logger.info("Create browser instance with name '" + webdriverName + "' in Thread '" + Thread.currentThread().getName() + "', ID '"
				+ Thread.currentThread().getId() + "'");
		
		if(webdriverName.equals("chrome")){
			WebDriverFactory webDriverFactory = new ChromeWebDriverFactory();
			this.webDriver = webDriverFactory.createWebDriver(webdriverPath);
		}else if(webdriverName.equals("firefox")){
			WebDriverFactory webDriverFactory = new FirefoxWebDriverFactory();  
			this.webDriver = webDriverFactory.createWebDriver();
		}else if(webdriverName.equals("ie")){
			WebDriverFactory webDriverFactory = new InternetExplorerWebDriverFactory();  
			this.webDriver = webDriverFactory.createWebDriver(webdriverPath);
		}else{
			Logger.info("ERROR: the webDriver with name - " + webdriverName + " doesn't exist...");
		}
		
		//To use RemoteWebDrive:
		//WebDriverFactory webDriverFactory = new RemoteWebDriverFactory(new RemoteWebDriverConfiguration("10.221.1.107", "8020"), WebDriverType.FIREFOX);  
		//this.webDriver = webDriverFactory.createWebDriver();
		
		browsersGarbage.add(this);
	}
	
	/**
	 * Returns instance for specified name
	 * 
	 * @param name
	 * @param webDriverFactory
	 * @return
	 */
	public static Browser getInstance(String name, WebDriverFactory webDriverFactory) {
		Map<String, Browser> currentThreadInstancesCache = instancesCache.get();
		if (!currentThreadInstancesCache.containsKey(name)) {
			Browser webDriverWrapper = new Browser(name, webDriverFactory);
			currentThreadInstancesCache.put(name, webDriverWrapper);
			return webDriverWrapper;
		} else {
			Browser browser = currentThreadInstancesCache.get(name);

			/*
			 * We have to create new instance in case if browser is not
			 * reachable
			 */
			boolean isReachable = isBrowserReachable(browser);
			Logger.debug("Browser reachable state: " + isReachable);
			if (!isReachable) {
				browser.quit();
				Browser webDriverWrapper = new Browser(name, webDriverFactory);
				currentThreadInstancesCache.put(name, webDriverWrapper);
				return webDriverWrapper;
			}
			return browser;
		}
	}

	/**
	 * Is browser reachable.
	 * 
	 * @param browser
	 * @return
	 */
	private static boolean isBrowserReachable(Browser browser) {
		if (browser.getWebDriver() instanceof RemoteWebDriver) {
			RemoteWebDriver remoteWd = (RemoteWebDriver) browser.getWebDriver();
			try {
				return !(null == remoteWd.getSessionId() || null == remoteWd.getWindowHandle());
			} catch (UnreachableBrowserException e) {
				return false;
			}
		}
		return true;

	}

	public static Browser getInstance(String name) {
		return getInstance(name, getDefaultWebBrowserFactory());
	}

	/**
	 * Returns default instance
	 * 
	 * @see om.mcgraw.test.automation.framework.selenium2.ui.Browser.DEFAULT_DRIVER
	 * 
	 * @return
	 */
	public static Browser getBrowser() {
		return getInstance(DEFAULT_DRIVER);
	}

	private PageRelativeUrl checkPageRelativeUrlAnnotation(Class<? extends Screen> screen) {
		PageRelativeUrl annotation = AnnotationUtils.getAnnotation(screen, PageRelativeUrl.class, false);
		if (null == annotation) {
			throw new CommonTestRuntimeException("You should annotate screen '" + screen + "' with 'PageRelativeUrl' annotation");
		}
		return annotation;
	}

	public String getScreenRelativeUrl(Class<? extends Screen> screen) {
		PageRelativeUrl annotation = checkPageRelativeUrlAnnotation(screen);
		if (!annotation.relative()) {
			throw new CommonTestRuntimeException(
					"Can't get relative url of "
							+ screen
							+ ", because 'relative()' attribute of its 'PageRelativeUrl' annotation is set to 'false'. Use getScreenAbsoluteUrl method or edit the 'PageRelativeUrl' annotation");
		}
		return annotation.value();
	}

	public String getScreenAbsoluteUrl(Class<? extends Screen> screen) {
		PageRelativeUrl annotation = checkPageRelativeUrlAnnotation(screen);
		if (annotation.relative()) {
			throw new CommonTestRuntimeException(
					"Can't get absolute url of "
							+ screen
							+ ", because 'relative()' attribute of its 'PageRelativeUrl' annotation is set to 'true'. Use getScreenRelativeUrl method or edit the 'PageRelativeUrl' annotation");
		}
		return annotation.value();
	}

	/**
	 * Check if current page url contains PageRelativeUrl value
	 * 
	 * @param screen
	 * @return
	 */
	public boolean isCurrentlyOnPageUrl(Class<? extends Screen> screen) {
		return getCurrentUrl().contains(getScreenAbsoluteUrl(screen));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#get(java.lang.String)
	 */
	@Override
	public void get(String url) {
		Logger.info("Opening URL: " + url + ". Thread ID: " + Thread.currentThread().getId() + " Browser: " + this);
		webDriver.get(url);
	}

	public <T extends Screen> T openScreen(Class<T> screen) {
		PageRelativeUrl annotation = AnnotationUtils.getAnnotation(screen, PageRelativeUrl.class, false);
		if (null == annotation) {
			throw new CommonTestRuntimeException("You should annotate screen '" + screen + "' with 'PageRelativeUrl' annotation");
		}
		if (annotation.relative()) {
			throw new CommonTestRuntimeException(
					"Screen contains relative url and cannot be opened. Use com.mcgraw.test.automation.framework.selenium2.ui.Browser.openScreen(baseUrl, Class<T>)");
		}
		get(annotation.value());
		return waitForPage(screen);
	}

	public <T extends Screen> T openScreen(String baseUrl, Class<T> screen, String urlAppendix) {
		PageRelativeUrl annotation = AnnotationUtils.getAnnotation(screen, PageRelativeUrl.class, false);
		if (null == annotation) {
			throw new CommonTestRuntimeException("You should annotate screen '" + screen + "' with 'PageRelativeUrl' annotation");
		}
		get(baseUrl + annotation.value() + urlAppendix);
		return waitForPage(screen);
	}

	public <T extends Screen> T openScreen(String baseUrl, Class<T> screen) {
		return openScreen(baseUrl, screen, "");
	}

	public boolean isPageOpened(Class<? extends Screen> screen) {

		if (null != getPageFrameIdentificators(screen)) {
			this.switchTo().defaultContent();
			for (By frameLocator : getPageFrameIdentificators(screen)) {
				try {
					this.switchTo().frame(waitForElement(frameLocator, 50));
				} catch (TimeoutException e) {
					if (isFrameCheckCanBeSkipped(screen)) {
						this.switchTo().defaultContent();
					} else {
						throw new ScreenFrameNotFoundException("Frame with locator \"" + frameLocator + "\" not found for the screen "
								+ screen.getSimpleName());
					}
				}
			}

			for (By candidateLocator : getPageIdentificators(screen)) {
				if (isElementPresent(candidateLocator)) {
					this.switchTo().defaultContent();
					return true;
				}
			}

		} else {
			for (By candidateLocator : getPageIdentificators(screen)) {
				if (isElementPresent(candidateLocator)) {
					return true;
				}
			}

		}

		return false;
	}

	public <T extends Screen> T waitForPage(final Class<T> screen) {
		return waitForPage(screen, 70);
	}

	public <T extends Screen> T waitForPage(final Class<T> screen, long customTimeoutSec, Object... params) {
		Logger.debug("Waiting for " + screen.getSimpleName() + " page...");
		Logger.info("Waiting for " + screen.getSimpleName() + " page...");
		Waitable<Class<? extends Screen>> wait = new SmartWait<Class<? extends Screen>>(screen) {
			@Override
			protected RuntimeException timeoutException(String message, Throwable lastException) {
				makeScreenshot();
				sendMessage(screen.getSimpleName() + " was not opened!");
				throw new ScreenIsNotOpenedException(screen.getSimpleName() + " was not opened!");
			}
		}.withTimeout(customTimeoutSec, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		wait.until(new Function<Class<? extends Screen>, Boolean>() {
			@Override
			public Boolean apply(Class<? extends Screen> screen) {
				return isPageOpened(screen);
			}
		});
		return ScreenFactory.initElements(this, screen, params);
	}

	private void sendMessage(String screenName){

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String toSend = "UI Tests: " + screenName + " DATE: " + dateFormat.format(date);
		try{
			Element textArea = this.findElement(By.id("ErrorDialog_ErrorDialogWindow_C_UserText"));
			textArea.sendKeys(toSend);
			Element letUsKnowButton = this.findElement(By.xpath("//*[@id='ErrorDialog_ErrorDialogWindow_C']/div[2]/center/button[1]"));
			letUsKnowButton.click();
			makeScreenshot();
		}catch(Exception ex){
			// To do
		}
	}
	
	private Set<By> getPageIdentificators(Class<? extends Screen> screen) {
		DefinedLocatorFieldProcessor annProcessor = new DefinedLocatorFieldProcessor();
		if (!screen.isAnnotationPresent(PageIdentificator.class)) {
			throw new CommonTestRuntimeException("You should annotate screen class '" + screen.getSimpleName()
					+ "' with 'PageIdentificator' annotation");
		}
		return annProcessor.buildBy(screen);
	}

	private Set<By> getPageFrameIdentificators(Class<? extends Screen> screen) {
		PageFrameIdentificatorTypeProcessor frameProcessor = new PageFrameIdentificatorTypeProcessor();
		return frameProcessor.buildBy(screen);
	}

	private boolean isFrameCheckCanBeSkipped(Class<? extends Screen> screen) {
		PageFrameIdentificatorTypeProcessor frameProcessor = new PageFrameIdentificatorTypeProcessor();
		return frameProcessor.isFrameCheckCanBeSkipped(screen);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#getCurrentUrl()
	 */
	@Override
	public String getCurrentUrl() {
		return webDriver.getCurrentUrl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#getTitle()
	 */
	@Override
	public String getTitle() {
		return webDriver.getTitle();
	}

	/**
	 * Find all elements within the current page using the given mechanism.
	 * 
	 * @param by
	 *            The locating mechanism to use
	 * @return A list of all {@link Element}s, or an empty list if nothing
	 *         matches
	 * @see org.openqa.selenium.By
	 */
	@Override
	public List<WebElement> findElements(By by) {
		List<WebElement> elements = new ArrayList<WebElement>();
		for (WebElement webElement : webDriver.findElements(by)) {
			elements.add(new Element(webElement));
		}
		return elements;
	}

	/**
	 * Wait for all elements within the current page using the given mechanism.
	 * 
	 * @param by
	 * @param visibilityRequired
	 * @param timeOutInSeconds
	 * @return A list of all {@link Element}s, if the list is empty and
	 *         visibilityRequired = true, then the method will fail with
	 *         NosuchElementException, else return empty list
	 */
	public List<WebElement> waitForElements(By by, boolean visibilityRequired, long timeOutInSeconds) {
		Waitable<WebDriver> wait = new SmartWait<WebDriver>(webDriver) {
			@Override
			protected RuntimeException timeoutException(String message, Throwable lastException) {
				makeScreenshot();
				throw new TimeoutException(message, lastException);
			}
		}.describe("Waiting for elements with path: " + by).withTimeout(timeOutInSeconds, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(WebDriverException.class);

		return wait.until(WebDriverFunctions.findElements(by, visibilityRequired));
	}

	public List<WebElement> waitForElements(By by, long timeOutInSeconds) {
		return waitForElements(by, true, timeOutInSeconds);
	}

	public List<WebElement> waitForElements(By by) {
		return waitForElements(by, true, DEFAULT_WAIT_FOR_ELEMENT);
	}

	public List<WebElement> findNativeElements(By by) {
		return webDriver.findElements(by);
	}

	/**
	 * Find the first {@link Element} using the given method.
	 * 
	 * @param by
	 *            The locating mechanism
	 * @return The first matching element on the current page
	 * @throws NoSuchElementException
	 *             If no matching elements are found
	 */
	@Override
	public Element findElement(By by) {
		return new Element(webDriver.findElement(by));
	}

	public Element waitForElement(By by, boolean visibilityRequired, long timeOutInSeconds) {
		Waitable<WebDriver> wait = new SmartWait<WebDriver>(webDriver) {
			@Override
			protected RuntimeException timeoutException(String message, Throwable lastException) {
				makeScreenshot();
				throw new TimeoutException(message, lastException);
			}
		}.describe("Waiting for element with path: " + by).withTimeout(timeOutInSeconds, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(WebDriverException.class);

		return new Element(wait.until(WebDriverFunctions.findElement(by, visibilityRequired)));
	}

	public Element waitForElement(By by, boolean visibilityRequired) {
		return waitForElement(by, visibilityRequired, DEFAULT_WAIT_FOR_ELEMENT);
	}

	public Element waitForElement(By by) {
		Logger.info("Wait for element By: " + by);
		Element result = waitForElement(by, true, DEFAULT_WAIT_FOR_ELEMENT);
		return result;
	}

	public Element waitForElement(By by, long timeOutInSeconds) {
		Logger.info("Wait for element By: " + by);
		return waitForElement(by, true, timeOutInSeconds);
	}

	public Element waitForElementIsClickable(By by) {
		Logger.info("Wait for element is clickble By: " + by);
		Waitable<WebDriver> wait = new SmartWait<WebDriver>(webDriver) {
			@Override
			protected RuntimeException timeoutException(String message, Throwable lastException) {
				makeScreenshot();
				throw new TimeoutException(message, lastException);
			}
		}.describe("Waiting for element with path: " + by).withTimeout(DEFAULT_WAIT_FOR_ELEMENT, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(WebDriverException.class);

		return new Element(wait.until(ExpectedConditions.elementToBeClickable(by)));
	}

	public Element waitForElementPresent(Element element, boolean visibilityRequired, long timeOutInSeconds) {
		Browser.DISABLE_LOGGER_WARNINGS = true;
		Waitable<Element> wait = new SmartWait<Element>(element) {
			@Override
			protected RuntimeException timeoutException(String message, Throwable lastException) {
				makeScreenshot();
				throw new TimeoutException(message, lastException);
			}
		}.describe("Waiting for element...").withTimeout(timeOutInSeconds, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class, ElementNotVisibleException.class);

		try {
			wait.until(new Function<Element, Boolean>() {

				@Override
				public Boolean apply(Element element) {
					return element.isElementPresent() && element.isDisplayed();
				}
			});
			Browser.DISABLE_LOGGER_WARNINGS = false;
			return element;
		} catch (TimeoutException e) {
			Browser.DISABLE_LOGGER_WARNINGS = false;
			throw e;
		}
	}

	public Element waitForElementPresent(Element element) {
		return waitForElementPresent(element, true, 30);
	}

	public Element waitForElementPresent(Element element, long timeOutInSeconds) {
		return waitForElementPresent(element, true, timeOutInSeconds);
	}

	public boolean waitForText(final String textToWait, int secondsToWait) {
		Logger.debug("Waiting for '" + textToWait + "' text...");
		Browser.DISABLE_LOGGER_WARNINGS = true;
		Waitable<Browser> wait = new SmartWait<Browser>(this) {
			@Override
			protected RuntimeException timeoutException(String message, Throwable lastException) {
				throw new TimeoutException("Expected text didn't appear!", lastException);
			}
		}.withTimeout(secondsToWait, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		try {
			wait.until(new Function<Browser, Boolean>() {
				@Override
				public Boolean apply(Browser browser) {
					return browser.isElementPresent(By.xpath("//*[contains(text(),'" + textToWait + "')]"));
				}
			});
			Browser.DISABLE_LOGGER_WARNINGS = false;
			return true;
		} catch (TimeoutException e) {
			Browser.DISABLE_LOGGER_WARNINGS = false;
			return false;
		}
	}

	/**
	 * Stops current browser's thread for provided duration
	 * 
	 * @param millis
	 */
	public void pause(long millis) {
		try {
			Logger.info("Pause for '" + millis + "' millis");
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			Logger.info("Errors occured when executing pause() method");
		}
	}

	/**
	 * Stops current browser's thread for privided time unit and duration
	 * 
	 * @param time
	 * @param timeUnit
	 */
	public void pause(long time, TimeUnit timeUnit) {
		pause(timeUnit.toMillis(time));
	}

	/**
	 * Executes custom JavaScript
	 * 
	 * @param javaScriptLengthToLog
	 *            log message length
	 * @param javaScript
	 * @param args
	 * @return
	 */
	public Object executeScript(String javaScript, Object... args) {
		String stringifiedArgs = stringifyArgs(args);

		Logger.debug("Execution script '" + shortenString(javaScript, MAX_JAVASCRIPT_LENGTH_TO_LOG) + "' with args "
				+ shortenString(stringifiedArgs, MAX_ARGUMENTS_LENGTH_TO_LOG));
		Object result = ((JavascriptExecutor) this.getWebDriver()).executeScript(javaScript, args);
		Logger.debug("Script Execution result: " + result);
		return result;
	}

	private String shortenString(String initialString, int targetLength) {
		if (initialString.length() <= targetLength || initialString.isEmpty()) {
			return initialString;
		} else {
			return initialString.substring(0, targetLength - 1) + "...";
		}
	}

	private String stringifyArgs(Object... args) {
		if (args.length == 0) {
			return "<no args>";
		} else {
			return StringUtils.join(args, ',');
		}
	}

	public void performInnerHtmlFoProvidedElements(String htmlToInner, Element element) {
		Logger.info("Inner html '" + htmlToInner + "' for elements " + stringifyArgs(element));
		String script = "arguments[0].innerHTML=" + htmlToInner + "}";
		executeScript(script, element.getWebElement());
	}

	public void makeFlashElemetsTransparentForScreenshots(Element element) {
		String htmlToInner = "<param name=\\\"wmode\\\" value=\\\"transparent\\\">";
		performInnerHtmlFoProvidedElements(htmlToInner, element);
	}

	/**
	 * Executes some Action on provided element
	 * 
	 * @param javaScriptAction
	 * @param element
	 * @return
	 */
	public Object fireJavaScriptEventForElement(JSActions javaScriptAction, Element element) {
		Logger.debug("Firing event '" + javaScriptAction.getFunctionName() + "' for element'" + element.getIdentifyingText() + "'");
		return executeScript(generateScriptForJSAction(javaScriptAction), element.getWebElement());
	}

	public Object fireJavaScriptEventForElement(JSActions javaScriptAction, WebElement webElement) {
		Logger.debug("Firing event '" + javaScriptAction.getFunctionName() + "' for element'"
				+ new Element(webElement).getIdentifyingText() + "'");
		return executeScript(generateScriptForJSAction(javaScriptAction), webElement);
	}

	private String generateScriptForJSAction(JSActions javaScriptAction) {
		if (getBrowserType().equals("Microsoft Internet Explorer")) {
			return String.format(IE_MOUSE_EVENT_SCRIPT_PATTERN, javaScriptAction.getFunctionName());
		} else {
			return String.format(FF_CHROME_MOUSE_EVENT_SCRIPT_PATTERN, javaScriptAction.getFunctionName());
		}
	}

	public void setElementAttributeViaJS(Element element, String attrName, String value) {
		Logger.debug("Setting 'value' of element " + element.getIdentifyingText() + " to '" + value + "'");
		executeScript("arguments[0].setAttribute('" + attrName + "', " + "arguments[1]" + ")", element.getWebElement(), value);
	}

	public String getBrowserType() {
		return executeScript("return navigator.appName.toString()").toString();
	}

	public String getBrowserFullVersion() {
		String fullVersion = executeScript("return navigator.appVersion.toString()").toString();
		String userAgent = executeScript("return navigator.userAgent.toString()").toString();
		int verOffset, ix;

		// In Opera, the true version is after "Opera" or after "Version"
		if ((verOffset = userAgent.indexOf("Opera")) != -1) {
			fullVersion = userAgent.substring(verOffset + 6);
			if ((verOffset = userAgent.indexOf("Version")) != -1)
				fullVersion = userAgent.substring(verOffset + 8);
		}
		// In MSIE, the true version is after "MSIE" in userAgent
		else if ((verOffset = userAgent.indexOf("MSIE")) != -1) {
			fullVersion = userAgent.substring(verOffset + 5);
		}
		// In Chrome, the true version is after "Chrome"
		else if ((verOffset = userAgent.indexOf("Chrome")) != -1) {
			fullVersion = userAgent.substring(verOffset + 7);
		}
		// In Safari, the true version is after "Safari" or after "Version"
		else if ((verOffset = userAgent.indexOf("Safari")) != -1) {
			fullVersion = userAgent.substring(verOffset + 7);
			if ((verOffset = userAgent.indexOf("Version")) != -1)
				fullVersion = userAgent.substring(verOffset + 8);
		}
		// In Firefox, the true version is after "Firefox"
		else if ((verOffset = userAgent.indexOf("Firefox")) != -1) {
			fullVersion = userAgent.substring(verOffset + 8);
		}
		// In most other browsers, "name/version" is at the end of userAgent
		else if ((userAgent.lastIndexOf(' ') + 1) < (verOffset = userAgent.lastIndexOf('/'))) {
			fullVersion = userAgent.substring(verOffset + 1);
		}
		// trim the fullVersion string at semicolon/space if present
		if ((ix = fullVersion.indexOf(";")) != -1)
			fullVersion = fullVersion.substring(0, ix);
		if ((ix = fullVersion.indexOf(" ")) != -1)
			fullVersion = fullVersion.substring(0, ix);

		return fullVersion;
	}

	public int getBrowserMajorVersion() {
		int majorVersion;
		try {
			majorVersion = Integer.parseInt(getBrowserFullVersion().split("[.]")[0]);
		} catch (NumberFormatException e) {
			majorVersion = Integer.parseInt(executeScript("return navigator.appName.toString()").toString());
		}
		return majorVersion;
	}

	public Date getCurrentBrowserTime() {
		Date result;
		DateFormat dataConsoleDataFormat = null;
		if (getBrowserType().equals("Microsoft Internet Explorer")) {
			dataConsoleDataFormat = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy", Locale.ROOT);
		} else {
			dataConsoleDataFormat = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss", Locale.ROOT);
		}

		String object = (String) Browser.getBrowser().executeScript("return new Date().toString()");
		try {
			result = (Date) dataConsoleDataFormat.parseObject(object.replaceAll(" PDT\\S*", "").replaceAll(" UTC\\S*", ""));
		} catch (Exception e) {
			Logger.warn(e.getClass().getSimpleName()
					+ " occurred on attemt to get browser time. Java System time will be returned instead.");
			return Calendar.getInstance().getTime();
		}
		return result;
	}

	public void setElementValueViaJS(Element element, String value) {
		setElementValueViaJS(-1, element, value);
	}

	public void setElementValueViaJS(int javaScriptLengthToLog, Element element, String value) {
		Logger.debug("Setting 'value' of element " + element.getIdentifyingText() + " to '"
				+ value.substring(0, Math.min(value.length(), MAX_ARGUMENTS_LENGTH_TO_LOG)) + "...'");
		if (executeScript("return arguments[0].value", element.getWebElement()) == null) {
			Logger.warn("Can't set value: there is no such attribute");
			return;
		}
		executeScript("arguments[0].value = arguments[1]", element.getWebElement(), value);
	}

	public boolean isElementPresent(By by) {
		return (webDriver.findElements(by).size() != 0);
	}

	public int getElementsCount(By by) {
		return webDriver.findElements(by).size();
	}
	
	public boolean isElementPresentWithWait(By by){
		return isElementPresentWithWait(by, DEFAULT_WAIT_FOR_ELEMENT);
	}

	public boolean isElementPresentWithWait(By by, long timeOutInSeconds) {
		try {
			waitForElement(by, false, timeOutInSeconds);
			return true;
		} catch (TimeoutException e) {
			return false;
		}

	}
	
	public boolean isElementVisibletWithWait(By by, long timeOutInSeconds) {
		try {
			waitForElement(by, true, timeOutInSeconds);
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public void waitForAlert(long timeOutInSeconds) {
		int i = 0;
		while (i < timeOutInSeconds) {
			try {
				webDriver.switchTo().alert();
				break;
			} catch (NoAlertPresentException e) {
				pause(1000);
				i++;
				continue;
			}
		}
	}
	
	public boolean isAlertPresent() {		
		try {
			webDriver.switchTo().alert();
			webDriver.switchTo().defaultContent();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	public void clickOkInAlertIfPresent() {
		try {
			Logger.debug("Trying to obtain alert...");
			// Get a handle to the open alert, prompt or confirmation
			Alert alert = webDriver.switchTo().alert();		
			try{
				// Get the text of the alert or prompt
				Logger.info("Selecting OK in alert: '" + alert.getText().replace("\n", "") + "'");
			} catch (Exception ex) {
				Logger.warn(ex.getClass().getSimpleName() + " occured on alert processing.", ex);
			}
			// And acknowledge the alert (equivalent to clicking "OK")
			alert.accept();
			pause(1000);
			//makeScreenshot();
		} catch (NoAlertPresentException e) {
			Logger.debug(e.getMessage());
			Logger.info("No alerts appeared");
		} catch (Exception e) {
			Logger.warn(e.getClass().getSimpleName() + " occured on alert processing.", e);
		}
	}

	public void clickOkInAlertIfPresent(int timeOutInSeconds) {
		WebDriverWait wait = new WebDriverWait(webDriver, timeOutInSeconds);
		try{
			wait.until(ExpectedConditions.alertIsPresent());
			webDriver.switchTo().alert().accept();
			Logger.info("Alert was present and accepted");
		}catch(org.openqa.selenium.TimeoutException e){
			Logger.info(String.format("Alert was not Present after %s seconds", timeOutInSeconds));
		}
	}

	// Poor method, testing required
	public String getElementXPath(Element element) {
		return (String) executeScript(
				"gPt=function(c){if(c.id!==''){return'id(\"'+c.id+'\")'}if(c===document.body){return c.tagName}var a=0;var e=c.parentNode.childNodes;for(var b=0;b<e.length;b++){var d=e[b];if(d===c){return gPt(c.parentNode)+'/'+c.tagName+'['+(a+1)+']'}if(d.nodeType===1&&d.tagName===c.tagName){a++}}};return gPt(arguments[0]).toLowerCase();",
				element.getWebElement());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#getPageSource()
	 */
	@Override
	public String getPageSource() {
		return webDriver.getPageSource();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#close()
	 */
	@Override
	public void close() {
		Logger.info("Closing browser...");
		webDriver.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#quit()
	 */
	@Override
	public void quit() {
		if (webDriver instanceof RemoteWebDriver && ((RemoteWebDriver) webDriver).getSessionId() == null) {
			return;
		}
		try {
			Logger.info("Terminating browser...");
			webDriver.quit();
		} catch (Exception e) {
			// do nothing
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#getWindowHandles()
	 */
	@Override
	public Set<String> getWindowHandles() {
		return webDriver.getWindowHandles();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#getWindowHandle()
	 */
	@Override
	public String getWindowHandle() {
		return webDriver.getWindowHandle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#switchTo()
	 */
	@Override
	public TargetLocator switchTo() {
		return webDriver.switchTo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#navigate()
	 */
	@Override
	public Navigation navigate() {
		return webDriver.navigate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#manage()
	 */
	@Override
	public Options manage() {
		return webDriver.manage();
	}

	/**
	 * @return the browserName
	 */
	public String getBrowserName() {
		return browserName;
	}

	public WebDriver getWebDriver() {
		return webDriver;
	}

	public void makeScreenshot() {
		
		try {
			byte[] screenshotBytes = getScreenshotAs(OutputType.BYTES);
			File screenShotFile = new File("test-output/html/screenshoots", "screenshot-" + CommonUtils.timeStampDetailed() + ".png");
			FileUtils.writeByteArrayToFile(screenShotFile, screenshotBytes);
			Logger.htmlOutput("Taken screenshoot <a href='screenshoots/" + screenShotFile.getName() + "'>" + screenShotFile.getName()
					+ "</a>");
		} catch (Exception e) {
			Logger.error("Error during taking screenshoot", e);
		}
	}
	
	public void makeScreenshotUsingRobot() {
		try {

			File screenShotFile = new File("test-output/html/screenshoots", "screenshot-" + CommonUtils.timeStampDetailed() + ".png");
			Point browserPoint = this.manage().window().getPosition();
			Dimension browserDimension = this.manage().window().getSize();
			java.awt.Point javaPoint = new java.awt.Point(browserPoint.getX(), browserPoint.getY());
			java.awt.Dimension javaDimension = new java.awt.Dimension(browserDimension.getWidth(), browserDimension.getHeight());
			Robot robot = new Robot();
			Rectangle browserRect = new Rectangle(javaPoint, javaDimension);
			BufferedImage bufferedImage = robot.createScreenCapture(browserRect);
			ImageIO.write(bufferedImage,"png",screenShotFile);
			Logger.htmlOutput("Taken screenshoot using java robot <a href='screenshoots/" + screenShotFile.getName() + "'>" + screenShotFile.getName()
					+ "</a>");
		} catch (Exception e) {
			Logger.error("Error during taking screenshoot", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.internal.WrapsDriver#getWrappedDriver()
	 */
	@Override
	public WebDriver getWrappedDriver() {
		return this.webDriver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.openqa.selenium.TakesScreenshot#getScreenshotAs(org.openqa.selenium
	 * .OutputType)
	 */
	@Override
	public <X> X getScreenshotAs(OutputType<X> paramOutputType) throws WebDriverException {
		
		return ((TakesScreenshot) webDriver).getScreenshotAs(paramOutputType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.openqa.selenium.JavascriptExecutor#executeAsyncScript(java.lang.String
	 * , java.lang.Object[])
	 */
	@Override
	public Object executeAsyncScript(String paramString, Object... paramArrayOfObject) {
		return ((JavascriptExecutor) webDriver).executeAsyncScript(paramString, paramArrayOfObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.HasInputDevices#getKeyboard()
	 */
	@Override
	public Keyboard getKeyboard() {
		return ((HasInputDevices) webDriver).getKeyboard();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.HasInputDevices#getMouse()
	 */
	@Override
	public Mouse getMouse() {
		return ((HasInputDevices) webDriver).getMouse();
	}

	/**
	 * Performs some action and switches browser to new opened window<br>
	 * Throws {@link WebDriverException} in case new window is not opened
	 * 
	 * @param action
	 */
	public String opensNewWindow(Action action) {
		/* Get current window handles */
		String oldHandle = this.getWindowHandle();
		Set<String> handles = this.getWindowHandles();
		Logger.debug("Window handles: " + StringUtils.join(handles, ','));

		action.perform();
		
		this.waitForNewWindowOpened(15);
		
		/* find new window handle */
		Set<String> newHandles = Sets.symmetricDifference(handles, this.getWindowHandles());
		Logger.debug("NEW Window handles: " + StringUtils.join(newHandles, ','));

		/* We have to be sure that publisher details is opened in new window */
		if (newHandles.isEmpty()) {
			throw new WebDriverException("There was no new windows opened");
		}

		/* switch to the new window */
		this.switchTo().window(newHandles.iterator().next());
		return oldHandle;
	}

	public void switchToLastWindow() {
		Logger.info("Current URL before switching is : " + this.getCurrentUrl());
		Set<String> handles = this.getWindowHandles();
		Logger.info("Now opened : " + handles.size() + " windows!");
		Iterator<String> it = handles.iterator();
		String handle = it.next();
		for (int i = 1; i < handles.size(); ++i) {
			handle = it.next();
		}
		webDriver.switchTo().window(handle);
		Logger.info("Switched to Last window with URL: " + this.getCurrentUrl());
	}
	
	public void switchToWindow(String windowTitle) {
		Set<String> handles = this.getWindowHandles();
		Logger.info("Now opened : " + handles.size() + " windows!");
        for (String window : handles) {
            this.switchTo().window(window);
            if (this.getTitle().contains(windowTitle)) {
            	Logger.info("Swith to window with title: " + this.getTitle() + " which contais : " + windowTitle);
            	this.makeScreenshot();
                return;
            }else{
            	Logger.info("Can't swith to window, because current title: " + this.getTitle() + " doesn't contais : " + windowTitle);
            }
        }
    }
	
	//Added AlexandrY for debug on server side
	public void printHandles(){
		String currentHandle = this.getWindowHandle();

		Set<String> handles = this.getWindowHandles();
		
		if(handles.size() > 1){
			Logger.info("Now opened : " + handles.size() + " windows!");
			
			for(String window : handles){
				this.switchTo().window(window);
				pause(1000);
	            Logger.info(" - window handle is: " + window);
				makeScreenshot();
			}
			this.switchTo().window(currentHandle);
			pause(1000);
		} else {
			Logger.info("Now opened : " + handles.size() + " windows!");
			Logger.info(" - window handle is: " + handles.iterator().next());
			makeScreenshot();
		}	
	}
	
	public void switchToFirstWindow() {
		Logger.info("Current URL before switching is : " + this.getCurrentUrl());
		Set<String> handles = this.getWindowHandles();
		Logger.info("Now opened : " + handles.size() + " windows!");
		Iterator<String> it = handles.iterator();
		String handle = it.next();
		webDriver.switchTo().window(handle);
		Logger.info("Switched to First window with URL: " + this.getCurrentUrl());
	}

	public void closeAllWindowsExceptCurrentWithSubURL(String subURL) {
		Logger.info("Closing all windows except current using subURL...");
		Set<String> handles = this.getWindowHandles();
		Logger.info("Now opened : " + handles.size() + " windows!");
		if (handles.size() == 1) {
			Logger.info("Only one window opened, with URL: " + this.getCurrentUrl());
			return;
		}
		Logger.debug("Window handles: " + StringUtils.join(handles, ','));
		for (String window : handles) {
			this.switchTo().window(window);
			if (!this.getCurrentUrl().contains(subURL)) {
				this.switchTo().window(window);
				Logger.info("Close the window with URL: " + this.getCurrentUrl());
				this.close();
			}
		}		
		handles = this.getWindowHandles();
		if (handles.size() == 1) {
			this.switchTo().window(handles.iterator().next());
			Logger.info("Only one window opened, with URL: " + this.getCurrentUrl());
			this.makeScreenshot();
		}
	}

	public void closeAllWindowsExceptCurrent(String windowHandle) {
		Set<String> handles = this.getWindowHandles();
		Logger.info("Now opened : " + handles.size() + " windows!");
		if (handles.size() == 1) {
			Logger.debug("Only one window opened: " + windowHandle);
			return;
		}
		Logger.debug("Window handles: " + StringUtils.join(handles, ','));
		for (String handle : handles) {
			if (!handle.equals(windowHandle)) {
				this.switchTo().window(handle);
				Logger.info("Close the window with URL: " + this.getCurrentUrl());
				this.close();
			}
		}

		// at the end switch to current window
		this.switchTo().window(windowHandle);
		Logger.info("Opened one window with URL: " + this.getCurrentUrl());
	}
	
	public void waitForNewWindowOpened(int timeoutInseconds){
		Logger.debug("Waiting for new window opens...");
		Waitable<Browser> wait = new SmartWait<Browser>(this) {
			@Override
			protected RuntimeException timeoutException(String message, Throwable lastException) {
				makeScreenshot();
				throw new RuntimeException("No new window opened!");
			}
		}.withTimeout(timeoutInseconds, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		wait.until(new Function<Browser, Boolean>() {
			@Override
			public Boolean apply(Browser browser) {
				Set<String> handles = browser.getWindowHandles();				
				return handles.size()>1;
			}
		});
	}

	public void closeAllWindowsExceptFirst() {
		Set<String> handles = this.getWindowHandles();
		if (handles.size() == 1) {
			Logger.debug("Only one window opened");
			return;
		}
		Iterator<String> it = handles.iterator();
		String firstHandle = it.next();
		Logger.debug("Window handles: " + StringUtils.join(handles, ','));
		for (String handle : handles) {
			if (!handle.equals(firstHandle)) {
				this.switchTo().window(handle);
				Logger.info("Close the window with URL: " + this.getCurrentUrl());
				this.close();
			}
		}

		// at the end switch to curent window
		webDriver.switchTo().window(firstHandle);
		Logger.info("Opened one window with URL: " + this.getCurrentUrl());
	}
	
	public void closeAllWindowsExceptFirst(Boolean clickOnAlert) {
		Set<String> handles = this.getWindowHandles();
		if (handles.size() == 1) {
			Logger.debug("Only one window opened");
			return;
		}
		Iterator<String> it = handles.iterator();
		String firstHandle = it.next();
		Logger.debug("Window handles: " + StringUtils.join(handles, ','));
		for (String handle : handles) {
			if (!handle.equals(firstHandle)) {
				this.switchTo().window(handle);
				Logger.info("Close the window with URL: " + this.getCurrentUrl());
				this.close();
				this.clickOkInAlertIfPresent();
			}
		}

		// at the end switch to curent window
		webDriver.switchTo().window(firstHandle);
		Logger.info("Opened one window with URL: " + this.getCurrentUrl());
	}
	
	public void doInFrame(String frame, Action action) {
		this.switchTo().frame(frame);
		action.perform();
		this.switchTo().defaultContent();
	}

	public void doInFrame(WebElement frame, Action action) {
		this.switchTo().frame(frame);
		action.perform();
		this.switchTo().defaultContent();
	}

	public Downloads acquireDownloads() {
		try {
			return downloads.acquire();
		} catch (InterruptedException e) {
			throw new RuntimeException("Unable to acquire downloads folder");
		}
	}

	public void releaseDownloads() {
		downloads.release();
	}

	public static class Downloads extends File {

		private static final long serialVersionUID = -8310279119232668324L;

		private static final FilenameFilter NO_ACTIVE_DOWNLOADS_FILTER = new NoActiveDownloadsFilenameFilter();

		private Semaphore semaphore;

		public Downloads(String directory) {
			super(directory);
			semaphore = new Semaphore(1);
		}

		public File getDownloadedFile(String filename) {
			return new File(this, filename);
		}

		public void waitForDownloadedFile(String filename, long duration, TimeUnit timeUnit) {
			waitForDownloadedFile(filename, duration, timeUnit, null);
		}

		public File waitForDownloadedFile(String filename, long duration, TimeUnit timeUnit, String errorMessage) {
			SmartWait<String> wait = new SmartWait<String>(filename).withTimeout(duration, timeUnit).pollingEvery(1, TimeUnit.SECONDS);
			if (null != errorMessage && !errorMessage.isEmpty()) {
				wait = wait.withMessage(errorMessage);
			}
			return wait.until(new Function<String, File>() {
				@Override
				public File apply(String filename) {
					File file = new File(Downloads.this, filename);
					return file.exists() ? file : null;
				}
			});
		}

		private Downloads acquire() throws InterruptedException {
			Logger.info("Obtaining semaphore for downloads folders..." + Thread.currentThread().getId());
			semaphore.acquire();
			Logger.info("Obtained semaphore for downloads folders..." + Thread.currentThread().getId());
			return this;
		}

		public void release() {
			semaphore.release();
			Logger.info("Semaphore released for downloads folders..." + Thread.currentThread().getId());
		}

		/**
		 * Clears Downloads directory
		 */
		public synchronized void clearDownloadsDirectory() {
			if (GlobalConfiguration.getInstance().getRemote()) {
				throw new UnsupportedOperationException("Unable to clear downloads directory in remote launch mode");
			}
			try {
				if (this.exists()) {
					Logger.debug("Clearing downloads directory...");
					FileUtils.cleanDirectory(this);
				}
			} catch (IOException e) {
				throw new BrowserDownloadsException("Unable to clean downloads folder", e);
			}
		}

		public static void waitForDownloadsCompleted(long period, TimeUnit timeUnit) {
			new SmartWait<File>(new File(downloadsDirectory)).withTimeout(period, timeUnit).pollingEvery(1l, TimeUnit.SECONDS)
					.until(new Predicate<File>() {
						@Override
						public boolean apply(File dir) {
							Logger.debug("Checking partly downloaded files count...");
							return dir.list(NO_ACTIVE_DOWNLOADS_FILTER).length == 0;
						}
					});
		}

		public static void waitForDownloadsCompleted() {
			waitForDownloadsCompleted(20, TimeUnit.SECONDS);
		}

		private static class NoActiveDownloadsFilenameFilter implements FilenameFilter {

			@Override
			public boolean accept(File paramFile, String filename) {
				Logger.debug("Waiting for downloads completed...");
				return filename.endsWith(".part");
			}

		}

	}

	public ScreenRegion waitForFlashElement(String imagePath) {
		ScreenRegion desktop = new DesktopScreenRegion();
		Target targetPicture = new ImageTarget(ResourceUtils.getResourceAsTempFile(imagePath));
		ScreenRegion region = desktop.wait(targetPicture, 30000);
		return region;
	}

	public void clickFlashElementByImage(ScreenRegion screenRegion) {
		org.sikuli.api.robot.Mouse mouse = new DesktopMouse();
		mouse.click(screenRegion.getCenter());
	}

}