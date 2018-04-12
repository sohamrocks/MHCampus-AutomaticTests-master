package com.mcgraw.test.automation.framework.core.timing;

import java.util.concurrent.TimeUnit;

/**
 * Abstraction around {@link Thread#sleep(long)} to permit better testability.
 */
public interface Sleeper {

	public static final Sleeper SYSTEM_SLEEPER = new Sleeper() {
		public void sleep(Time duration) throws InterruptedException {
			Thread.sleep(duration.in(TimeUnit.MILLISECONDS));
		}
	};

	/**
	 * Sleeps for the specified duration of time.
	 *
	 * @param duration
	 *            How long to sleep.
	 * @throws InterruptedException
	 *             If hte thread is interrupted while sleeping.
	 */
	void sleep(Time duration) throws InterruptedException;

}
