package com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Set of {@link com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator}<br>
 * {@link om.mcgraw.test.automation.framework.selenium2.ui.elements.Element} will be injected into
 * field marked by this annotation.<br>
 * Set of locators will be sorted according provided priorities. Driver tries to
 * find first valid (with founded element) locator and use it.
 *
 * @author Andrei Varabyeu
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DefinedLocators {
	DefinedLocator[] value();
}
