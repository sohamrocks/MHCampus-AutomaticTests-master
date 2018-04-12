package com.mcgraw.test.automation.framework.selenium2.ui;

import com.mcgraw.test.automation.framework.core.exception.UnsupportedDriverTypeException;

/**
 * Types of WebDrivers
 * 
 * @author avarabyeu
 */
public enum WebDriverType
{
  /** Mozilla Firefox browser */
  FIREFOX( "firefox" ),

  /** Google Chrome browser */
  CHROME( "chrome" ),

  /** Internet Explorer browser */
  EXPLORER( "internet explorer" ),

  /** GUI-Less browser */
  HTML_UNIT( "htmlunit" );

  private String driverName;

  private WebDriverType( String driverName )
  {
    this.driverName = driverName;
  }

  public String getDriverName()
  {
    return driverName;
  }

  /**
   * Get type of WebDriver by name
   * 
   * @param driverName Name of WebDriver
   * @return WebDriverType
   */
  public static WebDriverType getByDriverName( String driverName )
  {
    for( WebDriverType driverType : values() )
    {
      if( driverType.getDriverName().equals( driverName ) )
        return driverType;
    }
    throw new UnsupportedDriverTypeException( driverName );
  }

  @Override
  public String toString()
  {
    return getDriverName();
  }
}