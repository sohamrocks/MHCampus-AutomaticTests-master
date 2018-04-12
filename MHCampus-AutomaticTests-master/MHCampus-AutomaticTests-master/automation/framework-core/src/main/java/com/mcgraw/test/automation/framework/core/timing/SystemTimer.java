package com.mcgraw.test.automation.framework.core.timing;

import java.util.concurrent.TimeUnit;

/**
 * SystemTimer representation
 *
 * @author Andrei Varabyeu
 *
 */
public class SystemTimer implements Timer {

	private Time startTime;

	public SystemTimer() {
		startTime = new Time(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

	/**
	 * Returns passed time since timer is created
	 *
	 * @param timeunit
	 * @return
	 */
	public long passed(TimeUnit timeunit) {
		return timeunit.convert(getPassed(), TimeUnit.MILLISECONDS);
	}

	public String howLongPassed() {

		long passed = getPassed();
		TimeUnit timeUnit = getHumanReadableUnit(passed);

		StringBuilder howLong = new StringBuilder();
		howLong.append(timeUnit.convert(passed, TimeUnit.MILLISECONDS));
		howLong.append(" ");
		howLong.append(timeUnit.toString().toLowerCase());
		return howLong.toString();

	}

	/**
	 * Gets passed time in milliseconds
	 *
	 * @return
	 */
	private long getPassed() {
		return (System.currentTimeMillis() - startTime
				.in(TimeUnit.MILLISECONDS));
	}

	/**
	 * Calculates humar-readable TimeUnit for provided duration
	 *
	 * @param duration
	 * @return
	 */
	private TimeUnit getHumanReadableUnit(long duration) {
		// one minute
		if (duration < 60000) {
			return TimeUnit.SECONDS;
		} else {
			return TimeUnit.MINUTES;
		}
	}
}
