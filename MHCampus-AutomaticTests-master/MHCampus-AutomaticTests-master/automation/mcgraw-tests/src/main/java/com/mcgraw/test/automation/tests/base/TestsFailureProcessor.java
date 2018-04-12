package com.mcgraw.test.automation.tests.base;

import java.util.List;

import org.testng.ITestResult;

import com.mcgraw.test.automation.framework.core.runner.FailurePostProcessor;

public class TestsFailureProcessor implements FailurePostProcessor{

	@Override
	public boolean shouldFail(ITestResult testResult) {
		return (!testResult.isSuccess());

	}

	@Override
	public boolean shouldFail(List<ITestResult> testResults) {
		for (ITestResult testResult : testResults) {
			if (shouldFail(testResult)) {
				return true;
			}
		}
		return false;
	}

}
