package com.mcgraw.test.automation.framework.selenium2.configuration;

import java.util.List;

import org.kohsuke.args4j.Option;

import com.mcgraw.test.automation.framework.core.runner.cli.CommandLineParameters;
import com.mcgraw.test.automation.framework.core.runner.cli.ExplicitBooleanOptionHandler;

public class SeleniumCliParameters extends CommandLineParameters {

	@Option(name = "-rmt", aliases = { "--isRemote" }, usage = "Wheither web driver should be remote(SauceLabs). Default is 'false'.", handler = ExplicitBooleanOptionHandler.class)
	private Boolean isRemote;

	@Option(name = "-sh", aliases = { "--selenium-host" }, usage = "Set SeleniumRC host. Default is 'localhost'.")
	private String seleniumHostName = "127.0.0.1";

	@Option(name = "-sp", aliases = { "--selenium-port" }, usage = "Set SeleniumRC port. Default is '4444'.")
	private String seleniumHostPort = "4444";

	@Option(name = "-bu", aliases = { "--base-url" }, usage = "Base URL to be oppened. Default is 'https://webapp-dev005.krxd.net'")
	private String baseUrl = "https://webapp-dev005.krxd.net";

	/** Number of Remote Urls */
	@Option(name = "-nru", aliases = { "--numberRemoteUrls" }, usage = "Count of remote urls should be tested. Default value is defined in 'selenium.properties' file")
	private Integer numberOfRemoteUrls;

	/** Number of Local Urls */
	@Option(name = "-nlu", aliases = { "--numberLocalUrls" }, usage = "Count of local urls should be tested. Default value is defined in 'selenium.properties' file")
	private Integer numberOfLocalUrls;

	@Option(name = "-extraparam", aliases = { "--extraparameter" }, usage = "Extra parameters for Control Tag tests")
	private List<String> controlTagExtraParameters;

	@Option(name = "-ctv", aliases = { "--controlTagVersion" }, usage = "Control Tag version")
	private String controlTagVersion;

	@Option(name = "-csu", aliases = { "--customSauceUrl" }, usage = "Custom Sauce URL")
	private String customSauceUrl;

	@Option(name = "-scr", aliases = { "--screenshoots" }, usage = "Taking screenshots", handler = ExplicitBooleanOptionHandler.class)
	private Boolean isScreenshotsEnabled = true;

	/**
	 * @return the seleniumHostName
	 */
	public String getSeleniumHostName() {
		return seleniumHostName;
	}

	/**
	 * @param seleniumHostName
	 *            the seleniumHostName to set
	 */
	public void setSeleniumHostName(String seleniumHostName) {
		this.seleniumHostName = seleniumHostName;
	}

	/**
	 * @return the seleniumHostPort
	 */
	public String getSeleniumHostPort() {
		return seleniumHostPort;
	}

	/**
	 * @param seleniumHostPort
	 *            the seleniumHostPort to set
	 */
	public void setSeleniumHostPort(String seleniumHostPort) {
		this.seleniumHostPort = seleniumHostPort;
	}

	/**
	 * @return the rootUrl
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * @param rootUrl
	 *            the rootUrl to set
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * @return the isRemote
	 */
	public Boolean getRemote() {
		return isRemote;
	}

	/**
	 * @param isRemote
	 *            the isRemote to set
	 */
	public void setRemote(Boolean remote) {
		this.isRemote = remote;
	}

	/**
	 * @return the numberOfRemoteUrls
	 */
	public Integer getNumberOfRemoteUrls() {
		return numberOfRemoteUrls;
	}

	/**
	 * @param numberOfRemoteUrls
	 *            the numberOfRemoteUrls to set
	 */
	public void setNumberOfRemoteUrls(Integer numberOfRemoteUrls) {
		this.numberOfRemoteUrls = numberOfRemoteUrls;
	}

	/**
	 * @return the numberOfLocalUrls
	 */
	public Integer getNumberOfLocalUrls() {
		return numberOfLocalUrls;
	}

	/**
	 * @param numberOfLocalUrls
	 *            the numberOfLocalUrls to set
	 */
	public void setNumberOfLocalUrls(Integer numberOfLocalUrls) {
		this.numberOfLocalUrls = numberOfLocalUrls;
	}

	public List<String> getControlTagExtraParameters() {
		return controlTagExtraParameters;
	}

	public void setControlTagExtraParameters(
			List<String> controlTagExtraParameters) {
		this.controlTagExtraParameters = controlTagExtraParameters;
	}

	public String getControlTagVersion() {
		return controlTagVersion;
	}

	public void setControlTagVersion(String controlTagVersion) {
		this.controlTagVersion = controlTagVersion;
	}

	public String getCustomSauceUrl() {
		return customSauceUrl;
	}

	public void setCustomSauceUrl(String customSauceUrl) {
		this.customSauceUrl = customSauceUrl;
	}

	/**
	 * @return the isScreenshotsEnabled
	 */
	public Boolean getIsScreenshotsEnabled() {
		return isScreenshotsEnabled;
	}

	/**
	 * @param isScreenshotsEnabled
	 *            the isScreenshotsEnabled to set
	 */
	public void setIsScreenshotsEnabled(Boolean isScreenshotsEnabled) {
		this.isScreenshotsEnabled = isScreenshotsEnabled;
	}

}
