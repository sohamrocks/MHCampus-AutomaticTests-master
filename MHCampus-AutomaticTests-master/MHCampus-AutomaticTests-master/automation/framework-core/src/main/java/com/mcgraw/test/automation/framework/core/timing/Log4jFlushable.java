package com.mcgraw.test.automation.framework.core.timing;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;

/**
 * Sleeper which uses {@link Logger} to log data
 *
 * @author Andrei Varabyeu
 *
 */
public class Log4jFlushable implements Flushable {

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mcgraw.test.automation.framework.core.timing.Flushable#flush(java.lang.String)
	 */
	@Override
	public void flush(String log) {
		Logger.debug(log);
	}

}
