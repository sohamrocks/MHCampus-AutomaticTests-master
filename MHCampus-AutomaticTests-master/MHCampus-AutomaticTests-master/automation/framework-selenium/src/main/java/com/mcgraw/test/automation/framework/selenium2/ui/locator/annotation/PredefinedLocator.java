package com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Predefined locator placed on some external place
 * 
 * @author Andrei Varabyeu
 * 
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface PredefinedLocator
{
  /**
   * Locator name
   */
  String name();

  /**
   * Some where locator placed (path to file, for example)
   */
  String source();
}
