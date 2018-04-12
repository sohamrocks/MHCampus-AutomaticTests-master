package com.mcgraw.test.automation.framework.core.timing;

import com.google.common.base.Function;

/**
 * Wait representation
 *
 * @author Andrei Varabyeu
 *
 * @param <F>
 */
public interface Waitable<F> {

	<T> T until(Function<? super F, T> paramFunction);
}
