package com.mcgraw.test.automation.framework.core.timing;

import java.util.concurrent.TimeUnit;

/**
 * Timer representation
 *
 * @author Andrei_Varabyeu
 *
 */
public interface Timer {

	/**
	 * Return passed time in provided unit
	 *
	 * @param timeunit
	 * @return
	 */
	long passed(TimeUnit timeunit);

	/**
	 * How long passed as human-readable string
	 *
	 * @return
	 */
	String howLongPassed();
}
