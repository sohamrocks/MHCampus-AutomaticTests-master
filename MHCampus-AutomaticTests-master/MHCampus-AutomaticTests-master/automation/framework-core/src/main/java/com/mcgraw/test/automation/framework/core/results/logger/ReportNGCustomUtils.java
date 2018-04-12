package com.mcgraw.test.automation.framework.core.results.logger;

import java.util.concurrent.TimeUnit;

import org.uncommons.reportng.ReportNGUtils;

/**
 * utils which provide customDurationformat
 * used with  {@link @com.mcgraw.test.automation.framework.core.results.logger.CustomHTMLReporter}
 * 
 * @author Andrei_Turavets
 *
 */
public class ReportNGCustomUtils extends ReportNGUtils {

	@Override
	public String formatDuration(long elapsed) {

		String formattedDuration = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(elapsed),
				TimeUnit.MILLISECONDS.toMinutes(elapsed) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(elapsed)),
				TimeUnit.MILLISECONDS.toSeconds(elapsed) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsed)));
		return formattedDuration;
	}

}
