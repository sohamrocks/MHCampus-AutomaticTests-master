package com.mcgraw.test.automation.ui.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Value;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.timing.WaitForConditionUtils;
import com.mcgraw.test.automation.framework.core.timing.WaitForConditionUtils.TestCondition;

public class InstanceUtils {

	private BufferedReader input;
	private String result = "";
	
	@Value("${tegrity.deleteinstance.path}")
	private String tegrityDeleteInstanceUtilityPath;
	
	@Value("${tegrity.deleteinstance.service.url}")
	private String serviceUrl;
	
	@Value("${tegrity.deleteinstance.service.username}")
	private String serviceUserName;
	
	@Value("${tegrity.deleteinstance.service.password}")
	private String servicePassword;

	public class ResultIsAvailable implements TestCondition {
		public Boolean condition() throws Exception {
			result = input.readLine();
			return (result != null);
		}
	}

	private String createDeleteCliParameters(String customerNumber) {
		String cliparameters = tegrityDeleteInstanceUtilityPath + " " + serviceUrl + " " + serviceUserName + " " + servicePassword + " " + customerNumber;
		return cliparameters;
	}

	public void deleteInstance(String customerNumber) throws Exception {

		Logger.info("Removing MH Campus instance with number " + customerNumber + "...");

		Process p = Runtime.getRuntime().exec(
				createDeleteCliParameters(customerNumber));
		input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		WaitForConditionUtils.WaitForCondition(new ResultIsAvailable(), 30000, "No response from service within 30 seconds!");

		Logger.info("Result from Delete Instance service is: " + result);
		if (!result.equals("Result status = SUCCESS")) {
			Logger.info("Failed to delete instance. Possible cause of failure: instance name doesn't start with 'epam' or it is removed already");
		}
	}
}
