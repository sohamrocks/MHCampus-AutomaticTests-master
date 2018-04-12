package com.mcgraw.test.automation.framework.selenium2.wait;

import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.SystemClock;

import com.mcgraw.test.automation.framework.core.timing.Log4jFlushable;
import com.mcgraw.test.automation.framework.core.timing.Waitable;

public class LoggedWait<T> extends FluentWait<T> implements Waitable<T> {

	public LoggedWait(T input) {
		super(input, new SystemClock(), new LoggedSleeper(new Log4jFlushable()));
	}

}
