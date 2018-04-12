package com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If annotation is present, locator of defined element should be cached
 * 
 * @author Andrei Varabyeu
 * 
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface CachedLocator
{
}