package com.mcgraw.test.automation.framework.core.timing;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

/**
 *
 * @author Andrei Varabyeu
 *
 */
public class FlushableAttemptLogger implements AttemptLogger {

	private Flushable flushable;

	public FlushableAttemptLogger(@Nonnull Flushable flushable) {
		this.flushable = Preconditions.checkNotNull(flushable);
	}

	public void logAttempt(int attempt, Timer timer, String description) {
		StringBuilder log = new StringBuilder();
		log.append(description);
		log.append(" ");
		log.append("[Attempt: ");
		log.append(attempt);
		log.append(", Passed: ");
		log.append(timer.howLongPassed());
		log.append("]");

		flushable.flush(log.toString());
	}
}
