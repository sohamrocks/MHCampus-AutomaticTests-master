package com.mcgraw.test.automation.framework.selenium2.ui.locator.factory;

import java.util.Set;

import org.openqa.selenium.By;

/**
 * Interface for parsing locators with provided name from provided place. Should be used with
 * {@link om.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.PredefinedLocator} annotation
 * 
 * @author Andrei Varabyeu
 * 
 */
public interface PredifinedLocatorFactory
{
  /**
   * Parses source and returns set of Selenium locators for provided name
   * 
   * @param locatorName - Name of locator in source
   * @param source - source where locators placed (like path to file)
   * @return - Set of Selenium locators
   */
  Set<By> createLocator( String locatorName, String source );
}
