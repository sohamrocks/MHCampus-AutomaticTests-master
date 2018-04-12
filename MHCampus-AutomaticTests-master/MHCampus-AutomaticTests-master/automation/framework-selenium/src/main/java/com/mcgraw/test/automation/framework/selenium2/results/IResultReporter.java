package com.mcgraw.test.automation.framework.selenium2.results;

import java.util.Map;

/**
 * Reporter for Custom Results Storage
 *
 * @author Andrei Varabyeu
 *
 */
public interface IResultReporter {

	/**
	 * Reports custom (not TestNG results) provided as Map
	 *
	 * @param results
	 *            - Map with results to be imported
	 * @throws Exception
	 */
	void report(Map<?, ?> results) throws Exception;
}
