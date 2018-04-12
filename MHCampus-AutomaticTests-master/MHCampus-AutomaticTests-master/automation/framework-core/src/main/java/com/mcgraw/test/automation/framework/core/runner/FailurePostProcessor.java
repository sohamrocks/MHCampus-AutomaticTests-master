package com.mcgraw.test.automation.framework.core.runner;

import java.util.List;

import org.testng.ITestResult;

public interface FailurePostProcessor {

	boolean shouldFail(ITestResult testResult);

	boolean shouldFail(List<ITestResult> testResult);
}
