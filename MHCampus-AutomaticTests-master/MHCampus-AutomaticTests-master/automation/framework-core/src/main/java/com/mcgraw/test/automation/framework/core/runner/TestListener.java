package com.mcgraw.test.automation.framework.core.runner;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.internal.Utils;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.results.logger.LoggerLevel;
import com.mcgraw.test.automation.framework.core.testng.Assert;

/**
 * @author yyudzitski
 */
public class TestListener extends TestListenerAdapter implements IInvokedMethodListener {
	private String getCaseName(ITestResult result) {
		return result.getTestClass().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName();
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		if (!method.isTestMethod()) {
			// Logger.getLogger().log(LoggerLevel.CONFIGURATION_STARTED,
			// getCaseName(testResult));
		}
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult result) {
		Reporter.setCurrentTestResult(result);

		if (method.isTestMethod()) {

			List<Throwable> verificationFailures = Assert.getVerificationFailures();

			// if there are verification failures...
			if (verificationFailures.size() > 0) {

				// set the test to failed
				result.setStatus(ITestResult.FAILURE);

				// if there is an assertion failure add it to
				// verificationFailures
				if (result.getThrowable() != null) {
					verificationFailures.add(result.getThrowable());
				}

				int size = verificationFailures.size();
				// if there's only one failure just set that
				if (size == 1) {
					result.setThrowable(verificationFailures.get(0));
				} else {

					// create a failure message with all failures and stack
					// traces (except last failure)
					StackTraceElement[] failureStackTrace = null;
					StringBuffer failureMessage = new StringBuffer("Multiple assertion failures (").append(size).append("):");
					for (int i = 0; i < size; i++) {
						Throwable t = verificationFailures.get(i);
						
						//creating custom stacktrace from all throwables
						StackTraceElement[] stackTracelement = t.getStackTrace();
						failureStackTrace = ArrayUtils.add(failureStackTrace, new StackTraceElement(new StringBuffer("\n\nFailure ")
								.append(i + 1).append(" of ").append(size).append(":\n").toString(), t.getMessage(), "Stacktrace", i+1));
						failureStackTrace = ArrayUtils.addAll(failureStackTrace, stackTracelement);
					}

					// set merged throwable
					Throwable merged = new Throwable(failureMessage.toString());
					merged.setStackTrace(failureStackTrace);
					result.setThrowable(merged);
				}
			}
		}
		Assert.clearVerificationFailuresMap();
	}

	@Override
	public void onStart(ITestContext testContext) {
		Logger.getLogger().log(LoggerLevel.TEST_STARTED, testContext.getName());
	}

	@Override
	public void onFinish(ITestContext testContext) {
		Logger.getLogger().log(LoggerLevel.TEST_FINISHED, testContext.getName());
	}

	@Override
	public void onConfigurationFailure(ITestResult testResult) {
		Logger.getLogger().log(LoggerLevel.CONFIGURATION_FAILED,
				getCaseName(testResult) + ExceptionUtils.getStackTrace(testResult.getThrowable()));
	}

	@Override
	public void onConfigurationSuccess(ITestResult testResult) {
		// Logger.getLogger().log(LoggerLevel.CONFIGURATION_SUCCESS,
		// getCaseName(testResult));
	}

	@Override
	public void onConfigurationSkip(ITestResult testResult) {
		// Logger.getLogger().log(LoggerLevel.CONFIGURATION_SKIPPED,
		// getCaseName(testResult));
	}

	@Override
	public void onTestStart(ITestResult testResult) {
		String message = getCaseName(testResult);
		if (isParameterized(testResult)) {
			message += getParametersDescription(testResult);
		}
		Logger.getLogger().log(LoggerLevel.METHOD_STARTED, message);

	}

	@Override
	public void onTestSuccess(ITestResult testResult) {
		String message = getCaseName(testResult);
		if (isParameterized(testResult)) {
			message += getParametersDescription(testResult);
		}
		Logger.getLogger().log(LoggerLevel.METHOD_SUCCESS, message);
	}

	@Override
	public void onTestFailure(ITestResult testResult) {
		String message = getCaseName(testResult);
		if (isParameterized(testResult)) {
			message += getParametersDescription(testResult);
		}
		Logger.getLogger().log(LoggerLevel.METHOD_FAILED, message + ": " + ExceptionUtils.getStackTrace(testResult.getThrowable()));
	}

	@Override
	public void onTestSkipped(ITestResult testResult) {
		String message = getCaseName(testResult);
		if (isParameterized(testResult)) {
			message += getParametersDescription(testResult);
		}
		Logger.getLogger().log(LoggerLevel.METHOD_SKIPPED, message);
	}

	private boolean isParameterized(ITestResult testResult) {
		return testResult.getMethod().getParameterInvocationCount() > 1;
	}

	private String getParametersDescription(ITestResult testResult) {
		int invocationCount = testResult.getMethod().getParameterInvocationCount() * testResult.getTestContext().getAllTestMethods().length;
		StringBuilder builder = new StringBuilder();
		builder.append("\n");
		builder.append("Invocation: ");
		builder.append(testResult.getMethod().getCurrentInvocationCount() + 1);
		builder.append(" of ");
		builder.append(invocationCount);

		builder.append("[");
		builder.append(((float) (testResult.getMethod().getCurrentInvocationCount() + 1) / (float) invocationCount) * 100);
		builder.append("%] ");
		builder.append("\n");
		builder.append("In thread:" + Thread.currentThread().getName());
		builder.append("\n");
		builder.append("Parameters: ");
		builder.append("\n");
		for (int i = 0, length = testResult.getParameters().length; i < length; i++) {
			builder.append(i);
			builder.append(". ");
			builder.append(testResult.getParameters()[i]);
			builder.append("\n");
		}
		return builder.toString();
	}
}