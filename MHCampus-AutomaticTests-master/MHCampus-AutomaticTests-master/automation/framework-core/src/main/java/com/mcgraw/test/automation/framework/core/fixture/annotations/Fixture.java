package com.mcgraw.test.automation.framework.core.fixture.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON Fixture definition
 *
 * @author Andrei Varabyeu
 *
 */
@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Fixture {
	String[] names();
	boolean forceReinit() default false;
}
