package com.mcgraw.test.automation.framework.selenium2.wait;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.Sleeper;

import com.mcgraw.test.automation.framework.core.timing.Flushable;
import com.mcgraw.test.automation.framework.core.timing.SystemTimer;

/**
 * WebDriver's {@link Sleeper} replacement. <br>
 * Adds logging of attempts and passed seconds
 *
 * @author Andrei Varabyeu
 *
 */
public class LoggedSleeper implements Sleeper {

	private Flushable flushable;

	private SystemTimer timer;

	private AtomicInteger attempt;

	public LoggedSleeper(Flushable flushable) {
		this.flushable = flushable;
		timer = new SystemTimer();
		attempt = new AtomicInteger();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.openqa.selenium.support.ui.Sleeper#sleep(org.openqa.selenium.support
	 * .ui.Duration)
	 */
	@Override
	public void sleep(Duration duration) throws InterruptedException {
		StringBuilder log = new StringBuilder();
		log.append("Sleep for ");
		log.append(duration.in(TimeUnit.SECONDS));
		log.append(" second(s) [Attempt: ");
		log.append(attempt.incrementAndGet());
		log.append(", Passed: ");
		log.append(timer.howLongPassed());
		log.append("]");

		flushable.flush(log.toString());

		Thread.sleep(duration.in(TimeUnit.MILLISECONDS));
	}

}
