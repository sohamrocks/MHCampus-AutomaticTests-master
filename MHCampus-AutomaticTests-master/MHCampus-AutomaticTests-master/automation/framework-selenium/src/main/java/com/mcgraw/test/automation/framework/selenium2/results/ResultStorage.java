package com.mcgraw.test.automation.framework.selenium2.results;

import java.util.Map;

import org.apache.commons.collections.map.MultiValueMap;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;

/**
 * Storage for custom results. Independent from TestNG results
 *
 * @author Andrei Varabyeu
 *
 */
public class ResultStorage {

	/** Singleton instance */
	private static ResultStorage instance;

	/** Custom test results as multi-value map */
	private MultiValueMap failures;

	private ResultStorage() {
		failures = new MultiValueMap();
	}

	/**
	 * Returns Singleton instance of this class
	 *
	 * @return
	 */
	public static ResultStorage getInstance() {
		if (null == instance) {
			instance = new ResultStorage();
		}
		return instance;
	}

	/**
	 * Since MultiValueMap is not synchronized and not thread-save this method
	 * should be synchronized <br>
	 *
	 * @see <a href=
	 *      "http://commons.apache.org/collections/apidocs/org/apache/commons/collections/map/MultiValueMap.html">MultiValueMap.html</a>
	 *
	 * @param sauceJobInfo
	 * @param causeTags
	 */
	public synchronized void addFailure(SauceJobResult sauceJobInfo,
			String... causeTags) {
		for (String tag : causeTags) {
			failures.put(tag, sauceJobInfo);
		}
	}

	/**
	 * Check presence of failures for all tags
	 *
	 * @return Returns True in case at least one failure is present
	 */
	public boolean hasFailures() {
		Logger.debug("Result Storage - has failures: " + !failures.isEmpty());
		return !failures.isEmpty();
	}

	/**
	 * Check presence of failures for some tag
	 *
	 * @param tag
	 *            - Name of tag
	 * @return - Returns True in case failures are present
	 */
	public boolean hasFailures(String tag) {
		return failures.containsKey(tag);
	}

	/**
	 * Returns all failures for all tags
	 *
	 * @return - all failures for all tags
	 */
	public Map<?, ?> getFailures() {
		return failures;
	}

}
