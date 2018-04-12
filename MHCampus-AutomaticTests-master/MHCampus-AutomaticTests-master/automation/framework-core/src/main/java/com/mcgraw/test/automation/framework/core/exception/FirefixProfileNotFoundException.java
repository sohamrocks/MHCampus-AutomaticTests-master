/**
 * 
 */
package com.mcgraw.test.automation.framework.core.exception;

/**
 * @author Andrei Varabyeu
 * 
 */
public class FirefixProfileNotFoundException extends RuntimeException
{

  private static final long serialVersionUID = -1792587327370938998L;

  /**
   * @param arg0
   */
  public FirefixProfileNotFoundException( String arg0 )
  {
    super( arg0 );
  }

  /**
   * @param arg0
   */
  public FirefixProfileNotFoundException( Throwable arg0 )
  {
    super( arg0 );
  }

  /**
   * @param arg0
   * @param arg1
   */
  public FirefixProfileNotFoundException( String arg0, Throwable arg1 )
  {
    super( arg0, arg1 );
  }

}
