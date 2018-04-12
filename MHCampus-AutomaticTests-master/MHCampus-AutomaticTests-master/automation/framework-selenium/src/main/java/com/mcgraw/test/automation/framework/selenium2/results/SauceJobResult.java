package com.mcgraw.test.automation.framework.selenium2.results;

/**
 * Base SauceLabs Job Result representation
 *
 * @see <a href="http://saucelabs.com">http://saucelabs.com</a>
 *
 * @author Andrei Varabyeu
 *
 */
public class SauceJobResult {

	/** URL to Job at SauceLabs */
	private String sauceJobUrl;

	/** System Information provided by browser */
	private String systemInformation;

	/** Failure message */
	private String failureMessage;

	/** Browser's URL in moment of fail */
	private String testUrl;

	/** Name of used browser */
	private String browserName;

	public SauceJobResult(String sauceJobUrl, String systemInformation,
			String failureMessage, String testUrl, String browserName) {
		this.sauceJobUrl = sauceJobUrl;
		this.systemInformation = systemInformation;
		this.failureMessage = failureMessage;
		this.testUrl = testUrl;
		this.browserName = browserName;
	}

	/**
	 * @return the sauceJobUrl
	 */
	public String getSauceJobUrl() {
		return sauceJobUrl;
	}

	/**
	 * @param sauceJobUrl
	 *            the sauceJobUrl to set
	 */
	public void setSauceJobUrl(String sauceJobUrl) {
		this.sauceJobUrl = sauceJobUrl;
	}

	/**
	 * @return the systemInformation
	 */
	public String getSystemInformation() {
		return systemInformation;
	}

	/**
	 * @param systemInformation
	 *            the systemInformation to set
	 */
	public void setSystemInformation(String systemInformation) {
		this.systemInformation = systemInformation;
	}

	/**
	 * @return the failureMessage
	 */
	public String getFailureMessage() {
		return failureMessage;
	}

	/**
	 * @param failureMessage
	 *            the failureMessage to set
	 */
	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}

	/**
	 * @return the testUrl
	 */
	public String getTestUrl() {
		return testUrl;
	}

	/**
	 * @param testUrl
	 *            the testUrl to set
	 */
	public void setTestUrl(String testUrl) {
		this.testUrl = testUrl;
	}

	/**
	 * @return the browserName
	 */
	public String getBrowserName() {
		return browserName;
	}

	/**
	 * @param browserName
	 *            the browserName to set
	 */
	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

}
