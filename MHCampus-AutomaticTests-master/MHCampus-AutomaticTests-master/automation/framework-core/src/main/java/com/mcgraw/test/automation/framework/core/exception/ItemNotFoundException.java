package com.mcgraw.test.automation.framework.core.exception;

/**
 * @author yyudzitski
 */
public class ItemNotFoundException extends RuntimeException
{
  private static final long serialVersionUID = 8470378839209537048L;

  public ItemNotFoundException( String errorMessage )
  {
    super( errorMessage );
  }

  public ItemNotFoundException( String message, Throwable cause )
  {
    super( message, cause );
  }
}