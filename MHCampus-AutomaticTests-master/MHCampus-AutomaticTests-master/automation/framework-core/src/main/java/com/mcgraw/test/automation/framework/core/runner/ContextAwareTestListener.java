package com.mcgraw.test.automation.framework.core.runner;

import org.springframework.context.ApplicationContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.mcgraw.test.automation.framework.core.configuration.AccessibleField;

/**
 * TestNG listener adapter with access to the Spring's
 * {@link ApplicationContext} . Access to the application context possible only
 * in case test overrides Spring's {@link #AbstractTestNGSpringContextTests}
 *
 * @see ApplicationContext
 * @see AbstractTestNGSpringContextTests
 *
 * @author Andrei Varabyeu
 *
 */
public class ContextAwareTestListener extends TestListenerAdapter {

	/**
	 * Returns Spring Application Context
	 *
	 * @param testResult
	 *            - TestNG's ITestResult
	 * @return
	 */
	protected ApplicationContext getApplicationContext(ITestResult testResult) {
		if (testResult.getInstance() instanceof AbstractTestNGSpringContextTests) {
			AbstractTestNGSpringContextTests springTest = (AbstractTestNGSpringContextTests) testResult
					.getInstance();
			try {
				AccessibleField fieldProcessor = new AccessibleField(springTest,
						AbstractTestNGSpringContextTests.class
								.getDeclaredField("applicationContext"));
				return (ApplicationContext) fieldProcessor.getValue();
			} catch (SecurityException e) {
				throw new RuntimeException(e);
			} catch (NoSuchFieldException e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new RuntimeException("");
		}
	}

	@Override
	public final void onStart(ITestContext paramITestContext) {
		/* restrict overriding since we have no access to test instance */
	}

	@Override
	public final void onFinish(ITestContext paramITestContext) {
		/* restrict overriding since we have no access to test instance */
	}
}
