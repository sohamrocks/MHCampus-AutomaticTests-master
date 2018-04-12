package com.mcgraw.test.automation.framework.core.exception;

/**
 * Exception is throwed when some type of WebDriver unsupported in used conditions
 * 
 * @author avarabyeu
 */
public class UnsupportedDriverTypeException extends RuntimeException
{
  private static final long serialVersionUID = -4103368802633163008L;

  public UnsupportedDriverTypeException( String message )
  {
    super( message );
  }
}