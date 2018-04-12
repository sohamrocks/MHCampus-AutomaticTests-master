/**
 * 
 */
package com.mcgraw.test.automation.framework.core.exception;

/**
 * Some exception with Predefined Locators
 * 
 * @author Andrei Varabyeu
 * 
 */
public class PredefinedLocatorException extends RuntimeException
{

  private static final long serialVersionUID = 6107045312907132877L;

  public PredefinedLocatorException( String message )
  {
    super( message );
  }

  public PredefinedLocatorException( String message, Throwable t )
  {
    super( message, t );
  }
}
