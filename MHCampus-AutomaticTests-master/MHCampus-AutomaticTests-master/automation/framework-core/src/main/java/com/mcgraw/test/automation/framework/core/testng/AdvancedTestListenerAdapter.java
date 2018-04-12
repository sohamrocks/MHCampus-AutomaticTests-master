package com.mcgraw.test.automation.framework.core.testng;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.TestListenerAdapter;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * Extend default TestNG's {@link TestListenerAdapter} with several useful
 * features<br>
 * TestNG doesn't has possibility to skip/fail tests from the
 * {@link TestListenerAdapter#onStart(ITestContext)} method, because this action
 * triggers after creating of instance of test class. Sometimes we have to
 * fail/skip some tests after errors in mentioned method. This class extends
 * TestNG with such functionality
 *
 * @author Andrei Varabyeu
 *
 */
public class AdvancedTestListenerAdapter extends TestListenerAdapter {

	private Set<SkippedContext> contextsToBeSkipped = new HashSet<SkippedContext>();

	protected void skipContext(ITestContext testContext, String errorMessage,
			boolean isSkip) {
		contextsToBeSkipped.add(new SkippedContext(testContext, errorMessage,
				isSkip));
	}

	/**
	 * Just check if we should fail this test
	 */
	@Override
	public void onTestStart(ITestResult result) {
		super.onTestStart(result);
		Optional<SkippedContext> optional = Iterables.tryFind(
				contextsToBeSkipped, shouldSkip(result));
		if (optional.isPresent()) {
			optional.get().fail();
		}
	}
	
	/**
	 * Just check if we should fail this test
	 */
	@Override
	public void beforeConfiguration(ITestResult result) {
		super.beforeConfiguration(result);
		Optional<SkippedContext> optional = Iterables.tryFind(
				contextsToBeSkipped, shouldSkip(result));
		if (optional.isPresent()) {
			optional.get().fail();
		}
	}

	private Predicate<SkippedContext> shouldSkip(final ITestResult testResult) {
		return new Predicate<SkippedContext>() {
			@Override
			public boolean apply(@Nullable SkippedContext skippedContext) {
				return skippedContext.shouldSkip(testResult);
			}
		};
	}

	private class SkippedContext {
		private ITestContext testContext;
		private String errorMessage;
		private boolean isSkip;

		public SkippedContext(ITestContext testContext, String errorMessage,
				boolean isSkip) {
			super();
			this.testContext = Preconditions.checkNotNull(testContext);
			this.errorMessage = Preconditions.checkNotNull(errorMessage);
			this.isSkip = isSkip;
		}

		public boolean shouldSkip(ITestResult testResult) {
			return this.testContext.equals(testResult.getTestContext());
		}

		public void fail() {
			throw new SkipException(errorMessage) {
				private static final long serialVersionUID = -483299499748765259L;

				@Override
				public boolean isSkip() {
					return isSkip;
				}
			};
		}

	}
}
