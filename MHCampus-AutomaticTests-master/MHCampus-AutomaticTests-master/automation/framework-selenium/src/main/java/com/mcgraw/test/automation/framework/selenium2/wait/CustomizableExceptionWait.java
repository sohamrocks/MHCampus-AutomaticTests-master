package com.mcgraw.test.automation.framework.selenium2.wait;

import org.openqa.selenium.TimeoutException;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.mcgraw.test.automation.framework.core.timing.SmartWait;

public class CustomizableExceptionWait<T> extends SmartWait<T> {

	private Function<TimeoutException, ? extends RuntimeException> customException;

	public CustomizableExceptionWait<T> withException(
			Function<TimeoutException, ? extends RuntimeException> customizeException) {
		this.customException = customizeException;
		return this;
	}

	public CustomizableExceptionWait(T input) {
		super(input);
	}

	@Override
	public <V> V until(Function<? super T, V> isTrue) {
		try {
			return super.until(isTrue);
		} catch (TimeoutException e) {
			if (null != customException) {
				RuntimeException customized = customException.apply(e);
				Preconditions.checkNotNull(customized);
				throw customized;
			} else {
				throw e;
			}
		}

	}
}
