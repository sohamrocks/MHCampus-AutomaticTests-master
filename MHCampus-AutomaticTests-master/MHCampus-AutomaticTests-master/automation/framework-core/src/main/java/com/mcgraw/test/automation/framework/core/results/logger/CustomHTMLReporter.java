package com.mcgraw.test.automation.framework.core.results.logger;

import java.util.ResourceBundle;

import org.apache.velocity.VelocityContext;
import org.uncommons.reportng.HTMLReporter;
import org.uncommons.reportng.ReportNGUtils;

/**
 * Extended {@link @org.uncommons.reportng.HTMLReporter}
 * Currently uses {@link @com.mcgraw.test.automation.framework.core.results.logger.ReportNGCustomUtils} to format the tests time duration
 * 
 * @author Andrei_Turavets
 * 
 */
public class CustomHTMLReporter extends HTMLReporter {

	private static final String META_KEY = "meta";
	private static final String UTILS_KEY = "utils";

	/** Custom reportng utils which format the test duration */
	private static final ReportNGUtils UTILS = new ReportNGCustomUtils();

	private static final String MESSAGES_KEY = "messages";
	private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("org.uncommons.reportng.messages.reportng", META.getLocale());

	@Override
	protected VelocityContext createContext() {
		VelocityContext context = new VelocityContext();
		context.put(META_KEY, META);
		context.put(UTILS_KEY, UTILS);
		context.put(MESSAGES_KEY, MESSAGES);
		return context;
	}

}
