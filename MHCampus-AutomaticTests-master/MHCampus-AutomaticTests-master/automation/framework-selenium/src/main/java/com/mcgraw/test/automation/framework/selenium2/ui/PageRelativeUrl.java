package com.mcgraw.test.automation.framework.selenium2.ui;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PageRelativeUrl {
	String value();
	boolean relative() default true;
}
