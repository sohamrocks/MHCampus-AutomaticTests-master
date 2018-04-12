package com.mcgraw.test.automation.framework.core.exception;

/**
 * @author yyudzitski
 */
public class UnimplementedMethodException extends RuntimeException
{
  private static final long serialVersionUID = -3472905454852456473L;

  public UnimplementedMethodException( String message )
  {
    super( message );
  }
}