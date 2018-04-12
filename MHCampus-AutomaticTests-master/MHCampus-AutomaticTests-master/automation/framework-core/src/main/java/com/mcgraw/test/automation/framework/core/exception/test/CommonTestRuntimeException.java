package com.mcgraw.test.automation.framework.core.exception.test;

/**
 * @author yyudzitski
 */
public class CommonTestRuntimeException extends RuntimeException
{
  private static final long serialVersionUID = 6170092134601639565L;

  public CommonTestRuntimeException( String errorMessage )
  {
    super( errorMessage );
  }

  public CommonTestRuntimeException( String msg, Throwable cause )
  {
    super( msg, cause );
  }
}