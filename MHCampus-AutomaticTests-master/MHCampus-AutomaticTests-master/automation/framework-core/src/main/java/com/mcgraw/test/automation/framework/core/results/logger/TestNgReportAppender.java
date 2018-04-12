package com.mcgraw.test.automation.framework.core.results.logger;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.testng.Reporter;

public class TestNgReportAppender extends AppenderSkeleton {

	@Override
	protected void append(LoggingEvent event) {
		String log = this.layout.format(event);
		// We do not need to escape HTML in case of Html output for reporter
		if (event.getLevel().toInt() != LoggerLevel.HTML_OUTPUT_INT) {
			log = StringEscapeUtils.escapeHtml4(log);
		}
		log = log.replaceAll("\n", "<br>\n");
		Reporter.log(log);

	}

	@Override
	public void close() {
	}

	@Override
	public boolean requiresLayout() {
		return true;
	}

}
