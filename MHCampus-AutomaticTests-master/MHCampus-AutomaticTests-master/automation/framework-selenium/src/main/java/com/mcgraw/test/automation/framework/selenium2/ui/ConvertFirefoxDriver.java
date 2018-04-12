package com.mcgraw.test.automation.framework.selenium2.ui;

import java.util.Map;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.UnreachableBrowserException;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;

public class ConvertFirefoxDriver extends FirefoxDriver{

	private final int retryCount = 5;
	
	public ConvertFirefoxDriver() {
		super();
	}
	
	public ConvertFirefoxDriver(FirefoxProfile profile) {
		super(new FirefoxBinary(), profile);
	  }

	public ConvertFirefoxDriver(Capabilities desiredCapabilities, Capabilities requiredCapabilities) {
		super(desiredCapabilities, requiredCapabilities);
	}
	
	public ConvertFirefoxDriver(Capabilities desiredCapabilities) {
		super(desiredCapabilities);
	}
	
	public ConvertFirefoxDriver(FirefoxBinary binary, FirefoxProfile profile) {
		super(binary, profile);
	}
	
	public ConvertFirefoxDriver(FirefoxBinary binary, FirefoxProfile profile, Capabilities capabilities) {
		super(binary, profile, capabilities);
	}
	 
	public ConvertFirefoxDriver(FirefoxBinary binary, FirefoxProfile profile,
		      Capabilities desiredCapabilities, Capabilities requiredCapabilities) {
		super(binary, profile, desiredCapabilities, requiredCapabilities);
	}
	
	@Override
	protected Response execute(String driverCommand, Map<String, ?> parameters) {
		int retryAttempt = 0;
		
		while (true) {
			try {
				return super.execute(driverCommand, parameters);
			} catch (UnreachableBrowserException e) {
				Logger.error("--------------------------- retryCount is: " + retryCount + " retryAttempt is: " + retryAttempt + " --------------------------------");
				Logger.error("Errors occured when executing execute() method: " + e);
				retryAttempt++;
				
				try {
					Logger.info("Pause ...");
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					Logger.info("Errors occured when executing sleep() method:");
					e1.printStackTrace();
				}
				
				if (retryAttempt > retryCount) 
					throw e;
			}
		}
	}
}