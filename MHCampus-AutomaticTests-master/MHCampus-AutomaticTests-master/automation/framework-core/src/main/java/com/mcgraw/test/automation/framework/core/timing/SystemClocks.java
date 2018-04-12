package com.mcgraw.test.automation.framework.core.timing;

import java.util.concurrent.TimeUnit;

public class SystemClocks implements Clocks {

	public long laterBy(long durationInMillis) {
		return System.currentTimeMillis() + durationInMillis;
	}

	public boolean isNowBefore(long endInMillis) {
		return System.currentTimeMillis() < endInMillis;
	}

	public Time now() {
		return new Time(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}
}
