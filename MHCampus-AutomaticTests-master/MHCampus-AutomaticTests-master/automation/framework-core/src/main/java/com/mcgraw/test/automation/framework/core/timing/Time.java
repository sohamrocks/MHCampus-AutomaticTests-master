package com.mcgraw.test.automation.framework.core.timing;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * Represents an immutable moment of time. <br>
 * Grabbed from WebDriver implementation
 *
 * @author Andrei Varabyeu
 */
public class Time {

	private final long time;
	private final TimeUnit unit;

	/**
	 * @param time
	 *            The amount of time.
	 * @param unit
	 *            The unit of time.
	 */
	public Time(long time, TimeUnit unit) {
		Preconditions.checkArgument(time >= 0, "Time < 0: %d", time);
		Preconditions.checkNotNull(unit);
		this.time = time;
		this.unit = unit;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Time) {
			Time other = (Time) o;
			return this.time == other.time && this.unit == other.unit;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(time, unit);
	}

	@Override
	public String toString() {
		return String.format("%d %s", time, unit);
	}

	/**
	 * Converts this duration to the given unit of time.
	 *
	 * @param unit
	 *            The time unit to convert to.
	 * @return The value of this duration in the specified unit of time.
	 */
	public long in(TimeUnit unit) {
		return unit.convert(time, this.unit);
	}
}
