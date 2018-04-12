package com.mcgraw.test.automation.framework.core.runner.cli;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation for mapping bean fields with property sources and
 * environment variables
 *
 * @author Andrei Varabyeu
 *
 */
@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapped {
	String value();
}
