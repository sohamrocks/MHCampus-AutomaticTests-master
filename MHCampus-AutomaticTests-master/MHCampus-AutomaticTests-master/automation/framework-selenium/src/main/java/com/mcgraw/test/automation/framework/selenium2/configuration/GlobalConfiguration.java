package com.mcgraw.test.automation.framework.selenium2.configuration;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.mcgraw.test.automation.framework.core.configuration.MappedBean;
import com.mcgraw.test.automation.framework.core.runner.cli.Mapped;
import com.mcgraw.test.automation.framework.selenium2.ui.WebDriverType;

/**
 * Main configuration. Contains parameter needed for work.
 *
 * @author Andrei Varabyeu
 */
public final class GlobalConfiguration extends
		MappedBean<SeleniumCliParameters> {
	private static GlobalConfiguration configuration;

	private GlobalConfiguration() {

	}

	public static GlobalConfiguration getInstance() {
		if (configuration == null) {
			configuration = new GlobalConfiguration();
		}
		return configuration;
	}

	@Mapped("selenium.remote.driver")
	private Boolean remote;

	private String seleniumHostName;

	private String seleniumHostPort;

	private WebDriverType driverType;

	private Boolean screenshotsEnabled;

	@Mapped("paths.downloadsDir")
	private String downloadsDir;

	public String getDownloadsDir() {
		return FileUtils.getTempDirectoryPath() + File.separatorChar
				+ downloadsDir;
	}

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
	 * @return the driverType
	 */
	public WebDriverType getDriverType() {
		return driverType;
	}

	/**
	 * @param driverType
	 *            the driverType to set
	 */
	public void setDriverType(WebDriverType driverType) {
		this.driverType = driverType;
	}

	/**
	 * @return the isRemote
	 */
	public Boolean getRemote() {
		return remote;
	}

	/**
	 * @param isRemote
	 *            the isRemote to set
	 */
	public void setRemote(Boolean remote) {
		this.remote = remote;
	}

	public Boolean isScreenshotsEnabled() {
		return screenshotsEnabled;
	}

	@Override
	protected void initialize(SeleniumCliParameters commandLineParameters) {
		this.remote = commandLineParameters.getRemote();
		this.seleniumHostName = commandLineParameters.getSeleniumHostName();
		this.seleniumHostPort = commandLineParameters.getSeleniumHostPort();
		this.screenshotsEnabled = commandLineParameters
				.getIsScreenshotsEnabled();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GlobalConfiguration [remote=" + remote + ", seleniumHostName="
				+ seleniumHostName + ", seleniumHostPort=" + seleniumHostPort
				+ ", driverType=" + driverType + "]";
	}

}