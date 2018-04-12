package com.mcgraw.test.automation.framework.core.exception.test;

/**
 * @author yyudzitski
 */
public class CommonTestException extends Exception
{
  private static final long serialVersionUID = 4150430542627979642L;

  public CommonTestException( String errorMessage )
  {
    super( errorMessage );
  }

  public CommonTestException( String message, Throwable cause )
  {
    super( message, cause );
  }
}